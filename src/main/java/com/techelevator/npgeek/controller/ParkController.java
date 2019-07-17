package com.techelevator.npgeek.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.npgeek.dao.ParkDAO;
import com.techelevator.npgeek.dao.SurveyDAO;
import com.techelevator.npgeek.dao.WeatherDAO;
import com.techelevator.npgeek.model.Forecast;
import com.techelevator.npgeek.model.Park;
import com.techelevator.npgeek.model.StateList;
import com.techelevator.npgeek.model.Survey;
import com.techelevator.npgeek.model.WeatherSuggestor;

@Controller
@SessionAttributes("tempFormat")
public class ParkController {

	@Autowired
	ParkDAO parkDao;
	@Autowired
	SurveyDAO surveyDao;
	@Autowired
	WeatherDAO weatherDao;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String getHomePage(ModelMap map) {
		map.addAttribute("parks", parkDao.getAllParksAlphabetically());
		return "index";
	}

	@RequestMapping(path = "/parkDetails", method = RequestMethod.GET)
	public String getParkDetails(@RequestParam String parkId, ModelMap map) {
		// Get chosen park from DB and add to the request object
		Park park = parkDao.getParkByCode(parkId);
		map.addAttribute("park", park);
		// Add the five day forecast to the request object
		List<Forecast> fiveDayForecast = weatherDao.getFiveDayForecast(park);
		map.addAttribute("forecast", fiveDayForecast);
		// Add weather suggestions to the request object for the first day
		List<String> weatherSuggestions = WeatherSuggestor.getSuggestions(park, fiveDayForecast.get(0));
		map.addAttribute("weatherSuggestions", weatherSuggestions);
		return "parkDetails";
	}

	@RequestMapping(path = "/parkDetails", method = RequestMethod.POST)
	public String changeTempFormat(@RequestParam String tempFormat, @RequestParam String currentParkId, ModelMap map) {
		map.put("tempFormat", tempFormat);
		return "redirect:/parkDetails?parkId=" + currentParkId + "#weather";
	}

	@RequestMapping(path = "/survey", method = RequestMethod.GET)
	public String getSurveyForm(ModelMap map) {
		map.addAttribute("parks", parkDao.getAllParksAlphabetically());
		if (!map.containsAttribute("survey")) {
			map.addAttribute("survey", new Survey());
		}
		map.addAttribute("states", StateList.getStateCodes());
		return "survey";
	}

	@RequestMapping(path = "/survey", method = RequestMethod.POST)
	public String submitSurvey(@Valid @ModelAttribute("survey") Survey survey, BindingResult result, ModelMap map) {
		if (result.hasErrors()) {
			map.addAttribute("states", StateList.getStateCodes());
			map.addAttribute("parks", parkDao.getAllParksAlphabetically());
			return "survey";
		}
		surveyDao.create(survey);
		return "redirect:/surveyResults";
	}

	@RequestMapping(path = "/surveyResults", method = RequestMethod.GET)
	public String getSurveyResults(ModelMap map) {
		// Grabs parks sorted by number of results in descending order,
		// ties are settled by alphabetical order.
		// Attaches that list to the request for display on surveyResults
		Map<Park, Integer> sortedParks = parkDao.getParksSortedBySurveyCount();
		map.addAttribute("sortedParks", sortedParks);
		return "surveyResults";
	}
}