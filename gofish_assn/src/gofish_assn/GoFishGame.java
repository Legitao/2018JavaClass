package gofish_assn;

public class GoFishGame 
{
	Player p1;
	Player p2;
	Deck game_deck;
	
	public GoFishGame(){}
	public GoFishGame(String name1, String name2) 
	{
		p1 = new Player(name1);
		p2 = new Player(name2);
		game_deck = new Deck();
	}

	public void twoPeoplePlay()
	{
		game_deck.shuffle();
		for(int i = 0; i < 7; i++)
		{
			p1.addCardToHand(game_deck.dealCard());
			p2.addCardToHand(game_deck.dealCard());
		}
		/*
		System.out.println(p1.handToString());
		System.out.println(p2.handToString());
		game_deck.printDeck();
		*/
		
		int turn = 0;
		
		while(p1.getBookSize() + p2.getBookSize() != 52)
		{
			if(turn == 0)
			{
				Card request_card = p1.chooseCardFromHand();
				if(request_card == null)
				{
					turn = (turn + 1) % 2;
					p1.drawFromDeck(game_deck);
				}
				else
				{
					boolean draw_from_opponent = p1.askOpponent(p2, game_deck, request_card);
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
			/*
			System.out.println(p1.getName() + " hand: " + p1.handToString());
			System.out.println(p2.getName() + " hand: " + p2.handToString());
			game_deck.printDeck();
			*/
		}
		
		int p1_book = p1.getBookSize() / 2;
		int p2_book = p2.getBookSize() / 2;
		if(p1_book > p2_book)
		{
			System.out.println(p1.getName() + " wins with " + p1_book + " booked pairs:");
			System.out.println(p1.bookToString());
			System.out.println(p2.getName() + " has " + p2_book + " booked pairs:");
			System.out.println(p2.bookToString());
		}
		else
		{
			System.out.println(p2.getName() + " wins with " + p2_book + " booked pairs:");
			System.out.println(p2.bookToString());
			System.out.println(p1.getName() + " has " + p1_book + " booked pairs:");
			System.out.println(p1.bookToString());
		}

	}

}
