package de.btu.swp.carcassonne.util;

import java.util.Random;

public class BotUtil {
	
	public final static String[] NAMES = {
			"Jamar",
			"Hollis",
			"Lydia",
			"Candelaria",
			"Maya",
			"Diedra",
			"Alan",
			"Britany",
			"Myra",
			"Wyatt",
			"Moriah",
			"Van",
			"Sadie",
			"Darron",
			"Mitchel",
			"Vincent",
			"Versie",
			"Jonathon",
			"Elias",
			"Sharan",
	};
	
	public static String randomName(Random random) {
		
		return NAMES[random.nextInt(NAMES.length)];
	}

}
