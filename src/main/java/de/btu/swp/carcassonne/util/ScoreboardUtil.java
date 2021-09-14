package de.btu.swp.carcassonne.util;

import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.gui.score.Position;

public class ScoreboardUtil {

	private ScoreboardUtil() {}
	
	private final static Position DEFAULT_POSITION = new Position(0,0);
	
	public final static Position[][] POSITIONS = getPositions();
	
	private static Position[][] getPositions() {
		
		Position[][] pos = new Position[5][];
		
		pos[Color.BLUE.ordinal()] = getBlue();
		pos[Color.BLACK.ordinal()] = getBlack();
		pos[Color.RED.ordinal()] = getRed();
		pos[Color.YELLOW.ordinal()] = getYellow();
		pos[Color.GREEN.ordinal()] = getGreen();
		
		return pos;
	}
	
	private static Position[] getBlue() {
		
		Position[] pos = new Position[50];
		
		// ENTFERNEN WENN ALLE FELDER ERSTELLT WURDEN!
		for(int i = 0; i < 50; i++) {
			pos[i] = DEFAULT_POSITION;
		}
		
		pos[0] = new Position(83,83);
		pos[1] = new Position(73,79);
		pos[2] = new Position(61,81);
		pos[3] = new Position(56,85);
		pos[4] = new Position(46,87);
		pos[5] = new Position(34,90);
		pos[6] = new Position(29,83);
		pos[7] = new Position(16,88);
		pos[8] = new Position(8,78);
		pos[9] = new Position(10,67);
		pos[10] = new Position(11,51);
		pos[11] = new Position(8,38);
		pos[12] = new Position(11,27);
		pos[13] = new Position(4,11);
		pos[14] = new Position(12,12);
		pos[15] = new Position(19,9);
		pos[16] = new Position(31,6);
		pos[17] = new Position(35,13);
		pos[18] = new Position(47,6);
		pos[19] = new Position(54,11);
		pos[20] = new Position(60,9);
		pos[21] = new Position(66,6);
		pos[22] = new Position(76,8);
		pos[23] = new Position(83,9);
		pos[24] = new Position(88,9);
		pos[25] = new Position(91,18);
		pos[26] = new Position(94,26);
		pos[27] = new Position(84,29);
		pos[28] = new Position(76,33);
		pos[29] = new Position(67,27);
		pos[30] = new Position(64,29);
		pos[31] = new Position(55,31);
		pos[32] = new Position(48,31);
		pos[33] = new Position(42,31);
		pos[34] = new Position(36,31);
		pos[35] = new Position(29,33);
		pos[36] = new Position(23,35);
		pos[37] = new Position(19,45);
		pos[38] = new Position(21,53);
		pos[39] = new Position(31,57);
		pos[40] = new Position(36,54);
		pos[41] = new Position(41,54);
		pos[42] = new Position(50,54);
		pos[43] = new Position(57,54);
		pos[44] = new Position(65,54);
		pos[45] = new Position(72,48);
		pos[46] = new Position(77,50);
		pos[47] = new Position(87,48);
		pos[48] = new Position(92,51);
		pos[49] = new Position(90,62);

		return pos;
	}
	
	private static Position[] getBlack() {
		
		Position[] pos = new Position[50];

		// ENTFERNEN WENN ALLE FELDER ERSTELLT WURDEN!
		for(int i = 0; i < 50; i++) {
			pos[i] = DEFAULT_POSITION;
		}
		
		pos[0] = new Position(83,78);
		pos[1] = new Position(75,79);
		pos[2] = new Position(66,85);
		pos[3] = new Position(52,79);
		pos[4] = new Position(45,80);
		pos[5] = new Position(38,80);
		pos[6] = new Position(27,80);
		pos[7] = new Position(18,80);
		pos[8] = new Position(10,78);
		pos[9] = new Position(10,65);
		pos[10] = new Position(9,53);
		pos[11] = new Position(6,38);
		pos[12] = new Position(8,30);
		pos[13] = new Position(7,20);
		pos[14] = new Position(13,7);
		pos[15] = new Position(22,7);
		pos[16] = new Position(28,8);
		pos[17] = new Position(39,9);
		pos[18] = new Position(42,11);
		pos[19] = new Position(53,7);
		pos[20] = new Position(57,6);
		pos[21] = new Position(69,7);
		pos[22] = new Position(76,5);
		pos[23] = new Position(80,7);
		pos[24] = new Position(89,5);
		pos[25] = new Position(92,16);
		pos[26] = new Position(88,27);
		pos[27] = new Position(81,31);
		pos[28] = new Position(77,31);
		pos[29] = new Position(71,29);
		pos[30] = new Position(60,29);
		pos[31] = new Position(54,33);
		pos[32] = new Position(46,35);
		pos[33] = new Position(40,33);
		pos[34] = new Position(34,33);
		pos[35] = new Position(28,35);
		pos[36] = new Position(25,39);
		pos[37] = new Position(23,43);
		pos[38] = new Position(26,52);
		pos[39] = new Position(30,53);
		pos[40] = new Position(38,52);
		pos[41] = new Position(43,52);
		pos[42] = new Position(48,51);
		pos[43] = new Position(55,51);
		pos[44] = new Position(64,52);
		pos[45] = new Position(72,50);
		pos[46] = new Position(76,48);
		pos[47] = new Position(83,46);
		pos[48] = new Position(91,48);
		pos[49] = new Position(92,63);
		
		return pos;
	}
	
	private static Position[] getRed() {
		
		Position[] pos = new Position[50];

		// ENTFERNEN WENN ALLE FELDER ERSTELLT WURDEN!
		for(int i = 0; i < 50; i++) {
			pos[i] = DEFAULT_POSITION;
		}
		
		pos[0] = new Position(88,83);
		pos[1] = new Position(71,77);
		pos[2] = new Position(61,87);
		pos[3] = new Position(56,76);
		pos[4] = new Position(43,81);
		pos[5] = new Position(35,75);
		pos[6] = new Position(25,76);
		pos[7] = new Position(18,77);
		pos[8] = new Position(14,74);
		pos[9] = new Position(12,62);
		pos[10] = new Position(5,50);
		pos[11] = new Position(6,43);
		pos[12] = new Position(10,30);
		pos[13] = new Position(6,12);
		pos[14] = new Position(12,5);
		pos[15] = new Position(22,5);
		pos[16] = new Position(28,4);
		pos[17] = new Position(38,5);
		pos[18] = new Position(43,4);
		pos[19] = new Position(55,5);
		pos[20] = new Position(61,4);
		pos[21] = new Position(68,5);
		pos[22] = new Position(73,7);
		pos[23] = new Position(84,5);
		pos[24] = new Position(92,5);
		pos[25] = new Position(96,16);
		pos[26] = new Position(90,29);
		pos[27] = new Position(85,33);
		pos[28] = new Position(74,29);
		pos[29] = new Position(70,32);
		pos[30] = new Position(64,35);
		pos[31] = new Position(53,35);
		pos[32] = new Position(48,36);
		pos[33] = new Position(38,36);
		pos[34] = new Position(36,36);
		pos[35] = new Position(31,37);
		pos[36] = new Position(27,39);
		pos[37] = new Position(25,43);
		pos[38] = new Position(27,48);
		pos[39] = new Position(32,49);
		pos[40] = new Position(36,49);
		pos[41] = new Position(41,50);
		pos[42] = new Position(51,48);
		pos[43] = new Position(57,47);
		pos[44] = new Position(62,48);
		pos[45] = new Position(68,50);
		pos[46] = new Position(80,48);
		pos[47] = new Position(87,46);
		pos[48] = new Position(94,49);
		pos[49] = new Position(94,61);
		
		return pos;
	}
	
	private static Position[] getYellow() {
		
		Position[] pos = new Position[50];

		// ENTFERNEN WENN ALLE FELDER ERSTELLT WURDEN!
		for(int i = 0; i < 50; i++) {
			pos[i] = DEFAULT_POSITION;
		}
		
		pos[0] = new Position(88,78);
		pos[1] = new Position(73,85);
		pos[2] = new Position(65,82);
		pos[3] = new Position(53,84);
		pos[4] = new Position(48,82);
		pos[5] = new Position(34,85);
		pos[6] = new Position(25,86);
		pos[7] = new Position(18,86);
		pos[8] = new Position(4,88);
		pos[9] = new Position(4,71);
		pos[10] = new Position(4,59);
		pos[11] = new Position(9,42);
		pos[12] = new Position(3,27);
		pos[13] = new Position(7,14);
		pos[14] = new Position(16,13);
		pos[15] = new Position(22,13);
		pos[16] = new Position(32,13);
		pos[17] = new Position(38,13);
		pos[18] = new Position(43,15);
		pos[19] = new Position(53,13);
		pos[20] = new Position(60,13);
		pos[21] = new Position(68,13);
		pos[22] = new Position(75,13);
		pos[23] = new Position(82,13);
		pos[24] = new Position(87,13);
		pos[25] = new Position(90,18);
		pos[26] = new Position(87,24);
		pos[27] = new Position(81,25);
		pos[28] = new Position(76,23);
		pos[29] = new Position(67,25);
		pos[30] = new Position(62,25);
		pos[31] = new Position(57,27);
		pos[32] = new Position(49,27);
		pos[33] = new Position(42,27);
		pos[34] = new Position(32,27);
		pos[35] = new Position(25,29);
		pos[36] = new Position(19,31);
		pos[37] = new Position(17,42);
		pos[38] = new Position(19,56);
		pos[39] = new Position(28,61);
		pos[40] = new Position(36,58);
		pos[41] = new Position(41,56);
		pos[42] = new Position(50,58);
		pos[43] = new Position(57,58);
		pos[44] = new Position(62,58);
		pos[45] = new Position(71,58);
		pos[46] = new Position(78,56);
		pos[47] = new Position(85,52);
		pos[48] = new Position(90,53);
		pos[49] = new Position(88,65);
		
		return pos;
	}
	
	private static Position[] getGreen() {
		
		Position[] pos = new Position[50];

		// ENTFERNEN WENN ALLE FELDER ERSTELLT WURDEN!
		for(int i = 0; i < 50; i++) {
			pos[i] = DEFAULT_POSITION;
		}
		
		pos[0] = new Position(85,73);
		pos[1] = new Position(76,89);
		pos[2] = new Position(64,77);
		pos[3] = new Position(52,88);
		pos[4] = new Position(45,91);
		pos[5] = new Position(38,88);
		pos[6] = new Position(28,89);
		pos[7] = new Position(18,89);
		pos[8] = new Position(6,85);
		pos[9] = new Position(6,69);
		pos[10] = new Position(7,57);
		pos[11] = new Position(3,40);
		pos[12] = new Position(6,30);
		pos[13] = new Position(10,20.1);
		pos[14] = new Position(15,11);
		pos[15] = new Position(22,11);
		pos[16] = new Position(29,11);
		pos[17] = new Position(36,7);
		pos[18] = new Position(45,11);
		pos[19] = new Position(51,11);
		pos[20] = new Position(57,10);
		pos[21] = new Position(66,11);
		pos[22] = new Position(73,11);
		pos[23] = new Position(80,11);
		pos[24] = new Position(90,9);
		pos[25] = new Position(93,19);
		pos[26] = new Position(90,24);
		pos[27] = new Position(81,27);
		pos[28] = new Position(75,27);
		pos[29] = new Position(71,27);
		pos[30] = new Position(64,27);
		pos[31] = new Position(54,27);
		pos[32] = new Position(50,29);
		pos[33] = new Position(40,29);
		pos[34] = new Position(34,29);
		pos[35] = new Position(30,29);
		pos[36] = new Position(21,35);
		pos[37] = new Position(19,41);
		pos[38] = new Position(23,56);
		pos[39] = new Position(28,57);
		pos[40] = new Position(37,58);
		pos[41] = new Position(43,56);
		pos[42] = new Position(48,56);
		pos[43] = new Position(55,56);
		pos[44] = new Position(64,56);
		pos[45] = new Position(69,56);
		pos[46] = new Position(76,54);
		pos[47] = new Position(83,50);
		pos[48] = new Position(88,55);
		pos[49] = new Position(85,65);
		
		return pos;
	}
	
	
	
}
