/*
 * paket for a placed card that gets sent and exectued. Paket contains playerid, cardid, rotation and the coordinates of the field
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.GamePaket;

public class CardPlacePaket implements GamePaket {

	private static final long serialVersionUID = -1168169770821743241L;
	
	private final int playerId;
	private final String cardId;
	private final Rotation rotation;
	private final int x, y;
	
	public CardPlacePaket(Player player, Field f) {
		
		this.playerId = player.getId();
		this.cardId = f.getCard().getId();
		this.rotation = f.getCard().getRotation();
		this.x = f.getX();
		this.y = f.getY();
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}
	
	public String getCardId() {
		return cardId;
	}
	
	public Rotation getRotation() {
		return rotation;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		
		Card card = carcassonneService.gameService().getCard(cardId);
		
		if(card == null) {
			card = carcassonneService.gameService().getGame().getCurrentCard();
		}
		
		card.setRotation(rotation);
		
		Player p = carcassonneService.lobbyService().getPlayer(playerId);	
		carcassonneService.gameService().fieldService().addField(p, x, y, card);
		carcassonneService.timerService().resetAfkTimer();
	}

}
