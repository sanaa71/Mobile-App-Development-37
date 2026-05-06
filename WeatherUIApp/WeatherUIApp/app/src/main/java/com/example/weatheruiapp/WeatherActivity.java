package com.example.weatheruiapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    public static final String EXTRA_WEATHER_CONDITION =
            "com.example.weatheruiapp.extra.WEATHER_CONDITION";

    private ConstraintLayout layoutWeatherRoot;
    private Spinner spinnerCities;
    private TextInputEditText editTextNewCity;
    private TextInputEditText editTextTemperature;
    private TextInputEditText editTextCondition;
    private MaterialCardView cardWeatherResult;
    private TextView textViewCityName;
    private TextView textViewTemperature;
    private TextView textViewCondition;
    private TextView textViewWeatherTip;
    private TextView textViewLastUpdated;
    private ImageView imageViewWeatherIcon;
    private MaterialButton buttonShareWeather;
    private MaterialButton buttonPlanDay;

    private final LinkedHashMap<String, WeatherInfo> weatherDataMap = new LinkedHashMap<>();
    private final ArrayList<String> cityList = new ArrayList<>();
    private ArrayAdapter<String> cityAdapter;
    private WeatherInfo currentWeatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        layoutWeatherRoot = findViewById(R.id.layoutWeatherRoot);
        MaterialToolbar toolbar = findViewById(R.id.toolbarWeather);
        spinnerCities = findViewById(R.id.spinnerCities);
        editTextNewCity = findViewById(R.id.editTextNewCity);
        editTextTemperature = findViewById(R.id.editTextTemperature);
        editTextCondition = findViewById(R.id.editTextCondition);
        MaterialButton buttonShowWeather = findViewById(R.id.buttonShowWeather);
        MaterialButton buttonAddCity = findViewById(R.id.buttonAddCity);
        MaterialButton buttonAbout = findViewById(R.id.buttonAbout);
        buttonShareWeather = findViewById(R.id.buttonShareWeather);
        buttonPlanDay = findViewById(R.id.buttonPlanDay);
        cardWeatherResult = findViewById(R.id.cardWeatherResult);
        textViewCityName = findViewById(R.id.textViewCityName);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewCondition = findViewById(R.id.textViewCondition);
        textViewWeatherTip = findViewById(R.id.textViewWeatherTip);
        textViewLastUpdated = findViewById(R.id.textViewLastUpdated);
        imageViewWeatherIcon = findViewById(R.id.imageViewWeatherIcon);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        initializeWeatherData();
        setupSpinner();
        applyWeatherBackground("Cloudy");

        buttonShowWeather.setOnClickListener(view -> showWeatherForSelectedCity());
        buttonAddCity.setOnClickListener(view -> addOrUpdateCustomCity());
        buttonShareWeather.setOnClickListener(view -> shareCurrentWeather());
        buttonPlanDay.setOnClickListener(view -> openPlanDayScreen());
        buttonAbout.setOnClickListener(view ->
                startActivity(new Intent(WeatherActivity.this, AboutActivity.class)));
    }

    private void initializeWeatherData() {
        addWeatherInfo(new WeatherInfo("Pune", "30\u00B0C", "Sunny"));
        addWeatherInfo(new WeatherInfo("Mumbai", "28\u00B0C", "Rainy"));
        addWeatherInfo(new WeatherInfo("Delhi", "25\u00B0C", "Cloudy"));
        addWeatherInfo(new WeatherInfo("Bangalore", "27\u00B0C", "Windy"));
    }

    private void setupSpinner() {
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(cityAdapter);
    }

    private void showWeatherForSelectedCity() {
        String selectedCity = spinnerCities.getSelectedItem().toString();
        WeatherInfo weatherInfo = weatherDataMap.get(selectedCity);

        if (weatherInfo == null) {
            Toast.makeText(this, R.string.weather_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        cardWeatherResult.setVisibility(android.view.View.VISIBLE);
        textViewCityName.setText(weatherInfo.cityName);
        textViewTemperature.setText(weatherInfo.temperature);
        textViewCondition.setText(weatherInfo.condition);
        textViewWeatherTip.setText(buildWeatherTip(weatherInfo.condition, weatherInfo.temperature));
        textViewLastUpdated.setText(getString(
                R.string.last_updated_format,
                new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                        .format(System.currentTimeMillis())
        ));
        imageViewWeatherIcon.setImageResource(weatherInfo.iconResId);
        cardWeatherResult.setCardBackgroundColor(
                ContextCompat.getColor(this, getCardColorForCondition(weatherInfo.condition))
        );

        currentWeatherInfo = weatherInfo;
        buttonShareWeather.setEnabled(true);
        buttonPlanDay.setEnabled(true);
        applyWeatherBackground(weatherInfo.condition);
    }

    private void addOrUpdateCustomCity() {
        String cityName = getInputValue(editTextNewCity);
        String temperature = formatTemperature(getInputValue(editTextTemperature));
        String condition = formatCondition(getInputValue(editTextCondition));

        if (cityName.isEmpty() || temperature.isEmpty() || condition.isEmpty()) {
            Toast.makeText(this, R.string.enter_all_city_details, Toast.LENGTH_SHORT).show();
            return;
        }

        WeatherInfo weatherInfo = new WeatherInfo(cityName, temperature, condition);
        boolean alreadyExists = weatherDataMap.containsKey(cityName);

        addWeatherInfo(weatherInfo);
        cityAdapter.notifyDataSetChanged();
        spinnerCities.setSelection(cityList.indexOf(cityName));
        clearCustomInputs();
        showWeatherForSelectedCity();

        int messageRes = alreadyExists ? R.string.city_updated_success : R.string.city_added_success;
        Toast.makeText(this, getString(messageRes, cityName), Toast.LENGTH_SHORT).show();
    }

    private void addWeatherInfo(WeatherInfo weatherInfo) {
        weatherDataMap.put(weatherInfo.cityName, weatherInfo);
        if (!cityList.contains(weatherInfo.cityName)) {
            cityList.add(weatherInfo.cityName);
        }
    }

    private void shareCurrentWeather() {
        if (currentWeatherInfo == null) {
            Toast.makeText(this, R.string.select_city_first, Toast.LENGTH_SHORT).show();
            return;
        }

        String shareMessage = getString(
                R.string.share_weather_text,
                currentWeatherInfo.cityName,
                currentWeatherInfo.temperature,
                currentWeatherInfo.condition,
                buildWeatherTip(currentWeatherInfo.condition, currentWeatherInfo.temperature)
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_weather)));
    }

    private void openPlanDayScreen() {
        if (currentWeatherInfo == null) {
            Toast.makeText(this, R.string.select_city_first, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(WeatherActivity.this, PlanDayActivity.class);
        intent.putExtra(EXTRA_WEATHER_CONDITION, currentWeatherInfo.condition);
        startActivity(intent);
    }

    private String getInputValue(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }

    private String formatTemperature(String value) {
        if (value.isEmpty()) {
            return "";
        }

        return value.contains("\u00B0") ? value : value + "\u00B0C";
    }

    private String formatCondition(String value) {
        if (value.isEmpty()) {
            return "";
        }

        String normalized = value.toLowerCase(Locale.getDefault());
        return normalized.substring(0, 1).toUpperCase(Locale.getDefault()) + normalized.substring(1);
    }

    private void clearCustomInputs() {
        editTextNewCity.setText("");
        editTextTemperature.setText("");
        editTextCondition.setText("");
    }

    private String buildWeatherTip(String condition, String temperature) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());
        int temperatureValue = extractTemperatureValue(temperature);

        if (lowerCondition.contains("sun")) {
            return getString(R.string.tip_sunny);
        }
        if (lowerCondition.contains("rain")) {
            return getString(R.string.tip_rainy);
        }
        if (lowerCondition.contains("wind")) {
            return getString(R.string.tip_windy);
        }
        if (temperatureValue >= 32) {
            return getString(R.string.tip_hot);
        }
        return getString(R.string.tip_cloudy);
    }

    private int extractTemperatureValue(String temperature) {
        String digitsOnly = temperature.replaceAll("[^0-9-]", "");
        if (digitsOnly.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(digitsOnly);
    }

    private void applyWeatherBackground(String condition) {
        layoutWeatherRoot.setBackgroundResource(getBackgroundForCondition(condition));
    }

    private int getBackgroundForCondition(String condition) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());

        if (lowerCondition.contains("sun")) {
            return R.drawable.bg_sunny;
        }
        if (lowerCondition.contains("rain")) {
            return R.drawable.bg_rainy;
        }
        if (lowerCondition.contains("wind")) {
            return R.drawable.bg_windy;
        }
        return R.drawable.bg_cloudy;
    }

    private int getCardColorForCondition(String condition) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());

        if (lowerCondition.contains("sun")) {
            return R.color.card_sunny;
        }
        if (lowerCondition.contains("rain")) {
            return R.color.card_rainy;
        }
        if (lowerCondition.contains("wind")) {
            return R.color.card_windy;
        }
        return R.color.card_cloudy;
    }

    private static int resolveWeatherIcon(String condition) {
        String lowerCondition = condition.toLowerCase(Locale.getDefault());

        if (lowerCondition.contains("sun")) {
            return R.drawable.ic_sun;
        }
        if (lowerCondition.contains("rain")) {
            return R.drawable.ic_rain;
        }
        if (lowerCondition.contains("wind")) {
            return R.drawable.ic_windy;
        }
        return R.drawable.ic_cloud;
    }

    private static class WeatherInfo {
        private final String cityName;
        private final String temperature;
        private final String condition;
        private final int iconResId;

        private WeatherInfo(String cityName, String temperature, String condition) {
            this.cityName = cityName;
            this.temperature = temperature;
            this.condition = condition;
            this.iconResId = resolveWeatherIcon(condition);
        }
    }
}
