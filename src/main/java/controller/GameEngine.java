package controller;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;


/**
 * Class responsible to interpret different commands as provided by User over Command Line Interface
 * Call required methods as appropriate, also update game phases based on method responses.
 *
 */
public class GameEngine {
	
	/**
	 * d_Map It is reference for GameMap
	 */
    public GameMap d_Map;
    
    /**
	 * d_RunG It is reference for RunGameEngine
	 */
    public RunGameEngine d_RunG;
    /**
	 * d_Arfc It is reference for AssignReinforcement 
	 */
    public AssignReinforcement d_Arfc;
    
    /**
	 * d_GamePhase It is reference for InternalPhase
	 */
    public InternalPhase d_GamePhase;
    
    /**
	 * d_Players It is reference for players list
	 */
    public ArrayList<Player> d_Players;
    
    /**
	 * d_Play It is reference for PlayRisk
	 */
    public PlayRisk d_Play;
    
    /**
	 * d_Phase It is reference for Phase
	 */
    public Phase d_Phase;
    
    /**
	 * d_Card It is reference for Card
	 */
    public Card d_Card;
    
    /**
	 * d_LogEntry It is reference for LogEntryBuffer
	 */
    public LogEntryBuffer d_LogEntry;
    
    /**
	 * d_WriteLog It is reference for WriteLogEntry
	 */
    public WriteLogEntry d_WriteLog;
    
    /**
     * Holds the data related to the game.
     */
    public GameData d_Game;
    /**
     * Reference for GameEngine
     */
    public GameEngine d_Ge; 
    /**
     * Reference for StartUp
     */
    public StartUp d_StartUp;
    /**
     * initializing load variable
     */
    public static int load=0;
    /**
     * Store the active player
     */
    public Player d_ActivePlayer;
    /**
     * Initializes the variables and objects required to play the game and act on user commands
     */
    public GameEngine()  {
        d_Map = new GameMap();
        d_RunG = new RunGameEngine();
        d_StartUp = new StartUp(d_Ge);
        d_Arfc = new AssignReinforcement();
        d_Players = new ArrayList<Player>();
        d_GamePhase = InternalPhase.NULL;
        d_Play = new PlayRisk();
        d_LogEntry = new LogEntryBuffer();
        d_WriteLog = new WriteLogEntry();
        d_Game= new GameData();
      
        d_LogEntry.attach(d_WriteLog);
    }
    
    /**
     * Constructor of GameEngine to initialize with given parameter
     * @param p_gameData Reference of gamedata class
     */
  public GameEngine(GameData p_gameData) {
	  d_Map = p_gameData.getMap();
      d_RunG = new RunGameEngine();
      //d_StartUp = new StartUp(d_Ge);
      d_Arfc = new AssignReinforcement();
      d_Players = p_gameData.getPlayers();
      d_GamePhase = p_gameData.getGamePhase();
      d_Play = new PlayRisk();
      d_LogEntry = new LogEntryBuffer();
      d_WriteLog = new WriteLogEntry();
      d_Phase=p_gameData.getD_Phase();
    this.d_Game= new GameData(d_Map,"domination", d_GamePhase,d_Phase, d_Players, d_ActivePlayer, d_Card);
    
      System.out.println(d_Game);
      d_LogEntry.attach(d_WriteLog);
      System.out.println("gamengine hellos");
  }
   
	/**
     * Ensures map name is valid.
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String p_sample) {
        return p_sample != null && p_sample.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures string matches the defined criteria of being an Alpha for Names.
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isPlayerNameValid(String p_sample) {
        return p_sample != null && p_sample.matches("[a-zA-Z0-9]+");
    }

    /**
     * Ensures string matches the defined criteria of being an Alpha for Names.
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isAlphabetic(String p_sample) {
        return p_sample != null && p_sample.matches("^[a-zA-Z-_]*$");
    }

    /**
     * Ensures string matches the defined criteria of being a Numeric for ID.
     * @param p_sample input string
     * @return true if valid match, else false
     */
    public boolean isNumeric(String p_sample) {
        return p_sample != null && p_sample.matches("[0-9]+");
    }


    /**
     * Setter method to set Game InternalPhase.
     * @param p_gamePhase InternalPhase value to be set.
     */
    public void setGamePhase(InternalPhase p_gamePhase) {
        this.d_GamePhase = p_gamePhase;
    }

    /**
     * Sets the current phase
     * @param p_Phase is the phase that is to be set
     */
    public void setPhase(Phase p_Phase)
    {
        d_Phase = p_Phase;
    }
    /**
     * gets the current phase
     * @return d_Phase is the phase that is to be set
     */    
    public Phase getPhase() {
		return d_Phase;
    	
    }
    /**
     * gets the player that is active.
     * @return d_ActivePlayer
     */
    public Player getD_ActivePlayer() {
    	return d_ActivePlayer;
    }
    /**
     * sets the active player during game.
     * @param d_ActivePlayer its the object of active player.
     */
    public void setD_ActivePlayer(Player d_ActivePlayer) {
    	this.d_ActivePlayer = d_ActivePlayer;
    }
    /**
     * Creates a game data object.
     * @param p_gameDataBuilder Helps build GameData object.
     */
    public void createGameData(GameDataBuilder p_gameDataBuilder){      	
        this.d_Game = p_gameDataBuilder.buildGameData();
    }
    
    /**
     * Set the game state
     */
    public void setGame() {
    	System.out.println(d_ActivePlayer.getPlayerName());
    	System.out.println(this.d_Game.getDeck());
    	System.out.println(this.d_Game.getMap());
    	this.d_Game.setActivePlayer(d_ActivePlayer);
    	this.d_Game.setGamePhase(d_GamePhase);
    	this.d_Game.setD_Phase(d_Phase);
    	this.d_Game.setPlayers(d_Players);
    	this.d_Game.setMapType("domination");
    	this.d_Game.setCardsDealt(load);	
    	d_Game = new GameData(d_Map,"domination", d_GamePhase,d_Phase, d_Players, d_ActivePlayer, d_Card);
    }
    /**
     * Returns the game state.
     * @return returns the game state as GameData object.
     */
    public GameData getGame() {
        return this.d_Game;
    }

    /**
     * Function responsible for parsing user commands passed on Command line interface.
     * Calls appropriate methods, and Updates game Phases.
     * @param p_player Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return next game InternalPhase
     */
   
    public InternalPhase parseCommand(Player p_player, String p_newCommand) {
    	
        int l_controlValue = 0;
        int l_numberOfArmies = 0;
        String l_mapName = null;
        String l_continentId = null;
        String l_countryNameFrom = null;
        String l_countryNameTo = null;
        String l_sourceCountryId = null;
        String l_targetCountryId = null;
        String l_countryId = null;
        String l_neighborCountryId = null;
        String l_playerName = null;
        String l_playerStrategy = null;
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];
        String l_fileName;
    	
    	
    	
        //initial command line commands
        //NULL : editmap / loadmap
        if (d_GamePhase.equals(InternalPhase.NULL)) {
        	setPhase(new Load(this));
            switch (l_commandName) {
            
            case "loadgame":
                try{
                    if(l_data.length == 2){
                        if(isAlphabetic(l_data[1])) {
                            l_fileName = l_data[1];
                            GameDataBuilder l_gameDataBuilder = d_RunG.loadGame(l_fileName);
                            createGameData(l_gameDataBuilder);
                            System.out.println("Loaded successfully");
                            d_LogEntry.setMessage("Game loaded successfully.");
                          
                            
                        }
                        //method call for load game and parse this filename as argument
                    }else{
                         System.out.println("Invalid command. Enter file name to load a game.");
                        d_LogEntry.setMessage(" - Invalid command. Enter file name to load a game.");
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                	System.out.println("Invalid Command. It should be 'loadgame filename'");
                     
                    d_LogEntry.setMessage(" - Invalid command. It should be 'loadgame filename'");
                }
                break;
                case "editmap":
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.editMap(l_data, l_mapName);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "loadmap":
                    
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.loadMap(l_data,l_mapName);
                    String str1=d_Phase.getD_PhaseName();
                    System.out.println(str1);
                    break;
                default:
                    System.out.println("try LoadMap or EditMap first using commands: loadmap sample.map or editmap sample.map");
                    d_LogEntry.setMessage("try LoadMap or EditMap first using commands: loadmap sample.map or editmap sample.map");
                    break;
            }
        }
        //EDITMAP InternalPhase
        //EDIT MAP : editcontinent, editcountry, editneighbour, savemap, showmap, editmap, loadmap, validatemap
        else if (d_GamePhase.equals(InternalPhase.EDITMAP)) {
            switch (l_commandName) {
                case "editcontinent":
                    setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.editContinent(l_data, l_continentId, l_controlValue);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "editcountry":
                    setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.editCountry(l_data, l_continentId, l_countryId) ;
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    String str1=d_Phase.getD_PhaseName();
                    System.out.println(str1);
//
                    break;

                case "editneighbor":
                    setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.editNeighbour(l_data, l_countryId, l_neighborCountryId);
                    String str2=d_Phase.getD_PhaseName();
                    System.out.println(str2);
//
                    break;

                case "savemap":
                    setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.savemap(l_data, l_mapName);
                    String str3=d_Phase.getD_PhaseName();
                    System.out.println(str3);
//
                    break;

                case "showmap":
                   setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.showMap(d_Map);
                    String str4=d_Phase.getD_PhaseName();
                    System.out.println(str4);
                    // d_RunG.showMap(d_Map);
                    d_GamePhase = InternalPhase.EDITMAP;
                    break;

                case "validatemap":
                    setPhase(new Load(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.validatemap();
                    String strValidate=d_Phase.getD_PhaseName();
                    System.out.println(strValidate);

                    break;

                default:
                    System.out.println("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
                    d_LogEntry.setMessage("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
                    break;
            }
        }
        //STARTUP InternalPhase
        //STARTUP : gameplayer, assigncountries, showmap
        else if (d_GamePhase.equals(InternalPhase.STARTUP)) {
            switch (l_commandName) {
                case "gameplayer":
                    setPhase(new StartUp(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.gamePlayer(l_data,d_Players, l_playerName);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "assigncountries":
                    boolean l_check = d_Phase.assignCountries(d_Map, d_Players);
                    if (l_check) {
                        System.out.println("Countries allocated randomly amongst Players");
                        d_LogEntry.setMessage("Countries allocated randomly amongst Players");
                        setPhase(new MainPlay(this));
                        d_LogEntry.setGamePhase(d_Phase);
                        d_LogEntry.setCommand(l_commandName+" Command is being executed");
                        d_Phase.reinforce();
                        String str1=d_Phase.getD_PhaseName();
                        System.out.println(str1);
                        d_GamePhase = InternalPhase.ISSUE_ORDERS;
                    }
                    d_GamePhase = InternalPhase.ISSUE_ORDERS;
                    break;

                case "showmap":
                    //(new StartUp(this));
                    d_Phase.showMap(d_Players,d_Map);
                    String str2=d_Phase.getD_PhaseName();
                    System.out.println(str2);
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    break;
                    
                case "savegame":
                    try{
                        if(l_data.length == 2){
                            if(isAlphabetic(l_data[1])) {
                                String fileName = l_data[1];
                                d_RunG.saveGame(this.d_Game, fileName);
                                System.out.println("current Game saved is saved ");
                                d_LogEntry.setMessage("current Game saved is saved");
                            }
                        }else{
                            String message = "Invalid command. enter file name to save a game.";
                            d_LogEntry.setMessage("Invalid command. enter file name to save a game.");
                        }

                    }catch (ArrayIndexOutOfBoundsException e){
                    	String message = "Invalid Command, It should be 'savegame filename'";
                    	d_LogEntry.setMessage("Invalid Command, It should be 'savegame filename'");
                    }
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer command/assigncountries command/showmap command in start up InternalPhase!");
                    d_LogEntry.setMessage("Invalid command - use gameplayer command/assigncountries command/showmap command in start up InternalPhase!");
                    break;
            }
        }

        //ISSUE_ORDERS InternalPhase
        //ISSUE_ORDERS : deploy - orders, showmap
        else if (d_GamePhase.equals(InternalPhase.ISSUE_ORDERS)) {
            d_LogEntry.setMessage("In Issue Orders Phase:");
            int l_counter = 0;
            //traverses through all players to check if armies left in pool
            Iterator<Player> l_itr = d_Players.listIterator();
            while(l_itr.hasNext()) {
                Player l_p = l_itr.next();
                if (l_p.getOwnedArmies() > 0) {
                    l_counter = l_counter + l_p.getOwnedArmies();
                }
            }
            System.out.println("Total Armies left with all Players in Pool: "+l_counter);
            d_LogEntry.setMessage("Total Armies left with all Players in Pool: "+l_counter);
//            if(!p_player.getD_Deck().isEmpty()){
//                System.out.println(p_player.getPlayerName());
//                p_player.showCards();
//                System.out.println(p_player.getPlayerName());
//            }

            //case when atleast one player has any army/armies left
            if (l_counter > 0) {
                if(!p_player.getD_isHuman()){
                    //Call for issueOrder() of non human Player
                    p_player.issueOrder();
                    d_GamePhase = InternalPhase.TURN;
                    return d_GamePhase;
                }
                else {
                //Case for Human Player
                    switch (l_commandName) {
                        case "deploy":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null) || !(l_data[2] == null)) {
                                    if (this.isNumeric(l_data[1]) || this.isNumeric(l_data[2])) {
                                        l_countryId = l_data[1];
                                        l_numberOfArmies = Integer.parseInt(l_data[2]);
                                        boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                        boolean l_checkArmies = (p_player.getOwnedArmies() >= l_numberOfArmies);
                                        if (l_checkOwnedCountry && l_checkArmies) {
                                            p_player.addOrder(new Deploy(p_player, l_countryId, l_numberOfArmies));
                                            p_player.issue_order();
                                            p_player.setOwnedArmies(p_player.getOwnedArmies() - l_numberOfArmies);
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " deploy order added to Players OrdersList: " + l_data[0] + "  " + l_data[1] + " " + l_data[2]);
                                            System.out.println("Player " + p_player.getPlayerName() + " now has " + p_player.getOwnedArmies() + " Army units left!");
                                            d_LogEntry.setMessage("Player " + p_player.getPlayerName() + " now has " + p_player.getOwnedArmies() + " Army units left!");
                                        } else {
                                            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                                            d_LogEntry.setMessage("Country not owned by player or insufficient Army units | please pass to next player");
                                        }
                                        d_GamePhase = InternalPhase.TURN;
                                        break;
                                    }
                                } else
                                    System.out.println("Invalid Command");
                                d_LogEntry.setMessage("Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                                d_LogEntry.setMessage("Country not owned by player or insufficient Army units | please pass to next player");
                            }
                            break;

                        case "advance":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null) || !(l_data[2] == null) || !(l_data[3] == null)) {
                                    if (this.isAlphabetic(l_data[1]) || this.isAlphabetic(l_data[2]) || this.isNumeric(l_data[3])) {
                                        l_countryNameFrom = l_data[1];
                                        l_countryNameTo = l_data[2];
                                        l_numberOfArmies = Integer.parseInt(l_data[3]);
                                        boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryNameFrom.toLowerCase());
                                        CountryDetails attackingCountry = p_player.getOwnedCountries().get(l_countryNameFrom.toLowerCase());
                                        CountryDetails defendingCountry = attackingCountry.getNeighbours().get(l_countryNameTo.toLowerCase());
                                        boolean l_checkNeighbourCountry = (l_countryNameTo.equals(defendingCountry.getCountryId()));

                                        //Checks if required armies present on Source territory
                                        CountryDetails l_c = p_player.getOwnedCountries().get(l_countryNameFrom.toLowerCase());
                                        int l_existingArmies = l_c.getNumberOfArmies();

                                        Player l_targetPlayer = null;
                                        for (Player temp : d_Players) {
                                            //check which player has target countryID
                                            if (temp.getOwnedCountries().containsKey(l_countryNameTo.toLowerCase())) {
                                                l_targetPlayer = temp;
                                                break;
                                            }
                                        }

                                        boolean l_checkArmies = (l_existingArmies >= l_numberOfArmies);
                                        if (l_checkOwnedCountry && l_checkNeighbourCountry && l_checkArmies) {
                                            p_player.addOrder(new Advance(p_player, l_countryNameFrom, l_countryNameTo, l_numberOfArmies, l_targetPlayer));
                                            p_player.issue_order();
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " advance order added to Players OrdersList: " + l_data[0] + "  " + l_data[1] + " " + l_data[2]);
                                        } else {
                                            System.out.println("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                            d_LogEntry.setMessage("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                        }
                                        d_GamePhase = InternalPhase.TURN;
                                        break;
                                    }
                                } else
                                    System.out.println("Invalid Command");
                                d_LogEntry.setMessage("Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                d_LogEntry.setMessage("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                            }
                            break;

                        case "bomb":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null)) {
                                    if (this.isAlphabetic(l_data[1])) {
                                        l_countryId = l_data[1];
                                        //checks if countryId is not of current player
                                        boolean l_checkOwnedCountryNotOfCurrent = !p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                        boolean targetCountryNeighbour = false;
                                        //checks if target country id is neighbour to one of current player's country
                                        for (CountryDetails cD : p_player.getOwnedCountries().values()) {
                                            if (cD.getNeighbours().containsKey(l_countryId.toLowerCase()) && !p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase())) {
                                                targetCountryNeighbour = true;
                                                break;
                                            }
                                        }
                                        Player targetPlayer = null;
                                        for (Player temp : d_Players) {

                                            //check which player has target countryID
                                            if (temp.getOwnedCountries().containsKey(l_countryId.toLowerCase())) {
                                                targetPlayer = temp;
                                                break;
                                            }
                                        }
                                        boolean checkCard = p_player.doesCardExists("Bomb");
                                        if (l_checkOwnedCountryNotOfCurrent && targetCountryNeighbour && checkCard) {
                                            //need to send target player instead of current player as param
                                            p_player.addOrder(new Bomb(p_player, targetPlayer, l_countryId));
                                            p_player.issue_order();
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " Bomb order added to Players OrdersList: " + l_data[0] + "  " + l_data[1]);
                                            p_player.removeCard("Bomb");
                                            d_LogEntry.setMessage("Bomb card removed from players card list");
                                        } else {
                                            System.out.println("Bomb Card not Owned or Country owned by current player or target Country not adjacent | please pass to next player");
                                            d_LogEntry.setMessage("Bomb Card not Owned or Country owned by current player or target Country not adjacent | please pass to next player");
                                        }
                                        d_GamePhase = InternalPhase.TURN;
                                        break;
                                    }
                                } else
                                    System.out.println("Invalid Command");
                                d_LogEntry.setMessage("Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Bomb Card not Owned or Country owned by current player or target Country not adjacent | please pass to next player");
                                d_LogEntry.setMessage("Bomb Card not Owned or Country owned by current player or target Country not adjacent | please pass to next player");
                            }
                            break;

                        case "blockade":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null)) {
                                    if (this.isAlphabetic(l_data[1])) {
                                        l_countryId = l_data[1];
                                        boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                        boolean checkCard = p_player.doesCardExists("Blockade");
                                        if (l_checkOwnedCountry && checkCard) {
                                            p_player.addOrder(new Blockade(p_player, l_countryId));
                                            p_player.issue_order();
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " Blockade order added to Players OrdersList: " + l_data[0] + "  " + l_data[1]);
                                            p_player.removeCard("Blockade");
                                            d_LogEntry.setMessage("Bloackade card removed from Player's cardList ");
                                        } else {
                                            System.out.println("Blockade Card not Owned or Country not owned by current player | please pass to next player");
                                            d_LogEntry.setMessage("Blockade Card not Owned or Country not owned by current player | please pass to next player");
                                        }
                                        d_GamePhase = InternalPhase.TURN;
                                        break;
                                    }
                                } else
                                    System.out.println("Invalid Command");
                                d_LogEntry.setMessage("Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Blockade Card not Owned or Country not owned by current player | please pass to next player");
                                d_LogEntry.setMessage("Blockade Card not Owned or Country not owned by current player | please pass to next player");
                            }
                            break;

                        case "airlift":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null) || !(l_data[2] == null) || !(l_data[3] == null)) {
                                    if (this.isAlphabetic(l_data[1]) || this.isAlphabetic(l_data[2]) || this.isNumeric(l_data[3])) {
                                        l_sourceCountryId = l_data[1];
                                        l_targetCountryId = l_data[2];
                                        l_numberOfArmies = Integer.parseInt(l_data[3]);
                                        boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_sourceCountryId.toLowerCase());
                                        boolean l_checkTargetOwnedCountry = p_player.getOwnedCountries().containsKey(l_targetCountryId.toLowerCase());

                                        //check armies from map which are deployed on source Country
                                        CountryDetails l_c = p_player.getOwnedCountries().get(l_sourceCountryId.toLowerCase());
                                        int l_existingArmies = l_c.getNumberOfArmies();
                                        boolean l_checkArmies = (l_existingArmies >= l_numberOfArmies);
                                        boolean checkCard = p_player.doesCardExists("Airlift");
                                        if (l_checkOwnedCountry && l_checkTargetOwnedCountry && l_checkArmies && checkCard) {
                                            p_player.addOrder(new Airlift(p_player, l_sourceCountryId, l_targetCountryId, l_numberOfArmies));
                                            p_player.issue_order();
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " Airlift order added to Players OrdersList: " + l_data[0] + "  " + l_data[1] + " " + l_data[2] + " " + l_data[3]);
                                            p_player.removeCard("Airlift");
                                            d_LogEntry.setMessage("Airlift card removed from Player's cardList ");
                                        } else {
                                            System.out.println("Airlift Card not Owned or Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                                            d_LogEntry.setMessage("Airlift Card not Owned or Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                                        }
                                        d_GamePhase = InternalPhase.TURN;
                                        break;
                                    }
                                } else
                                    System.out.println("Invalid Command");
                                d_LogEntry.setMessage("Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Airlift Card not Owned or Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                                d_LogEntry.setMessage("Airlift Card not Owned or Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                            }
                            break;

                        case "negotiate":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                if (!(l_data[1] == null)) {
                                    if (this.isAlphabetic(l_data[1])) {
                                        Player l_NegPlayer = getPlayerByName(l_data[1]);
                                        boolean checkCard = p_player.doesCardExists("Diplomacy");
                                        if (checkCard) {
                                            p_player.addOrder(new Diplomacy(p_player, l_NegPlayer));
                                            p_player.issue_order();
                                            d_LogEntry.setMessage(p_player.getPlayerName() + " Diplomacy order added to Players OrdersList: " + l_data[0] + "  " + l_data[1] + " " + l_data[2] + " " + l_data[3]);
                                            p_player.removeCard("Diplomacy");
                                            d_LogEntry.setMessage("Diplomacy card removed from Player's cardList ");
                                        }
                                    }
                                } else
                                    System.out.println("Diplomacy Card not Owned or Invalid Command");
                                d_LogEntry.setMessage("Diplomacy Card not Owned or Invalid Command");
                            } catch (Exception e) {
                                System.out.println("Diplomacy Card not Owned or Invalid Player name");
                                d_LogEntry.setMessage("Diplomacy Card not Owned or Invalid Player name");
                            }
                            break;

                        case "pass":
                            d_LogEntry.setCommand(l_commandName + " Command is being executed");
                            try {
                                d_GamePhase = InternalPhase.TURN;
                            } catch (Exception e) {
                                System.out.println("Invalid Command - it should be of the form -> deploy countryID num | pass");
                                d_LogEntry.setMessage("Invalid Command - it should be of the form -> deploy countryID num | pass");
                            }
                            break;

                        case "savegame":
                            try {
                                if (l_data.length == 2) {
                                    if (isAlphabetic(l_data[1])) {
                                        String fileName = l_data[1];
                                        setGame();
                                        boolean l_save=d_RunG.saveGame(this.d_Game, fileName);
                                        if(l_save) {
                                        System.out.println("current Game saved is saved ");
                                        d_LogEntry.setMessage("current Game saved is saved");
                                        }else {
                                        	 System.out.println("current Game saved is not saved  ");
                                             d_LogEntry.setMessage("current Game saved is not saved");
                                        }
                                        }
                                } else {
                                    String message = "Invalid command. enter file name to save a game.";
                                    d_LogEntry.setMessage("Invalid command. enter file name to save a game.");
                                }

                            } catch (ArrayIndexOutOfBoundsException e) {
                                String message = "Invalid Command, It should be 'savegame filename'";
                                d_LogEntry.setMessage("Invalid Command, It should be 'savegame filename'");
                            }
                            break;

                        case "showmap":
                            d_Phase.showMap(d_Players, d_Map);
                            break;

                        case "stop":
                            if (l_counter > 0) {
                                System.out.println("There are still some armies left to be deployed!");
                                d_GamePhase = InternalPhase.ISSUE_ORDERS;
                            } else {
                                System.out.println("Getting you to Execution Phase");
                                d_GamePhase = InternalPhase.EXECUTE_ORDERS;
                            }
                            return d_GamePhase;

                        default:
                            System.out.println("Invalid command - either use deploy | advance | pass | special commands | showmap commands in ISSUE_ORDERS InternalPhase");
                            d_LogEntry.setMessage("Invalid command - either use deploy | advance | pass | special commands | showmap commands in ISSUE_ORDERS InternalPhase");
                            break;
                    }
                }
            }else {
                    //no armies left to deploy, so execute orders
                    System.out.println("press ENTER to continue to execute InternalPhase..");
                    d_LogEntry.setMessage("No more armies left,move to execute Phase");
                    d_GamePhase = InternalPhase.EXECUTE_ORDERS;
                    return d_GamePhase;
                }
            }

        //EXECUTE_ORDERS InternalPhase
        //EXECUTE ORDERS : execute, showmap
        else if (d_GamePhase.equals(InternalPhase.EXECUTE_ORDERS)) {
            d_LogEntry.setMessage("In Execute Orders Phase:");
            //Execution of Orders for nonHuman Player
            if(!p_player.getD_isHuman()){
                //Call for issueOrder() of non human Player
                int l_count = 0;
                for (Player l_p : d_Players) {
                    Queue<Order> l_temp = l_p.getD_orderList();
                    l_count = l_count +l_temp.size();
                }

                if(l_count == 0){
                    System.out.println("Orders already executed!");
                    d_LogEntry.setMessage("Orders already executed!");
                    d_Phase.showMap(d_Players, d_Map);
                    d_GamePhase = InternalPhase.ISSUE_ORDERS;
                    return d_GamePhase;
                }
                else{
                    System.out.println("Total Orders  :" + l_count);
                    d_LogEntry.setMessage("Total Orders  :" + l_count);
                    while (l_count != 0) {
                        for (Player l_p : d_Players) {
                            Queue<Order> l_temp = l_p.getD_orderList();
                            if (l_temp.size() > 0) {
                                Order l_toRemove = l_p.next_order();
                                System.out.println("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                d_LogEntry.setMessage("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                l_toRemove.execute();
                            }
                        }
                        l_count--;
                    }
                    for(Player l_p : d_Players) {
                        l_p.flushNegotiators();
                    }
                    System.out.println("Orders executed!");
                    d_LogEntry.setMessage("Orders executed!");
                    //d_Phase.showMap(d_Players, d_Map);
                    d_Phase.reinforce();
                    //Check if any Player owns all Countries
                    for (Player l_p : d_Players){
                        if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                            System.out.println(l_p.getPlayerName()+" has Won the Game!");
                            d_LogEntry.setMessage(l_p.getPlayerName()+" has Won the Game!");
                            d_LogEntry.detach(d_WriteLog);
                            System.exit(0);
                        }
                    }
                    //check if any player needs to be removed as of losing all territories
                    for (Player l_p : d_Players){
                        if(l_p.getOwnedCountries().size() == 0){
                            System.out.println(l_p.getPlayerName()+" has lost all its territories and is no longer part of the game");
                            d_LogEntry.setMessage(l_p.getPlayerName()+" has lost all its territories and is no longer part of the game");
                            d_Players.remove(l_p);
                        }
                    }

                    System.out.println("Current Orders were executed,Starting again with assigning Reinforcements!");
                    System.out.println("Reinforcements assigned! Players can provide deploy Orders now!");
                    d_LogEntry.setMessage("Current Orders were executed,Starting again with assigning Reinforcements!");
                    d_LogEntry.setMessage("Reinforcements assigned! Players can provide deploy Orders now!");
                    d_GamePhase = InternalPhase.ISSUE_ORDERS;
                }

                return d_GamePhase;
            }
            switch (l_commandName) {
                case "execute":
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_LogEntry.setMessage("Executing orders in round robin way:");
                    int l_count = 0;
                    for (Player l_p : d_Players) {
                        Queue<Order> l_temp = l_p.getD_orderList();
                        l_count = l_count +l_temp.size();
                    }

                    if(l_count == 0){
                        System.out.println("Orders already executed!");
                        d_LogEntry.setMessage("Orders already executed!");
                        d_Phase.showMap(d_Players, d_Map);
                        d_GamePhase = InternalPhase.ISSUE_ORDERS;
                        return d_GamePhase;
                    }
                    else{
                        System.out.println("Total Orders  :" + l_count);
                        d_LogEntry.setMessage("Total Orders  :" + l_count);
                        while (l_count != 0) {
                            for (Player l_p : d_Players) {

                                Queue<Order> l_temp = l_p.getD_orderList();
                                if (l_temp.size() > 0) {
                                    Order l_toRemove = l_p.next_order();
                                    System.out.println("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                    d_LogEntry.setMessage("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                    l_toRemove.execute();
                                }
                            }
                            l_count--;
                        }
                        for(Player l_p : d_Players) {
                        	l_p.flushNegotiators();
                        }
                        System.out.println("Orders executed!");
                        d_LogEntry.setMessage("Orders executed!");
                        d_Phase.showMap(d_Players, d_Map);
                        d_Phase.reinforce();
                        //Check if any Player owns all Countries
                        for (Player l_p : d_Players){
                            if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                                System.out.println(l_p.getPlayerName()+" has Won the Game!");
                                d_LogEntry.setMessage(l_p.getPlayerName()+" has Won the Game!");
                                d_LogEntry.detach(d_WriteLog);
                                System.exit(0);
                            }
                        }
                        //check if any player needs to be removed as of losing all territories
                        for (Player l_p : d_Players){
                            if(l_p.getOwnedCountries().size() == 0){
                                System.out.println(l_p.getPlayerName()+" has lost all its territories and is no longer part of the game");
                                d_LogEntry.setMessage(l_p.getPlayerName()+" has lost all its territories and is no longer part of the game");
                                d_Players.remove(l_p);
                            }
                        }

                        System.out.println("Current Orders were executed,Starting again with assigning Reinforcements!");
                        System.out.println("Reinforcements assigned! Players can provide deploy Orders now!");
                        System.out.println("\nnext Player can provide deploy | pass order..");
                        d_LogEntry.setMessage("Current Orders were executed,Starting again with assigning Reinforcements!");
                        d_LogEntry.setMessage("Reinforcements assigned! Players can provide deploy Orders now!");
                        d_LogEntry.setMessage("\nnext Player can provide deploy | pass order..");

                        d_GamePhase = InternalPhase.ISSUE_ORDERS;
                    }
                    break;

                case "showmap":
                    d_LogEntry.setCommand(l_commandName+" Command is being executed");
                    d_Phase.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Execute Order InternalPhase has commenced, either use showmap | execute");
                    d_LogEntry.setMessage("Execute Order InternalPhase has commenced, either use showmap | execute");
                    break;
            }
        }
        
    	
    	return d_GamePhase;
    }
    /**
     * Gets Player Name of Current player
     * @param p_playerName player object
     * @return player name
     */
    private Player getPlayerByName(String p_playerName) {
        for(Player l_player:d_Players) {
            if(l_player.getPlayerName().equals(p_playerName))
                return l_player;
        }
        return null;
    }
	
}