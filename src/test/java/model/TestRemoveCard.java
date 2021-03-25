package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test Class to checks whether card exist and is removed or not.
 */
public class TestRemoveCard {
    Player d_player;
    String d_Card;

    /**
     * Creating the player object
     */
    @Before
    public void before(){
        d_player = new Player("Jupiter");
        d_Card="Bomb";
    }

    /**
     * To check the card exist and is removed or not
     */
    @Test
    public void TestCard(){
        d_player.addCard("Bomb");
        d_player.addCard("Airlift");
        d_player.addCard("Diplomacy");
        Boolean l_cardExist=d_player.doesCardExists(d_Card);
        assertEquals(true,l_cardExist);
        d_player.showCards();
        d_player.removeCard("Bomb");
        System.out.println("After Removing Bomb card from the Deck");
        d_player.showCards();
    }
}
