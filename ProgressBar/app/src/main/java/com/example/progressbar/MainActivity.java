package com.example.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtPercent;
    Button btnStart;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        txtPercent = findViewById(R.id.txtPercent);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            progressStatus = 0;

            new Thread(() -> {
                while (progressStatus < 100) {
                    handler.post(() -> {
                        progressBar.setProgress(progressStatus);
                        txtPercent.setText(progressStatus + "%");
                    });

                    progressStatus += 10;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}