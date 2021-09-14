package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.settings.Settings;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class CardController extends Controller {
	
	private final BoardController boardController;
	
	public CardController(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void initialize() {
		
		gameService().getGame().currentCardProperty().addListener(new ChangeListener<Card>() {

			@Override
			public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
				
				if(newValue == null) {
					boardController.currentCardView.setImage(null);
					return;
				}
				
				boardController.currentCardView.setImage(new Image(newValue.getPath()));
				boardController.currentCardView.setRotate(newValue.getRotation().toDouble());
			}
		});
		
		boardController.root.setOnKeyReleased(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent key) {
	        	
	        	if(!gameService().isRotatingAllowed().get()) {
	        		return;
	        	}
	        	
				Settings settings = settingsService().getSettings();
	        	
	            if (key.getCode() == settings.rotateRightProperty().get()) {
	            	boardController.rotateCardRight();       
	            }
	            
	            if (key.getCode() == settings.rotateLeftProperty().get()) {
	            	boardController.rotateCardLeft();
	            }
	        }
	    });
		
		Card c = gameService().getGame().getCurrentCard();
		
		if(c != null) {
			boardController.currentCardView.setImage(new Image(c.getPath()));
			boardController.currentCardView.setRotate(c.getRotation().toDouble());
		}
		
		boardController.rotateLeftButton.visibleProperty().bind(gameService().isRotatingAllowed());
		boardController.rotateRightButton.visibleProperty().bind(gameService().isRotatingAllowed());
	}

}
