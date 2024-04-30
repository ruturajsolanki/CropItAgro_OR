package com.cropitagro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.cropitagro.connection.API;
import com.cropitagro.databinding.ActivityPdfViewerBinding;

public class PdfViewerActivity extends AppCompatActivity {

    ActivityPdfViewerBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + API.baseUrl + "pocketbook_0.pdf");

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}