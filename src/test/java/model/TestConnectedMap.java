package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test if the given map is connected or not
 *
 */
public class TestConnectedMap {

	MapValidator d_MapValidator;
    GameMap d_Map;
    RunGameEngine d_RunGE;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new GameMap("world.map");
        d_MapValidator = new MapValidator();
        d_RunGE = new RunGameEngine();
    }

    /**
     * Test if tests are rightly identified or not for the given map
     */
    @Test
    public void testConnectedMap(){
    	d_Map = d_RunGE.editMap("world.map");
        boolean l_check = d_MapValidator.isGraphConnected(d_MapValidator.createGraph(d_Map));
        assertEquals(true,l_check);
    }
}
