package de.btu.swp.carcassonne.util;

import java.util.LinkedList;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.graph.Graph;
import de.btu.swp.carcassonne.data.graph.Structure;
import de.btu.swp.carcassonne.data.graph.Vector;

/**
 * Includes all Cards of the base game. 
 */
public class CardUtil {

	/**
	 * creates the start card of the game(Type D)
	 */
	public static Card createStartCard() {
		return createCardD(3);
	}

	/**
	 * creates a list of all cards
	 */
	public static LinkedList<Card> createAllCards() {

		LinkedList<Card> cards = new LinkedList<>();

		// 2xA
		for (int i = 0; i < 2; i++) {
			Card c = createCardA(i);
			cards.add(c);
		}

		// 4xB
		for (int i = 0; i < 4; i++) {
			Card c = createCardB(i);
			cards.add(c);
			
		}

		// 1xC
		for (int i = 0; i < 1; i++) {
			Card c = createCardC(i);
			cards.add(c);
		}

		// 3xD + StartCard is another method
		for (int i = 0; i < 3; i++) {
			Card c = createCardD(i);
			cards.add(c);
		}

		// 5xE
		for (int i = 0; i < 5; i++) {
			Card c = createCardE(i);
			cards.add(c);
		}

		// 2xF
		for (int i = 0; i < 2; i++) {
			Card c = createCardF(i);
			cards.add(c);
		}

		// 1xG
		for (int i = 0; i < 1; i++) {
			Card c = createCardG(i);
			cards.add(c);
		}

		// 3xH
		for (int i = 0; i < 3; i++) {
			Card c = createCardH(i);
			cards.add(c);
		}

		// 2xI
		for (int i = 0; i < 2; i++) {
			Card c = createCardI(i);
			cards.add(c);
		}

		// 3xJ
		for (int i = 0; i < 3; i++) {
			Card c = createCardJ(i);
			cards.add(c);
		}

		// 3xK
		for (int i = 0; i < 3; i++) {
			Card c = createCardK(i);
			cards.add(c);
		}

		// 3xL
		for (int i = 0; i < 3; i++) {
			Card c = createCardL(i);
			cards.add(c);
		}

		// 2xM
		for (int i = 0; i < 2; i++) {
			Card c = createCardM(i);
			cards.add(c);
		}

		// 3xN
		for (int i = 0; i < 3; i++) {
			Card c = createCardN(i);
			cards.add(c);
		}

		// 2xO
		for (int i = 0; i < 2; i++) {
			Card c = createCardO(i);
			cards.add(c);
		}

		// 3xP
		for (int i = 0; i < 3; i++) {
			Card c = createCardP(i);
			cards.add(c);
		}

		// 1xQ
		for (int i = 0; i < 1; i++) {
			Card c = createCardQ(i);
			cards.add(c);
		}

		// 3xR
		for (int i = 0; i < 3; i++) {
			Card c = createCardR(i);
			cards.add(c);
		}

		// 2xS
		for (int i = 0; i < 2; i++) {
			Card c = createCardS(i);
			cards.add(c);
		}

		// 1xT
		for (int i = 0; i < 1; i++) {
			Card c = createCardT(i);
			cards.add(c);
		}

		// 8xU
		for (int i = 0; i < 8; i++) {
			Card c = createCardU(i);
			cards.add(c);
		}

		// 9xV
		for (int i = 0; i < 9; i++) {
			Card c = createCardV(i);
			cards.add(c);
		}

		// 4xW
		for (int i = 0; i < 4; i++) {
			Card c = createCardW(i);
			cards.add(c);
		}

		// 1xX
		for (int i = 0; i < 1; i++) {
			Card c = createCardX(i);
			cards.add(c);
		}
		
		return cards;

		
	}

	/*
	 * each card type uses it's own graph. A graph consists of Vectors, which
	 * describe the structures on the card itself. A Card is created using an id and
	 * a Path of the card.
	 * 
	 * @param int id
	 * 
	 * @return Card
	 */
	private static Card createCardA(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.STREET, 1, true);

		Vector monas = new Vector(Structure.MONAS, 1, true);

		Graph graph = new Graph(north, east, south, west, monas);
		Card card = new Card("A-" + id, graph);

		return card;

	}

	private static Card createCardB(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector monas = new Vector(Structure.MONAS, 1, true);

		Graph graph = new Graph(north, east, south, west, monas);
		Card card = new Card("B-" + id, graph);

		return card;
	}

	private static Card createCardC(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.TOWN, 0, false);

		Vector inner = new Vector(Structure.TOWN, 2, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(south);
		south.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("C-" + id, graph);

		return card;
	}

	private static Card createCardD(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.STREET, 0, false);
		Vector west = new Vector(Structure.STREET, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.STREET, 1, false);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("D-" + id, graph);

		return card;
	}

	private static Card createCardE(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("E-" + id, graph);

		return card;
	}

	private static Card createCardF(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 2, false);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("F-" + id, graph);

		return card;
	}

	private static Card createCardG(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 1, false);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("G-" + id, graph);

		return card;
	}

	private static Card createCardH(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.TOWN, 1, true);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("H-" + id, graph);

		return card;
	}

	private static Card createCardI(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.TOWN, 1, true);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("I-" + id, graph);

		return card;
	}

	private static Card createCardJ(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.STREET, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.STREET, 1, false);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(south);
		south.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("J-" + id, graph);

		return card;
	}

	private static Card createCardK(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);
		Vector west = new Vector(Structure.STREET, 0, false);

		Vector inner = new Vector(Structure.STREET, 1, false);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(south);
		south.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("K-" + id, graph);

		return card;
	}

	// TODO maybe?
	private static Card createCardL(int id) {

		Vector north = new Vector(Structure.TOWN, 1, true);
		Vector east = new Vector(Structure.STREET, 1, true);
		Vector south = new Vector(Structure.STREET, 1, true);
		Vector west = new Vector(Structure.STREET, 1, true);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("L-" + id, graph);

		return card;

	}

	// TODO maybe?
	private static Card createCardM(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 2, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("M-" + id, graph);

		return card;
	}

	private static Card createCardN(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 1, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("N-" + id, graph);

		return card;
	}

	private static Card createCardO(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.STREET, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);

		Vector innerTown = new Vector(Structure.TOWN, 2, false);
		Vector innerStreet = new Vector(Structure.STREET, 1, false);

		innerTown.getConnections().add(north);
		north.getConnections().add(innerTown);

		innerTown.getConnections().add(west);
		west.getConnections().add(innerTown);

		innerStreet.getConnections().add(east);
		east.getConnections().add(innerStreet);

		innerStreet.getConnections().add(south);
		south.getConnections().add(innerStreet);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("O-" + id, graph);

		return card;
	}

	private static Card createCardP(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.STREET, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);

		Vector innerTown = new Vector(Structure.TOWN, 1, false);
		Vector innerStreet = new Vector(Structure.STREET, 1, false);

		innerTown.getConnections().add(north);
		north.getConnections().add(innerTown);

		innerTown.getConnections().add(west);
		west.getConnections().add(innerTown);

		innerStreet.getConnections().add(east);
		east.getConnections().add(innerStreet);

		innerStreet.getConnections().add(south);
		south.getConnections().add(innerStreet);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("P-" + id, graph);

		return card;
	}

	private static Card createCardQ(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 2, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("Q-" + id, graph);

		return card;
	}

	private static Card createCardR(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.FIELD, 0, false);

		Vector inner = new Vector(Structure.TOWN, 1, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("R-" + id, graph);

		return card;
	}

	private static Card createCardS(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.STREET, 1, true);

		Vector inner = new Vector(Structure.TOWN, 2, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("S-" + id, graph);

		return card;
	}

	private static Card createCardT(int id) {

		Vector north = new Vector(Structure.TOWN, 0, false);
		Vector east = new Vector(Structure.TOWN, 0, false);
		Vector west = new Vector(Structure.TOWN, 0, false);
		Vector south = new Vector(Structure.STREET, 1, true);

		Vector inner = new Vector(Structure.TOWN, 1, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(east);
		east.getConnections().add(inner);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("T-" + id, graph);

		return card;
	}

	private static Card createCardU(int id) {

		Vector north = new Vector(Structure.STREET, 0, false);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.FIELD, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);

		Vector inner = new Vector(Structure.STREET, 1, false);

		inner.getConnections().add(north);
		north.getConnections().add(inner);

		inner.getConnections().add(south);
		south.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("U-" + id, graph);

		return card;
	}

	private static Card createCardV(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.FIELD, 0, false);
		Vector west = new Vector(Structure.STREET, 0, false);
		Vector south = new Vector(Structure.STREET, 0, false);

		Vector inner = new Vector(Structure.STREET, 1, false);

		inner.getConnections().add(west);
		west.getConnections().add(inner);

		inner.getConnections().add(south);
		south.getConnections().add(inner);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("V-" + id, graph);

		return card;
	}

	private static Card createCardW(int id) {

		Vector north = new Vector(Structure.FIELD, 0, false);
		Vector east = new Vector(Structure.STREET, 1, true);
		Vector south = new Vector(Structure.STREET, 1, true);
		Vector west = new Vector(Structure.STREET, 1, true);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("W-" + id, graph);

		return card;

	}

	private static Card createCardX(int id) {

		Vector north = new Vector(Structure.STREET, 1, true);
		Vector east = new Vector(Structure.STREET, 1, true);
		Vector south = new Vector(Structure.STREET, 1, true);
		Vector west = new Vector(Structure.STREET, 1, true);

		Graph graph = new Graph(north, east, south, west);
		Card card = new Card("X-" + id, graph);

		return card;
	}
}
