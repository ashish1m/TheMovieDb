package com.example.themoviedb.ui.movie_detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.themoviedb.R;
import com.example.themoviedb.repository.remote.model.movie_credit.Cast;
import com.example.themoviedb.repository.remote.model.movie_credit.Credit;
import com.example.themoviedb.repository.remote.model.movie_detail.MovieDetail;
import com.example.themoviedb.util.Utils;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "movie_id";
    private MovieDetailViewModel mMovieDetailViewModel;
    private Toolbar mToolbar;
    private TextView mMovieTitleTv, mMovieDurationTv, mMovieReleaseTv, mMovieLanguageTv,
            mMovieGenresTv, mMovieRatingPtTv, mMovieAboutTv, mMovieCastTv;
    private ImageView mMovieIv;
    private String mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mMovieId = getIntent().getStringExtra(MOVIE_ID);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mMovieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        initView();
    }

    private void initView() {
        TextView toolbarTitleTv = mToolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitleTv.setText("Movie Detail");

        mMovieIv = findViewById(R.id.iv_movie);
        mMovieTitleTv = findViewById(R.id.tv_movie_title);
        mMovieDurationTv = findViewById(R.id.tv_movie_duration_val);
        mMovieReleaseTv = findViewById(R.id.tv_movie_release_val);
        mMovieLanguageTv = findViewById(R.id.tv_movie_language_val);
        mMovieGenresTv = findViewById(R.id.tv_movie_genres_val);
        mMovieRatingPtTv = findViewById(R.id.tv_movie_rating_val);
        mMovieAboutTv = findViewById(R.id.tv_movie_about_val);
        mMovieCastTv = findViewById(R.id.tv_movie_cast_val);

        mMovieDetailViewModel.getMovieDetail(mMovieId).observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(MovieDetail movieDetail) {
                updateMovieDetail(movieDetail);
            }
        });

        mMovieDetailViewModel.getMovieCredit(mMovieId).observe(this, new Observer<Credit>() {
            @Override
            public void onChanged(Credit credit) {
                updateMovieCast(credit.getCast());
            }
        });
    }

    private void updateMovieDetail(MovieDetail movieDetail) {
        mMovieTitleTv.setText(movieDetail.getTitle());
        mMovieDurationTv.setText(Utils.getFormattedTime(movieDetail.getRuntime()));
        mMovieReleaseTv.setText(Utils.getFormattedDate(movieDetail.getRelease_date()));
        mMovieLanguageTv.setText(mMovieDetailViewModel.getLanguages(movieDetail.getSpoken_languages()));
        mMovieGenresTv.setText(mMovieDetailViewModel.getGenres(movieDetail.getGenres()));
        String movieRating = movieDetail.getVote_average() + " & " + movieDetail.getVote_count() + " votes";
        mMovieRatingPtTv.setText(movieRating);
        mMovieAboutTv.setText(movieDetail.getOverview());

        Glide.with(this)
                .load(Utils.getBackDropUrl(movieDetail.getBackdrop_path()))
                .into(mMovieIv);
    }

    private void updateMovieCast(List<Cast> castList) {
        mMovieCastTv.setText(mMovieDetailViewModel.getCast(castList));
    }
}
