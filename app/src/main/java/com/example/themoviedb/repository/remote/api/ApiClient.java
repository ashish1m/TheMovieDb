package com.example.themoviedb.repository.remote.api;

import android.app.Application;

import com.example.themoviedb.TheMovieApp;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient mInstance;
    private Retrofit mRetrofit;

    private ApiClient() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl(TMDbService.BASE_API)
                .client(getOkHttpClient(getCache(TheMovieApp.getInstance())))
                .build();
    }

    public static ApiClient getInstance() {
        if (mInstance == null) {
            synchronized (ApiClient.class) {
                if (mInstance == null)
                    mInstance = new ApiClient();
            }
        }
        return mInstance;
    }

    private Cache getCache(Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    private OkHttpClient getOkHttpClient(Cache cache) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging);

        return httpClient.build();
    }

    public TMDbService getGithubRepoService() {
        return mRetrofit.create(TMDbService.class);
    }
}
