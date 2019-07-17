package com.techelevator.dao.jdbc;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.npgeek.dao.WeatherDAO;
import com.techelevator.npgeek.dao.jdbc.JDBCWeatherDAO;
import com.techelevator.npgeek.model.Forecast;
import com.techelevator.npgeek.model.Park;

public class JDBCWeatherDAOIntegrationTest extends DAOIntegrationTest {

	private WeatherDAO dao;
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		truncateTables();
		dao = new JDBCWeatherDAO(getDataSource());
	}

	@Test
	public void get_five_day_forecast() {
		// Add test park
		Park park1 = createPark("AAA", "aPark", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park1);
		// Add second test park
		Park park2 = createPark("BBB", "bPark", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park2);

		// Insert test forecasts to the DB for first park
		for (int i = 1; i < 6; i++) {
			insertForecast(park1.getCode(), i, 50, 72, "sunny");
		}
		// Insert test forecasts to the DB for second park
		for (int i = 1; i < 6; i++) {
			insertForecast(park2.getCode(), i, 50000, 2, "thunderstorms");
		}

		// Get five day forecast from DAO for first park
		List<Forecast> fiveDayForecast1 = dao.getFiveDayForecast(park1);
		// Ensure the same forecasts are returned for first park
		for (int i = 0; i < fiveDayForecast1.size(); i++) {
			Assert.assertEquals("Forecast day for day " + i + " in park one is incorrect", i + 1,
					fiveDayForecast1.get(i).getForecastDay());
			Assert.assertEquals("Park code for day " + i + " in park two is incorrect", park1.getCode(),
					fiveDayForecast1.get(i).getParkCode());
		}
		// Get five day forecast from DAO for second park
		List<Forecast> fiveDayForecast2 = dao.getFiveDayForecast(park2);
		// Ensure the same forecasts are returned for second park
		for (int i = 0; i < fiveDayForecast2.size(); i++) {
			Assert.assertEquals("Forecast day for day " + i + " in park two is incorrect", i + 1,
					fiveDayForecast2.get(i).getForecastDay());
			Assert.assertEquals("Park code for day " + i + " in park two is incorrect", park2.getCode(),
					fiveDayForecast2.get(i).getParkCode());
		}
	}

	private Park createPark(String code, String name, String state, int acreage, int elevationInFeet,
			BigDecimal milesOfTrail, int numberOfCampsites, String climate, int yearFounded, int annualVisitorCount,
			String inspirationalQuote, String inspirationalQuoteSource, String description, BigDecimal entryFee,
			int numberOfAnimalSpecies) {
		Park p = new Park();
		p.setCode(code);
		p.setName(name);
		p.setState(state);
		p.setAcreage(acreage);
		p.setElevationInFeet(elevationInFeet);
		p.setMilesOfTrail(milesOfTrail);
		p.setNumberOfCampsites(numberOfCampsites);
		p.setClimate(climate);
		p.setYearFounded(yearFounded);
		p.setAnnualVisitorCount(annualVisitorCount);
		p.setInspirationalQuote(inspirationalQuoteSource);
		p.setInspirationalQuoteSource(inspirationalQuoteSource);
		p.setDescription(description);
		p.setEntryFee(entryFee);
		p.setNumberOfAnimalSpecies(numberOfAnimalSpecies);
		return p;
	}

	private void addParkToDataBase(Park park) {
		String sql = "INSERT INTO park (parkcode, parkname, state, acreage, elevationinfeet, "
				+ "milesoftrail, numberofcampsites, climate, yearfounded, annualvisitorcount, "
				+ "inspirationalquote, inspirationalquotesource, parkdescription, entryfee, numberofanimalspecies) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, park.getCode(), park.getName(), park.getState(), park.getAcreage(),
				park.getElevationInFeet(), park.getMilesOfTrail(), park.getNumberOfCampsites(), park.getClimate(),
				park.getYearFounded(), park.getAnnualVisitorCount(), park.getInspirationalQuote(),
				park.getInspirationalQuoteSource(), park.getDescription(), park.getEntryFee(),
				park.getNumberOfAnimalSpecies());
	}

	private void insertForecast(String parkCode, int forecastDay, int low, int high, String forecast) {
		String sql = "INSERT INTO weather (parkcode, fivedayforecastvalue, low, high, forecast) "
				+ "VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, parkCode, forecastDay, low, high, forecast);
	}

	private void truncateTables() {
		String sql = "TRUNCATE weather CASCADE; TRUNCATE park CASCADE";
		jdbcTemplate.update(sql);
	}
}