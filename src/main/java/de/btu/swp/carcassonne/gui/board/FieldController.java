package de.btu.swp.carcassonne.gui.board;

import de.btu.swp.carcassonne.data.card.Card;
import de.btu.swp.carcassonne.data.card.Field;
import de.btu.swp.carcassonne.gui.BoardController;
import de.btu.swp.carcassonne.gui.Controller;
import javafx.animation.FillTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FieldController extends Controller {
	
	public final static Color PLACEHOLDER_COLOR = Color.LIGHTGRAY;
	public final static Color PLACEHOLDER_ERROR = Color.RED.brighter();
	public final static Duration PLACEHOLDER_DURATION = Duration.millis(500);
	
	private final BoardController boardController;
	
	public FieldController(BoardController boardController) {
		this.boardController = boardController;
	}
	
	public void initialize() {
		
		ListProperty<Field> fields = gameService().getGame().getBoard();
		
		fields.addListener(new ListChangeListener<>() {

			@Override
			public void onChanged(Change<? extends Field> c) {

				while (c.next()) {
					for (Field f : c.getAddedSubList()) {
						addField(f);
					}
					if(c.getList().size() == 0) {
						boardController.grid.getChildren().clear();
					}
				}
			}
		});
		
		for (Field f : fields) {
			addField(f);
		}
	}

	private void addField(Field f) {

		final DoubleProperty FIELD_SIZE = settingsService().getSettings().fieldSizeProperty();
		final DoubleBinding FIELD_SIZE_SPACE = settingsService().getSettings().fieldSizeSpaceProperty();
		
		Node node;
		
		if(f.getCard() == Card.PLACEHOLDER) {
			node = createPlaceholder(f, FIELD_SIZE);
		} else {
			node = createCard(f, FIELD_SIZE);
		}

		node.translateXProperty().bind(FIELD_SIZE_SPACE.multiply(f.getX()));
		node.translateYProperty().bind(FIELD_SIZE_SPACE.multiply(f.getY()));
		
		boardController.grid.getChildren().add(node);
	}
	
	private Node createPlaceholder(Field f, DoubleProperty FIELD_SIZE) {

		Rectangle placeholder = new Rectangle();
		placeholder.setFill(PLACEHOLDER_COLOR);
		
		placeholder.widthProperty().bind(FIELD_SIZE);
		placeholder.heightProperty().bind(FIELD_SIZE);
		
		FillTransition error = new FillTransition(PLACEHOLDER_DURATION, placeholder, PLACEHOLDER_ERROR, PLACEHOLDER_COLOR);
		
		placeholder.setOnMouseClicked((event) -> {
			if(event.getButton() == MouseButton.PRIMARY) {
				
				boolean placed = gameService().fieldService().addField(f.getX(), f.getY());
				
				if(placed) {
					
					boardController.grid.getChildren().remove(event.getSource());
				} else {
					error.playFromStart();
				}
				
			}
		});
		
		placeholder.visibleProperty().bind(gameService().areCardsPlaceable());
		
		return placeholder;
	}
	
	private Node createCard(Field f, DoubleProperty FIELD_SIZE) {
		ImageView img = new ImageView(f.getCard().getPath());

		img.setRotate(f.getCard().getRotation().toDouble());
		
		img.fitWidthProperty().bind(FIELD_SIZE);
		img.fitHeightProperty().bind(FIELD_SIZE);
		
		return img;
	}

}
