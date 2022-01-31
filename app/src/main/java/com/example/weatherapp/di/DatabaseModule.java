package com.example.weatherapp.di;

import android.content.Context;

import androidx.room.Room;

import com.example.weatherapp.data.db.WeatherDatabase;
import com.example.weatherapp.data.db.dao.WeatherDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static WeatherDatabase provideWeatherDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                WeatherDatabase.class,
                "weather-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    @Provides
    public static WeatherDao provideWeatherDao(WeatherDatabase database) {
        return database.weatherDao();
    }
}
