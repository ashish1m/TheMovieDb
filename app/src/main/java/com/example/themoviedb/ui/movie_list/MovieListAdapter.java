package com.example.themoviedb.ui.movie_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.themoviedb.R;
import com.example.themoviedb.repository.db.entity.Movie;
import com.example.themoviedb.util.Utils;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Movie> mMovieList;
    private OnItemClickListener mListener;

    public MovieListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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

    interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View mMainLayout;
        private ImageView mPosterIv;
        private TextView mMovieTitleTv, mMovieRatingTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMainLayout = itemView.findViewById(R.id.main_layout);
            mPosterIv = itemView.findViewById(R.id.iv_movie);
            mMovieTitleTv = itemView.findViewById(R.id.tv_title);
            mMovieRatingTv = itemView.findViewById(R.id.tv_rating);

            mMainLayout.setOnClickListener(this);
        }

        void bind(Movie movie) {
            mMovieTitleTv.setText(movie.getTitle());
            mMovieRatingTv.setText(movie.getVote_average());
            mMainLayout.setTag(movie);

            Glide.with(itemView)
                    .load(Utils.getPosterUrl(movie.getPoster_path()))
                    .into(mPosterIv);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_layout:
                    if (mListener != null) {
                        mListener.onItemClick((Movie) mMainLayout.getTag());
                    }
                    break;
            }
        }
    }
}
