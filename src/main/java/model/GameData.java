package model;

import java.io.Serializable;
import java.util.ArrayList;

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
        
        private Phase d_Phase;

        /**
         *Stores the current phase name
         */
         private String d_PhaseName;

        /**
         * Get the current phase of the game.
         * @return returns the current phase of the game.
         */
        public Phase getD_Phase() {
    		return d_Phase;
    	}
    /**
     * sets current phase of the game
     * @param d_Phase phase of the game.
     */
    	public void setD_Phase(Phase d_Phase) {
    		this.d_Phase = d_Phase;
    	}

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
            

        }

        /**
         * Constructor to initialize the game data.
         * @param p_map Represents the state of the game.
         * @param p_mapType Domination/Conquest type map
         * @param p_gamePhase Phase the game is currently in
         * @param p_players List of players involved in the game
         * @param p_phase determine the phase of game
         * @param p_activePlayer Player who's turn is going on
         * @param p_card Card of cards.
         */
        public GameData(GameMap p_map, String p_mapType, InternalPhase p_gamePhase,Phase p_phase, ArrayList<Player> p_players, Player p_activePlayer, Card p_card,String p_phaseName){
            this.d_Map = p_map;
            this.d_MapType = p_mapType;
            this.d_GamePhase = p_gamePhase;
            this.d_Phase=p_phase;
            this.d_Players = p_players;
            this.d_ActivePlayer = p_activePlayer;
            this.d_Card = p_card;
            this.d_PhaseName = p_phaseName;
           // this.d_CardsDealt = p_cardsDealt;
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
                System.out.println(this.d_ActivePlayer.getPlayerName() + "'s reinforcement phase");
                notifyObservers(this);
            } else if (this.d_GamePhase == InternalPhase.ISSUE_ORDERS) {
            	System.out.println(this.d_ActivePlayer.getPlayerName() + "'s Issue order phase");
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
                notifyObservers(this);
            }
       }

        /**
        * This function is a setter function for phasename
        */
        public void setD_PhaseName(String p_phaseName)
       {
           this.d_PhaseName = p_phaseName;
       }

       /**
       * This function is a getter function for phaseName
       * @return d_PhaseName
       */
      public String getD_PhaseName() {
         return d_PhaseName;
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
         * @param p_card Card of card for the game.
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
    }
