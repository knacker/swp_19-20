/*
 * @author Maximilian Raspe
 */
package de.btu.swp.carcassonne.game.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.listener.ChatServerListener;
import de.btu.swp.carcassonne.game.listener.ClientPaketListener;
import de.btu.swp.carcassonne.game.listener.GameServerListener;
import de.btu.swp.carcassonne.game.listener.LobbyServerListener;
import de.btu.swp.carcassonne.game.paket.GamePaket;
import de.btu.swp.carcassonne.game.paket.LobbyPaket;
import de.btu.swp.carcassonne.game.paket.ServerPaket;
import de.btu.swp.carcassonne.game.paket.game.PlayerTypeChangePaket;
import de.btu.swp.carcassonne.game.paket.lobby.PlayerKickPaket;
import de.btu.swp.carcassonne.network.Connection;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;
import de.btu.swp.carcassonne.network.server.Server;
import de.btu.swp.carcassonne.util.GameidUtil;
import javafx.util.Pair;

/**
 * Manager for all server and client related things
 */
public class ConnectionService extends Service {

	private Connection connection;

	/**
	 * checks, if a server is already running, and if not, starts a new server.
	 * checks for free ports and starts the server if a free port has been found
	 */
	public void loadServer() {

		if (isConnected()) {
			throw new IllegalStateException("ConnectionService is already connected");
		}

		if (connection != null && !(connection instanceof Server) && ((Server) connection).isConnected()) {
			throw new IllegalStateException("Server is already running");
		}

		Server server = new Server(6060);

		server.addListener(new LobbyServerListener(carcassonneService()));
		server.addListener(new GameServerListener(carcassonneService()));
		server.addListener(new ChatServerListener(carcassonneService()));

		// Find Port
		for (int port = 6060; port < 7000; port++) {
			server.setPort(port);
			try {
				server.start();
				break;
			} catch (IOException e) {
			}
		}

		if (!server.isConnected()) {
			throw new IllegalStateException("No Port available between 6060 and 7000");
		}

		connection = server;

		lobbyService().setLocalPlayer(new Player(1));
		gameService().getGame().setState(GameState.LOBBY);

		try {
			System.out.println("ConnectionService has created a server on "
					+ InetAddress.getLocalHost().getHostAddress() + ":" + server.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * checks if a connection is present, and if so, it closes the server
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {

		if (isConnected()) {
			connection.close();
			connection = null;

			System.out.println("ConnectionService has closed the connection");
		}
	}

	/**
	 * Takes an address and a port of the User that wants to connection, and creates
	 * a new Client
	 * 
	 * @param IP of type InetAddress
	 * 
	 * @param port of type Integer
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	public void connectToServer(String gameid) {
		
		try {
			Pair<InetAddress, Integer> address;
			if(gameid == null || gameid.isBlank()) {
				address = new Pair<>(InetAddress.getLocalHost(), 6060);
			} else {
				address = GameidUtil.hexToIP(gameid);
			}
			connectToServer(address.getKey(), address.getValue());
		} catch (Exception e) {
			carcassonneService().setError("Ungültige Gameid");
		}
		
	}

	/**
	 * Takes an address and a port of the User that wants to connection, and creates
	 * a new Client
	 * 
	 * @param IP of type InetAddress
	 * 
	 * @param port of type Integer
	 * 
	 * @throws UnknownHostException
	 * 
	 * @throws IOException
	 */
	private void connectToServer(InetAddress address, int port) throws UnknownHostException, IOException {

		if (isConnected()) {
			throw new IllegalStateException("ConnectionService is already connected");
		}

		Client client = new Client(address, port);

		client.addListener(new ClientPaketListener(carcassonneService()));

		client.start();

		System.out.println("ConnectionService has connected to " + address.getHostAddress() + ":" + port);

		connection = client;
	}

	/**
	 * Sends a packet
	 * 
	 * @param Paket
	 * 
	 * @throws IOException
	 */
	public void sendPaket(Paket paket) throws IOException {

		if (!isConnected()) {
			throw new IllegalStateException("ConnectionService is not connected");
		}
		
		if(!isServer()) {

			// ServerPakets should only be send by the Server
			if (paket instanceof ServerPaket) {
				return;
			}

			// LobbyPakets and GamePakets should only be send by the corresponding Player
			if (paket instanceof LobbyPaket || paket instanceof GamePaket) {

				Player player = lobbyService().getLocalPlayer();

				if (player.getId() != paket.getPlayerId()) {
					return;
				}
			}

			// GamePakets should ony be send if it is the turn of the player
			if (paket instanceof GamePaket) {

				Player player = gameService().getGame().getPlayerOnTurn();

				if (player.getId() != paket.getPlayerId()) {
					
					return;
				}
			}
		} else if((paket instanceof PlayerKickPaket || paket instanceof PlayerTypeChangePaket) && paket.getPlayerId() > 0) {	
			
			getServer().kickPlayer(paket.getPlayerId());
		}

		connection.sendPaket(paket);
	}

	/**
	 * checks, if a connection is available for usage
	 */
	public boolean isConnected() {
		return connection != null && connection.isConnected();
	}

	/**
	 * checks if the connection is available and tries to cast it to a server
	 * 
	 * @return server or null
	 * @throws ClassCastException
	 */
	private Server getServer() {
		return isConnected() ? (Server) connection : null;
	}

	/**
	 * checks if the connection is available and tries to cast it to a client
	 * 
	 * @return client or null
	 * @throws ClassCastException
	 */
	private Client getClient() {
		return isConnected() ? (Client) connection : null;
	}

	/**
	 * checks if the connection is available and if this connection is server
	 * 
	 * @return is connection a server
	 */
	public boolean isServer() {
		return isConnected() && connection instanceof Server;
	}

	/**
	 * checks if the connection is available and if this connection is client
	 * 
	 * @return is connection a client
	 */
	public boolean isClient() {
		return isConnected() && connection instanceof Client;
	}

	/**
	 * represents connection as gameid with which can be connected to the server
	 * @return gameid
	 * @throws UnknownHostException
	 */
	public String getGameid() throws UnknownHostException {
		
		if(isServer()) {

			return GameidUtil.ipToHex(InetAddress.getLocalHost(), getServer().getPort());
		} else if(isClient()) {
			try {
				InetSocketAddress address = (InetSocketAddress) getClient().getChannel().getRemoteAddress();

				return GameidUtil.ipToHex(address.getAddress(), address.getPort());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
