package com.appsup.pocket.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.appsup.pocket.map.roomdb.dao.PlacesDao;
import com.appsup.pocket.map.roomdb.db.PlacesRoomDB;
import com.appsup.pocket.map.roomdb.entity.Place;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Week1: " + MainActivity.class.getSimpleName();
    private PlacesRoomDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = getDBInstance();

        insertUser();
    }

    /**
     * Get Room Database instance
     */
    private PlacesRoomDB getDBInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                PlacesRoomDB.class, "my-room-db").allowMainThreadQueries().build();
    }

    /**
     * Insert one record to User Table
     */
    private void insertUser() {
        PlacesDao userDao = db.wordDao();
        userDao.insert(new Place(1,"test","1.1","1.1"));
        Log.d(TAG, "Insert Successfully");
    }

}