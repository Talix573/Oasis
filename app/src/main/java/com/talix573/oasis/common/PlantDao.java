package com.talix573.oasis.common;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talix573.oasis.common.Plant;

import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plant")
    LiveData<List<Plant>> getAll();

    @Query("SELECT * FROM plant WHERE id IN (:plantIds)")
    LiveData<List<Plant>> getAllByIds(int[] plantIds);

    @Query("SELECT * FROM plant WHERE id LIKE :id LIMIT 1")
    LiveData<Plant> getPlantById(int id);

    @Insert
    void insertAll(Plant... plant);

    @Delete
    void delete(Plant plant);
}
