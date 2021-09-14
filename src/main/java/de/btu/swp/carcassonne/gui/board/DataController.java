package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.gui.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DataController extends Controller {
	
	@FXML
	private Text timeLabel;
	
	@FXML
	private Text cardLabel;

	public void initialize() {
		timeLabel.textProperty().bind(timerService().getTimer().asString());
		cardLabel.textProperty().bind(gameService().getGame().getRemainingCards().sizeProperty().asString());
	}
}
