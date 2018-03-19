package gofish_assn;

public class GoFishGame 
{
	Player p1;  //One player
	Player p2;  //The other player
	Deck game_deck;  //The deck used in the game
	
	/**
	 * This is default constructor
	 */
	public GoFishGame(){}
	
	/**
	 * This is parameterized constructor to create two players and a deck
	 * @param name1 This is the name of one player
	 * @param name2 This is the name of the other player
	 */
	public GoFishGame(String name1, String name2) 
	{
		p1 = new Player(name1);
		p2 = new Player(name2);
		game_deck = new Deck();
	}

	/**
	 * This method is the procedure of two players playing game.
	 */
	public void twoPeoplePlay()
	{
		game_deck.shuffle();
		for(int i = 0; i < 7; i++)
		{
			p1.addCardToHand(game_deck.dealCard());
			p2.addCardToHand(game_deck.dealCard());
		}
		//print initial condition
		Main.output.println(p1.getName() + ": " + p1.handToString());
		Main.output.println(p2.getName() + ": " + p2.handToString());
		Main.output.print("deck: ");
		game_deck.printDeck();
		Main.output.println();
		
		int turn = 0;  //turn == 0 means its the first player's term while turn == 1 means its the second player's term
		
		while(p1.getBookSize() + p2.getBookSize() != 52)  //Continue game until two players get 52 overall cards booked
		{
			if(turn == 0)  //Fist player's term
			{
				Card request_card = p1.chooseCardFromHand();
				if(request_card == null) //The other player doesn't any card in hand
				{
					//switch next turn to the second player
					turn = (turn + 1) % 2;
					//draw a card from deck
					p1.drawFromDeck(game_deck);
				}
				else
				{
					//Ask the opponent for card.
					boolean draw_from_opponent = p1.askOpponent(p2, game_deck, request_card);
					//If opponent doesn't have the desired card, change the turn to opponent. Else the turn is keet 0.
					if(draw_from_opponent == false)
						turn = (turn + 1) % 2;
				}

			}
			else
			{
				Card request_card = p2.chooseCardFromHand();
				if(request_card == null)
				{
					turn = (turn + 1) % 2;
					p2.drawFromDeck(game_deck);
				}
				else
				{
					boolean draw_from_opponent = p2.askOpponent(p1, game_deck, request_card);
					if(draw_from_opponent == false)
						turn = (turn + 1) % 2;
				}
			}
			Main.output.println();
			/*
			Main.output.println(p1.getName() + " hand: " + p1.handToString());
			Main.output.println(p2.getName() + " hand: " + p2.handToString());
			game_deck.printDeck();
			*/
		}
		
		int p1_book = p1.getBookSize() / 2;  //number of books in p1's hand
		int p2_book = p2.getBookSize() / 2;  //number of books in p2's hand
		Main.output.println();
		if(p1_book > p2_book)
		{
			Main.output.println(p1.getName() + " wins with " + p1_book + " booked pairs:");
			Main.output.println(p1.bookToString());
			Main.output.println(p2.getName() + " has " + p2_book + " booked pairs:");
			Main.output.println(p2.bookToString());
		}
		else
		{
			Main.output.println(p2.getName() + " wins with " + p2_book + " booked pairs:");
			Main.output.println(p2.bookToString());
			Main.output.println(p1.getName() + " has " + p1_book + " booked pairs:");
			Main.output.println(p1.bookToString());
		}
	}

}
