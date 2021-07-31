package com.talix573.oasis.common;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Plant {
    @PrimaryKey
    public int id;

    // Name of the plant
    @ColumnInfo(name = "name")
    public String name;

    // Date of when plant was last watered
    @ColumnInfo(name = "last_watered")
    public Date lastWatered;

    // The duration the plant does not require watering in milliseconds
    @ColumnInfo(name = "watering_rate")
    public Long wateringRate;

    // File path of images
    @ColumnInfo(name = "img_path")
    public String imagePath;
}
