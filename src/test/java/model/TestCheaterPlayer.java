package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestCheaterPlayer {
	
	RunGameEngine rg;
	GameMap map;
	GameData game;
	Player player1, player2;
	PlayerStrategy pg1, pg2;
	
    @Before
    public void before(){
    	
    	
    	rg= new RunGameEngine();
    	player1= new Player("p1");
    	player2= new Player("p2");
    	game= new GameData();
    	map=rg.loadMap("demo.map");
    	game.setMap(map);
    	pg1 = new CheaterPlayer(player1,map);
        game.getPlayers().add(player1);
        pg2 = new CheaterPlayer(player2,map);
        game.getPlayers().add(player2);
        
        player1.getOwnedCountries().put("one", game.getMap().getCountries().get("one"));
        player1.getOwnedCountries().put("two", game.getMap().getCountries().get("two"));
        
        player1.getOwnedCountries().get("one").setOwnerPlayer(player1);
        player1.getOwnedCountries().get("two").setOwnerPlayer(player1);
        
        player2.getOwnedCountries().put("four", game.getMap().getCountries().get("four"));
        player2.getOwnedCountries().put("three", game.getMap().getCountries().get("three"));
        player2.getOwnedCountries().put("five", game.getMap().getCountries().get("five"));
       
        player2.getOwnedCountries().get("four").setOwnerPlayer(player2);
        player2.getOwnedCountries().get("three").setOwnerPlayer(player2);
        player2.getOwnedCountries().get("five").setOwnerPlayer(player2);
       
        player1.getOwnedCountries().get("one").setNumberOfArmies(9);
        player1.getOwnedCountries().get("two").setNumberOfArmies(8);
        
        player2.getOwnedCountries().get("four").setNumberOfArmies(10);
        player2.getOwnedCountries().get("three").setNumberOfArmies(9);
        player2.getOwnedCountries().get("five").setNumberOfArmies(7);
        
        player1.getOwnedCountries().get("one").getNeighbours().put("three", player2.getOwnedCountries().get("three")); 
        player1.getOwnedCountries().get("two").getNeighbours().put("three", player2.getOwnedCountries().get("three"));
        player1.getOwnedCountries().get("two").getNeighbours().put("four", player2.getOwnedCountries().get("four"));
        
        player2.getOwnedCountries().get("four").getNeighbours().put("two", player1.getOwnedCountries().get("two"));
        player2.getOwnedCountries().get("three").getNeighbours().put("one", player1.getOwnedCountries().get("one"));
        player2.getOwnedCountries().get("four").getNeighbours().put("five", player2.getOwnedCountries().get("five"));
        player2.getOwnedCountries().get("three").getNeighbours().put("two", player1.getOwnedCountries().get("two"));
        player2.getOwnedCountries().get("five").getNeighbours().put("four", player2.getOwnedCountries().get("four"));
       
    }
   
    @Test
    public void TestcreateOrders(){
        pg1.createOrder();
        Assert.assertEquals(20, player1.getOwnedCountries().get("four").getNumberOfArmies());
    }
}
