package com.example.movieapplication.DataBase;


import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Insert
    void InsertMovie(MovieEntry movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    @Query("SELECT * FROM movie WHERE movieID=:ID")
    MovieEntry loadMovieByID(int ID);

}

