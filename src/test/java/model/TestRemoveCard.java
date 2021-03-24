package model;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class to checks wether card is removed or not.
 */
public class TestRemoveCard {
    Player d_player;

    /**
     * Creating the player object
     */
    @Before
    public void before(){
        d_player = new Player("Jupiter");
    }

    /**
     * To check the card is removed or not
     */
    @Test
    public void TestCard(){
        d_player.addCard("Bomb");
        d_player.addCard("Airlift");
        d_player.addCard("Diplomacy");
        d_player.showCards();
        d_player.removeCard("Bomb");
        System.out.println("After Removing Bomb card from the Deck");
        d_player.showCards();
    }
}
