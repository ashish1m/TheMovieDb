package com.example.themoviedb.repository.remote.api;

import com.example.themoviedb.repository.remote.model.configuration.ConfigurationResponse;
import com.example.themoviedb.repository.remote.model.movie_credit.Credit;
import com.example.themoviedb.repository.remote.model.movie_detail.MovieDetail;
import com.example.themoviedb.repository.remote.model.movie_list.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

    String API_KEY = "ce03d8fbcbbf38bf40ad0a2251ac2684";

    String BASE_API = "https://api.themoviedb.org/3/";

    String TOP_RATED_MOVIES = "movie/top_rated";
    String CONFIGURATION = "configuration";
    String MOVIE_DETAIL = "movie/{movie_id}";
    String MOVIE_CREDITS = "movie/{movie_id}/credits";

    @GET(TOP_RATED_MOVIES)
    Call<MovieListResponse> fetchTopRatedMovies(@Query("api_key") String apiKey);

    @GET(CONFIGURATION)
    Call<ConfigurationResponse> getConfiguration(@Query("api_key") String apiKey);

    @GET(MOVIE_DETAIL)
    Call<MovieDetail> getMovieDetail(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

    @GET(MOVIE_CREDITS)
    Call<Credit> getMovieCredits(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
}
