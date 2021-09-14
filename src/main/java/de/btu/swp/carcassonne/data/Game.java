/*
 * holds the propertys of the game(list of followers, board, remaining cards, playerOnturn, gamestate and the current card
 */
package de.btu.swp.carcassonne.data;

import java.util.LinkedList;
import java.util.Random;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.util.CardUtil;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class Game {

	private final long seed;
	private final ListProperty<Field> board = new SimpleListProperty<>();
	private final ListProperty<Follower> followers = new SimpleListProperty<>();
	private final ListProperty<Card> remainingCards = new SimpleListProperty<>();
	private final Random random;
	private final ObjectProperty<Player> playerOnTurn = new SimpleObjectProperty<>();
	private final ObjectProperty<GameState> state = new SimpleObjectProperty<>();
	private final ObjectProperty<Card> currentCard = new SimpleObjectProperty<>();
	
	public Game() {
		this.seed = System.currentTimeMillis();
		this.random = new Random(seed);
		
		board.set(FXCollections.observableList(new LinkedList<>()));
		followers.set(FXCollections.observableList(new LinkedList<>()));
		remainingCards.set(FXCollections.observableList(CardUtil.createAllCards()));
		FXCollections.shuffle(remainingCards, random);
	}
	
	public long getSeed() {
		return seed;
	}
	
	public ListProperty<Field> getBoard() {
		return board;
	}
	
	public ListProperty<Card> getRemainingCards() {
		return remainingCards;
	}
	
	public Random getRandom() {
		return random;
	}
	
	public Player getPlayerOnTurn() {
		return playerOnTurn.get();
	}
	
	public void setPlayerOnTurn(Player player) {
		playerOnTurn.set(player);
	}
	
	public ObjectProperty<Player> playerOnTurnProperty() {
		return playerOnTurn;
	}
	
	public GameState getState() {
		return state.get();
	}
	
	public void setState(GameState s) {
		state.set(s);
	}
	
	public ObjectProperty<GameState> stateProperty() {
		return state;
	}
	
	public Card getCurrentCard() {
		return currentCard.get();
	}
	
	public void setCurrentCard(Card c) {
		currentCard.set(c);
	}
	
	public ObjectProperty<Card> currentCardProperty() {
		return currentCard;
	}
	
	public ListProperty<Follower> getFollowers() {
		return followers;
	}
	
}
