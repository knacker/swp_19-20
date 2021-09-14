package de.btu.swp.carcassonne.game.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.Lobby;
import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.game.Service;
import de.btu.swp.carcassonne.game.paket.game.PlayerTypeChangePaket;
import de.btu.swp.carcassonne.game.paket.lobby.ColorChangePaket;
import de.btu.swp.carcassonne.game.paket.lobby.OrderChangePaket;
import de.btu.swp.carcassonne.game.paket.lobby.PlayerKickPaket;
import de.btu.swp.carcassonne.game.paket.lobby.UsernameChangePaket;
import de.btu.swp.carcassonne.game.paket.server.PlayerJoinPaket;
import de.btu.swp.carcassonne.network.Paket;
import de.btu.swp.carcassonne.util.BotUtil;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manager for all lobby related things
 */
public class LobbyService extends Service {

	private final Lobby lobby = new Lobby();

	private ObjectProperty<Player> localPlayer = new SimpleObjectProperty<>();

	private ObservableList<Color> takenColorList = FXCollections.observableArrayList(Color.values());
	/*
	 * @return lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}
	
	/**
	 * get all selectable colors
	 * @return list
	 */
	public ObservableList<Color> getColorList() {
		return takenColorList;
	}

	/*
	 * Checks, if a player is already in a lobby. If not, add the player to the
	 * lobby and send a packet.
	 * 
	 * @param player of type Player
	 * 
	 * @throws IllegalStateException, if player is already in lobby
	 */
	public void addPlayer(Player player) {

		if (lobby.getPlayers().contains(player)) {
			throw new IllegalStateException("Player already in lobby");
		} else {
			
			if(player.getColor() == null) {
				Color c = getFirstFreeColor();
				player.setColor(c);
			}

			Paket paket = new PlayerJoinPaket(player);

			try {
				connectionService().sendPaket(paket);
				lobby.getPlayers().add(player);

				for(Color c : Color.values()) {
					if(isColorTaken(c) && getLocalPlayer().getColor() != c) {
						takenColorList.remove(c);
					}
				}
				System.out.println("Player added to Lobby: " + player.getId());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * checks, if a color is already taken in a lobby. if not, set the color of the given player and send a packet
	 * 
	 * @param new chosen color
	 * @param player, whose color gets changed
	 * 
	 * @throws IllegalStateException if color already taken
	 */
	public boolean updateColor(Color color) {

		if(isColorTaken(color)) {
			return false;
		}
		
		Player player = getLocalPlayer();
		player.setColor(color);
		
		Paket paket = new ColorChangePaket(player);
		try {
			connectionService().sendPaket(paket);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/*
	 * checks, if lobby already contains a player, and if so, remove the player from
	 * the lobby. Then send a packet to notify the rest.
	 * 
	 * @param player of type Player
	 * 
	 * @throws IllegalStateException, if player not in lobby
	 */
	public void kickPlayer(Player player) {

		if (lobby.getPlayers().contains(player) && player != lobbyService().getLocalPlayer()) {
			if(gameService().getGame().getState() == GameState.INGAME) {
				player.setType(PlayerType.BOT);
				
				if(gameService().getGame().getPlayerOnTurn() == player) {
					botService().executeTurn(player);
				}

				Paket paket = new PlayerTypeChangePaket(player);

				try {
					connectionService().sendPaket(paket);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return;
			}
			lobby.getPlayers().remove(player);	
			
			System.out.println("Player kicked: " + player.getId());
			
			Paket paket = new PlayerKickPaket(player);

			try {
				connectionService().sendPaket(paket);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
			updateColorList(player.getColor());
	
		} else
			throw new IllegalStateException("Player not in lobby");
	}

	/*
	 * checks, if a username is already taken
	 * 
	 * @param String username
	 * 
	 * @return boolean
	 */
	public boolean isUsernameTaken(String username) {

		if(username == null || username.isBlank()) {
			return true;
		}
		
		for (Player p : lobby.getPlayers()) {
			if (username.equalsIgnoreCase(p.getUsername())) {
				return true;
			}
		}

		return false;
	}

	/*
	 * checks, if a username is already taken. if not, update it.
	 * 
	 * @param Player player
	 * 
	 * @param String username
	 */
	public boolean updateUsername(String username) {

		if (isUsernameTaken(username)) {
			return false;
		}

		Player player = getLocalPlayer();
		player.setUsername(username);

		Paket paket = new UsernameChangePaket(player);

		try {
			connectionService().sendPaket(paket);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a Bot to the lobby
	 * 
	 * @param bot
	 */
	public void addBot() {
		
		int smallest_bot_id = 0;
		
		for(Player p : lobby.getPlayers()) {
			if(p.getType() == PlayerType.BOT) {
				if(p.getId() < smallest_bot_id) {
					smallest_bot_id = p.getId();
				}
			}
		}

		Random random = gameService().getGame().getRandom();
		
		Color color = getFirstFreeColor();
		String name = BotUtil.randomName(random);
		
		while(isUsernameTaken(name)) {
			
			name = BotUtil.randomName(random);
		}
		
		addPlayer(new Player(PlayerType.BOT, smallest_bot_id - 1, name, color));
		
	}

	/**
	 * Returns the Player of this device
	 * 
	 * @return local player
	 */
	public Player getLocalPlayer() {
		return localPlayer.get();
	}

	/**
	 * Sets the local player of this device and adds him to the lobby
	 * 
	 * @param player
	 */
	public void setLocalPlayer(Player player) {
		
		this.localPlayer.set(player);
		addPlayer(player);
		
		System.out.println("Local Player set: " + player.getId());
	}

	/**
	 * Gets the player with the given player ID
	 * @param playerId
	 * @return player
	 */
	public Player getPlayer(int playerId) {
		for(Player p : lobby.getPlayers()) {
			if(p.getId() == playerId) {
				return p;
			}
		}
		return null;
	}

	/**
	 * get Local player Property
	 * @return property
	 */
	public ObjectProperty<Player> localPlayerProperty() {
		return localPlayer;
	}

	/**
	 * Get the first free color which is selectable
	 * @return color
	 */
	public Color getFirstFreeColor() {
		
		for(Color c : Color.values()) {
			boolean found = false;
			for(Player p : lobbyService().getLobby().getPlayers()) {
				if(p.getColor() == c) {
					found = true;
				}
			}
			if(!found) {
				return c;
			}
		}
		
		return null;
	}
	
	/**
	 * Determinates whether the color is already taken by a player or not
	 * @param c Color to check
	 * @return is color taken
	 */
	public boolean isColorTaken(Color c) {
		
		if(c == null) {
			return true;
		}
		
		for(Player p : lobbyService().getLobby().getPlayers() ) {
			if(p.getColor().equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	// direction true => up; direction false => down
	/**
	 * Moves a player in the player order in a given direction
	 * @param a Player to move
	 * @param direction true -> up, false -> down
	 * @return move was sucessful
	 */
	public boolean updateOrder(Player a, boolean direction) {
		
		ListProperty<Player> players = lobbyService().getLobby().getPlayers();
		
		if(players.size() <= 1) {
			return false;
		}
		
		int indexA = players.indexOf(a);
		int indexB;
		
		if(direction) {
			
			indexB = (players.size() + indexA - 1) % players.size();
			
		} else {
			
			indexB = (players.size() + indexA + 1) % players.size();
			
		}
		Collections.swap(players, indexA, indexB);
			
		Paket paket = new OrderChangePaket(getLocalPlayer(), indexA, indexB);
		
		try {
			connectionService().sendPaket(paket);
		} catch (IOException e) {
			return false;
		}
		
		return true;

	}

	/**
	 * Update taken colors when a new color is selected
	 * @param newValue Color
	 */
	public void updateColorList(Color newValue) {
		
		if(newValue != null) {
			
			if(getLocalPlayer() == null) {
				
			}
			
			else if(getLocalPlayer().getColor() != newValue) {
				takenColorList.remove(newValue);
			}
		
			for(Color c : Color.values()) {
				if(!isColorTaken(c) && !takenColorList.contains(c)) {
					takenColorList.add(c);
				}
			}
		}

	}

}
