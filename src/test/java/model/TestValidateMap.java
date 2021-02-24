package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests if map is validated or not
 */
public class TestValidateMap {
    RunGameEngine d_Rgame;
    GameMap d_Map;

    /**
     * Set up the context
     */
    @Before
    public void before(){

        d_Rgame = new RunGameEngine();
        d_Map = new GameMap("ameroki.map");
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testValidateMap(){
        d_Map = d_Rgame.editMap("ameroki.map");
        boolean check = d_Rgame.validateMap(d_Map);
        assertTrue(check);
    }
}
