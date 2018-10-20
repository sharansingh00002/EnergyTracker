package com.example.sharansingh.solarifyenergy;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyDao {

@Insert
public void add(DailyData dailyData);

@Update
public void update(DailyData dailyData);

@Query("SELECT * FROM dailydata")
    public List<DailyData> getAll();
}
