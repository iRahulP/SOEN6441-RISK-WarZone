package model;

import controller.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test if a specific player won or
 * @author Rahul
 */
public class TestGameWon{
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
        d_Player1 = new Player("Spidey");
        d_Player2 = new Player("WonderWoman");
        d_Map = new GameMap("dummy.map");
        d_Rge = new RunGameEngine();
        d_Players = new ArrayList<Player>();
        d_Players.add(d_Player1);
        d_Players.add(d_Player2);
        d_GamePhase = InternalPhase.ISSUE_ORDERS;
        l_checkOwnedCountry = true;
        d_Ge = new GameEngine();
        d_Stup = new StartUp(d_Ge);
        d_Map = d_Rge.loadMap("dummy.map");
        d_Stup.assignCountries(d_Map, d_Players);
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());
        d_Player2.getOwnedCountries().putAll(d_Player1.getOwnedCountries());
        d_Player1.getOwnedCountries().clear();
    }

    /**
     * Tests if a specific player won the Game
     */
    @Test
    public void testGameWon() {
        System.out.println("Countries assigned to "+d_Player1.getPlayerName()+" : "+d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to "+d_Player2.getPlayerName()+" : "+d_Player2.getOwnedCountries());

        boolean ifWon = false;
        for (Player l_p : d_Players){
            if(l_p.getOwnedCountries().size() == d_Map.getCountries().size()){
                System.out.println(l_p.getPlayerName()+" has Won the Game!");
                ifWon = true;
            }
        }
        assertTrue(ifWon);
    }

    /**
     * Tests if a specific player was removed from the Game
     */
    @Test
    public void testPlayerRemoved() {
        System.out.println("Countries assigned to " + d_Player1.getPlayerName() + " : " + d_Player1.getOwnedCountries());
        System.out.println("Countries assigned to " + d_Player2.getPlayerName() + " : " + d_Player2.getOwnedCountries());

        System.out.println(d_Players);
        boolean ifRemoved = false;
        for (Player l_p : d_Players){
            if(l_p.getOwnedCountries().size() == 0){
                System.out.println(l_p.getPlayerName()+" will be removed!");
                d_Players.remove(l_p);
                ifRemoved = true;
            }
        }
        System.out.println(d_Players);
        assertTrue(ifRemoved);
    }}

