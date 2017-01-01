package com.TourGuide.service;

import org.springframework.stereotype.Service;

import com.TourGuide.common.getWeatherByCity;
import com.TourGuide.model.Weather;

@Service
public class WeatherService {
	
	private getWeatherByCity getWeatherByCity;
	
	public Weather getWeather(String city){
		return getWeatherByCity.getCityWeather(city);
	}

}
