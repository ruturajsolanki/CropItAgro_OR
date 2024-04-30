package com.cropitagro.connection;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by android on 11/11/2016.
 */

public interface Connect {

    @POST(API.loginSeller)
    Call<String> login(@Body RequestBody requestBody);

    @POST(API.registerCustomer)
    Call<String> signUp(@Body RequestBody requestBody);

    @POST(API.updateProfile)
    Call<String> updateProfile(@Body RequestBody requestBody);


}



