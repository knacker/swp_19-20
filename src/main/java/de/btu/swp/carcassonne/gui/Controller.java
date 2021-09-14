package de.btu.swp.carcassonne.gui;

import de.btu.swp.carcassonne.App;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.service.BotService;
import de.btu.swp.carcassonne.game.service.ChatService;
import de.btu.swp.carcassonne.game.service.ConnectionService;
import de.btu.swp.carcassonne.game.service.GameService;
import de.btu.swp.carcassonne.game.service.LobbyService;
import de.btu.swp.carcassonne.game.service.SettingsService;
import de.btu.swp.carcassonne.game.service.TimerService;
import de.btu.swp.carcassonne.game.service.TutorialService;

public abstract class Controller {

	private App app;

	/**
	 * Returns the underlying app instance
	 * 
	 * @return app
	 */
	public App app() {
		return app;
	}

	/**
	 * Sets the app instance which should be used for all service methods
	 */
	public void setApp(App app) {
		if (this.app != null) {
			throw new RuntimeException("App already initialized");
		}
		this.app = app;
	}

	/**
	 * Returns the carcassonneService of the underlying app
	 * 
	 * @return carcassonneService
	 */
	public CarcassonneService carcassonneService() {
		return app.carcassonneService();
	}

	/**
	 * Returns the connectionService of the underlying carcassonneService
	 * 
	 * @return connectionService
	 */
	public ConnectionService connectionService() {
		return carcassonneService().connectionService();
	}

	/**
	 * Returns the lobbyService of the underlying carcassonneService
	 * 
	 * @return lobbyService
	 */
	public LobbyService lobbyService() {
		return carcassonneService().lobbyService();
	}

	/**
	 * Returns the gameService of the underlying carcassonneService
	 * 
	 * @return gameService
	 */
	public GameService gameService() {
		return carcassonneService().gameService();
	}

	/**
	 * Returns the chatService of the underlying carcassonneService
	 * 
	 * @return chatService
	 */
	public ChatService chatService() {
		return carcassonneService().chatService();
	}

	/**
	 * Returns the botService of the underlying carcassonneService
	 * 
	 * @return botService
	 */
	public BotService botService() {
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

	
	/**
	 * Initializes the Controller after the FXML file is loaded
	 */
	public abstract void initialize();

	public void open() {
	}

}
