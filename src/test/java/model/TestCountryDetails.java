package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * To test the functinalities carried out related to Country
 *
 */
public class TestCountryDetails {

	GameMap d_Map;
    String d_CountryId;
	String d_ContinentId;
    RunGameEngine d_RunGE;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new GameMap("ameroki.map");
        d_RunGE = new RunGameEngine();
        d_ContinentId="utropa";
        d_CountryId="Iceland";
    }
    
    /**
     * Test if the country is added to the continent
     */
    @Test
    public void testAddCountry(){
    	d_Map = d_RunGE.editMap("ameroki.map");
    	System.out.println(d_Map.getMapName());
        System.out.println(d_Map.getCountries().size());   	
        boolean check_add = d_RunGE.addCountry(d_Map, d_CountryId, d_ContinentId);
        assertEquals(true,check_add);
    }
   

    /**
     * Test if the country is removed from map
     */
    @Test
    public void testRemoveCountry(){
    	
    	//worrick is part of map
    	d_Map = d_RunGE.editMap("ameroki.map");
    	d_CountryId = "worrick";
        boolean check_remove = d_RunGE.removeCountry(d_Map, d_CountryId);
        assertEquals(true,check_remove);
        
        //Brazil is not part of map
        d_CountryId="Brazil";
        check_remove = d_RunGE.removeCountry(d_Map, d_CountryId);
        assertEquals(false,check_remove);
    }
    
   
}
