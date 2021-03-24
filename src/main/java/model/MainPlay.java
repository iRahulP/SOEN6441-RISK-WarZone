package model;

import java.util.ArrayList;
import java.util.Iterator;

import controller.GameEngine;

/**
 * Implementation of MainPlay phase of the game.
 */
public class MainPlay extends Play{
	/**
	 * it is constructor to initialize values
	 * @param p_ge is the reference of gameEngine class 
	 */
	public MainPlay(GameEngine p_ge) {
		d_Ge = p_ge;
		d_PhaseName = "MainPlay";
	}

	@Override
	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		d_Ge.d_LogEntry.setMessage("command given by user : showmap");
		if(p_map==null)
			return;
		if(p_players.size()==0 || p_players.get(0).getOwnedCountries().size()==0) {
			PostLoad l_rc = new PostLoad(d_Ge);
			l_rc.showMap(p_map);
			return;
		}
		System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
		System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
		boolean l_printPlayerName = true;
		boolean l_printContinentId = true;
		boolean l_printCountryId = true;
		boolean l_printNumberOfArmies = true;

		for(int i=0; i<p_players.size(); i++){
			Player l_p = p_players.get(i);
			for(CountryDetails l_country : l_p.getOwnedCountries().values()) {
				for(CountryDetails l_neighbor : l_country.getNeighbours().values()) {
					if(l_printPlayerName && l_printContinentId && l_printCountryId) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", l_p.getPlayerName(), l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getInContinent(), l_country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printContinentId = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else if(l_printContinentId && l_printCountryId && l_printNumberOfArmies) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", "", l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getInContinent(), l_country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else {
						System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", l_neighbor.getCountryId(), "", "");
					}
				}
				l_printContinentId = true;
				l_printCountryId = true;
				l_printNumberOfArmies = true;
			}
			l_printPlayerName = true;
			l_printContinentId = true;
			l_printCountryId = true;
			l_printNumberOfArmies = true;
		}
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
	}

	@Override
	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		printInvalidCommandMessage();
		return false;
	}
}


