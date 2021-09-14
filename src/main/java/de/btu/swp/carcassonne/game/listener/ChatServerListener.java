/*
 * Listener for the Chat 
 */
package de.btu.swp.carcassonne.game.listener;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.chat.ChatPaket;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;
import de.btu.swp.carcassonne.network.server.ServerListener;
import javafx.application.Platform;

public class ChatServerListener extends Service implements ServerListener {

	public ChatServerListener(CarcassonneService carcassonneService) {
		setCarcassonneService(carcassonneService);
	}

	@Override
	public boolean onConnect(Client client, Player player) {
		return true;
	}

	@Override
	public void onDisconnect(Client client, Player player) {

	}

	@Override
	public boolean onPaketReceived(Client client, Player player, Paket paket) {

		if (paket instanceof ChatPaket && player.getId() != paket.getPlayerId()) {
			return false;
		}

		Platform.runLater(() -> {
			paket.executeChange(carcassonneService());
		});

		return true;
	}

	@Override
	public void onPaketSend(Paket paket) {
	}

}
