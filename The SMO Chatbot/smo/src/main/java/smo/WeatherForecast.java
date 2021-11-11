package smo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherForecast {

	private static String API_KEY = "9e01734394d95a6a6b982c6da660fe53";


	public static Weather forecastWeather(String city, String countryCode, int days) {

		String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + city +"," + countryCode  + "&appid=" + API_KEY + "&units=metric";

		try {
			
			JSONObject data = getWeatherFromAPI(urlString);
			
			//Create midday of expected date to travel
			Calendar calendarDate = Calendar.getInstance();
			calendarDate.add(Calendar.DAY_OF_MONTH, days);
			calendarDate.set(Calendar.HOUR_OF_DAY,12);
			calendarDate.set(Calendar.MINUTE,0);
			calendarDate.set(Calendar.SECOND,0);
			
			Date calDate =  calendarDate.getTime();
			
			//Convert date to string with same format as in data from API
			String pattern = "yyyy-MM-dd HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			String expectedDate = df.format(calDate);
			
			// Work with JSON Object to get forecast from array of data provided by API	
			JSONArray arrayData = data.getJSONArray("list");
			for (int i = 0; i < arrayData.length(); i++) {
				
				JSONObject forecast = arrayData.getJSONObject(i);
				
				String sDate = forecast.getString("dt_txt");
				
				//Compare expected travel date with date from data
				if(expectedDate.contains(sDate)) {
					
					JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
					JSONObject main = forecast.getJSONObject("main");
					
					//Create Weather instance and populate with values
					Weather predicted = new Weather();
					
					predicted.date = sDate;
					predicted.description = weather.getString("description");
					
					String temp = main.getString("temp");
					predicted.temperature = Double.parseDouble(temp);
					
					
					return predicted;
					
				}

			}
						
			// If Data is not found then return first from the list 					
			JSONObject forecast = data.getJSONArray("list").getJSONObject(days);
			
			JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
			JSONObject main = forecast.getJSONObject("main");
			
			//Create Weather instance and populate with values
			Weather predicted = new Weather();
			
			predicted.date = forecast.getString("dt_txt");
			predicted.description = weather.getString("description");
			
			String temp = main.getString("temp");
			predicted.temperature = Double.parseDouble(temp);
			
			
			return predicted;

		} catch (Exception e) {
			System.out.println( e.getMessage());
			return null;
		} 
	}
	
	public static String currentTemperatureByCityName(String city) {

		String urlString = "http://api.openweathermap.org/data/2.5/find?q=" + city + "&appid=" + API_KEY
				+ "&units=metric";

		try {
			JSONObject data = getWeatherFromAPI(urlString);

			// Work with JSON Object to get temperature field data
			String temp = data.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp");

			return temp;

		}  catch (Exception e) {
			return e.getMessage();
		}
	}

	private static JSONObject getWeatherFromAPI(String urlString) throws MalformedURLException, IOException, JSONException {
		
		// Create new URl and open connection
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		// Create buffer reader and read from url connection input
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		// Create String Buffer
		StringBuffer json = new StringBuffer(1024);
		String tmp = "";

		// Read and store data from buffer reader to string buffer
		while ((tmp = reader.readLine()) != null) {

			json.append(tmp).append("\n");
		}

		// close buffer reader
		reader.close();

		// Convert to JSON Object
		JSONObject data = new JSONObject(json.toString());
		return data;
	}

}
