package model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests if connected continent are being rightly identified or not.
 */
public class TestConnectedContinent {
    MapValidator d_Mvr;
    RunGameEngine d_Rgame;
    GameMap d_Map;

    /**
     * Set up the context
     */
    @Before
    public void before() {
        d_Mvr = new MapValidator();
        d_Rgame = new RunGameEngine();
        d_Map = new GameMap("world.map");
    }

    /**
     * Test cases when Continent subgraphs are connected.
     */
    @Test
    public void testConnectedContinent() {
        d_Map = d_Rgame.editMap("world.map");
        boolean l_check = d_Mvr.continentConnectivityCheck(d_Map);
        assertTrue(l_check);

        //Continent with a single country is connected subgraph
        d_Map = d_Rgame.editMap("createdMap.map");
        l_check = d_Mvr.continentConnectivityCheck(d_Map);
        assertTrue(l_check);
    }

    /**
     * Tests case where a continent subgraph is not connected
     */
    @Test
    public void testunConnectedContinent() {
        d_Map = d_Rgame.editMap("unconnectedContinent.map");
        boolean l_check = d_Mvr.continentConnectivityCheck(d_Map);
        assertFalse(l_check);
    }


}
