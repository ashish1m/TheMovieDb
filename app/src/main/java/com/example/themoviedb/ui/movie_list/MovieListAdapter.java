package com.example.themoviedb.ui.movie_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.repository.db.entity.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Movie> mMovieList;

    public MovieListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPosterIv;
        private TextView mMovieTitleTv, mMovieRatingTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPosterIv = itemView.findViewById(R.id.iv_movie);
            mMovieTitleTv = itemView.findViewById(R.id.tv_title);
            mMovieRatingTv = itemView.findViewById(R.id.tv_rating);
        }

        void bind(Movie movie) {
            mMovieTitleTv.setText(movie.getTitle());
            mMovieRatingTv.setText(movie.getVote_average());
        }
    }
}
