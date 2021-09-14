/*
 * listener for the pakets from clients
 */
package de.btu.swp.carcassonne.game.listener;

import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.ClientListener;
import javafx.application.Platform;

public class ClientPaketListener extends Service implements ClientListener {
	
	public ClientPaketListener(CarcassonneService carcassonneService) {
		setCarcassonneService(carcassonneService);
	}

	@Override
	public void onDisconnect() {

		Platform.runLater(() -> {
			System.out.println("testi");
			carcassonneService().gameService().stopGame();
		});
		
	}

	@Override
	public void onPaketReceived(Paket paket) {

		System.out.println("Paket: " + paket.getName());
		
		Platform.runLater(() -> {
			paket.executeChange(carcassonneService());
		});
		
	}

	@Override
	public void onPaketSend(Paket paket) {
		
	}

}
