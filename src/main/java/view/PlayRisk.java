package view;

import controller.GameEngine;

import controller.TournamentEngine;
import model.AssignReinforcement;
import model.InternalPhase;
import model.Player;

import java.io.File;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;


/**
 * Responsible for playing the game acts as GameEngine.
 * Covers tasks ranging from 'map editing' to 'actual game play'.
 * Responsible for only interacting with the user and calling appropriate methods for further
 * actions.
 *
 * @author Rahul
 *
 */
public class PlayRisk {
    /**
     * @param args Command line arguments
     */
    public static void main(String[] args) {
    	  PlayRisk l_game = new PlayRisk();
          Scanner sc = new Scanner(System.in);
          //initial command reader from cli
          String l_cmd;
          String message;
          int traversalCounter = 0;
          InternalPhase l_gamePhase = InternalPhase.NULL;
          GameEngine cmd = new GameEngine();
          //GameEngine lcmd ;
          PlayRisk game = new PlayRisk();
          TournamentEngine tEngine;

          boolean validCommand = false;
          boolean loadGame = false;
  		
          System.out.println("Welcome to Risk Game based on Warzone!");
          System.out.println("Enter 1 to play single-game mode or 2 to play tournament mode.");
          l_cmd = sc.nextLine();
        while (!validCommand) {
            if (l_cmd.equals("1")) {
                //single-game mode
                //select user-choice to load game or play new game
                while (!validCommand) {
                    System.out.println("Enter 1 to load a saved game or 2 to edit/load map for new game.");
                    l_cmd = sc.nextLine();

                    if (l_cmd.equals("1")) {

                        //loads game
                        validCommand = true;
                        loadGame = true;
                        System.out.println("To continue, select a game to load from the existing save games.");
                        game.printSavedGames();
                        do {
                            l_cmd = sc.nextLine();
                            l_gamePhase = cmd.parseCommand(null, l_cmd);
                            System.out.println("!11");
                        } while (cmd.parseCommand(null, l_cmd).equals("Loaded successfully"));

                        System.out.println("heeeellllo");
                        //set traversal counter by finding appropriate player
                        traversalCounter = 0;
                        System.out.println(cmd.getGame().getPlayers());
                        for (Player player1 : cmd.getGame().getPlayers()) {
                        	
                            traversalCounter++;
                            System.out.println(traversalCounter);
                            if (player1 == cmd.getGame().getActivePlayer()) {
                                break;
                            }
                       }
                        System.out.println("wasup1");
                      //set controller to turn controller to continue playing the loaded game
                        
                        cmd = new GameEngine(cmd.getGame());                        
                                                
                        
                    } else if (l_cmd.equals("2")) {


                        System.out.println("try, Selecting a map from the below mentioned sample maps or create a new one: ");
                        l_game.sampleMaps();
//
//        //initial command reader from cli
//        String l_cmd;
//        InternalPhase l_gamePhase = InternalPhase.NULL;
//        GameEngine cmd = new GameEngine();

                        //looping for commands until initial Phases where Player iteration not required!
                        while (l_gamePhase != InternalPhase.ISSUE_ORDERS) {
                            l_cmd = sc.nextLine();
                            l_gamePhase = cmd.parseCommand(null, l_cmd);
                        }

                        //Assign to each player the correct number of reinforcement armies according to the Warzone rules.
                        l_game.assignEachPlayerReinforcements(cmd);

                        //Loops through all Players in Round Robin fashion collecting orders.
                        int l_numberOfPlayers = cmd.d_Players.size();
                        int l_traversalCounter = 0;
                        while (true) {
                            while (l_traversalCounter < l_numberOfPlayers) {
                                Player l_p = cmd.d_Players.get(l_traversalCounter);
                                System.out.println("It's " + l_p.getPlayerName() + "'s turn");
                                cmd.setD_ActivePlayer(l_p);
                                System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmies() + " Army units currently!");
                                //listen orders from players - deploy | pass
                                l_gamePhase = InternalPhase.ISSUE_ORDERS;
                                cmd.setGamePhase(l_gamePhase);
                                while (l_gamePhase != InternalPhase.TURN) {
                                    //System.out.println(l_p.getD_isHuman());
                                    if (l_p.getD_isHuman()) {
                                        l_cmd = sc.nextLine();
                                        l_gamePhase = cmd.parseCommand(l_p, l_cmd);
                                    } else {
                                        l_gamePhase = cmd.parseCommand(l_p, "");
                                    }
                                }
                                //gets to next Player
                                l_traversalCounter++;
                            }
                            l_gamePhase = InternalPhase.ISSUE_ORDERS;
                            cmd.setGamePhase(l_gamePhase);
                            l_traversalCounter = 0;
                        }
                    }else {
                    	System.out.println("invalid command, Enter 1 to load game and enter 2 to load maps.");
                    }
                    
                    //Loops through all Players in Round Robin fashion collecting orders.
                    int l_numberOfPlayers = cmd.getGame().getPlayers().size();
                   System.out.println(l_numberOfPlayers);
                   System.out.println("aaaaaaaaaa");
                    
                    	System.out.println("bbbbbbbbbbb");
                        while (traversalCounter < l_numberOfPlayers) {
                        	System.out.println(traversalCounter);
                        	System.out.println("bbbbbbbbbbb");
                            Player l_p = cmd.d_Players.get(traversalCounter);
                            System.out.println("It's " + l_p.getPlayerName() + "'s turn");
                            System.out.println("Player " + l_p.getPlayerName() + " has " + l_p.getOwnedArmies() + " Army units currently!");
                            //listen orders from players - deploy | pass
                            l_gamePhase = InternalPhase.ISSUE_ORDERS;
                            cmd.setGamePhase(l_gamePhase);
                            while (l_gamePhase != InternalPhase.TURN) {
                                //System.out.println(l_p.getD_isHuman());
                                if (l_p.getD_isHuman()) {
                                    l_cmd = sc.nextLine();
                                    l_gamePhase = cmd.parseCommand(l_p, l_cmd);
                                } else {
                                    l_gamePhase = cmd.parseCommand(l_p, "");
                                }
                            }
                            //gets to next Player
                            traversalCounter++;
                        }
                        l_gamePhase = InternalPhase.ISSUE_ORDERS;
                        cmd.setGamePhase(l_gamePhase);
                        traversalCounter = 0;
                    }
                
            } else if (l_cmd.equals("2")) {
                //tournament mode
                tEngine = new TournamentEngine(cmd);
                do {
                    System.out.println("Enter a valid tournament command of the form 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'");
                    l_cmd = sc.nextLine();
                    message = tEngine.parse(null, l_cmd);
                } while (!message.equals("success"));
                validCommand = true;
            } else {
                System.out.println("Invalid Command, Enter 1 to play single-game mode or 2 to play tournament mode.");
                validCommand = true;
            }
        }
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

    /**
     * Shows names of existing map files from sample Resources.
     *
     */
    private void sampleMaps() {
        File d_Folder = new File("src/main/resources/maps/");
        File[] d_Files = d_Folder.listFiles();
        System.out.println("Consider files as sample.map and enter: loadmap sample.map | editmap sample.map");
        for(int i = 1; i< Objects.requireNonNull(d_Files).length; i++) {
            if(d_Files[i].isFile())
                System.out.print(d_Files[i].getName()+" | ");
        }
        System.out.println();
    }

    /**
     * Shows names of existing game files under Resources.
     */
    private void printSavedGames(){
        File d_Folder = new File("src/main/resources/game/");
        File[] d_Files = d_Folder.listFiles();

        for(int i = 0; i< d_Files.length; i++) {
            if(d_Files[i].isFile())
                System.out.print(d_Files[i].getName()+" | ");
        }
        System.out.println();
    }
}
