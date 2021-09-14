package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class HeaderController extends Controller {
	
	private final BoardController boardController;

	private HBox header = new HBox();
	
	private DropShadow shadow = new DropShadow(2, 1, 1, Color.BLACK);
	
	public HeaderController(BoardController boardController) {
		this.boardController = boardController;
	}
	
	public void initialize() {
		
		AnchorPane.setLeftAnchor(header, 10.0);
		AnchorPane.setTopAnchor(header, 10.0);
		AnchorPane.setRightAnchor(header, 10.0);
		
		header.setSpacing(20);
		header.setAlignment(Pos.TOP_CENTER);
		header.setPrefHeight(70);
		
		lobbyService().getLobby().getPlayers().addListener(new ListChangeListener<>() {

			@Override
			public void onChanged(Change<? extends Player> c) {
				
				updateHeader();
			}
			
		});
		updateHeader();
		
		boardController.root.getChildren().add(1, header);
	}
	
	public void updateHeader() {
		
		header.getChildren().clear();
		
		Node data = app().loadFxml("board/data", new DataController());
		data.setEffect(shadow);
		
		header.getChildren().add(data);
		
		for(Player p : lobbyService().getLobby().getPlayers()) {

			Node node = app().loadFxml("board/player", new PlayerController(p));
			node.setEffect(shadow);
			
			header.getChildren().add(node);
		}
		
	}
}
