package com.example.progressdialogbox;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLoad = findViewById(R.id.btnLoad);

        btnLoad.setOnClickListener(v -> {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            progressStatus=0;

            new Thread(() -> {
                while (progressStatus < 100) {

                    progressStatus += 10;

                    handler.post(() ->
                            progressDialog.setProgress(progressStatus));

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                progressDialog.dismiss();
            }).start();
        });
    }
}