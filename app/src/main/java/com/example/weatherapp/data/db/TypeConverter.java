package com.example.weatherapp.data.db;

import com.example.weatherapp.data.models.Clouds;
import com.example.weatherapp.data.models.Coord;
import com.example.weatherapp.data.models.Main;
import com.example.weatherapp.data.models.Sys;
import com.example.weatherapp.data.models.Weather;
import com.example.weatherapp.data.models.Wind;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConverter {

    @androidx.room.TypeConverter
    public static List<Weather> fromString(String value) {
        Type listType = new TypeToken<List<Weather>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @androidx.room.TypeConverter
    public static String fromList(List<Weather> list) {
        return new Gson().toJson(list);
    }

    @androidx.room.TypeConverter
    public static Coord stringToCoord(String value) {
        return new Gson().fromJson(value, Coord.class);
    }

    @androidx.room.TypeConverter
    public static String coordToString(Coord coord) {
        return new Gson().toJson(coord);
    }

    @androidx.room.TypeConverter
    public static Main stringToMain(String value) {
        return new Gson().fromJson(value, Main.class);
    }

    @androidx.room.TypeConverter
    public static String mainToString(Main main) {
        return new Gson().toJson(main);
    }

    @androidx.room.TypeConverter
    public static Wind stringToWind(String value) {
        return new Gson().fromJson(value, Wind.class);
    }

    @androidx.room.TypeConverter
    public static String windToString(Wind wind) {
        return new Gson().toJson(wind);
    }

    @androidx.room.TypeConverter
    public static Clouds stringToClouds(String value) {
        return new Gson().fromJson(value, Clouds.class);
    }

    @androidx.room.TypeConverter
    public static String cloudsToString(Clouds clouds) {
        return new Gson().toJson(clouds);
    }

    @androidx.room.TypeConverter
    public static Sys stringToSys(String value) {
        return new Gson().fromJson(value, Sys.class);
    }

    @androidx.room.TypeConverter
    public static String sysToString(Sys sys) {
        return new Gson().toJson(sys);
    }
}
