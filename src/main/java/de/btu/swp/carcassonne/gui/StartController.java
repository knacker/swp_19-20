package de.btu.swp.carcassonne.gui;

import de.btu.swp.carcassonne.util.GlyphUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController extends Controller {

	@FXML
	public Button createButton, joinButton, settingsBt;

	@FXML
	public void createGame(ActionEvent e) {
		connectionService().loadServer();
		app().openUsernameScreen();
	}

	@FXML
	public void joinGame(ActionEvent e) {
		app().openJoinScreen();
	}
	
	@FXML
	public void openRulesScreen() {
		app().openRulesScreen();
	}
	
	@FXML
	public void playTutorial() {
		app().openTutorialScreen();
	}

	@Override
	public void initialize() {
		settingsBt.setGraphic(GlyphUtil.settings());
	}

	@FXML
	public void openSettings() {
		app().openSettingsScreen();
	}

}
