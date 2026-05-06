package com.example.weatheruiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCheckWeather = findViewById(R.id.buttonCheckWeather);

        buttonCheckWeather.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, WeatherActivity.class)));
    }
}
