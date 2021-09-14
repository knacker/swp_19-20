/*
 * contains the data that defines a follower. a follwer has a player, a field its placed on and the fields vector
 */
package de.btu.swp.carcassonne.data.player;

import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Vector;
import javafx.scene.Node;

public class Follower {

	private final Player player;
	private final Vector vector;
	private final Field field;
	private Node path;
	
	public Follower(Player player, Field field, Vector v) {
		this.player = player;
		this.field = field;
		this.vector = v;
		
		player.getFollowers().add(this);
		v.setFollower(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Vector getVector() {
		return vector;
	}
	
	public void setNode(Node node) {
		this.path = node;
	}
	
	public Node getNode() {
		return path;
	}
	
	public Color getColor() {
		return player.getColor();
	}
	
	public Field getField() {
		return field;
	}
	
	public Rotation getDirectionOnField() {
		for(Rotation r : Rotation.values()) {
			if(field.getCard().getVector(r) == vector) {
				return r;
			}
		}
		return null;
	}
	
}
