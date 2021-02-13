package view;

import controller.Command;
import controller.GameEngine;
import model.Phase;
import model.Player;

import java.io.File;
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

        Scanner sc = new Scanner(System.in);
        String l_cmd = sc.nextLine();
        Phase l_gamePhase = Phase.NULL;
        GameEngine cmd = new GameEngine();
        l_gamePhase = cmd.parseCommand(null, l_cmd);
        while(l_gamePhase!= Phase.ASSIGN_REINFORCEMENT) {
            l_cmd = sc.nextLine();
            l_gamePhase = cmd.parseCommand(null, l_cmd);
        }

        int l_numberOfPlayers = cmd.d_Players.size();
        int l_traversalCounter = 0;
        while(true) {
            while(l_traversalCounter<l_numberOfPlayers) {
                Player l_p = cmd.d_Players.get(l_traversalCounter);
                AssignReinforcement.assignReinforcementArmies(l_p);
                System.out.println("It's "+ l_p.getPlayerName() + "'s turn");

                while(l_gamePhase!=Phase.TURNEND) {
                    if(l_gamePhase==Phase.ASSIGN_REINFORCEMENT) {
                        System.out.println("Reinforcement Armies: " + l_p.getOwnedArmies());
                    }
                    l_cmd = sc.nextLine();
                    l_gamePhase = cmd.parseCommand(l_p, l_cmd);
                }
                l_gamePhase = Phase.ASSIGN_REINFORCEMENT;
                cmd.setGamePhase(l_gamePhase);
                l_traversalCounter++;
            }
            l_traversalCounter = 0;
        }

    }

    /**
     * Shows names of existing map files from sample Resources.
     *
     */
    private void sampleMaps() {
        File d_Folder = new File("src/main/resources/maps/");
        File[] d_Files = d_Folder.listFiles();

        for(int i=0; i<d_Files.length; i++) {
            if(d_Files[i].isFile())
                System.out.print(d_Files[i].getName()+" /t");
        }
        System.out.println();
    }
}
