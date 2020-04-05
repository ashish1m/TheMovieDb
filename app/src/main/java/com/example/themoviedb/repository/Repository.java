package com.example.themoviedb.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.db.AppDatabase;
import com.example.themoviedb.repository.db.dao.MovieDao;
import com.example.themoviedb.repository.db.entity.Movie;
import com.example.themoviedb.repository.remote.api.ApiClient;
import com.example.themoviedb.repository.remote.api.TMDbService;
import com.example.themoviedb.repository.remote.model.configuration.Configuration;
import com.example.themoviedb.repository.remote.model.movie_credit.Credit;
import com.example.themoviedb.repository.remote.model.movie_detail.MovieDetail;
import com.example.themoviedb.repository.remote.model.movie_list.MovieListResponse;
import com.example.themoviedb.shared_prefs.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository sInstance;
    private final TMDbService mTMDbService;
    private final MovieDao mMovieDao;
    private LiveData<List<Movie>> mMovieList;
    private MutableLiveData<MovieDetail> mMovieDetail;
    private MutableLiveData<Credit> mMovieCredit;

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

    public void fetchConfiguration() {
        mTMDbService.getConfiguration(TMDbService.API_KEY).enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SharedPrefManager.getInstance().saveConfiguration(response.body());
                }
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {
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

    public MutableLiveData<MovieDetail> getMovieDetail(String movieId) {
        mTMDbService.getMovieDetail(movieId, TMDbService.API_KEY).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovieDetail.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return mMovieDetail;
    }

    public MutableLiveData<Credit> getMovieCast(String movieId) {
        mTMDbService.getMovieCredits(movieId, TMDbService.API_KEY).enqueue(new Callback<Credit>() {
            @Override
            public void onResponse(Call<Credit> call, Response<Credit> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovieCredit.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Credit> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return mMovieCredit;
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }
}
