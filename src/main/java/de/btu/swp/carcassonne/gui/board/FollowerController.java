package de.btu.swp.carcassonne.gui.board;

import java.util.HashSet;

import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.card.Rotation;
import de.btu.swp.carcassonne.data.graph.Vector;
import de.btu.swp.carcassonne.data.player.Follower;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class FollowerController extends Controller {
	private static int counter = 0;
	private static final double CIRCLE_RADIUS = 8;
	private final BoardController boardController;

	public FollowerController(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void initialize() {
		
		ObservableList<Follower> followers = gameService().getGame().getFollowers();
		
		followers.addListener(new ListChangeListener<>() {

			@Override
			public void onChanged(Change<? extends Follower> f) {
				while (f.next()) {
					for (Follower fo : f.getAddedSubList()) {
						addFollower(fo);
					}
					
					for(Follower fo : f.getRemoved()) {
						removeFollower(fo);
					}
				}
			}
		});
		
		lobbyService().getLocalPlayer().lastFieldProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Field> observable, Field oldValue, Field newValue) {
				if (newValue != null && gameService().playerService().isPlayerOnTurn().get() && gameService().getGame().getPlayerOnTurn().getType() != PlayerType.BOT ) {	
					
					if(lobbyService().getLocalPlayer().getType() == PlayerType.PLAYER) {
						showPossibleFollowers(newValue);
					}
				}
			}
		});
	}

	private void addFollower(Follower f) {

		final DoubleProperty FIELD_SIZE = settingsService().getSettings().fieldSizeProperty();
		final DoubleBinding FIELD_SIZE_SPACE = settingsService().getSettings().fieldSizeSpaceProperty();
		final DoubleBinding FIELD_SIZE_HALF = settingsService().getSettings().fieldSizeHalfProperty();
		
		ImageView img = new ImageView("/img/follower/" + f.getColor().name().toLowerCase() + ".png");
		
		f.setNode(img);
		
		DoubleBinding translateX = FIELD_SIZE_SPACE.multiply(f.getField().getX());
		DoubleBinding translateY = FIELD_SIZE_SPACE.multiply(f.getField().getY());
		
		double width = img.getImage().getWidth();
		double height = img.getImage().getHeight();
	
		Rotation r = f.getDirectionOnField();
		
		if(r == null) {
			translateX = translateX.add(FIELD_SIZE_HALF).subtract(width / 2);
			translateY = translateY.add(FIELD_SIZE_HALF).subtract(height / 2);
			
		} else {
			translateX = translateX.add(FIELD_SIZE.multiply(r.getXOffset()));
			translateY = translateY.add(FIELD_SIZE.multiply(r.getYOffset()));

			switch(r) {
			case EAST:
				translateX = translateX.subtract(width);
				translateY = translateY.subtract(height / 2);
				break;
			case NORTH:
				translateX = translateX.subtract(width / 2);
				break;
			case SOUTH:
				translateX = translateX.subtract(width / 2);
				translateY = translateY.subtract(height);
				break;
			case WEST:
				translateY = translateY.subtract(height / 2);
				break;
			}
		}
		
		img.translateXProperty().bind(translateX);
		img.translateYProperty().bind(translateY);
		
		boardController.grid.getChildren().add(f.getNode());
	}
	
	private void removeFollower(Follower f) {
		boardController.grid.getChildren().remove(f.getNode());
	}

	private void showPossibleFollowers(Field f) {
		
		boardController.skipFollowerBtn.setVisible(true);
		Player p = gameService().getGame().getPlayerOnTurn();
		
		if(p.getFollowers().size() >= 7) {
			boardController.skipFollowerBtn.setVisible(false);
			gameService().followerService().placeNoFollower(f);
			return;
		}
		
		for(Rotation r : Rotation.values()) {
			showFollower(f, r);
		}
		showFollower(f, null);
		if(counter == 5 ) {
			counter= 0;
			boardController.skipFollowerBtn.setVisible(false);
			gameService().followerService().placeNoFollower(f);		
			
		}	
		counter= 0;
		
	}
	
	private void showFollower(Field f, Rotation r) {

		final DoubleProperty FIELD_SIZE = settingsService().getSettings().fieldSizeProperty();
		final DoubleBinding FIELD_SIZE_SPACE = settingsService().getSettings().fieldSizeSpaceProperty();
		final DoubleBinding FIELD_SIZE_HALF = settingsService().getSettings().fieldSizeHalfProperty();
		
		boardController.skipFollowerBtn.setVisible(true);
		Vector v = f.getCard().getVector(r);
		if(v != null) {
		if(!gameService().followerService().checkClaimable(v)) {
			counter++;
			return;
		
		}
		}if(v == null) {
			counter++;
			return;
		}
		Player p = gameService().getGame().getPlayerOnTurn();
		
		DoubleBinding translateX = FIELD_SIZE_SPACE.multiply(f.getX());
		DoubleBinding translateY = FIELD_SIZE_SPACE.multiply(f.getY());
	
		if(r == null) {
			translateX = translateX.add(FIELD_SIZE_HALF);
			translateY = translateY.add(FIELD_SIZE_HALF);
			
		} else {
			translateX = translateX.add(FIELD_SIZE.multiply(r.getXOffset()));
			translateY = translateY.add(FIELD_SIZE.multiply(r.getYOffset()));

			switch(r) {
			case EAST:
				translateX = translateX.subtract(CIRCLE_RADIUS);
				break;
			case NORTH:
				translateY = translateY.add(CIRCLE_RADIUS);
				break;
			case SOUTH:
				translateY = translateY.subtract(CIRCLE_RADIUS);
				break;
			case WEST:
				translateX = translateX.add(CIRCLE_RADIUS);
				break;
			}
		}
	
		
		Circle c = new Circle();

		c.setRadius(CIRCLE_RADIUS);
		c.translateXProperty().bind(translateX);
		c.translateYProperty().bind(translateY);
		c.setFill(p.getPaint());
		
		c.setViewOrder(-2);
		
		c.setOnMouseClicked((event) -> {
			boardController.skipFollowerBtn.setVisible(false);
			gameService().followerService().placeFollower(v);
			removePossibleFollowers();
		});
		
		boardController.grid.getChildren().add(c);
	}

	private void removePossibleFollowers() {
		HashSet<Node> followers = new HashSet<>();
		
		for(Node n : boardController.grid.getChildren()) {
			if(n instanceof Circle) {
				followers.add(n);
			}
		}
		
		boardController.grid.getChildren().removeAll(followers);
	}

}
