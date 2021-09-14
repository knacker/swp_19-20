package de.btu.swp.carcassonne.network;

import java.io.Serializable;

import de.btu.swp.carcassonne.game.CarcassonneService;

/**
 * This class is serializable and is used to identify pakets/objects from the project.
 */
public interface Paket extends Serializable {

	/**
	 * Returns the name of the Paket by default the simple name of the class
	 * @return Paket name
	 */
	default String getName() {
		return getClass().getSimpleName();
	}
	
	/**
	 * Returens the player who sent the paket
	 * @return sending player id
	 */
	public int getPlayerId();
	
	/**
	 * 
	 * @param carcassonneService
	 */
	public void executeChange(CarcassonneService carcassonneService);
	
}
