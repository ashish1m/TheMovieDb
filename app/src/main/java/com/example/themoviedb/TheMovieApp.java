package com.example.themoviedb;

import android.app.Application;

import com.example.themoviedb.executor.AppExecutor;
import com.example.themoviedb.repository.Repository;
import com.example.themoviedb.repository.db.AppDatabase;

public class TheMovieApp extends Application {

    private static TheMovieApp sInstance;
    private AppExecutor mAppExecutor;

    public static TheMovieApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mAppExecutor = new AppExecutor();

        getRepository().fetchConfiguration();
        getRepository().fetchTopRatedMovies();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getDatabase(this);
    }

    public Repository getRepository() {
        return Repository.getInstance(getDatabase());
    }

    public AppExecutor getAppExecutor() {
        return mAppExecutor;
    }
}
