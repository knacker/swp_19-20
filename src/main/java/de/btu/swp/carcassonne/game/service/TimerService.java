package de.btu.swp.carcassonne.game.service;

import de.btu.swp.carcassonne.data.CountDownTimer;
import de.btu.swp.carcassonne.data.Timer;
import de.btu.swp.carcassonne.data.player.PlayerType;
import de.btu.swp.carcassonne.game.Service;

/**
 * Manager for all timer related things
 */
public class TimerService extends Service {

	private CountDownTimer countDownTimer = new CountDownTimer(() -> {
		if(gameService().getGame().getPlayerOnTurn() != lobbyService().getLocalPlayer() && gameService().getGame().getPlayerOnTurn().getType() != PlayerType.BOT) {
			//chatService().sendMessage("Spieler " + gameService().getGame().getPlayerOnTurn().getUsername() + " wurde gekickt, da er das Zeitlimit überschritten hat.");
			//lobbyService().kickPlayer(gameService().getGame().getPlayerOnTurn());
		}
	});;

	private final Timer timer = new Timer();
	
	/**
	 * start all timers (countdown Timer only if the player is the server)
	 */
	public void start() {

		if(connectionService().isServer()) {
			countDownTimer.start();
		}
		timer.start();
	}
	
	/**
	 * stop all timers
	 */
	public void stop() {
		timer.stop();
		countDownTimer.stop();
	}
	
	/**
	 * Get AFK Timer
	 * @return timer
	 */
	public CountDownTimer getAfkTimer() {
		return countDownTimer;
	}

	/**
	 * Get playtime timer
	 * @return timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Reset AFK Timer
	 */
	public void resetAfkTimer() {
		countDownTimer.resetTimer();
	}
}
