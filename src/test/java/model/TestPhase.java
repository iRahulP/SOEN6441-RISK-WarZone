package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
/**
 * 
 *test whether phase state is running properly or not
 *it does not allow to call any other state functionality. 
 */
public class TestPhase {
	public GameEngine p_Ge;
	  Player d_Player1;
	    Player d_Player2;
	    GameMap d_Map;
	    ArrayList<Player> d_Players;
	    String d_PlayerName="xyz";
	    RunGameEngine d_Rge;
	    String[] d_Data={"gameplayer", "-add", "xyz"};
	    String[] d_Data1 = new String[]{"loadmap","world.map"};
	    String d_MapName;
	    
	    /**
	     * initial setup
	     */
	 @Before
	 public void before(){
		 p_Ge = new GameEngine();
		 d_Rge = new RunGameEngine();
		 d_Players = new ArrayList<Player>();
		 d_Map = new GameMap("dummy.map");
		 d_Player1 = new Player("Yash");
	     d_Player2 = new Player("Rahul");
		 d_Players.add(d_Player1);
	     d_Players.add(d_Player2);
	     d_MapName= "dummy.map";
	    }
	 /**
	     * Test if the Reinforcements is allowed in preload or postload phase as it is part of mainplay phase.
	     */
	 @Test
	 public void testPhase(){
		 
		 p_Ge.setPhase(new PreLoad(p_Ge));
		 d_MapName= "world.map";
	 	 p_Ge.setPhase(new PreLoad(p_Ge));
		 String str=p_Ge.d_Phase.getD_PhaseName();
         System.out.println(str);  
	     p_Ge.d_Phase.loadMap(d_Data1,d_MapName);
		 boolean a=p_Ge.d_Phase.assignCountries(d_Map,d_Players );
		 System.out.println(a);
		 assertEquals(false, a); 
		 String str1=p_Ge.d_Phase.getD_PhaseName();
         System.out.println("Game is in "+str1+" state");
		
	    }
	 
}
