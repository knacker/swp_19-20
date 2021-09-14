package de.btu.swp.carcassonne.game.service.game;

import java.io.IOException;

import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Structure;
import de.btu.swp.carcassonne.data.graph.StructureData;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.game.paket.game.CardPlacePaket;
import de.btu.swp.carcassonne.game.paket.game.FollowerPaket;
import de.btu.swp.carcassonne.network.Paket;

/**
 * Ingame Manager for all follower related things
 */
public class FollowerService extends SubgameService {

	/**
	 * Check wether a Structure already has a follower or not
	 * @param v Vector to check
	 * @return true if no follower is placed
	 */
	public boolean checkClaimable(Vector v) {

		if (v.getType() == Structure.FIELD) {
			return false;
		}

		StructureData data = scoreService().analyseStructure(v);

		return data != null && data.getFollowers().isEmpty();

	}

	/**
	 * Places no follower as the current player
	 */
	public void placeNoFollower() {
		placeNoFollower(fieldService().getPlacedField());
	}

	/**
	 * Placed no follower and sends the placed card to all clients
	 * @param f Field which was placed
	 */
	public void placeNoFollower(Field f) {

		Player p = lobbyService().getLocalPlayer();

		if (p.getType() == PlayerType.BOT || playerService().isPlayerOnTurn().get()) {

			Paket cpaket = new CardPlacePaket(p, f);
			
			try {
				connectionService().sendPaket(cpaket);
			} catch (IOException es) {

			}
			Paket fpaket = new FollowerPaket(p);
			try {
				connectionService().sendPaket(fpaket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (connectionService().isServer()) {
			scoreService().calculatePoints();
			timerService().resetAfkTimer();
			gameService().nextTurn();
		}
	}

	/**
	 * Place Follower at the given vector as the current player
	 * @param v Vector to place
	 * @return follower was placed
	 */
	public boolean placeFollower(Vector v) {
		Player p = lobbyService().getLocalPlayer();

		return placeFollower(p, v);
	}

	/**
	 * Place Follower as the given player at the given vector
	 * @param p Player who places
	 * @param v Vector to place
	 * @return follower was placed
	 */
	public boolean placeFollower(Player p, Vector v) {

		if (v != null) {

			Field field = fieldService().getPlacedField();

			Follower f = new Follower(p, field, v);

			game().getFollowers().add(f);

			for (Rotation r : Rotation.values()) {

				if (fieldService().getPlacedField().getCard().getVector(r) == v) {
					fieldService().getPlacedField().getCard().getVector(r).setFollower(f);
				}

			}

			if (playerService().isPlayerOnTurn().get() || p.getType() == PlayerType.BOT) {
				Paket fpaket = new FollowerPaket(p, field, v);
				Paket cpaket = new CardPlacePaket(p, field);

				try {
					connectionService().sendPaket(cpaket);
				} catch (IOException es) {

				}
				try {
					connectionService().sendPaket(fpaket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (connectionService().isServer()) {

			scoreService().calculatePoints();
			timerService().resetAfkTimer();
			gameService().nextTurn();
		}

		return true;
	}
}
