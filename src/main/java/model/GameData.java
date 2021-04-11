package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class holds the data required to maintain the state of the game.
 */
public class GameData extends Observable implements Serializable {

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
     * Logs the information related to the game.
     */
    Logger logger;

    /**
     * Constructor to initialize the game data.
     */
    public GameData(){
        d_Map = new GameMap();
        d_GamePhase = InternalPhase.NULL;
        d_Players = new ArrayList<Player>();
        d_ActivePlayer = null;
        d_Card = new Card();
        d_CardsDealt = 0;
        logger = Logger.getLogger("MyLog");

    }

    /**
     * Constructor to initialize the game data.
     * @param p_map Represents the state of the game.
     * @param p_mapType Domination/Conquest type map
     * @param p_gamePhase Phase the game is currently in
     * @param p_players List of players involved in the game
     * @param p_activePlayer Player who's turn is going on
     * @param p_card Card of cards.
     * @param p_cardsDealt Number of cards dealt till now
     */
    public GameData(GameMap p_map, String p_mapType, InternalPhase p_gamePhase, ArrayList<Player> p_players, Player p_activePlayer, Card p_card, int p_cardsDealt){
        this.d_Map = p_map;
        this.d_MapType = p_mapType;
        this.d_GamePhase = p_gamePhase;
        this.d_Players = p_players;
        this.d_ActivePlayer = p_activePlayer;
        this.d_Card = p_card;
        this.d_CardsDealt = p_cardsDealt;
        this.logger = Logger.getLogger("MyLog");
        try{
            FileHandler fh;
            fh = new FileHandler("src/main/resources/game/" + this.d_Map.getMapName() + ".log");
            this.logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        }catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the map being edited or played on.
     * @return returns the map being edited or played on.
     */
    public GameMap getMap() {
        return this.d_Map;
    }

    /**
     * Set the game map with the input argument.
     * @param p_map map to set as the game map.
     */
    public void setMap(GameMap p_map) {
        this.d_Map = p_map;
    }

    /**
     * Gets the type of the map
     * @return Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public String getMapType() {
        return d_MapType;
    }

    /**
     * Sets the type of the map
     * @param p_mapType Type of the map, i.e. 'Conquest' format map or 'Domination' format map
     */
    public void setMapType(String p_mapType) {
        this.d_MapType = p_mapType;
    }

    /**
     * Get the current phase of the game.
     * @return returns the current phase of the game.
     */
    public InternalPhase getGamePhase() {
        return this.d_GamePhase;
    }

    /**
     * Set the phase of the game with the input argument.
     * @param p_gamePhase new phase of the game.
     */
    public void setGamePhase(InternalPhase p_gamePhase) {

        this.d_GamePhase = p_gamePhase;
        if(this.d_GamePhase==InternalPhase.ASSIGN_REINFORCEMENTS){
            this.logger.info(this.d_ActivePlayer.getPlayerName() + "'s reinforcement phase");
            notifyObservers(this);
        } else if (this.d_GamePhase == InternalPhase.ISSUE_ORDERS) {
            this.logger.info(this.d_ActivePlayer.getPlayerName() + "'s Issue order phase");
            notifyObservers(this);
        }
    }

    /**
     * Get the list of players.
     * @return returns the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.d_Players;
    }

    /**
     * Set the list of players.
     * @param p_players list of players to play the game.
     */
    public void setPlayers(ArrayList<Player> p_players) {
        this.d_Players = p_players;
    }

    /**
     * Get currently active player.
     * @return returns currently active player.
     */
    public Player getActivePlayer(){
        return this.d_ActivePlayer;
    }

    /**
     * Set the currently active player.
     * @param p_player currently active player.
     */
   public void setActivePlayer(Player p_player){
        this.d_ActivePlayer = p_player;
        if(p_player!=null){
            this.logger.info("Active player: " + p_player.getPlayerName() + "\n");
            notifyObservers(this);
        }
   }

    /**
     * Gets the deck of card.
     * @return Returns the deck of card.
     */
    public Card getDeck() {
        return d_Card;
    }

    /**
     * Sets the deck of card.
     * @param deck Deck of card for the game.
     */
    public void setDeck(Card p_card) {
        this.d_Card = p_card;
    }

    /**
     * set the trade in phase of card exchange
     * @return trade in phase number
     */
    public int getCardsDealt() {
        return d_CardsDealt;
    }

    /**
     * set the phase for card trade in phase
     * @param p_cardsDealt trade in phase number
     */
    public void setCardsDealt(int  p_cardsDealt) {
        this.d_CardsDealt = p_cardsDealt;
    }

    /**
     * Gets the logger to log information.
     * @return Logger object to log information.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Sets the logger to a specific logger object.
     * @param p_logger Logger object to set to.
     */
    public void setLogger(Logger p_logger) {
        this.logger = p_logger;
    }

    /**
     * Remove player
     * @param p_p PLayer to be removed.
     */
    public void removePlayer(Player p_p){
        this.d_Players.remove(p_p);
    }

    /**
     * Resets the game data
     */
    public void resetGameData(){
        d_Map = new GameMap();
        d_GamePhase = InternalPhase.NULL;
        d_Players = new ArrayList<Player>();
        d_ActivePlayer = null;
        d_Card = new Card();
        d_CardsDealt = 0;
    }

    /**
     * Sets up the logger file.
     */
    public void setUpLogger(){
        try{
            FileHandler fh;
            fh = new FileHandler("src/main/resources/game/" + this.d_Map.getMapName() + ".log");
            this.logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        }catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}