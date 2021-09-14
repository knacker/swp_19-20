/*
 * data class used for the tutorial. 
 */
package de.btu.swp.carcassonne.data.tutorial;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Tutorial {
	
	private final ObjectProperty<TutorialState> state = new SimpleObjectProperty<>();
	
	private final BooleanProperty cardRotationAllowed = new SimpleBooleanProperty(true);
	private final BooleanProperty placeholderVisible = new SimpleBooleanProperty(true);
	
	public ObjectProperty<TutorialState> stateProperty() {
		return state;
	}
	
	public TutorialState getState() {
		return state.get();
	}
	
	public void setState(TutorialState s) {
		state.set(s);
	}
	
	public boolean isCardRotationAllowed() {
		return cardRotationAllowed.get();
	}
	
	public void setCardRotationAllowed(boolean b) {
		cardRotationAllowed.set(b);
	}
	
	public BooleanProperty cardRotationAllowedProperty() {
		return cardRotationAllowed;
	}
	
	public boolean isPlaceholderVisible() {
		return placeholderVisible.get();
	}
	
	public void setPlaceholderVisible(boolean b) {
		placeholderVisible.set(b);
	}
	
	public BooleanProperty placeholderVisibleProperty() {
		return placeholderVisible;
	}

}
