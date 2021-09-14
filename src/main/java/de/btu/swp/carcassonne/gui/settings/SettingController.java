package de.btu.swp.carcassonne.gui.settings;

import de.btu.swp.carcassonne.gui.Controller;
import de.btu.swp.carcassonne.gui.SettingsController;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SettingController extends Controller {

	private SettingsController settingsController;
	
	private final Label titleLabel = new Label();
	private final AnchorPane parent = new AnchorPane();
	private Node control;
	
	public SettingController(String title) {
		
		titleLabel.setText(title);
		titleLabel.getStyleClass().add("font-white");
	}
	
	public void setSettingsController(SettingsController controller) {
		if(settingsController != null) {
			throw new IllegalStateException("Controller already initialized");
		}
		settingsController = controller;
		
		AnchorPane.setRightAnchor(titleLabel, 0.0);
		AnchorPane.setTopAnchor(titleLabel, 0.0);
		AnchorPane.setLeftAnchor(titleLabel, 0.0);
		
		parent.getChildren().add(titleLabel);
	}
	
	@Override
	public final void initialize() {
		settingsController.settings.getChildren().add(parent);
	}
	
	public void setControl(Node control) {
		
		if(this.control != null) {
			parent.getChildren().remove(this.control);
		}
		
		this.control = control;
		
		AnchorPane.setLeftAnchor(control, 0.0);
		AnchorPane.setTopAnchor(control, 30.0);
		AnchorPane.setRightAnchor(control, 0.0);
		
		parent.getChildren().add(control);
		
	}
	
	public Node getControl() {
		return control;
	}
}
