package model;


import static org.junit.Assert.*;

import java.util.ArrayList;

import controller.GameEngine;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests if countries are assigned properly or not
 *
 */

public class TestAssigncountries {
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    RunGameEngine d_Rge;
    InternalPhase d_GamePhase;
    GameEngine d_Ge;
    AssignReinforcement d_Arfc;

    /**
     * Set up the map and add players 
     */
    @Before
    public void before() {
        d_Player1 = new Player("yash");
        d_Player2 = new Player("rahul");
        d_Map = new GameMap("dummy.map");
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
    }

    /**
     * Test if the countries are assign properly or not
     */
    @Test
    public void testPopulateCountries() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        boolean l_check = d_Stup.assignCountries(d_Map, d_Players);
        assertEquals(true,l_check);
        assertEquals(d_Player1.getOwnedCountries().size(),d_Player2.getOwnedCountries().size());
    }

}

