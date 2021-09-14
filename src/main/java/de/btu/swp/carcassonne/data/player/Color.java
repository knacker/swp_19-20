/*
 * holds enums of colors
 */
package de.btu.swp.carcassonne.data.player;

import java.io.Serializable;

public enum Color implements Serializable {
	
	BLUE,
	BLACK,
	RED,
	YELLOW,
	GREEN(javafx.scene.paint.Color.LIMEGREEN);
	
	javafx.scene.paint.Color paint;

	private Color() {
		paint = javafx.scene.paint.Color.valueOf(name());
	}
	
	private Color(javafx.scene.paint.Color c) {
		paint = c;
	}

	public javafx.scene.paint.Color toPaint() {
		return paint;
	}
	
}
