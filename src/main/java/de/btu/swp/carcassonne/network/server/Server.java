package de.btu.swp.carcassonne.network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.network.Connection;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;

/**
 * Allows Connections from Clients to the Server
 */
public class Server implements Connection {

	private final Map<Client, Player> clients = new ConcurrentHashMap<>();
	private final Set<ServerListener> listener = new HashSet<>();

	private int port;
	private ServerSocketChannel channel;

	private Thread listenForPaketThread, acceptConnectionThread;

	/**
	 * Creates a Server
	 * 
	 * @param port Port to be started on
	 */
	public Server(int port) {
		this.port = port;
	}

	/**
	 * After the restart of the Server the new port will be used
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the port which is currently in use.
	 * 
	 * @return port in use
	 */
	public int getPort() {
		return isConnected() ? channel.socket().getLocalPort() : port;
	}

	/*
	 * Get the localHost IP
	 * 
	 * @return ip in use
	 */
	public InetAddress getIP() throws UnknownHostException {
		return InetAddress.getLocalHost();
	}

	/**
	 * Get all Clients which are collected to the Server
	 * 
	 * @return clients
	 */
	public Map<Client, Player> getClients() {
		return clients;
	}

	/**
	 * Get all ServerListeners which are listening for events
	 * 
	 * @return listeners
	 */
	public Set<ServerListener> getListener() {
		return listener;
	}

	/**
	 * Add ServerListener which listens for events
	 * 
	 * @param listener ServerListener to add
	 */
	public void addListener(ServerListener l) {
		listener.add(l);
	}

	/**
	 * Remove ServerListener which listens for events
	 * 
	 * @param listener ServerListener to remove
	 */
	public void removeListener(ServerListener l) {
		listener.remove(l);
	}

	/**
	 * Send a Paket to all connected Clients<br>
	 * Afterwards all listener are called with the onPaketReceived method
	 * 
	 * @param paket Paket to send
	 * @throws IOException
	 */
	@Override
	public void sendPaket(Paket paket) {
		sendPaket(null, paket);
	}

	/**
	 * Send a Paket to all connected Clients, except for a given Client<br>
	 * Afterwards all listener are called with the onPaketReceived method
	 * 
	 * @param paket Paket to send
	 * @throws IOException
	 */
	public void sendPaket(Client client, Paket paket) {
		if (paket == null) {
			throw new IllegalArgumentException("Paket can not be null");
		}
		for (Client c : clients.keySet()) {
			if (c == client) {
				continue;
			}
			try {
				c.sendPaket(paket);
			} catch (IOException e) {

				Player p = clients.remove(c);
				try {
					c.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				for (ServerListener l : listener) {
					l.onDisconnect(c, p);
				}
			}
		}
		for (ServerListener l : listener) {
			l.onPaketSend(paket);
		}
	}

	/**
	 * Starts the Server on the currently given port.
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		if (isConnected()) {
			throw new RuntimeException("The Server is already running");
		}

		channel = ServerSocketChannel.open();
		channel.socket().bind(new InetSocketAddress(port));

		if (listenForPaketThread == null) {
			listenForPaketThread = new Thread(new ServerListenForPaketsRunnable(this), "ServerListForPaketsThread");
			listenForPaketThread.start();
		}
		if (acceptConnectionThread == null) {
			acceptConnectionThread = new Thread(new ServerAcceptConnectionRunnable(this),
					"ServerAcceptConnectionThread");
			acceptConnectionThread.start();
		}
	}

	/**
	 * Stops the Server and closes all ongoing connections and ObjectStreams
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (!isConnected()) {
			throw new RuntimeException("Server is already closed");
		}
		for (Client c : clients.keySet()) {
			clients.remove(c);
			c.close();
		}
		channel.close();
		channel = null;
	}

	/**
	 * Determinate if the server is running
	 * 
	 * @return is the server running
	 */
	@Override
	public boolean isConnected() {
		return channel != null && channel.isOpen();
	}

	/**
	 * Get underlying ServerSocket
	 * 
	 * @return serversocket
	 */
	public ServerSocketChannel getChannel() {
		return channel;
	}

	public void kickPlayer(int playerId) {
		for(Entry<Client, Player> entry : clients.entrySet()) {
			if(entry.getValue().getId() == playerId) {
				clients.remove(entry.getKey());
				try {
					entry.getKey().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
}
