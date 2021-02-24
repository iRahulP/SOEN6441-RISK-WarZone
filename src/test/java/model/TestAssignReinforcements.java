package model;


import controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests if Reinforcements are assigned properly or not
 *
 */

public class TestAssignReinforcements {
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    Phase d_GamePhase;
    GameEngine d_Ge;
    AssignReinforcement d_Arfc;

    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Yash");
        d_Player2 = new Player("Rahul");
        d_Map = new GameMap("dummy.map");
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = Phase.STARTUP;

    }

    /**
     * Test if the Reinforcements are assigned or not.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testAssignReinforcements() {
        d_Stup = new StartUp();
        d_Ge = new GameEngine();
        boolean check = d_Stup.assignCountries(d_Map, d_Players);
        if(check){
            AssignReinforcement.assignReinforcementArmies(d_Player1);
        }

        assertNotEquals(d_Player1.getOwnedArmies(),d_Player2.getOwnedArmies());
        assertEquals(3,d_Player1.getOwnedArmies());
        assertEquals(0,d_Player2.getOwnedArmies());
    }

}

