package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.npgeek.model.Forecast;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.WeatherSuggestor;

public class WeatherSuggestorTest {

	// Forecast messages (taken from WeatherSuggestor class)
	private static final String SNOW_MSG = "It is going to snow, so please pack snowshoes";
	private static final String RAIN_MSG = "It is going to rain, so please pack rain gear and wear waterproof shoes";
	private static final String THUNDERSTORMS_MSG = "There will be thunderstorms, please seek shelter and avoid hiking on exposed ridges";
	private static final String SUN_MSG = "The sun will be intense today, please pack sunblock";
	// Temperature messages (taken from WeatherSuggestor class)
	private static final String HOT_MSG = "Today will be very hot, please bring an extra gallon of water";
	private static final String VARIED_MSG = "The temperature will fluctuate wildly today, please bring breathable layers";
	private static final String COLD_MSG = "Today will be very cold, be careful not to be too exposed to frigid temperatures";

	private Forecast forecast;
	private Park park;

	@Before
	public void setup() {
		forecast = new Forecast();
		park = new Park();
		park.setCode("AAA");
	}

	@Test
	public void forecasts_return_appropriate_weather_messages() {
		// Setting temperatures so they won't display temperature warnings
		forecast.setHigh(50);
		forecast.setLow(50);

		// Snow message
		forecast.setForecast("snow");
		Assert.assertEquals("Snow should return snow msg", SNOW_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		// Rain message
		forecast.setForecast("rain");
		Assert.assertEquals("Rain should return rain msg", RAIN_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		// Thunderstorms message
		forecast.setForecast("thunderstorms");
		Assert.assertEquals("Thunderstorms should return thunderstorms msg", THUNDERSTORMS_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		// Sunny message
		forecast.setForecast("sunny");
		Assert.assertEquals("Sunny should return sunny msg", SUN_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		// Other weather status should return no weather warnings
		forecast.setForecast("partly cloudy");
		Assert.assertEquals("Partly cloudy should not return a warning", 0,
				WeatherSuggestor.getSuggestions(park, forecast).size());
	}

	@Test
	public void temperature_limits_return_appropriate_temperature_messages() {
		// Setting forecast so we won't receive a weather warning
		forecast.setForecast("partly cloudy");

		// Hot message
		forecast.setHigh(76);
		forecast.setLow(73);
		Assert.assertEquals("Temp above 75 should show hot msg", HOT_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		forecast.setHigh(75);
		Assert.assertEquals("Temp 75 should not show hot msg", 0,
				WeatherSuggestor.getSuggestions(park, forecast).size());
		// Cold message
		forecast.setLow(19);
		forecast.setHigh(21);
		Assert.assertEquals("Temp below 20 should show cold msg", COLD_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		forecast.setLow(20);
		Assert.assertEquals("Temp 20 should not show cold msg", 0,
				WeatherSuggestor.getSuggestions(park, forecast).size());
	}

	@Test
	public void temperature_difference_20_or_more_should_show_warning_message() {
		// Setting forecast so we won't receive a weather warning
		forecast.setForecast("partly cloudy");

		forecast.setLow(40);
		forecast.setHigh(61);
		Assert.assertEquals("Temperature difference over 20 should show varied msg", VARIED_MSG,
				WeatherSuggestor.getSuggestions(park, forecast).get(0));
		forecast.setHigh(60);
		Assert.assertEquals("Temperature difference of 20 should not show varied msg", 0,
				WeatherSuggestor.getSuggestions(park, forecast).size());
	}

	@Test
	public void multiple_messages_display_properly() {
		// One of each message type
		forecast.setForecast("sunny");
		forecast.setLow(19);
		forecast.setHigh(76);
		List<String> correctMessages = new ArrayList<>();
		correctMessages.add(SUN_MSG);
		correctMessages.add(HOT_MSG);
		correctMessages.add(COLD_MSG);
		correctMessages.add(VARIED_MSG);
		Assert.assertEquals("Sunny, hot, cold, and varied should display all those messages", correctMessages,
				WeatherSuggestor.getSuggestions(park, forecast));
		// Hot and varied
		forecast.setForecast("partly cloudy");
		forecast.setLow(50);
		forecast.setHigh(90);
		correctMessages = new ArrayList<>();
		correctMessages.add(HOT_MSG);
		correctMessages.add(VARIED_MSG);
		Assert.assertEquals("Hot and varied should display those messages", correctMessages,
				WeatherSuggestor.getSuggestions(park, forecast));
		// Rain and cold
		forecast.setForecast("rain");
		forecast.setLow(19);
		forecast.setHigh(30);
		correctMessages = new ArrayList<>();
		correctMessages.add(RAIN_MSG);
		correctMessages.add(COLD_MSG);
		Assert.assertEquals("Rain and cold should display those messages", correctMessages,
				WeatherSuggestor.getSuggestions(park, forecast));
	}

}
