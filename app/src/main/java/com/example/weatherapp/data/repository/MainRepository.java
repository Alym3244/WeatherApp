package com.example.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.db.dao.WeatherDao;
import com.example.weatherapp.data.models.MainWeather;
import com.example.weatherapp.data.remote.WeatherApi;
import com.example.weatherapp.ui.fragments.weather_fragment.WeatherFragment;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WeatherApi api;
    private WeatherDao weatherDao;
    private WeatherFragment weatherFragment = new WeatherFragment();

    @Inject
    public MainRepository(WeatherApi api, WeatherDao weatherDao) {
        this.api = api;
        this.weatherDao = weatherDao;
    }

    public MutableLiveData<Resource<MainWeather>> getWeather(String cityName) {
        MutableLiveData<Resource<MainWeather>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Resource.loading());
        api.getWeather(cityName, "34f284ef687268abb84bca32a3522cf7", "metric")
                .enqueue(new Callback<MainWeather>() {
                    @Override
                    public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            weatherDao.deleteAll();
                            mutableLiveData.setValue(Resource.success(response.body()));
                            weatherDao.insertAll(response.body());
                            weatherDao.update(response.body());
                        } else {
                            mutableLiveData.setValue(Resource.error(null, response.message()));
                            weatherDao.delete(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<MainWeather> call, Throwable t) {
                        mutableLiveData.setValue(Resource.error(null, t.getLocalizedMessage()));
                    }
                });
        return mutableLiveData;
    }

    public LiveData<MainWeather> getAll() {
        return weatherDao.getAll();
    }
}