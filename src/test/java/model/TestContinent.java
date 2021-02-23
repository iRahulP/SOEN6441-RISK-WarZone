package  model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Tests if continents are being removed properly or not
 *
 */
public class TestContinent {

    GameMap d_Map;
    String d_ContinentId;
    int d_ControlValue;
    RunGameEngine d_Rgame;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Map = new GameMap("ameroki.map");
        d_Rgame = new RunGameEngine();
        d_ContinentId = "eurozio";
        d_ControlValue = 5;
        d_Rgame.addContinent(d_Map,"azio",d_ControlValue);

    }

    /**
     * Test if continents are added properly
     */
    @Test
    public void testAddContinent(){

        d_Map = d_Rgame.editMap("ameroki.map");
        System.out.println(d_Map.getMapName());
        System.out.println(d_Map.getContinents().size());

        boolean check = d_Rgame.addContinent(d_Map,d_ContinentId,d_ControlValue);
        assertTrue(check);
    }

    /**
     * Test if continents are removed properly
     */
    @Test
    public void testRemoveContinent(){
        //azio is in the map,so it'll be removed
        d_ContinentId = "azio";
        boolean check = d_Rgame.removeContinent(d_Map, d_ContinentId);
        assertTrue(check);

        //Asia not part of the map.
        d_ContinentId = "Asia";
        check = d_Rgame.removeContinent(d_Map, d_ContinentId);
        assertFalse(check);

    }
}
