package com.example.weatherapp.ui.fragments.weather_fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.models.MainWeather;
import com.example.weatherapp.data.repository.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    private MainRepository repository;
    public LiveData<Resource<MainWeather>> liveData;
    public LiveData<MainWeather> localLiveData;

    @Inject
    public WeatherViewModel(MainRepository repository) {
        this.repository=repository;
    }

    public void getWeather(String cityName){
        liveData=repository.getWeather(cityName);
    }

    public void getAll() {
        localLiveData = repository.getAll();
    }
}
