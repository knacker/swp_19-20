/*
 * A Graph consist of 4 Vectors(or 5 with a monas). The Vectors are used to define the graph in a card.
 */
package de.btu.swp.carcassonne.data.graph;

public class Graph {
	
	private Vector north, east, south, west;
	
	private final Vector monas;
	
	public Graph(Vector north, Vector east, Vector south, Vector west) {
		this(north, east, south, west, null);
	}
	
	public Graph(Vector north, Vector east, Vector south, Vector west, Vector monas) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
		this.monas = monas;
	}
	
	public void setNorth(Vector northNew) {
		this.north = northNew;
	}
	
	public void setEast(Vector eastNew) {
		this.east = eastNew;
	}
	
	public void setSouth(Vector southNew) {
		this.south = southNew;
	}
	
	public void setWest(Vector westNew) {
		this.west = westNew;
	}

	public Vector getNorth() {
		return north;
	}
	
	public Vector getSouth() {
		return south;
	}
	
	public Vector getEast() {
		return east;
	}
	
	public Vector getWest() {
		return west;
	}
	
	public Vector getMonas() {
		return monas;
	}
	
	@Override
	public String toString() {
		return "{north=" + north + ", east=" + east + ", south=" + south + ", west=" + west + ", monas=" + monas + "}";
	}

}
