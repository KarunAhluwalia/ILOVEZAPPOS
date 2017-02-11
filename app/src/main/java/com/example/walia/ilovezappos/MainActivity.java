package com.example.walia.ilovezappos;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.walia.ilovezappos.databinding.ActivityMainBinding;
import com.example.walia.ilovezappos.model.Product;
import com.example.walia.ilovezappos.network.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends Activity implements Client.Listener {

    private Client mClient;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mClient = new Client(this);

        final Button searchButton = (Button) findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchField = (EditText) findViewById(R.id.et_query);
                String query = searchField.getText().toString();
                if (!query.trim().equals("")) {
                    mClient.search(query);
                }
            }
        });
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

    @Override
    public void onProductsResponse(@Nullable List<Product> products) {
        if (products == null || products.isEmpty()) {
            return;
        }

        mBinding.setProduct(products.get(0));

        final Animation fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FloatingActionButton addToCartButton = (FloatingActionButton)findViewById(R.id.btn_add);
        addToCartButton.setVisibility(VISIBLE);
        addToCartButton.startAnimation(fabOpen);

        addToCartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                View cartView = findViewById(R.id.layout_cart);
                cartView.setVisibility(VISIBLE);
                cartView.startAnimation(fabOpen);
            }
        });
    }
}
