package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestMapType {
	
	 LoadMap d_LoadMap;
	 String d_MapName, d_MapType;
	 
	@Before
    public void before(){
        d_LoadMap= new LoadMap();
    }
	
	@Test
    public void testConquestMap(){
        d_MapName= "Asia.map";
        d_MapType= d_LoadMap.readMap("src/main/resources/maps/"+ d_MapName);
        assertEquals("conquest", d_MapType);
    }
	
	@Test
    public void testDominationMap(){
        d_MapName= "dummy.map";
        d_MapType= d_LoadMap.readMap("src/main/resources/maps/"+ d_MapName);
        assertEquals("domination", d_MapType);
    }
}
