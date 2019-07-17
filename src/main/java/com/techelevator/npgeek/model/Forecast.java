package com.techelevator.npgeek.model;

public class Forecast {
	
	private String parkCode;
	private int forecastDay;
	private int low;
	private int high;
	private String forecast;

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public int getForecastDay() {
		return forecastDay;
	}

	public void setForecastDay(int forecastDay) {
		this.forecastDay = forecastDay;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public String getForecast() {
		return forecast;
	}
	
	public String getImageName() {
		String[] parts = forecast.split(" ");
		if (parts.length < 2) {
			return forecast;
		}
		for (int i = 1; i < parts.length; i++) {
			parts[i] = Character.toUpperCase(parts[i].charAt(0)) + parts[i].substring(1);
		}
		return String.join("", parts);
	}

	public void setForecast(String forecast) {
		this.forecast = forecast;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forecast == null) ? 0 : forecast.hashCode());
		result = prime * result + forecastDay;
		result = prime * result + high;
		result = prime * result + low;
		result = prime * result + ((parkCode == null) ? 0 : parkCode.hashCode());
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
		Forecast other = (Forecast) obj;
		if (forecast == null) {
			if (other.forecast != null)
				return false;
		} else if (!forecast.equals(other.forecast))
			return false;
		if (forecastDay != other.forecastDay)
			return false;
		if (high != other.high)
			return false;
		if (low != other.low)
			return false;
		if (parkCode == null) {
			if (other.parkCode != null)
				return false;
		} else if (!parkCode.equals(other.parkCode))
			return false;
		return true;
	}
}