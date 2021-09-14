package de.btu.swp.carcassonne.gui.data;

import de.btu.swp.carcassonne.data.chat.ChatMessage;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ChatCell extends ListCell<ChatMessage> {
	
	private final ListView<ChatMessage> listview;
	
	public ChatCell(ListView<ChatMessage> listview) {
		this.listview = listview;
        getStyleClass().addAll("carcassonne-font", "chat-font");
	}

	@Override
	protected void updateItem(ChatMessage item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			textProperty().unbind();
			setText(null);
			setGraphic(null);
		} else {
			// Insets of ListView (could be removed in fxml files)
			DoubleBinding width = listview.widthProperty().subtract(6);	
			
            minWidthProperty().bind(width);
            maxWidthProperty().bind(width);
            prefWidthProperty().bind(width);

            setWrapText(true);
            
			textProperty().bind(item.getSender().usernameProperty().concat(": " + item.getMessage()));
		}
	}

}
