package com.example.themoviedb.repository;

import androidx.lifecycle.LiveData;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.db.AppDatabase;
import com.example.themoviedb.repository.db.dao.MovieDao;
import com.example.themoviedb.repository.db.entity.Movie;
import com.example.themoviedb.repository.remote.api.ApiClient;
import com.example.themoviedb.repository.remote.api.TMDbService;
import com.example.themoviedb.repository.remote.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository sInstance;
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mMovieList;

    private Repository(AppDatabase db) {
        mMovieDao = db.movieDao();
        mMovieList = mMovieDao.getAllMovies();
    }

    public static Repository getInstance(AppDatabase db) {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository(db);
                }
            }
        }
        return sInstance;
    }

    public void fetchTopRatedMovies() {
        ApiClient.getInstance().getTMDbService().fetchTopRatedMovies(TMDbService.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TheMovieApp.getInstance().getAppExecutor().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mMovieDao.insertAll(response.body().getResults());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }
}
