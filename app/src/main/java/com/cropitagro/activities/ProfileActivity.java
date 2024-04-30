package com.cropitagro.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cropitagro.databinding.ActivityProfileBinding;
import com.cropitagro.models.UserModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.ValidationHelper;


public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserModel.Data data = Constants.shared().getUser();

        String name = data.name;
        if (name != null && !name.isEmpty()) {
            binding.etFirstName.setText(name.split(" ")[0]);
            binding.etLastName.setText(name.split(" ")[1]);
        }
        binding.etEmail.setText(data.email);
        binding.etPhone.setText(data.phone);

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.shared().clear();

                        startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    void updateUserProfile() {
        if (!ValidationHelper.isValidString(binding.etFirstName, 3)) {
            return;
        }
        if (!ValidationHelper.isValidString(binding.etLastName, 3)) {
            return;
        }
        if (!ValidationHelper.isValidEmail(binding.etEmail)) {
            return;
        }
        if (!ValidationHelper.isValidPhoneNumber(binding.etPhone)) {
            return;
        }

    }
}