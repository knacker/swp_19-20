package de.btu.swp.carcassonne.gui;

import java.util.LinkedList;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.chat.ChatMessage;
import de.btu.swp.carcassonne.gui.board.CardController;
import de.btu.swp.carcassonne.gui.board.ChatController;
import de.btu.swp.carcassonne.gui.board.FieldController;
import de.btu.swp.carcassonne.gui.board.FollowerController;
import de.btu.swp.carcassonne.gui.board.HeaderController;
import de.btu.swp.carcassonne.gui.board.LastFieldController;
import de.btu.swp.carcassonne.util.GlyphUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class BoardController extends Controller {
	
	@FXML
	public AnchorPane root;
	
	@FXML    
	public ListView<ChatMessage> chatarea;
	    
	@FXML
	public TextField chatinput;
	
	@FXML
	public Button rotateRightButton;
	
	@FXML
	public Button rotateLeftButton;
	
	@FXML
	public Button showChatButton;
	
	@FXML
	public Button hideChatButton;
	
	@FXML
	public Button menuButton;
	
	@FXML
	public Button skipFollowerBtn;

	@FXML
	public Group grid;
	
	@FXML
	public ImageView currentCardView;
	

	@FXML
	public void initialize() {
		
		LinkedList<Controller> controllers = new LinkedList<>();
		skipFollowerBtn.setVisible(false);
		controllers.add(new FieldController(this));
		controllers.add(new FollowerController(this));
		controllers.add(new LastFieldController(this));
		controllers.add(new HeaderController(this));
		controllers.add(new ChatController(this));
		controllers.add(new CardController(this));
		
		for(Controller controller : controllers) {

			controller.setApp(app());
			controller.initialize();
		}

		rotateRightButton.setGraphic(GlyphUtil.rotateRight());
		rotateLeftButton.setGraphic(GlyphUtil.rotateLeft());
		menuButton.setGraphic(GlyphUtil.menu());
	}

	@FXML
	public void showChat(ActionEvent e) {
		chatarea.setVisible(true);
		chatinput.setVisible(true);
		showChatButton.setVisible(false);
		hideChatButton.setVisible(true);
	}
	
	@FXML
	public void hideChat(ActionEvent e) {
		chatarea.setVisible(false);
		chatinput.setVisible(false);
		showChatButton.setVisible(true);
		hideChatButton.setVisible(false);
	}
	
	@FXML
	public void showMenu(ActionEvent e) {
		app().openMenuScreen();
	}
	
	@FXML
	public void skipFollower() {
		
		gameService().followerService().placeNoFollower();

		skipFollowerBtn.setVisible(false);

		for (Node c : grid.getChildren()) {
			
			if(c instanceof Circle) {
				c.setVisible(false);
			}
		}
	}
	
	@FXML
	public void rotateCardLeft() {
		
		Card card = gameService().getGame().getCurrentCard();

		card.rotateLeft();
		currentCardView.setRotate(card.getRotation().toDouble());
		
	}
	
	@FXML
	public void rotateCardRight() {
		
		Card card = gameService().getGame().getCurrentCard();

		card.rotateRight();
		currentCardView.setRotate(card.getRotation().toDouble());
		
	}
	
	@FXML
	public void showScoreboard() {
		app().openScoreScreen();
	}
	
	@FXML
	public void openRulesScreen() {
		app().openRulesScreen();
	}

}
