package de.btu.swp.carcassonne.gui.score;

public class Position {

	private final double x,y;
	
	public Position(double x, double y) {
		this.x = x / 100.0;
		this.y = y / 100.0;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
}
