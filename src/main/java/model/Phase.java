package model;

import java.util.ArrayList;

import controller.GameEngine;

/**
 * It maintains the current phase of the game.
 * @author rahul
 *
 */
public abstract class Phase {
	GameEngine d_Ge;
	String d_PhaseName;
	public String getD_PhaseName() {
		return d_PhaseName;
	}
	abstract public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players);
	abstract public void loadMap(String[] p_data, String p_mapName) ;
	abstract public void showMap(GameMap p_map) ;
	abstract public void editMap(String[] p_data, String p_mapName) ;
	abstract public void savemap(String[] p_data, String p_mapName) ;
	abstract public void editNeighbour(String[] p_data, String p_countryId, String p_neighborCountryId) ;
	abstract public void editCountry(String[] p_data, String p_continentId, String p_countryId);
	abstract public void editContinent(String[] p_data, String p_continentId, int p_controlValue);
	abstract public void reinforce() ;
	abstract public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) ;
	abstract public void showMap(ArrayList<Player> p_players, GameMap p_map);
	public void printInvalidCommandMessage(){
		System.out.println("Invalid command in state "
				+ this.getClass().getSimpleName() );

	}
}

