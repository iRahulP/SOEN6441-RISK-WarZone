package view;

import controller.Command;
import model.Phase;
import model.Player;

import java.io.File;
import java.util.Scanner;


/**
 * Responsible for playing the game.
 * Covers tasks ranging from 'map editing' to 'actual game play'.
 * Responsible for only interacting with the user and calling appropriate methods for further
 * actions.
 *
 * @author rahul
 *
 */
public class PlayRisk {

    public static void main(String[] args) {
        PlayRisk l_game = new PlayRisk();
        System.out.println("Welcome to Risk Game based on Warzone!");
        System.out.println("try, Selecting a map from the below mentioned sample maps or create a new one: ");
        l_game.sampleMaps();

        Scanner sc = new Scanner(System.in);
        String l_cmd = sc.nextLine();
        Phase l_gamePhase = Phase.NULL;
        Command cmd = new Command();
        l_gamePhase = cmd.parseCommand(null, l_cmd);
        while(l_gamePhase!= Phase.ASSIGN_REINFORCEMENT) {
            l_cmd = sc.nextLine();
            l_gamePhase = cmd.parseCommand(null, l_cmd);
        }

        int l_numberOfPlayers = cmd.d_players.size();
        int l_traversalCounter = 0;
        while(true) {
            while(l_traversalCounter<l_numberOfPlayers) {
                Player l_p = cmd.d_players.get(l_traversalCounter);
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
        File d_folder = new File("src/main/resources/maps/");
        File[] d_files = d_folder.listFiles();

        for(int i=0; i<d_files.length; i++) {
            if(d_files[i].isFile())
                System.out.print(d_files[i].getName()+" /t");
        }
        System.out.println();
    }
}
