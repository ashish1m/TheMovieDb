package com.example.themoviedb.repository.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.themoviedb.repository.db.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);

    @Query("DELETE FROM Movie")
    void deleteAll();

    @Query("SELECT * from Movie")
    LiveData<List<Movie>> getAllMovies();
}
