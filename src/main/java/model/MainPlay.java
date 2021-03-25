package model;

import java.util.ArrayList;
import java.util.Iterator;

import controller.GameEngine;
import view.MapView;

/**
 * Implementation of MainPlay phase of the game.
 */
public class MainPlay extends Play{

	/**
	 * mv Reference for MapView.
	 */
	MapView mv;
	/**
	 * it is constructor to initialize values
	 * @param p_ge is the reference of gameEngine class 
	 */
	public MainPlay(GameEngine p_ge) {
		d_Ge = p_ge;
		d_PhaseName = "MainPlay";
		mv=new MapView();
	}

	@Override
	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		mv.showMap(p_players, p_map);
	}

	@Override
	public void reinforce() {
		Iterator<Player> itr = d_Ge.d_Players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			AssignReinforcement.assignReinforcementArmies(p);
			d_Ge.d_LogEntry.setMessage("Assign reinforcement armies to player "+p.getPlayerName());
		}
	}

	@Override
	public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
	}

	@Override
	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		printInvalidCommandMessage();
		d_Ge.d_LogEntry.setMessage("Invalid command in phase "+d_Ge.d_Phase.getD_PhaseName());
		return false;
	}
}


