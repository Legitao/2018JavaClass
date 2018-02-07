package gofish_assn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
//import java.util.Comparator;
public class Player {
	
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> book = new ArrayList<Card>();
	String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public void addCardToHand(Card c) {
		hand.add(c);
	}
	
	public Card removeCardFromHand(Card c) {  //romove the card with same [[rank]] as c
		Card retCard = new Card();
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).getRank() == c.getRank())
			{
				hand.remove(i);
				break;
			}
		}
		//hand.remove(hand.indexOf(c));
		return retCard;  //return what?
	}
	
	public String getName() {
		return name;
	}
	
	public String handToString() {
		String s = new String();
		for(int i = 0; i < hand.size(); i++)
		{
			s += hand.get(i).toString() + " ";
		}
		return s;
	}
	
	public String bookToString() {
		String s = new String();
		for(int i = 0; i < book.size(); i++)
		{
			s += book.get(i).toString() + " ";
		}
		return s;
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	
	public int getBookSize() {
		return book.size();
	}
	
	
    //this function will check a players hand for a pair. 
    //If a pair is found, it moves the cards to the book and returns true

    public boolean checkHandForBook() 
    {
    	if(hand.size() >= 3)
    	{
    		hand.sort(new Comparator<Card>(){	
    			public int compare(Card a, Card b)
    			{
    				return Integer.compare(a.getRank(), b.getRank());
    			}
    		}		
    		);
    	}
    	boolean find_pair = false;
    	for(int i = 0; i < hand.size() - 1; i++)
    	{
    		if(hand.get(i).getRank() == hand.get(i + 1).getRank())
    		{
    			book.add(hand.remove(i)); //risk: not sure if right or not
    			book.add(hand.remove(i));
    			i--;
    			find_pair = true;
    		}
    	}
    	return find_pair;
    }
  
    //Does the player have a card with the same rank as c in her hand?
    public boolean rankInHand(Card c) {
    	for(Card tmp : hand)
    	{
    		if(tmp.getRank() == c.getRank())
    		{
    			return true;
    		}
    	}
    	return false; 
    }
    
    //uses some strategy to choose one card from the player's
    //hand so they can say "Do you have a 4?"
    public Card chooseCardFromHand() 
    {
    	if(hand.isEmpty())
    	{
    		return null;
    	}
    	Card c = new Card();
    	Random rand_generator = new Random();
    	int rand_index = rand_generator.nextInt(hand.size());
    	c = hand.get(rand_index);
    	return c;
    }
    
    public void drawFromDeck(Deck game_deck)
    {
    	Card c = game_deck.dealCard();
		if(c == null)
		{
			System.out.println("Deck is empty");
			
		}
		addCardToHand(c);  
		System.out.println(getName() + " draws " + Card.rankToString(c.getRank()) +" from deck");
		checkHandForBook();
		
    }
    
	public boolean askOpponent(Player opponent, Deck game_deck, Card request_card)
	{
		int request_number = request_card.getRank();
		System.out.println(getName() + ": Do you have " + Card.rankToString(request_number));
		if(opponent.rankInHand(request_card))
		{
			System.out.println(getName() + " draws " + Card.rankToString(request_number)+ " from " + opponent.getName());
			opponent.removeCardFromHand(request_card);
			addCardToHand(request_card);
			checkHandForBook();
			return true;
		}
		else
		{
			System.out.println(opponent.getName() + ": Go fish");
			drawFromDeck(game_deck);
			return false;
		}
	}
    //Does the player have the card c in her hand?
    /*
    public boolean cardInHand(Card c) {
    	return false; //stubbed
    }
    */

    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?
    //e.g. will return true if the player has a 7d and the parameter is 7c
    
    /*
    public boolean sameRankInHand(Card c) {
    	return false; //stubbed
    }
    */

}
