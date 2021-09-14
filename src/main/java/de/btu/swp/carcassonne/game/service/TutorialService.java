package de.btu.swp.carcassonne.game.service;

import de.btu.swp.carcassonne.data.Game;
import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.data.tutorial.ExecuteTutorialState;
import de.btu.swp.carcassonne.data.tutorial.Tutorial;
import de.btu.swp.carcassonne.data.tutorial.TutorialState;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.service.game.FieldService;
import de.btu.swp.carcassonne.game.service.game.FollowerService;
import de.btu.swp.carcassonne.util.CardUtil;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

/**
 * Executes the tutorial and manages it states
 */
public class TutorialService extends Service implements ExecuteTutorialState {

	private final Tutorial tutorial = new Tutorial();

	private boolean running = false;

	private Runnable nextTurn;

	public boolean isRunning() {
		return running;
	}

	public void start() {
		running = true;
		getTutorial().setCardRotationAllowed(false);
		getTutorial().setPlaceholderVisible(false);
		connectionService().loadServer();
		lobbyService().getLocalPlayer().setUsername("Tutorial");
		gameService().startGame();
		gameService().getGame().setCurrentCard(null);
		gameService().getGame().setPlayerOnTurn(null);

		getTutorial().stateProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends TutorialState> observable, TutorialState oldValue,
					TutorialState newValue) {
				execute(newValue);
			}

		});

//		Platform.runLater(() -> {
//			getTutorial().setState(TutorialState.RESET);
//			getTutorial().setCardRotationAllowed(true);
//			getTutorial().setPlaceholderVisible(true);
//			setLocalPlayer();
//		});
	}

	public void nextState() {
		TutorialState s = tutorial.getState();
		int ordinal = s == null ? -1 : s.ordinal();

		try {
			tutorial.setState(TutorialState.values()[ordinal + 1]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			tutorial.setState(null);
		}
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	@Override
	public void executeNull() {
		unset();
	}

	@Override
	public void executeCardStart() {
		unset();
	}

	@Override
	public void executeCardTown() {
		setCard("N-0");
		setLocalPlayer();
	}

	@Override
	public void executeRotation() {
		Card c = gameService().getGame().getCurrentCard();

		addCardRotationListener(c);

		getTutorial().setCardRotationAllowed(true);
	}

	@Override
	public void executePlaceCard() {

		addCardPlacedListener();

		getTutorial().setPlaceholderVisible(true);
	}

	@Override
	public void executePlaceFollower() {
		addFollowerPlacedListener();
	}

	@Override
	public void executeCardStreet() {
		setCard("V-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executeCardTownStreet() {
		setCard("L-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executeCardMonas() {
		setCard("B-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executeCardMonasStreet() {
		setCard("A-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executeCardTownShield() {
		setCard("Q-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executeReset() {
		unsetCard();
	}

	@Override
	public void executeStructureFinishTown() {
		Game game = gameService().getGame();
		FieldService fieldService = gameService().fieldService();
		FollowerService followerService = gameService().followerService();
		Player p = lobbyService().getLocalPlayer();

		game.currentCardProperty().set(null);
		game.getRemainingCards().clear();
		game.getRemainingCards().addAll(CardUtil.createAllCards());
		game.getFollowers().clear();
		game.getBoard().clear();
		lobbyService().getLocalPlayer().setPoints(0);
		lobbyService().getLocalPlayer().getFollowers().clear();

		Card A0 = getCard("A-0", Rotation.WEST);
		Card E0 = getCard("E-0", Rotation.EAST);
		Card V0 = getCard("V-0", Rotation.EAST);
		Card V1 = getCard("V-1", Rotation.NORTH);
		Card c = getCard("O-0", Rotation.WEST);

		fieldService.addField(null, 0, 0, CardUtil.createStartCard());

		new Thread(() -> {
			try {
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -1, 0, A0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					followerService.placeFollower(p, A0.getGraph().getMonas());
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -1, -1, E0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, 1, 0, V0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					followerService.placeFollower(p, V0.getWest());
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, 1, -1, V1);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					game.setCurrentCard(c);
					addCardPlacedListener();
				});
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	public void executePointsTown() {

		addFollowerPlacedListener();
	}

	@Override
	public void executeStructureFinishStreet() {

		setCard("X-0");
		addFollowerPlacedListener();
	}

	@Override
	public void executePointsStreet() {
	}

	@Override
	public void executeStructureFinishMonas() {
		FieldService fieldService = gameService().fieldService();

		Card W0 = getCard("W-0", Rotation.EAST);
		Card U0 = getCard("U-0", Rotation.NORTH);
		Card S0 = getCard("S-0", Rotation.SOUTH);
		Card N0 = getCard("N-0", Rotation.SOUTH);
		Card I0 = getCard("I-0", Rotation.EAST);

		new Thread(() -> {
			try {
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -2, -1, W0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -2, 0, U0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -2, 1, S0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, -1, 1, N0);
				});
				Thread.sleep(1000L);
				Platform.runLater(() -> {
					fieldService.addField(null, 0, 1, I0);
					gameService().followerService().placeNoFollower();
				});
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	public void executePointsMonas() {

	}

	@Override
	public void executeBiggerField() {

		getTutorial().setCardRotationAllowed(false);
		getTutorial().setPlaceholderVisible(false);
		botService().setInterval(500L, 200L);
		lobbyService().getLocalPlayer().setType(PlayerType.BOT);
		lobbyService().addBot();
		lobbyService().addBot();
		FXCollections.shuffle(gameService().getGame().getRemainingCards());

		IntegerProperty count = new SimpleIntegerProperty(30);

		nextTurn = () -> {
			new Thread(() -> {
				if (count.get() == 0) {
					nextTurn = null;
					nextState();
					return;
				}

				count.set(count.get() - 1);

				Platform.runLater(() -> {
					Player p = gameService().playerService().nextPlayer();
					Card c = gameService().cardService().nextCard();

					botService().executeTurn(p, c);
				});
			}).start();
		};
		nextTurn.run();
	}

	@Override
	public void executeOtherFollower() {
		lobbyService().getLocalPlayer().setType(PlayerType.PLAYER);
		// TODO Auto-generated method stub
	}

	@Override
	public void executeGameEnd() {
		// TODO Auto-generated method stub
		getTutorial().setState(null);
	}

	private Card getCard(String s) {
		return getCard(s, Rotation.NORTH);
	}

	private Card getCard(String s, Rotation r) {
		Card c = gameService().getCard(s);
		gameService().game().getRemainingCards().remove(c);
		c.setRotation(r);
		return c;
	}

	private void setCard(String s) {

		Card c = getCard(s);
		setCard(c);

	}

	private void setCard(Card c) {
		Card old = gameService().getGame().getCurrentCard();
		if (old != null) {
			gameService().getGame().getRemainingCards().add(old);
		}
		gameService().getGame().setCurrentCard(c);
	}

	private void setPlayer(Player p) {
		gameService().getGame().setPlayerOnTurn(p);
	}

	private void setLocalPlayer() {
		Player p = lobbyService().getLocalPlayer();
		setPlayer(p);
	}

	private void unsetPlayer() {
		setPlayer(null);
	}

	private void unsetCard() {
		setCard((Card) null);
	}

	private void unset() {
		unsetPlayer();
		unsetCard();
	}

	private void addCardPlacedListener() {

		ListProperty<Field> fields = gameService().getGame().getBoard();

		fields.addListener(new ListChangeListener<Field>() {

			@Override
			public void onChanged(Change<? extends Field> c) {
				while (c.next()) {
					for (Field f : c.getAddedSubList()) {
						if (f.getCard() != Card.PLACEHOLDER) {
							System.out.println(f);
							fields.removeListener(this);
							nextState();
							return;
						}
					}
				}
			}

		});
	}

	private void addFollowerPlacedListener() {

		nextTurn = () -> {
			nextTurn = null;
			nextState();
		};
	}

	private void addCardRotationListener(Card c) {

		c.rotationProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Rotation> observable, Rotation oldValue, Rotation newValue) {
				c.rotationProperty().removeListener(this);
				nextState();
			}

		});
	}

	public void nextTurn() {
		if (nextTurn != null) {
			nextTurn.run();
		}
	}
}
