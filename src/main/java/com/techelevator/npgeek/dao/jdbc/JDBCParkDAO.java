package com.techelevator.npgeek.dao.jdbc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.dao.ParkDAO;
import com.techelevator.npgeek.model.Park;

@Component
public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParksAlphabetically() {
		String sql = "SELECT parkcode, parkname, parkdescription " + "FROM park " + "ORDER BY parkname";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

		List<Park> parks = new ArrayList<>();

		while (results.next()) {
			parks.add(mapRowToParkOverview(results));
		}

		return parks;
	}

	@Override
	public Park getParkByCode(String parkCode) {
		String sql = "SELECT parkcode, parkname, state, acreage, elevationinfeet, "
				+ "milesoftrail, numberofcampsites, climate, yearfounded, annualvisitorcount, "
				+ "inspirationalquote, inspirationalquotesource, parkdescription, entryfee, numberofanimalspecies "
				+ "FROM park " + "WHERE parkcode = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkCode);

		if (results.next()) {
			return mapRowToPark(results);
		}

		return null;
	}

	@Override
	public Map<Park, Integer> getParksSortedBySurveyCount() {
		Map<Park, Integer> sortedParks = new LinkedHashMap<>();

		String sql = "SELECT p.parkcode, p.parkname, p.parkdescription, count(sr.*) AS count " + "FROM park p "
				+ "JOIN survey_result sr on sr.parkcode = p.parkcode " + "GROUP BY p.parkcode "
				+ "ORDER BY count DESC, p.parkname ASC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			sortedParks.put(mapRowToParkOverview(results), results.getInt("count"));
		}

		return sortedParks;
	}

	private Park mapRowToParkOverview(SqlRowSet results) {
		Park p = new Park();
		p.setCode(results.getString("parkcode"));
		p.setName(results.getString("parkname"));
		p.setDescription(results.getString("parkdescription"));
		return p;
	}

	private Park mapRowToPark(SqlRowSet results) {
		Park p = new Park();
		p.setCode(results.getString("parkcode"));
		p.setName(results.getString("parkname"));
		p.setState(results.getString("state"));
		p.setAcreage(results.getInt("acreage"));
		p.setElevationInFeet(results.getInt("elevationinfeet"));
		p.setMilesOfTrail(results.getBigDecimal("milesoftrail"));
		p.setNumberOfCampsites(results.getInt("numberofcampsites"));
		p.setClimate(results.getString("climate"));
		p.setYearFounded(results.getInt("yearfounded"));
		p.setAnnualVisitorCount(results.getInt("annualvisitorcount"));
		p.setInspirationalQuote(results.getString("inspirationalquote"));
		p.setInspirationalQuoteSource(results.getString("inspirationalquotesource"));
		p.setDescription(results.getString("parkdescription"));
		p.setEntryFee(results.getBigDecimal("entryfee"));
		p.setNumberOfAnimalSpecies(results.getInt("numberofanimalspecies"));
		return p;
	}
}