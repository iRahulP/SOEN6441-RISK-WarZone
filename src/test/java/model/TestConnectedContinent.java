package model;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests if connected continent are being rightly identified or not.
 *
 */
public class TestConnectedContinent {
    MapValidator d_Mvr;
    RunGameEngine d_Rgame;
    GameMap d_Map;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Mvr = new MapValidator();
        d_Rgame = new RunGameEngine();
        d_Map = new GameMap("world.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testConnectedContinent(){
        d_Map = d_Rgame.editMap("world.map");
        boolean check = d_Mvr.continentConnectivityCheck(d_Map);
        assertTrue(check);

        d_Map = d_Rgame.editMap("createdMap.map");
        check = d_Mvr.continentConnectivityCheck(d_Map);
        assertTrue(check);
    }

    @Test
    public void testNotConnectedContinent(){

    }


}
