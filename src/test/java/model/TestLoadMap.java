package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 * Test Class checks the loading of the map.
 */
public class TestLoadMap {

    RunGameEngine d_RunGame;
    GameMap d_Map;
    String d_MapName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_RunGame = new RunGameEngine();
    }

    /**
     * Test to load the domination map 
     */
    @Test
    public void testDominationLoadMap(){
        d_MapName= "dummy.map";
        System.out.println(d_MapName);
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }
    

    /**
     * Test to load the conquest map
     */
    @Test
    public void testConquestLoadMap(){
        d_MapName= "Asia.map";
        System.out.println(d_MapName);
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

}
