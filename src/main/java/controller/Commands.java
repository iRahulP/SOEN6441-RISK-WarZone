package controller;

import model.GameMap;
import model.Phase;
import java.util.ArrayList;

/**
 * Class responsible to interpret user commands, call apt methods, and update game phases based on method responses.
 *
 */
public class Commands {

    public static boolean l_allArmiesPlaced = false;

    public GameMap map;
    public RunCommand runCmd;
    public StartUp startUp;
    public AssignReinforcement arfc;
    Phase gamePhase;
    public ArrayList<Player> players;

    public Commands() {
        map = new GameMap();
        runCmd = new RunCommand();
        startUp = new StartUp();
        rfc = new Reinforcement();
        players = new ArrayList<Player>();
        gamePhase = Phase.NULL;
    }


    /**
     * Ensures map name is valid.
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {
        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures string matches the defined criteria.
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z-_]*$");
    }

    /**
     * Setter method to set game phase.
     * @param gamePhase changed phase value
     */
    public void setGamePhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Function responsible to parsing user commands, calling apt methods, and updating the game phase.
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return next game phase
     */
    public Phase parseCommand(Player player, String newCommand) {

        int controlValue = 0;
        int numberOfArmies = 0;
        int armiesToFortify = 0;

        String mapName = null;
        String continentName = null;
        String countryName = null;
        String neighborCountryName = null;
        String playerName = null;
        String fromCountry = null;
        String toCountry = null;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        if (gamePhase.equals(Phase.NULL)) {
            switch (commandName) {
                case "editmap":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                map = runCmd.editMap(mapName);
                                System.out.println("You can now edit " + mapName);
                                gamePhase = Phase.EDITMAP;
                            } else {
                                System.out.println("Invalid map name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editmap mapname.map");
                    }
                    break;

                case "loadmap":
                    try {
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                map = runCmd.loadMap(mapName);
                                if (map != null) {
                                    if (!map.getValid()) {
                                        System.out.println("Map is not valid for game play");
                                        gamePhase = Phase.NULL;
                                    } else {
                                        System.out.println("Map is valid. Add players now.");
                                        gamePhase = Phase.STARTUP;
                                    }
                                } else {
                                    gamePhase = Phase.NULL;
                                }
                            } else {
                                System.out.println("Map name not valid.");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form loadmap mapname.map");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form loadmap mapname.map");
                    }
                    break;
                default:
                    System.out.println("Load or edit map first using commands: loadmap mapname.map or editmap.mapname.map");
                    break;
            }
        } else if (gamePhase.equals(Phase.EDITMAP)) {
            switch (commandName) {
                case "editcontinent":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1])) {
                                    continentName = data[i + 1];
                                } else {
                                    System.out.println("Invalid continent name");
                                }
                                controlValue = Integer.parseInt(data[i + 2]);

                                boolean check = runCmd.addContinent(map, continentName, controlValue);
                                if (check) {
                                    System.out.println(continentName + " continent added to the map");
                                    gamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Continent already exists - Please add valid Continent Name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    continentName = data[i + 1];
                                } else
                                    System.out.println("Invalid continent name");

                                boolean check = runCmd.removeContinent(map, continentName);
                                if (check) {
                                    System.out.println("Continent removed from the map");
                                    gamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Continent does not exist - Please enter valid continent name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editcontinent -add continentName controlValue -remove continentName");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form editcontinent -add continentName controlValue -remove continentName");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form editcontinent -add continentName controlValue -remove continentName");
                    }
                    break;

                case "editcountry":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    continentName = data[i + 2];
                                } else {
                                    System.out.println("Invalid country name");
                                }
                                boolean check = runCmd.addCountry(map, countryName, continentName);
                                if (check) {
                                    System.out.println("Country added to the map");
                                    gamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country already exists - Please add valid Continent Name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1])) {
                                    countryName = data[i + 1];
                                } else {
                                    System.out.println("Invalid country name");
                                }
                                boolean check = runCmd.removeCountry(map, countryName);
                                if (check) {
                                    System.out.println("Country removed from the map");
                                    gamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country doed not exist - Please enter valid country name");
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editcountry -add countryName continentName -remove countryName");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form editcountry -add countryName continentName -remove countryName");
                    }
                    break;

                case "editneighbor":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                } else {
                                    System.out.println("Invalid country name");
                                }

                                boolean check = runCmd.addNeighbor(map, countryName, neighborCountryName);
                                if (check) {
                                    System.out.println("Neighbor added to the map");
                                    gamePhase = Phase.EDITMAP;
                                } else {
                                    System.out.println("Country does not exist - Please enter valid neighbor or country name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (this.isAlpha(data[i + 1]) || this.isAlpha(data[i + 2])) {
                                    countryName = data[i + 1];
                                    neighborCountryName = data[i + 2];
                                } else {
                                    System.out.println("Invalid country name");
                                }

                                boolean check = runCmd.removeNeighbor(map, countryName, neighborCountryName);
                                if (check) {
                                    System.out.println("Neighbor removed from the map");
                                    gamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Country does not exist - Please enter valid neighbor or country name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form editneighbor -add countryName neighborCountryName -remove countryName neighborCountryName");
                    }
                    break;

                case "savemap":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                boolean check = runCmd.saveMap(map, mapName);
                                if (check) {
                                    System.out.println("Map file saved successfully");
                                    gamePhase = Phase.EDITMAP;
                                } else
                                    System.out.println("Error in map saving - invalid map");
                            } else
                                System.out.println("Map name not valid.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form savemap filename");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form savemap filename");
                    }
                    break;

                case "showmap":
                    runCmd.showMap(map);
                    gamePhase = Phase.EDITMAP;
                    break;

                case "editmap":
                    try {
                        if (data[1] != null) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                map = runCmd.editMap(mapName);
                                System.out.println("You can now edit " + mapName);
                                gamePhase = Phase.EDITMAP;
                            } else
                                System.out.println("Map name not valid.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form editmap mapname.map");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form editmap mapname.map");
                    }
                    break;

                case "loadmap":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isMapNameValid(data[1])) {
                                mapName = data[1];
                                map = runCmd.loadMap(mapName);
                                if (map != null) {
                                    if (!map.getValid()) {
                                        System.out.println("Map is not valid for game play");
                                        gamePhase = Phase.NULL;
                                    } else {
                                        System.out.println("Map is valid. Add players now.");
                                        gamePhase = Phase.STARTUP;
                                    }
                                } else {
                                    gamePhase = Phase.NULL;
                                }
                            } else
                                System.out.println("Map name not valid.");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form loadmap mapname.map");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form loadmap mapname.map");
                    }
                    break;

                case "validatemap":
                    if (runCmd.validateMap(map)) {
                        System.out.println("Valid map");
                    } else {
                        System.out.println("Invalid map");
                    }
                    break;

                default:
                    System.out.println("Invalid command - either use edit commands(editcontinent/editcountry/editneighbor) or savemap/validatemap/editmap/loadmap/showmap command");
                    break;

            }
        } else if (gamePhase.equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = startUp.addPlayer(players, playerName);
                                    if (check) {
                                        System.out.println("Player successfully added");
                                    } else {
                                        System.out.println("Can not add any more player. Maximum 6 players can play.");
                                    }
                                    gamePhase = Phase.STARTUP;
                                } else {
                                    System.out.println("Invalid player name");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = startUp.removePlayer(players, playerName);
                                    if (check)
                                        System.out.println("Player successfully removed");
                                    else
                                        System.out.println("Player does not exist");
                                    gamePhase = Phase.STARTUP;
                                } else
                                    System.out.println("Invalid player name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form gameplayer -add playername -remove playername");
                    }
                    break;

                case "populatecountries":
                    boolean check = startUp.populateCountries(map, players);
                    if (check) {
                        System.out.println("Countries allocated amongst the players");
                    }
                    gamePhase = Phase.ARMYALLOCATION;
                    startUp.armyDistribution(players, this, gamePhase);
                    gamePhase = Phase.REINFORCEMENT;
                    break;

                case "showmap":
                    startUp.showMap(players, map);
                    break;

                default:
                    System.out.println("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.");
                    break;
            }
        } else if (gamePhase.equals(Phase.ARMYALLOCATION)) {
            switch (commandName) {
                case "placearmy":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isAlpha(data[1])) {
                                countryName = data[1];
                                startUp.placeArmy(player, countryName);
                                boolean checkstatus = startUp.isAllArmyPlaced(players);
                                if (checkstatus)
                                    gamePhase = Phase.REINFORCEMENT;
                                else
                                    gamePhase = Phase.ARMYALLOCATION;
                            } else
                                System.out.println("Invalid country name");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form placearmy countryname");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form placearmy countryname");
                    }
                    break;

                case "placeall":
                    if (startUp.placeAll(players)) {
                        System.out.println("Armies placed successfully");
                    }
                    gamePhase = Phase.REINFORCEMENT;
                    break;

                case "showmap":
                    startUp.showMap(players, map);
                    break;

                default:
                    System.out.println("Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.");
                    break;
            }
        } else if (gamePhase.equals(Phase.REINFORCEMENT)) {
            switch (commandName) {
                case "reinforce":
                    try {
                        if (!(data[1] == null) || !(data[2] == null)) {
                            if (this.isAlpha(data[1]) || data[2].matches("[0-9]+")) {
                                countryName = data[1];
                                numberOfArmies = Integer.parseInt(data[2]);
                                boolean check = rfc.reinforce(player, countryName, numberOfArmies);
                                if (check) {
                                    if (player.getOwnedArmies() == 0) {
                                        System.out.println("Reinforcement phase successfully ended. Begin fortification now.");
                                        gamePhase = Phase.FORTIFICATION;
                                    }
                                }
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form reinforce countryName num");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form reinforce countryName num");
                    }
                    break;

                case "showmap":
                    startUp.showMap(players, map);
                    break;

                default:
                    System.out.println("Invalid command - either use reinforce or showmap commands in reinforcement phase.");
                    break;
            }
        } else if (gamePhase.equals(Phase.FORTIFICATION)) {
            switch (commandName) {
                case "fortify":
                    try {
                        if (data[1].equals("none")) {
                            System.out.println("Successfull fortification");
                            gamePhase = Phase.TURNEND;
                        } else if (!(data[1] == null) && !(data[2] == null) && !(data[3] == null)) {
                            if (this.isAlpha(data[1]) || this.isAlpha(data[2]) || data[3].matches("[0-9]+")) {
                                fromCountry = data[1];
                                toCountry = data[2];
                                armiesToFortify = Integer.parseInt(data[3]);

                                boolean check = ftf.fortify(player, fromCountry, toCountry, armiesToFortify);
                                if (check) {
                                    System.out.println("Successfull fortification");
                                    gamePhase = Phase.TURNEND;
                                }
                            } else
                                System.out.println("Invalid command - invalid characters in command");
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid command - it should be of the form fortify fromCountry toCountry num or foritfy none");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command - it should be of the form fortify fromCountry toCountry num or foritfy none");
                    } catch (Exception e) {
                        System.out.println("Invalid command - it should be of the form fortify fromCountry toCountry num or foritfy none");
                    }
                    break;

                case "showmap":
                    startUp.showMap(players, map);
                    break;

                default:
                    System.out.println("Invalid command - either use fortify/showmap command.");
                    break;
            }
        }
        return gamePhase;

    }
}
