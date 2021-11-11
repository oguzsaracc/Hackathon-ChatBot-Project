/*
 * The SMO - Chatbotv1.0 LAST UPDATED: 03.04.2020
 * We created this bot for understanding:
 *  1-How is the 'Chatbot' works?
 *  2-What are mainly used?
 *  3-Using JFrame
 *  4-Visualizing the code
 * 
 * Thanks to the Oguz for coding. (29.03.2020)
 * Thanks to Sergey and Mert for commenting. (03.04.2020)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Chatbot extends JFrame {
	/**
	 * JFRAME- CREATING A FRAME FOR THE CHATBOT
	 */
	private static final long serialVersionUID = -6396390679771966336L;
	static JTextArea txt = new JTextArea();
	static JTextField field = new JTextField();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame(); // Generating object for the JFrame.
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(560, 400); // Sizing the frame.
		frame.setLayout(null);
		frame.setTitle("SMO Chatbotv1.0"); // Setting the title.
		frame.add(field); // Adding Field area.
		frame.add(txt);	// Adding Text area.
		txt.setLocation(0, 0); // Setting the location for Text field.
		txt.setSize(560, 425); // Setting the size for 'Text field for answering chatbot'.
		field.setLocation(0, 300);
		field.setSize(560, 63);
		field.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String userMsg = field.getText();
				txt.append("You: " + userMsg + "\n");
				field.setText("");
				
				if(userMsg.toLowerCase().contains("hi")) { // Generating Messages from user and answers from Chatbot.
					txt.append("Bot: Hello Buddy!\n");
				}
				else if(userMsg.toLowerCase().contains("how are you")) {
					txt.append("Bot: I am very well and you?\n");
				}
				else if(userMsg.toLowerCase().contains("fine")) {
					txt.append("Bot: I am happy to hear that!\n");
				}
				else if(userMsg.toLowerCase().contains("joke")) {
					txt.append("Bot: I dreamed I was forced to eat a giant marshmallow. When I woke up, my pillow was gone.\n");
				}
				else if(userMsg.toLowerCase().contains("weather")) {
					txt.append("Bot: Weather is 32 Celcius right now. Have fun with it.\n");
				}
				else if(userMsg.toLowerCase().contains("you")) {
					txt.append("Bot: I am Chatbotv1.0 My creator is SMO! They are cool guys!\n");
				}
				else if(userMsg.toLowerCase().contains("wear")) {
					txt.append("Bot: It is a good weather for wearing t-shirt and short!\n");
				}
				else if(userMsg.toLowerCase().contains("swim")) {
					txt.append("Bot: Yeah! It is a lovely weather for swimming. Do not forget your sun cream. \n");
				}
				else if(userMsg.toLowerCase().contains("bye")) { //Added From Mert 01.04.2020
					txt.append("Bot: See you and take yourself!\n");
				}
				else if(userMsg.toLowerCase().contains("lol")) {
					txt.append("Bot: Hahahah xD\n");
				}
				else if(userMsg.toLowerCase().contains("paris")) {
					txt.append("Bot: Paris is always cold! do not forget your umbrella for today.\n");
				}
				else if(userMsg.toLowerCase().contains("dublin")) {
					txt.append("Bot: You are lucky, today is just a cloudy.\n");
				}
				

				
			}
			
		});
		
	}
}
