package de.btu.swp.carcassonne.game.service.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.chat.ChatMessage;
import de.btu.swp.carcassonne.data.graph.Structure;
import de.btu.swp.carcassonne.data.graph.StructureData;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.paket.game.PointsPaket;
import de.btu.swp.carcassonne.network.Paket;
import javafx.collections.ObservableList;

/**
 * Ingame Manager for all score related things
 */
public class ScoreService extends SubgameService {

	/**
	 * If the game is running and the last card was placed this method should be
	 * called for entering the scoring mode
	 */
	public void scoreGame() {

		timerService().stop();
		game().setCurrentCard(null);
		game().setPlayerOnTurn(null);
		
		ObservableList<Field> board = game().getBoard();

		for (Field f : board) {
			if (f.getCard() != Card.PLACEHOLDER) {
				scoreService().calculatePoints(f, GameState.SCORE);
			}
		}
		game().setState(GameState.SCORE);

	}

	/**
	 * Ranks players based on their scores
	 * @return
	 */
	public List<Player> evaluateWinner() {

		ArrayList<Player> order = new ArrayList<>(lobbyService().getLobby().getPlayers());
		order.sort((p1, p2) -> {
			return p2.getPoints() - p1.getPoints();
		});
		return order;
	}

	/**
	 * Calculate points for the placed field
	 */
	public void calculatePoints() {
		Field f = fieldService().getPlacedField();
		calculatePoints(f, GameState.INGAME);
	}

	/**
	 * Calculate points of a Field
	 * @param f Field to calcluate points for
	 * @param state Ingame state
	 */
	public void calculatePoints(Field f, GameState state) {

		if (f == null) {
			return;
		}

		HashSet<StructureData> datas = new HashSet<>();

		rot: for (Rotation r : Rotation.values()) {

			Vector v = f.getCard().getVector(r);

			if (v.getType() == Structure.FIELD) {
				continue;
			}

			for (StructureData d : datas) {
				if (d.getVectors().contains(v)) {
					continue rot;
				}
			}

			datas.add(analyseStructure(v));
		}

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {

				StructureData d;

				if (x == 0 && y == 0) {
					d = analyseMonas(f);
				} else {
					Field m = fieldService().getField(f.getX() + x, f.getY() + y);

					if (m != null && m.getCard() != Card.PLACEHOLDER) {
						d = analyseMonas(m);
					} else {
						continue;
					}
				}
				if (d != null) {
					datas.add(d);
				}
			}
		}

		for (StructureData d : datas) {

			int largest = 0;
			HashMap<Color, LinkedList<Follower>> follower = new HashMap<>();

			for (Color c : Color.values()) {
				follower.put(c, new LinkedList<>());
			}

			if (d.isFinished() && !d.getFollowers().isEmpty()
					|| (state == GameState.SCORE && !d.getFollowers().isEmpty())) {
				Set<Follower> fs = d.getFollowers();

				for (Follower fo : fs) {
					follower.get(fo.getColor()).add(fo);
				}

				for (LinkedList<Follower> list : follower.values()) {
					largest = Math.max(largest, list.size());
				}

				for (LinkedList<Follower> list : follower.values()) {
					if (list.size() == largest) {
						addpoints(list.get(0).getPlayer(), d, state);
					}
				}

				for (Follower fo : fs) {
					fo.getVector().setFollower(null);
					fo.getPlayer().getFollowers().remove(fo);
					game().getFollowers().remove(fo);
				}
			}
		}
	}

	/**
	 * Add points to a player based on the Strucute Data
	 * @param p
	 * @param d
	 * @param state
	 */
	public void addpoints(Player p, StructureData d, GameState state) {

		int points = d.getPoints();

		if (d.getType() == Structure.TOWN && d.isFinished()) {
			points *= 2;
		}

		p.addPoints(points);

		if (state != GameState.SCORE) {
			Paket paket = new PointsPaket(p);
			try {
				connectionService().sendPaket(paket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String message;
		
		if (state != GameState.INGAME) {
			message = d.getType() + " nicht abgeschlossen. Punkte durch Spielende " + points + ".";
		} else {
			message = d.getType() + " abgeschlossen. Punkte: " + points + ".";
		}
		ChatMessage msg = new ChatMessage(p, message);
		chatService().getChat().getMessages().add(msg);
	}

	/**
	 * Analyses a monas Structure
	 * @param monasField Field with monas
	 * @return StructureData
	 */
	public StructureData analyseMonas(Field monasField) {

		Vector monasVector = monasField.getCard().getGraph().getMonas();

		if (monasVector == null || monasVector.getFollower() == null) {
			return null;
		}

		StructureData monas = new StructureData();

		monas.getVectors().add(monasVector);
		monas.getFollowers().add(monasVector.getFollower());

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {

				if (x == 0 && y == 0) {
					monas.addPoints(1);
					continue;
				}

				Field f = fieldService().getField(monasField.getX() + x, monasField.getY() + y);

				if (f != null && f.getCard() != Card.PLACEHOLDER) {
					monas.addPoints(1);
				}
			}
		}

		monas.setFinished(monas.getPoints() == 9);

		return monas;
	}

	/**
	 * Analyse a Structure starting from a given vector
	 * @param start Starting point
	 * @return analysed Structure
	 */
	public StructureData analyseStructure(Vector start) {
		StructureData data = new StructureData();

		Queue<Vector> queue = new LinkedList<>();

		queue.add(start);

		while (!queue.isEmpty()) {
			Vector curr = queue.poll();

			data.getVectors().add(curr);

			for (Vector v : curr.getConnections()) {
				if (!data.getVectors().contains(v)) {
					queue.add(v);
				}
			}

			boolean f = curr.getConnections().size() >= 2 || curr.getConnections().size() == 1 && curr.isEndpoint();

			if (curr.getFollower() != null) {
				data.getFollowers().add(curr.getFollower());

			}

			data.andFinished(f);

			data.addPoints(curr.getPoints());
		}
		return data;
	}

}
