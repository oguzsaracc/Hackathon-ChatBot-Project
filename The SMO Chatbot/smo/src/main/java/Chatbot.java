
import java.io.File;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import smo.SMOProcessorExtension;



public class Chatbot {

	private static final boolean TRACE_MODE = false;
	static String botName = "smoBot";
	

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			
			String resourcesPath = getResourcesPath();
			
			if(args != null && args.length > 0) {
				//Get first parameter as resources path
				resourcesPath = args[0];
			}
			System.out.println(resourcesPath);
			
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot(botName, resourcesPath);
			
			AIMLProcessor.extension = new SMOProcessorExtension();
			Chat chatSession = new Chat(bot);
			
			String textLine = "";
			System.out.println("Hi");
			
			while (true) {
				
				
				System.out.print("Human : ");
				textLine = IOUtils.readInputTextLine();
				
				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input;
				
				if (textLine.equals("q")) {
					
					System.exit(0);
				} else if (textLine.equals("wq")) {
					
					bot.writeQuit();
					System.exit(0);
				} else {
					
					String request = textLine;
					if (MagicBooleans.trace_mode) {
						
						System.out.println(
								"STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
										+ ":TOPIC=" + chatSession.predicates.get("topic"));
					}
					
					String response = chatSession.multisentenceRespond(request);					
					
					while (response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					
					while (response.contains("&gt;"))
						response = response.replace("&gt;", ">");
					
					System.out.println("Robot : " + response);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}
	
}
		
	
