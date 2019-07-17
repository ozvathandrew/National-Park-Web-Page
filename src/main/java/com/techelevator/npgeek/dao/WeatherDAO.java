package com.techelevator.npgeek.dao;

import java.util.List;

import com.techelevator.npgeek.model.Forecast;
import com.techelevator.npgeek.model.Park;

public interface WeatherDAO {
	public List<Forecast> getFiveDayForecast(Park park);
}
