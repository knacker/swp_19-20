/*
 * paket that gets sent if a player initialliy trys to join the server
 */

package de.btu.swp.carcassonne.game.paket.server;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerData;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class InitPaket extends Service implements ServerPaket {

	private static final long serialVersionUID = -536508464116880426L;

	private final int playerId;
	
	private final PlayerData own;
	
	private final PlayerData[] players;

	public InitPaket(CarcassonneService carcassonneService, Player player) {
		
		setCarcassonneService(carcassonneService);

		this.playerId = lobbyService().getLocalPlayer().getId();

		own = player.toPlayerData();
		
		players = new PlayerData[lobbyService().getLobby().getPlayers().size()];
		
		for(int i = 0; i < players.length; i++) {
			players[i] = lobbyService().getLobby().getPlayers().get(i).toPlayerData();
		}
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		setCarcassonneService(carcassonneService);
		
		lobbyService().getLobby().getPlayers().clear();
		for(PlayerData data : players) {
			Player player = data.toPlayer();
			if(data.getId() == own.getId()) {
				lobbyService().setLocalPlayer(player);
			} else {
				lobbyService().getLobby().getPlayers().add(player);
			}
		}
		
		gameService().getGame().setState(GameState.LOBBY);
	}

}
