package controller;

import model.*;
import view.PlayRisk;

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
    public StartUp d_StartUp;
    public AssignReinforcement d_Arfc;
    public Phase d_GamePhase;
    public ArrayList<Player> d_Players;
    public PlayRisk d_Play;

    public GameEngine() {
        d_Map = new GameMap();
        d_RunG = new RunGameEngine();
        d_StartUp = new StartUp();
        d_Arfc = new AssignReinforcement();
        d_Players = new ArrayList<Player>();
        d_GamePhase = Phase.NULL;
        d_Play = new PlayRisk();
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
     * Setter method to set Game Phase.
     * @param p_gamePhase Phase value to be set.
     */
    public void setGamePhase(Phase p_gamePhase) {
        this.d_GamePhase = p_gamePhase;
    }

    /**
     * Function responsible for parsing user commands passed on Command line interface.
     * Calls appropriate methods, and Updates game Phases.
     * @param p_player Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return next game phase
     */
    public Phase parseCommand(Player p_player, String p_newCommand) {
        int l_controlValue = 0;
        int l_numberOfArmies = 0;
        String l_mapName = null;
        String l_continentId = null;
        String l_countryId = null;
        String l_neighborCountryId = null;
        String l_playerName = null;
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];

        //initial command line commands
        //NULL : editmap / loadmap
        if (d_GamePhase.equals(Phase.NULL)) {
            switch (l_commandName) {
                case "editmap":
                    try {
                        if (l_data[1] != "") {
                            if (this.isMapNameValid(l_data[1])) {
                                l_mapName = l_data[1];
                                d_Map = d_RunG.editMap(l_mapName);
                                System.out.println("Editing for Map: " + l_mapName);
                                d_GamePhase = Phase.EDITMAP;
                            } else {
                                System.out.println("Invalid Map Name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - try command -> editmap sample.map");
                    }
                    break;

                case "loadmap":
                    try {
                        if (l_data[1] != null) {
                            if (this.isMapNameValid(l_data[1])) {
                                l_mapName = l_data[1];
                                d_Map = d_RunG.loadMap(l_mapName);
                                if (d_Map != null) {
                                    if (!d_Map.getValid()) {
                                        System.out.println("Map is not valid");
                                        d_GamePhase = Phase.NULL;
                                    } else {
                                        System.out.println("Map is valid. Please Add players -> ");
                                        d_GamePhase = Phase.STARTUP;
                                    }
                                } else {
                                    d_GamePhase = Phase.NULL;
                                }
                            } else {
                                System.out.println("Map name not valid");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid command - try -> loadmap sample.map");
                    }
                    break;
                default:
                    System.out.println("try LoadMap or EditMap first using commands: loadmap sample.map or editmap sample.map");
                    break;
            }
        }
        //EDITMAP Phase
        //EDIT MAP : editcontinent, editcountry, editneighbour, savemap, showmap, editmap, loadmap, validatemap
        else if (d_GamePhase.equals(Phase.EDITMAP)) {
            switch (l_commandName) {
                case "editcontinent":
                    try {
                        for (int i = 1; i < l_data.length; i++) {
                            if (l_data[i].equals("-add")) {
                                if (this.isAlphabetic(l_data[i + 1])) {
                                    l_continentId = l_data[i + 1];
                                }
                                else {
                                    System.out.println("Invalid Continent ID");
                                }
                                l_controlValue = Integer.parseInt(l_data[i + 2]);

                                boolean l_check = d_RunG.addContinent(d_Map, l_continentId , l_controlValue);
                                if (l_check) {
                                    System.out.println(l_continentId + " continent added to the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Continent already exists - Please add valid Continent ID");
                                }
                            } else if (l_data[i].equals("-remove")) {
                                if (this.isAlphabetic(l_data[i + 1])) {
                                    l_continentId = l_data[i + 1];
                                }
                                else
                                    System.out.println("Invalid Continent Id");

                                boolean l_check = d_RunG.removeContinent(d_Map, l_continentId);
                                if (l_check) {
                                    System.out.println("Continent removed from Map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Continent doesn't exist - Please enter valid Continent ID");
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println("Invalid command - It should be of the form editcontinent -add continentID controlvalue -remove continentID");
                    } catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form editcontinent -add continentID controlvalue -remove continentID");
                    }
                    break;

                case "editcountry":
                    try {
                        for (int i = 1; i < l_data.length; i++) {
                            if (l_data[i].equals("-add")) {
                                if (this.isAlphabetic(l_data[i + 1]) || this.isAlphabetic(l_data[i + 2])) {
                                    l_countryId = l_data[i + 1];
                                    l_continentId = l_data[i + 2];
                                } else {
                                    System.out.println("Invalid country name");
                                }
                                boolean l_check = d_RunG.addCountry(d_Map, l_countryId, l_continentId);
                                if (l_check) {
                                    System.out.println("Country added to the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country already exists - Please add valid Country ID");
                                }
                            } else if (l_data[i].equals("-remove")) {
                                if (this.isAlphabetic(l_data[i + 1])) {
                                    l_countryId = l_data[i + 1];
                                }
                                else {
                                    System.out.println("Invalid country name");
                                }
                                boolean l_check = d_RunG.removeCountry(d_Map, l_countryId);
                                if (l_check) {
                                    System.out.println("Country removed from the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country does not exist - Please enter valid country name");
                                }
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editcountry -add countryId continentId -remove countryId");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form editcountry -add countryId continentId -remove countryId");
                    }
                    break;

                case "editneighbor":
                    try {
                        for (int i = 1; i < l_data.length; i++) {
                            if (l_data[i].equals("-add")) {
                                if (this.isAlphabetic(l_data[i + 1]) || this.isAlphabetic(l_data[i + 2])) {
                                    l_countryId = l_data[i + 1];
                                    l_neighborCountryId = l_data[i + 2];
                                } else {
                                    System.out.println("Invalid country ID");
                                }

                                boolean l_check = d_RunG.addNeighbor(d_Map, l_countryId, l_neighborCountryId);
                                if (l_check) {
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country does not exist - Please enter valid countryID neighborcountryID");
                                }
                            } else if (l_data[i].equals("-remove")) {
                                if (this.isAlphabetic(l_data[i + 1]) || this.isAlphabetic(l_data[i + 2])) {
                                    l_countryId = l_data[i + 1];
                                    l_neighborCountryId = l_data[i + 2];
                                } else {
                                    System.out.println("Invalid country ID");
                                }

                                boolean l_check = d_RunG.removeNeighbor(d_Map, l_countryId, l_neighborCountryId);
                                if (l_check) {
                                    d_GamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Country does not exist - Please enter valid countryID neighborcountryID");
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                    }
                    break;

                case "savemap":
                    try {
                        if (l_data[1] != "") {
                            if (this.isMapNameValid(l_data[1])) {
                                l_mapName = l_data[1];
                                boolean l_check = d_RunG.saveMap(d_Map, l_mapName);
                                if (l_check) {
                                    System.out.println("Map file saved successfully");
                                    d_GamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Error in saving - invalid map");
                            } else
                                System.out.println("Map name not valid!");
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form(without extension) savemap filename");
                    }
                    break;

                case "showmap":
                    d_RunG.showMap(d_Map);
                    d_GamePhase = Phase.EDITMAP;
                    break;

                case "editmap":
                    try {
                        if (l_data[1] != null) {
                            if (this.isMapNameValid(l_data[1])) {
                                l_mapName = l_data[1];
                                d_Map = d_RunG.editMap(l_mapName);
                                System.out.println("Start editing " + l_mapName);
                                d_GamePhase = Phase.EDITMAP;
                            } else
                                System.out.println("Map name is invalid!");
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editmap sample.map");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form editmap sample.map");
                    }
                    break;

                case "loadmap":
                    try {
                        if (l_data[1] != "") {
                            if (this.isMapNameValid(l_data[1])) {
                                l_mapName = l_data[1];
                                d_Map = d_RunG.loadMap(l_mapName);
                                if (d_Map != null) {
                                    if (!d_Map.getValid()) {
                                        System.out.println("Invalid Map");
                                        d_GamePhase = Phase.NULL;
                                    } else {
                                        System.out.println("Map is valid. Add players now.");
                                        d_GamePhase = Phase.STARTUP;
                                    }
                                } else {
                                    d_GamePhase = Phase.NULL;
                                }
                            } else
                                System.out.println("Map name is invalid!");
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form loadmap sample.map");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form loadmap sample.map");
                    }
                    break;

                case "validatemap":
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
        //STARTUP Phase
        //STARTUP : gameplayer, assigncountries, showmap
        else if (d_GamePhase.equals(Phase.STARTUP)) {
            switch (l_commandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < l_data.length; i++) {
                            if (l_data[i].equals("-add")) {
                                if (this.isPlayerNameValid(l_data[i + 1])) {
                                    l_playerName = l_data[i + 1];
                                    boolean l_check = d_StartUp.addPlayer(d_Players, l_playerName);
                                    if (l_check) {
                                        System.out.println("Player added!");
                                    } else {
                                        System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
                                    }
                                    d_GamePhase = Phase.STARTUP;
                                } else {
                                    System.out.println("Invalid Player Name");
                                }
                            } else if (l_data[i].equals("-remove")) {
                                if (this.isPlayerNameValid(l_data[i + 1])) {
                                    l_playerName = l_data[i + 1];
                                    boolean l_check = d_StartUp.removePlayer(d_Players, l_playerName);
                                    if (l_check)
                                        System.out.println("Player removed!");
                                    else
                                        System.out.println("Player doesn't exist");
                                    d_GamePhase = Phase.STARTUP;
                                } else
                                    System.out.println("Invalid Player Name");
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
                    }
                    catch(Exception e) {
                        System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
                    }
                    break;

                case "assigncountries":
                    boolean l_check = d_StartUp.assignCountries(d_Map, d_Players);
                    if (l_check) {
                        System.out.println("Countries allocated randomly amongst Players");
                        d_GamePhase = Phase.ISSUE_ORDERS;
                    }
                    d_GamePhase = Phase.ISSUE_ORDERS;
                    break;

                case "showmap":
                    d_StartUp.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer command/assigncountries command/showmap command in start up phase!");
                    break;
            }
        }

        //ISSUE_ORDERS Phase
        //ISSUE_ORDERS : deploy - orders, showmap
        else if (d_GamePhase.equals(Phase.ISSUE_ORDERS)) {
            int l_counter = 0;
            //traverses through all players to check if armies left in pool
            Iterator<Player> l_itr = d_Players.listIterator();
            while(l_itr.hasNext()) {
                Player l_p = l_itr.next();
                System.out.println("Player "+l_p.getPlayerName()+" has "+l_p.getOwnedArmies()+" Armies currently!");
                if (l_p.getOwnedArmies() > 0) {
                    l_counter = l_counter + l_p.getOwnedArmies();
                }
            }
            System.out.println("Total Armies left with all Players in Pool: "+l_counter);
            //case when atleast one player has any army/armies left
            if(l_counter > 0){
                switch (l_commandName) {
                    case "deploy":
                        try {
                            if (!(l_data[1] == null) || !(l_data[2] == null)) {
                                if (this.isNumeric(l_data[1]) || this.isNumeric(l_data[2])) {
                                    l_countryId = l_data[1];
                                    l_numberOfArmies = Integer.parseInt(l_data[2]);
                                    boolean l_checkOwnedCountry = p_player.getOwnedCountries().containsKey(l_countryId.toLowerCase());
                                    boolean l_checkArmies = (p_player.getOwnedArmies() >= l_numberOfArmies);
                                    System.out.println("Player "+p_player.getPlayerName()+" Can provide deploy order or pass order");
                                    if(l_checkOwnedCountry && l_checkArmies){
                                        Order l_temp = new Order(p_player, l_countryId, l_numberOfArmies);
                                        p_player.addOrder(l_temp);
                                        p_player.issue_order();
                                        p_player.setOwnedArmies(p_player.getOwnedArmies()-l_numberOfArmies);
                                        System.out.println("Player "+p_player.getPlayerName()+" now has "+p_player.getOwnedArmies()+" Army units left!");
                                    }
                                    else{
                                        System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                                    }
                                    d_GamePhase = Phase.TURN;
                                    break;
                                }
                            } else
                                System.out.println("Invalid Command");

                        }catch (Exception e) {
                            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
                        }
                        break;

                case "pass":
                    try {
                        d_GamePhase = Phase.TURN;
                    }catch (Exception e) {
                        System.out.println("Invalid Command - it should be of the form -> deploy countryID num | pass");
                    }
                    break;

                case "showmap":
                    d_StartUp.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Invalid command - either use deploy | pass | showmap commands in ISSUE_ORDERS Phase");
                    break;
            }
        }
            else{
                //no armies left to deploy, so execute orders
                System.out.println("press ENTER to continue to execute Phase..");
                d_GamePhase = Phase.EXECUTE_ORDERS;
                return d_GamePhase;
            }
        }

        //EXECUTE_ORDERS Phase
        //EXECUTE ORDERS : execute, showmap
        else if (d_GamePhase.equals(Phase.EXECUTE_ORDERS)) {
            switch (l_commandName) {
                case "execute":
                    int l_count = 0;
                    for (Player l_p : d_Players) {
                        Queue<Order> l_temp = l_p.getD_orderList();
                            l_count = l_count +l_temp.size();
                        }

                    if(l_count == 0){
                        System.out.println("Orders already executed!");
                        d_StartUp.showMap(d_Players, d_Map);
                        d_GamePhase = Phase.ISSUE_ORDERS;
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
                        d_StartUp.showMap(d_Players, d_Map);
                        d_GamePhase = Phase.ISSUE_ORDERS;
                    }
                    break;

                case "showmap":
                    d_StartUp.showMap(d_Players, d_Map);
                    break;

                case "exit":
                    System.out.println("Build 1 ENDS HERE!");
                    exit(0);

                default:
                    System.out.println("Execute Order Phase has commenced, either use showmap | execute");
                    break;
            }
        }
            return d_GamePhase;
    }
}
