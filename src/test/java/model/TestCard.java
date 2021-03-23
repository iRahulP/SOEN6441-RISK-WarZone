package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCard {
    ArrayList<Card> d_Deck;
    Card d_Card;
    String d_CardType;

    @Before
    public void before(){
        d_CardType="Bomb";
        d_Card=new Card(d_CardType);
    }

    @Test
    public void TestCard(){
        d_Deck.add(d_Card);
        assertEquals(d_Deck,d_Card);
    }
}
