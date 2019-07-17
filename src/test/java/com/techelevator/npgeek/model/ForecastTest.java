package com.techelevator.npgeek.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ForecastTest {

	private Forecast forecast;

	@Before
	public void setup() {
		forecast = new Forecast();
	}

	@Test
	public void getImageName() {
		forecast.setForecast("partly cloudy");
		Assert.assertEquals("partly cloudy -> partlyCloudy", "partlyCloudy", forecast.getImageName());
		forecast.setForecast("rain");
		Assert.assertEquals("rain -> rain", "rain", forecast.getImageName());
		forecast.setForecast("really long weather name to pascal case");
		Assert.assertEquals("really long weather name to pascal case -> reallyLongWeatherNameToPascalCase", "reallyLongWeatherNameToPascalCase", forecast.getImageName());
	}

}
