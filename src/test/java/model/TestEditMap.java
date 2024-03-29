package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * TestClass checks edit functionality of the map.
 */
public class TestEditMap {
	
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
    public void testEditNewMap(){
    	d_MapName= "practice.map";
    	System.out.println(d_MapName);
        d_Map = d_RunGame.editMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }
    
    /**
     * Test to edit the domination map which exist in folder.
     */
    @Test
    public void testEditExistingDominationMap(){
    	d_MapName= "dummy.map";
    	System.out.println(d_MapName);
        d_Map = d_RunGame.editMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }
    
    /**
     * Test to edit the conquest map which exist in folder.
     */
    @Test
    public void testEditExistingConquestMap(){
    	d_MapName= "Asia.map";
    	System.out.println(d_MapName);
        d_Map = d_RunGame.editMap(d_MapName);
        assertEquals(d_Map.getMapName(), d_MapName);
    }
    
}
