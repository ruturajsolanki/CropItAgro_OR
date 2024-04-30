package com.cropitagro.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cropitagro.BuildConfig;
import com.cropitagro.R;
import com.cropitagro.adapters.HelpListAdapter;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityHomeBinding;
import com.cropitagro.models.AgricultureModel;
import com.cropitagro.models.HelpModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.FusedLocationHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, FusedLocationHelper.LocationListener {

    private GoogleMap googleMap;
    private ActivityHomeBinding binding;

    FusedLocationHelper locationHelper;
    private static final int DEFAULT_ZOOM = 18;
    private static final int DEFAULT_TILT = 0;
    private static final int DEFAULT_KMS = 10;

    private final List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationHelper = new FusedLocationHelper(this, "location", BuildConfig.DEBUG);
        binding.btnMessages.setOnClickListener(this);
        binding.btnMore.setOnClickListener(this);
        binding.btnShops.setOnClickListener(this);
        binding.btnInfo.setOnClickListener(this);
        binding.btnregion.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationHelper.onDestroy();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(true);

        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                openMarker(marker);
                return false;
            }
        });

        locationHelper.initializeLocationProviders();
        locationHelper.setListener(this);
        locationHelper.startLocationUpdates();

        getHelp();
    }

    void getAgriculture(Location location) {
        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<AgricultureModel> call = connect.getAgriculture(new HashMap<>());
        call.enqueue(new Callback<AgricultureModel>() {
            @Override
            public void onResponse(@NonNull Call<AgricultureModel> call, @NonNull Response<AgricultureModel> response) {
                googleMap.clear();

                AgricultureModel agricultureModel = response.body();
                assert agricultureModel != null;
                Log.d("onResponse>>", "JSON: " + new Gson().toJson(agricultureModel));

                for (AgricultureModel.Data data : agricultureModel.data) {
                    double latitude = Double.parseDouble(data.latitude);
                    double longitude = Double.parseDouble(data.longitude);

                    Location newLocation = new Location(data.title);
                    newLocation.setLatitude(latitude);
                    newLocation.setLongitude(longitude);

                    float distanceInMetres = location.distanceTo(newLocation);
                    if (distanceInMetres <= (DEFAULT_KMS * 1000)) {
                        createMarker(data.title, data.details, new LatLng(latitude, longitude));
                    }
                }
                Log.d("onResponse>>", "Markers Count: " + markers.size());
            }

            @Override
            public void onFailure(@NonNull Call<AgricultureModel> call, @NonNull Throwable t) {
                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getHelp() {
        binding.recyclerView.setVisibility(View.GONE);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<HelpModel> call = connect.getHelp(new HashMap<>());
        call.enqueue(new Callback<HelpModel>() {
            @Override
            public void onResponse(@NonNull Call<HelpModel> call, @NonNull Response<HelpModel> response) {
                HelpModel helpModel = response.body();
                assert helpModel != null;
                binding.recyclerView.setVisibility(!helpModel.data.isEmpty() ? View.VISIBLE : View.GONE);

                HelpListAdapter adapter = new HelpListAdapter(HomeActivity.this, helpModel.data);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<HelpModel> call, @NonNull Throwable t) {
                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void createMarker(String title, String description, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(description)
                .flat(true)
                .draggable(false);
        markers.add(googleMap.addMarker(markerOptions));
    }


    void openMarker(@NonNull Marker marker) {
        new AlertDialog.Builder(this)
                .setTitle(marker.getTitle())
                .setMessage(marker.getSnippet())
                .setPositiveButton("Locate", (dialog, which) -> {
                    String direction = String.format(Locale.getDefault(), "%s,%s", String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude));
                    openGoogleMaps(HomeActivity.this, direction);
                })
                .setNegativeButton("Dismiss", null)
                .show();
    }

    void openGoogleMaps(Context context, String direction) {
        if (direction == null || direction.isEmpty()) {
            return;
        }
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + direction.trim() + "&mode=d"));
        navigationIntent.setPackage("com.google.android.apps.maps");
        navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(navigationIntent);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnMessages) {
            startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
        } else if (id == R.id.btnMore) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        } else if (id == R.id.btnShops) {
            startActivity(new Intent(HomeActivity.this, ShopListActivity.class));
        } else if (id == R.id.btnInfo) {
            startActivity(new Intent(HomeActivity.this, PdfViewerActivity.class));
        } else if (id == R.id.btnregion) {
            startActivity(new Intent(HomeActivity.this, RegionActivity.class));
        }
    }

    @Override
    public void onLocationReceived(@NonNull Location location) {
        locationHelper.stopLocationUpdates();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(currentLatLng)
                .tilt(DEFAULT_TILT)
                .zoom(DEFAULT_ZOOM).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        getAgriculture(location);
    }

    @Override
    public void onLocationAvailability(boolean isAvailable) {

    }
}