package gofish_assn;

import java.io.*;

public class Main 
{
	/**
	 * This is the main method
	 * @param args Command line arguments
	 */
	public static PrintWriter output = null;
	public static void main(String args[]) throws IOException
	{
		
		try
		{
			output = new PrintWriter(new FileWriter("GoFish_results.txt"));
			String name1 = "Jay";
			String name2 = "Chou";
			GoFishGame game = new GoFishGame(name1, name2);
			game.twoPeoplePlay();
			output.close();
		}
		catch(IOException e)
		{
			System.err.println("Caught IOException: " + e.getMessage());
		}
	}

}
