package com.appsup.pocket.map.roomdb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appsup.pocket.map.roomdb.dao.PlacesDao;
import com.appsup.pocket.map.roomdb.entity.Place;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Place.class},version = 1, exportSchema = false)
public abstract class PlacesRoomDB extends RoomDatabase {

    public abstract PlacesDao wordDao();

    private static volatile PlacesRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlacesRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlacesRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlacesRoomDB.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
