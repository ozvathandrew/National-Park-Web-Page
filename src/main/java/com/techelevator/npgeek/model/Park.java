package com.techelevator.npgeek.model;

import java.math.BigDecimal;

public class Park {

	private String code;
	private String name;
	private String state;
	private int acreage;
	private int elevationInFeet;
	private BigDecimal milesOfTrail;
	private int numberOfCampsites;
	private String climate;
	private int yearFounded;
	private int annualVisitorCount;
	private String inspirationalQuote;
	private String inspirationalQuoteSource;
	private String description;
	private BigDecimal entryFee;
	private int numberOfAnimalSpecies;

	public Park() {

	}
	
	public String getCodeAsLowerCase() {
		return code.toLowerCase();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getAcreage() {
		return acreage;
	}

	public void setAcreage(int acreage) {
		this.acreage = acreage;
	}

	public int getElevationInFeet() {
		return elevationInFeet;
	}

	public void setElevationInFeet(int elevationInFeet) {
		this.elevationInFeet = elevationInFeet;
	}

	public BigDecimal getMilesOfTrail() {
		return milesOfTrail;
	}

	public void setMilesOfTrail(BigDecimal milesOfTrail) {
		this.milesOfTrail = milesOfTrail;
	}

	public int getNumberOfCampsites() {
		return numberOfCampsites;
	}

	public void setNumberOfCampsites(int numberOfCampsites) {
		this.numberOfCampsites = numberOfCampsites;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public int getYearFounded() {
		return yearFounded;
	}

	public void setYearFounded(int yearFounded) {
		this.yearFounded = yearFounded;
	}

	public int getAnnualVisitorCount() {
		return annualVisitorCount;
	}

	public void setAnnualVisitorCount(int annualVisitorCount) {
		this.annualVisitorCount = annualVisitorCount;
	}

	public String getInspirationalQuote() {
		return inspirationalQuote;
	}

	public void setInspirationalQuote(String inspirationalQuote) {
		this.inspirationalQuote = inspirationalQuote;
	}

	public String getInspirationalQuoteSource() {
		return inspirationalQuoteSource;
	}

	public void setInspirationalQuoteSource(String inspirationalQuoteSource) {
		this.inspirationalQuoteSource = inspirationalQuoteSource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEntryFee() {
		return entryFee;
	}

	public void setEntryFee(BigDecimal entryFee) {
		this.entryFee = entryFee;
	}

	public int getNumberOfAnimalSpecies() {
		return numberOfAnimalSpecies;
	}

	public void setNumberOfAnimalSpecies(int numberOfAnimalSpecies) {
		this.numberOfAnimalSpecies = numberOfAnimalSpecies;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Park other = (Park) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Park [code=" + code + ", name=" + name + "]";
	}
}