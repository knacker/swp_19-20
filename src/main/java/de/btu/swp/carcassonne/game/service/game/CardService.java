package de.btu.swp.carcassonne.game.service.game;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Rotation;
import javafx.collections.ObservableList;

/**
 * Ingame Manager for all Card related things
 *
 */
public class CardService extends SubgameService {

	/**
	 * The current card will be replaced by the next card.
	 * 
	 * @return next Card
	 */
	public Card nextCard() {

		ObservableList<Card> cards = game().getRemainingCards();

		if (cards.size() == 0) {
			return null;
		}

		Card next_card = cards.get(0);

		for (int i = 1; i < cards.size(); i++) {
			if (i == cards.size()) {
				game().setCurrentCard(null);
				return null;
			}
			if (fieldService().canCardBePlaced(next_card)) {
				break;
			}
			next_card = cards.get(i);
		}

		next_card.setRotation(Rotation.NORTH);

		cards.remove(next_card);
		game().setCurrentCard(next_card);

		return next_card;

	}

}
