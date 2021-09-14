package de.btu.swp.carcassonne.gui.settings;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Slider;

public class SliderController extends SettingController {
	
	public SliderController(String title, DoubleProperty value, double min, double max, double major, int minor) {
		
		super(title);
		
		Slider slider = new Slider();
		slider.valueProperty().bindBidirectional(value);
		slider.setMin(min);
		slider.setMax(max);
		slider.setMajorTickUnit(major);
		slider.setMinorTickCount(minor);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.getStyleClass().addAll("carcassonne-font", "font-white");
		
		setControl(slider);
	}

}
