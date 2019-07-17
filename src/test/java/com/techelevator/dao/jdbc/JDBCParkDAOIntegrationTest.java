package com.techelevator.dao.jdbc;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.npgeek.dao.ParkDAO;
import com.techelevator.npgeek.dao.jdbc.JDBCParkDAO;
import com.techelevator.npgeek.model.Park;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {

	private ParkDAO dao;
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		truncateTables();
		dao = new JDBCParkDAO(getDataSource());
	}

	// getAllParksAsMap()
	@Test
	public void get_all_parks_alphabetically_returns_all_parks() {
		// 0 parks in db returns 0 parks
		Assert.assertEquals("No parks in database should return an empty map", 0,
				dao.getAllParksAlphabetically().size());
		// Add test park
		Park park1 = createPark("AAA", "Park1", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park1);
		// 1 park in db returns 1 park
		Assert.assertEquals("1 park in database should return 1 park", 1, dao.getAllParksAlphabetically().size());
		// Add another test park
		Park park2 = createPark("BBB", "Park2", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park2);
		// 2 parks in db returns 2 parks
		Assert.assertEquals("2 park in database should return 2 park", 2, dao.getAllParksAlphabetically().size());
	}

	// getParkByCode()
	@Test
	public void get_park_by_code() {
		// Add test park
		Park park1 = createPark("AAA", "Park1", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park1);
		// Add another test park
		Park park2 = createPark("BBB", "Park2", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park2);

		// Using first park's code should return first park
		Assert.assertEquals("park1's code should return park1", park1.getCode(),
				dao.getParkByCode(park1.getCode()).getCode());
		// Using second park's code should return second park
		Assert.assertEquals("park2's code should return park2", park2.getCode(),
				dao.getParkByCode(park2.getCode()).getCode());
		// Using invalid code should return null
		Assert.assertEquals("invalid code returns null", null, dao.getParkByCode("ZZZ"));
	}

	// getParksBySurveyCount()
	@Test
	public void get_parks_by_survey_count() {
		// Add test park
		Park park1 = createPark("CCC", "ParkCCC", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park1);
		addNumOfDummySurveysToDbForPark(park1.getCode(), 1);
		// Add a second test park
		Park park2 = createPark("BBB", "ParkBBB", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park2);
		addNumOfDummySurveysToDbForPark(park2.getCode(), 2);
		// Add a third test park
		Park park3 = createPark("DDD", "ParkDDD", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park3);
		addNumOfDummySurveysToDbForPark(park3.getCode(), 20);
		// Add a fourth test park
		Park park4 = createPark("AAA", "ParkAAA", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park4);
		addNumOfDummySurveysToDbForPark(park4.getCode(), 2);
		// Setup array of correct sorting to compare to
		Park[] parks = {park3, park4, park2, park1};
		// Get map from DAO to test against our array
		Map<Park, Integer> sortedParks = dao.getParksSortedBySurveyCount();
		// Make sure all 4 parks come back
		Assert.assertEquals("All 4 parks should come back", 4, sortedParks.size());
		// Loop through map and check against array
		int count = 0;
		for (Park park : sortedParks.keySet()) {
			Assert.assertEquals("Park " + (count + 1) + " is incorrectly sorted", parks[count], park);
			count++;
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
	
	private void addNumOfDummySurveysToDbForPark(String parkCode, int num) {
		for (int i = 0; i < num; i++) {
			addSurveyResultToDbForPark(parkCode, "test" + i + "@gmail.com", "ohio", "sedentary");
		}
	}
	
	private void addSurveyResultToDbForPark(String parkCode, String emailaddress, String state, String activityLevel) {
		String sql = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel) "
				+ "VALUES (DEFAULT, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, parkCode, emailaddress, state, activityLevel);
	}

	private void truncateTables() {
		String sql = "TRUNCATE park CASCADE";
		jdbcTemplate.update(sql);
	}
}