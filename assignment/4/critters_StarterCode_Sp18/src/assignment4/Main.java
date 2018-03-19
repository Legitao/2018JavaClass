package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        System.out.print("critters> ");
        String command = kb.nextLine();
        while(!command.equals("quit")) {
        	String [] command_arr = command.split("\\s+");  //TODO: regex, tab and space
        	switch(command_arr[0]) {
        		case "show":
        			Critter.displayWorld();
        			break;
        		case "step":
        			int count_step = 1;
        			if(command_arr.length > 1) {
        				count_step = Integer.parseInt(command_arr[1]);
        			}
    				while((count_step--) > 0) {
    					Critter.worldTimeStep();
    				}
        			break;
        		case "seed":
        			long num = Integer.parseInt(command_arr[1]);
        			Critter.setSeed(num);
        			break;
        		case "make":
        			int count_make = 1;
        			if(command_arr.length > 2) {
        				count_make = Integer.parseInt(command_arr[2]);
        			}
        			while((count_make--) > 0) {
        				try {
							Critter.makeCritter(command_arr[1]);
						} catch (InvalidCritterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        			break;
        		case "stats":
        			//TODO
        			break;
        		default: break;
        	}
        	System.out.print("critters> ");
        	command = kb.nextLine();
        }
        // System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
}
