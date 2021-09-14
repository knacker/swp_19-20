package de.btu.swp.carcassonne.network;

import java.io.IOException;

public interface Connection {

	/**
	 * Sends an Paket to the underlying connections<br>
	 * Afterwards all Listeners will be called with the onPaketSend method
	 * 
	 * @param paket Paket to send
	 * @throws IOException
	 */
	public void sendPaket(Paket paket) throws IOException;

	/**
	 * Closes the connection and makes it unavailable
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * Starts the connection and all listeners which are needed
	 */
	public void start() throws IOException;

	/**
	 * Determines if the connection is available for communication
	 * 
	 * @return is connection available
	 */
	public boolean isConnected();
}
