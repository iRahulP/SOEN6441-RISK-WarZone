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
    int d_NumberOfArmies = 4;

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
        d_DOrder = new Deploy(d_Player1,d_WeakCountry,d_NumberOfArmies);
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

}
