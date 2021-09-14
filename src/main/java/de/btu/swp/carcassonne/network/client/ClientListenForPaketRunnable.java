package de.btu.swp.carcassonne.network.client;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.AsynchronousCloseException;

import de.btu.swp.carcassonne.network.Paket;

/**
 * Runnable for the ClientListenForPaketThread<br>
 * Listens for all new incoming pakets and calls all EventHandler from the
 * Client
 */
class ClientListenForPaketRunnable implements Runnable {

	private final Client client;

	ClientListenForPaketRunnable(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (client.isConnected()) {
			try {
				// System.out.println("Client waiting for Paket...");
				Paket p = client.listenForPaket();

				if (p != null) {
					for (ClientListener l : client.getListener()) {
						l.onPaketReceived(p);
					}
				}
			} catch (AsynchronousCloseException | SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
