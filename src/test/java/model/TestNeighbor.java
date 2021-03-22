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
    String d_NeighborCountryName1, d_NeighborCountryName2;
   
    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new GameMap("dummy.map");
        d_RunGame = new RunGameEngine();
        d_CountryName = "japan";
        d_NeighborCountryName1 = "india";
        d_NeighborCountryName2 = "pero";
        d_Map = d_RunGame.editMap("dummy.map");
        
    }
    /**
     * It's the after method for test.
     */
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
    	System.out.println("checking of "+ d_CountryName+ " and " + d_NeighborCountryName1 );
        System.out.println(d_Map.getMapName());
        boolean l_check = d_RunGame.removeNeighbor(d_Map, d_CountryName, d_NeighborCountryName1);
        assertEquals(true,l_check);
        
        System.out.println("checking of "+ d_CountryName+ " and " + d_NeighborCountryName2 );
        l_check = d_RunGame.removeNeighbor(d_Map, d_CountryName, d_NeighborCountryName2);
        System.out.println("not neighbor");
        assertEquals(true,l_check);
    }
    
    /**
     * Test to add neighbor
     */
    @Test
    public void addNeighbor() {
    	System.out.println("Inside addNeighbor");
    	System.out.println("checking of "+ d_CountryName+ " and " + d_NeighborCountryName1 );
        System.out.println(d_Map.getMapName());
        boolean l_check = d_RunGame.addNeighbor(d_Map, d_CountryName, d_NeighborCountryName1);
        assertEquals(true,l_check);
        
        System.out.println("checking of "+ d_CountryName+ " and " + d_NeighborCountryName2 );
        l_check = d_RunGame.addNeighbor(d_Map, d_CountryName, d_NeighborCountryName2);
        System.out.println("not neighbors so added as neighbor");
        assertEquals(true,l_check);
    }
}