package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;




/**
 * Tests if a valid game loading takes place or not.
 */
public class TestLoadGame {
	 /**
     * Represents the state of the game
     */
    GameData d_Game;

    /**
     * To help load the map.
     */
    RunGameEngine d_RunG;

    /**
     * Refers to controller
     */
    GameEngine d_Ge;

    /**
     * Sets up the context for test.
     */
    @Before
    public void before(){
        //initialize required references
        d_RunG = new RunGameEngine();
        d_Game = new GameData();
        d_Ge = new GameEngine();
    }

    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame(){
         d_Ge.parseCommand(null, "loadgame newgames");
         
        Assert.assertEquals(InternalPhase.ISSUE_ORDERS, d_Ge.getGame().getGamePhase());
    } 
    /**
     * Tests if valid game phase is maintained or not.
     */
    @Test
    public void testLoadGame3(){
        d_Ge.parseCommand(null, "loadgame newgames");
        Assert.assertEquals("p2", d_Ge.getGame().getActivePlayer().getPlayerName().toLowerCase());
    }

}
