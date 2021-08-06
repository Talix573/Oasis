package com.talix573.oasis.common;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.talix573.oasis.common.Plant;

import java.util.Date;
import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plant")
    Plant[] getAll();

    @Query("SELECT * FROM plant WHERE id IN (:plantIds)")
    Plant[] getAllByIds(int[] plantIds);

    @Query("SELECT * FROM plant WHERE id LIKE :id LIMIT 1")
    Plant getPlantById(int id);

    @Query("UPDATE Plant SET last_watered = :newWateredDate WHERE id = :id")
    void updatePlantLastWatered(Date newWateredDate, int id);

    @Insert
    void insertAll(Plant... plant);

    @Insert
    void insertOne(Plant plant);

    @Delete
    void delete(Plant plant);
}
