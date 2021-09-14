package de.btu.swp.carcassonne.game.service.game;

import de.btu.swp.carcassonne.data.player.Player;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;

/**
 * Ingame Manager for all player related things
 */
public class PlayerService extends SubgameService {

	private BooleanBinding isPlayerOnTurn;

	/**
	 * The current player will be replaced by the next player in the playerList of
	 * lobby
	 * 
	 * @return next player
	 */
	public Player nextPlayer() {

		ObservableList<Player> players = lobbyService().getLobby().getPlayers();

		Player current = game().getPlayerOnTurn();
		
		int id = players.indexOf(current);
		int next_id = (id + 1) % players.size();
		
		Player next = players.get(next_id);

		game().setPlayerOnTurn(next);

		return next;
	}

	/**
	 * Creates a Binding which determinates if the local Player is on turn
	 * @return binding
	 */
	public BooleanBinding isPlayerOnTurn() {

		if (isPlayerOnTurn == null) {
			isPlayerOnTurn = game().playerOnTurnProperty().isEqualTo(lobbyService().localPlayerProperty());
		}

		return isPlayerOnTurn;
	}

}
