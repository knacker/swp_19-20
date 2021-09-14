/*
 * listener for the lobbyserver
 */
package de.btu.swp.carcassonne.game.listener;

import java.io.IOException;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.LobbyPaket;
import de.btu.swp.carcassonne.game.paket.server.ErrorPaket;
import de.btu.swp.carcassonne.game.paket.server.InitPaket;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;
import de.btu.swp.carcassonne.network.server.ServerListener;
import javafx.application.Platform;

public class LobbyServerListener extends Service implements ServerListener {

	public LobbyServerListener(CarcassonneService carcassonneService) {
		setCarcassonneService(carcassonneService);
	}

	@Override
	public boolean onConnect(Client client, Player player) {

		if (gameService().getGame().getState() != GameState.LOBBY) {	
			try {
				client.sendPaket(new ErrorPaket("Server is already ingame"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		if (lobbyService().getLobby().getPlayers().size() >= 5) {
			try {
				client.sendPaket(new ErrorPaket("Server is already full"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		Platform.runLater(() -> {
			lobbyService().addPlayer(player);

			try {
				client.sendPaket(new InitPaket(carcassonneService(), player));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		return true;
	}

	@Override
	public void onDisconnect(Client client, Player player) {

		if (connectionService().isConnected()) {
			Platform.runLater(() -> {
				try {
					lobbyService().kickPlayer(player);
				} catch(IllegalStateException ex) {
				}
			});
		}

	}

	@Override
	public boolean onPaketReceived(Client client, Player player, Paket paket) {
		
		System.out.println("Paket: " + paket.getName());

		if (gameService().getGame().getState() != GameState.LOBBY || !(paket instanceof LobbyPaket)
				|| paket.getPlayerId() != player.getId()) {
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
