package com.example.marketplaceapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketplaceapp.R;
import com.example.marketplaceapp.api.ApiClient;
import com.example.marketplaceapp.api.ApiService;
import com.example.marketplaceapp.models.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    EditText title, desc, price;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // LINK XML
        title = findViewById(R.id.title);
        desc = findViewById(R.id.description);
        price = findViewById(R.id.price);
        addBtn = findViewById(R.id.addBtn);

        ApiService api = ApiClient.getClient().create(ApiService.class);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = title.getText().toString().trim();
                String d = desc.getText().toString().trim();
                String pStr = price.getText().toString().trim();

                if (t.isEmpty() || d.isEmpty() || pStr.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double pVal = Double.parseDouble(pStr);

                Product p = new Product();
                p.title = t;
                p.description = d;
                p.price = pVal;

                api.addProduct(p).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddProductActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}