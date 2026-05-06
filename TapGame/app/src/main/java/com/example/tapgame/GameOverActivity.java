package com.example.tapgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView tvFinalScore = findViewById(R.id.tvFinalScore);
        TextView tvFinalHigh = findViewById(R.id.tvFinalHigh);
        Button btnRestart = findViewById(R.id.btnRestart);

        int score = getIntent().getIntExtra("score", 0);
        int highScore = getIntent().getIntExtra("highScore", 0);

        tvFinalScore.setText("Score: " + score);
        tvFinalHigh.setText("High Score: " + highScore);

        btnRestart.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
