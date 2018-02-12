package gofish_assn;

public class Card {
	
	public enum Suits {club, diamond, heart, spade};
	
	final static int TOP_RANK = 13; //King
	final static int LOW_RANK = 1; //Ace
	
	private int rank;  //1 is an Ace
	private Suits suit;

	/**
	 * This method is constructor
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}
	
	/**
	 * This method is constructor
	 * @param r This is the rank
	 * @param s This is a char indicating suit
	 */
	public Card(int r, char s) {
		rank = r;
		suit = toSuit(s);
	}
	
	/**
	 * This method is constructor
	 * @param r This is the rank
	 * @param s This is the suit
	 */
	public Card(int r, Suits s) {
		rank = r;
		suit = s;
	}
	
	private Suits toSuit(char c) {
		switch(c){
			case 'c': return Suits.club;   //why need Suits.club, rather than club
			case 'd': return Suits.diamond; 
			case 'h': return Suits.heart;
			case 's': return Suits.spade;
			default: return null;
		}
	}
	
	private String suitToString(Suits s)
	{
		switch(s){
			case club: return "c"; 
			case diamond: return "d"; 
			case heart: return "h";
			case spade: return "s";
			default: return "";
	}
	}
	
	public static String rankToString(int r)
	{
		switch(r) {
			case 1: return "A";
			case 2: return "2";
			case 3: return "3";
			case 4: return "4";
			case 5: return "5";
			case 6: return "6";
			case 7: return "7";
			case 8: return "8";
			case 9: return "9";
			case 10: return "10";
			case 11: return "J";
			case 12: return "Q";
			case 13: return "K";
			default: return "";
		}
	}
		
	
	public int getRank() {
		return rank;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + suitToString(getSuit());
		
		return s;
	}
}
