package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Class testing the implementation of cheater strategy
 * @author Rucha
 *
 */
public class TestCheaterPlayer {
	
	RunGameEngine d_RunGame;
	GameMap d_Map;
	GameData game;
	Player d_Player1, d_Player2;
	PlayerStrategy d_Pg1, d_Pg2;
	
	/**
	 * set up the context
	 */
    @Before
    public void before(){
    	
    	d_RunGame= new RunGameEngine();
    	d_Player1= new Player("p1");
    	d_Player2= new Player("p2");
    	game= new GameData();
    	d_Map=d_RunGame.loadMap("demo.map");
    	d_Pg1 = new CheaterPlayer(d_Player1,d_Map);
        game.getPlayers().add(d_Player1);
        d_Pg2 = new CheaterPlayer(d_Player2,d_Map);
        game.getPlayers().add(d_Player2);
        
        d_Player1.getOwnedCountries().put("one", d_Map.getCountries().get("one"));
        d_Player1.getOwnedCountries().put("two", d_Map.getCountries().get("two"));
        
        d_Player1.getOwnedCountries().get("one").setOwnerPlayer(d_Player1);
        d_Player1.getOwnedCountries().get("two").setOwnerPlayer(d_Player1);
        
        d_Player2.getOwnedCountries().put("four",d_Map.getCountries().get("four"));
        d_Player2.getOwnedCountries().put("three",d_Map.getCountries().get("three"));
        d_Player2.getOwnedCountries().put("five", d_Map.getCountries().get("five"));
       
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
     * Test if it create orders properly or not and its implementation
     */
    @Test
    public void TestcreateOrders(){
        d_Pg1.createOrder();
        Assert.assertEquals(20, d_Player1.getOwnedCountries().get("four").getNumberOfArmies());
    }
}
