package com.appsup.pocket.map.roomdb.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Places")
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String latitude ;

    @ColumnInfo
    private String longitude;

    public Place(@NonNull String title,@NonNull String latitude,@NonNull String longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place() {
        this.title = "test";
        this.latitude = "0.0";
        this.longitude = "0.0";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
