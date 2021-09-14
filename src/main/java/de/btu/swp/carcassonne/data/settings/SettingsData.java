/*
 * used to define the settings 
 */
package de.btu.swp.carcassonne.data.settings;

import javafx.scene.input.KeyCode;

public class SettingsData {
	
	public double fieldSize = 70;
	public double fieldSpace = 2;
	
	public KeyCode rotateRight = KeyCode.RIGHT;
	public KeyCode rotateLeft = KeyCode.LEFT;
	public KeyCode toggleFullscreen = KeyCode.F11;
	
	public SettingsData() {
	}
	
	public SettingsData(Settings settings) {

		fieldSize = settings.fieldSizeProperty().get();
		fieldSpace = settings.fieldSpaceProperty().get();
		rotateRight = settings.rotateRightProperty().get();
		rotateLeft = settings.rotateLeftProperty().get();
		toggleFullscreen = settings.toggleFullscreenProperty().get();
	}
	
	public void loadSettings(Settings settings) {

		settings.fieldSizeProperty().set(fieldSize);
		settings.fieldSpaceProperty().set(fieldSpace);
		settings.rotateRightProperty().set(rotateRight);
		settings.rotateLeftProperty().set(rotateLeft);
		settings.toggleFullscreenProperty().set(toggleFullscreen);
	}

}
