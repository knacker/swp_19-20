package de.btu.swp.carcassonne.game.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;

import de.btu.swp.carcassonne.data.settings.Settings;
import de.btu.swp.carcassonne.data.settings.SettingsData;
import de.btu.swp.carcassonne.game.Service;

/**
 * Manager for all setting related things
 */
public class SettingsService extends Service {

	private final Settings settings = new Settings();
	
	private final Gson gson = new Gson();
	
	private final File file = new File("carcassonne-settings.json");
	
	public SettingsService() {
		
		loadSettings();
	}
	
	/**
	 * Get Settings Data class
	 * @return data
	 */
	public Settings getSettings() {
		return settings;
	}
	
	/**
	 * Load Settings from json file if it exists otherwise load default values.
	 */
	public void loadSettings() {
		
		if(file.exists()) {
			try(FileInputStream in = new FileInputStream(file); Reader reader = new InputStreamReader(in)) {

				SettingsData data = gson.fromJson(reader, SettingsData.class);
				
				data.loadSettings(settings);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Save settings to the json file
	 */
	public void saveSettings() {
		
		try(FileOutputStream out = new FileOutputStream(file); Writer writer = new OutputStreamWriter(out)) {

			gson.toJson(settings.toData(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Looad DefaultSettings into the settings data object
	 */
	public void loadDefaultSettings() {
		new SettingsData().loadSettings(settings);
	}
	
}
