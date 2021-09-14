/*
 * Holds the data of the Fields, which consist of x and y coordinates, aswell as a card.
 */
package de.btu.swp.carcassonne.data.card;

public class Field {

	private Card card;
	private final int x;
	private final int y;

	public Field(int x, int y, Card card) {
		this.x = x;
		this.y = y;
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "{x=" + x + ", y=" + y + ", card=" + card + "}";
	}

	public boolean isPlaceholder() {
		return card.isPlaceholder();
	}
	
}
