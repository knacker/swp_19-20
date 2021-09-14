/*
 * followerpaket contains the data, which is needed to display other players followers placed on the field
 */
package de.btu.swp.carcassonne.game.paket.game;

import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.game.CarcassonneService;
import de.btu.swp.carcassonne.game.paket.GamePaket;

public class FollowerPaket implements GamePaket {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8221382704717203291L;
	
	private final int playerId;
	private final int x;
	private final int y;
	private Rotation rot;
	private  int n;
	public FollowerPaket(Player p) {
		x = 0;
		y = 0;
		rot = null;
		this.playerId = p.getId();
		n =1;
	}
	public FollowerPaket(Player p, Field f, Vector v) {
		this.playerId = p.getId();
		this.x = f.getX();
		this.y = f.getY();
		for(Rotation r : Rotation.values()) {
			if(f.getCard().getVector(r) == v) {
				rot = r;
				
			}
		}
		
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void executeChange(CarcassonneService carcassonneService) {
		if(n != 1) {
		Player p = carcassonneService.lobbyService().getPlayer(playerId);
		
		Field field = carcassonneService.gameService().fieldService().getField(x, y);
		
		Vector v = field.getCard().getVector(rot);
		
		Follower f = new Follower(p, field, v);
		carcassonneService.gameService().getGame().getFollowers().add(f);
		
		}
		
		if(carcassonneService.connectionService().isServer()) {
			carcassonneService.gameService().scoreService().calculatePoints();
			carcassonneService.gameService().nextTurn();
		}
	}

}
