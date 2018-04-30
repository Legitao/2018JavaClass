package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * This class read documents under a folder and computes duplicate n-grams between each document
 * pairs and output documents with high similarities.
 *
 */
public class Cheaters {
	public static Map<String, List<String>> map = new HashMap<>();
	static Map<String, Map<String, Integer>> common = new HashMap<>();

	public static void main(String[] args) throws FileNotFoundException {
		if(args.length != 3) {
			System.out.println("Wrong commandline arguments");
			return;
		}
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++) {
			common.put(listOfFiles[i].getName(), new HashMap<String, Integer>());
			for(int j = i + 1; j < listOfFiles.length; j++) {
				common.get(listOfFiles[i].getName()).put(listOfFiles[j].getName(), 0);
			}
		}
		//System.out.println(common.size());
		for (File file : listOfFiles) {
		    if(file.isFile()) {
		    	Scanner input = new Scanner(new BufferedReader(new FileReader(file)));
		    	//Scanner input = new Scanner(file, "UTF-8");
		    	//System.out.println(file.getName());
		    	List<String> words = new ArrayList<>();
		    	while(input.hasNext()) {
		    		String[] tmp = input.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase().split("\\s+");		    		
		    		words.addAll(Arrays.asList(tmp));
		    	}
		    	int n = Integer.valueOf(args[1]);
		    	for(int i = 0; i < words.size() - n + 1; i++) {
		    		String six_word = null;
		    		for(int j = 0; j < n; j++) {
		    			six_word += words.get(i + j);
		    		}
		    		
		    		//System.out.println(six_word);
		    		if(map.containsKey(six_word)) {
		    			map.get(six_word).add(file.getName());
		    		} else {
		    			map.put(six_word, new ArrayList<String>());
		    			map.get(six_word).add(file.getName());
		    		}
		    	}
		    	input.close();
		    }
		    
		}
		List<String> list;
		Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, List<String>> pair = it.next();
		    list = pair.getValue();
		    for(int i = 0; i < list.size(); i++) {
		    	String a = list.get(i);
		    	for(int j = i + 1; j < list.size(); j++) {
		    		String b = list.get(j);
		    		if(!a.equals(b)) {
		    			common.get(a).put(b, common.get(a).get(b) + 1);
		    		}
		    	}
		    }
		}
		
		List<Pair> result = new ArrayList<>();
		for(int i = 0; i < listOfFiles.length; i++) {
			for(int j = i + 1; j < listOfFiles.length; j++) {
				int num = common.get(listOfFiles[i].getName()).get(listOfFiles[j].getName());
				if(num > Integer.valueOf(args[2])) {
					result.add(new Pair(listOfFiles[i].getName(), listOfFiles[j].getName(), num));
					//System.out.print(listOfFiles[i].getName() + " " + listOfFiles[j].getName() + " ");
					//System.out.println(num);
				}				
			}
		}
		Collections.sort(result);
		Collections.reverse(result);
		for(Pair e : result) {
			System.out.print(e.a + " " + e.b + " " + e.num + "\n");
		}
	}
}

/**
 * 
 * This class is to represent a pair of similar documents. It has data fields of
 * two documents names and the number of duplicate n-grams.
 *
 */
class Pair implements Comparable<Pair>{
	String a;
	String b;
	int num;
	public Pair(String a, String b, int num) {
		this.a = a;
		this.b = b;
		this.num = num;
	}
	@Override
	public int compareTo(Pair o) {		
		return this.num - o.num;
	}
	
}
