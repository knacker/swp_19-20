/*
 * listener for the gameServer 
 */
package de.btu.swp.carcassonne.game.listener;

import java.io.IOException;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.GamePaket;
import de.btu.swp.carcassonne.game.paket.game.GameTurnPaket;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;
import de.btu.swp.carcassonne.network.server.ServerListener;
import javafx.application.Platform;

public class GameServerListener extends Service implements ServerListener {

	public GameServerListener(CarcassonneService carcassonneService) {
		setCarcassonneService(carcassonneService);
	}

	@Override
	public boolean onConnect(Client client, Player player) {
		return true;
	}

	@Override
	public void onDisconnect(Client client, Player player) {

		if (gameService().getGame().getState() != GameState.INGAME
				|| player != gameService().getGame().getPlayerOnTurn() || !connectionService().isConnected()) {
			return;
		}

		Player next = gameService().playerService().nextPlayer();
		Card card = gameService().getGame().getCurrentCard();

		try {
			Paket paket = new GameTurnPaket(next, card);
			connectionService().sendPaket(paket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onPaketReceived(Client client, Player player, Paket paket) {

		if (gameService().getGame().getState() != GameState.INGAME || !(paket instanceof GamePaket) || paket.getPlayerId() != player.getId()) {
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
