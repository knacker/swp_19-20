package de.btu.swp.carcassonne.network.server;

import java.io.IOException;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.network.client.Client;

public interface ServerListener {

	/**
	 * Before a Client is allowed to connect to the server this method will be
	 * called<br>
	 * If on listeners return false the connection will not be established
	 * 
	 * @param client Client which connected
	 * @return is allowed to connect
	 */
	public boolean onConnect(Client client, Player player);

	/**
	 * After a Client disonnected for any reason this method will be called
	 * 
	 * @param client Client which disconnected
	 * @throws IOException
	 */
	public void onDisconnect(Client client, Player player);

	/**
	 * After the Server received a Paket this method will be called<br>
	 * It can then be determinated if the Paket should be send to all clients which
	 * are connected to the server. If one listener returns true it will be resend
	 * 
	 * @param client
	 * @param paket
	 * @return should the paket be send to all other clients
	 */
	public boolean onPaketReceived(Client client, Player player, Paket paket);

	/**
	 * After the Server send a Paket to all clients this method will be called
	 * 
	 * @param paket
	 */
	public void onPaketSend(Paket paket);

}
