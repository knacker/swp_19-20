/*
 * used for the tutorial
 */

package de.btu.swp.carcassonne.data.tutorial;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Platform;

public interface ExecuteTutorialState {
	
	default void execute(TutorialState state) {
		String method = state == null ? "Null" : state.toString();
		Platform.runLater(() -> {
			try {
				getClass().getMethod("execute" + method).invoke(this);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void executeNull();

	public void executeCardStart();
	
	public void executeCardTown();
	
	public void executeRotation();
	
	public void executePlaceCard();
	
	public void executePlaceFollower();
	
	public void executeCardStreet();
	
	public void executeCardTownStreet();
	
	public void executeCardMonas();
	
	public void executeCardMonasStreet();
	
	public void executeCardTownShield();
	
	public void executeReset();
	
	public void executeStructureFinishTown();
	
	public void executePointsTown();
	
	public void executeStructureFinishStreet();
	
	public void executePointsStreet();
	
	public void executeStructureFinishMonas();
	
	public void executePointsMonas();
	
	public void executeBiggerField();
	
	public void executeOtherFollower();
	
	public void executeGameEnd();
	
}
