package com.example.themoviedb.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.db.AppDatabase;
import com.example.themoviedb.repository.db.dao.MovieDao;
import com.example.themoviedb.repository.db.entity.Movie;
import com.example.themoviedb.repository.remote.api.ApiClient;
import com.example.themoviedb.repository.remote.api.TMDbService;
import com.example.themoviedb.repository.remote.model.configuration.ConfigurationResponse;
import com.example.themoviedb.repository.remote.model.movie_credit.CreditResponse;
import com.example.themoviedb.repository.remote.model.movie_detail.MovieDetailResponse;
import com.example.themoviedb.repository.remote.model.movie_list.MovieListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository sInstance;
    private final TMDbService mTMDbService;
    private final MovieDao mMovieDao;
    private LiveData<List<Movie>> mMovieList;
    private MutableLiveData<MovieDetailResponse> mMovieDetail;
    private MutableLiveData<CreditResponse> mMovieCredit;

    private Repository(AppDatabase db) {
        mMovieDao = db.movieDao();
        mMovieList = mMovieDao.getAllMovies();
        mTMDbService = ApiClient.getInstance().getTMDbService();
        mMovieDetail = new MutableLiveData<>();
        mMovieCredit = new MutableLiveData<>();
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

    public void getConfiguration() {
        mTMDbService.getConfiguration(TMDbService.API_KEY).enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {

            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchTopRatedMovies() {
        mTMDbService.fetchTopRatedMovies(TMDbService.API_KEY).enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, final Response<MovieListResponse> response) {
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
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMovieDetail(String movieId) {
        mTMDbService.getMovieDetail(TMDbService.API_KEY, movieId).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovieDetail.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMovieCast(String movieId) {
        mTMDbService.getMovieCredits(TMDbService.API_KEY, movieId).enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovieCredit.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }
}
