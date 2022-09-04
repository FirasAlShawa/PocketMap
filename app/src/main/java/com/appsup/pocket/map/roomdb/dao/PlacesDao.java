package com.appsup.pocket.map.roomdb.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.appsup.pocket.map.roomdb.entity.Place;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlacesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Place place);

    @Query("SELECT * FROM Places order by id desc")
    List<Place> getAllPlaces();

    @Query("SELECT * FROM Places where id = :id")
    Place getPlace(int id);
}
