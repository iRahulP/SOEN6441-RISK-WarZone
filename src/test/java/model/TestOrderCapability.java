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

public class TestOrderCapability {
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    Phase d_GamePhase;
    GameEngine d_Ge;
    RunGameEngine d_Rge;
    int d_NumberOfArmies = 4;

    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Yash");
        d_Player2 = new Player("Rahul");
        d_Map = new GameMap("dummy.map");
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = Phase.ISSUE_ORDERS;

    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testOrderCapability() {
        d_Stup = new StartUp();
        d_Ge = new GameEngine();
        d_Map = d_Rge.loadMap("dummy.map");
        boolean check = d_Stup.assignCountries(d_Map, d_Players);
        System.out.println(d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());

        //only assigned to Player 1
        if(check){
            AssignReinforcement.assignReinforcementArmies(d_Player1);
        }
        //performed checks for owned country and allowed army units.
        boolean checkOwnedCountry1 = d_Player1.getOwnedCountries().containsKey("japan");
        boolean checkArmies1 = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);

        if(checkOwnedCountry1 && checkArmies1){

            System.out.println("Before :"+d_Player1.getOwnedArmies());
            Order temp = new Order(d_Player1, "japan", d_NumberOfArmies);
            d_Player1.addOrder(temp);
            d_Player1.issue_order();
            d_Player1.setOwnedArmies(d_Player1.getOwnedArmies()-d_NumberOfArmies);
            System.out.println("After :"+d_Player1.getOwnedArmies());
        }
        else{
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }

        System.out.println(d_Player1.getPlayerName()+":"+d_Player1.getOwnedArmies());
        System.out.println(d_Player2.getPlayerName()+":"+d_Player2.getOwnedArmies());

        boolean checkOwnedCountry2 = d_Player2.getOwnedCountries().containsKey("sluci");
        boolean checkArmies2 = (d_Player2.getOwnedArmies() >= d_NumberOfArmies);

        if(checkOwnedCountry2 && checkArmies2){
            System.out.println("Before :"+d_Player2.getOwnedArmies());
            Order temp = new Order(d_Player2, "sluci", d_NumberOfArmies);
            d_Player2.addOrder(temp);
            d_Player2.issue_order();
            d_Player2.setOwnedArmies(d_Player2.getOwnedArmies()-d_NumberOfArmies);
            System.out.println("After :"+d_Player2.getOwnedArmies());
        }
        else{
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }
        System.out.println(d_Player1.getPlayerName()+":"+d_Player1.getOwnedArmies());
        System.out.println(d_Player2.getPlayerName()+":"+d_Player2.getOwnedArmies());

        //testing if above armies were updated for Player1
        assertEquals(3,d_Player1.getOwnedArmies());
        //testing if above armies were updated for Player2
        assertEquals(0,d_Player2.getOwnedArmies());
    }

}

