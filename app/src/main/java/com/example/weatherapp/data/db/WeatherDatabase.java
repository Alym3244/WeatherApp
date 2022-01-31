package com.example.weatherapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weatherapp.data.db.dao.WeatherDao;
import com.example.weatherapp.data.models.MainWeather;

@TypeConverters(TypeConverter.class)
@Database(entities = {MainWeather.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
