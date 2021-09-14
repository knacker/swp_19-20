package de.btu.swp.carcassonne.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController extends Controller {

	@FXML
	public void exitGame(ActionEvent e) {
		gameService().stopGame();
	}
	
	@FXML
	public void continueGame() {
		app().openBoardScreen();
	}
	
	@FXML
	public void openSettings() {
		app().openSettingsScreen();
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
