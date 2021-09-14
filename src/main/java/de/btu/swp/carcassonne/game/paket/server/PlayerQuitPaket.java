/*
 * sends a paket if a player leaves the game
 */
package de.btu.swp.carcassonne.game.paket.server;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class PlayerQuitPaket implements ServerPaket {

	private static final long serialVersionUID = 3282863834361205493L;

	private final int playerId;

	public PlayerQuitPaket(Player player) {
		this.playerId = player.getId();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Player player = carcassonneService.lobbyService().getPlayer(playerId);
		
		carcassonneService.lobbyService().getLobby().getPlayers().remove(player);
		
	}

}
