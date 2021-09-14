/*
 * chatpaket that contains a message aswell as the player id that sent it
 */
package de.btu.swp.carcassonne.game.paket.chat;

import de.btu.swp.carcassonne.data.chat.ChatMessage;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.network.Paket;

public class ChatPaket implements Paket {

	private static final long serialVersionUID = 572513408905840130L;

	private final int playerId;
	private final String message;

	public ChatPaket(Player player, String message) {
		this.playerId = player.getId();
		this.message = message;
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {

		Player player = carcassonneService.lobbyService().getPlayer(playerId);

		carcassonneService.chatService().getChat().getMessages().add(new ChatMessage(player, message));

	}

}
