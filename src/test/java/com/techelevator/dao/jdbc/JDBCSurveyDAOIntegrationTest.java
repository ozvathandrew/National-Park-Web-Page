package com.techelevator.dao.jdbc;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.DAOIntegrationTest;
import com.techelevator.npgeek.dao.SurveyDAO;
import com.techelevator.npgeek.dao.jdbc.JDBCSurveyDAO;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.Survey;

public class JDBCSurveyDAOIntegrationTest extends DAOIntegrationTest {

	private SurveyDAO dao;
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		truncateTables();
		dao = new JDBCSurveyDAO(getDataSource());
	}

	@Test
	public void create() {
		// Add test park
		Park park1 = createPark("CCC", "ParkCCC", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park1);
		// Add a second test park
		Park park2 = createPark("BBB", "ParkBBB", "Ohio", 123456, 1234, new BigDecimal("32.2"), 32, "Temperate", 1995,
				10000, "Stop. Hammer Time.", "M.C. Hammer", "Warm and Sunny", new BigDecimal("3.50"), 52);
		addParkToDataBase(park2);

		// Add test survey
		Survey survey1 = createSurvey(park1.getCode(), "test1@test.com", "ohio", "sedentary");
		dao.create(survey1);
		// Add second test survey
		Survey survey2 = createSurvey(park2.getCode(), "test1@test.com", "ohio", "sedentary");
		dao.create(survey2);
		// Add third test survey
		Survey survey3 = createSurvey(park1.getCode(), "test1@test.com", "ohio", "sedentary");
		dao.create(survey3);

		// survey1 is correctly inserted to the db
		String sql = "SELECT * FROM survey_result " + "WHERE surveyid = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, survey1.getId());
		results.next();
		Assert.assertEquals("Survey1 is correctly inserted into db", survey1.getId(), results.getLong("surveyid"));
		// survey2 is correctly inserted to the db
		sql = "SELECT * FROM survey_result " + "WHERE surveyid = ?";
		results = jdbcTemplate.queryForRowSet(sql, survey2.getId());
		results.next();
		Assert.assertEquals("Survey2 is correctly inserted into db", survey2.getId(), results.getLong("surveyid"));
		// survey3 is correctly inserted to the db
		sql = "SELECT * FROM survey_result " + "WHERE surveyid = ?";
		results = jdbcTemplate.queryForRowSet(sql, survey3.getId());
		results.next();
		Assert.assertEquals("Survey3 is correctly inserted into db", survey3.getId(), results.getLong("surveyid"));

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

	private Survey createSurvey(String parkCode, String emailAddress, String state, String activityLevel) {
		Survey s = new Survey();
		s.setParkCode(parkCode);
		s.setEmail(emailAddress);
		s.setState(state);
		s.setActivityLevel(activityLevel);
		return s;
	}

	private void truncateTables() {
		String sql = "TRUNCATE park CASCADE";
		jdbcTemplate.update(sql);
	}
}
