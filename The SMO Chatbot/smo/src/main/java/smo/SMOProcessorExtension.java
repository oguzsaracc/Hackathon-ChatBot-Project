package smo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//import org.alicebot.ab.AIMLMap;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.w3c.dom.Node;

public class SMOProcessorExtension implements AIMLProcessorExtension {

	public Set<String> extensionTagSet() {

		Set<String> tagSet = new HashSet<String>();
		tagSet.add("func");

		return tagSet;
	}

	// Checking if all the relevant information is provided
	public String recursEval(Node node, ParseState ps) {

		try {

			String nodeName = node.getNodeName();

			if (nodeName.equals("func")) {

				// get Country set name "country" in aiml.file
				String country = ps.chatSession.predicates.get("country");
				if (country == "" || country == "unknown") {
					return "Which country are you going?";
				}

				// get City from set name "city" in aiml.file
				String city = ps.chatSession.predicates.get("city");
				if (city == "" || city == "unknown") {
					return "Which city are you going?";
				}

				// get day set name "days" in aiml.file
				String day = ps.chatSession.predicates.get("days");
				if (day == "") {
					return "Which day are you going on?";
				}

				// clear predicates for the next prediction
				ps.chatSession.predicates.clear();

				// get day as integer
				int intDay = Integer.parseInt(day);
				int dayDiff = getDayDifference(intDay);

				if (dayDiff > 5) {
					return "Sorry, Currently could not predict more then 5 days in advance";
				}

				String responce = getForecast(city, country, dayDiff);
				return responce;
				
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return "I have not answer for that";
	}

	// Give response based on information given by user
	private String getForecast(String city, String country, int days) {

		Weather weather = WeatherForecast.forecastWeather(city, country, days);

		String cityCap = city.substring(0, 1).toUpperCase();
		String cityCapitalized = cityCap + city.substring(1);
		double temperature = weather.temperature;

		String advice = getExtremeConditionAdvice(weather.description);
		if (advice.length() > 0) {
			return advice;
		}

		if (temperature <= 10.0) {

			advice =  cityCapitalized + " is a great city but unfortunetly it will be cold. The Temperature in " + cityCapitalized + " will be "
					+ temperature + " so you should bring a coat, jumper, hat, gloves and a scarf to stay warm.";
		} else if (temperature > 10.0 && temperature <= 15.0) {

			advice =  "In " + cityCapitalized + " the temperature is " + temperature
					+ " so my suggestion would be to bring a long sleeve shirt, light jacket and jeans or trousers which ever you prefer. ";
		} else if (temperature > 15.0 && temperature <= 20.0) {

			advice = "There will be nice weather in " + cityCapitalized + " the temperature will be " + temperature
					+ " I would bring shorts and a hoodie";
		} else if (temperature > 20.0 && temperature <= 23.0) {
			advice = "There will be nice weather in " + cityCapitalized + " the temperature will be " + temperature
					+ " so it's warm enough for t-shirt and shorts also bring a hoodie as temperatures may drop towards the evening";
		} else if (temperature > 23.0) {
			advice = "It will be really warm with a temperature of " + temperature
					+ " you should wear light clothes like t-shirts, shorts and sandals dont forget your sunglasses and suncream";
		} else {
			advice = "Temperature on this day " + temperature;
		}
		
		return advice + "\n Which country would like to go next";

	}

	// Checking if day given is more than 5 days from todays day
	private int getDayDifference(int day) {

		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

		if (day >= currentDay) {

			// return days difference
			return day - currentDay;
		} else {

			// 7 days per week find day in the next week
			return (7 - currentDay) + day;
		}

	}

	private String getExtremeConditionAdvice(String weatherDescription) {

		String advice = getSnowAdvice(weatherDescription);
		if (advice.length() > 0) {
			return advice;
		}

		advice = getRainAdvice(weatherDescription);
		if (advice.length() > 0) {
			return advice;
		}

		advice = getThunderstormAdvice(weatherDescription);

		return advice;
	}

	private String getSnowAdvice(String weatherDescription) {
		String advice = "";
		if (weatherDescription == "snow") {
			advice = "Expected snow on that day, bring your winter jacket, gloves and hat";
		}
		return advice;
	}

	private String getRainAdvice(String weatherDescription) {

		String advice = "";
		if (weatherDescription == "rain" || weatherDescription == "shower rain") {
			advice = "It will be rainy so bring a rain jacket and an umbrella";
		}
		return advice;
	}

	private String getThunderstormAdvice(String weatherDescription) {
		String advice = "";
		if (weatherDescription == "thunderstorm") {
			advice = "Expectcted thunderstorm, advice to stay at inside on that day";
		}
		return advice;
	}

}
