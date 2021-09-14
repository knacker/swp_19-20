package de.btu.swp.carcassonne.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UsernameController extends Controller {
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	public void initialize() {
		errorLabel.textProperty().bind(carcassonneService().errorProperty());
		usernameField.setOnKeyReleased(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent key) {
	            if (key.getCode() == KeyCode.ENTER) {
	        		if(lobbyService().updateUsername(usernameField.getText())) {
	        			app().openLobbyScreen();
	        		} else {
	        			carcassonneService().setError("Dieser Username wird bereits verwendet.");
	        		}
	            }
	        }
	    });
	}
	
	@FXML
	public void updateUsername(ActionEvent e) {
		
		String username = usernameField.getText();
		
		if(lobbyService().updateUsername(username)) {
			app().openLobbyScreen();
		} else {
			carcassonneService().setError("Dieser Username wird bereits verwendet.");
		}
		
	}
	
	@FXML
	public void exitGame(ActionEvent e) {
		gameService().stopGame();
	}
	
}
