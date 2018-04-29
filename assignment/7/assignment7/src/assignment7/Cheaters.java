package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Cheaters {
	public static Map<String, List<String>> map = new HashMap<>();
	static Map<String, Map<String, Integer>> common = new HashMap<String, Map<String, Integer>>();
	
	public static void main(String[] args) throws FileNotFoundException {
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++) {
			common.put(listOfFiles[i].getName(), new HashMap<String, Integer>());
			for(int j = i + 1; j < listOfFiles.length; j++) {
				common.get(listOfFiles[i].getName()).put(listOfFiles[j].getName(), 0);
			}
		}
		for (File file : listOfFiles) {
		    if(file.isFile()) {
		    	Scanner input = new Scanner(file, "UTF-8");
		    	//System.out.println(file.getName());
		    	List<String> words = new ArrayList<>();
		    	while(input.hasNext()) {
		    		String[] tmp = input.next().replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");		    		
		    		words.addAll(Arrays.asList(tmp));
		    	}
		    	for(int i = 0; i < words.size() - 5; i++) {
		    		String six_word = words.get(i) + " " + words.get(i + 1) + " "
		    				+ words.get(i + 2) + " " + words.get(i + 3) + " "
		    				+ words.get(i + 4) + " " + words.get(i + 5);
		    		//System.out.println(six_word);
		    		if(map.containsKey(six_word)) {
		    			map.get(six_word).add(file.getName());
		    		} else {
		    			map.put(six_word, new ArrayList<String>());
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
		for(int i = 0; i < listOfFiles.length; i++) {
			for(int j = i + 1; j < listOfFiles.length; j++) {
				int num = common.get(listOfFiles[i].getName()).get(listOfFiles[j].getName());
				if(num > 0) {
					System.out.print(listOfFiles[i].getName() + " " + listOfFiles[j].getName() + " ");
					System.out.println(num);
				}
				
				
				
			}
		}
		System.out.println(args[1]);
		System.out.println(args[2]);
	}

}
