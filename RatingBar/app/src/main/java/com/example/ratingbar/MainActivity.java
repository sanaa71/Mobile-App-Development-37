package com.example.ratingbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ratingBar=findViewById(R.id.ratingBar);
        btnSubmit=findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v ->{
            float rating=ratingBar.getRating();
            Toast.makeText(MainActivity.this,"Rating: "+rating,Toast.LENGTH_SHORT).show();

        });
        ratingBar.setOnRatingBarChangeListener((ratingBar,rating,fromUser)->{
            Toast.makeText(MainActivity.this,"You selected: "+rating,Toast.LENGTH_SHORT).show();

        });

    }
}