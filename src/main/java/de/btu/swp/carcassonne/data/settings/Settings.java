/*
 * data being used for the settings a user can choose ingame. that includes visual changes, such as fieldspace and size, aswell as binding keys to rotation.
 */
package de.btu.swp.carcassonne.data.settings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;

public class Settings {
	
	private final DoubleProperty fieldSize = new SimpleDoubleProperty();
	private final DoubleProperty fieldSpace = new SimpleDoubleProperty();
	
	private final DoubleBinding fieldSizeSpace = fieldSize.add(fieldSpace);
	private final DoubleBinding fieldSizeHalf = fieldSize.divide(2);

	private final ObjectProperty<KeyCode> rotateRight = new SimpleObjectProperty<>();
	private final ObjectProperty<KeyCode> rotateLeft = new SimpleObjectProperty<>();
	private final ObjectProperty<KeyCode> toggleFullscreen = new SimpleObjectProperty<>();
	
	public Settings() {
		new SettingsData().loadSettings(this);
	}
	
	public SettingsData toData() {
		return new SettingsData(this);
	}
	
	public DoubleProperty fieldSizeProperty() {
		return fieldSize;
	}
	
	public DoubleProperty fieldSpaceProperty() {
		return fieldSpace;
	}
	
	public ObjectProperty<KeyCode> rotateRightProperty() {
		return rotateRight;
	}
	
	public ObjectProperty<KeyCode> rotateLeftProperty() {
		return rotateLeft;
	}
	
	public ObjectProperty<KeyCode> toggleFullscreenProperty() {
		return toggleFullscreen;
	}

	public DoubleBinding fieldSizeSpaceProperty() {
		return fieldSizeSpace;
	}

	public DoubleBinding fieldSizeHalfProperty() {
		return fieldSizeHalf;
	}

}
