package com.example.intentimplicentapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnFacebook, btnInstagram, btnTwitter, btnYoutube, btnWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnTwitter = findViewById(R.id.btnTwitter);
        btnYoutube = findViewById(R.id.btnYoutube);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);

        // Facebook
        btnFacebook.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com"));
            startActivity(intent);
        });

        // Instagram
        btnInstagram.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com"));
            startActivity(intent);
        });

        // Twitter (X)
        btnTwitter.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.twitter.com"));
            startActivity(intent);
        });

        // YouTube
        btnYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com"));
            startActivity(intent);
        });

        // WhatsApp
        btnWhatsapp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://wa.me/"));
            startActivity(intent);
        });
    }
}
