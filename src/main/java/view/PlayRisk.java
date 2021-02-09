package view;

import controller.Commands;
import model.Phase;
import model.Player;

import java.io.File;
import java.util.Scanner;

public class PlayRisk {

    public static void main(String[] args) {
        PlayRisk game = new PlayRisk();
        System.out.println("Welcome to Risk Game based on Warzone!");
        System.out.println("try, Selecting a map from the below mentioned sample maps or create a new one: ");
        game.sampleMaps();

        Scanner sc = new Scanner(System.in);
        String l_cmd = sc.nextLine();
        Phase gamePhase = Phase.NULL;
        Commands cmd = new Commands();
        gamePhase = cmd.parseCommand(null, l_cmd);
        while(gamePhase!= Phase.ASSIGN_REINFORCEMENT) {
            l_cmd = sc.nextLine();
            gamePhase = cmd.parseCommand(null, l_cmd);
        }

        int l_numberOfPlayers = cmd.players.size();
        int l_traversalCounter = 0;
        while(true) {
            while(l_traversalCounter<l_numberOfPlayers) {
                Player p = cmd.players.get(l_traversalCounter);
                AssignReinforcement.assignReinforcementArmies(p);
                System.out.println("It's "+ p.getPlayerName() + "'s turn");

                while(gamePhase!=Phase.TURNEND) {
                    if(gamePhase==Phase.ASSIGN_REINFORCEMENT) {
                        System.out.println("Reinforcement Armies: " + p.getOwnedArmies());
                    }
                    l_cmd = sc.nextLine();
                    gamePhase = cmd.parseCommand(p, l_cmd);
                }
                gamePhase = Phase.ASSIGN_REINFORCEMENT;
                cmd.setGamePhase(gamePhase);
                l_traversalCounter++;
            }
            l_traversalCounter = 0;
        }

    }

    /**
     * Prints names of existing map files from sample Resources.
     */
    private void sampleMaps() {
        File l_folder = new File("src/main/resources/maps/");
        File[] l_files = l_folder.listFiles();

        for(int i=0; i<l_files.length; i++) {
            if(l_files[i].isFile())
                System.out.print(l_files[i].getName()+" /t");
        }
        System.out.println();
    }
}
