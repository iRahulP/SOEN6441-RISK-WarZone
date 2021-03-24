package model;

import java.util.ArrayList;
/**
 * Implementation of Edit phase of the game. 
 */
public abstract class Edit extends Phase{
	
	public void reinforce() {
		printInvalidCommandMessage();
	}
    public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
    	printInvalidCommandMessage();

    }
	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		printInvalidCommandMessage();
		return false;
	}
    public void showMap(ArrayList<Player> p_players, GameMap p_map) {
	    	printInvalidCommandMessage();
	    }
  }
