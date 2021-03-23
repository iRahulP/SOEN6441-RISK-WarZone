package model;

import controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class TestBomb{
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
    //ArrayList<Player> d_NegotiateList;
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Astrid");
        d_Player2 = new Player("Mighty");
        d_Map = new GameMap("dummy.map");
        d_SourceCountryId = "china";
        d_TargetCountryId = "china";
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_DOrder = new Deploy(d_Player1,d_SourceCountryId,d_NumberOfArmies);
        d_OrderList = new ArrayDeque<>();
        //d_NegotiateList = new ArrayList<Player>();
    }

    /**
     * Player 1 deploys specific army units
     * Player 2 bombs the territory where the armies wee deployed by Player 1
     */
    @Test
    public void testBombEffect() {
        d_Stup = new StartUp(d_Ge);
        d_Ge = new GameEngine();
        d_Map = d_Rge.loadMap("dummy.map");
        d_Stup.assignCountries(d_Map, d_Players);
        AssignReinforcement.assignReinforcementArmies(d_Player1);
//        System.out.println(d_Player1.getD_NegotiateList());
//        d_Player1.addPlayerNegList(d_Player2);
//        System.out.println(d_Player1.getD_NegotiateList());

        //System.out.println("Countries assigned to " + d_Player1.getPlayerName() + " : " + d_Player1.getOwnedCountries()); //egypt, japan
        //System.out.println("Countries assigned to " + d_Player2.getPlayerName() + " : " + d_Player2.getOwnedCountries()); //albania, afghanistan
        //performed checks for owned country and allowed army units.
        boolean l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_SourceCountryId);
        boolean l_checkArmies = (d_Player1.getOwnedArmies() >= d_NumberOfArmies);

        if (l_checkOwnedCountry && l_checkArmies) {
            d_Player1.addOrder(d_DOrder);
            d_Player1.issue_order();
            d_Player1.setOwnedArmies(d_Player1.getOwnedArmies() - d_NumberOfArmies);
            System.out.println("After :" + d_Player1.getOwnedArmies());
        } else {
            System.out.println("Country not owned by player or insufficient Army units | please pass to next player");
        }
        Order l_toRemove = d_Player1.next_order();
        System.out.println("Order: " + l_toRemove + " executed for player: " + d_Player1.getPlayerName());
        l_toRemove.execute();

        //Check if china is of player 2
        boolean l_checkOwnedCountryNotOfCurrent = !d_Player2.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase());
        boolean targetCountryNeighbour = false;
        //checks if target country id is neighbour to one of current player's country
        for (CountryDetails cD : d_Player2.getOwnedCountries().values()) {
            //System.out.println(cD.getCountryId());
            //System.out.println(cD.getNeighbours().containsKey(d_TargetCountryId));
            if (cD.getNeighbours().containsKey(d_TargetCountryId.toLowerCase()) && l_checkOwnedCountryNotOfCurrent ) {
                targetCountryNeighbour = true;
                break;
            }
        }

        Player targetPlayer = null;
        for (Player temp : d_Players) {
            //check which player has target countryID
            if (temp.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())) {
                targetPlayer = temp;
                break;
            }
        }
//        System.out.println(targetPlayer);
//        System.out.println("Check");
//        d_Player2.addPlayerNegList(targetPlayer);
//        System.out.println(d_Player2.d_NegotiateList);
        if (l_checkOwnedCountryNotOfCurrent && targetCountryNeighbour && !d_Player2.getOwnedCountries().containsKey(d_TargetCountryId.toLowerCase())){
            //need to send target player instead of current player as param
            d_Player2.addOrder(new Bomb(d_Player2,targetPlayer, d_TargetCountryId));
            d_Player2.issue_order();
        }

        //Execute Bomb order
        Order l_toRemoveB = d_Player2.next_order();
        l_toRemoveB.execute();
        System.out.println("Order: " + l_toRemoveB + " executed for player: " + d_Player2.getPlayerName());
        CountryDetails l_cB= d_Player1.getOwnedCountries().get(d_TargetCountryId.toLowerCase());
        //Check if target territory was bombed
        assertEquals(2,l_cB.getNumberOfArmies());
    }
}
