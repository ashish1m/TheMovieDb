package com.example.themoviedb;

import android.app.Application;

public class TheMovieApp extends Application {

    private static TheMovieApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static TheMovieApp getInstance() {
        return mInstance;
    }
}
