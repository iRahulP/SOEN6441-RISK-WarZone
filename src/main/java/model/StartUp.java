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

}
