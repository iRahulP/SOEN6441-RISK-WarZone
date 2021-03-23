package model;

import java.util.Random;

public class Card {

    /**
     * Represents type of Card from Bomb, Airlift, Blockade and Diplomacy
     */
    String d_CardType;

    /**
     * This list contains the cards issued to players
     */
    String[] d_CardsList = {"Bomb","Airlift","Blockade","Diplomacy"};

    /**
     *The method gives type of a card.
     * @return string card type
     */
    String getCardType()
    {
        return d_CardType;
    }

    /**
     * Default constructor of Card to access the methods of this class.
     */
    public Card(){
    }

    /**
     * This constructor will assign type of cards
     * @param p_cardType Card Type that is assigned
     */
    public Card(String p_cardType){
        this.d_CardType=p_cardType;
    }
    /**
     * Stores the random card picked in the CardType String
     */
    void createCard()
    {
        d_CardType = randomCard();
    }

    /**
     * Picks a random card from the Cards List using random generator
     * @return The index of the Cards List
     */
    String randomCard()
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(d_CardsList.length);
        return d_CardsList[index];
    }
}
