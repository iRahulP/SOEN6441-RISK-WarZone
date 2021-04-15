package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;

public class TestSaveGame {

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
		private GameData d_Game;
	    /**
	     * initial setup
	     */
	    @Before
	    public void before() {
	    	d_Game= new GameData();
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
	        this.d_Game.setActivePlayer(d_Player1);
	        this.d_Game.setGamePhase(d_GamePhase);
	        this.d_Game.setPlayers(d_Players);
	        this.d_Game.setMap(d_Map);
	        this.d_Game.setMapType("domination");
	        this.d_Game.setLogger(null);
	        this.d_Game.setDeck(null);
	        this.d_Game.setCardsDealt(0);
	        
	    }

	    /**
	     * Tests if a specific player won the Game
	     */
	    @Test
	    public void testGameSave() {
	    	String d_Savegame="GameTen";
	    	 boolean l_save=d_Rge.saveGame(this.d_Game,d_Savegame );
	    	 assertTrue(l_save);
	    }}


