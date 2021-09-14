package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.data.player.BotBinding;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.beans.binding.IntegerBinding;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class PlayerController extends Controller {
	
	public final static IntegerBinding SEVEN = new IntegerBinding() {

		@Override
		protected int computeValue() {
			return 7;
		}
		
	};
	
	private final Player player;
	
	@FXML
	private Text usernameLabel;
	
	@FXML
	private ImageView colorRect;
	
	@FXML
	private Text pointsLabel;
	
	@FXML
	private Text typeLabel;
	
	@FXML
	private Text followerCount;
	
	public PlayerController(Player player) {
		this.player = player;
	}

	public void initialize() {
		
		if(lobbyService().getLocalPlayer() == player) {
			typeLabel.setText("Ich");
		} else {
			typeLabel.textProperty().bind(new BotBinding(player));
		}
		usernameLabel.textProperty().bind(player.usernameProperty());
		usernameLabel.underlineProperty().bind(gameService().getGame().playerOnTurnProperty().isEqualTo(player));
		Image img = new Image("/img/follower/" + player.getColor().name().toLowerCase() + ".png");
		colorRect.setImage(img);
		//colorRect.fillProperty().bind(player.paintProperty());
		pointsLabel.textProperty().bind(player.pointsProperty().asString());
		followerCount.textProperty().bind(SEVEN.subtract(player.getFollowers().sizeProperty()).asString());
	}

}
