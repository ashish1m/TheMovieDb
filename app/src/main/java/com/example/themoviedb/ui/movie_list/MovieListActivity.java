package com.example.themoviedb.ui.movie_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.repository.db.entity.Movie;
import com.example.themoviedb.ui.movie_detail.MovieDetailActivity;

import java.util.List;
import java.util.Objects;

public class MovieListActivity extends AppCompatActivity implements MovieListAdapter.OnItemClickListener {

    private RecyclerView mMovieListRv;
    private MovieListViewModel mMovieListViewModel;
    private MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        mMovieListAdapter = new MovieListAdapter(this);
        mMovieListAdapter.addOnItemClickListener(this);

        initView();
    }

    private void initView() {
        mMovieListRv = findViewById(R.id.rv_movie_list);
        mMovieListRv.setHasFixedSize(true);
        mMovieListRv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        mMovieListRv.addItemDecoration(itemDecoration);
        mMovieListRv.setAdapter(mMovieListAdapter);

        mMovieListViewModel.getAllSources().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mMovieListAdapter.updateList(movies);
            }
        });
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.getId());
        startActivity(intent);
    }
}
