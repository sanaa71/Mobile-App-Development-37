package com.example.marketplaceapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // SAVE TOKEN
    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // GET TOKEN
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // CHECK LOGIN
    public boolean isLoggedIn() {
        return getToken() != null;
    }

    // LOGOUT
    public void logout() {
        editor.clear();
        editor.apply();
    }
}