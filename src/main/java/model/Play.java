package model;

import java.util.ArrayList;

/**
 * Implementation of Play phase of the game.
 */
public abstract class Play extends Phase{

	public void loadMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		
	}
	
	public void showMap(GameMap p_map) {
		printInvalidCommandMessage();	
	}

	public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
		printInvalidCommandMessage();
	}

	public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
		printInvalidCommandMessage();
	}

	public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
		printInvalidCommandMessage();
	}

	public void savemap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
	}

	public void editMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
	}

	public void validatemap(){
		printInvalidCommandMessage();
	}
	
}
