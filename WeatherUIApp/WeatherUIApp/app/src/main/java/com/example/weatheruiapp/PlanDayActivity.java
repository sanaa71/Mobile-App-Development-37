package com.example.weatheruiapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class PlanDayActivity extends AppCompatActivity {

    private ConstraintLayout layoutPlanDayRoot;
    private TextView textViewPlanCondition;
    private TextView textViewOutfitDescription;
    private TextView textViewItemsDescription;
    private TextView textViewActivityDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_day);

        layoutPlanDayRoot = findViewById(R.id.layoutPlanDayRoot);
        MaterialToolbar toolbar = findViewById(R.id.toolbarPlanDay);
        MaterialButton buttonBack = findViewById(R.id.buttonBackPlan);
        textViewPlanCondition = findViewById(R.id.textViewPlanCondition);
        textViewOutfitDescription = findViewById(R.id.textViewOutfitDescription);
        textViewItemsDescription = findViewById(R.id.textViewItemsDescription);
        textViewActivityDescription = findViewById(R.id.textViewActivityDescription);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        buttonBack.setOnClickListener(view -> finish());

        // Default to Cloudy so the screen still works even if no condition is passed.
        String condition = getIntent() != null
                ? getIntent().getStringExtra(WeatherActivity.EXTRA_WEATHER_CONDITION)
                : null;
        if (condition == null || condition.trim().isEmpty()) {
            condition = "Cloudy";
        }

        textViewPlanCondition.setText(getString(R.string.weather_condition_format, condition));
        applyBackground(condition);
        showDayPlan(condition);
    }

    private void showDayPlan(String condition) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());
        String outfit;
        String items;
        String activity;

        if (lowerCondition.contains("sun")) {
            outfit = getString(R.string.plan_outfit_sunny);
            items = getString(R.string.plan_items_sunny);
            activity = getString(R.string.plan_activity_sunny);
        } else if (lowerCondition.contains("rain")) {
            outfit = getString(R.string.plan_outfit_rainy);
            items = getString(R.string.plan_items_rainy);
            activity = getString(R.string.plan_activity_rainy);
        } else if (lowerCondition.contains("wind")) {
            outfit = getString(R.string.plan_outfit_windy);
            items = getString(R.string.plan_items_windy);
            activity = getString(R.string.plan_activity_windy);
        } else {
            outfit = getString(R.string.plan_outfit_cloudy);
            items = getString(R.string.plan_items_cloudy);
            activity = getString(R.string.plan_activity_cloudy);
        }

        textViewOutfitDescription.setText(outfit);
        textViewItemsDescription.setText(items);
        textViewActivityDescription.setText(activity);
    }

    private void applyBackground(String condition) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());

        if (lowerCondition.contains("sun")) {
            layoutPlanDayRoot.setBackgroundResource(R.drawable.bg_sunny);
        } else if (lowerCondition.contains("rain")) {
            layoutPlanDayRoot.setBackgroundResource(R.drawable.bg_rainy);
        } else if (lowerCondition.contains("wind")) {
            layoutPlanDayRoot.setBackgroundResource(R.drawable.bg_windy);
        } else {
            layoutPlanDayRoot.setBackgroundResource(R.drawable.bg_cloudy);
        }
    }
}
