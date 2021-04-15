package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Responsible for building GameData object
 */
public class GameDataBuilder implements Serializable {

    /**
     * Stores the map being edited or the map being played on depending on the user's choice.
     */
    private GameMap d_Map;

    /**
     * Represents the type of the map.
     */
    private String d_MapType;

    /**
     * Stores the current phase of the game.
     */
    private InternalPhase d_GamePhase;
    
    /**
     * Stores the current phase of the game.
     */
    private Phase d_Phase;

    /**
     * Stores the list of players playing the game.
     */
    private ArrayList<Player> d_Players;

    /**
     * Stores currently active player.
     */
    private Player d_ActivePlayer;

    /**
     * Stores the deck of cards.
     */
    private Card d_Card;

    /**
     * Represents number of cards dealt.
     */
    private int d_CardsDealt;

    /**
     * Set the game map with the input argument.
     * @param p_map map to set as the game map.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setMap(GameMap p_map) {
        this.d_Map = p_map;
        return this;
    }

    /**
     * Sets the type of the map
     * @param p_mapType Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     * @return GameDataBuilder object
     */
    public GameDataBuilder setMapType(String p_mapType) {
        this.d_MapType = p_mapType;
        return this;
    }

    /**
     * Set the phase of the game with the input argument.
     * @param p_gamePhase new phase of the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setGamePhase(InternalPhase p_gamePhase) {
        this.d_GamePhase = p_gamePhase;
        return this;
    }
    /**
     * Set the phase of the game with the input argument.
     * @param p_Phase new phase of the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setPhase(Phase p_Phase) {
        this.d_Phase = p_Phase;
        return this;
    }

    /**
     * Set the list of players.
     * @param p_players list of players to play the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setPlayers(ArrayList<Player> p_players) {
        this.d_Players = p_players;
        return this;
    }

    /**
     * Set the currently active player.
     * @param p_activePlayer currently active player.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setActivePlayer(Player p_activePlayer) {
        this.d_ActivePlayer = p_activePlayer;
        return this;
    }

    /**
     * Sets the deck of card.
     * @param p_card Card of card for the game.
     * @return GameDataBuilder object
     */
    public GameDataBuilder setDeck(Card p_card) {
        this.d_Card = p_card;
        return this;
    }

    /**
     * set the phase for card trade in phase
     * @param p_cardsDealt trade in phase number
     * @return GameDataBuilder object
     */
    public GameDataBuilder setCardsDealt(int p_cardsDealt) {
        this.d_CardsDealt = p_cardsDealt;
        return this;
    }

    /**
     * Creates the required GameData object.
     * @return returns the newly created GameData object.
     */
    public GameData buildGameData(){
    	//System.out.println(d_Map+" "+ d_MapType+" "+ d_GamePhase+" "+ d_Players+" "+ d_ActivePlayer+" "+ d_Card+" "+d_CardsDealt);
        return new GameData(d_Map, d_MapType, d_GamePhase,d_Phase, d_Players, d_ActivePlayer, d_Card);
    }
}