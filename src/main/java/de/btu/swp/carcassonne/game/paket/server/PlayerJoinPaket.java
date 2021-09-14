/*
 * a paket that gets sent if a player joints the server
 */

package de.btu.swp.carcassonne.game.paket.server;

import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerData;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class PlayerJoinPaket implements ServerPaket {

	private static final long serialVersionUID = -1525473064778435880L;
	
	private final PlayerData data;

	public PlayerJoinPaket(Player player) {
		data = player.toPlayerData();
	}

	@Override
	public int getPlayerId() {
		return data.getId();
	}

	public String getUsername() {
		return data.getUsername();
	}

	public Color getColor() {
		return data.getColor();
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Player player = data.toPlayer();
		
		carcassonneService.lobbyService().getLobby().getPlayers().add(player);
		
		carcassonneService.lobbyService().updateColorList(player.getColor());
	}
}
