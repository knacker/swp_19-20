package de.btu.swp.carcassonne.gui.score;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.util.ScoreboardUtil;
import javafx.beans.binding.DoubleBinding;

public class PositionBinding extends DoubleBinding {

	private final Player player;
	private final boolean x;
	
	public PositionBinding(Player player, boolean x) {
		this.player = player;
		this.x = x;
		bind(player.pointsProperty(), player.colorProperty());
	}
	
	@Override
	protected double computeValue() {
		
		int color_id = player.getColor().ordinal();
		int score = player.getPoints() % 50;
		
		Position pos = ScoreboardUtil.POSITIONS[color_id][score];
		
		return x ? pos.getX() : pos.getY();
	}

}
