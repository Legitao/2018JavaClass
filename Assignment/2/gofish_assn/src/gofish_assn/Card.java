package gofish_assn;

public class Card 
{
	
	public enum Suits {club, diamond, heart, spade};
	
	final static int TOP_RANK = 13; //King
	final static int LOW_RANK = 1; //Ace
	
	private int rank;  //This is the card's rank
	private Suits suit;  //This is the card's suit

	/**
	 * This method is default constructor to construct a 1-spade card. 
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}
	
	/**
	 * This method is parameterized constructor
	 * @param r This is the rank
	 * @param s This is a char indicating suit
	 */
	public Card(int r, char s) 
	{
		rank = r;
		suit = toSuit(s);
	}
	
	/**
	 * This method is parameterized constructor
	 * @param r This is the rank
	 * @param s This is the suit
	 */
	public Card(int r, Suits s) 
	{
		rank = r;
		suit = s;
	}
	

	/**
	 * This method convert a char to its corresponding Suits
	 * @param c This is the char of a suit's first character
	 * @return If c is a desired character, it returns c's corresponding Suits. If not, it returns null.
	 */
	private Suits toSuit(char c) {
		switch(c)
		{
			case 'c': return Suits.club;   //why need Suits.club, rather than club
			case 'd': return Suits.diamond; 
			case 'h': return Suits.heart;
			case 's': return Suits.spade;
			default: return null;
		}
	}
	
	/**
	 * This method convert a Suits to its first character
	 * @param s This is one of four Suits
	 * @return If s is a desired Suit, it returns s's first character. If not, it returns a space character.
	 */
	private String suitToString(Suits s)
	{
		switch(s)
		{
			case club: return "c"; 
			case diamond: return "d"; 
			case heart: return "h";
			case spade: return "s";
			default: return "";
		}
	}
	
	/**
	 * This method convert a rank to its string representation
	 * @param r This is the number representation of rank
	 * @return If r is in range 1 to 13, it returns the string representation. If not, it returns an empty string.
	 */
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
		
	/**
	 * This is getter of rank
	 * @return It returns the value of rank
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * This is getter of suit
	 * @return It returns the value of suit
	 */
	public Suits getSuit() {
		return suit;
	}
	
	/**
	 * This is an overriding of Object's toString method which returns the String representation of card.
	 */
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + suitToString(getSuit());
		
		return s;
	}
}
