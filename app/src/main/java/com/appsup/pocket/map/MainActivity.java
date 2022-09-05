package com.appsup.pocket.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.appsup.pocket.map.AddNewPlace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.appsup.pocket.map.adapter.PlaceAdapter;
import com.appsup.pocket.map.roomdb.dao.PlacesDao;
import com.appsup.pocket.map.roomdb.db.PlacesRoomDB;
import com.appsup.pocket.map.roomdb.entity.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "Main Activity" + MainActivity.class.getSimpleName();
    private PlacesRoomDB db;
    private PlacesDao placeDao;
    RecyclerView placesRC;
    FloatingActionButton fab_new_place;
    PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * Initiate DB + RecyclerView
     */
    @Override
    protected void onResume() {
        super.onResume();
        initDB();
        initRecyclerView();
    }

    /**
     * Initiate the Views
     */
    public void initViews(){
        placesRC = findViewById(R.id.places_rc);
        fab_new_place = findViewById(R.id.fab_new_place);

        fab_new_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddNewPlace.class));
            }
        });
    }

    /**
     * Initiate the DB + PlacesDao
     */
    public void initDB(){
        db = getDBInstance();
        placeDao = db.placeDao();
    }

    /**
     * 1- Read all the places from DB
     * 2- Init new places RecyclerView adapter
     * 3- set the adapter to the RecyclerView
     */
    public void initRecyclerView(){
        List<Place> places = getAll();
        placeAdapter = new PlaceAdapter(places,this);
        placesRC.setAdapter(placeAdapter);
    }

    /**
     * Get Room Database instance
     */
    private PlacesRoomDB getDBInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                PlacesRoomDB.class, "my-room-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Get All data from User Table
     */
    public List<Place> getAll() {
        List<Place> places = placeDao.getAllPlaces();
        return places;
    }

}