/*
 * holds the data of the lobby, including players, timeout and the ingamehelp
 */
package de.btu.swp.carcassonne.data;

import de.btu.swp.carcassonne.data.player.Player;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Lobby {

	private final ListProperty<Player> players = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty timeout = new SimpleIntegerProperty();
	private final BooleanProperty helpActive = new SimpleBooleanProperty();
	
	/*
	 * sets timeout timer
	 * @param time
	 */
	public void setTimeout(int time) {
		timeout.set(time);
	}
	/*
	 * returns timeout timer
	 * @return timeout
	 */
	public int getTimeout() {
		return timeout.get();
	}
	
	public IntegerProperty timeoutProperty() {
		return timeout;
	}
	/*
	 * @return list of players
	 */
	public ListProperty<Player> getPlayers() {
		return players;
	}

	public boolean isHelpActive() {
		return helpActive.get();
	}

	public void setHelpActive(boolean b) {
		helpActive.set(b);
	}

}
