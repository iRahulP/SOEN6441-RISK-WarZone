package model;


import static org.junit.Assert.*;

import java.util.ArrayList;

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

    /**
     * Set up the map and add players 
     */
    @Before
    public void before() {

        d_Player1 = new Player("yash");
        d_Player2 = new Player("rahul");
        d_Map = new GameMap("dummy.map");
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
    }

    /**
     * Test if the countries are assign properly or not
     */
    @Test
    public void testPopulateCountries() {
    	d_Stup = new StartUp();
        boolean check = d_Stup.assignCountries(d_Map, d_Players);
        assertEquals(true,check);
    }

}

