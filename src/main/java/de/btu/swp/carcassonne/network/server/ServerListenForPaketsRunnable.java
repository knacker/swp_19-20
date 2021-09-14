package de.btu.swp.carcassonne.network.server;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;

/**
 * Runnable for the ServerListenForPaketsThread<br>
 * Listens for all new incoming pakets and calls all EventHandler from the
 * Server
 */
class ServerListenForPaketsRunnable implements Runnable {

	private final Server server;

	ServerListenForPaketsRunnable(Server server) {
		this.server = server;
	}

	@Override
	public void run() {

		while (server.isConnected()) {
			for (Client client : server.getClients().keySet()) {
				Paket paket = null;
				try {
					paket = client.listenForPaket();
				} catch (IOException e) {
					Player player = server.getClients().remove(client);

					for (ServerListener l : server.getListener()) {
						l.onDisconnect(client, player);
					}

					if (!(e instanceof AsynchronousCloseException) && !(e instanceof SocketException)
							&& !(e instanceof ClosedChannelException)) {
						e.printStackTrace();
					}
				}

				if (paket != null) {

					boolean resend = false;

					Player player = server.getClients().get(client);

					for (ServerListener l : server.getListener()) {
						resend = resend || l.onPaketReceived(client, player, paket);
					}

					if (resend) {
						server.sendPaket(client, paket);
					}
				}
			}
		}
	}

}
