package com.techelevator.npgeek.model;

import java.util.ArrayList;
import java.util.List;

public abstract class WeatherSuggestor {

	// Forecast messages
	private static final String SNOW_MSG = "It is going to snow, so please pack snowshoes";
	private static final String RAIN_MSG = "It is going to rain, so please pack rain gear and wear waterproof shoes";
	private static final String THUNDERSTORMS_MSG = "There will be thunderstorms, please seek shelter and avoid hiking on exposed ridges";
	private static final String SUN_MSG = "The sun will be intense today, please pack sunblock";
	// Temperature messages
	private static final String HOT_MSG = "Today will be very hot, please bring an extra gallon of water";
	private static final String VARIED_MSG = "The temperature will fluctuate wildly today, please bring breathable layers";
	private static final String COLD_MSG = "Today will be very cold, be careful not to be too exposed to frigid temperatures";

	public static List<String> getSuggestions(Park park, Forecast forecast) {
		List<String> suggestions = new ArrayList<>();
		// Add the suggestion based on today's forecast if applicable
		String forecastSuggestion = getForecastSuggestion(forecast);
		if (forecastSuggestion != null) {
			suggestions.add(forecastSuggestion);
		}
		// Add any temperature-related suggestions
		if (forecast.getHigh() > 75) {
			suggestions.add(HOT_MSG);
		}
		if (forecast.getLow() < 20) {
			suggestions.add(COLD_MSG);
		}
		if (forecast.getHigh() - forecast.getLow() > 20) {
			suggestions.add(VARIED_MSG);
		}
		return suggestions;
	}

	private static String getForecastSuggestion(Forecast forecast) {
		switch (forecast.getForecast()) {
		case ("snow"):
			return SNOW_MSG;
		case ("rain"):
			return RAIN_MSG;
		case ("thunderstorms"):
			return THUNDERSTORMS_MSG;
		case ("sunny"):
			return SUN_MSG;
		default:
			return null;
		}
	}
}