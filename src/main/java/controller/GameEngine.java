package controller;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import static java.lang.System.exit;

/**
 * Class responsible to interpret different commands as provided by User over Command Line Interface
 * Call required methods as appropriate, also update game phases based on method responses.
 *
 */
public class GameEngine {

    public GameMap d_Map;
    public RunGameEngine d_RunG;
    //public StartUp d_StartUp;
    public AssignReinforcement d_Arfc;
    public InternalPhase d_GamePhase;
    public ArrayList<Player> d_Players;
    public PlayRisk d_Play;
    public Phase d_Phase;
    public Card d_Card;
    public LogEntryBuffer d_LogEntry;
    public WriteLogEntry d_WriteLog;

    /**
     * Initializes the variables and objects required to play the game and act on user commands
     */
    public GameEngine()  {
        d_Map = new GameMap();
        d_RunG = new RunGameEngine();
        // d_StartUp = new StartUp();
        d_Arfc = new AssignReinforcement();
        d_Players = new ArrayList<Player>();
        d_GamePhase = InternalPhase.NULL;
        d_Play = new PlayRisk();

        d_LogEntry = new LogEntryBuffer();
        d_WriteLog = new WriteLogEntry();
        d_LogEntry.attach(d_WriteLog);
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
     *Sets the current phase
     */
    public void setPhase(Phase p_Phase)
    {
        d_Phase = p_Phase;
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
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];

        //initial command line commands
        //NULL : editmap / loadmap
        if (d_GamePhase.equals(InternalPhase.NULL)) {
            switch (l_commandName) {
                case "editmap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.editMap(l_data, l_mapName);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "loadmap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.loadMap(l_data,l_mapName);
                    String str1=d_Phase.getD_PhaseName();
                    System.out.println(str1);
                    break;
                default:
                    System.out.println("try LoadMap or EditMap first using commands: loadmap sample.map or editmap sample.map");
                    break;
            }
        }
        //EDITMAP InternalPhase
        //EDIT MAP : editcontinent, editcountry, editneighbour, savemap, showmap, editmap, loadmap, validatemap
        else if (d_GamePhase.equals(InternalPhase.EDITMAP)) {
            switch (l_commandName) {
                case "editcontinent":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.editContinent(l_data, l_continentId, l_controlValue);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "editcountry":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.editCountry(l_data, l_continentId, l_countryId) ;
                    String str1=d_Phase.getD_PhaseName();
                    System.out.println(str1);
//
                    break;

                case "editneighbor":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.editNeighbour(l_data, l_countryId, l_neighborCountryId);
                    String str2=d_Phase.getD_PhaseName();
                    System.out.println(str2);
//
                    break;

                case "savemap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.savemap(l_data, l_mapName);
                    String str3=d_Phase.getD_PhaseName();
                    System.out.println(str3);
//
                    break;

                case "showmap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.showMap(d_Map);
                    String str4=d_Phase.getD_PhaseName();
                    System.out.println(str4);
                    // d_RunG.showMap(d_Map);
                    d_GamePhase = InternalPhase.EDITMAP;
                    break;

                case "editmap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.editMap(l_data, l_mapName);
                    String str5=d_Phase.getD_PhaseName();
                    System.out.println(str5);
                    break;

                case "loadmap":
                    setPhase(new PreLoad(this));
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.loadMap(l_data,l_mapName);
                    String str6=d_Phase.getD_PhaseName();
                    System.out.println(str6);
//
                    break;

                case "validatemap":
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    if(d_RunG.validateMap(d_Map)) {
                        System.out.println("Map is Validated and Correct!");
                    }
                    else {
                        System.out.println("Invalid map");
                    }
                    break;

                default:
                    System.out.println("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
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
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.gamePlayer(l_data,d_Players, l_playerName);
                    String str=d_Phase.getD_PhaseName();
                    System.out.println(str);
//
                    break;

                case "assigncountries":
                    boolean l_check = d_Phase.assignCountries(d_Map, d_Players);
                    if (l_check) {
                        System.out.println("Countries allocated randomly amongst Players");
                        setPhase(new MainPlay(this));
                        d_LogEntry.setGamePhase(d_Phase);
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
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
                    d_LogEntry.setGamePhase(d_Phase);
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    String str2=d_Phase.getD_PhaseName();
                    System.out.println(str2);
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer command/assigncountries command/showmap command in start up InternalPhase!");
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
            //case when atleast one player has any army/armies left
            if(l_counter >= 0){
                switch (l_commandName) {
                    case "deploy":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        try {
                            if (!(l_data[1] == null) || !(l_data[2] == null)) {
                                if (this.isNumeric(l_data[1]) || this.isNumeric(l_data[2])) {
                                    l_countryId = l_data[1];
                                    l_numberOfArmies = Integer.parseInt(l_data[2]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                    boolean l_checkArmies = (p_player.getOwnedArmies() >= l_numberOfArmies);
                                    if(l_checkOwnedCountry && l_checkArmies){
                                        p_player.addOrder(new Deploy(p_player, l_countryId, l_numberOfArmies));
                                        p_player.issue_order();
                                        p_player.setOwnedArmies(p_player.getOwnedArmies()-l_numberOfArmies);
                                        System.out.println("Player "+p_player.getPlayerName()+" now has "+p_player.getOwnedArmies()+" Army units left!");
                                    }
                                    else{
                                        System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                                    }
                                    d_GamePhase = InternalPhase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                        }
                        break;

                    case "advance":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
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
                                    CountryDetails l_c= p_player.getOwnedCountries().get(l_countryNameFrom.toLowerCase());
                                    int l_existingArmies = l_c.getNumberOfArmies();

                                    Player l_targetPlayer = null;
                                    for(Player temp : d_Players){
                                        //check which player has target countryID
                                        if(temp.getOwnedCountries().containsKey(l_countryNameTo.toLowerCase())){
                                            l_targetPlayer = temp;
                                            break;
                                        }
                                    }

                                    boolean l_checkArmies = (l_existingArmies >= l_numberOfArmies);
                                    if(l_checkOwnedCountry && l_checkNeighbourCountry && l_checkArmies){
                                        p_player.addOrder(new Advance(p_player, l_countryNameFrom,l_countryNameTo, l_numberOfArmies,l_targetPlayer));
                                        p_player.issue_order();
                                    }
                                    else{
                                        System.out.println("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                                    }
                                    d_GamePhase = InternalPhase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Country not owned by player or target Country not adjacent or insufficient Army units | please pass to next player");
                        }
                        break;

                    case "bomb":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        try {
                            if (!(l_data[1] == null)) {
                                if (this.isAlphabetic(l_data[1])) {
                                    l_countryId = l_data[1];
                                    //checks if countryId is not of current player
                                    boolean l_checkOwnedCountryNotOfCurrent = !p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                    boolean targetCountryNeighbour = false;
                                    //checks if target country id is neighbour to one of current player's country
                                    for(CountryDetails cD : p_player.getOwnedCountries().values()){
                                        if( cD.getNeighbours().containsKey(l_countryId.toLowerCase()) && !p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase())){
                                            targetCountryNeighbour= true;
                                            break;
                                       }
                                    }
                                    Player targetPlayer = null;
                                    for(Player temp : d_Players){
                                        //check which player has target countryID
                                        if(temp.getOwnedCountries().containsKey(l_countryId.toLowerCase())){
                                            targetPlayer = temp;
                                            break;
                                        }
                                    }
                                    boolean checkCard = p_player.doesCardExists("Bomb");
                                    if(l_checkOwnedCountryNotOfCurrent && targetCountryNeighbour && checkCard){
                                        //need to send target player instead of current player as param
                                        p_player.addOrder(new Bomb(p_player,targetPlayer, l_countryId));
                                        p_player.issue_order();
                                        p_player.removeCard("Bomb");
                                    }
                                    else{
                                        System.out.println("Country owned by current player or target Country not adjacent | please pass to next player");
                                    }
                                    d_GamePhase = InternalPhase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Country owned by current player or target Country not adjacent | please pass to next player");
                        }
                        break;

                    case "blockade":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        try {
                            if (!(l_data[1] == null)) {
                                if (this.isAlphabetic(l_data[1])) {
                                    l_countryId = l_data[1];
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                    boolean checkCard = p_player.doesCardExists("Blockade");
                                    if(l_checkOwnedCountry && checkCard){
                                        p_player.addOrder(new Blockade(p_player, l_countryId));
                                        p_player.issue_order();
                                        p_player.removeCard("Blockade");
                                    }
                                    else{
                                        System.out.println("Country not owned by current player | please pass to next player");
                                    }
                                    d_GamePhase = InternalPhase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Country not owned by current player | please pass to next player");
                        }
                        break;

                    case "airlift":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        try {
                            if (!(l_data[1] == null) || !(l_data[2] == null) || !(l_data[3] == null)) {
                                if (this.isAlphabetic(l_data[1]) || this.isAlphabetic(l_data[2]) || this.isNumeric(l_data[3])) {
                                    l_sourceCountryId = l_data[1];
                                    l_targetCountryId = l_data[2];
                                    l_numberOfArmies = Integer.parseInt(l_data[3]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_sourceCountryId.toLowerCase());
                                    boolean l_checkTargetOwnedCountry = p_player.getOwnedCountries().containsKey(l_targetCountryId.toLowerCase());

                                    //check armies from map which are deployed on source Country
                                    CountryDetails l_c= p_player.getOwnedCountries().get(l_sourceCountryId.toLowerCase());
                                    int l_existingArmies = l_c.getNumberOfArmies();
                                    boolean l_checkArmies = (l_existingArmies >= l_numberOfArmies);
                                    boolean checkCard = p_player.doesCardExists("Airlift");
                                    if(l_checkOwnedCountry && l_checkTargetOwnedCountry && l_checkArmies && checkCard){
                                        p_player.addOrder(new Airlift(p_player, l_sourceCountryId, l_targetCountryId, l_numberOfArmies));
                                        p_player.issue_order();
                                        p_player.removeCard("Airlift");
                                    }
                                    else{
                                        System.out.println("Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                                    }
                                    d_GamePhase = InternalPhase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Source Country or Target Country not owned by player insufficient Army units | please pass to next player");
                        }
                        break;

                    case "pass":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        try {
                            d_GamePhase = InternalPhase.TURN;
                        }catch (Exception e) {
                            System.out.println("Invalid Command - it should be of the form -> deploy countryID num | pass");
                        }
                        break;

                    case "showmap":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        d_Phase.showMap(d_Players, d_Map);
                        break;

                    case "stop":
                        d_LogEntry.setCommand(l_commandName+"Command is being executed");
                        d_GamePhase = InternalPhase.EXECUTE_ORDERS;
                        return d_GamePhase;

                    default:
                        System.out.println("Invalid command - either use deploy | pass | showmap commands in ISSUE_ORDERS InternalPhase");
                        break;
                }
            }
            else{
                //no armies left to deploy, so execute orders
                System.out.println("press ENTER to continue to execute InternalPhase..");
                d_GamePhase = InternalPhase.EXECUTE_ORDERS;
                return d_GamePhase;
            }
        }

        //EXECUTE_ORDERS InternalPhase
        //EXECUTE ORDERS : execute, showmap
        else if (d_GamePhase.equals(InternalPhase.EXECUTE_ORDERS)) {
            d_LogEntry.setMessage("In Ecxecute Orders Phase:");
            switch (l_commandName) {
                case "execute":
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    int l_count = 0;
                    for (Player l_p : d_Players) {
                        Queue<Order> l_temp = l_p.getD_orderList();
                        l_count = l_count +l_temp.size();
                    }

                    if(l_count == 0){
                        System.out.println("Orders already executed!");
                        d_Phase.showMap(d_Players, d_Map);
                        d_GamePhase = InternalPhase.ISSUE_ORDERS;
                        return d_GamePhase;
                    }
                    else{
                        System.out.println("Total Orders  :" + l_count);
                        while (l_count != 0) {
                            for (Player l_p : d_Players) {

                                Queue<Order> l_temp = l_p.getD_orderList();
                                if (l_temp.size() > 0) {
                                    Order l_toRemove = l_p.next_order();
                                    System.out.println("Order: " +l_toRemove+ " executed for player: "+l_p.getPlayerName());
                                    l_toRemove.execute();
                                }
                            }
                            l_count--;
                        }

                        System.out.println("Orders executed!");
                        d_Phase.showMap(d_Players, d_Map);
                        d_Phase.reinforce();

                        //Check if any Player owns all Countries
                        for (Player l_p : d_Players){
                            if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                                System.out.println(l_p.getPlayerName()+" has Won the Game!");
                                System.exit(0);
                            }
                        }
                        //check if any player does not has lost its all territories
                        for (Iterator<Player> iterator = d_Players.iterator(); iterator.hasNext();) {
                            if (iterator.next().getOwnedCountries().size() == 0) {
                            	System.out.println(((Player) iterator).getPlayerName()+"has lost all its territories and is no longer part of the game");
                                iterator.remove();
                            }
                        }

                        System.out.println("Current Orders were executed,Starting again with assigning Reinforcements!");
                        System.out.println("Reinforcements assigned! Players can provide deploy Orders now!");
                        System.out.println("\nPlayer 1 can provide deploy | pass order..");
                        d_GamePhase = InternalPhase.ISSUE_ORDERS;
                    }
                    break;

                case "showmap":
                    d_LogEntry.setCommand(l_commandName+"Command is being executed");
                    d_Phase.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Execute Order InternalPhase has commenced, either use showmap | execute");
                    break;
            }
        }
        return d_GamePhase;
    }
}

