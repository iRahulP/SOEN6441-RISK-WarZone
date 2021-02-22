package view;

import controller.GameEngine;
import model.AssignReinforcement;
import model.Phase;
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
 * @author rahul
 *
 */
public class PlayRisk {
    /**
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PlayRisk l_game = new PlayRisk();
        System.out.println("Welcome to Risk Game based on Warzone!");
        System.out.println("try, Selecting a map from the below mentioned sample maps or create a new one: ");
        l_game.sampleMaps();

        //initial command reader from cli
        Scanner sc = new Scanner(System.in);
        String l_cmd = sc.nextLine();
        //initial Phase set to NULL
        Phase l_gamePhase = Phase.NULL;
        GameEngine cmd = new GameEngine();
        l_gamePhase = cmd.parseCommand(null, l_cmd);
        //looping for commands until initial Phases where Player iteration not required!
        while(l_gamePhase!= Phase.ISSUE_ORDERS) {
            l_cmd = sc.nextLine();
            l_gamePhase = cmd.parseCommand(null, l_cmd);
        }

        l_game.assignEachPlayerReinforcements(cmd);

        //Loops through list of Players
        int l_numberOfPlayers = cmd.d_Players.size();
        int l_traversalCounter = 0;
        while(true) {
            while(l_traversalCounter<l_numberOfPlayers) {
                Player l_p = cmd.d_Players.get(l_traversalCounter);
                //Assign to each player the correct number of reinforcement armies according to the Warzone rules.
                System.out.println("It's "+ l_p.getPlayerName() + "'s turn");
                //listen orders from players - deploy - pass
                l_gamePhase = Phase.ISSUE_ORDERS;
                cmd.setGamePhase(l_gamePhase);
                while(l_gamePhase!=Phase.TURNEND) {
                    l_cmd = sc.nextLine();
                    l_gamePhase = cmd.parseCommand(l_p, l_cmd);
                }
                l_traversalCounter++;
            }
            l_gamePhase = Phase.ISSUE_ORDERS;
            cmd.setGamePhase(l_gamePhase);
            l_traversalCounter = 0;
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
}
