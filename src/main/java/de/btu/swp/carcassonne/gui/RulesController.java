package de.btu.swp.carcassonne.gui;

import de.btu.swp.carcassonne.data.GameState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RulesController extends Controller{
	
	@FXML
	private ImageView currentCardView;	

	@FXML
	private Button previousRuleButton;
	
	@FXML
	private Button nextRuleButton;

	@FXML
	private AnchorPane root;

	@FXML
	private HBox hbox;
	
	private int page=1;
	
	@FXML
	public void closeRules(ActionEvent e) {
		GameState state = gameService().getGame().getState();
		
		if(state == null) {
			app().openStartScreen();
			return;
		}
		
		switch(state) {
		case INGAME:
			app().openBoardScreen();
			break;
		case LOBBY:
			app().openLobbyScreen();
			break;
		case SCORE:
			app().openScoreScreen();
			break;
		default:
			app().openStartScreen(); 
			break;
		}
	}
	
	@FXML
	public void showRules(ActionEvent e) {
		
		Image base1 = new Image("/rules/basegame/base_1", true);
		currentCardView = new ImageView(base1);
		
	}
	@FXML
	public void openNextRule() {
		
		previousRuleButton.setVisible(true);
		
		switch(page) {
		case 1:
			page++;
			currentCardView.setImage(setBaseRules2());
			
			break;
		case 2:			
			page++;
			currentCardView.setImage(setBaseRules3());
			break;
		case 3:
			page++;
			currentCardView.setImage(setBaseRules4());
			break;
		case 4:
			page++;
			nextRuleButton.setVisible(false);
			currentCardView.setImage(setBaseRules5());
			break;
		}

	}
	@FXML
    public void arrows(KeyEvent key) {

        if (key.getCode() == KeyCode.RIGHT) {
        	
        	if(page < 5) {
        	openNextRule();    
        	}
        }      
        if (key.getCode() == KeyCode.LEFT) {
        	
        	if(page > 1) {
        	openPreviousRule();
        	}
        }
    }
	@FXML
	public void openPreviousRule() {

		switch(page) {
		case 2:
			page--;
			currentCardView.setImage(setBaseRules1());
			previousRuleButton.setVisible(false);
			break;
		case 3:
			page--;		
			currentCardView.setImage(setBaseRules2());
			break;
		case 4:
			page--;
			currentCardView.setImage(setBaseRules3());		
			break;
		case 5:
			page--;
			currentCardView.setImage(setBaseRules4());	
			nextRuleButton.setVisible(true);
		}
		 
	}
	
	@FXML
	public Image setBaseRules1() {

		Image base1 = new Image("/rules/basegame/base_1.png");
		return base1;
	}

	@FXML
	public Image setBaseRules2() {
		
		Image base2 = new Image("/rules/basegame/base_2.png");
		return base2;
		
	}
	
	@FXML
	public Image setBaseRules3() {
		
		Image base3 = new Image("/rules/basegame/base_3.png");
		return base3;
		
	}
	
	@FXML
	public Image setBaseRules4() {
		
		Image base4 = new Image("/rules/basegame/base_4.png");
		return base4;
	}
	
	@FXML
	public Image setBaseRules5() {
		
		Image base5 = new Image("/rules/basegame/base_5.png");
		return base5;
	}
	
	@Override
	public void initialize() {

		currentCardView.setImage(currentCardView.getImage());
		previousRuleButton.setVisible(false);

		hbox.prefHeightProperty().bind(root.heightProperty());

		page++;
		
	}
	

}
