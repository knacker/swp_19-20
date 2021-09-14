package de.btu.swp.carcassonne;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.settings.Settings;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import de.btu.swp.carcassonne.gui.JoinController;
import de.btu.swp.carcassonne.gui.LobbyController;
import de.btu.swp.carcassonne.gui.MenuController;
import de.btu.swp.carcassonne.gui.RulesController;
import de.btu.swp.carcassonne.gui.ScoreController;
import de.btu.swp.carcassonne.gui.SettingsController;
import de.btu.swp.carcassonne.gui.StartController;
import de.btu.swp.carcassonne.gui.TutorialController;
import de.btu.swp.carcassonne.gui.UsernameController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

public class App extends Application {

	private Stage stage;
	private CarcassonneService carcassonneService;
	
	private HashMap<Class<? extends Controller>, Pair<Controller, Parent>> screens = new HashMap<>();

	public CarcassonneService carcassonneService() {
		return carcassonneService;
	}

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;

		openStartScreen();

		stage.setFullScreenExitHint("");
    	stage.setFullScreen(true);
		stage.setTitle("Carcassonne - Das Brettspiel");
		stage.getIcons().add(new Image(getResourceAsStream("/img/appicon.png")));
		stage.show();

		System.out.println("App has started.");
	}
	
	private void initNewCarcassonneService() {
		if(carcassonneService != null) {
			carcassonneService.gameService().stopGame();
		}
		screens.clear();
		carcassonneService = new CarcassonneService();
		
		carcassonneService.gameService().getGame().stateProperty().addListener(new ChangeListener<GameState>() {

			@Override
			public void changed(ObservableValue<? extends GameState> observable, GameState oldValue,
					GameState newValue) {
				System.out.println("GameState: " + oldValue + " -> " + newValue);

				if(newValue == null) {
					openStartScreen();
					return;
				}
				switch (newValue) {
				case INGAME:
					openBoardScreen();
					break;
				case LOBBY:
					openUsernameScreen();
					break;
				case SCORE:
					openScoreScreen();
					break;
				default:
					openStartScreen();
					break;
				}
			}
		});
		
	}
	
	public void openScreen(GameState state) {

		if(state == null) {
			openStartScreen();
			return;
		}
		switch (state) {
		case INGAME:
			openBoardScreen();
			break;
		case LOBBY:
			openLobbyScreen();
			break;
		case SCORE:
			openScoreScreen();
			break;
		default:
			openStartScreen();
			break;
		}
	}

	@Override
	public void stop() throws Exception {
		carcassonneService.connectionService().close();

		System.out.println("App has stopped.");
	}

	public void openStartScreen() {
		
		initNewCarcassonneService();

		loadScreen("start", StartController.class);
	}

	public void openLobbyScreen() {

		loadScreen("lobby", LobbyController.class);
	}

	public void openJoinScreen() {

		loadScreen("join", JoinController.class);
	}

	public void openUsernameScreen() {

		loadScreen("username", UsernameController.class);
	}

	public void openBoardScreen() {

		loadScreen("board", BoardController.class);
	}

	public void openScoreScreen() {

		loadScreen("score", ScoreController.class);
	}
	
	public void openTutorialScreen() {
		
		loadScreen("tutorial", TutorialController.class);
	}

	public void openMenuScreen() {
		
		loadScreen("menu", MenuController.class);
	}

	public void openSettingsScreen() {
		
		loadScreen("settings", SettingsController.class);
	}
	
	public void openRulesScreen() {
		
		loadScreen("rules", RulesController.class);
	}
	
	public <E> E loadFxml(String name, Controller controller) {

		try {
			controller.setApp(this);

			FXMLLoader loader = new FXMLLoader(getResource("/fxml/" + name + ".fxml"));
			loader.setController(controller);
			
			return loader.load();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadScreen(String name, Class<? extends Controller> controllerClazz) {
		
		carcassonneService.setError(null);
		
		Pair<Controller, Parent> screen = screens.get(controllerClazz);
		
		Parent root;
		Controller controller;

		if(screen == null) {
			
			try {
				controller = controllerClazz.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				controller = null;
			}
			
			root = loadFxml(name, controller);
			screens.put(controllerClazz, new Pair<>(controller, root));
		} else {
			controller = screen.getKey();
			root = screen.getValue();
		}
		
		controller.open();
		
		if (stage.getScene() == null) {
			Scene scene = new Scene(root);
			scene.setOnKeyReleased((event) -> {
				Settings settings = carcassonneService().settingsService().getSettings();
				
				if (event.getCode() == settings.toggleFullscreenProperty().get()) {
					stage.setFullScreen(!stage.isFullScreen());
				}
				
				event.consume();
			});

			scene.getStylesheets().add("/css/main.css");

			stage.setScene(scene);

		} else {
			stage.getScene().setRoot(root);
		}

		System.out.println("New Scene: " + name + " with " + controller.getClass().getSimpleName());
	}

	private URL getResource(String s) {
		return getClass().getResource(s);
	}

	private InputStream getResourceAsStream(String s) {
		return getClass().getResourceAsStream(s);
	}

}