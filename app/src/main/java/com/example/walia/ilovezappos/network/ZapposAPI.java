package com.example.walia.ilovezappos.network;

import com.example.walia.ilovezappos.model.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit API for Zappos.
 */
public interface ZapposAPI {

    @GET("Search")
    Call<ProductsResponse> getProducts(@Query("term") String status, @Query("key") String key);

}
