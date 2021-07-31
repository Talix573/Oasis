package com.talix573.oasis.common;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.talix573.oasis.common.Plant;

import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plant")
    List<Plant> getAll();

    @Query("SELECT * FROM plant WHERE id IN (:plantIds)")
    List<Plant> getAllByIds(int[] plantIds);

    @Query("SELECT * FROM plant WHERE id LIKE :id LIMIT 1")
    Plant getPlantById(int id);

    @Insert
    void insertAll(Plant... plant);

    @Delete
    void delete(Plant plant);
}
