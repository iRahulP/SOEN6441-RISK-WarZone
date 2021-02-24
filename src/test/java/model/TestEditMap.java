package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEditMap {
	
	RunGameEngine d_runGame;
	GameMap d_map;
	String d_mapName;
	
	/**
     * Set up the context
     */
    @Before
    public void before(){
        d_runGame = new RunGameEngine();
    }

    /**
     * Test to edit the map which is not existing and build it from scratch.
     */
    @Test
    public void testEditNewMap(){
    	d_mapName= "practice.map";
        d_map = d_runGame.editMap(d_mapName);
        assertEquals(d_map.getMapName(), d_mapName);
    }
    
    /**
     * Test to edit the map which exist in folder.
     */
    @Test
    public void testEditExistingMap(){
    	d_mapName= "dummy.map";
        d_map = d_runGame.editMap(d_mapName);
        assertEquals(d_map.getMapName(), d_mapName);
    }
    
}
