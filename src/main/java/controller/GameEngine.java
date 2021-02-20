package controller;

import model.*;

import java.util.ArrayList;

/**
 * Class responsible to interpret different commands as provided by User over Command Line Interface
 * Call required methods as appropriate, also update game phases based on method responses.
 *
 */
public class GameEngine {

    public static boolean l_allArmiesPlaced = false;
    public GameMap d_Map;
    public RunGameEngine d_RunG;
    public StartUp d_StartUp;
    //public AssignReinforcement d_Arfc;
    public Phase d_GamePhase;
    public ArrayList<Player> d_Players;

    public GameEngine() {
        d_Map = new GameMap();
        d_RunG = new RunGameEngine();
        d_StartUp = new StartUp();
        //d_Arfc = new AssignReinforcement();
        d_Players = new ArrayList<Player>();
        d_GamePhase = Phase.NULL;
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
        int d_ControlValue = 0;
        int d_NumberOfArmies = 0;
        String d_MapName = null;
        String d_ContinentId = null;
        String d_CountryId = null;
        String d_NeighborCountryId = null;
        String d_PlayerName = null;
        String[] d_Data = p_newCommand.split("\\s+");
        String d_CommandName = d_Data[0];

        //initial command line commands
        //NULL : editmap / loadmap
        if (d_GamePhase.equals(Phase.NULL)) {
            switch (d_CommandName) {
                case "editmap":
                    try {
                        if (d_Data[1] != "") {
                            if (this.isMapNameValid(d_Data[1])) {
                                d_MapName = d_Data[1];
                                d_Map = d_RunG.editMap(d_MapName);
                                System.out.println("Editing for Map: " + d_MapName);
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
                        if (d_Data[1] != null) {
                            if (this.isMapNameValid(d_Data[1])) {
                                d_MapName = d_Data[1];
                                d_Map = d_RunG.loadMap(d_MapName);
                                if (d_Map != null) {
                                    if (!d_Map.isValid()) {
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
            switch (d_CommandName) {
                case "editcontinent":
                    try {
                        for (int i = 1; i < d_Data.length; i++) {
                            if (d_Data[i].equals("-add")) {
                                if (this.isAlphabetic(d_Data[i + 1])) {
                                    d_ContinentId = d_Data[i + 1];
                                }
                                else {
                                    System.out.println("Invalid Continent ID");
                                }
                                d_ControlValue = Integer.parseInt(d_Data[i + 2]);

                                boolean check = d_RunG.addContinent(d_Map, d_ContinentId , d_ControlValue);
                                if (check) {
                                    System.out.println(d_ContinentId + " continent added to the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Continent already exists - Please add valid Continent ID");
                                }
                            } else if (d_Data[i].equals("-remove")) {
                                if (this.isAlphabetic(d_Data[i + 1])) {
                                    d_ContinentId = d_Data[i + 1];
                                }
                                else
                                    System.out.println("Invalid Continent Id");

                                boolean check = d_RunG.removeContinent(d_Map, d_ContinentId);
                                if (check) {
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
                        for (int i = 1; i < d_Data.length; i++) {
                            if (d_Data[i].equals("-add")) {
                                if (this.isAlphabetic(d_Data[i + 1]) || this.isNumeric(d_Data[i + 2])) {
                                    d_CountryId = d_Data[i + 1];
                                    d_ContinentId = d_Data[i + 2];
                                } else {
                                    System.out.println("Invalid country name");
                                }
                                boolean check = d_RunG.addCountry(d_Map, d_CountryId, d_ContinentId);
                                if (check) {
                                    System.out.println("Country added to the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country already exists - Please add valid Country ID");
                                }
                            } else if (d_Data[i].equals("-remove")) {
                                if (this.isAlphabetic(d_Data[i + 1])) {
                                    d_CountryId = d_Data[i + 1];
                                }
                                else {
                                    System.out.println("Invalid country name");
                                }
                                boolean check = d_RunG.removeCountry(d_Map, d_CountryId);
                                if (check) {
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
                        for (int i = 1; i < d_Data.length; i++) {
                            if (d_Data[i].equals("-add")) {
                                if (this.isAlphabetic(d_Data[i + 1]) || this.isAlphabetic(d_Data[i + 2])) {
                                    d_CountryId = d_Data[i + 1];
                                    d_NeighborCountryId = d_Data[i + 2];
                                } else {
                                    System.out.println("Invalid country ID");
                                }

                                boolean check = d_RunG.addNeighbor(d_Map, d_CountryId, d_NeighborCountryId);
                                if (check) {
                                    System.out.println("Neighbor added to the map");
                                    d_GamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country does not exist - Please enter valid countryID neighborcountryID");
                                }
                            } else if (d_Data[i].equals("-remove")) {
                                if (this.isAlphabetic(d_Data[i + 1]) || this.isAlphabetic(d_Data[i + 2])) {
                                    d_CountryId = d_Data[i + 1];
                                    d_NeighborCountryId = d_Data[i + 2];
                                } else {
                                    System.out.println("Invalid country ID");
                                }

                                boolean check = d_RunG.removeNeighbor(d_Map, d_CountryId, d_NeighborCountryId);
                                if (check) {
                                    System.out.println("Neighbor removed from the map");
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
                        if (d_Data[1] != "") {
                            if (this.isMapNameValid(d_Data[1])) {
                                d_MapName = d_Data[1];
                                boolean check = d_RunG.saveMap(d_Map, d_MapName);
                                if (check) {
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
                        if (d_Data[1] != null) {
                            if (this.isMapNameValid(d_Data[1])) {
                                d_MapName = d_Data[1];
                                d_Map = d_RunG.editMap(d_MapName);
                                System.out.println("Start editing " + d_MapName);
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
                        if (d_Data[1] != "") {
                            if (this.isMapNameValid(d_Data[1])) {
                                d_MapName = d_Data[1];
                                d_Map = d_RunG.loadMap(d_MapName);
                                if (d_Map != null) {
                                    if (!d_Map.isValid()) {
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
            switch (d_CommandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < d_Data.length; i++) {
                            if (d_Data[i].equals("-add")) {
                                if (this.isPlayerNameValid(d_Data[i + 1])) {
                                    d_PlayerName = d_Data[i + 1];
                                    boolean check = d_StartUp.addPlayer(d_Players, d_PlayerName);
                                    if (check) {
                                        System.out.println("Player added!");
                                    } else {
                                        System.out.println("Can not add any more player. Max pool of 6 Satisfied!");
                                    }
                                    d_GamePhase = Phase.STARTUP;
                                } else {
                                    System.out.println("Invalid Player Name");
                                }
                            } else if (d_Data[i].equals("-remove")) {
                                if (this.isPlayerNameValid(d_Data[i + 1])) {
                                    d_PlayerName = d_Data[i + 1];
                                    boolean check = d_StartUp.removePlayer(d_Players, d_PlayerName);
                                    if (check)
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
                    //boolean check = d_StartUp.assignCountries(d_Map, d_Players);
                    if (true) {
                        System.out.println("Countries allocated randomly amongst Players");
                    }
                    d_GamePhase = Phase.ASSIGN_REINFORCEMENTS;
                    ///d_StartUp.armyDistribution(d_Players, this, d_GamePhase);
                    d_GamePhase = Phase.DEPLOYMENT;
                    break;

                case "showmap":
                    //d_StartUp.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer command/assigncountries command/showmap command in start up phase!");
                    break;
            }
        }
        //DEPLOYMENT Phase
        //DEPLOYMENT : deploy, showmap
        else if (d_GamePhase.equals(Phase.DEPLOYMENT)) {
            switch (d_CommandName) {
                case "deploy":
                    try {
                        if (!(d_Data[1] == null) || !(d_Data[2] == null)) {
                            if (this.isNumeric(d_Data[1]) || this.isNumeric(d_Data[2])) {
                                d_CountryId = d_Data[1];
                                d_NumberOfArmies = Integer.parseInt(d_Data[2]);
//                                boolean check = d_Arfc.deploy(d_Player, d_CountryId, d_NumberOfArmies);
                                if (true) {
                                    //if (p_player.getOwnedArmies() == 0) {
                                    if (true) {
                                        System.out.println("Deployment phase Successful!");
                                    }
                                }
                            } else
                                System.out.println("Invalid command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form deploy countryID num");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form deploy countryID num");
                    }
                    break;

                case "showmap":
//                    d_StartUp.showMap(d_Players, d_Map);
                    break;

                default:
                    System.out.println("Invalid command - either use deploy or showmap commands in Deployment phase.");
                    break;
            }
        }
        else if (d_GamePhase.equals(Phase.ISSUE_ORDERS)) {

        }
        else if (d_GamePhase.equals(Phase.EXECUTE_ORDERS)) {

        }
            return d_GamePhase;
    }
}
