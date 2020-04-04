package com.example.themoviedb.repository.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MediatorLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themoviedb.repository.db.dao.MovieDao;
import com.example.themoviedb.repository.db.entity.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "app_database.db";
    private static volatile AppDatabase INSTANCE;
    private final MediatorLiveData<Boolean> mIsDatabaseCreated = new MediatorLiveData<>();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext());
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context applicationContext) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME)
                .build();
    }


    public abstract MovieDao movieDao();

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public MediatorLiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}