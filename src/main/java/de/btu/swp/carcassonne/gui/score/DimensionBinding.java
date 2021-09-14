package de.btu.swp.carcassonne.gui.score;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class DimensionBinding extends DoubleBinding {

	private final Node node;
	private final boolean width;
	
	public DimensionBinding(Node node, boolean width) {
		this.node = node;
		this.width = width;
		bind(node.boundsInLocalProperty());
	}
	
	@Override
	protected double computeValue() {
		Bounds bounds = node.getBoundsInLocal();
		return width ? bounds.getWidth() : bounds.getHeight();
	}

}
