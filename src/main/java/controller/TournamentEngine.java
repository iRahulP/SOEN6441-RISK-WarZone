package controller;

import model.*;
import view.TournamentResultView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

/**
 * Manages Tournament
 */
public class TournamentEngine extends GameEngine{
    StartUp d_StartUp;

    /**
     * Creates a tournament engine object.
     * @param p_Ge is object of gameEngine
     */
    public TournamentEngine(GameEngine p_Ge){
        super();
        d_StartUp = new StartUp(p_Ge);
    }

    /**
     * Function responsible for handling command for tournament
     * @param p_player Player playing the move
     * @param p_newCommand Command to be interpreted
     * @return success if command is valid else appropriate message which indicates reason of failure
     */
    public String parse(Player p_player, String p_newCommand){
        String[] l_data = p_newCommand.split("\\s+");
        String l_commandName = l_data[0];
        int l_noOfMaps;
        int l_noOfGames;
        int l_noOfTurns;
        ArrayList<String> l_maps = new ArrayList<String>();
        ArrayList<String> l_strategies = new ArrayList<String>();

        if (l_commandName.equals("tournament")) {
            try {
                if (l_data[1].equals("-M")) {
                    int i = 2;
                    while (!l_data[i].equals("-P")) {
                        if (i < l_data.length) {
                            if (isMapNameValid(l_data[i])) {
                                l_maps.add(l_data[i]);
                            } else {
                                printFailureMessage();
                                return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                            }
                        } else {
                            printFailureMessage();
                            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                        }
                        i++;
                    }

                    if(l_maps.size()>=1 && l_maps.size()<=5 && allMapExists(l_maps)) {

                        if (l_data[i].equals("-P")) {

                            int l_indexNew = i + 1;

                            while (!l_data[l_indexNew].equals("-G")) {
                                if (l_indexNew < l_data.length) {
                                    if (isPlayerStrategyValid(l_data[l_indexNew])) {
                                        l_strategies.add(l_data[l_indexNew]);
                                    } else {
                                        printFailureMessage();
                                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                    }

                                } else {
                                    printFailureMessage();
                                    return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                }
                                l_indexNew++;
                            }

                            if(l_strategies.size()>=2 && l_strategies.size()<=5 && isPlayerStrategyDistinct(l_strategies)) {
                                //System.out.println(strategies);

                                if (l_data[l_indexNew].equals("-G")) {
                                    if (l_indexNew + 1 < l_data.length) {
                                        if (l_data[l_indexNew + 1].matches("[1-5]")) {
                                            l_noOfGames = Integer.parseInt(l_data[l_indexNew + 1]);
                                            //System.out.println("Number of Games:" + noOfGames);
                                        } else {
                                            printFailureMessage();
                                            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                        }
                                    } else {
                                        printFailureMessage();
                                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                    }

                                    int l_newIndex = l_indexNew + 2;

                                    if (l_data[l_newIndex].equals("-D")) {


                                        if (l_newIndex + 1 < l_data.length) {

                                            int l_n = Integer.parseInt(l_data[l_newIndex + 1]);

                                            if (l_n >= 10 && l_n <= 50) {
                                                l_noOfTurns = Integer.parseInt(l_data[l_newIndex + 1]);
                                                playTournament(l_maps, l_strategies, l_noOfGames, l_noOfTurns);
                                                return "success";
                                            } else {
                                                printFailureMessage();
                                                return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                            }
                                        } else {
                                            printFailureMessage();
                                            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                        }
                                    } else {
                                        printFailureMessage();
                                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                    }
                                } else {
                                    printFailureMessage();
                                    return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                }
                            }else{
                                printFailureMessage();
                                return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                            }
                        } else {
                            printFailureMessage();
                            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                        }
                    }else {
                        printFailureMessage();
                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                    }
                } else {
                    printFailureMessage();
                    return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                }
            }catch (ArrayIndexOutOfBoundsException e){
                String message = "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                return message;
            }
        } else {
            printFailureMessage();
            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
        }

    }

    /**
     * Conducts the tournament as per the mentioned arguments.
     * @param p_mapFiles  List of map files to play on
     * @param p_strategies    List of player strategies
     * @param p_numberOfGames Number of games to play
     * @param p_numberOfTurns Number of turns to play
     */
    public void playTournament(ArrayList<String> p_mapFiles, ArrayList<String> p_strategies, int p_numberOfGames, int p_numberOfTurns){
        int l_numberOfPlayers = p_strategies.size();
        int l_traversalCounter = 0;
        int l_gameNumber = 0;
        HashMap<Integer, String> l_winner = new HashMap<Integer, String>();
        //Start playing on each map
        for(String l_mapName : p_mapFiles) {
            System.out.println("Playing on :"+l_mapName);
            //Start playing each game
            for (int i = 1; i <= p_numberOfGames; i++) {
                l_gameNumber++;
                System.out.println("Playing Game :"+l_gameNumber);
                d_Ge = new GameEngine();
                d_Game = new GameData();
                d_RunG = new RunGameEngine();
                d_StartUp = new StartUp(d_Ge);
                //load the map
                d_Map = d_RunG.loadMap(l_mapName);
                //Create player objects
                for (String l_strategy : p_strategies) {
                    d_StartUp.addPlayer(d_Players, l_strategy);
                }
                //Setting strategies as same as Player Names
                for (Player l_p : d_Players) {
                    switch (l_p.getPlayerName().toLowerCase()) {
                        case "aggressive":
                            l_p.setStrategy(new AggresivePlayer(l_p, d_Map));
                            l_p.setD_isHuman(false);
                            break;
                        case "benevolent":
                            l_p.setStrategy(new BenevolentPlayer(l_p, d_Map));
                            l_p.setD_isHuman(false);
                            break;
                        case "random":
                            l_p.setStrategy(new RandomPlayer(l_p, d_Map));
                            l_p.setD_isHuman(false);
                            break;
                        case "cheater":
                            l_p.setStrategy(new CheaterPlayer(l_p, d_Map));
                            l_p.setD_isHuman(false);
                            break;
                        default:
                            System.out.println("Invalid Player Strategy");
                            break;
                    }
                }
                //System.out.println(d_Players.size());
                d_StartUp = new StartUp(d_Ge);
                d_StartUp.assignCountries(d_Map, d_Players);
                //AssignCountries and Reinforcements
                assignEachPlayerReinforcements(d_Players);
                //tournament -M demo.map -P cheater random -G 1 -D 10


                for (int j = 1; j <= p_numberOfTurns; j++) {
                    int l_counter = 0;
                    System.out.println("It's " + j + "'th Turn");

                    //traverses through all players to check if armies left in pool
                    Iterator<Player> l_itr = d_Players.listIterator();
                    while (l_itr.hasNext()) {
                        Player l_p = l_itr.next();
                        if (l_p.getOwnedArmies() > 0) {
                            l_counter = l_counter + l_p.getOwnedArmies();
                        }
                    }

                    System.out.println("Total Armies left with all Players in Pool: " + l_counter);
                    //Case when pool has at least 1 army left

                    //Issued Orders
                    //Gets Orders until all pool is consumed for turn
                    while (l_counter > 0) {
                        //get Orders
                        for (Player p : d_Players) {
                            p.issueOrder();
                        }
                        l_counter = 0;
                        for (Player p : d_Players) {
                            if (p.getOwnedArmies() > 0) {
                                l_counter = l_counter + p.getOwnedArmies();
                            }
                        }
                    }

                    //Execute current Pool of Orders
                    int l_count = 0;
                    for (Player l_p : d_Players) {
                        //Get Orders Queue and check if empty
                        Queue<Order> l_temp = l_p.getD_orderList();
                        l_count = l_count + l_temp.size();
                    }

                    if (l_count == 0) {
                        //Case when no Order
                        System.out.println("Orders already executed!");
                    } else {
                        //Execute Orders and check if Player won
                        System.out.println("Total Orders  :" + l_count);
                        //Execute Current Orders
                        while (l_count != 0) {
                            for (Player l_p : d_Players) {
                                Queue<Order> l_temp = l_p.getD_orderList();
                                if (l_temp.size() > 0) {
                                    Order l_toRemove = l_p.next_order();
                                    l_toRemove.execute();
                                }
                            }
                            l_count--;
                        }

                        //Flush Owned Cards
                        for (Player l_p : d_Players) {
                            l_p.flushNegotiators();
                        }

                        //check if any player needs to be removed as of losing all territories
                        for (Player l_p : d_Players) {
                            if (l_p.getOwnedCountries().size() == 0) {
                                System.out.println(l_p.getPlayerName() + " has lost all its territories and is no longer part of the game");
                                d_LogEntry.setMessage(l_p.getPlayerName() + " has lost all its territories and is no longer part of the game");
                                d_Players.remove(l_p);
                            }
                        }

                        assignEachPlayerReinforcements(d_Players);

                        //Check if any Player Won
                        for (Player l_p : d_Players) {
                            if (l_p.getOwnedCountries().size() == d_Map.getCountries().size()) {
                                System.out.println(l_p.getPlayerName() + " has Won the Game!");
                                d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                                l_winner.put(l_gameNumber, l_p.getPlayerName());
                                break;
                            }
                        }
                    }
                }
                //Check if any Player Won
                for (Player l_p : d_Players) {
                    if (l_p.getOwnedCountries().size() == d_Map.getCountries().size()) {
                        System.out.println(l_p.getPlayerName() + " has Won the Game!");
                        d_LogEntry.setMessage(l_p.getPlayerName() + " has Won the Game!");
                        l_winner.put(l_gameNumber, l_p.getPlayerName());
                        break;
                    }
                }
                //Case when all Turns ended and no Winner was returned
                //Need to ref some Boolean here or will always draw
                l_winner.put(l_gameNumber, "Draw");
            }
        }
        //return winner;
        TournamentResultView tournamentResultView = new TournamentResultView();
        tournamentResultView.displayTournamentResult(l_winner, p_mapFiles);
    }

    /**
     * Ensures that all maps exist
     * @param p_list list of name of maps
     * @return true if valid else false
     */
    public boolean allMapExists(ArrayList<String> p_list){
        int l_counter=0;
        for(String s:p_list){
            if(isMapExists(s)){
                l_counter++;
            }
        }
        if(l_counter == p_list.size()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if the map with argument name exists or not.
     * If it exists, it also checks if its valid or not.
     * @param p_mapName Name of the map file to be checked.
     * @return true if file exists and is a valid file, otherwise false.
     */
    public boolean isMapExists(String p_mapName) {
        String l_filePath = "src/main/resources/maps/" + p_mapName;
        File f = new File(l_filePath);
        if (f.exists()) {
            RunGameEngine rGE = new RunGameEngine();
            d_Map = rGE.loadMap(p_mapName);
            return true;
        } else {
            return false;
        }
    }

    /**
     *Method prints failure message for tournament command
     */
    public void printFailureMessage(){
        String message = "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
        System.out.println(message);
        d_LogEntry.setMessage("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'");
    }

    /**
     * Ensures map name is valid.
     *
     * @param p_s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String p_s) {
        return p_s != null && p_s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures player strategy is valid
     * @param p_s strategy of the player
     * @return true if valid else false
     */
    public boolean isPlayerStrategyValid(String p_s){

        String[] l_array = new String[]{"aggressive", "benevolent", "random", "cheater"};
        for(int i=0; i<4; i++){
            if(p_s.equals(l_array[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure that all strategies of player are distinct
     * @param p_list list of player's strategy
     * @return true if valid else false
     */
    public boolean isPlayerStrategyDistinct(ArrayList<String> p_list){

        for(int i=0;i<p_list.size();i++){
            for(int j = i+1; j<p_list.size(); j++){
                if(p_list.get(i).equals(p_list.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * assigns default reinforcements to each player based on Countries owned.
     * @param p_Players  GameEngine ref from main to get track of players
     */
    public void assignEachPlayerReinforcements(ArrayList<Player> p_Players){
        for(Player l_p: p_Players) {
            AssignReinforcement.assignReinforcementArmies(l_p);
        }
    }
}
