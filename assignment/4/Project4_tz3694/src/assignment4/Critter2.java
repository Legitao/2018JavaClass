package assignment4;

/**
 * This critter will always reproduce a child in doTimeStep().
 * If it encounters a Craig, it will not fight and attempt to run towards a random direction.
 * Otherwise it will fight. 
 */
public class Critter2 extends Critter {
	@Override
	public String toString() { return "2"; }

	/**
	 * This method makes Critter2 produce a child.
	 */
	@Override
	public void doTimeStep() {
		Critter2 child = new Critter2();
		reproduce(child, Critter.getRandomInt(8));
	}

	/**
	 * This method makes Critter2 run towards a random direction when encountered with Craig while fight in other situations.
	 */
	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("C")) {
			run(Critter.getRandomInt(8));
			return false;
		}
		return true;
	}
}
