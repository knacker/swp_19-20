/*
 * paket that changes the playertype, when a player leaves and gets replaced by a bot
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class PlayerTypeChangePaket implements ServerPaket {

	private static final long serialVersionUID = -4160216671728536133L;

	private final int playerId;
	private final PlayerType type;
	
	public PlayerTypeChangePaket(Player player) {
		playerId = player.getId();
		type = player.getType();
	}
	
	@Override
	public int getPlayerId() {
		return playerId;
	}
	
	public PlayerType getType() {
		return type;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Player player = carcassonneService.lobbyService().getPlayer(playerId);
		
		player.setType(type);
	}

}
