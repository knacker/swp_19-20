package de.btu.swp.carcassonne.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class JoinController extends Controller {

	@FXML
	private Label errorLabel;
	
	@FXML
	public TextField inputField;

	@FXML
	public Button joinButton;
	
	@FXML
	public void initialize() {
		errorLabel.textProperty().bind(carcassonneService().errorProperty());
		inputField.setOnKeyReleased(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent key) {
	            if (key.getCode() == KeyCode.ENTER) {
	            	connectionService().connectToServer(inputField.getText());
	            }
	        }
	    });
	}

	@FXML
	public void joinGame(ActionEvent e) {
	    
		connectionService().connectToServer(inputField.getText());
	}
	
	@FXML
	public void exitGame(ActionEvent e) {
		app().openStartScreen();
	}

}
