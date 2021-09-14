package de.btu.swp.carcassonne.game;

import de.btu.swp.carcassonne.game.service.BotService;
import de.btu.swp.carcassonne.game.service.ChatService;
import de.btu.swp.carcassonne.game.service.ConnectionService;
import de.btu.swp.carcassonne.game.service.GameService;
import de.btu.swp.carcassonne.game.service.LobbyService;
import de.btu.swp.carcassonne.game.service.SettingsService;
import de.btu.swp.carcassonne.game.service.TimerService;
import de.btu.swp.carcassonne.game.service.TutorialService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * CarcassonneService is the wrapper for all other Services
 */
public class CarcassonneService extends Service {

	private final ConnectionService connectionService = new ConnectionService();
	private final GameService gameService = new GameService();
	private final LobbyService lobbyService = new LobbyService();
	private final ChatService chatService = new ChatService();
	private final BotService botService = new BotService();
	private final TutorialService tutorialService = new TutorialService();
	private final SettingsService settingsService = new SettingsService();
	private final TimerService timerService = new TimerService();
	
	private final StringProperty error = new SimpleStringProperty();

	/**
	 * Loads all child Services and initiates them with this CarcassonneService
	 */
	public CarcassonneService() {
		setCarcassonneService(this);
		connectionService.setCarcassonneService(this);
		gameService.setCarcassonneService(this);
		lobbyService.setCarcassonneService(this);
		chatService.setCarcassonneService(this);
		botService.setCarcassonneService(this);
		tutorialService.setCarcassonneService(this);
		settingsService.setCarcassonneService(this);
		timerService.setCarcassonneService(this);
	}

	@Override
	public ConnectionService connectionService() {
		return connectionService;
	}

	@Override
	public LobbyService lobbyService() {
		return lobbyService;
	}

	@Override
	public GameService gameService() {
		return gameService;
	}

	@Override
	public ChatService chatService() {
		return chatService;
	}

	@Override
	public BotService botService() {
		return botService;
	}

	@Override
	public TutorialService tutorialService() {
		return tutorialService;
	}

	@Override
	public SettingsService settingsService() {
		return settingsService;
	}

	@Override
	public TimerService timerService() {
		return timerService;
	}
	
	public StringProperty errorProperty() {
		return error;
	}
	
	public String getError() {
		return error.get();
	}
	
	public void setError(String message) {
		error.set(message);
	}
	
}
