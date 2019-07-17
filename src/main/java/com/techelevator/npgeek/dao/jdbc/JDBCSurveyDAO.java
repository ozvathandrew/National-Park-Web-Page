package com.techelevator.npgeek.dao.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.dao.SurveyDAO;
import com.techelevator.npgeek.model.Survey;

@Component
public class JDBCSurveyDAO implements SurveyDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCSurveyDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Survey survey) {
		String sql = "INSERT INTO survey_result (surveyid, parkcode, emailaddress, state, activitylevel) "
				+ "VALUES (DEFAULT, ?, ?, ?, ?) " + "RETURNING surveyid";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, survey.getParkCode(), survey.getEmail(), survey.getState(),
				survey.getActivityLevel());

		if (results.next()) {
			survey.setId(results.getLong("surveyid"));
		}
	}
}
