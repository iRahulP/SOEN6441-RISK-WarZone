package view;

import java.io.File;

public class PlayRisk {

    public static void main(String[] args) {
        PlayRisk game = new PlayRisk();
        System.out.println("Welcome to Risk Game based on Warzone!");
        System.out.println("To continue, select a map from the below mentioned existing maps or create a new one.");
        game.sampleMaps();


    }


    private void sampleMaps() {
        File folder = new File("../resources/maps/");
        File[] files = folder.listFiles();

        for(int i=0; i<files.length; i++) {
            if(files[i].isFile())
                System.out.print(files[i].getName()+" ");
        }
        System.out.println();
    }
}
