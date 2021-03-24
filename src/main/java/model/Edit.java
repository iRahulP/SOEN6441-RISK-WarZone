package model;

import java.util.ArrayList;
/**
 * Implementation of Edit phase of the game. 
 */
public abstract class Edit extends Phase{
	
	public void reinforce() {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

    public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
    	printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
    }

	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
		return false;
	}

    public void showMap(ArrayList<Player> p_players, GameMap p_map) {
	    	printInvalidCommandMessage();
     		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}
  }
