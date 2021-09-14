package de.btu.swp.carcassonne.network.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

import de.btu.swp.carcassonne.network.Connection;
import de.btu.swp.carcassonne.network.Paket;

/**
 * The Client connects to the server for paket exchange
 */
public class Client implements Connection {

	private final SocketChannel socket;
	private final HashSet<ClientListener> listener = new HashSet<>();
	private final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);

	private Thread listenForPaketThread;

	/**
	 * Creates a Client which connects to a Server
	 * 
	 * @param address Adress of the Server
	 * @param port    Port of the Server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(InetAddress address, int port) throws UnknownHostException, IOException {
		this(SocketChannel.open(new InetSocketAddress(address, port)), true);
	}

	/**
	 * Creates a Client which connects to a Server
	 * 
	 * @param address Adress of the Server
	 * @param port    Port of the Server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String address, int port) throws UnknownHostException, IOException {
		this(SocketChannel.open(new InetSocketAddress(address, port)), true);
	}

	/**
	 * Creates a Client which is connected to another device
	 * 
	 * @param socket Connection used
	 * @throws IOException
	 */
	public Client(SocketChannel socket, boolean block) throws IOException {
		this.socket = socket;
		socket.configureBlocking(block);
	}

	/**
	 * Sends an Paket to the underlying connection<br>
	 * Afterwards all ClientListeners will be called with the onPaketSend method
	 * 
	 * @param paket Paket to send
	 * @throws IOException
	 */
	@Override
	public void sendPaket(Paket paket) throws IOException {
		if (paket == null) {
			throw new IllegalArgumentException("Paket can not be null");
		}
		if (!isConnected()) {
			return;
		}

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < 4; i++)
			baos.write(0);

		final ObjectOutputStream oos = new ObjectOutputStream(baos);

		oos.writeObject(paket);
		oos.close();

		final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
		wrap.putInt(0, baos.size() - 4);
		socket.write(wrap);

		baos.close();

		for (ClientListener l : listener) {
			l.onPaketSend(paket);
		}
	}

	/**
	 * Starts the Thread which listens for pakets. This pakets will then be
	 * redirected to the ClientListeners onPaketReceived method
	 */
	public void start() {
		if (listenForPaketThread == null) {
			listenForPaketThread = new Thread(new ClientListenForPaketRunnable(this), "ClientListenForPaketThread");
			listenForPaketThread.start();
		}
	}

	/**
	 * Closes ObjectStreams and the underlying connection
	 * 
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		socket.close();

		for (ClientListener l : listener) {
			l.onDisconnect();
		}
	}

	/**
	 * Reads the next Paket from the ObjectInputStream (null if no paket received)
	 * 
	 * @return paket or null
	 * @throws IOException
	 */
	public Paket listenForPaket() throws IOException {
		if (socket.read(lengthByteBuffer) == -1) {
			throw new AsynchronousCloseException();
		}
		if (lengthByteBuffer.remaining() == 0) {
			final ByteBuffer dataByteBuffer = ByteBuffer.allocate(lengthByteBuffer.getInt(0));
			lengthByteBuffer.clear();

			socket.read(dataByteBuffer);
			if (dataByteBuffer.remaining() == 0) {
				try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dataByteBuffer.array()))) {

					return (Paket) ois.readObject();
				} catch (ClassNotFoundException | ClassCastException e) {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Add a listener which receives events
	 * 
	 * @param listener to be added
	 */
	public void addListener(ClientListener l) {
		listener.add(l);
	}

	/**
	 * Remove a listener which should not received any events anymore
	 * 
	 * @param listener to be removed
	 */
	public void removeListener(ClientListener l) {
		listener.remove(l);
	}

	/**
	 * Get all listeners which are currently registered
	 * 
	 * @return listeners
	 */
	public Set<ClientListener> getListener() {
		return listener;
	}

	/**
	 * Is the underlying connection still connected?
	 * 
	 * @return is conneceted
	 */
	public boolean isConnected() {
		return socket.isConnected();
	}

	public SocketChannel getChannel() {
		return socket;
	}

}
