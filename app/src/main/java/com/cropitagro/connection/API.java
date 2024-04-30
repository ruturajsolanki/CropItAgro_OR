package com.cropitagro.connection;

import com.cropitagro.models.AgricultureModel;
import com.cropitagro.models.HelpModel;
import com.cropitagro.models.MessageModel;
import com.cropitagro.models.ProductModel;
import com.cropitagro.models.ResponseModel;
import com.cropitagro.models.SellerModel;
import com.cropitagro.models.ShopModel;
import com.cropitagro.models.UserModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    String baseUrl = "http://cropitagro.kstechnologies.co/API/";

    String loginCustomer = "userlogin.php";
    String registerCustomer = "signup.php";
    String loginSeller = "sellerlogin.php";
    String registerSeller = "shop_signup.php";
    String updateProfile = "update_user.php";
    String getAgriculture = "getagri.php";
    String getHelps = "gethelps.php";
    String getMessages = "get_messages.php";
    String sendMessage = "insert_msg.php";
    String getShops = "get_shops.php";
    String getShopsByArea = "get_shops_by_area.php";
    String getProducts = "get_products_sellerid.php";
    String addProduct = "add_product.php";

    interface Connect {

        @POST(API.loginCustomer)
        Call<UserModel> loginCustomer(@Body HashMap<String, String> params);

        @POST(API.loginSeller)
        Call<SellerModel> loginSeller(@Body HashMap<String, String> params);

        @POST(API.registerCustomer)
        Call<ResponseModel> registerCustomer(@Body HashMap<String, String> params);

        @POST(API.registerSeller)
        Call<ResponseModel> registerSeller(@Body HashMap<String, String> params);

        @POST(API.updateProfile)
        Call<String> updateProfile(@Body RequestBody requestBody);

        @POST(API.getAgriculture)
        Call<AgricultureModel> getAgriculture(@Body HashMap<String, String> params);

        @POST(API.getHelps)
        Call<HelpModel> getHelp(@Body HashMap<String, String> params);

        @POST(API.getMessages)
        Call<MessageModel> getMessages(@Body HashMap<String, String> params);

        @POST(API.sendMessage)
        Call<ResponseModel> sendMessage(@Body HashMap<String, String> params);

        @POST(API.getProducts)
        Call<ProductModel> getProducts(@Body HashMap<String, String> params);

        @POST(API.addProduct)
        Call<ResponseModel> addProduct(@Body HashMap<String, String> params);

        @POST(API.getShops)
        Call<ShopModel> getShops(@Body HashMap<String, String> params);

        @POST(API.getShopsByArea)
        Call<ShopModel> getShopsByArea(@Body HashMap<String, String> params);
    }
}
