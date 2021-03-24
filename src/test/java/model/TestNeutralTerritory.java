package model;

import controller.GameEngine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test if a territory became neutral for a specifc player
 * @author Rahul
 */
public class TestNeutralTerritory {
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
    /**
     * initial setup
     */
    @Before
    public void before() {
        d_Player1 = new Player("Yash");
        d_Player2 = new Player("Rahul");
        d_Map = new GameMap("dummy.map");
        d_CountryId = "pero";
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
    }

    /**
     * Test limits of Player's orders, with checks for country owned and army units.
     * Sample reinforcements assigned for Player1 and tested with unassigned reinforcements for Player2
     */
    @Test
    public void testNeutralTerritory() {
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_Stup.assignCountries(d_Map, d_Players);
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());

        CountryDetails l_c= d_Player1.getOwnedCountries().get(d_CountryId.toLowerCase());
        d_Player1.getOwnedCountries().remove(l_c.getCountryId().toLowerCase());
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        //Check if country owned by player 1
        l_checkOwnedCountry = d_Player1.getOwnedCountries().containsKey(d_CountryId.toLowerCase());
        System.out.println(l_checkOwnedCountry);
        //testing if above territory is neutral
        assertFalse(l_checkOwnedCountry);
    }

}
