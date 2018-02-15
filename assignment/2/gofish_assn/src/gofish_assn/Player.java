package gofish_assn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Player 
{
	
	private ArrayList<Card> hand = new ArrayList<Card>();  //the container of cards in hand
	private ArrayList<Card> book = new ArrayList<Card>();  //the container of cards booked
	private String name;  //the name of the player
	
	/**
	 * This method is the parameterized constructor
	 * @param name This is the name of the player
	 */
	public Player(String name) 
	{
		this.name = name;
	}
	
	/**
	 * This method adds one card to player's hand
	 * @param c This is the card to be added
	 */
	public void addCardToHand(Card c) 
	{
		hand.add(c);
	}
	
	/**
	 * This method removes a card from player's hand
	 * @param c This is the card to be removed
	 * @return The removed card
	 */
	public Card removeCardFromHand(Card c) 
	{
		Card retCard = new Card();
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).getRank() == c.getRank())
			{
				hand.remove(i);
				break;
			}
		}
		return retCard;  //why return card?
	}
	
	/**
	 * This method is the getter of name
	 * @return It returns the name of player
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * This method gets the string representation of the cards in hand
	 * @return It returns the string representation of the cards in hand
	 */
	public String handToString() 
	{
		String s = new String();
		for(int i = 0; i < hand.size(); i++)
		{
			s += hand.get(i).toString() + " ";
		}
		return s;
	}
	
	/**
	 * This method gets the string representation of the cards booked
	 * @return It returns the string representation of the cards booked
	 */
	public String bookToString() 
	{
		String s = new String();
		for(int i = 0; i < book.size(); i++)
		{
			s += book.get(i).toString() + " ";
		}
		return s;
	}
	
	/**
	 * This method is the getter of hand's size
	 * @return The number of cards in hand
	 */
	public int getHandSize() {
		return hand.size();
	}
	
	/**
	 * This method is the getter of book's size
	 * @return The number of cards booked
	 */
	public int getBookSize() {
		return book.size();
	}
	
	
    //
	/**
	 * This method checks a players hand for a pair. 
	 * @return If a pair is found, it moves the cards to the book and returns true.
	 */
    public boolean checkHandForBook() 
    {
    	if(hand.size() >= 3)  //Sort hand's cards with the order of their ranks
    	{
    		hand.sort(new Comparator<Card>(){	
    			public int compare(Card a, Card b)
    			{
    				return Integer.compare(a.getRank(), b.getRank());
    			}
    		}		
    		);
    	}
    	boolean find_pair = false;  //If one pair is hand, it will be turn true
    	for(int i = 0; i < hand.size() - 1; i++)
    	{
    		if(hand.get(i).getRank() == hand.get(i + 1).getRank())
    		{
    			book.add(hand.remove(i));
    			book.add(hand.remove(i));
    			i--;  //Move the index one step back after removing, so that after i++ the index remains at current position
    			find_pair = true;
    		}
    	}
    	return find_pair;
    }
    
    /**
     * This method checks whether the player has a card with the same rank as c in his hand
     * @param c The card to be checked
     * @return If their is a card with the same rank as c, it returns true
     */
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
    
    /**
     * This method randomly choose one card from hand
     * @return It returns the chosen card
     */
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
    
    /**
     * This method attempts to draw a card from the deck
     * @param game_deck The deck of the game
     */
    public void drawFromDeck(Deck game_deck)
    {
    	Card c = game_deck.dealCard();
		if(c == null)  //If there is no more card in deck, print the information
		{
			Main.output.println("Deck is empty");
			
		}
		addCardToHand(c);  
		Main.output.println(getName() + " draws " + Card.rankToString(c.getRank()) +" from deck");
		checkHandForBook();
		
    }
    /**
     * This method attempts to request a card from opponent
     * @param opponent This is opponent player
     * @param game_deck This is the deck being used
     * @param request_card This is the card being requested
     * @return If opponent has the requested card, it returns true
     */
	public boolean askOpponent(Player opponent, Deck game_deck, Card request_card)
	{
		int request_number = request_card.getRank();
		Main.output.println(getName() + ": Do you have " + Card.rankToString(request_number));
		if(opponent.rankInHand(request_card))
		{
			Main.output.println(opponent.getName() + ": Yes, I have a " + Card.rankToString(request_number));
			Main.output.println(getName() + " draws " + Card.rankToString(request_number)+ " from " + opponent.getName());
			opponent.removeCardFromHand(request_card);
			addCardToHand(request_card);
			checkHandForBook();
			return true;
		}
		else
		{
			Main.output.println(opponent.getName() + ": Go fish");
			drawFromDeck(game_deck);
			return false;
		}
	}

}
