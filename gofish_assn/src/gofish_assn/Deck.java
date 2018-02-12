package gofish_assn;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card> ();
	final int NUM_CARDS = 52;  //for this kind of deck
	
	//creates a new sorted deck
	public Deck() {
		for(int i = 0; i < NUM_CARDS; i++)
		{
			if(i % 4 == 0)
			{
				deck.add(new Card(i / 4 + 1, 'c'));
			}
			else if(i % 4 == 1)
			{
				deck.add(new Card(i / 4 + 1, 'd'));
			}
			else if(i % 4 == 2)
			{
				deck.add(new Card(i / 4 + 1, 'h'));
			}
			else
			{
				deck.add(new Card(i / 4 + 1, 's'));
			}
		}
		
	}
	
	public void shuffle() {
		//System.out.println(deck.size());
		Random rand_generator = new Random();
		for(int i = 0; i < NUM_CARDS; i++)
		{
			int rand_index = rand_generator.nextInt(NUM_CARDS);
			Card tmp = deck.get(rand_index);
			deck.set(rand_index, deck.get(i));
			deck.set(i, tmp);
		}
	}
	
	
	public void printDeck() {
		String s = "";
		for(int i = 0; i < deck.size(); i++)
		{
			s += deck.get(i).toString() + " ";
		}
		System.out.println(s);
		
	}
	
	
	public Card dealCard() {
		if(deck.isEmpty())
		{
			System.out.println("no cardddddddddd");
			return null;
		}
		
		Card c = deck.get(deck.size() - 1);
		deck.remove(deck.size() - 1);
		return c;		
	}
	

}
