package smo;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Calendar;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import org.junit.Test;

//import net.aksingh.owmjapis.api.APIException;


public class ChatbotTests {
	//Testing to see if multisentenceRespond method works properly
	@Test
	public void multisentenceRespondTest() {
		
		MagicBooleans.trace_mode = false;
		
		Bot bot = new Bot("super", getResourcesPath());		
		Chat chatSession = new Chat(bot);
		
		String responce = chatSession.multisentenceRespond("Where are you from?");
		assertEquals(responce,"My birthplace is Portland, Maine.");
	}
	
	//Checking if our both is visible and its content can be seen
	@Test
	public void botIsNotNullTest() {
		
		String resourcesPath = getResourcesPath();
		
		Bot bot = new Bot("smoBot", resourcesPath);	
		assertNotNull(bot);
	}
	
	//Checking to see if <pattern> tag is working correctly
	@Test
	public void hiTest() {
		
		
		Chat chatSession =  getChatSession("smoBot");
		
		String responce = chatSession.multisentenceRespond("HI");
		
		assertTrue(responce.contains("Hi"));
	}
	
	//Checking integer is returned for the days of the week
	@Test
	public void calendarTest() {
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 

		assertTrue(day > 0 && day <= 7);
	}
	

	// Checks if Exception happens in currentWeatherByCityName method or in parseDouble method
	@Test
	public void temperatureTest() {
		Exception ex = null;
		 try {
			 
			 String temp = WeatherForecast.currentTemperatureByCityName("Dublin,IE");
			 
			 double temperature = Double.parseDouble(temp);
			 System.out.println(temperature);
		
		 } catch (Exception e) {
	            ex = e;
	        }	       
	        assertNull(ex);		
	}
	
	//Testing that the we are given a temperature
	@Test
	public void forecastTest() {
		Exception ex = null;
		 try {
			 
			 Weather temp = WeatherForecast.forecastWeather("London", "GB", 2);
			 			 
			 System.out.println(temp.temperature);
		
		 } catch (Exception e) {
	            ex = e;
	        }	       
	        assertNull(ex);		
	}
	
	
	// Method written here so can be seen for our tests
	private static Chat getChatSession(String botName) {
		
		String resourcesPath = getResourcesPath();
		
		Bot bot = new Bot("smoBot", resourcesPath);
		//bot.writeAIMLIFFiles();
		bot.brain.nodeStats();
		
		return new Chat(bot);
	}
	
	// Method written here so can be seen for our tests
	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

}
