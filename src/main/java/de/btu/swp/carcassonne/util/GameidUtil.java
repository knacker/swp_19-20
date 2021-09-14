package de.btu.swp.carcassonne.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.util.Pair;

public class GameidUtil {

	/*
	 * @author Maximilian Raspe
	 * 
	 * @param InetAdress address
	 * 
	 * @param Integer port
	 * 
	 * @return hexString Nimmt eine addresse und einen port entgegen. Erstellt aus
	 * Inet Adresse einen byte array, der dann in sub bytes aufgeteilt wird. Dann
	 * wird die Hex zahl berechnet. Ein HexString wird zurückgegeben.
	 */
	public static String ipToHex(InetAddress address, int port) throws UnknownHostException {

		byte[] numbers = address.getAddress(); // [127,0,0,1]

		int sub1 = numbers[0];
		int sub2 = numbers[1];
		int sub3 = numbers[2];
		int sub4 = numbers[3];

		long hex = (long) (Long.parseLong(Integer.toString(sub1 & 0xff)) * (Math.pow(256, 3))
				+ Long.parseLong(Integer.toString(sub2 & 0xff)) * (Math.pow(256, 2))
				+ Long.parseLong(Integer.toString(sub3 & 0xff)) * (Math.pow(256, 1))
				+ Long.parseLong(Integer.toString(sub4 & 0xff)) * (Math.pow(256, 0)));
		
		String hexString = Long.toHexString(hex) + Integer.toHexString(port);

		return hexString;

	}

	/*
	 * @author Maximilian Raspe
	 * 
	 * @param hexValue
	 * 
	 * @return Ein tupel von IP und dem Port
	 * 
	 * Es wird ein Byte Array der Länge 4 erstellt, in dem die IP Adresse
	 * abgespeichert werden soll. Da die Hexadezimaldarstellung einer IP immer aus 8
	 * zeichen besteht, kann man alle 2 stelligen Werte auslesen als jeweiligen Teil
	 * der IP. Der Port wird Seperat am Ende ausgelesen.
	 * 
	 */
	public static Pair<InetAddress, Integer> hexToIP(String hexValue) throws UnknownHostException {

		byte[] address = new byte[4];

		for (int i = 0; i < 4; i++) {
			
			String sub = hexValue.substring(2*i, 2*i + 2);
			
			if (Integer.valueOf(sub, 16) > 127) {
				int value = Integer.valueOf(sub, 16);
				value = -value;
				address[i] = (byte) -value;
			} else
				address[i] = Byte.valueOf(sub, 16);

		}

		int port = Integer.parseInt(hexValue.substring(8), 16);

		return new Pair<InetAddress, Integer>(InetAddress.getByAddress(address), port);
	}

}
