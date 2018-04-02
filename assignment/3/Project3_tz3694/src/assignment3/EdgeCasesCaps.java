package assignment3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
/**
 * This is the sample test cases for students
 * @author lisahua
 *
 */

public class EdgeCasesCaps {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    private boolean verifyLadder(ArrayList<String> ladder) {
        for(int i = 0; i  < ladder.size(); i ++)
            ladder.set(i, ladder.get(i).toLowerCase());
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
            if (s1.charAt(i) != s2.charAt(i) && ++diff > 1) {
                return false;
            }
        }

        return true;
    }
    //Different Word Length
    //Test 6
    @Test(timeout = 30000)
    public void testBFS6() {
        ArrayList<String> res = Main.getWordLadderBFS("HELLO", "IDIOMS");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);

    }

    @Test(timeout = 30000)
    public void testDFS6() {
        ArrayList<String> res = Main.getWordLadderDFS("HELLO", "IDIOMS");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }
    //Same length, not in dictionary
    //Test 7
    @Test(timeout = 30000)
    public void testBFS7() {
        ArrayList<String> res = Main.getWordLadderBFS("HELLOS", "IDIOMS");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);

    }

    @Test(timeout = 30000)
    public void testDFS7() {
        ArrayList<String> res = Main.getWordLadderDFS("HELLOS", "IDIOMS");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    //correct length, not in dictionary
    //Test 8
    @Test(timeout = 30000)
    public void testDFS8() {
        ArrayList<String> res = Main.getWordLadderDFS("VULPS", "IDIOM");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void testBFS8() {
        ArrayList<String> res = Main.getWordLadderBFS("TWIXK", "HACUS");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }
}
