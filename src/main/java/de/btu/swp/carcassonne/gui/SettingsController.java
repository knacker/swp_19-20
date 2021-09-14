package de.btu.swp.carcassonne.gui;

import java.util.LinkedList;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.settings.Settings;
import de.btu.swp.carcassonne.data.settings.SettingsData;
import de.btu.swp.carcassonne.gui.settings.KeyController;
import de.btu.swp.carcassonne.gui.settings.SettingController;
import de.btu.swp.carcassonne.gui.settings.SliderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SettingsController extends Controller {

	@FXML
	public VBox settings;
	
	private SettingsData unsaved;

	@Override
	public void initialize() {
		Settings settings = settingsService().getSettings();

		LinkedList<SettingController> controllers = new LinkedList<>();

		controllers.add(new SliderController("Karten Größe", settings.fieldSizeProperty(), 50, 100, 5, 4));
		controllers.add(new SliderController("Karten Abstand", settings.fieldSpaceProperty(), 0, 5, 1, 0));
		controllers.add(new KeyController("An/Aus Vollbild", settings.toggleFullscreenProperty()));
		controllers.add(new KeyController("Karte im Uhrzeigersinn rotieren", settings.rotateRightProperty()));
		controllers.add(new KeyController("Karte gegen Uhrzeigersinn rotieren", settings.rotateLeftProperty()));
		
		for(SettingController controller : controllers) {
			controller.setSettingsController(this);
			controller.initialize();
		}
	}
	
	@Override
	public void open() {
		unsaved = settingsService().getSettings().toData();
	}

	@FXML
	public void back(ActionEvent e) {
		unsaved.loadSettings(settingsService().getSettings());
		changeScreen();
	}
	
	@FXML
	public void reset() {
		settingsService().loadDefaultSettings();
	}
	
	@FXML
	public void save() {
		settingsService().saveSettings();
		changeScreen();
	}
	
	public void changeScreen() {

		GameState state = gameService().getGame().getState();
		if (state == null) {
			app().openStartScreen();
			return;
		}
		switch (state) {
		case INGAME:
		case SCORE:
			app().openMenuScreen();
			break;
		case LOBBY:
			app().openLobbyScreen();
			break;
		default:
			app().openStartScreen();
			break;
		}
	}
}
