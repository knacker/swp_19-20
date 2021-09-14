/*
 * being used by the lobby, a colorchangepaket contains a playerid and a color. sends a paket if a player changes his color
 */
package de.btu.swp.carcassonne.game.paket.lobby;

import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.LobbyPaket;

public class ColorChangePaket implements LobbyPaket {

	private static final long serialVersionUID = 2120548765049310739L;

	private final int playerId;
	private final Color color;

	public ColorChangePaket(Player player) {
		this.playerId = player.getId();
		this.color = player.getColor();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Player player = carcassonneService.lobbyService().getPlayer(playerId);
		player.setColor(color);
		carcassonneService.lobbyService().updateColorList(color);
	}

}
