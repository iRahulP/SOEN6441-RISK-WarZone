package model;

import controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Tests Random Order of Random Player Strategy
 * @author Rahul
 */
public class TestRandomStrategy{
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
    String d_SourceCountryId = null;
    String d_TargetCountryId = null;
    boolean l_checkOwnedCountry;
    int d_NumberOfArmies = 4;
    Card d_Card;
    String[] d_CardsList;


    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Astrid");
        d_Player2 = new Player("Mighty");
        //d_Map = new GameMap("dummy.map");
        d_Ge = new GameEngine();
        d_Rge = new RunGameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_SourceCountryId = "china";
        d_TargetCountryId = "china";
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_SourceCountryId,d_NumberOfArmies);
        d_OrderList = new ArrayDeque<>();
        d_Card = new Card();
        d_CardsList = new String[]{"Bomb", "Airlift", "Blockade", "Diplomacy"};
    }

//    /**
//     * Tests Random Order
//     */
//    @Test
//    public void testRandomStrat() {
//        d_Stup.assignCountries(d_Map, d_Players);
//        AssignReinforcement.assignReinforcementArmies(d_Player1);
//        AssignReinforcement.assignReinforcementArmies(d_Player2);
//
//        d_Player1.setStrategy(new RandomPlayer(d_Player1, d_Map));
//        d_Player1.setD_isHuman(false);
//        d_Player1.addCard("Bomb");
//        d_Player1.addCard("Airlift");
//        d_Player1.addCard("Blockade");
//        d_Player1.addCard("Diplomacy");
//
//
//        boolean ord = false;
//        ord = d_Player1.issueOrder();
//        System.out.println(ord);
//        if(!ord){
//            //Case when deploy Order
//            assertFalse(ord);
//            assertEquals(0, d_Player1.getD_orderList().size());
//        }else{
//            assertTrue(ord);
//            assertEquals(1, d_Player1.getD_orderList().size());
//        }
//    }

    /**
     * Tests Random Country Owner
     */
    @Test
    public void testRandomCountryPlayerOwner() {
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
        AssignReinforcement.assignReinforcementArmies(d_Player2);

        Random rand = new Random();
        Object[] values = d_Map.getCountries().values().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        CountryDetails d_RandomCountry = (CountryDetails) randomValue;
        System.out.println(d_RandomCountry.getCountryId());

        Player l_targetPlayer = null;
        for (Player temp : d_Players) {
            //check which player has target countryID
            if (temp.getOwnedCountries().containsKey(d_RandomCountry.getCountryId().toLowerCase())) {
                l_targetPlayer = temp;
                break;
            }
        }
        System.out.println(d_RandomCountry.getOwnerPlayer().getPlayerName());
        assertEquals(l_targetPlayer, d_RandomCountry.getOwnerPlayer());

    }
}
