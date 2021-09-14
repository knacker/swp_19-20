package de.btu.swp.carcassonne.gui;

import de.btu.swp.carcassonne.data.tutorial.ExecuteTutorialState;
import de.btu.swp.carcassonne.data.tutorial.TutorialState;
import de.btu.swp.carcassonne.gui.tutorial.PopupController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TutorialController extends Controller implements ExecuteTutorialState {

	@FXML
	private AnchorPane root;

	private PopupController popup;

	@FXML
	public void initialize() {
		
		tutorialService().start();

		AnchorPane board = app().loadFxml("board", new BoardController());
		AnchorPane.setTopAnchor(board, 0.0);
		AnchorPane.setRightAnchor(board, 0.0);
		AnchorPane.setBottomAnchor(board, 0.0);
		AnchorPane.setLeftAnchor(board, 0.0);

		popup = new PopupController(root, tutorialService()::nextState);
		AnchorPane node = app().loadFxml("popup", popup);

		root.getChildren().add(board);
		root.getChildren().add(node);

		tutorialService().getTutorial().stateProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends TutorialState> observable, TutorialState oldValue,
					TutorialState newValue) {
				execute(newValue);
			}

		});

		popup.setText("Willkommen im Tutorial!" + System.lineSeparator()
				+ "Hier lernst du die Grundlagen von Carcassonne kennen" + System.lineSeparator()
				+ "und kannst dich etwas mit dem Spiel vertraut machen.");
		popup.setSize(500, 160);
		popup.setX(50, 0);
		popup.setY(50, 45);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeNull() {
		popup.setText("Das Tutorial ist zu Ende und du kannst" + System.lineSeparator() + 
				"in das richtige Spiel einsteigen.");
		popup.setSize(520, 140);
		popup.setX(50, 0);
		popup.setY(50, 45);
		popup.setAction(app()::openStartScreen);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeCardStart() {
		
		final double FIELD_SIZE = settingsService().getSettings().fieldSizeProperty().get();
		
		popup.setText("Das ist die Startkarte." + System.lineSeparator() + 
				"Sie beeinhaltet auf der einen Seite eine Stadt, " + System.lineSeparator() + 
				"eine gerade Straße und auf der letzten Seite ein Stück Wiese.");
		popup.setSize(520, 160);
		popup.setX(50, 260 + FIELD_SIZE);
		popup.setY(50, 45);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeCardTown() {
		popup.setText("Hier siehst du die aktuelle Karte" + System.lineSeparator() + 
				"welche der Spieler der am Zug ist benutzen kann.");
		popup.setSize(480, 140);
		popup.setX(100, -250);
		popup.setY(100, -242.4);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeRotation() {
		popup.setText("Um Karten legen zu können musst du diese" + System.lineSeparator() + 
				"ggf. drehen. Probiere es doch mal aus.");
		popup.setSize(400, 100);
		popup.setX(100, -210);
		popup.setY(100, -222.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executePlaceCard() {
		popup.setText("Jetzt rotiere dir die Karte so wie du willst" + System.lineSeparator() + 
				"und lege sie an das Spielfeld an.");
		popup.setSize(400, 100);
		popup.setX(100, -210);
		popup.setY(100, -222.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executePlaceFollower() {
		
		final double FIELD_SIZE = settingsService().getSettings().fieldSizeProperty().get();
		
		popup.setText("Du kannst nun die Struktur in Anspruchen nehmen," + System.lineSeparator() + 
				"indem du einen Gefolgsmann darauf platzierst." + System.lineSeparator() +
				"Auf jeder Struktur darf nur ein Gefolgsmann stehen.");
		popup.setSize(520, 120);
		popup.setX(50, 260 + FIELD_SIZE);
		popup.setY(50, 55);
		popup.showPopup();
	}

	@Override
	public void executeCardStreet() {
		popup.setText("Wir gehen mal noch ein paar weitere Karten" + System.lineSeparator() + 
				"durch, damit du das Spiel kennen lernst.");
		popup.setSize(400, 100);
		popup.setX(100, -210);
		popup.setY(100, -222.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeCardTownStreet() {
		popup.setText("Wir gehen mal noch ein paar weitere Karten" + System.lineSeparator() + 
				"durch, damit du das Spiel kennen lernst.");
		popup.setSize(400, 100);
		popup.setX(100, -210);
		popup.setY(100, -222.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeCardMonas() {
		popup.setText("Das Kloster verhält sich im Spielverlauf" + System.lineSeparator() + 
				"anders, da diese Struktur als vollständig" + System.lineSeparator() +
				"zählt, wenn 8 Karten um diese liegen.");
		popup.setSize(400, 120);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeCardMonasStreet() {
		popup.setText("Das Kloster verhält sich im Spielverlauf" + System.lineSeparator() + 
				"anders, da diese Struktur als vollständig" + System.lineSeparator() +
				"zählt, wenn 8 Karten um diese liegen.");
		popup.setSize(400, 120);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeCardTownShield() {
		popup.setText("Das Wappen bedeutet, dass dieser eine" + System.lineSeparator() + 
				"Stadtteil doppelt so viele Punkte wert ist.");
		popup.setSize(400, 100);
		popup.setX(100, -210);
		popup.setY(100, -222.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeReset() {
		popup.setText("Für die Punkteberechnungen passen" + System.lineSeparator() + 
				"wir einmal das Spielfeld an.");
		popup.setSize(400, 140);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeStructureFinishTown() {
		popup.hidePopup();
		
		new Thread(() -> {
			try {
				Thread.sleep(6000L);
				
				Platform.runLater(() -> {

					popup.setText("Strukturen zählen als abgeschlossen, wenn an" + System.lineSeparator() +
							"diese keine weitere Karte mehr anlegbar sind." + System.lineSeparator() +
							"Aufgabe: Schließe die Stadt ab.");
					popup.setSize(400, 130);
					popup.setX(100, -210);
					popup.setY(100, -269.4);
					popup.hideButton();
					popup.showPopup();
				});
				
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	public void executePointsTown() {

		popup.setText("Wenn du jetzt eine Figur auf die Stadt stellst," + System.lineSeparator() +
				"dann erhälst du für jeden Stadtteil 2 Punkte" + System.lineSeparator() + 
				"und für jedes Wappen weitere 2 Punkte");
		popup.setSize(400, 130);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executeStructureFinishStreet() {
		
		popup.setText("Nachdem platzieren eines Followers werden" + System.lineSeparator()+
				"die Punkte berechnet. Für jedes" + System.lineSeparator() +
				"Straßenteil gibt es einen Punkt.");
		popup.setSize(400, 130);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.hideButton();
		popup.showPopup();
	}

	@Override
	public void executePointsStreet() {

		popup.setText("Vervollständigen wir mal das Kloster. Dafür" + System.lineSeparator() +
				"müssen alle 8 Felder um das Kloster gelegt sein.");
		popup.setSize(400, 140);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeStructureFinishMonas() {

		popup.setText("Wenn die Struktur abgeschlossen ist," + System.lineSeparator() + 
				"dann gibt es 9 Punkte.");
		popup.setSize(400, 140);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.hideButton();
		popup.showPopup();
		
		new Thread(() -> {
			try {
				Thread.sleep(6000L);
				
				Platform.runLater(() -> {
					popup.showButton();
				});
				
			} catch (InterruptedException e) {
			}
		}).start();
	}

	@Override
	public void executePointsMonas() {

		popup.setText("Jetzt lassen wir das Spiel mal" + System.lineSeparator() + 
				"etwas automatisch laufen");
		popup.setSize(400, 140);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeBiggerField() {
		popup.hidePopup();
	}

	@Override
	public void executeOtherFollower() {

		popup.setText("Erklärung fürs Spielende folgt noch." + System.lineSeparator() + 
				"Du kannst jetzt das Tutorial beenden.");
		popup.setSize(400, 140);
		popup.setX(100, -210);
		popup.setY(100, -242.4);
		popup.showButton();
		popup.showPopup();
	}

	@Override
	public void executeGameEnd() {
		// TODO Auto-generated method stub
	}

}
