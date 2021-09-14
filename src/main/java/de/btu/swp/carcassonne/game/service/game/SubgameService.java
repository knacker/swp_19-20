package de.btu.swp.carcassonne.game.service.game;

import de.btu.swp.carcassonne.data.Game;
import de.btu.swp.carcassonne.game.Service;

/**
 * Wrapper for alle Subgameservices to make them easier accessible
 */
public abstract class SubgameService extends Service {
	
	public Game game() {
		return gameService().getGame();
	}
	
	protected FieldService fieldService() {
		return gameService().fieldService();
	}
	
	protected CardService cardService() {
		return gameService().cardService();
	}
	
	protected FollowerService followerService() {
		return gameService().followerService();
	}
	
	protected PlayerService playerService() {
		return gameService().playerService();
	}
	
	protected ScoreService scoreService() {
		return gameService().scoreService();
	}
	
}
