package de.btu.swp.carcassonne.gui.tutorial;

import de.btu.swp.carcassonne.gui.Controller;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PopupController extends Controller {
	
	private DoubleProperty absX = new SimpleDoubleProperty();
	private DoubleProperty absY = new SimpleDoubleProperty();

	private DoubleProperty relX = new SimpleDoubleProperty();
	private DoubleProperty relY = new SimpleDoubleProperty();
	
	private ReadOnlyDoubleProperty width, height;
	
	private Runnable runnable;
	
	@FXML
	private Button button;
	
	@FXML
	private Text text;
	
	@FXML
	private AnchorPane root;
	
	public PopupController(AnchorPane parent, Runnable runnable) {
		width = parent.widthProperty();
		height = parent.heightProperty();
		this.runnable = runnable;
	}

	@FXML
	public void initialize() {
		DropShadow shadow = new DropShadow(2, 1, 1, Color.BLACK);
		root.translateXProperty().bind(width.multiply(relX).add(absX).subtract(root.widthProperty().divide(2)));
		root.translateYProperty().bind(height.multiply(relY).add(absY).subtract(root.heightProperty().divide(2)));
		button.setOnAction((event) -> runnable.run());
		root.setEffect(shadow);
	}
	
	public void setText(String s) {
		text.setText(s);
	}
	
	public void setX(double relX, double absX) {
		this.relX.set(relX / 100.0);
		this.absX.set(absX);
	}
	
	public void setY(double relY, double absY) {
		this.relY.set(relY / 100.0);
		this.absY.set(absY);
	}
	
	public void showButton() {
		button.setVisible(true);
	}
	
	public void hideButton() {
		button.setVisible(false);
	}
	
	public void showPopup() {
		root.setVisible(true);
	}
	
	public void hidePopup() {
		root.setVisible(false);
	}
	
	public void setSize(double width, double height) {
		root.setPrefSize(width, height);
	}

	public void setAction(Runnable runnable) {
		this.runnable = runnable;
	}
}
