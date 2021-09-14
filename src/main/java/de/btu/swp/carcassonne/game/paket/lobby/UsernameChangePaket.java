/*
 * sends a paket to all players when a player changes his username to display it
 */

package de.btu.swp.carcassonne.game.paket.lobby;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.LobbyPaket;

public class UsernameChangePaket implements LobbyPaket {

	private static final long serialVersionUID = -7382222064674770227L;

	private final int playerId;
	private final String username;

	public UsernameChangePaket(Player player) {
		this.playerId = player.getId();
		this.username = player.getUsername();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Player player = carcassonneService.lobbyService().getPlayer(playerId);
		
		player.setUsername(username);
		
	}

}
