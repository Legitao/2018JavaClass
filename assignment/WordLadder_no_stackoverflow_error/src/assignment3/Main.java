/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2017
 */


package assignment3;
import java.util.*;


import java.io.*;

public class Main {
	static ArrayList<String> command;
	// static variables and constants only here.

	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		command = parse(kb);
		//ArrayList<String> path = getWordLadderBFS(command.get(0), command.get(1));
		ArrayList<String> path = getWordLadderDFS(command.get(0), command.get(1));
		
		printLadder(path);
	
	}
	
	public static void initialize() {
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> command = new ArrayList<String>();
		String str = keyboard.next();
		if(str.equals("/quit"))
			return command;
		command.add(str.toUpperCase());
		command.add(keyboard.next().toUpperCase());
		return command;
	}
	
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Set<String> dict = makeDictionary();
		Map<String,  String> prev = new HashMap<String, String>();
		Set<String> visited = new HashSet<String> ();
		for(String s : dict)
		{
			prev.put(s, null);
		}
		
		
		DFS(start, end, dict, prev, visited);
		
		ArrayList<String> path = new ArrayList<String>();
		String tmp = end;
		while(!tmp.equals(start) && prev.get(tmp) != null)
		{
			path.add(tmp);
			tmp = prev.get(tmp);			
		}
		if(tmp.equals(start))  //has a path
		{
			path.add(start);		
		}
		Collections.reverse(path);
		if(path.isEmpty())
		{
			path.add(start);
			path.add(end);
		}
		return path; // replace this line later with real return
	}
	
	
	public static boolean DFS(String current, String end, Set<String> dict, Map<String, String> prev, Set<String> visited)
	{
		visited.add(current);
		if(current.equals(end))
		{
			return true;
		}		
		
		ArrayList<String> adjacent_nodes = getAdjacentNodes(current, dict, visited);
		for(String next: adjacent_nodes)
		{
			if(!visited.contains(next))  //don't forget this again. Returning from one sub-DFS, some originally unvisited nodes might have been visited
			{
				prev.replace(next, current);
				if(DFS(next, end, dict, prev, visited))
					return true;
				
			}
		}
		return false;
		
	}
	
	private static ArrayList<String> getAdjacentNodes(String current, Set<String> dict, Set<String> visited)
	{
		ArrayList<String> adjacent_nodes = new ArrayList<>();
		char[] modified_word = current.toCharArray();
		for(int i = 0; i < modified_word.length; i++)
		{
			char letter = current.charAt(i);
			for(int j = 0; j < 26; j++)
			{
				modified_word[i] = (char)('A' + j);
				String next = String.valueOf(modified_word);
				if(!next.equals(current) && dict.contains(next) && !visited.contains(next))
				{
					adjacent_nodes.add(next);
				}
				
			}
			modified_word[i] = letter;
		}
		return adjacent_nodes;
	}
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Set<String> dict = makeDictionary();
		Map<String, String> prev = new HashMap<String, String>();
		Map<String, Integer> visited = new HashMap<String, Integer>();
	    //dict.forEach(t -> map.put(t, ""));
		for (String s : dict) {
		    prev.put(s, null);
		    visited.put(s, 0);  //0 auto convert to Integer?
		}
		
		Queue<String> q = new LinkedList<>();
		visited.replace(start, 1);
		prev.replace(start, start);
		q.add(start);
		while(!q.isEmpty())
		{
			String current = q.poll();
			if(current.equals(end))
			{
				break;
			}
			char[] modified_word = current.toCharArray();
			for(int i = 0; i < modified_word.length; i++)
			{
				char letter = modified_word[i];
				for(int j = 0; j < 26; j++)
				{
					modified_word[i] = (char)('A' + j);
					String next = String.valueOf(modified_word);
					if(!next.equals(current) && dict.contains(next) && visited.get(next) == 0)
					{
						visited.replace(next, 1);
						prev.replace(next, current);
						q.add(next);
					}
				}
				modified_word[i] = letter;
			}
		}
		ArrayList<String> path = new ArrayList<String>();
		String tmp = end;
		while(!tmp.equals(start) && prev.get(tmp) != null)
		{
			path.add(tmp);
			tmp = prev.get(tmp);			
		}
		if(tmp.equals(start))  //has a path
		{
			path.add(start);		
		}
		//else, no path, keep path empty

		Collections.reverse(path);
		//Intuitively, I can just return empty ArrayList if there is no ladder.
		//But in order to pass that ill-designed 
		//assertEquals("no word ladder can be found between twixt and hakus", str); 
		//I have to write this silly code to mark this.(continue to printLadder)
		if(path.isEmpty())
		{
			path.add(start);
			path.add(end);
		}
		return path;
	}
    
	
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder.size() == 2)
		{
			//System.out.printf("no word ladder can be found between %s and %s\n", command.get(0).toLowerCase(), command.get(1).toLowerCase());
			//This will be error in MianTest because that doesn't call main(), so there is command is empty.
			
			//But since "For this assignment, do not consider the case where the start and end words differ by just one letter."
			//I can tweak it with silly code in getWordLadder.
			System.out.printf("no word ladder can be found between %s and %s\n", ladder.get(0).toLowerCase(), ladder.get(1).toLowerCase());
		}
		else
		{
			for(String e : ladder)
			{
				System.out.println(e.toLowerCase());
			}
		}
	}

	// Other private static methods here


	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}

