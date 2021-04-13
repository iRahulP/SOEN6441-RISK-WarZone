package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;

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
}
