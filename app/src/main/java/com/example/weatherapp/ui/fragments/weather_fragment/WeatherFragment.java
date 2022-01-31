package com.example.weatherapp.ui.fragments.weather_fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.models.MainWeather;
import com.example.weatherapp.databinding.FragmentWeatherBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherViewModel viewModel;
    private NavController controller;
    private WeatherFragmentArgs args;
    private String lol;

    public WeatherFragmentArgs getArgs() {
        return args;
    }

    public String getLol() {
        return lol;
    }

    public void setLol(String lol) {
        this.lol = lol;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        args = WeatherFragmentArgs.fromBundle(getArguments());
        String city = args.getCityName();

        if (checkConnection()) {
            viewModel.getWeather(city);
        } else {
            viewModel.getAll();
        }

        NavHostFragment hostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        controller = hostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (checkConnection()) {
            viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainWeather>>() {
                @Override
                public void onChanged(Resource<MainWeather> resource) {
                    switch (resource.status) {
                        case SUCCESS: {
                            String date = getTime(resource.data.getDt(), "EEE, dd MMM yyyy  |  HH:MM:SS", "GMT+6");

                            String cityName = resource.data.getName() + ", " + resource.data.getSys().getCountry();
                            setLol(cityName);
                            String iconUrl = "https://openweathermap.org/img/wn/" + resource.data.getWeather().get(0).getIcon()
                                    + "@2x.png";
                            String sunny = resource.data.getWeather().get(0).getMain();
                            String temp = new DecimalFormat("0").format(resource.data.getMain().getTemp());
                            String tempMax = new DecimalFormat("0").format(resource.data.getMain().getTempMax()) + "°C";
                            String tempMin = new DecimalFormat("0").format(resource.data.getMain().getTempMin()) + "°C";
                            String humidity = resource.data.getMain().getHumidity() + "%";
                            String pressure = resource.data.getMain().getPressure() + "mBar";
                            String wind = resource.data.getWind().getSpeed() + "m/c";
                            String sunrise = getTime(resource.data.getSys().getSunrise(), "HH:mm", "GMT+6");
                            String sunset = getTime(resource.data.getSys().getSunset(), "HH:mm", "GMT+6");
                            Integer d = resource.data.getSys().getSunset() - resource.data.getSys().getSunrise();
                            String daytime = getTime(d, "HH'h' MM'm'", "GMT");

                            binding.tvDate.setText(date);
                            binding.tvMumbai.setText(args.getCityName());
                            Glide.with(requireContext()).load(iconUrl).into(binding.ivSunny);
                            binding.tvSunny.setText(sunny);
                            binding.tvGradus.setText(temp);
                            binding.tvGradusUp.setText(tempMax);
                            binding.tvGradusDown.setText(tempMin);
                            binding.tvHumidityPercent.setText(humidity);
                            binding.tvMBar.setText(pressure);
                            binding.tvSpeed.setText(wind);
                            binding.tvTimeSunrise.setText(sunrise);
                            binding.tvTimeSunset.setText(sunset);
                            binding.tvTimeDaytime.setText(daytime);
                            break;
                        }
                        case ERROR: {
                            Toast.makeText(requireActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case LOADING: {
                            Toast.makeText(requireActivity(), "LOADING", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        } else {
            viewModel.localLiveData.observe(getViewLifecycleOwner(), new Observer<MainWeather>() {
                @Override
                public void onChanged(MainWeather data) {
                    String date = getTime(data.getDt(), "EEE, dd MMM yyyy  |  HH:MM:SS", "GMT+6");

                    String cityName = data.getName() + ", " + data.getSys().getCountry();
                    setLol(cityName);
                    String iconUrl = "https://openweathermap.org/img/wn/" + data.getWeather().get(0).getIcon()
                            + "@2x.png";
                    String sunny = data.getWeather().get(0).getMain();
                    String temp = new DecimalFormat("0").format(data.getMain().getTemp());
                    String tempMax = new DecimalFormat("0").format(data.getMain().getTempMax()) + "°C";
                    String tempMin = new DecimalFormat("0").format(data.getMain().getTempMin()) + "°C";
                    String humidity = data.getMain().getHumidity() + "%";
                    String pressure = data.getMain().getPressure() + "mBar";
                    String wind = data.getWind().getSpeed() + "m/c";
                    String sunrise = getTime(data.getSys().getSunrise(), "HH:mm", "GMT+6");
                    String sunset = getTime(data.getSys().getSunset(), "HH:mm", "GMT+6");
                    Integer d = data.getSys().getSunset() - data.getSys().getSunrise();
                    String daytime = getTime(d, "HH'h' MM'm'", "GMT");

                    binding.tvDate.setText(date);
                    binding.tvMumbai.setText(args.getCityName());
                    Glide.with(requireContext()).load(iconUrl).into(binding.ivSunny);
                    binding.tvSunny.setText(sunny);
                    binding.tvGradus.setText(temp);
                    binding.tvGradusUp.setText(tempMax);
                    binding.tvGradusDown.setText(tempMin);
                    binding.tvHumidityPercent.setText(humidity);
                    binding.tvMBar.setText(pressure);
                    binding.tvSpeed.setText(wind);
                    binding.tvTimeSunrise.setText(sunrise);
                    binding.tvTimeSunset.setText(sunset);
                    binding.tvTimeDaytime.setText(daytime);
                }
            });
        }
        binding.ivMumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                controller.navigate(R.id.cityFragment);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

    }

    private String getTime(Integer timeInt, String timeFormat, String gmt) {
        long time = timeInt * (long) 1000;
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        format.setTimeZone(TimeZone.getTimeZone(gmt));
        return format.format(date);
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED || connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED;
    }
}