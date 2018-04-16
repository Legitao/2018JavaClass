// insert header here
package assignment6;

import java.util.ArrayList;
import java.util.List;

public class Theater {
	private int row;
	private int line;
	private String show;
	private int current_row = 1;
	private int current_line = 1;
	private List<Ticket> log = new ArrayList<>();
	
	/*
	 * Represents a seat in the theater
	 * A1, A2, A3, ... B1, B2, B3 ...
	 */
	static class Seat {
		private int rowNum;
		private int seatNum;

		public Seat(int rowNum, int seatNum) {
			this.rowNum = rowNum;
			this.seatNum = seatNum;
		}

		public int getSeatNum() {
			return seatNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		@Override
		public String toString() {
			String res = "";
			int t = rowNum;
			while(t != 0) {
				int r = t % 26;
				t = t / 26;
				String current = Character.toString((char)('A' + r - 1));
				res = current + res;
			}
			res += Integer.toString(seatNum);
			return res;
		}
	}

  /*
	 * Represents a ticket purchased by a client
	 */
	static class Ticket {
		private String show;
		private String boxOfficeId;
		private Seat seat;
	    private int client;

		public Ticket(String show, String boxOfficeId, Seat seat, int client) {
			this.show = show;
			this.boxOfficeId = boxOfficeId;
			this.seat = seat;
			this.client = client;
		}

		public Seat getSeat() {
			return seat;
		}

		public String getShow() {
			return show;
		}

		public String getBoxOfficeId() {
			return boxOfficeId;
		}

		public int getClient() {
			return client;
		}

		@Override
		public String toString() {
			int width = 31;
			String border = "";
			for(int i = 0; i < width; i++) {
				border += "-";
			}
			
			String line1 = "| ";
			line1 = line1 + "Show: " + show;
			int l1 = line1.length();
			for(int i = l1; i < width - 1; i++) {
				line1 += " ";
			}
			line1 += "|";
			
			String line2 = "| ";
			line2 = line2 + "Box Office ID: " + boxOfficeId;
			int l2 = line2.length();
			for(int i = l2; i < width - 1; i++) {
				line2 += " ";
			}
			line2 += "|";
			
			String line3 = "| ";
			line3 = line3 + "Seat: " + seat;
			int l3 = line3.length();
			for(int i = l3; i < width - 1; i++) {
				line3 += " ";
			}
			line3 += "|";
			
			String line4 = "| ";
			line4 = line4 + "Client: " + String.valueOf(client);
			int l4 = line4.length();
			for(int i = l4; i < width - 1; i++) {
				line4 += " ";
			}
			line4 += "|";
			
			String res = border + "\n" + line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + border + "\n";
			return res;
		}
	}
	

	
	public Theater(int numRows, int seatsPerRow, String show) {
		row = numRows;
		line = seatsPerRow;
		this.show = show;
	}

	/*
	 * Calculates the best seat not yet reserved
	 *
 	 * @return the best seat or null if theater is full
   */
	public Seat bestAvailableSeat() {
		if(current_row > row) {
			return null;
		}
		Seat s = new Seat(current_row, current_line);
		if(current_line == line) {
			current_line = 1;
			current_row++;
		} else {
			current_line++;
		}
		return s;
	}

	/*
	 * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
	 *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
	public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
		if(seat == null) {
			return null;
		}
		Ticket t = new Ticket(show, boxOfficeId, seat, client);
		System.out.print(t.toString());
		if(seat.getRowNum() == row && seat.getSeatNum() == line) {
			System.out.println("Sorry, we are sold out!");
		}
		log.add(t);
		return t;
	}

	/*
	 * Lists all tickets sold for this theater in order of purchase
	 *
   * @return list of tickets sold
   */
	public List<Ticket> getTransactionLog() {
		return log;
	}
}
