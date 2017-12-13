package de.hhu.stups.propra.compute.services;

import com.jayway.jsonpath.JsonPath;
import de.hhu.stups.propra.compute.oldPJ.Credentials;
import de.hhu.stups.propra.compute.oldPJ.Thermometer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OpenWeatherMapForecast implements Thermometer {

	private static final double KELVIN = 273.15;
	private static final long CACHE_EXPIRE = 1000 * 60; // one minute caching
	private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/weather?id=2934246&appid=";

	private double temperature = 0;
	private long lastFetchTime = 0;
	private Credentials credentials;

	public OpenWeatherMapForecast(Credentials credentials) {
		this.credentials = credentials;

	}

	private String fetchWeatherJSON() throws IOException {
		URL url = new URL(FORECAST_URL + credentials.getWeatherApiKey());
		URLConnection weatherServiceConnection = url.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(weatherServiceConnection.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			a.append(inputLine);
		}
		in.close();
		return a.toString();
	}

	private double extractTemprature(String weatherJson) {
		return (Double) JsonPath.read(weatherJson, "$.main.temp");
	}

	@Override
	public int getCelsiusTemperature() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFetchTime > CACHE_EXPIRE) {
			try {
				String json = fetchWeatherJSON();
				double temp = extractTemprature(json);
				this.temperature = temp;
				this.lastFetchTime = currentTime;
			} catch (Exception e) {
				// use old value if something goes wrong
			}

		}
		return (int) (temperature - KELVIN);
	}

}
