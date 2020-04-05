package com.example.themoviedb.ui.movie_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.TheMovieApp;
import com.example.themoviedb.repository.Repository;
import com.example.themoviedb.repository.remote.model.movie_credit.Cast;
import com.example.themoviedb.repository.remote.model.movie_credit.Credit;
import com.example.themoviedb.repository.remote.model.movie_detail.Genres;
import com.example.themoviedb.repository.remote.model.movie_detail.MovieDetail;
import com.example.themoviedb.repository.remote.model.movie_detail.Spoken_languages;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    private final Repository mRepository;

    public MovieDetailViewModel() {
        mRepository = TheMovieApp.getInstance().getRepository();
    }

    public LiveData<MovieDetail> getMovieDetail(String movieId) {
        return mRepository.getMovieDetail(movieId);
    }

    public LiveData<Credit> getMovieCredit(String movieId) {
        return mRepository.getMovieCast(movieId);
    }

    public String getCast(List<Cast> castList) {
        StringBuilder casts = new StringBuilder();
        for (Cast c: castList) {
            String cast = String.format("%s (%s)",c.getName(), c.getCharacter());
            casts.append(cast).append("\n");
        }

        return casts.toString();
    }

    public String getLanguages(List<Spoken_languages> spoken_languages) {
        StringBuilder languages = new StringBuilder();
        for (int i = 0; i < spoken_languages.size(); i++) {
            languages.append(spoken_languages.get(i).getName());
            if (i + 1 < spoken_languages.size()){
                languages.append(", ");
            }
        }
        return languages.toString();
    }

    public String getGenres(List<Genres> genresList) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genresList.size(); i++) {
            genres.append(genresList.get(i).getName());
            if (i + 1 < genresList.size()){
                genres.append(", ");
            }
        }
        return genres.toString();
    }
}
