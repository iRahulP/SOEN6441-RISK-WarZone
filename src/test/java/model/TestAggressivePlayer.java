package model;

import controller.GameEngine;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Tests Aggressive order for non-human player
 * @author Aarthi
 */
public class TestAggressivePlayer {
    Order d_DOrder;
    Queue<Order> d_OrderList;
    Player d_Player1, d_Player2;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    InternalPhase d_GamePhase;
    GameEngine d_Ge;
    RunGameEngine d_Rge;
    boolean l_checkOwnedCountry;
    CountryDetails d_StrongestCountry,d_DefendingCountry,d_MoveFromCountry,d_InitialCountry;
    private int d_OrderVal,d_MaxArmies;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Player1 = new Player("Aarthi");
        d_Player2 = new Player("Shravya");
        d_Ge = new GameEngine();
        d_Rge = new RunGameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_Player1.setOwnedArmies(7);
        d_Player2.setOwnedArmies(7);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        d_OrderList = new ArrayDeque<>();
        d_StrongestCountry = null;
        d_DefendingCountry = null;
        d_InitialCountry = null;
        d_MaxArmies = 0;
    }

    /**
     * Test Aggressive Deploy  order
     */
    @Test
    public void testAggressiveDeploy(){
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
        AssignReinforcement.assignReinforcementArmies(d_Player2);

        AggresivePlayer l_strat = new AggresivePlayer(d_Player1, d_Map);
        d_Player1.setStrategy(l_strat);
        d_Player1.setD_isHuman(false);

        l_strat.isTest(true);

        //to test deploy
        l_strat.setTestOrderValue(0);
        Order l_order = l_strat.createOrder();

        d_StrongestCountry = l_strat.d_StrongestCountry;

        if(l_strat.d_TestReinforceArmies > 0) {
            d_Player1.addOrder(l_order);
            d_Player1.issue_order();

            System.out.println("Order stored: " + d_Player1.getD_orderList());
            Order l_toRemove = d_Player1.next_order();
            System.out.println("Order: " + l_toRemove + " executed for player: " + d_Player1.getPlayerName());
            System.out.println(d_Player1.getD_orderList());
            l_toRemove.execute();
            CountryDetails l_c = d_Player1.getOwnedCountries().get(d_StrongestCountry.getCountryId().toLowerCase());
            System.out.println("Armies deployed on country"+l_c.getCountryId().toLowerCase()+" "+l_c.getNumberOfArmies());
            //Check if num of armies deployed correctly
            assertEquals(l_strat.d_TestReinforceArmies, l_c.getNumberOfArmies());
            l_strat.isTest(false);
        }
        else
            System.out.println("The random value for deployment armies is not gretaer than 0");
    }

    /**
     * Test Aggressive Advance  order
     */
    @Test
    public void testAggressiveAttack(){

        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
        AssignReinforcement.assignReinforcementArmies(d_Player2);

        AggresivePlayer l_strat = new AggresivePlayer(d_Player1, d_Map);
        d_Player1.setStrategy(l_strat);
        d_Player1.setD_isHuman(false);

        l_strat.isTest(true);

        //to test deploy
        l_strat.setTestOrderValue(0);
        Order l_order = l_strat.createOrder();

        d_StrongestCountry = l_strat.d_StrongestCountry;

        if(l_strat.d_TestReinforceArmies > 0) {
            d_Player1.addOrder(l_order);
            d_Player1.issue_order();

            System.out.println("Order stored: " + d_Player1.getD_orderList());
            Order l_toRemove = d_Player1.next_order();
            System.out.println("Order: " + l_toRemove + " executed for player: " + d_Player1.getPlayerName());
            System.out.println(d_Player1.getD_orderList());
            l_toRemove.execute();
            CountryDetails l_c = d_Player1.getOwnedCountries().get(d_StrongestCountry.getCountryId().toLowerCase());
            System.out.println("Armies deployed on country "+l_c.getCountryId().toLowerCase()+" "+l_c.getNumberOfArmies());
            //Check if num of armies deployed correctly

            System.out.println("Armies in attacking country "+d_StrongestCountry.getNumberOfArmies());
            l_strat.setTestOrderValue(1);
            Order l_order1 = l_strat.createOrder();
            if(l_order1!=null) {
                d_Player1.addOrder(l_order1);
                d_Player1.issue_order();
                System.out.println("Order stored: " + d_Player1.getD_orderList());
                Order l_toRemove1 = d_Player1.next_order();
                System.out.println("Order: " + l_toRemove1 + " executed for player: " + d_Player1.getPlayerName());
                System.out.println(d_Player1.getD_orderList());
                l_toRemove1.execute();

                System.out.println("Attacking country is" + d_StrongestCountry.getCountryId() + " defending country is " + l_strat.d_DefendingCountry.getCountryId());
                System.out.println("Armies in attacking country " + l_strat.d_StrongestCountry.getNumberOfArmies());
                System.out.println("Armies in defending country " + l_strat.d_DefendingCountry.getNumberOfArmies());
            }
            else
                System.out.println("Invalid attack order given");
        }
        else
            System.out.println("The random value for deployment armies is not gretaer than 0");

    }
}
