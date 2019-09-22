package com.example.movieapplication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movieapplication.DataBase.AppDataBase;
import com.example.movieapplication.DataBase.MovieEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<MovieEntry>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDataBase dataBase=AppDataBase.getAppInstance(this.getApplication());
        movies=dataBase.movieDao().loadAllMovies();

    }

    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }
}
