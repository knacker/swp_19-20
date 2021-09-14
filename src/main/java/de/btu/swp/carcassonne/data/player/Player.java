/*
 * holds all the player data. that includes the name of a player, the player type, the the player id, the player color, aswell as the players points
 */
package de.btu.swp.carcassonne.data.player;

import de.btu.swp.carcassonne.data.card.Field;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Player {
	
	private final int id;
	private final ObjectProperty<PlayerType> type = new SimpleObjectProperty<>();
	private final StringProperty username = new SimpleStringProperty();
	private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
	private final SetProperty<Follower> followers = new SimpleSetProperty<>(FXCollections.observableSet());
	private final ColorBinding paint = new ColorBinding(this);
	private final IntegerProperty points = new SimpleIntegerProperty();
	private final ObjectProperty<Field> lastField = new SimpleObjectProperty<>();
	
	public Player(int id) {
		this(PlayerType.PLAYER, id);
		
	}
	
	public Player(PlayerType type, int id) {
		this.id = id;
		setType(type);
		
	}
	
	/*
	 * creates a player with a name and a color
	 * @param String username
	 * @param Color color
	 */
	public Player(int id, String username, Color color) {
		this(PlayerType.BOT, id, username, color);
		
	}
	
	/*
	 * creates a player with a name and a color
	 * @param String username
	 * @param Color color
	 */
	public Player(PlayerType type, int id, String username, Color color) {
		this(type, id);
		setColor(color);
		setUsername(username);
		
	}

	/*
	 * sets username
	 * @param String name
	 */
	public void setUsername(String name) {
		username.set(name);
	}
	
	/*
	 * returns username String
	 * @return username
	 */
	public String getUsername() {
		return username.get();
	}
	/*
	 * For java FX
	 * @return username property
	 */
	public StringProperty usernameProperty() {
		return username;
	}
	
	/*
	 * sets color
	 * @param Color dye
	 */
	public void setColor(Color dye) {
		color.set(dye);
	}
	/*
	 * @return color
	 */
	public Color getColor() {
		return color.get();
	}

	
	
	/*
	 * For java FX
	 * @return color property
	 */
	public ObjectProperty<Color> colorProperty() {
		return color;
	}
	
	/*
	 * for Java fx
	 * @return followers property
	 */
	public SetProperty<Follower> getFollowers() {
		return followers;
	}
	
	/*
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	public PlayerData toPlayerData() {
		return new PlayerData(type.get(), id, username.get(), color.get());
	}
	
	@Override
	public String toString() {
		return "{id=" + id + ", username=" + username.get() + ", color=" + color.get() + "}";
	}
	
	public javafx.scene.paint.Color getPaint() {
		return getColor().toPaint();
	}

	public ColorBinding paintProperty() {
		return paint;
	}
	
	public int getPoints() {
		return points.get();
	}
	
	public IntegerProperty pointsProperty() {
		return points;
	}
	
	public void setPoints(int p) {
		points.set(p);
	}
	
	public void addPoints(int p) {
		points.set(points.get() + p);
	}
	
	public PlayerType getType() {
		return type.get();
	}

	public void setType(PlayerType t) {
		type.set(t);
	}
	
	public ObjectProperty<PlayerType> typeProperty() {
		return type;
	}
	
	public Field getLastField() {
		return lastField.get();
	}
	
	public void setLastField(Field f) {
		lastField.set(f);
	}
	
	public ObjectProperty<Field> lastFieldProperty() {
		return lastField;
	}
	
}
