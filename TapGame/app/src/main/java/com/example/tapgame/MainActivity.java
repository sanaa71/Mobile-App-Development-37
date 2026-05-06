package com.example.tapgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvScore, tvTimer, tvHighScore;
    Button btnTap;

    int score = 0;
    int highScore = 0;
    int tapSize = 200;

    SharedPreferences prefs;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        tvHighScore = findViewById(R.id.tvHighScore);
        btnTap = findViewById(R.id.btnTap);

        prefs = getSharedPreferences("TapGame", MODE_PRIVATE);
        highScore = prefs.getInt("HIGH_SCORE", 0);
        tvHighScore.setText("High Score: " + highScore);


        startTimer();

        btnTap.setOnClickListener(v -> {
            score++;
            tvScore.setText("Score: " + score);


            // HARD MODE: button shrinks
            tapSize -= 5;
            if (tapSize < 80) tapSize = 80;

            btnTap.getLayoutParams().width = tapSize;
            btnTap.getLayoutParams().height = tapSize;
            btnTap.requestLayout();
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                if (score > highScore) {
                    prefs.edit().putInt("HIGH_SCORE", score).apply();
                }

                Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("highScore", Math.max(score, highScore));
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
