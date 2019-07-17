package com.techelevator.npgeek.dao;

import java.util.List;
import java.util.Map;

import com.techelevator.npgeek.model.Park;

public interface ParkDAO {

	public List<Park> getAllParksAlphabetically();
	
	public Map<Park, Integer> getParksSortedBySurveyCount();

	Park getParkByCode(String parkCode);
}
