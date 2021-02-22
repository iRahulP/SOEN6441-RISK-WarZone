package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Aarthi
 */
public class Player {

    private String d_playerName;
    private HashMap<String, Continent> d_ownedContinents;
    private HashMap<String, CountryDetails> d_ownedCountries;
    private int d_ownedArmies;
    private int d_reinforcementArmies;
    private String d_countryId;
    private Order d_Order;
    private PriorityQueue<Order> d_orderList;

    /**
     * This constructor assigns name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        d_playerName = p_playerName;
        d_ownedContinents = new HashMap<>();
        d_ownedCountries = new HashMap<>();
        this.d_ownedArmies = 0;
        d_orderList = new PriorityQueue<>();
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

    /**
     * Getter method to return the initial armies.
     *
     * @return d_ownedArmies
     */
    public int getOwnedArmies() {
        return d_ownedArmies;
    }

    /**
     * Setter method to set the initial armies
     *
     * @param d_ownedArmies number of armies owned by players
     */
    public void setOwnedArmies(int d_ownedArmies) {
        this.d_ownedArmies = d_ownedArmies;
    }

    /**
     * This function adds Order object to the list of Orders.
     * It has no parameters.
     */
    public void issue_order() {
        d_orderList.add(this.d_Order);

        System.out.println(d_orderList.peek().getD_numArmies());
    }

    /**
     * This function sets the created Object to Players Object
     *
     * @param p_order created Order
     */
    public void addOrder(Order p_order) {
        this.d_Order = p_order;
    }

    /**
     * This function takes the first Order in the List and calls execute() on it.
     *
     * @return first Order in the List.
     */
    public Order next_order() {
        //TODO need to be completed.
        Order l_firstOrder = d_orderList.peek();
        if( l_firstOrder != null) {
            l_firstOrder.execute(l_firstOrder);
        }
        return l_firstOrder;
    }

}
