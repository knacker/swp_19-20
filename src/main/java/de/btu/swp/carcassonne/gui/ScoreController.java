package de.btu.swp.carcassonne.gui;

import java.util.List;

import de.btu.swp.carcassonne.data.GameState;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.gui.score.DimensionBinding;
import de.btu.swp.carcassonne.gui.score.PositionBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

public class ScoreController extends Controller {

	@FXML
	private ImageView scoreboard;
	
	@FXML
	private TilePane board;
	@FXML
	public Button leaveGameAtEnd;
	@FXML
	private Group followers;
	@FXML
	public Text firstPlace;
	@FXML
	public Text secondPlace;
	@FXML
	public Text thirdPlace;
	@FXML
	public Text fourthPlace;
	@FXML
	public Text fifthPlace;
	@FXML
	public Text winner;

	
	@FXML
	public void initialize() {
		scoreboard.fitHeightProperty().bind(board.heightProperty().multiply(4).divide(5));
		scoreboard.setFitWidth(0);
		firstPlace.setVisible(false);
		secondPlace.setVisible(false);
		thirdPlace.setVisible(false);
		fourthPlace.setVisible(false);
		fifthPlace.setVisible(false);
		winner.setVisible(false);
		leaveGameAtEnd.setVisible(false);
		
		if(gameService().getGame().getState() == GameState.SCORE)
		{
			showWinners();
		}
		for(Player p : lobbyService().getLobby().getPlayers()) {
			addFollower(p);
		}
	}
	
	private void addFollower(Player p) {
		ImageView img = new ImageView("/img/follower/" + p.getColor().name().toLowerCase() + ".png");
		followers.getChildren().add(img);
		
		DimensionBinding width = new DimensionBinding(scoreboard, true);
		DimensionBinding height = new DimensionBinding(scoreboard, false);
		
		img.fitHeightProperty().bind(height.divide(20));
		img.setFitWidth(0);
		img.setPreserveRatio(true);
		
		PositionBinding posX = new PositionBinding(p, true);
		PositionBinding posY = new PositionBinding(p, false);

		img.translateYProperty().bind(height.multiply(posY));
		img.translateXProperty().bind(width.multiply(posX));
	}

	@FXML
	public void showBoard(ActionEvent e) {
		app().openBoardScreen();
	}
	public void showWinners() {
		List<Player> winnerlist = gameService().scoreService().evaluateWinner();
		leaveGameAtEnd.setVisible(true);
		switch(lobbyService().getLobby().getPlayers().size()) {
		
		case 2:
			firstPlace.setVisible(true);
			firstPlace.setFill(winnerlist.get(0).getPaint());
			secondPlace.setVisible(true);
			secondPlace.setFill(winnerlist.get(1).getPaint());
			firstPlace.setText("1. " + winnerlist.get(0).getUsername() + " : " + winnerlist.get(0).getPoints() + " Punkte" );
			secondPlace.setText("2. " + winnerlist.get(1).getUsername() + " : " + winnerlist.get(1).getPoints() + " Punkte" );
			adraw(winnerlist);
			break;
		case 3:
			firstPlace.setVisible(true);
			firstPlace.setFill(winnerlist.get(0).getPaint());
			secondPlace.setVisible(true);
			secondPlace.setFill(winnerlist.get(1).getPaint());
			thirdPlace.setVisible(true);
			thirdPlace.setFill(winnerlist.get(2).getPaint());
			firstPlace.setText("1. " + winnerlist.get(0).getUsername() + " : " + winnerlist.get(0).getPoints() + " Punkte" );
			secondPlace.setText("2. " + winnerlist.get(1).getUsername() + " : " + winnerlist.get(1).getPoints() + " Punkte" );
			thirdPlace.setText("3. " + winnerlist.get(2).getUsername() + " : " + winnerlist.get(2).getPoints() + " Punkte" );
			adraw(winnerlist);
			break;
		case 4:
			firstPlace.setVisible(true);
			firstPlace.setFill(winnerlist.get(0).getPaint());
			secondPlace.setVisible(true);
			secondPlace.setFill(winnerlist.get(1).getPaint());
			thirdPlace.setVisible(true);
			thirdPlace.setFill(winnerlist.get(2).getPaint());
			fourthPlace.setVisible(true);
			fourthPlace.setFill(winnerlist.get(3).getPaint());
			firstPlace.setText("1. " + winnerlist.get(0).getUsername() + " : " + winnerlist.get(0).getPoints() + " Punkte" );
			secondPlace.setText("2. " + winnerlist.get(1).getUsername() + " : " + winnerlist.get(1).getPoints() + " Punkte" );
			thirdPlace.setText("3. " + winnerlist.get(2).getUsername() + " : " + winnerlist.get(2).getPoints() + " Punkte" );
			fourthPlace.setText("4. " + winnerlist.get(3).getUsername() + " : " + winnerlist.get(3).getPoints() + " Punkte" );
			adraw(winnerlist);
			break;
		case 5:
			firstPlace.setVisible(true);
			firstPlace.setFill(winnerlist.get(0).getPaint());
			secondPlace.setVisible(true);
			secondPlace.setFill(winnerlist.get(1).getPaint());
			thirdPlace.setVisible(true);
			thirdPlace.setFill(winnerlist.get(2).getPaint());
			fourthPlace.setVisible(true);
			fourthPlace.setFill(winnerlist.get(3).getPaint());
			fifthPlace.setVisible(true);
			fifthPlace.setFill(winnerlist.get(4).getPaint());
			firstPlace.setText("1. " + winnerlist.get(0).getUsername() + " : " + winnerlist.get(0).getPoints() + " Punkte" );
			secondPlace.setText("2. " + winnerlist.get(1).getUsername() + " : " + winnerlist.get(1).getPoints() + " Punkte" );
			thirdPlace.setText("3. " + winnerlist.get(2).getUsername() + " : " + winnerlist.get(2).getPoints() + " Punkte" );
			fourthPlace.setText("4. " + winnerlist.get(3).getUsername() + " : " + winnerlist.get(3).getPoints() + " Punkte" );
			fifthPlace.setText("5. " + winnerlist.get(4).getUsername() + " : " + winnerlist.get(4).getPoints() + " Punkte" );
			adraw(winnerlist);
			break;						
		}
	}
		
	public void adraw(List<Player> winnerlist) {
		boolean nodraw = true;
		winner.setVisible(true);
		switch(winnerlist.size()) {
		case 2:
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints()) {
				winner.setText(winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername() +  " haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			break;
		case 3:
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints()) {
				winner.setText(winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername() +  " haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() ) {
				winner.setText( "Alle haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			break;
		case 4:
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints()) {
				winner.setText(winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername() +  " haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() ) {
				winner.setText( winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername()  + " && " + winnerlist.get(2).getUsername() + "haben haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() && winnerlist.get(2).getPoints() == winnerlist.get(3).getPoints() ) {
				winner.setText( "Alle haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			break;
		case 5:
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints()) {
				winner.setText(winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername() +  " haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() ) {
				winner.setText( winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername()  + " && " + winnerlist.get(2).getUsername() + "haben haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() && winnerlist.get(2).getPoints() == winnerlist.get(3).getPoints() ) {
				winner.setText( winnerlist.get(0).getUsername() + " && " + winnerlist.get(1).getUsername()  + " && " + winnerlist.get(2).getUsername() +  " && " + winnerlist.get(3).getUsername() + "haben haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			if(winnerlist.get(0).getPoints() == winnerlist.get(1).getPoints() && winnerlist.get(1).getPoints() == winnerlist.get(2).getPoints() && winnerlist.get(2).getPoints() == winnerlist.get(3).getPoints()  && winnerlist.get(3).getPoints() == winnerlist.get(4).getPoints()) {
				winner.setText( "Alle haben das Spiel gewonnen !! Herzlichen Glückwunsch !!");
				nodraw = false;
			}
			break;
		}
		if(nodraw) {
		
		winner.setFill(winnerlist.get(0).getPaint());
		winner.setText(winnerlist.get(0).getUsername() + " Hat das Spiel gewonnen !! Herzlichen Glückwunsch !!");
		}
	}

	@FXML
	public void leaveGame() {
		app().openStartScreen();
	}
}
