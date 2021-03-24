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
 * Class testing the attack order when other country is not owned by the player
 * @author Rucha
 *
 */
public class TestAttack{
    Order d_DOrder;
    Queue<Order> d_OrderList;
    Player d_Player1, d_Player2, d_TargetPlayer;
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
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("p1");
        d_Player2 = new Player("p2");
        d_Map = new GameMap("dummy.map");
        d_SourceCountryId = "egypt";
        d_TargetCountryId = "siberia";
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_DOrder = new Advance(d_Player1,d_SourceCountryId,d_TargetCountryId,d_NumberOfArmies, d_TargetPlayer);
        d_OrderList = new ArrayDeque<>();
    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testAdvanceEffect() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_Stup.assignCountries(d_Map, d_Players);
        for(Player tmp: d_Players) {
        	if(tmp.getOwnedCountries().containsKey(d_TargetCountryId)) {
        		d_TargetPlayer= tmp;
        		break;
        	}
        }
        AssignReinforcement.assignReinforcementArmies(d_Player1);

        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_SourceCountryId);
        CountryDetails attackingCountry = d_Player1.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
        CountryDetails defendingCountry = attackingCountry.getNeighbours().get(d_TargetCountryId.toLowerCase());
        attackingCountry.setNumberOfArmies(7);
        defendingCountry.setNumberOfArmies(3);
        
        boolean l_checkNeighbourCountry = (d_TargetCountryId.equals(defendingCountry.getCountryId()));
        boolean l_checkExistingArmies= attackingCountry.getNumberOfArmies()>= d_NumberOfArmies;
        if(l_checkOwnedCountry && l_checkNeighbourCountry && l_checkExistingArmies){
            d_Player1.addOrder(new Advance(d_Player1,d_SourceCountryId,d_TargetCountryId, d_NumberOfArmies, d_TargetPlayer ));
            d_Player1.issue_order();
        }
        else{
            System.out.println("Country not owned by player or sufficient army not present | please pass to next player");
        }
        
        System.out.println(d_Player1.getOwnedCountries().size());
        System.out.println(d_TargetPlayer.getOwnedCountries().size());
        
        System.out.println(d_Player1.getD_orderList());
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " +l_toRemove+ " executed for player: "+d_Player1.getPlayerName());
        System.out.println(d_Player1.getD_orderList());
        l_toRemove.execute();

        //Check if attack happen correctly and attacker win
        System.out.println(d_SourceCountryId + " and "+ d_TargetCountryId + " are neighbour to each other \n"
        					+ d_TargetCountryId + " is not owned by "+ d_Player1 );
        assertEquals(1 ,attackingCountry.getNumberOfArmies()); //((7-1)*60/100) = 3 //6-2=4 //1 remain
        assertEquals(4, defendingCountry.getNumberOfArmies()); //3*70/100= 2  // 3-3=0 //4 deploy
        
        System.out.println(d_Player1.getOwnedCountries().size());
        System.out.println(d_TargetPlayer.getOwnedCountries().size());
        
        System.out.println("Winning country is added to player countrylist");
        boolean l_checkWinningCountryOwned= d_Player1.getOwnedCountries().containsKey(d_TargetCountryId);
        assertEquals(true, l_checkWinningCountryOwned);
        d_Player1.showCards();
    }

}
