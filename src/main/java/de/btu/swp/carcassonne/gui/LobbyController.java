package de.btu.swp.carcassonne.gui;

import java.net.UnknownHostException;

import de.btu.swp.carcassonne.data.chat.ChatMessage;
import de.btu.swp.carcassonne.data.player.Player;
import de.btu.swp.carcassonne.gui.data.ChatCell;
import de.btu.swp.carcassonne.gui.data.PlayerCell;
import de.btu.swp.carcassonne.util.GlyphUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class LobbyController extends Controller {

	@FXML
	ListView<Player> players;

	@FXML
	Label gameid;

	@FXML
	ListView<ChatMessage> chatarea;

	@FXML
	TextField chatinput;

	@FXML
	Button startGameButton;

	@FXML
	Button addBotButton;

	@FXML
	Button settingsBt;

	@FXML
	public void exitGame(ActionEvent e) {
		gameService().stopGame();
	}

	@FXML
	public void startGame(ActionEvent event) {
		gameService().startGame();
		app().openBoardScreen();
	}

	@FXML
	public void addBot(ActionEvent e) {
		lobbyService().addBot();
	}

	@FXML
	public void initialize() {

		settingsBt.setGraphic(GlyphUtil.settings());

		players.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
			@Override
			public ListCell<Player> call(ListView<Player> listview) {
				return new PlayerCell(app());
			}
		});
		chatinput.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ENTER) {
					chatService().sendMessage(chatinput.getText());
					chatinput.clear();
				}
			}
		});

		ListProperty<Player> playerList = lobbyService().getLobby().getPlayers();

		players.setItems(playerList);

		try {
			String id = connectionService().getGameid();
			gameid.setText(id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		chatarea.setCellFactory((listview) -> {
			return new ChatCell(listview);
		});
		chatarea.setItems(chatService().getChat().getMessages());

		BooleanProperty isServer = new SimpleBooleanProperty(connectionService().isServer());

		startGameButton.visibleProperty().bind(isServer.and(playerList.sizeProperty().greaterThan(1)));
		addBotButton.visibleProperty().bind(isServer.and(playerList.sizeProperty().lessThan(5)));
	}

	@FXML
	public void copyGameId() throws UnknownHostException {

		String gameID = connectionService().getGameid();

		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();

		content.putString(gameID);
		clipboard.setContent(content);
	}

	@FXML
	public void openSettings() {
		app().openSettingsScreen();
	}
}
