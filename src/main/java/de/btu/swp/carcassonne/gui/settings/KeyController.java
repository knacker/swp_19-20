package de.btu.swp.carcassonne.gui.settings;

import java.util.HashSet;

import com.google.common.collect.Sets;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class KeyController extends SettingController {
	
	public final static HashSet<KeyCode> UNALLOWED_CODES = Sets.newHashSet(
			KeyCode.ENTER
			);

	public KeyController(String title, ObjectProperty<KeyCode> keyCode) {
		super(title);
		
		TextField keyCodeDisplay = new TextField();
		keyCodeDisplay.textProperty().bind(keyCode.asString());
		keyCodeDisplay.setEditable(false);
		
		Button pickKeyCode = new Button("Auswählen");
		
		pickKeyCode.setOnAction((e) -> {
			keyCodeDisplay.requestFocus();
			
		});
		
		keyCodeDisplay.setOnKeyReleased((event) -> {
			if(!UNALLOWED_CODES.contains(event.getCode())) {
				
				keyCode.set(event.getCode());
				pickKeyCode.requestFocus();
			}
			
			event.consume();
		});
		
		keyCodeDisplay.setFocusTraversable(false);
		
		keyCodeDisplay.focusedProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(newValue) {
					pickKeyCode.setText("Drücke eine Taste");
				} else {
					pickKeyCode.setText("Auswählen");
				}
				
			}
		});
		
		
		HBox hbox = new HBox(keyCodeDisplay, pickKeyCode);
		hbox.setSpacing(20);
		
		setControl(hbox);
	}

}
