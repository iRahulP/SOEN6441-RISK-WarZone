package model;

import java.util.ArrayList;

/**
 * Implementation of Play phase of the game.
 */
public abstract class Play extends Phase{

	public void loadMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}
	
	public void showMap(GameMap p_map) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void editContinent(String[] p_data, String p_continentId, int p_controlValue) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void editCountry(String[] p_data, String p_continentId, String p_countryId) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void savemap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void editMap(String[] p_data, String p_mapName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	public void validatemap(){
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}
	
}
