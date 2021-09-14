package de.btu.swp.carcassonne.network.client;

import de.btu.swp.carcassonne.network.Paket;

public interface ClientListener {

	/**
	 * Will be called after the Client has disconnected for any reason from the
	 * other device
	 */
	public void onDisconnect();

	/**
	 * Will be called after the Client receives a Paket
	 * 
	 * @param paket which was received
	 */
	public void onPaketReceived(Paket paket);

	/**
	 * Will be called after the Client send a Paket
	 * 
	 * @param paket which was send
	 */
	public void onPaketSend(Paket paket);

}
