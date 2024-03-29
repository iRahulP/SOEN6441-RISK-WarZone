package model;

import controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Test deploy operation for a specific player in Game
 * @author Rahul
 */
public class TestDeploy{
    Order d_DOrder;
    Queue<Order> d_OrderList;
    Player d_Player1;
    Player d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    InternalPhase d_GamePhase;
    GameEngine d_Ge;
    RunGameEngine d_Rge;
    String d_CountryId = null;
    boolean l_checkOwnedCountry;
    int d_NumberOfArmies = 4;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Rahul");
        d_Player2 = new Player("Yash");
        d_Map = new GameMap("dummy.map");
        d_CountryId = "pero";
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_CountryId,d_NumberOfArmies);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testDeployEffect() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);

        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_CountryId);
        boolean l_checkArmies = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);

        if(l_checkOwnedCountry && l_checkArmies){
            d_Player1.addOrder(d_DOrder);
            d_Player1.issue_order();
            d_Player1.setOwnedArmies(d_Player1.getOwnedArmies()-d_NumberOfArmies);
            System.out.println("After Deploy reinforcement Pool:"+d_Player1.getOwnedArmies());
        }
        else{
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }
        System.out.println(d_Player1.getD_orderList());
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " +l_toRemove+ " executed for player: "+d_Player1.getPlayerName());
        System.out.println(d_Player1.getD_orderList());
        l_toRemove.execute();

        CountryDetails l_c= d_Player1.getOwnedCountries().get(d_CountryId.toLowerCase());
        System.out.println(l_c.getNumberOfArmies());
        //Check if num of armies deployed correctly
        assertEquals(d_NumberOfArmies ,l_c.getNumberOfArmies());
    }

}
