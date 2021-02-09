package model;

import java.util.HashMap;

/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Aarthi
 */
public class Player {

    private String d_playerName;
    private HashMap<String, Continent> d_ownedContinents;
    private HashMap<String, CountryDetails> d_ownedCountries;

    /**
     * This constructor assigns name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        d_playerName = p_playerName;
        d_ownedContinents = new HashMap<String, Continent>();
        d_ownedCountries = new HashMap<String, CountryDetails>();
    }

    /**
     * This method sets the name of the player.
     *
     * @param p_playerName name of the player
     */
    public void setPlayerName(String p_playerName) {
        d_playerName = p_playerName;
    }

    /**
     * Getter method to return the player name.
     *
     * @return d_playerName name of the player
     */
    public String getPlayerName() {
        return d_playerName;
    }

    /**
     * This method assigns countries to the player as a HashMap.
     *
     * @param p_countries Countries owned by player
     */
    public void setOwnedCountries(HashMap<String, CountryDetails> p_countries) {
        d_ownedCountries = p_countries;
    }

    /**
     * Getter method to return to countries owned by a player.
     *
     * @return d_ownedCountries
     */
    public HashMap<String, CountryDetails> getOwnedCountries() {
        return d_ownedCountries;
    }

    /**
     * This method assigns continents to the player as a HashMap.
     *
     * @param p_continents Countries owned by player
     */
    public void setOwnedContinents(HashMap<String, Continent> p_continents) {
        d_ownedContinents = p_continents;
    }

    /**
     * Getter method to return to Continents owned by a player.
     *
     * @return d_ownedContinents
     */
    public HashMap<String, Continent> getOwnedContinents() {
        return d_ownedContinents;
    }

    //TO-DO exec_order and issue_order
}
