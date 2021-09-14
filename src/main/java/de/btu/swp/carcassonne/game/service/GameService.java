package de.btu.swp.carcassonne.game.service;

import java.io.IOException;

import de.btu.swp.carcassonne.data.Game;
import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.game.GameEndPaket;
import de.btu.swp.carcassonne.game.paket.game.GameTurnPaket;
import de.btu.swp.carcassonne.game.service.game.CardService;
import de.btu.swp.carcassonne.game.service.game.FieldService;
import de.btu.swp.carcassonne.game.service.game.FollowerService;
import de.btu.swp.carcassonne.game.service.game.PlayerService;
import de.btu.swp.carcassonne.game.service.game.ScoreService;
import de.btu.swp.carcassonne.game.service.game.SubgameService;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.util.CardUtil;
import javafx.beans.binding.BooleanBinding;

/**
 * Manager for all game related things
 */
public class GameService extends SubgameService {

	private final Game game = new Game();

	private final FieldService fieldService = new FieldService();
	private final CardService cardService = new CardService();
	private final FollowerService followerService = new FollowerService();
	private final PlayerService playerService = new PlayerService();
	private final ScoreService scoreService = new ScoreService();

	private BooleanBinding rotatingAllowed;
	private BooleanBinding cardsPlaceable;

	@Override
	public void setCarcassonneService(CarcassonneService carcassonneService) {
		super.setCarcassonneService(carcassonneService);
		fieldService.setCarcassonneService(carcassonneService);
		cardService.setCarcassonneService(carcassonneService);
		followerService.setCarcassonneService(carcassonneService);
		playerService.setCarcassonneService(carcassonneService);
		scoreService.setCarcassonneService(carcassonneService);
	}

	@Override
	public FieldService fieldService() {
		return fieldService;
	}

	@Override
	public CardService cardService() {
		return cardService;
	}

	@Override
	public FollowerService followerService() {
		return followerService;
	}

	@Override
	public PlayerService playerService() {
		return playerService;
	}

	@Override
	public ScoreService scoreService() {
		return scoreService;
	}

	/**
	 * Underlaying Game data class
	 * 
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * If the Game is in the State of Lobby it will start the game. The game will be
	 * set to be ingame und the StartCard will be placed at (0|0) The first player
	 * and card will be selected and the GameInitPaket will be sent to all
	 * connections
	 */
	public void startGame() {

		if (game.getState() != GameState.LOBBY) {
			throw new IllegalStateException("Game is already running");
		}

		fieldService().addField(null, 0, 0, CardUtil.createStartCard());

		timerService().start();

		nextTurn();

		game.setState(GameState.INGAME);

		System.out.println(lobbyService().getLobby().getPlayers().get());

		System.out.println("Game started.");
	}

	/**
	 * The next player and card will be selected and will be send to all connections
	 */
	public void nextTurn() {

		timerService().resetAfkTimer();

		if (tutorialService().isRunning()) {
			tutorialService().nextTurn();
			return;
		}

		fieldService.placedFieldProperty().set(null);
		Card card = cardService().nextCard();

		Paket paket;

		if (card == null) {

			scoreService().scoreGame();
			paket = new GameEndPaket();

		} else {

			Player player = playerService().nextPlayer();

			paket = new GameTurnPaket(player, card);

			if (player.getType() == PlayerType.BOT) {
				botService().executeTurn(player, card);
			}

		}

		try {
			connectionService().sendPaket(paket);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stops the game all together and closes all connections
	 */
	public void stopGame() {
		game.setState(null);
		timerService().stop();
		try {
			connectionService().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Game stopped.");
	}

	/**
	 * Get card for a given Card id format: A-0 or C-3
	 * @param cardId
	 * @return card
	 */
	public Card getCard(String cardId) {
		for (Card c : game.getRemainingCards()) {
			if (c.getId().equals(cardId)) {
				return c;
			}
		}
		return null;
	}

	public BooleanBinding isPlayerPlacingFollower() {
		return playerService().isPlayerOnTurn().and(fieldService().placedFieldProperty().isNotNull());
	}

	public BooleanBinding isRotatingAllowed() {

		if (rotatingAllowed == null) {
			rotatingAllowed = playerService().isPlayerOnTurn().and(game.currentCardProperty().isNotNull())
					.and(tutorialService().getTutorial().cardRotationAllowedProperty());
		}

		return rotatingAllowed;
	}

	public BooleanBinding areCardsPlaceable() {

		if (cardsPlaceable == null) {
			cardsPlaceable = playerService().isPlayerOnTurn().and(game.currentCardProperty().isNotNull())
					.and(tutorialService().getTutorial().placeholderVisibleProperty());
		}

		return cardsPlaceable;
	}
}
