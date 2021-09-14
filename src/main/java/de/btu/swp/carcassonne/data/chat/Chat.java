//contains the data of a list of messages, which are used to display the chat
package de.btu.swp.carcassonne.data.chat;

import java.util.LinkedList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Chat {
	
	private final ListProperty<ChatMessage> messages = new SimpleListProperty<>();
	
	public Chat() {
		messages.set(FXCollections.observableList(new LinkedList<>()));
	}
	
	public ListProperty<ChatMessage> getMessages() {
		return messages;
	}

}
