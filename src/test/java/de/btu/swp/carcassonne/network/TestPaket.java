package de.btu.swp.carcassonne.network;

import de.btu.swp.carcassonne.game.CarcassonneService;

public class TestPaket implements Paket {

	private static final long serialVersionUID = 7506296351834261244L;

	@Override
	public String toString() {
		return "TestPaket";
	}

	@Override
	public int getPlayerId() {
		return 0;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		// TODO Auto-generated method stub
		
	}
	
}
