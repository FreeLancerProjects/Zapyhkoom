package com.creativeshare.zapyhakoom.services;


import com.creativeshare.zapyhakoom.Model.BalanceCount;
import com.creativeshare.zapyhakoom.Model.Data_Model;
import com.creativeshare.zapyhakoom.Model.Catogry_Model_Slide;
import com.creativeshare.zapyhakoom.Model.Item_Cart_Model;
import com.creativeshare.zapyhakoom.Model.Model_Offr_Product;
import com.creativeshare.zapyhakoom.Model.PlaceGeocodeData;
import com.creativeshare.zapyhakoom.Model.Product_Model;
import com.creativeshare.zapyhakoom.Model.Orders_Model;
import com.creativeshare.zapyhakoom.Model.Offers_Model;
import com.creativeshare.zapyhakoom.Model.PlaceMapDetailsData;
import com.creativeshare.zapyhakoom.Model.Setting_Model;
import com.creativeshare.zapyhakoom.Model.Slider_Model;
import com.creativeshare.zapyhakoom.Model.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Services {


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("mobile") String mobile//,
                          // @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> register(
            @Field("mobile") String mmobile,
            @Field("name") String name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/update")
    Call<UserModel> update_profile(
            @Field("user_id") Integer user_id,
            @Field("name") String name,
            @Field("password") String password,
            @Field("mobile") String mobile

    );

    @GET("api/categories")
    Call<Catogry_Model_Slide> getcateogries(
    );

    @GET("api/slider")
    Call<Slider_Model> get_slider();

    @GET("api/products")
    Call<Product_Model> get_product
            (@Query("category_id") Integer category_id
            )
            ;

    @GET("api/offers")
    Call<Offers_Model> getoffrs(


    );

    @GET("api/offer")
    Call<Model_Offr_Product> get_product_offrid
            (@Query("offer_id") Integer offer_id);

    @FormUrlEncoded
    @POST("api/contact-us")
    Call<ResponseBody> contact_us(
            @Field("name") String name,
            @Field("email") String email,
            @Field("message") String message

    );

    @GET("api/orders")
    Call<Orders_Model> get_orders(
            @Query("user_id") Integer user_id,
            @Query("status") Integer status


    );

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);

    @POST("api/create-order")
    Call<Orders_Model> accept_orders(@Body Item_Cart_Model item_cart_model);

    @GET("api/settings")
    Call<Setting_Model> get_setting();
    @FormUrlEncoded
    @POST("api/get-balance")
    Call<BalanceCount> getBalance(
            @Field("user_id") Integer user_id

    );

    @FormUrlEncoded
    @POST("api/finish-order")
    Call<Data_Model> finish_order(
            @Field("order_id") Integer order_id,
            @Field("user_id") Integer user_id

    );

    @FormUrlEncoded
    @POST("api/rate")
    Call<ResponseBody> rate_sales(
            @Field("from_user") Integer from_user,
            @Field("to_user") Integer user_id,
            @Field("rate") float rate

    );

    @FormUrlEncoded
    @POST("api/cancel-order")
    Call<Data_Model> cancel_order(
            @Field("order_id") Integer order_id,
            @Field("user_id") Integer user_id
    );

    @GET("api/sales-orders")
    Call<Orders_Model> getsales_order(
            @Query("sales_id") Integer sales_id,
            @Query("status") Integer status

    );

}
