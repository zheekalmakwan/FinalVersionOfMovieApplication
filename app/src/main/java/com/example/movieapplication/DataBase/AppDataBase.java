package com.example.movieapplication.DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntry.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String LOG_TAG = AppDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movieData";
    private static AppDataBase appInstance;

    public static AppDataBase getAppInstance(Context context) {
        if (appInstance == null) {
            synchronized (LOCK) {
                appInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, AppDataBase.DATABASE_NAME)
                        .allowMainThreadQueries().build();
            }
        }
        return appInstance;
    }

    //create an object of our Dao Interface
    public abstract MovieDao movieDao();


}