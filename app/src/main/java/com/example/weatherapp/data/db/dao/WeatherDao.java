package com.example.weatherapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherapp.data.models.MainWeather;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM mainweather")
    LiveData<MainWeather> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MainWeather model);

    @Update
    void update(MainWeather model);

    @Delete
    void delete(MainWeather model);

    @Query("DELETE FROM mainweather")
    void deleteAll();
}
