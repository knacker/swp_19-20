/*
 * Describes a card with an id and a graph. That data can be returned from here.
 */
package de.btu.swp.carcassonne.data.card;

import de.btu.swp.carcassonne.data.graph.Graph;
import de.btu.swp.carcassonne.data.graph.Vector;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Card {
	
	public final static Card PLACEHOLDER = new Card("PLACEHOLDER", null);

	private final String id;
	private Graph graph;
	
	private final ObjectProperty<Rotation> rotation = new SimpleObjectProperty<>(Rotation.NORTH);
	
	public Card(String id, Graph graph) {
		this.id = id;
		this.graph = graph;
	}

	public String getPath() {
		return "/card/Card-" + id + ".png";
	}
	
	public Graph getGraph() {
		return this.graph;
	}

	public Rotation getRotation() {
		return rotation.get();
	}
	
	public void setRotation(Rotation r) {
		if(r == null) {
			r = Rotation.NORTH;
		}
		while(getRotation() != r) {
			rotateRight();
		}
	}
	
	/*
	 * rotiert die karten in die gewünschte richtung
	 */
	public void rotateRight() {
		
		Rotation current = getRotation();
		
		Vector northOld = graph.getNorth();
		Vector eastOld = graph.getEast();
		Vector southOld = graph.getSouth();
		Vector westOld = graph.getWest();

		graph.setNorth(westOld);
		graph.setEast(northOld);
		graph.setSouth(eastOld);
		graph.setWest(southOld);
		
		if (current == Rotation.NORTH) {
			rotation.set(Rotation.EAST);
		}
		
		if(current == Rotation.EAST) {
			rotation.set(Rotation.SOUTH);
		}
		
		if(current == Rotation.SOUTH) {
			rotation.set(Rotation.WEST);
		}
		
		if(current == Rotation.WEST) {
			rotation.set(Rotation.NORTH);
		}
		
	}
	
	public void rotateLeft() {
		
		Rotation current = getRotation();
		
		Vector northOld = graph.getNorth();
		Vector eastOld = graph.getEast();
		Vector southOld = graph.getSouth();
		Vector westOld = graph.getWest();
		
		graph.setNorth(eastOld);
		graph.setWest(northOld);
		graph.setSouth(westOld);
		graph.setEast(southOld);

		if (current == Rotation.NORTH) {
			rotation.set(Rotation.WEST);
		}
		
		if(current == Rotation.WEST) {
			rotation.set(Rotation.SOUTH);
		}
		
		if(current == Rotation.SOUTH) {
			rotation.set(Rotation.EAST);
		}
		
		if(current == Rotation.EAST) {
			rotation.set(Rotation.NORTH);
		}
		
	}
	
	public ObjectProperty<Rotation> rotationProperty() {
		return rotation;
	}
	
	public boolean hasMonas() {
		return graph.getMonas() != null;
	}

	public String getId() {
		return id;
	}

	// TODO - MAX
	// Hier muss entsprechend die Rotation verändert werden.
	// Bsp an getNorth():
	// Rotation -> Vector vom graph
	// NORTH -> NORTH
	// WEST -> EAST
	// SOUTH -> SOUTH
	// EAST -> WEST
	
	public Vector getVector(Rotation rot) {
		if(rot == null) {
			return graph.getMonas();
		}
		switch(rot) {
		case EAST:
			return getEast();
		case NORTH:
			return getNorth();
		case SOUTH:
			return getSouth();
		case WEST:
			return getWest();
		default:
			return graph.getMonas();
		}
	}
	
	public Vector getNorth() {
		return graph.getNorth();
	}
	
	public Vector getSouth() {
		return graph.getSouth();
	}
	
	public Vector getWest() {
		return graph.getWest();
	}
	
	public Vector getEast() {
		return graph.getEast();
	}
	
	@Override
	public String toString() {
		if(isPlaceholder()) {
			return "PLACEHOLDER";
		}
		return "{id=" + id + ", rotation=" + getRotation() + ", graph=" + graph + "}";
	}

	public boolean isPlaceholder() {
		return this == PLACEHOLDER;
	}
	
}
