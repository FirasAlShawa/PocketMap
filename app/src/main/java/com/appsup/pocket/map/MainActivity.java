package com.appsup.pocket.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.appsup.pocket.map.adapter.PlaceAdapter;
import com.appsup.pocket.map.roomdb.dao.PlacesDao;
import com.appsup.pocket.map.roomdb.db.PlacesRoomDB;
import com.appsup.pocket.map.roomdb.entity.Place;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Week1: " + MainActivity.class.getSimpleName();
    private PlacesRoomDB db;
    RecyclerView placesRC;
    PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = getDBInstance();

//        insertUser();
        getAll();

        placesRC = findViewById(R.id.places_rc);
        placeAdapter = new PlaceAdapter(getAll(),this);
        placesRC.setAdapter(placeAdapter);
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
        PlacesDao userDao = db.placeDao();
        userDao.insert(new Place(1,"test","1.1","1.1"));
        Log.d(TAG, "Insert Successfully");
    }

    /**
     * Get All data from User Table
     */
    public List<Place> getAll() {
        PlacesDao placeDao = db.placeDao();
        List<Place> places = placeDao.getAllPlaces();
        Log.d(TAG,"places list size -> "+ places.size());
        return places;
    }


}