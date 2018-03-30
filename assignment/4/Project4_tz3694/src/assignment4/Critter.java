package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Tao Zhu
 * tz3694
 * 15455
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.Iterator;
import java.util.List;

import org.hamcrest.core.IsInstanceOf;



public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean moved = false;  //mark if a critter has moved once, to achieve second time moving prevention
	
	/**
	 * This private method is a utility function used to determine if there is any critter occupying (x, y)
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return If there is a critter occupying (x, y), return true.
	 */
	private boolean occupied(int x, int y) {
		for(Critter c : population) {
			if(c.energy > 0) {
				if(c.x_coord == x && c.y_coord == y) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method gets a new point in the provided direction and length with respect to current point.
	 * @param direction The direction of move
	 * @param length The distance of move
	 * @return A List whose first element is x coordinate and second element is y coordinate.
	 */
	private List<Integer> try_move(int direction, int length) {
		List<Integer> destination = new java.util.ArrayList<Integer>();
		int x_new = x_coord;
		int y_new = y_coord;
		switch(direction) {
			//note: a % b is wrong. In Java, % is remainder, while in Python, its modulus.
			case 0: x_new = Math.floorMod((x_coord + 1), Params.world_width);
					break;
			case 1: x_new = Math.floorMod((x_coord + 1), Params.world_width);  
					y_new = Math.floorMod((y_coord - 1), Params.world_height);
					break;
			case 2: y_new = Math.floorMod((y_coord - 1), Params.world_height);  
					break;	
			case 3: x_new = Math.floorMod((x_coord - 1), Params.world_width); 
					y_new = Math.floorMod((y_coord - 1), Params.world_height);
					break;
			case 4: x_new = Math.floorMod((x_coord - 1), Params.world_width);
					break;
			case 5: x_new = Math.floorMod((x_coord - 1), Params.world_width);
					y_new = Math.floorMod((y_coord + 1), Params.world_height);
					break;
			case 6: y_new = Math.floorMod((y_coord + 1), Params.world_height);
					break;
			case 7: x_new = Math.floorMod((x_coord + 1), Params.world_width);
					y_new = Math.floorMod((y_coord + 1), Params.world_height);
					break;
			default: break;
		}
		destination.add(x_new);
		destination.add(y_new);
		return destination;
	}
	
	/**
	 * This method implements the walking functionality of a critter.
	 * @param direction The direction to walk
	 */
	protected final void walk(int direction) {
		String caller_name = Thread.currentThread().getStackTrace()[2].getMethodName();
		energy -= Params.walk_energy_cost;
		if(moved == false) {
			moved = true;
			List<Integer> destination = try_move(direction, 1);
			if(caller_name.equals("fight") && occupied(destination.get(0), destination.get(1))) {
				return;  //In fight, if destination is occupied, critter cannot move
			}
			x_coord = destination.get(0);
			y_coord = destination.get(1);
		}
	}
	
	/**
	 * This method implements the running functionality of a critter.
	 * @param direction The direction to run
	 */
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
		String caller_name = Thread.currentThread().getStackTrace()[2].getMethodName();
		if(moved == false) {
			moved = true;
			List<Integer> destination = try_move(direction, 2);
			if(caller_name.equals("fight") && occupied(destination.get(0), destination.get(1))) {
				return;  //In fight, if destination is occupied, critter cannot move
			}
			x_coord = destination.get(0);
			y_coord = destination.get(1);
		}
	}
	

	/**
	 * This method implements the reproduction functionality of a critter.
	 * @param offspring This is the new critter to be added
	 * @param direction This is the direction of the new critter's initial position with regard to its parent
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy)
		{
			return;
		}
		offspring.energy = this.energy / 2;
		this.energy = this.energy - this.energy / 2;
		List<Integer> destination = try_move(direction, 1);
		offspring.x_coord = destination.get(0);
		offspring.y_coord = destination.get(1);
		babies.add(offspring);  //quick note: This is an example where a subclass can indirectly access superclass's private data through protected/public methods
		
	}
	
	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name This is the name of the new intance's class
	 * @throws InvalidCritterException This is the exception during creation of an instance
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class <?> c = Class.forName(myPackage + "." + critter_class_name);
			Critter new_critter = (Critter) c.newInstance();
			//to do
			new_critter.energy = Params.start_energy;
			new_critter.x_coord = getRandomInt(Params.world_width);
			new_critter.y_coord = getRandomInt(Params.world_height);
			//encounter will be solved in the next time step
			population.add(new_critter);
		}
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} 
		catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		} 
		catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters. This contains all instances with the class name provided
	 * @throws InvalidCritterException This is the exception when the class name provided cannot be found
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		try {
			Class<?> clazz = Class.forName(myPackage + "." + critter_class_name);
			
			for(Critter c : population) {		
				if(clazz.isAssignableFrom(c.getClass())) {
					result.add(c);	
				}	
			}
			return result;
		}		
		catch (ClassNotFoundException e1) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}	


	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	/**
	 * This method does whole process in one time step: each critter invokes doTimeStep(), solve encounter,
	 * deduct rest energy, remove dead critters and plant algae.
	 */
	public static void worldTimeStep() {
		for(Critter c : population) {
			c.moved = false;  //before each time step, set moved to false
			c.doTimeStep();
		}
		//solve encounter
		//This loop automatically resolves situations where >=3 critters are in one spot
		for(int i = 0; i < population.size(); i++) {
			Critter tmp1 = population.get(i);
			if(tmp1.energy > 0) {
				for(int j = i + 1; j < population.size(); j++) {
					Critter tmp2 = population.get(j);
					if(tmp1.energy > 0 && tmp2.energy > 0 && tmp2.x_coord == tmp1.x_coord && tmp2.y_coord == tmp1.y_coord) {  //tmp1.energy > 0 is essential, because there may be many critters in one spot
						boolean a = tmp1.fight(tmp2.toString());
						boolean b = tmp2.fight(tmp1.toString());
						if(a == true && b == true) {
							int a_number = getRandomInt(tmp1.energy);
							int b_number = getRandomInt(tmp2.energy);
							if(a_number >= b_number) {
								tmp1.energy += tmp2.energy / 2;
								tmp2.energy = 0;
							}
							else {
								tmp2.energy += tmp1.energy /2;
								tmp1.energy = 0;
							}
						}
						else if(a == true && b == false) {
							if(tmp1.energy > 0 && tmp2.energy > 0 && tmp2.x_coord == tmp1.x_coord && tmp2.y_coord == tmp1.y_coord) {
								int a_number = getRandomInt(tmp1.energy);
								int b_number = 0;
								if(a_number >= b_number) {
									tmp1.energy += tmp2.energy / 2;
									tmp2.energy = 0;
								}
								else {
									tmp2.energy += tmp1.energy /2;
									tmp1.energy = 0;
								}
							}
						}
						else if(a == false && b == true) {
							if(tmp1.energy > 0 && tmp2.energy > 0 && tmp1.x_coord == tmp2.x_coord && tmp1.y_coord == tmp2.y_coord) {
								int a_number = 0;
								int b_number = getRandomInt(tmp2.energy);
								if(a_number >= b_number) {
									tmp1.energy += tmp2.energy / 2;
									tmp2.energy = 0;
								}
								else {
									tmp2.energy += tmp1.energy /2;
									tmp1.energy = 0;
								}
							}
						}
						else {
							//both don't wanna fight, run away during calling fight()
						}
					}
				}
			}
		}
		//deduct rest_energy
		for(Critter c : population) {
			c.energy -= Params.rest_energy_cost;
		}
		//cull the dead critters
		/*
		for(Critter c : population) {
			if(c.energy <= 0) {
				population.remove(c);  //TODO: WORING!!!! to remove during iteration. I should search canonical usage.
			}
		}
		*/
		for(Iterator<Critter> i = population.iterator(); i.hasNext();) {
			if(i.next().energy <= 0) {
				i.remove();
			}
		}
		//plant algae
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			Algae t = new Algae();
			t.setEnergy(Params.start_energy);
			t.setX_coord(getRandomInt(Params.world_width));
			t.setY_coord(getRandomInt(Params.world_height));
			population.add(t);
		}
		
		//Move babies to general population
		population.addAll(babies);
		babies.clear();
	}
	
	/**
	 * This is the viewer of the model. It prints the grid.
	 */
	public static void displayWorld() {
		char grid[][] = new char[Params.world_height + 2][Params.world_width + 2];
		for(int i = 0; i < Params.world_height + 2; i++) {
			for(int j = 0; j < Params.world_width + 2; j++) {
				grid[i][j] = ' ';  //TODO: why if default initialize to null, empty world test will fail
			}
		}
		grid[0][0] = grid[0][Params.world_width + 1] = grid[Params.world_height + 1][0] = grid[Params.world_height + 1][Params.world_height + 1] = '+';
		for(int i = 1; i < Params.world_width + 1; i++) {
			grid[0][i] = grid[Params.world_height + 1][i] = '-';
		}
		for(int i = 1; i < Params.world_height + 1; i++) {
			grid[i][0] = grid[i][Params.world_width + 1] = '|';
		}
		
		for(Critter c : population) {
			if(c.energy > 0) { //TODO: in case there are dead critters not culled. At end, I should delete this line and it still works
				grid[c.y_coord + 1][c.x_coord + 1] = c.toString().charAt(0);
			}
		}
		
		for(int i = 0; i < Params.world_height + 2; i++) {
			for(int j = 0; j < Params.world_width + 2; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.print('\n');
		}
	}
	
	
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
}
