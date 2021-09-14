/*
 * sends a packet that ends the game when all cards are gone
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class GameEndPaket implements ServerPaket {

	private static final long serialVersionUID = 825540726786738549L;

	@Override
	public int getPlayerId() {
		return 0;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		carcassonneService.gameService().scoreService().scoreGame();
	
	}

}
