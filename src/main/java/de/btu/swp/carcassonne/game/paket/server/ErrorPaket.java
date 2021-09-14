/*
 * sends a paket to all players if a error occurs
 */

package de.btu.swp.carcassonne.game.paket.server;

import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;

public class ErrorPaket implements ServerPaket {

	private static final long serialVersionUID = -1722049862703733193L;

	private final String message;
	
	public ErrorPaket(String message) {
		this.message = message;
	}
	
	@Override
	public int getPlayerId() {
		return 0;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		carcassonneService.setError(message);
		
	}

}
