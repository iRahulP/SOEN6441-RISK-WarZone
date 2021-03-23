package model;

import java.util.*;

/**
 * This class creates a Player and assigns attributes to the player.
 *
 * @author Aarthi
 */
public class Player {

    private String d_PlayerName;
    private HashMap<String, Continent> d_OwnedContinents;
    private HashMap<String, CountryDetails> d_OwnedCountries;
    private int d_OwnedArmies;
    private int d_ReinforcementArmies;
    private String d_CountryId;
    private Order d_Order;
    private Queue<Order> d_OrderList;

    /**
     * list of cards owned by player is stored in d_Deck
     */
    ArrayList<Card> d_Deck;


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
     *
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
     * If a player issues a card Order,he can use cards from his cards deck.
     * @param p_cardOrder card order command as input
     * @param p_card string representation of card to be used
     */
    public void use_card(Order p_cardOrder, String p_card) {
        addOrder(p_cardOrder);
        removeCard(p_card);
    }

    /**
     * If a player uses a card,it will be removed from deck of cards.
     * @param p_card String representation of card to be used
     */
    void removeCard(String p_card)
    {
        //remove card from deck
        Iterator iter = d_Deck.iterator();
        while (iter.hasNext()) {
            Card l_card = (Card) iter.next();
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
    boolean doesCardExists(String p_card) {

        int existsCount = 0;
        Iterator iter = d_Deck.iterator();
        while (iter.hasNext()) {
            Card l_card = (Card) iter.next();
            if (l_card.getCardType() == p_card)
                existsCount++;
        }
        if(existsCount>0)
            return true;
        else
            return false;
    }
}


