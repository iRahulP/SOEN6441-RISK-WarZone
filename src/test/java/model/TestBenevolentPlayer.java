package model;

import controller.GameEngine;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Tests benevolent order for non-human player
 * @author Shravya
 */
public class TestBenevolentPlayer {
    Order d_DOrder;
    Queue<Order> d_OrderList;
    Player d_Player1, d_Player2;
    String d_SourceCountry = null;
    String d_WeakCountry = null;
    GameMap d_Map;
    ArrayList<Player> d_Players;
    StartUp d_Stup;
    InternalPhase d_GamePhase;
    GameEngine d_Ge;
    RunGameEngine d_Rge;
    boolean l_checkOwnedCountry;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_Player1 = new Player("Jupiter");
        d_Player2 = new Player("Saturn");
        d_Ge = new GameEngine();
        d_Rge = new RunGameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_SourceCountry = "japan";
        d_WeakCountry = "south_afrori";
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_Player1.setOwnedArmies(3);
        d_Player2.setOwnedArmies(4);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_WeakCountry,d_Player1.getOwnedArmies());
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test benevolent order
     */
    @Test
    public void testBenevolentStart(){
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
        AssignReinforcement.assignReinforcementArmies(d_Player2);

        d_Player1.setStrategy(new BenevolentPlayer(d_Player1, d_Map));
        d_Player1.setD_isHuman(false);

        boolean ord = false;
        ord = d_Player1.issueOrder();
        System.out.println(ord);
        if(!ord){
            //Case when deploy Order
            assertFalse(ord);
            assertEquals(0, d_Player1.getD_orderList().size());
        }else{
            assertTrue(ord);
            assertEquals(1, d_Player1.getD_orderList().size());
        }
    }

    /**
     * Tests Random Country with Armies deployed
     */
    @Test
    public void testArmiesDeployed() {
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
        AssignReinforcement.assignReinforcementArmies(d_Player2);

        //String d_CountryId = "japan";
        d_DOrder = new Deploy(d_Player1, d_WeakCountry, d_Player1.getOwnedArmies());
        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_WeakCountry);

        if (l_checkOwnedCountry) {
            d_Player1.addOrder(d_DOrder);
            d_Player1.issue_order();
            System.out.println("After Deploy reinforcement Pool:" + d_Player1.getOwnedArmies());
        } else {
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }

        System.out.println("Order stored: "+d_Player1.getD_orderList());
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " + l_toRemove + " executed for player: " + d_Player1.getPlayerName());
        System.out.println(d_Player1.getD_orderList());
        l_toRemove.execute();
        CountryDetails l_c = d_Player1.getOwnedCountries().get(d_WeakCountry.toLowerCase());
        System.out.println(l_c.getNumberOfArmies());
        //Check if num of armies deployed correctly
        assertEquals(d_Player1.getOwnedArmies(), l_c.getNumberOfArmies());
    }
}
