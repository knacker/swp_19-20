/*
 * used to return the playerdata in one object
 */
package de.btu.swp.carcassonne.data.player;

import java.io.Serializable;

public class PlayerData implements Serializable {

	private static final long serialVersionUID = 6206966029328055502L;
	
	private final int id;
	private final PlayerType type;
	private final String username;
	private final Color color;
	
	public PlayerData(PlayerType type, int id, String username, Color color) {
		this.id = id;
		this.type = type;
		this.username = username;
		this.color = color;
	}

	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Player toPlayer() {
		return new Player(type, id, username, color);
	}
	
}
