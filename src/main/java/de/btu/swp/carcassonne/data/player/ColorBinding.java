/*
 * holds the data of a color bound to a player
 */
package de.btu.swp.carcassonne.data.player;

import javafx.beans.binding.ObjectBinding;
import javafx.scene.paint.Color;

public class ColorBinding extends ObjectBinding<Color> {

	private final Player player;
	
	public ColorBinding(Player player) {
		this.player = player;
		bind(player.colorProperty());
	}
	
	@Override
	protected Color computeValue() {
		return player.colorProperty().get().toPaint();
	}

}
