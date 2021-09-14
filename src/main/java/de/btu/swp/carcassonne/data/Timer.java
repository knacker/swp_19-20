/*
 * holds the time in seconds, how long a game is already running
 */
package de.btu.swp.carcassonne.data;

import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer {

	private final IntegerProperty minutes = new SimpleIntegerProperty();
	private final IntegerProperty seconds = new SimpleIntegerProperty();

	private boolean running;

	public void start() {
		if (running) {
			throw new IllegalStateException("Timer is already running");
		}

		minutes.set(0);
		seconds.set(0);
		running = true;

		Thread th = new Thread(() -> {
			
			while (running) {

				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					stop();
					break;
				}
				
				int s = seconds.get() + 1;
				int m = minutes.get();
				
				Platform.runLater(() -> {
					seconds.set(s % 60);
					minutes.set(m + (s / 60));
				});
			}
		}, "CarcassonneTimer");
		th.setDaemon(true);
		th.start();
	}

	public void stop() {
		running = false;
	}

	public StringExpression asString() {
		return minutes.asString("%02d").concat(":").concat(seconds.asString("%02d"));
	}

}
