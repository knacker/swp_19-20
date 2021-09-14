/*
 * Describes the Structure of a card using enums.
 */
package de.btu.swp.carcassonne.data.graph;

public enum Structure {
	
	STREET("Straße"),
	TOWN("Stadt"),
	FIELD("Wiese"),
	MONAS("Kloster");
	
	private final String toString;
	
	private Structure(String toString) {
		this.toString = toString;
	}
	
	@Override
	public String toString() {
		return toString;
	}
}
