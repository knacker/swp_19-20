/*
 * Holds the data of a Structure and its graph. 
 */
package de.btu.swp.carcassonne.data.graph;

import java.util.HashSet;
import java.util.Set;

import de.btu.swp.carcassonne.data.player.Follower;

public class StructureData {
	
	private int points = 0;
	private boolean finished = true;
	private final Set<Vector> vectors = new HashSet<>();

	private final Set<Follower> followers = new HashSet<>();
	
	public Set<Vector> getVectors() {
		return vectors;
	}
	
	public Set<Follower> getFollowers() {
		return followers;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int p) {
		points += p;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void andFinished(boolean b) {
		finished = finished && b;
	}
	
	@Override
	public String toString() {
		return "{finished=" + finished + ", points=" + points + "}";
	}
	
	public Structure getType() {
		if(vectors.size() == 0) {
			return null;
		}
		return vectors.iterator().next().getType();
		
	}
}
