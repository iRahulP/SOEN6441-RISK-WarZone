package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;

import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Tests if addition of orders works
 */
public class TestRemoveOrder {
    Order d_order;
    Queue<Order> d_orderList;
    Player d_player;
    String d_playerName;

    /**
     * Set up the context
     */
    @Before
    public void before(){
        d_playerName = "Rahul";
        d_player = new Player(d_playerName);
        d_order = new Order(d_player,"India",5);
        d_orderList = new ArrayDeque<>();
        d_orderList.add(d_order);
    }

    /**
     * Test if tests are rightly identified or not
     */
    @Test
    public void testRemoveOrder(){
        //removes order
        d_orderList.poll();
        assertEquals(0,d_orderList.size());
    }

}


