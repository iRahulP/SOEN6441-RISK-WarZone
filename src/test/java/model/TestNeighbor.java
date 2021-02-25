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

	RunGameEngine d_RunGame;
    GameMap d_Map;
    String d_CountryName;
    String d_NeighborCountryName;
   
    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new GameMap("dummy.map");
        d_RunGame = new RunGameEngine();
        d_CountryName = "japan";
        d_NeighborCountryName = "india";//pero
        d_Map = d_RunGame.editMap("dummy.map");
        
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
        System.out.println(d_Map.getMapName());
        boolean l_check = d_RunGame.removeNeighbor(d_Map, d_CountryName, d_NeighborCountryName);
        assertEquals(true,l_check);
    }
    
    /**
     * Test to add neighbor
     */
    @Test
    public void addNeighbor() {
    	System.out.println("Inside addNeighbor");
        System.out.println(d_Map.getMapName());
        boolean l_check = d_RunGame.addNeighbor(d_Map, d_CountryName, d_NeighborCountryName);
        assertEquals(true,l_check);
    }
}