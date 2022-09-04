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
    public abstract PlacesDao placeDao();

}
