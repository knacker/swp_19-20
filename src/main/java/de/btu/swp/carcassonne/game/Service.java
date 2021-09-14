package de.btu.swp.carcassonne.game;

import de.btu.swp.carcassonne.game.service.BotService;
import de.btu.swp.carcassonne.game.service.ChatService;
import de.btu.swp.carcassonne.game.service.ConnectionService;
import de.btu.swp.carcassonne.game.service.GameService;
import de.btu.swp.carcassonne.game.service.LobbyService;
import de.btu.swp.carcassonne.game.service.SettingsService;
import de.btu.swp.carcassonne.game.service.TimerService;
import de.btu.swp.carcassonne.game.service.TutorialService;

/**
 * Wrapper for all Services so that subservices are easier accessible
 */
public abstract class Service {

	private CarcassonneService carcassonneService;

	/**
	 * Sets the carcassonneService instance which should be used for all service
	 * methods
	 */
	public void setCarcassonneService(CarcassonneService carcassonneService) {
		if (this.carcassonneService != null) {
			throw new RuntimeException("CarcassoneService already initialized");
		}
		this.carcassonneService = carcassonneService;
	}

	/**
	 * Returns the underlying carcassonneService
	 * 
	 * @return carcassonneService
	 */
	protected CarcassonneService carcassonneService() {
		return carcassonneService;
	}

	/**
	 * Returns the connectionService of the underlying carcassonneService
	 * 
	 * @return connectionService
	 */
	protected ConnectionService connectionService() {
		return carcassonneService().connectionService();
	}

	/**
	 * Returns the lobbyService of the underlying carcassonneService
	 * 
	 * @return lobbyService
	 */
	protected LobbyService lobbyService() {
		return carcassonneService().lobbyService();
	}

	/**
	 * Returns the gameService of the underlying carcassonneService
	 * 
	 * @return gameService
	 */
	protected GameService gameService() {
		return carcassonneService().gameService();
	}

	/**
	 * Returns the chatService of the underlying carcassonneService
	 * 
	 * @return chatService
	 */
	protected ChatService chatService() {
		return carcassonneService().chatService();
	}

	/**
	 * Returns the botService of the underlying carcassonneService
	 * 
	 * @return botService
	 */
	protected BotService botService() {
		return carcassonneService().botService();
	}

	/**
	 * Returns the tutorialService of the underlying carcassonneService
	 * 
	 * @return tutorialService
	 */
	protected TutorialService tutorialService() {
		return carcassonneService().tutorialService();
	}

	/**
	 * Returns the SettingsService of the underlying carcassonneService
	 * 
	 * @return settingsService
	 */
	protected SettingsService settingsService() {
		return carcassonneService().settingsService();
	}

	/**
	 * Returns the TimerService of the underlying carcassonneService
	 * 
	 * @return timerService
	 */
	protected TimerService timerService() {
		return carcassonneService().timerService();
	}

}
