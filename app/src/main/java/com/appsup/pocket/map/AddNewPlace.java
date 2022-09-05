package com.appsup.pocket.map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.appsup.pocket.map.roomdb.dao.PlacesDao;
import com.appsup.pocket.map.roomdb.db.PlacesRoomDB;
import com.appsup.pocket.map.roomdb.entity.Place;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;

import java.text.DecimalFormat;

public class AddNewPlace extends AppCompatActivity implements OnMapReadyCallback , HuaweiMap.OnMapClickListener {

    HuaweiMap hMap;
    private PlacesRoomDB db;
    private PlacesDao placesDao;
    private final static String TAG = "Add New Place" + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);

        initMap();
    }

    /**
     * Initiate DB
     */
    @Override
    protected void onResume() {
        super.onResume();
        initDB();
    }

    /**
     * Initiate the Map
     */
    public void initMap(){
        SupportMapFragment mSupportMapFragment;
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment_mapfragmentdemo);
        mSupportMapFragment.getMapAsync(this);
    }

    /**
     * Initiate the DB + PlacesDao variable
     */
    public void initDB(){
        db = getDBInstance();
        placesDao = db.placeDao();
    }

    /**
     * Get Room Database instance
     */
    private PlacesRoomDB getDBInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                PlacesRoomDB.class, "my-room-db").allowMainThreadQueries().build();
    }

    /**
     * Insert new place into Places Table
     */
    private void insertPlace(String Title,String Latitude,String Longitude) {
        placesDao.insert(new Place(Title,Latitude,Longitude));
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.setOnMapClickListener(this::onMapClick);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        AddNewPlaceDialog(latLng);
    }

    public void AddNewPlaceDialog(LatLng latLng){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.place_add_dialog, null);

        TextView latitudeTv = view.findViewById(R.id.latitudeTv);
        TextView longitudeTv = view.findViewById(R.id.longitudeTv);
        EditText placeEt = view.findViewById(R.id.placeEt);

        String lat = Double.parseDouble(new DecimalFormat("##.######").format(latLng.latitude))+ "";
        String lng = Double.parseDouble(new DecimalFormat("##.######").format(latLng.longitude))+ "";

        latitudeTv.setText(lat);
        longitudeTv.setText(lng);

        alert.setMessage("Enter Your Place Title");

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                insertPlace(placeEt.getText().toString(),lat,lng);
                finish();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.setView(view);
        alert.show();
    }

}