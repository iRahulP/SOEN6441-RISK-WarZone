package controller;

import controller.GameEngine;
import controller.TournamentEngine;
import model.GameData;
import model.StartUp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests tournament related commands.
 */
public class TournamentEngineTest {

    /**
     * Represents the state of the game
     */
    GameData d_Game;

    /**
     * Represents gameEngine object
     */
    GameEngine d_Ge;

    /**
     * To help load the map.
     */
    StartUp d_Stup;

    /**
     * Refers to d_Te
     */
    TournamentEngine d_Te;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        d_Stup = new StartUp(d_Ge);
        d_Game = new GameData();
        d_Te = new TournamentEngine(d_Ge);
    }

    /**
     * Test if invalid tournament commands are detected or not.
     */
    @Test
    public void testTournamentCommand(){

        String message = d_Te.parse(null, "tournament -M -P player  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map ConquestCliff.map eurasien.map uk.map world.map -P player  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P human  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P benevolent benevolent  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P benevolent random cheater aggressive random  -G 3 -D 15");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P benevolent random  -G 3 -D 5");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P benevolent random  -G 3 -D 55");
        Assert.assertEquals("Comand has to be in form of 'tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns'", message);

        message = d_Te.parse(null, "tournament -M dummy.map ameroki.map -P benevolent random  -G 3 -D 15");
        Assert.assertEquals("success", message);
    }


}
