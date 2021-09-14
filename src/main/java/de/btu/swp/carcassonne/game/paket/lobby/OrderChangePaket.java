/*
 * swaps positions of players when a server host wants to. sends a paket with indices +1 or -1
 */

package de.btu.swp.carcassonne.game.paket.lobby;

import java.util.Collections;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;
import javafx.beans.property.ListProperty;

public class OrderChangePaket implements ServerPaket {

	private static final long serialVersionUID = -4586421817056647856L;
	
	private int playerId;
	private int indexA;
	private int indexB;

	public OrderChangePaket(Player player, int indexA, int indexB) {
		this.playerId = player.getId();
		this.indexA = indexA;
		this.indexB = indexB;
	}
	@Override
	public int getPlayerId() {
		return this.playerId;
	}
	
	public int getIndexA() {
		return indexA;
	}
	
	public int getIndexB() {
		return indexB;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		ListProperty<Player> players = carcassonneService.lobbyService().getLobby().getPlayers();
		
		Collections.swap(players, indexA, indexB);
	}

}
