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
     */
    public TournamentEngine(GameEngine p_Ge){
        super();
        d_Game = new GameData();
        d_RunG = new RunGameEngine();
        d_StartUp = new StartUp(p_Ge);
    }

    /**
     * Function responsible for handling command for tournament
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return success if command is valid else appropriate message which indicates reason of failure
     */
    public String parse(Player player, String newCommand){
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];
        int noOfMaps;
        int noOfGames;
        int noOfTurns;
        ArrayList<String> maps = new ArrayList<String>();
        ArrayList<String> strategies = new ArrayList<String>();

        if (commandName.equals("tournament")) {
            try {
                if (data[1].equals("-M")) {
                    int i = 2;
                    while (!data[i].equals("-P")) {
                        if (i < data.length) {
                            if (isMapNameValid(data[i])) {
                                maps.add(data[i]);
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

                    if(maps.size()>=1 && maps.size()<=5 && allMapExists(maps)) {

                        if (data[i].equals("-P")) {

                            int indexNew = i + 1;

                            while (!data[indexNew].equals("-G")) {
                                if (indexNew < data.length) {
                                    if (isPlayerStrategyValid(data[indexNew])) {
                                        strategies.add(data[indexNew]);
                                    } else {
                                        printFailureMessage();
                                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                    }

                                } else {
                                    printFailureMessage();
                                    return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                }
                                indexNew++;
                            }

                            if(strategies.size()>=2 && strategies.size()<=5 && isPlayerStrategyDistinct(strategies)) {
                                //System.out.println(strategies);

                                if (data[indexNew].equals("-G")) {
                                    if (indexNew + 1 < data.length) {
                                        if (data[indexNew + 1].matches("[1-5]")) {
                                            noOfGames = Integer.parseInt(data[indexNew + 1]);
                                            //System.out.println("Number of Games:" + noOfGames);
                                        } else {
                                            printFailureMessage();
                                            return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                        }
                                    } else {
                                        printFailureMessage();
                                        return "Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'";
                                    }

                                    int newIndex = indexNew + 2;

                                    if (data[newIndex].equals("-D")) {


                                        if (newIndex + 1 < data.length) {

                                            int n = Integer.parseInt(data[newIndex + 1]);

                                            if (n >= 10 && n <= 50) {
                                                noOfTurns = Integer.parseInt(data[newIndex + 1]);
                                                //tournament -M dummy.map ameroki.map -P benevolent random  -G 3 -D 150
                                                System.out.println("Can Play");
                                                //playTournament(maps, strategies, noOfGames, noOfTurns);
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
     * @param mapFiles  List of map files to play on
     * @param strategies    List of player strategies
     * @param numberOfGames Number of games to play
     * @param numberOfTurns Number of turns to play
     */
    public void playTournament(ArrayList<String> mapFiles, ArrayList<String> strategies, int numberOfGames, int numberOfTurns){
        int numberOfPlayers = strategies.size();
        int traversalCounter = 0;
        int gameNumber = 0;
        HashMap<Integer, String> winner = new HashMap<Integer, String>();

        //Start playing on each map
        for(String mapName : mapFiles) {
            //Start playing each game
            for (int i = 1; i <= numberOfGames; i++) {
                gameNumber++;

                //load the map
                d_Ge = new GameEngine();
                d_Map = d_RunG.loadMap(mapName);

                //Create player objects
                for (String strategy : strategies) {
                    d_StartUp.addPlayer(d_Players, strategy);
                }

                //Setting strategies as same as Player Names
                for (Player l_p : d_Players) {
                    switch (l_p.getPlayerName().toLowerCase()) {
                        case "aggressive":
                            l_p.setStrategy(new AggresivePlayer(l_p, d_Map));
                            l_p.setD_isHuman(false);
                            break;
                        case "benevolent":
                            l_p.setStrategy(new BenevolentPlayer(l_p,d_Map));
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

                //AssignCountries and Reinforcements
                d_StartUp.assignCountries(d_Map, d_Players);
                assignEachPlayerReinforcements(d_Ge);

                //For D number of Turns iterate Players to get Orders
                for(int j=1; j<=numberOfTurns; j++){

                    int l_counter = 0;
                    //traverses through all players to check if armies left in pool
                    Iterator<Player> l_itr = d_Players.listIterator();
                    while(l_itr.hasNext()) {
                        Player l_p = l_itr.next();
                        if (l_p.getOwnedArmies() > 0) {
                            l_counter = l_counter + l_p.getOwnedArmies();
                        }
                    }

                    //Case when pool has at least 1 army left
                    //Gets Orders until all pool is consumed for turn
                    while (l_counter > 0){
                        //get Orders
                        for (Player p : d_Players) {
                            p.issueOrder();
                        }
                        //Check for Pool
                        Iterator<Player> itr = d_Players.listIterator();
                        l_counter = 0;
                        while(itr.hasNext()) {
                            Player l_p = itr.next();
                            if (l_p.getOwnedArmies() > 0) {
                                l_counter = l_counter + l_p.getOwnedArmies();
                            }
                        }
                        //If pool is consumed
                        //Execute current Orders
                        if(l_counter == 0){
                            int l_count = 0;
                            for (Player l_p : d_Players) {
                                Queue<Order> l_temp = l_p.getD_orderList();
                                l_count = l_count +l_temp.size();
                            }

                            if(l_count == 0){
                                System.out.println("Orders already executed!");
                            }
                            else{
                                System.out.println("Total Orders  :" + l_count);
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
                                for(Player l_p : d_Players) {
                                    l_p.flushNegotiators();
                                }

                                //Check if any Player Won
                                for (Player l_p : d_Players){
                                    if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                                        System.out.println(l_p.getPlayerName()+" has Won the Game!");
                                        d_LogEntry.setMessage(l_p.getPlayerName()+" has Won the Game!");
                                        winner.put(gameNumber, l_p.getPlayerName());
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
                        }
                    }
                }
            }
        }
    }
        //return winner;
        TournamentResultView tournamentResultView = new TournamentResultView();
        tournamentResultView.displayTournamentResult(winner, mapFiles);
    }

    /**
     * Ensures that all maps exist
     * @param list list of name of maps
     * @return true if valid else false
     */
    public boolean allMapExists(ArrayList<String> list){
        int counter=0;
        for(String s:list){
            if(isMapExists(s)){
                counter++;
            }
        }
        if(counter == list.size()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if the map with argument name exists or not.
     * If it exists, it also checks if its valid or not.
     * @param mapName Name of the map file to be checked.
     * @return true if file exists and is a valid file, otherwise false.
     */
    public boolean isMapExists(String mapName) {
        String filePath = "src/main/resources/maps/" + mapName;
        File f = new File(filePath);
        if (f.exists()) {
            RunGameEngine rGE = new RunGameEngine();
            d_Map = rGE.loadMap(mapName);
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
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isMapNameValid(String s) {
        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Ensures player strategy is valid
     * @param s strategy of the player
     * @return true if valid else false
     */
    public boolean isPlayerStrategyValid(String s){

        String[] array = new String[]{"aggressive", "benevolent", "random", "cheater"};
        for(int i=0; i<4; i++){
            if(s.equals(array[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Ensure that all strategies of player are distinct
     * @param list list of player's strategy
     * @return true if valid else false
     */
    public boolean isPlayerStrategyDistinct(ArrayList<String> list){

        for(int i=0;i<list.size();i++){
            for(int j = i+1; j<list.size(); j++){
                if(list.get(i).equals(list.get(j))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * assigns default reinforcements to each player based on Countries owned.
     * @param p_cmd GameEngine ref from main to get track of players
     */
    public void assignEachPlayerReinforcements(GameEngine p_cmd){
        Iterator<Player> itr = p_cmd.d_Players.listIterator();
        while(itr.hasNext()) {
            Player p = itr.next();
            AssignReinforcement.assignReinforcementArmies(p);
        }
    }
}
