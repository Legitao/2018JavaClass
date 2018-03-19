package assignment3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import java.util.Random;

public class RandomTest {
	private static Set<String> dict;
	@Test
	public void test() {
		Main.initialize();
		dict = Main.makeDictionary();
		String start = null;
		String end = null;
		int size = dict.size();
		
		int t = 1000;
		while(t-- > 0)
		{
			int item1 = new Random().nextInt(size);
			int item2 = new Random().nextInt(size);
			int i = 0;
			for(String obj : dict)
			{
			    if (i == item1)
			        start = obj;
			    if(i == item2)
			    	end = obj;
			    i++;
			}
			
			System.out.println(start + " " + end);
			
			ArrayList<String> res = Main.getWordLadderBFS(start, end);
	
			if (res != null) {
				HashSet<String> set = new HashSet<String>(res);
				assertEquals(set.size(), res.size());
			}
			if(res.size() != 2)
				assertTrue(verifyLadder(res));
			else
				assertTrue(res.get(0).equals(start) && res.get(1).equals(end));
		}
	}
	
	private boolean verifyLadder(ArrayList<String> ladder) {
		String prev = null;
		if (ladder == null)
			return true;
		for (String word : ladder) {
			if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
				return false;
			}
			if (prev != null && !differByOne(prev, word))
				return false;
			prev = word;
		}
		return true;
	}

	private static boolean differByOne(String s1, String s2) {
		if (s1.length() != s2.length())
			return false;

		int diff = 0;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
                diff++;
			}
		}
        return diff==1;
	}

}
