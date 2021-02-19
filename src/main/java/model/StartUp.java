package model;

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
}
