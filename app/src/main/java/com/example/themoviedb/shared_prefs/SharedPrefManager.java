package com.example.themoviedb.shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.remote.model.configuration.Configuration;
import com.google.gson.Gson;

public class SharedPrefManager {

    private static SharedPrefManager sInstance;
    private final String PREFS_FILE_KEY = "com.example.themoviedb.SharedPrefs";
    private final SharedPreferences mSharedPref;


    private SharedPrefManager() {
        mSharedPref =
                TheMovieApp.getInstance().getSharedPreferences(PREFS_FILE_KEY, Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance() {
        if (sInstance == null) {
            synchronized (SharedPrefManager.class) {
                if (sInstance == null) {
                    sInstance = new SharedPrefManager();
                }
            }
        }
        return sInstance;
    }

    public void saveConfiguration(Configuration configuration) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        String configStr = new Gson().toJson(configuration);
        editor.putString(SharedPrefKey.CONFIGURATION, configStr);
        editor.apply();
    }

    public Configuration getConfiguration() {
        String configStr = mSharedPref.getString(SharedPrefKey.CONFIGURATION, "");
        return new Gson().fromJson(configStr, Configuration.class);
    }


}
