package com.example.walia.ilovezappos.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.walia.ilovezappos.model.Product;
import com.example.walia.ilovezappos.model.ProductsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client to make network calls and listen to response.
 */
public class Client implements Callback<ProductsResponse> {

    private static final String BASE_URL = "https://api.zappos.com/";
    private static final String ZAPPOS_API_KEY = "b743e26728e16b81da139182bb2094357c31d331";

    private static final String TAG = Client.class.getSimpleName();

    @NonNull private final Listener mListener;

    public Client(@NonNull  Listener listener) {
        this.mListener = listener;
    }

    /**
     * Make a network call to search for products.
     *
     * @param query The query to look for.
     */
    public void search(@NonNull String query) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ZapposAPI zapposAPI = retrofit.create(ZapposAPI.class);

        Call<ProductsResponse> call = zapposAPI.getProducts(query, ZAPPOS_API_KEY);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
        if(response.isSuccessful()) {
            List<Product> products = response.body().getResults();
            Log.d(TAG, products + "");
            mListener.onProductsResponse(products);
        } else {
            try {
                Log.e(TAG, response.errorBody().string());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<ProductsResponse> call, Throwable t) {
        Log.e(TAG, t.getMessage());
    }

    /**
     * Listener for this {@link Client}.
     */
    public interface Listener {
        void onProductsResponse(List<Product> products);
    }
}
