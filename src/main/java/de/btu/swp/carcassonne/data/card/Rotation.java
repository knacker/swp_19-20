//holds the data for the rotation of a card
package de.btu.swp.carcassonne.data.card;

import java.io.Serializable;

public enum Rotation implements Serializable {
	
	NORTH(0, 0.5, 0),
	EAST(90, 1, 0.5),
	SOUTH(180, 0.5, 1),
	WEST(270, 0, 0.5);
	
	private final double value;
	private final double xOffset;
	private final double yOffset;
	
	private Rotation(double value, double xOffset, double yOffset) {
		this.value = value;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public double toDouble() {
		return value;
	}
	
	public double getXOffset() {
		return xOffset;
	}
	
	public double getYOffset() {
		return yOffset;
	}
	
	public Rotation opposite() {
		return values()[(ordinal() + 2) % 4];
	}
	
	public int getRelativeX() {
		return (ordinal() % 2) * (ordinal() == 3 ? -1 : 1);
	}
	
	public int getRelativeY() {
		return ((ordinal() + 1) % 2) * (ordinal() == 0 ? -1 : 1);
	}
}
