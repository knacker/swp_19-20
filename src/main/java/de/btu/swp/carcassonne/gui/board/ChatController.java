package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import de.btu.swp.carcassonne.gui.data.ChatCell;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController extends Controller {
	
	private final BoardController boardController;
	
	public ChatController(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void initialize() {
		
		boardController.chatinput.setOnKeyReleased(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent key) {
	        	
	            if (key.getCode() == KeyCode.ENTER) {            	            	
	                chatService().sendMessage(boardController.chatinput.getText());	  
	                boardController.chatinput.clear();	                
	            }
	        }
	    });
		
		boardController.chatarea.setCellFactory((listview) -> {
			return new ChatCell(listview);
		});

		boardController.chatarea.setItems(chatService().getChat().getMessages());
	}

}
