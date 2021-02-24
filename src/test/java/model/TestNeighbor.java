package model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test if neighbors are being added and removed properly or not
 *
 */
public class TestNeighbor {

	RunGameEngine d_runGame;
    GameMap d_map;
    String d_countryName;
    String d_neighborCountryName;
   
    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_map = new GameMap("dummy.map");
        d_runGame = new RunGameEngine();
        d_countryName = "japan";
        d_neighborCountryName = "india";//pero
        d_map = d_runGame.editMap("dummy.map");
        
    }
    
    @After
    public void after() {
    	System.out.println("functionality check done");
    }

    /**
     * Test to remove neighbor
     */
    @Test
    public void removeNeighbor() {
    	System.out.println("Inside removeNeighbor");
        System.out.println(d_map.getMapName());
        boolean check = d_runGame.removeNeighbor(d_map, d_countryName, d_neighborCountryName);
        assertEquals(true,check);
    }
    
    /**
     * Test to add neighbor
     */
    @Test
    public void addNeighbor() {
    	System.out.println("Inside addNeighbor");
        System.out.println(d_map.getMapName());
        boolean check = d_runGame.addNeighbor(d_map, d_countryName, d_neighborCountryName);
        assertEquals(true,check);
    }
}