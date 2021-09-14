/*
 * returns "bot", if a player in a game is a bot and its displayed ingame
 */
package de.btu.swp.carcassonne.data.player;

import javafx.beans.binding.StringBinding;

public class BotBinding extends StringBinding {
	
	private final Player player;
	
	public BotBinding(Player player) {
		this.player = player;
		bind(player.typeProperty());
	}

	@Override
	protected String computeValue() {
		if(player.getType() == PlayerType.BOT) {
			return "Bot";
		}
		return "";
	}

}
