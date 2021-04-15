package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
/**
 * test case for saving the game.
 * @author yashs
 *
 */
public class TestSaveGame {


	RunGameEngine d_RunGame;
	GameMap d_Map;
	GameData d_Game;
	Player d_Player1, d_Player2;
	PlayerStrategy d_Pg1, d_Pg2;
	    /**
	     * initial setup
	     */
	    @Before
	    public void before() {
	    	d_RunGame= new RunGameEngine();
	    	d_Player1= new Player("p1");
	    	d_Player2= new Player("p2");
	    	d_Game= new GameData();
	    	d_Map=d_RunGame.loadMap("demo.map");
	    	d_Game.setMap(d_Map);
	    	d_Pg1 = new CheaterPlayer(d_Player1,d_Map);
	        d_Game.getPlayers().add(d_Player1);
	        d_Pg2 = new CheaterPlayer(d_Player2,d_Map);
	        d_Game.getPlayers().add(d_Player2);
	        
	        d_Player1.getOwnedCountries().put("one", d_Game.getMap().getCountries().get("one"));
	        d_Player1.getOwnedCountries().put("two", d_Game.getMap().getCountries().get("two"));
	        
	        d_Player1.getOwnedCountries().get("one").setOwnerPlayer(d_Player1);
	        d_Player1.getOwnedCountries().get("two").setOwnerPlayer(d_Player1);
	        
	        d_Player2.getOwnedCountries().put("four", d_Game.getMap().getCountries().get("four"));
	        d_Player2.getOwnedCountries().put("three", d_Game.getMap().getCountries().get("three"));
	        d_Player2.getOwnedCountries().put("five", d_Game.getMap().getCountries().get("five"));
	       
	        d_Player2.getOwnedCountries().get("four").setOwnerPlayer(d_Player2);
	        d_Player2.getOwnedCountries().get("three").setOwnerPlayer(d_Player2);
	        d_Player2.getOwnedCountries().get("five").setOwnerPlayer(d_Player2);
	       
	        d_Player1.getOwnedCountries().get("one").setNumberOfArmies(9);
	        d_Player1.getOwnedCountries().get("two").setNumberOfArmies(8);
	        
	        d_Player2.getOwnedCountries().get("four").setNumberOfArmies(10);
	        d_Player2.getOwnedCountries().get("three").setNumberOfArmies(9);
	        d_Player2.getOwnedCountries().get("five").setNumberOfArmies(7);
	        
	        d_Player1.getOwnedCountries().get("one").getNeighbours().put("three", d_Player2.getOwnedCountries().get("three")); 
	        d_Player1.getOwnedCountries().get("two").getNeighbours().put("three", d_Player2.getOwnedCountries().get("three"));
	        d_Player1.getOwnedCountries().get("two").getNeighbours().put("four", d_Player2.getOwnedCountries().get("four"));
	        
	        d_Player2.getOwnedCountries().get("four").getNeighbours().put("two", d_Player1.getOwnedCountries().get("two"));
	        d_Player2.getOwnedCountries().get("three").getNeighbours().put("one", d_Player1.getOwnedCountries().get("one"));
	        d_Player2.getOwnedCountries().get("four").getNeighbours().put("five", d_Player2.getOwnedCountries().get("five"));
	        d_Player2.getOwnedCountries().get("three").getNeighbours().put("two", d_Player1.getOwnedCountries().get("two"));
	        d_Player2.getOwnedCountries().get("five").getNeighbours().put("four", d_Player2.getOwnedCountries().get("four"));
	       
	        
	    }

	    /**
	     * Tests if a specific a specific game is saved or not.
	     */
	    @Test
	    public void testGameSave() {
	    	String d_Savegame="GameTen";
	    	try { boolean l_save=d_RunGame.saveGame(this.d_Game,d_Savegame );
	    	if(l_save) {
                System.out.println("current Game saved is saved ");
                }
	    	 assertTrue(l_save);
	    	}catch(Exception e) {System.out.println("exception");}
	    	
	    }}


