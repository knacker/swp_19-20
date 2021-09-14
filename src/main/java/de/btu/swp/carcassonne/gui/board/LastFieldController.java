package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LastFieldController extends Controller {
	
	private final BoardController boardController;

	public LastFieldController(BoardController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void initialize() {
		for(Player p : lobbyService().getLobby().getPlayers()) {
			showLastField(p);
		}
		
	}
	
	private void showLastField(Player p) {
		
		final DoubleProperty FIELD_SIZE = settingsService().getSettings().fieldSizeProperty();
		final DoubleBinding FIELD_SIZE_SPACE = settingsService().getSettings().fieldSizeSpaceProperty();
		
		Rectangle rect = new Rectangle();
		rect.widthProperty().bind(FIELD_SIZE);
		rect.heightProperty().bind(FIELD_SIZE);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(p.getColor().toPaint());
		rect.setStrokeWidth(2);
		rect.setVisible(false);
		rect.setViewOrder(-1);
		
		boardController.grid.getChildren().add(rect);
		
		p.lastFieldProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Field> observable, Field oldValue, Field newValue) {
				if(newValue == null) {
					rect.setVisible(false);
				} else {
					rect.setVisible(true);
					
					rect.translateXProperty().bind(FIELD_SIZE_SPACE.multiply(newValue.getX()));
					rect.translateYProperty().bind(FIELD_SIZE_SPACE.multiply(newValue.getY()));
				}
			}
			
		});
		
	}

}
