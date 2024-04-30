package com.cropitagro.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.cropitagro.R;
import com.cropitagro.adapters.MessageListAdapter;
import com.cropitagro.connection.API;
import com.cropitagro.connection.Client;
import com.cropitagro.databinding.ActivityMessagesBinding;
import com.cropitagro.databinding.ActivitySendMessageBinding;
import com.cropitagro.models.MessageModel;
import com.cropitagro.models.ResponseModel;
import com.cropitagro.models.UserModel;
import com.cropitagro.tools.Constants;
import com.cropitagro.tools.Loader;
import com.cropitagro.tools.ValidationHelper;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMessagesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(this);
        binding.btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMessages();
    }

    void getMessages() {
        UserModel.Data data = Constants.shared().getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("mobileno", data.phone);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<MessageModel> call = connect.getMessages(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                loader.dismiss();

                try {
                    MessageModel messageModel = response.body();
                    assert messageModel != null;
                    Log.d("onResponse>>", "JSON: " + new Gson().toJson(messageModel));

                    MessageListAdapter adapter = new MessageListAdapter(MessagesActivity.this, messageModel.data, new MessageListAdapter.MessageListAdapterListener() {
                        @Override
                        public void onReply(String phone) {
                            createMessage(phone);
                        }
                    });
                    binding.recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(MessagesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(MessagesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void createMessage(String phone) {
        Dialog dialog = new Dialog(this);
        ActivitySendMessageBinding binding = ActivitySendMessageBinding.inflate(getLayoutInflater());

        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM);
            window.getAttributes().windowAnimations = com.google.android.material.R.style.Animation_Design_BottomSheetDialog;
        }
        dialog.setContentView(binding.getRoot());
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }

        binding.etPhone.setText(phone);
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidationHelper.isValidPhoneNumber(binding.etPhone)) {
                    return;
                }
                if (!ValidationHelper.isNonEmpty(binding.etMessage)) {
                    return;
                }

                String phone = binding.etPhone.getText().toString().trim();
                String message = binding.etMessage.getText().toString().trim();
                sendMessage(phone, message, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        getMessages();
                    }
                });
            }
        });
        dialog.show();
    }

    void sendMessage(String phone, String message, View.OnClickListener listener) {
        UserModel.Data data = Constants.shared().getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("from_number", data.phone);
        params.put("to_number", phone);
        params.put("msg", message);

        API.Connect connect = Client.getClient().create(API.Connect.class);
        Call<ResponseModel> call = connect.sendMessage(params);

        Dialog loader = new Loader(this).getDialog();
        loader.show();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                loader.dismiss();

                try {
                    ResponseModel responseModel = response.body();
                    assert responseModel != null;
                    if (!responseModel.response) {
                        return;
                    }
                    Log.d("onResponse>>", "JSON: " + new Gson().toJson(responseModel));
                    listener.onClick(null);

                } catch (Exception e) {
                    Log.d("onResponse>>", "Error: " + e.getMessage());
                    Toast.makeText(MessagesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                loader.dismiss();

                Log.d("onFailure>>", "Error: " + t.getMessage());
                Toast.makeText(MessagesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnAdd) {
            createMessage("");
        } else if (id == R.id.btnBack) {
            finish();
        }
    }
}