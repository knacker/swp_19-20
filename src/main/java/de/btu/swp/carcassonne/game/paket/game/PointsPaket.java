/*
 * sends a paket to players that calculates the points for each client when a players gets a point
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.GamePaket;

public class PointsPaket implements GamePaket  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7979992828058827105L;
	
	private final int playerId;

	public PointsPaket(Player p) {
		this.playerId = p.getId();
		
	}
	@Override
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		carcassonneService.gameService().scoreService().calculatePoints();
		
	}

}
