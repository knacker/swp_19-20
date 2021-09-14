/*
 * holds the data for the timer
 */
package de.btu.swp.carcassonne.data;

import javafx.application.Platform;

public class CountDownTimer {

	private int count = 120;

	private boolean running;

	private Runnable runnable;

	public CountDownTimer(Runnable runnable) {
		this.runnable = runnable;
	}

	public void resetTimer() {
		count = 120;
	}

	public void stop() {
		running = false;
	}

	public void start() {

		if (running) {
			throw new IllegalStateException("Timer is already running");
		}
		running = true;

		Thread th = new Thread(() -> {

			while (running) {

				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					break;
				}

				if (count > 0) {
					count--;
				} else {
					Platform.runLater(runnable);
					count = 120;
				}
			}
		}, "CarcassonneCountdownTimer");
		th.setDaemon(true);
		th.start();
	}

}
