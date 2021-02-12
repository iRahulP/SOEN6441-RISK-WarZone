package controller;

import model.GameMap;
import model.Phase;
import model.Player;

import java.util.ArrayList;

/**
 * Class responsible to interpret different commands as provided by User over Command Line Interface
 * Call required methods as appropriate, also update game phases based on method responses.
 *
 */
public class Command {

    public static boolean l_allArmiesPlaced = false;
    public GameMap d_map;
    public RunCommand d_runCmd;
    public StartUp d_startUp;
    public AssignReinforcement d_arfc;
    Phase d_gamePhase;
    public ArrayList<Player> d_players;

    public Command() {
        d_map = new GameMap();
        d_runCmd = new RunCommand();
        d_startUp = new StartUp();
        d_arfc = new AssignReinforcement();
        d_players = new ArrayList<Player>();
        d_gamePhase = Phase.NULL;
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
        this.d_gamePhase = p_gamePhase;
    }

    /**
     * Function responsible for parsing user commands passed on Command line interface.
     * Calls appropriate methods, and Updates game Phases.
     * @param p_player Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return next game phase
     */
    public Phase parseCommand(Player p_player, String p_newCommand) {

        int d_controlValue = 0;
        int d_numberOfArmies = 0;
        int d_armiesToFortify = 0;
        int d_continentId = 0;
        int d_countryId = 0;
        int d_neighbourCountryId = 0;
        String d_mapName = null;
        String d_continentName = null;
        String d_countryName = null;
        String d_neighborCountryName = null;
        String d_playerName = null;
        String d_fromCountry = null;
        String d_toCountry = null;
        String[] d_data = p_newCommand.split("\\s+");
        String d_commandName = d_data[0];
//initial command line commands
        //editmap nad loadmap
        if (d_gamePhase.equals(Phase.NULL)) {
            switch (d_commandName) {
                case "editmap":
                    try {
                        if (d_data[1] != "") {
                            if (this.isMapNameValid(d_data[1])) {
                                d_mapName = d_data[1];
                                d_map = d_runCmd.editMap(d_mapName);
                                System.out.println("Editing for Map: " + d_mapName);
                                d_gamePhase = Phase.EDITMAP;
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
                        if (d_data[1] != null) {
                            if (this.isMapNameValid(d_data[1])) {
                                d_mapName = d_data[1];
                                d_map = d_runCmd.loadMap(d_mapName);
                                if (d_map != null) {
                                    if (!d_map.getValid()) {
                                        System.out.println("Map is not valid");
                                        d_gamePhase = Phase.NULL;
                                    } else {
                                        System.out.println("Map is valid. Please Add players -> ");
                                        d_gamePhase = Phase.STARTUP;
                                    }
                                } else {
                                    d_gamePhase = Phase.NULL;
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
    //editcontinent, editcountry, editneighbour, savemap, showmap, editmap, loadmap, validatemap
        else if (d_gamePhase.equals(Phase.EDITMAP)) {

        }
    //STARTUP Phase
    //gameplayer, assigncountries, showmap
        else if (d_gamePhase.equals(Phase.STARTUP)) {

        }
    //REINFORCEMENT/DEPLOYMENT Phase
    //deploy, showmap
        else if (d_gamePhase.equals(Phase.ASSIGN_REINFORCEMENT)) {

        }

        return d_gamePhase;
    }
}
