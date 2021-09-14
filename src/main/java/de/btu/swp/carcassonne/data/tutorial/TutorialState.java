/*
 * enums used for the tutorial 
 */
package de.btu.swp.carcassonne.data.tutorial;

import com.google.common.base.CaseFormat;

public enum TutorialState {

	CARD_START,
	CARD_TOWN,
	ROTATION,
	PLACE_CARD,
	PLACE_FOLLOWER,
	CARD_STREET,
	CARD_TOWN_STREET,
	CARD_MONAS,
	CARD_MONAS_STREET,
	CARD_TOWN_SHIELD,
	RESET,
	STRUCTURE_FINISH_TOWN,
	POINTS_TOWN,
	STRUCTURE_FINISH_STREET,
	POINTS_STREET,
	STRUCTURE_FINISH_MONAS,
	POINTS_MONAS,
	BIGGER_FIELD,
	OTHER_FOLLOWER,
	GAME_END;
	
	public String toString() {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name());
	}
}
