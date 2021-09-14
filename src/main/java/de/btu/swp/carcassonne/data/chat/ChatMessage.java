//contains the data of the  sender of the message, as well as the message as a string.
package de.btu.swp.carcassonne.data.chat;

import de.btu.swp.carcassonne.data.player.Player;

public class ChatMessage {

	private final Player sender;
	private final String message;
	
	public ChatMessage(Player sender, String message) {
		this.sender = sender;
		this.message = message;
	}
	
	public Player getSender() {
		return sender;
	}

	public String getMessage() {
		return message;
	}
}
