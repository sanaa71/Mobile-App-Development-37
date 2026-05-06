package com.example.marketplaceapp.api;

import com.example.marketplaceapp.models.User;
import com.example.marketplaceapp.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("auth/register")
    Call<User> register(@Body User user);

    @POST("auth/login")
    Call<User> login(@Body User user);

    @GET("products")
    Call<List<Product>> getProducts();

    @POST("products")
    Call<Product> addProduct(@Body Product product);
}