package com.appsup.pocket.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.appsup.pocket.map.AddNewPlace;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appsup.pocket.map.adapter.PlaceAdapter;
import com.appsup.pocket.map.adapter.SwipeHelper;
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

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        placesRC = findViewById(R.id.places_rc);
        List<Place> places = getAll();
        placeAdapter = new PlaceAdapter(places, this);
        SwipeHelper swipeHelper = new SwipeHelper(getApplicationContext(), placesRC) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#EB1D36"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                deletePlace(places.get(pos));
                                places.remove(pos);
                                placeAdapter.notifyItemRemoved(pos);
                                placeAdapter.notifyItemRangeChanged(pos, places.size());
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "View",
                        0,
                        Color.parseColor("#A2B5BB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Nav",
                        0,
                        Color.parseColor("#CFD2CF"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Place place = places.get(pos);
                                Uri content_url = Uri.parse("petalmaps://navigation?daddr="+place.getLatitude()+","+place.getLongitude());
                                Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        }
                ));
            }
        };
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

    public void deletePlace(Place place){
        placeDao.deletePlace(place);
    }

}