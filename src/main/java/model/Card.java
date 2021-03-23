package model;

import java.util.Random;

public class Card {
    String d_CardType;
    String d_Card;
    String[] cardsList = {"Bomb","Airlift","Blockade","Diplomacy"};


    String getCardType()
    {
        return d_CardType;
    }

    void createCard()
    {
        d_CardType = randomCard();
    }

    String randomCard()
    {

        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(cardsList.length);
        return cardsList[index];
    }
}
