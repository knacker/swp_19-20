package de.btu.swp.carcassonne.game.service;

import java.io.IOException;

import de.btu.swp.carcassonne.data.chat.Chat;
import de.btu.swp.carcassonne.data.chat.ChatMessage;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.chat.ChatPaket;

/**
 * Manager for all chat related things
 */
public class ChatService extends Service {

	private final Chat chat = new Chat();
	
	/**
	 * Send a message to all players
	 * @param message
	 */
	public void sendMessage(String message) {
		
		Player sender = lobbyService().getLocalPlayer();
		
		chat.getMessages().add(new ChatMessage(sender, message));
		
		ChatPaket paket = new ChatPaket(sender, message);
		
		try {
			connectionService().sendPaket(paket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get Data Class Chat
	 * @return chat
	 */
	public Chat getChat() {
		return chat;
	}
}
