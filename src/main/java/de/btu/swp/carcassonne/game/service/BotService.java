package de.btu.swp.carcassonne.game.service;

import java.util.List;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.PossibleField;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Structure;
import de.btu.swp.carcassonne.data.graph.StructureData;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.Service;
import javafx.application.Platform;

/**
 * executes turns for bots and determinates the best field
 */
public class BotService extends Service {

	private long SLEEP_BEFORE_CARD_PLACE = 2000L;
	private long SLEEP_BEFORE_FOLLOWER_PLACE = 2000L;

	/**
	 * Execute turn for a given bot
	 * @param bot
	 */
	public void executeTurn(Player bot) {

		Card card = gameService().getGame().getCurrentCard();

		executeTurn(bot, card);
	}

	/**
	 * Execute the turn for a given bot and card
	 * @param bot
	 * @param card
	 */
	public void executeTurn(Player bot, Card card) {

		new Thread(() -> {

			try {
				Thread.sleep(SLEEP_BEFORE_CARD_PLACE);
			} catch (InterruptedException e) {
				return;
			}

			List<PossibleField> fields = gameService().fieldService().getPossibleFields(bot, card);

			fields.sort((f1, f2) -> {
				return f1.getPriority().ordinal() - f2.getPriority().ordinal();
			});

			PossibleField f = fields.get(0);
			int x = f.getField().getX();
			int y = f.getField().getY();

			Platform.runLater(() -> {
				card.setRotation(f.getRotation());
				gameService().fieldService().addField(bot, x, y, card);
			});

			try {
				Thread.sleep(SLEEP_BEFORE_FOLLOWER_PLACE);
			} catch (InterruptedException e) {
				return;
			}

			if (Math.random() < 0.8 && bot.getFollowers().size() < 7) {

				Card c = gameService().fieldService().getPlacedField().getCard();

				Rotation rot = null;
				int points = 0;

				for (Rotation r : Rotation.values()) {

					if (c.getVector(r).getType() != Structure.FIELD) {
						StructureData d = gameService().scoreService().analyseStructure(c.getVector(r));
						if (d.getFollowers().isEmpty() && d.getPoints() > points) {
							rot = r;
							points = d.getPoints();
						}
					}
				}

				if (c.hasMonas()) {
					StructureData m = gameService().scoreService().analyseStructure(c.getGraph().getMonas());
					if (m.getFollowers().isEmpty() && m.getPoints() > points) {
						rot = null;
						points = m.getPoints();
					}
				}

				if (points != 0) {
					final Rotation r = rot;
					Platform.runLater(() -> {
						Vector v = c.getVector(r);
						gameService().followerService().placeFollower(bot, v);
					});
				} else {
					Platform.runLater(() -> {
						gameService().followerService().placeNoFollower();
					});
				}
			} else {
				Platform.runLater(() -> {
					gameService().followerService().placeNoFollower();
				});
			}
		}).start();

	}

	public void setInterval(long placeCard, long placeFollower) {
		SLEEP_BEFORE_CARD_PLACE = placeCard;
		SLEEP_BEFORE_FOLLOWER_PLACE = placeFollower;
	}

}
