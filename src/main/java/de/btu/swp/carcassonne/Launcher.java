package de.btu.swp.carcassonne;

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) throws Exception {
		System.out.println("Main is starting the app");
		Application.launch(App.class, args);
		System.out.println("Main has closed the app");
	}
}
