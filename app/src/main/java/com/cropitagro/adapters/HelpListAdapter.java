package com.cropitagro.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.cropitagro.R;
import com.cropitagro.databinding.CellHelpBinding;
import com.cropitagro.models.HelpModel;

import java.util.List;

public class HelpListAdapter extends RecyclerView.Adapter<HelpListAdapter.ViewHolder> {

    private final Context context;
    private final List<HelpModel.Data> list;

    public HelpListAdapter(Context context, List<HelpModel.Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cell_help, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HelpModel.Data model = list.get(position);
        holder.binding.tvTitle.setText(model.title);
        holder.binding.tvDescription.setText(model.details);
        holder.binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneCall(view.getContext(), model.phone);
            }
        });
        boolean isLastPosition = position == getItemCount() - 1;
        holder.binding.space.setVisibility(isLastPosition ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CellHelpBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CellHelpBinding.bind(itemView);
        }
    }

    void phoneCall(Context context, String phoneNumber) {
        int isGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (isGranted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.CALL_PHONE
            }, 0);
            return;
        }
        String uri = "tel:" + phoneNumber.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }
}
