package model;

import java.util.*;

import controller.GameEngine;
import view.MapView;

/**
 * Implementation of startup phase of the game.
 *
 */
public class StartUp extends Play {

	/**
	 * mv Reference for MapView.
	 */
	MapView mv;
	String playerStrategy;

	/**
	 * it is constructor to initialize values
	 *
	 * @param p_ge is the reference of gameEngine class
	 */
	public StartUp(GameEngine p_ge) {
		d_Ge = p_ge;
		d_PhaseName = "StartUp";
		mv = new MapView();
		playerStrategy = null;
	}

	/**
	 * Addition of players to the game
	 * The maximum no. of players assigned is restricted to 6
	 *
	 * @param p_players    List of players in the game
	 * @param p_playerName Names of players in the game
	 * @return true if the players are added successfully, else false
	 */
	public boolean addPlayer(ArrayList<Player> p_players, String p_playerName) {
		if (p_players.size() == 6) {
			System.out.println("Can not add any more player. Maximum no. of players allowed is 6.");
			d_Ge.d_LogEntry.setMessage("Can not add any more player. Maximum no. of players allowed is 6.");
			return false;
		}
		p_players.add(new Player(p_playerName));
		return true;
	}

	/**
	 * Removing players from the game
	 *
	 * @param p_players    List of players in the game
	 * @param p_playerName Names of players in the game
	 * @return true if the players are removed successfully, else false
	 */
	public boolean removePlayer(ArrayList<Player> p_players, String p_playerName) {
		Iterator<Player> itr = p_players.listIterator();
		while (itr.hasNext()) {
			Player l_p = itr.next();
			if (l_p.getPlayerName().equals(p_playerName)) {
				d_Ge.d_LogEntry.setMessage("Player removed " + l_p.getPlayerName());
				p_players.remove(l_p);
				return true;
			}
		}
		return false;
	}

	/**
	 * Responsible for distributing countries amongst players.
	 *
	 * @param p_map     Game map
	 * @param p_players List of players in the game
	 * @return true if successful, else false
	 */
	public boolean assignCountries(GameMap p_map, ArrayList<Player> p_players) {
		d_Ge.d_LogEntry.setMessage("Assigning Countries to players");
		int l_numberOfPlayers = p_players.size();
		if (p_players.size() < 2) {
			System.out.println("Minimum two players are required to play the game.");
			return false;
		}
		int l_counter = 0;
		for (CountryDetails l_c : p_map.getCountries().values()) {
			Player l_p = p_players.get(l_counter);
			l_p.getOwnedCountries().put(l_c.getCountryId().toLowerCase(), l_c);
			l_c.setOwnerPlayer(l_p);
			d_Ge.d_LogEntry.setMessage("Country: " + l_c.getCountryId().toLowerCase() + " assigned to player: " + l_p.getPlayerName());
			if (l_counter >= l_numberOfPlayers - 1)
				l_counter = 0;
			else
				l_counter++;
		}
		return true;
	}

	/**
	 * Shows map with along with Owner and Army units.
	 *
	 * @param p_players List of players in the game
	 * @param p_map     Game map
	 */


	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		mv.showMap(p_players, p_map);
	}

	@Override
	public void reinforce() {
		printInvalidCommandMessage();
	}

	@Override
	public void gamePlayer(String[] p_data, ArrayList<Player> p_players, String p_playerName) {
		try {
			for (int i = 1; i < p_data.length; i++) {
				if (p_data[i].equals("-add")) {
					if (d_Ge.isPlayerNameValid(p_data[i + 1])) {
						p_playerName = p_data[i + 1];
						playerStrategy = p_data[i + 2];
						boolean l_check = addPlayer(p_players, p_playerName);
						if (l_check && d_Ge.isAlphabetic(p_data[i + 2])) {
							for (Player l_p : d_Ge.d_Players) {
								if (l_p.getPlayerName().equals(p_playerName)) {
									switch (playerStrategy) {
										case "human":
											l_p.setD_isHuman(true);
											break;
										case "aggressive":
											l_p.setStrategy(new AggresivePlayer(l_p, d_Ge.d_Map));
											l_p.setD_isHuman(false);
											break;
										case "benevolent":
											l_p.setStrategy(new BenevolentPlayer(l_p, d_Ge.d_Map));
											l_p.setD_isHuman(false);
											break;
										case "random":
											l_p.setStrategy(new RandomPlayer(l_p, d_Ge.d_Map));
											l_p.setD_isHuman(false);
											break;
										case "cheater":
											l_p.setStrategy(new CheaterPlayer(l_p, d_Ge.d_Map));
											l_p.setD_isHuman(false);
											break;
										default:
											l_p.setD_isHuman(true);
											break;
									}
								}
							}
							if (l_check) {
								System.out.println(p_playerName+" added! with strategy : "+playerStrategy);
								d_Ge.d_LogEntry.setMessage(p_playerName+" added! with strategy : "+playerStrategy);
							} else {
								System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
								d_Ge.d_LogEntry.setMessage("Can not add any more player. Max pool of 6 Satisfied!");
							}
							d_Ge.d_GamePhase = InternalPhase.STARTUP;
						} else {
							System.out.println("Invalid Player Name or Strategy");
							d_Ge.d_LogEntry.setMessage("Invalid Player Name or Strategy");
						}
					} else if (p_data[i].equals("-remove")) {
						if (d_Ge.isPlayerNameValid(p_data[i + 1])) {
							p_playerName = p_data[i + 1];
							boolean l_check = removePlayer(p_players, p_playerName);
							if (l_check) {
								System.out.println("Player removed!");
								d_Ge.d_LogEntry.setMessage("Player removed! " + p_playerName);
							} else {
								System.out.println("Player doesn't exist");
								d_Ge.d_LogEntry.setMessage("Player doesn't exist");
							}
							d_Ge.d_GamePhase = InternalPhase.STARTUP;
						} else
							System.out.println("Invalid Player Name");
						d_Ge.d_LogEntry.setMessage("Invalid Player Name");
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
			d_Ge.d_LogEntry.setMessage("Invalid command - it should be of the form gameplayer -add playername -remove playername");
		} catch (Exception e) {
			System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
			d_Ge.d_LogEntry.setMessage("Invalid command - it should be of the form gameplayer -add playername -remove playername");
		}
	}
}