package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testLoadMap(){
        d_MapName= "world.map";
        d_Map = d_RunGame.loadMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }

}
