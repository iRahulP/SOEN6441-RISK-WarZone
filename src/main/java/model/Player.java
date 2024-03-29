package model;

import java.io.Serializable;
import java.util.*;

/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Aarthi
 */
public class Player  implements Serializable{

	/**
	 * d_PlayerName name of player
	 */
    private String d_PlayerName;
    /**
     * d_OwnedContinents hold the continent owned by player
     */
    private HashMap<String, Continent> d_OwnedContinents;
    /**
     * d_OwnedCountries hold the countries owned by player
     */
    private HashMap<String, CountryDetails> d_OwnedCountries;
    /**
     * d_OwnedArmies represent the armied owned by player
     */
    private int d_OwnedArmies;
    /**
     * d_ReinforcementArmies determine reinforcement armies
     */
    private int d_ReinforcementArmies;
    /**
     * d_CountryId ID of the country
     */
    private String d_CountryId;
    /**
     * d_Order reference for order
     */
    private Order d_Order;
    /**
     * d_OrderList maintain the lis of order
     */
    private Queue<Order> d_OrderList;
    /**
     * d_isHuman check whether the player type is human or not
     */
    private boolean d_isHuman;

    /**
     * Negotiators list
     */
    ArrayList<Player> d_NegotiateList;

    /**
     * list of cards owned by player is stored in d_Deck
     */
    ArrayList<Card> d_Deck;

    /**
     * Strategy object for PlayerStrategy
     */
    PlayerStrategy d_Strategy;


    /**
     * This constructor assigns name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        d_PlayerName = p_playerName;
        d_OwnedContinents = new HashMap<>();
        d_OwnedCountries = new HashMap<>();
        this.d_OwnedArmies = 0;
        d_OrderList = new ArrayDeque<>();
        d_Deck = new ArrayList<>();
        d_NegotiateList= new ArrayList<Player>();
        this.d_isHuman = false;
    }

    /**
     * This function adds Order object to the list of Orders for a non-human Player
     * @return true if Order added else false
     */
    public boolean issueOrder() {
        Order order;
        order = d_Strategy.createOrder();
        if (order != null) {
            this.d_OrderList.add(order);
            return true;
        }
        return false;
    }

    /**
     * Sets Player as human
     * @param d_isHuman true if human else false
     */
    public void setD_isHuman(boolean d_isHuman) {
        this.d_isHuman = d_isHuman;
    }

    /**
     * Getter for User Player
     * @return true if human else false
     */
    public boolean getD_isHuman(){
        return this.d_isHuman;
    }

    /**
     * Sets strategy for a specific player
     * @param p_strategy strategy
     */
    public void setStrategy(PlayerStrategy p_strategy) {
        d_Strategy = p_strategy;
    };

    /**
     * Getter for Negotiators
     * @return d_NegotiateList
     */
    public ArrayList<Player> getD_NegotiateList() {
        return d_NegotiateList;
    }

    /**
     * Adds Player to Negotiator list
     * @param p_player target player to be added
     */
    void addPlayerNegList(Player p_player) {
        d_NegotiateList.add(p_player);
    }

    /**
     * flush lists after Turn
     */
    public void flushNegotiators() {
        d_NegotiateList.clear();
    }
    /**
     * This method sets the name of the player.
     *
     * @param p_playerName name of the player
     */
    public void setPlayerName(String p_playerName) {
        d_PlayerName = p_playerName;
    }

    /**
     * Getter method to return the player name.
     *
     * @return d_playerName name of the player
     */
    public String getPlayerName() {
        return d_PlayerName;
    }

    /**
     * This method assigns countries to the player as a HashMap.
     *
     * @param p_countries Countries owned by player
     */
    public void setOwnedCountries(HashMap<String, CountryDetails> p_countries) {
        d_OwnedCountries = p_countries;
    }

    /**
     * Getter method to return to countries owned by a player.
     *
     * @return d_ownedCountries
     */
    public HashMap<String, CountryDetails> getOwnedCountries() {
        return d_OwnedCountries;
    }

    /**
     * This method assigns continents to the player as a HashMap.
     *
     * @param p_continents Countries owned by player
     */
    public void setOwnedContinents(HashMap<String, Continent> p_continents) {
        d_OwnedContinents = p_continents;
    }

    /**
     * Getter method to return to Continents owned by a player.
     *
     * @return d_ownedContinents
     */
    public HashMap<String, Continent> getOwnedContinents() {
        return d_OwnedContinents;
    }

    /**
     * Getter method to return the initial armies.
     *
     * @return d_ownedArmies
     */
    public int getOwnedArmies() {
        return d_OwnedArmies;
    }

    /**
     * Setter method to set the initial armies
     *
     * @param p_ownedArmies number of armies owned by players
     */
    public void setOwnedArmies(int p_ownedArmies) {
        this.d_OwnedArmies = p_ownedArmies;
    }

    /**
     * This function adds Order object to the list of Orders.
     * It has no parameters.
     */
    public void issue_order() {
        this.d_OrderList.add(this.d_Order);
    }

    /**
     * getter for order queue
     * @return d_OrderList
     */
    public Queue<Order> getD_orderList() {
        return d_OrderList;
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
        return d_OrderList.poll();
    }

    /**
     * If a player conquers a territory,a card will be added to that player
     */
    public void addCard() {
        Card l_card = new Card();
        l_card.createCard();
        d_Deck.add(l_card);
    }

    /**
     * Added a clone of addCard inorder to test custom cards
     * @param test custom card
     */
    public void addCard(String test){
        Card l_card = new Card();
        l_card.createCard(test);
        d_Deck.add(l_card);
    }

    /**
     * If a player uses a card,it will be removed from deck of cards.
     * @param p_card String representation of card to be used
     */
    public void removeCard(String p_card)
    {
        //remove card from deck
        Iterator l_iter = d_Deck.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card) {
                d_Deck.remove(l_card);
                break;
            }
        }
    }

    /**
     * Before using a card, we can check if player has that card
     * @param p_card String representation of card to be used
     * @return true if card exists else false
     */
    public boolean doesCardExists(String p_card) {

        int l_existsCount = 0;
        Iterator l_iter = d_Deck.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card)
                l_existsCount++;
        }
        if(l_existsCount>0)
            return true;
        else
            return false;
    }

    /**
     * show the particular card owned by player
     */
    public void showCards()
    {
        Iterator l_iter = d_Deck.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            System.out.println("Player now owns card:"+l_card.getCardType());
        }

    }

    /**
     * Getter for Player's Cards Deck
     * @return d_Deck
     */
    public ArrayList<Card> getD_Deck() {
        return d_Deck;
    }
}


