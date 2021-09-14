/*
 * sends a paket for all other players to display a player getting kicked
 */

package de.btu.swp.carcassonne.game.paket.lobby;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class PlayerKickPaket implements ServerPaket{

	private final int playerId;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2546400046862950106L;

	public PlayerKickPaket(Player p) {
		this.playerId = p.getId();
	}
	
	@Override
	public int getPlayerId() {
		return this.playerId;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {

		Player player = carcassonneService.lobbyService().getPlayer(playerId);
			
		carcassonneService.lobbyService().getLobby().getPlayers().remove(player);
	
		carcassonneService.lobbyService().updateColorList(player.getColor());
	}

}
