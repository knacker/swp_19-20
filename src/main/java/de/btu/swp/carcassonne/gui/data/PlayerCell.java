package de.btu.swp.carcassonne.gui.data;

import de.btu.swp.carcassonne.App;
import de.btu.swp.carcassonne.data.player.Color;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.util.GlyphUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

public class PlayerCell extends ListCell<Player> {

	public final static double ICON_SIZE = 20;

	private final App app;

	public PlayerCell(App app) {
		this.app = app;
	}

	@Override
	protected void updateItem(Player player, boolean empty) {
		super.updateItem(player, empty);

		if (empty || player == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			
			/*
			 * kick button, nur für host sichtbar
			 */
			ImageView kickImg = new ImageView("/img/kick2.png");				
			kickImg.setFitHeight(ICON_SIZE);
			kickImg.setFitWidth(ICON_SIZE);				
			Button kick = new Button(null, kickImg);				
			
			kick.setOnAction((event) -> {
					app.carcassonneService().lobbyService().kickPlayer(player);
			});
			kick.setVisible(app.carcassonneService().connectionService().isServer() && app.carcassonneService().lobbyService().getLocalPlayer() != player);		
			
			VBox arrows;
			
			if(app.carcassonneService().connectionService().isServer()) {
				
				Button arrow_up = new Button(null, GlyphUtil.up());
				Button arrow_down = new Button(null, GlyphUtil.down());

				arrow_up.setPadding(new Insets(0,15,0,15));
				arrow_down.setPadding(new Insets(0,15,0,15));
				
				arrow_up.setOnAction((event) -> {
					app.carcassonneService().lobbyService().updateOrder(player, true);
				});
				
				arrow_down.setOnAction((event) -> {
					app.carcassonneService().lobbyService().updateOrder(player, false);
				});
				
				arrows = new VBox(arrow_up, arrow_down);
				arrows.setSpacing(2);
			} else {
				arrows = new VBox();
			}

			Label usernameLabel = new Label();
			usernameLabel.textProperty().bind(player.usernameProperty());
			usernameLabel.setAlignment(Pos.CENTER_LEFT);
			usernameLabel.getStyleClass().addAll("carcassonne-font", "font-white");

			Node color;
			
			if (player == app.carcassonneService().lobbyService().getLocalPlayer()) {
				
				ComboBox<Color> colorBox = new ComboBox<>(app.carcassonneService().lobbyService().getColorList());
				
				colorBox.setVisibleRowCount(Color.values().length);
				colorBox.setValue(player.getColor());
				colorBox.valueProperty().bind(player.colorProperty());
				colorBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {

					@Override
					public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
						if(newValue != null) {
							
							app.carcassonneService().lobbyService().updateColor(newValue);
							
							colorBox.getSelectionModel().clearSelection();	
							
						}
					}
					
				});
				colorBox.setCellFactory(new Callback<ListView<Color>, ListCell<Color>>() {

					@Override
					public ListCell<Color> call(ListView<Color> param) {
						return new ListCell<Color>() {
							
							@Override
							protected void updateItem(Color item, boolean empty) {
								super.updateItem(item, empty);

								if (empty || item == null) {
									setGraphic(null);
								} else {
									Rectangle rect = new Rectangle(ICON_SIZE, ICON_SIZE, item.toPaint());
									setGraphic(rect);
								}
							}
						};
					}

				});
				colorBox.setButtonCell(new ListCell<Color>() {
					@Override
					protected void updateItem(Color item, boolean empty) {
						super.updateItem(item, empty);

						if (empty || item == null) {
							setGraphic(null);
						} else {
							Rectangle rect = new Rectangle(ICON_SIZE, ICON_SIZE, item.toPaint());
							setGraphic(rect);
						}
					}

				});
				
				color = colorBox;
			} else {
				Rectangle rect = new Rectangle(ICON_SIZE, ICON_SIZE);
				rect.fillProperty().bind(player.paintProperty());
				
				color = rect;
			}

			HBox row = new HBox(arrows, usernameLabel, color, kick);
			row.setSpacing(20);
			row.setAlignment(Pos.CENTER_LEFT);
			row.getStyleClass().add("custom-cell");

			setGraphic(row);
		}
		
	}

}
