package assignment5;


/**
 * This critter will always run towards direction 4 in doTimeStep().
 * If it encounters a Craig, it will fight.
 * Otherwise it will not fight and attempt to walk towards direction 5. 
 */
public class Critter1 extends Critter {

	@Override
	public String toString() { return "1"; }
	
	/**
	 * This method makes Critter1 run towards direction 4.
	 */
	@Override
	public void doTimeStep() {
		look(1, false);
		run(4);
	}

	/**
	 * This method makes Critter1 fight against Craig while walk towards direction 5 in other situation.
	 */
	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("C")) {
			return true;
		}
		walk(5);
		return false;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.CRIMSON; }
}
