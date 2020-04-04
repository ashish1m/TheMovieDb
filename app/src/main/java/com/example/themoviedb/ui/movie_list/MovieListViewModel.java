package com.example.themoviedb.ui.movie_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.db.entity.Movie;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    public LiveData<List<Movie>> getAllSources() {
        return TheMovieApp.getInstance().getRepository().getMovieList();
    }
}
