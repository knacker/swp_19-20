package de.btu.swp.carcassonne.game.service.game;

import java.util.LinkedList;
import java.util.List;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.FieldPriority;
import de.btu.swp.carcassonne.data.card.PossibleField;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Structure;
import de.btu.swp.carcassonne.data.graph.StructureData;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

/**
 * Ingame Manager for all Field related things
 */
public class FieldService extends SubgameService {

	private ObjectProperty<Field> placedField = new SimpleObjectProperty<>();

	/**
	 * Gets the Field at a given Location or null if there is no field
	 * 
	 * @param x Coordinate
	 * @param y Coordinate
	 * @return field or null
	 */
	public Field getField(int x, int y) {

		for (Field f : game().getBoard()) {
			if (f.getX() == x && f.getY() == y) {
				return f;
			}
		}

		return null;
	}

	/**
	 * Determinates if a card is placeable in any of the four rotations
	 * 
	 * @param card to place
	 * @return true if a placeable field exists, otherwise false
	 */
	public boolean canCardBePlaced(Card card) {

		List<Field> placeholder = game().getBoard().filtered((field) -> {
			return field.isPlaceholder();
		});

		for (Rotation r : Rotation.values()) {
			card.setRotation(r);
			for (Field f : placeholder) {
				if (canCardBePlacedAt(f.getX(), f.getY(), card)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Determinates if a card is placeable at x-y-coordinate with its current
	 * rotation
	 * 
	 * @param x    Coordinate
	 * @param y    Coordinate
	 * @param card to place
	 * @return true if card is placeable otherwise false
	 */
	public boolean canCardBePlacedAt(int x, int y, Card card) {

		for (Rotation r : Rotation.values()) {
			if (!canConnect(x, y, card, r)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Determinates if a card is connectable at an x-y-coordinate with the card in a specific rotation
	 * 
	 * @param x Coordinate
	 * @param y Coordinate
	 * @param c card to place
	 * @param r Rotation to check
	 * @return true if card is connectable
	 */
	public boolean canConnect(int x, int y, Card c, Rotation r) {

		Field to = getRelativeField(x, y, r);

		return isPlaceholder(to) || c.getVector(r).getType() == to.getCard().getVector(r.opposite()).getType();
	}

	/**
	 * Determinates if a Field is a placeholder
	 * @param f Field to check
	 * @return true if field is a placeholder
	 */
	public boolean isPlaceholder(Field f) {
		return f == null || f.getCard().isPlaceholder();
	}

	/**
	 * Gets a neighbour field in the specific direction or the field itself when r is null
	 * @param f Field
	 * @param r Direction
	 * @return relative field
	 */
	public Field getRelativeField(Field f, Rotation r) {
		if (r == null) {
			return f;
		}
		return getRelativeField(f.getX(), f.getY(), r);
	}

	/**
	 * Gets a neighbour field in the specific direction
	 * @param x
	 * @param y
	 * @param r
	 * @return
	 */
	public Field getRelativeField(int x, int y, Rotation r) {
		return getField(x + r.getRelativeX(), y + r.getRelativeY());
	}

	/**
	 * Determinates possible fields for a bot and assigns each field a priority
	 * @param p Bot
	 * @param card to check
	 * @return List of possible fields
	 */
	public List<PossibleField> getPossibleFields(Player p, Card card) {

		List<Field> placeholder = game().getBoard().filtered((field) -> {
			return field.isPlaceholder();
		});

		List<PossibleField> placeable = new LinkedList<>();

		for (Rotation r : Rotation.values()) {
			card.setRotation(r);
			for (Field f : placeholder) {
				if (canCardBePlacedAt(f.getX(), f.getY(), card)) {

					FieldPriority prio = calculatePriority(p, f, card);
					placeable.add(new PossibleField(f, card.getRotation(), prio));
				}
			}
		}

		return placeable;
	}

	/**
	 * Calculate Priority for a player, field and card
	 * @param p Bot who places
	 * @param f Field where to place
	 * @param card Card to place
	 * @return priority
	 */
	private FieldPriority calculatePriority(Player p, Field f, Card card) {
		FieldPriority prio = FieldPriority.NOTHING;
		for (Rotation r : Rotation.values()) {
			if (card.getVector(r).getType() != Structure.FIELD) {
				Field rel = getRelativeField(f, r);
				if (isPlaceholder(rel)) {
					continue;
				}

				StructureData data = scoreService().analyseStructure(rel.getCard().getVector(r.opposite()));
				if (prio == FieldPriority.NOTHING && data.getFollowers().size() == 0) {
					prio = FieldPriority.UNCLAIMED;
				} else {

					if (hasEnemyFollower(p, data)) {
						if (prio == FieldPriority.NOTHING || prio == FieldPriority.UNCLAIMED) {
							prio = FieldPriority.ENEMY;
						} else if (prio == FieldPriority.OWN) {
							prio = FieldPriority.OWN_AND_ENEMY;
						}
					}
					if (hasOwnFollower(p, data)) {
						if (prio == FieldPriority.NOTHING || prio == FieldPriority.UNCLAIMED) {
							prio = FieldPriority.OWN;
						} else if (prio == FieldPriority.ENEMY) {
							prio = FieldPriority.OWN_AND_ENEMY;
						}
					}

				}
			}
		}
		return prio;
	}

	/**
	 * Does a Structure contain enemy followers
	 * @param p Bot to check
	 * @param data Structure to check
	 * @return has enemy follower
	 */
	private boolean hasEnemyFollower(Player p, StructureData data) {
		for (Follower f : data.getFollowers()) {
			if (f.getPlayer() != p) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Does a Structure contain enemy followers
	 * @param p Bot to check
	 * @param data Structure to check
	 * @return has own follower
	 */
	private boolean hasOwnFollower(Player p, StructureData data) {
		for (Follower f : data.getFollowers()) {
			if (f.getPlayer() == p) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Place the current card as the current player at the x-y-corrdinate
	 * @param x Coordinate
	 * @param y Coordinate
	 * @return card was placed
	 */
	public boolean addField(int x, int y) {
		Card c = game().getCurrentCard();
		return addField(x, y, c);
	}

	/**
	 * Place the given card as the current player at the x-y-corrdinate
	 * @param x Coordinate
	 * @param y Cootdinate
	 * @param card to place
	 * @return card was placed
	 */
	public boolean addField(int x, int y, Card card) {

		Player player = lobbyService().getLocalPlayer();

		return addField(player, x, y, card);
	}

	/**
	 * Check if the given position has no card and the Place the card at the given
	 * position otherwise do nothing.
	 * 
	 * @param x    Coordinate
	 * @param y    Coordinate
	 * @param card to be placed
	 * @return card was successfully placed
	 */
	public boolean addField(Player player, int x, int y, Card card) {

		ObservableList<Field> board = game().getBoard();
		Field exists = getField(x, y);

		if (!card.isPlaceholder() && !canCardBePlacedAt(x, y, card)) {
			return false;
		}

		if (exists != null) {
			if (!exists.getCard().isPlaceholder()) {
				return false;
			}

			board.remove(exists);
		}

		Field f = new Field(x, y, card);

		board.add(f);

		if (!card.isPlaceholder()) {

			placedField.set(f);
			game().setCurrentCard(null);

			addField(x + 1, y, Card.PLACEHOLDER);
			addField(x - 1, y, Card.PLACEHOLDER);
			addField(x, y + 1, Card.PLACEHOLDER);
			addField(x, y - 1, Card.PLACEHOLDER);

			setConnections(f);

			if (player == null) {
				return true;
			}

			player.setLastField(f);
		}
		return true;

	}

	/**
	 * Connect all vectors with neighbour fields
	 * @param f Field to connect
	 */
	private void setConnections(Field f) {

		for (Rotation r : Rotation.values()) {
			connect(f, r);
		}
	}

	/**
	 * connect a single vector with another vector
	 * @param f1 Field to connect
	 * @param r Direction to connect
	 */
	public void connect(Field f1, Rotation r) {

		Field f2 = getRelativeField(f1, r);

		if (f1.getCard().isPlaceholder() || f2 == null || f2.getCard().isPlaceholder()) {
			return;
		}

		Vector v1 = f1.getCard().getVector(r);
		Vector v2 = f2.getCard().getVector(r.opposite());
		v1.addConnection(v2);
		v2.addConnection(v1);
	}

	/**
	 * Get placed Field
	 * @return placed Field
	 */
	public Field getPlacedField() {
		return placedField.get();
	}

	/**
	 * Set placed Field
	 * @param f Field
	 */
	public void setPlaceField(Field f) {
		placedField.set(f);
	}

	/**
	 * get Placed Field Property
	 * @return placed field Property
	 */
	public ObjectProperty<Field> placedFieldProperty() {
		return placedField;
	}
}
