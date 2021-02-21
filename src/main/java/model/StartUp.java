package model;

import controller.GameEngine;

import java.util.*;

/**
 * Implementation of startup phase of the game.
 *
 */
public class StartUp {
	

	/**
	 * Addition of players to the game
	 * The maximum no. of players assigned is restricted to 6
	 * 
	 * @param p_players List of players in the game
	 * @param p_playerName Names of players in the game
	 * @return true if the players are added successfully, else false
	 */
	public boolean addPlayer(ArrayList<Player> p_players, String p_playerName){
		if(p_players.size()==6) {
			System.out.println("Can not add any more player. Maximum no. of players allowed is 6.");
			return false;
		}
		p_players.add(new Player(p_playerName));
		return true;
	}

	/**
	 * Removing players from the game
	 * 
	 * @param p_players List of players in the game
	 * @param p_playerName Names of players in the game
	 * @return true if the players are removed successfully, else false
	 */
	public boolean removePlayer(ArrayList<Player> p_players, String p_playerName){
		Iterator<Player> itr = p_players.listIterator();
		while(itr.hasNext()) {
			Player p = itr.next();
			if(p.getPlayerName().equals(p_playerName)) {
				p_players.remove(p);
				return true;
			}
		}
		return false;
	}

	/**
	 * Responsible for distributing countries amongst players.
	 * @param map Game map 
	 * @param players List of players in the game
	 * @return true if successful, else false
	 */
	public boolean assignCountries(GameMap map, ArrayList<Player> players) {
		int numberOfPlayers = players.size();
		if(players.size()<2) {
			System.out.println("Minimum two players are required to play the game.");
			return false;
		}
		int counter = 0;
		for(CountryDetails c : map.getCountries().values()) {
			Player p = players.get(counter);
			p.getOwnedCountries().put(c.getCountryId().toLowerCase(), c);
			if(counter>=numberOfPlayers-1)
				counter = 0;
			else
				counter++;
		}
		//assignInitialArmies(players);  //assigning initial armies
		return true;
	}


	/**
	 * Shows map with along with Owner and Army units.
	 * @param p_players List of players in the game
	 * @param p_map Game map
	 */
	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		if(p_map==null)
			return;
		if(p_players.size()==0 || p_players.get(0).getOwnedCountries().size()==0) {
			RunGameEngine rc = new RunGameEngine();
			rc.showMap(p_map);
			return;
		}
		System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
		System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
		boolean l_printPlayerName = true;
		boolean l_printContinentId = true;
		boolean l_printCountryId = true;
		boolean l_printNumberOfArmies = true;

		for(int i=0; i<p_players.size(); i++){
			Player p = p_players.get(i);
			for(CountryDetails country : p.getOwnedCountries().values()) {
				for(CountryDetails neighbor : country.getNeighbours().values()) {
					if(l_printPlayerName && l_printContinentId && l_printCountryId) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", p.getPlayerName(), country.getCountryId(), neighbor.getCountryId(), country.getInContinent(), country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printContinentId = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else if(l_printContinentId && l_printCountryId && l_printNumberOfArmies) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", "", country.getCountryId(), neighbor.getCountryId(), country.getInContinent(), country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else {
						System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", neighbor.getCountryId(), "", "");
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
}
