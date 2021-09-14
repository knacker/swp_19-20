/*
 * Defines how a vector is build. a Vector consists of a Structure type, the points value it has and if it has an endpoint.
 */
package de.btu.swp.carcassonne.data.graph;

import java.util.HashSet;
import java.util.Set;

import de.btu.swp.carcassonne.data.player.Follower;

public class Vector {

	private final Structure type;
	private final int points;
	private final boolean endpoint;
	private final Set<Vector> connections = new HashSet<>();
	
	private Follower follower;
	
	public Vector(Structure type, int points, boolean endpoint) {
		this.type = type;
		this.points = points;
		this.endpoint = endpoint;
	}
	
	public Structure getType() {
		return type;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean isEndpoint() {
		return endpoint;
	}
	
	public void addConnection(Vector v) {
		connections.add(v);
	}
	
	public Set<Vector> getConnections() {
		return connections;
	}
	
	public Follower getFollower() {
		return follower;
	}
	
	public boolean hasFollower() {
		return follower != null;
	}
	
	public void setFollower(Follower follower) {
		this.follower = follower;
	}
	
	@Override
	public String toString() {
		return type.name();
	}
	
}
