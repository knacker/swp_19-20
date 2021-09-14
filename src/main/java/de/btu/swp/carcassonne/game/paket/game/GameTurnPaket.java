/*
 * paket that changes the turn in the game for each player and executes the changes for other players aswell
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.data.Game;
import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.ServerPaket;
import de.btu.swp.carcassonne.game.service.GameService;
import de.btu.swp.carcassonne.game.service.LobbyService;
import de.btu.swp.carcassonne.util.CardUtil;

public class GameTurnPaket implements ServerPaket {

	private static final long serialVersionUID = -8229389580659132431L;

	private final int playerId;
	private final String cardId;

	public GameTurnPaket(Player player, Card startCard) {
		playerId = player.getId();
		cardId = startCard.getId();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public String getCardId() {
		return cardId;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		GameService gameService = carcassonneService.gameService();
		LobbyService lobbyService = carcassonneService.lobbyService();
		
		Game game = gameService.getGame();
		
		Player nextPlayer = lobbyService.getPlayer(playerId);
		Card nextCard = gameService.getCard(cardId);
		game.getRemainingCards().remove(nextCard);
		
		game.setPlayerOnTurn(nextPlayer);
		
		if(game.getState() == GameState.LOBBY) {

			gameService.fieldService().addField(0, 0, CardUtil.createStartCard());
			game.setState(GameState.INGAME);
			carcassonneService.timerService().start();
		}
		
		game.setCurrentCard(nextCard);
	}

}
