// Insert header here
package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Thread;
import assignment6.Theater.*;

public class BookingClient {
	private Theater theater;
	private Map<String, Integer> office;
	
	//Inner class Task represents a single box office's task
	public class Task implements Runnable {
	  String boxID;
	  int start_clientID;
	  
	  public Task(String boxID, int start_clientID) {
		  this.boxID = boxID;
		  this.start_clientID = start_clientID;
	  }
	  
	  @Override
	  public void run() {
		  for(int i = 0; i < office.get(boxID); i++) {
			  int clientID = start_clientID + i;
			  synchronized (theater) {
				  Seat s = theater.bestAvailableSeat();
				  theater.printTicket(boxID, s, clientID);
			  }
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
	  }
	  
	}
  /*
   * @param office maps box office id to number of customers in line
   * @param theater the theater where the show is playing
   */
	public BookingClient(Map<String, Integer> office, Theater theater) {
		this.office = office;
		this.theater = theater;
	}

  /*
   * Starts the box office simulation by creating (and starting) threads
   * for each box office to sell tickets for the given theater
   *
   * @return list of threads used in the simulation,
   *         should have as many threads as there are box offices
   */
	public List<Thread> simulate() {
		int num_threads = office.size();
		Task[] tasks = new Task[num_threads];
		Thread[] threads = new Thread[num_threads];
		int start_clientID = 1;
		int j = 0;
		for (String boxID : office.keySet()) {
			tasks[j] = new Task(boxID, start_clientID);
			start_clientID += office.get(boxID);
			j++;
		}
		
		//create 5 threads to execute 5 tasks
		List<Thread> thread_list = new ArrayList<>();
		for(int i = 0; i < tasks.length; i++) {
			threads[i] = new Thread(tasks[i]);
			thread_list.add(threads[i]);
			threads[i].start();
		}
		
		try {
			for(Thread t : threads) {			
				t.join();
			} 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return thread_list;
	}
	

	public static void main(String[] args) {
		Map<String, Integer> office = new HashMap<>();
		office.put("BOX1", 3);
		office.put("BOX3", 3);
		office.put("BOX2", 4);
		office.put("BOX5", 3);
		office.put("BOX4", 3);
		Theater theater = new Theater(3, 5, "Ouija");
		
		BookingClient b = new BookingClient(office, theater);
		b.simulate();
	}
}
