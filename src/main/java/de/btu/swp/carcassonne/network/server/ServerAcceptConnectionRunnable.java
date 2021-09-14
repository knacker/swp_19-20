package de.btu.swp.carcassonne.network.server;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.network.client.Client;

/**
 * Runnable for the ServerAcceptConnectionThread<br>
 * Incoming connections are validated and are called to all listeners of the
 * Server
 */
class ServerAcceptConnectionRunnable implements Runnable {

	private static int GLOBAL_PLAYER_ID = 1;

	private final Server server;

	ServerAcceptConnectionRunnable(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		while (server.isConnected()) {

			try {
				SocketChannel s = server.getChannel().accept();

				Client c = new Client(s, false);
				Player p = new Player(++GLOBAL_PLAYER_ID);

				boolean allowed = true;

				for (ServerListener l : server.getListener()) {
					allowed = allowed && l.onConnect(c, p);
				}

				if (allowed) {
					server.getClients().put(c, p);
				} else {
					c.close();
				}
			} catch (AsynchronousCloseException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}
	}
}
