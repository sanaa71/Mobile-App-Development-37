package com.example.marketplaceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketplaceapp.R;
import com.example.marketplaceapp.adapters.ProductAdapter;
import com.example.marketplaceapp.api.ApiClient;
import com.example.marketplaceapp.api.ApiService;
import com.example.marketplaceapp.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // LINK XML
        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // API
        ApiService api = ApiClient.getClient().create(ApiService.class);

        // FETCH PRODUCTS
        api.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    ProductAdapter adapter =
                            new ProductAdapter(response.body(), HomeActivity.this);

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(HomeActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // OPEN ADD PRODUCT SCREEN
        addBtn.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddProductActivity.class));
        });
    }
}