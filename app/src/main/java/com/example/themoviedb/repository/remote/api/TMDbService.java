package com.example.themoviedb.repository.remote.api;

import com.example.themoviedb.repository.remote.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbService {

    String BASE_API = "https://api.themoviedb.org/3/";
    String TOP_RATED_MOVIES = "movie/top_rated";
    String API_KEY = "ce03d8fbcbbf38bf40ad0a2251ac2684";

    @GET(TOP_RATED_MOVIES)
    Call<MovieResponse> fetchTopRatedMovies(@Query("api_key") String apiKey);
}
