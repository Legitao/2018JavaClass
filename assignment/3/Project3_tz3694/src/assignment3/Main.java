/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Tao Zhu
 * tz3694
 * 15500
 * Slip days used: <0>
 * Git URL:https://github.com/Tenesiszzz/EE422.git
 * Spring 2018
 */


package assignment3;
import java.util.*;


import java.io.*;

public class Main {
	static ArrayList<String> command;

	public static void main(String[] args) throws Exception 
	{		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) 
		{
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} 
		else 
		{
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		command = parse(kb);
		if(command.isEmpty()) // quit
			return;
		//ArrayList<String> path = getWordLadderBFS(command.get(0), command.get(1));
		ArrayList<String> path = getWordLadderDFS(command.get(0), command.get(1));
		
		printLadder(path);
	
	}
	
	/**
	 * I don't use this function. Just remain it as empty
	 */
	public static void initialize() 
	{
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
	
	/**
	 * This method finds a word ladder from start to end in DFS order
	 * precondition: start and end are different and both in dictionary, at least differ by two letters.
	 * @param start This is the first word of ladder
	 * @param end This is the final word of ladder
	 * @return a word ladder from start(inclusive) to end(inclusive). 
	 * If there is no ladder, return a ladder just containing start and end.
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) 
	{
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Set<String> dict = makeDictionary();
		Map<String,  String> prev = new HashMap<String, String>();  //used to track each word's previous word
		Set<String> visited = new HashSet<String> ();  //used to mark words as visited
		for(String s : dict)
		{
			prev.put(s, null);
		}
		
		//invoke the recursive helper method.
		DFS(start, end, dict, prev, visited);
		
		//path tracking
		ArrayList<String> path = new ArrayList<String>();
		String tmp = end;
		while(!tmp.equals(start) && prev.get(tmp) != null)
		{
			path.add(tmp);
			tmp = prev.get(tmp);			
		}
		if(tmp.equals(start))  //Means there is a path.
		{
			path.add(start);		
		}
		Collections.reverse(path);
		
		//If there is no path, return ArrayList of just start and end
		if(path.isEmpty())
		{
			path.add(start);
			path.add(end);
		}
		return path; 
	}
	
	/**
	 * This is the recursive helper method to search a path in DFS order
	 * @param current This is current word being visited
	 * @param end This is the end word of the ladder
	 * @param dict This is the dictionary
	 * @param prev This is a map recording each word's previous word
	 * @param visited This is a set recording visited words
	 * @return If find a ladder between current and end, return True. If not, return false;
	 */
	public static boolean DFS(String current, String end, Set<String> dict, Map<String, String> prev, Set<String> visited)
	{
		visited.add(current);
		if(current.equals(end))
		{
			return true;
		}		
		
		//Find neighbors of current node
		ArrayList<String> adjacent_nodes = getAdjacentNodes(current, dict, visited);
		
		//Sort neighbors in terms of their distance toward the end word
		//distance is defined as number of different characters in each position
		Collections.sort(adjacent_nodes, new Comparator<String>() {
		    public int compare(String node1, String node2) {
		        return (distance(node1, end) - distance(node2, end));
		    }
		});
		
		//Search the neighbors of current node
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
	
	/**
	 * This method finds unvisited neighbors of current node
	 * @param current This is current word
	 * @param dict This is the dictionary
	 * @param visited This is the set recording visited words
	 * @return an ArrayList of neighbors of current node
	 */
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
	
	/**
	 * This method counts number of characters differ in two words
	 * @param a This is one word
	 * @param b This is the other word
	 * @return number of characters differ in two words
	 */
	private static int distance(String a, String b)
	{
		int d = 0;
		for(int i = 0; i < a.length(); i++)
		{
			if(a.charAt(i) != b.charAt(i))
			{
				d++;
			}
		}
		return d;
	}
	
	/**
	 * This method finds a word ladder from start to end in BFS order
	 * precondition: start and end are different and both in dictionary, at least differ by two letters.
	 * @param start This is the first word of ladder
	 * @param end This is the final word of ladder
	 * @return a word ladder from start(inclusive) to end(inclusive). 
	 * If there is no ladder, return a ladder just containing start and end.
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Set<String> dict = makeDictionary();
		Map<String, String> prev = new HashMap<String, String>();
		Set<String> visited = new HashSet<String> ();
		
		for(String s : dict)
		{
			prev.put(s, null);
		}
		Queue<String> q = new LinkedList<>();
		//visit start word, put it into queue
		visited.add(start);
		prev.replace(start, start);
		q.add(start);
		while(!q.isEmpty())
		{
			String current = q.poll();  //pop current word
			if(current.equals(end))
			{
				break;  //When finding end, stop searching
			}
			
			//get all unvisited neighbors of current nodes
			ArrayList<String> adjacent_nodes = getAdjacentNodes(current, dict, visited);
			//mark all neighbors as visited and put them into queue
			for(String next : adjacent_nodes) 
			{
				visited.add(next);  
				prev.replace(next, current);
				q.add(next);
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
		Collections.reverse(path);
		//If there is no path, return ArrayList of just start and end
		if(path.isEmpty())
		{
			path.add(start);
			path.add(end);
		}
		return path;
	}
    
	/**
	 * This method prints ladder
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder.size() == 2)  //only has start and end
		{
			System.out.printf("no word ladder can be found between %s and %s.\n", ladder.get(0).toLowerCase(), ladder.get(1).toLowerCase());
		}
		else
		{
			int rung_num = ladder.size() - 2;
			System.out.printf("a %d-rung word ladder exists between %s and %s.\n", rung_num, ladder.get(0).toLowerCase(), ladder.get(ladder.size() - 1).toLowerCase());
			for(String e : ladder)
			{
				System.out.println(e.toLowerCase());
			}
		}
	}

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

