package com.techelevator.npgeek.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.dao.WeatherDAO;
import com.techelevator.npgeek.model.Forecast;
import com.techelevator.npgeek.model.Park;

@Component
public class JDBCWeatherDAO implements WeatherDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCWeatherDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Forecast> getFiveDayForecast(Park park) {
		String sql = "SELECT parkcode, fivedayforecastvalue, low, high, forecast "
				+ "FROM weather WHERE parkcode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, park.getCode());
		
		List<Forecast> fiveDayForecast = new ArrayList<>();
		
		while (results.next()) {
			Forecast forecast = mapRowToForecast(results);
			fiveDayForecast.add(forecast);
		}
		
		return fiveDayForecast;
	}

	private Forecast mapRowToForecast(SqlRowSet results) {
		Forecast f = new Forecast();
		f.setParkCode(results.getString("parkcode"));
		f.setForecastDay(results.getInt("fivedayforecastvalue"));
		f.setLow(results.getInt("low"));
		f.setHigh(results.getInt("high"));
		f.setForecast(results.getString("forecast"));
		return f;
	}
}