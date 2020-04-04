package com.example.themoviedb.ui.movie_list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.repository.db.entity.Movie;

import java.util.List;
import java.util.Objects;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView mMovieListRv;
    private MovieListViewModel mMovieListViewModel;
    private MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        mMovieListAdapter = new MovieListAdapter(this);

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
}
