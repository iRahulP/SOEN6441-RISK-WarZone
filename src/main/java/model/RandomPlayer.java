package model;

import java.util.Random;

/**
 * RandomPlayerStrategy class is ConcreteStrategy.
 * This strategy deploys on a random country, attacks random neighboring countries, and moves armies
 * randomly between its countries
 * @author  Rahul
 */
public class RandomPlayer extends PlayerStrategy{
    Random rand = new Random();
    CountryDetails d_RandomCountry,d_RandomNeighbour, d_RandomCountryWithArmies;
    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    public RandomPlayer(Player p_player, GameMap p_map) {
        super(p_player, p_map);
        d_RandomCountry = null;
        d_RandomNeighbour = null;
        d_RandomCountryWithArmies = null;
    }

    /**
     * Finds random Country to Bomb from map which is not owned by current player
     * @return random CountryID
     */
    private CountryDetails targetCountryNeighbour()
    {
        CountryDetails temp = null;
        boolean t = true;
        while(t){
            //gets a random Map from Map
            Object[] values = d_Map.getCountries().values().toArray();
            Object randomValue = values[rand.nextInt(values.length)];
            d_RandomCountry = (CountryDetails) randomValue;
            if(!d_Player.getOwnedCountries().containsValue(d_RandomCountry)){
                //get a random Country not owned by current player
                for (CountryDetails cD : d_Player.getOwnedCountries().values()) {
                    //check if target country neighbour to one of current occupied territories of source Player
                    if (cD.getNeighbours().containsKey(d_RandomCountry.getCountryId().toLowerCase()) && !d_Player.getOwnedCountries().containsKey(d_RandomCountry.getCountryId().toLowerCase())) {
                        temp = d_RandomCountry;
                        t = false;
                        break;
                    }
                }
                t = false;
            }
        }
        return temp;
    }

    /**
     * Finds random Country to deploy
     * @return random CountryID
     */
    private CountryDetails findRandomCountry()
    {
        Object[] values = d_Player.getOwnedCountries().values().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        d_RandomCountry = (CountryDetails)randomValue;
        return d_RandomCountry;
    }

    /**
     * Finds random Country to deploy other than the current source Country for Airlift
     * @return random CountryID
     */
    private CountryDetails findOtherRandomCountry()
    {
        boolean t = true;
        while(t){
            Object[] values = d_Player.getOwnedCountries().values().toArray();
            Object randomValue = values[rand.nextInt(values.length)];
            d_RandomCountry = (CountryDetails) randomValue;
            if(d_RandomCountry != d_RandomCountryWithArmies){
                t = false;
            }
        }
        return d_RandomCountry;
    }

    /**
     * toAttack function implements attacks to the
     * neighboring country from random country
     * @return country to attack
     */
    protected CountryDetails toAttack()
    {
        d_RandomNeighbour = null;
        boolean t = true;
        while(t){
            for(CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values())
            {
                if(!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
                {
                    d_RandomNeighbour = l_neighborCountry;
                    t = false;
                    break;
                }
            }
        }
        return  d_RandomNeighbour;
    }

    /**
     * toAdvance function implements advance to the
     * neighboring country from random owned country
     * @return country to advance
     */
    protected CountryDetails toAdvance()
    {
        d_RandomNeighbour = null;
        boolean t = true;
        while(t){
            for(CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values())
            {
                if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
                {
                    d_RandomNeighbour = l_neighborCountry;
                    t = false;
                    break;
                }
            }
        }
        return  d_RandomNeighbour;
    }

    @Override
    protected CountryDetails toMoveFrom() {
        return null;
    }

    /**
     * finds a Random Country owned by player which has armies to be advanced
     * @return country to attack from
     */
    protected CountryDetails toAttackFrom()
    {
        int l_maxArmies = 0, l_numArmies;
        for(CountryDetails l_countryDetails : this.d_Player.getOwnedCountries().values()) {
            l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies > l_maxArmies) {
                d_RandomCountryWithArmies = l_countryDetails;
                return d_RandomCountryWithArmies;
            }
        }
        return null;
    }

    /**
     * Gets a random Player for negotiation
     */
    protected Player getRandomPlayer(){
        Player l_diffPlayer = null;
        boolean t = true;
        while(t){
            //gets a random Map from Map
            Object[] values = d_Map.getCountries().values().toArray();
            Object randomValue = values[rand.nextInt(values.length)];
            d_RandomCountry = (CountryDetails) randomValue;
            if(!d_Player.getOwnedCountries().containsValue(d_RandomCountry) && d_RandomCountry.getOwnerPlayer()!= null){
                //get a random Country not owned by current player and also not neutral
                //get the Player which owns this country
                l_diffPlayer = d_RandomCountry.getOwnerPlayer();
                t = false;
            }
        }
        return l_diffPlayer;
    }

    /**
     * Creates a Random Order as per Strategy
     * @return random Order
     */
    @Override
    public Order createOrder() {
        CountryDetails l_attackingCountry,l_defendingCountry,l_advanceCountry;
        l_attackingCountry = toAttackFrom();
        l_defendingCountry = toAttack();
        l_advanceCountry = toAdvance();

        int rndOrder = rand.nextInt(7);
        int rnd_num_of_armies_pool = d_Player.getOwnedArmies();
        //returns one of the orders randomly with a Probability of 80% else null
        if (rand.nextInt(5) != 0) {
            switch (rndOrder) {
                case (0):
                    int randArmies = rand.nextInt(rnd_num_of_armies_pool);
                    d_Player.setOwnedArmies(d_Player.getOwnedArmies() - randArmies);
                    return new Deploy(d_Player, findRandomCountry().getCountryId(), randArmies);
                case (1):
                    if(l_defendingCountry!=null)
                        return new Advance(d_Player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(), rand.nextInt(l_attackingCountry.getNumberOfArmies()), l_defendingCountry.getOwnerPlayer());
                    else {
                        System.out.println("Neighbor does not exist for this country");
                        return null;
                    }
                case (2):
                    if(l_advanceCountry != null)
                        return new Advance(d_Player, l_attackingCountry.getCountryId(), l_advanceCountry.getCountryId(), rand.nextInt(l_attackingCountry.getNumberOfArmies()), l_advanceCountry.getOwnerPlayer());
                    else {
                        System.out.println("Neighbor does not exist for this country");
                        return null;
                    }
                case (3):
                    if(d_Player.doesCardExists("Blockade")){
                        d_Player.removeCard("Blockade");
                        return new Blockade(d_Player, findRandomCountry().getCountryId());
                    }else {
                        System.out.println("Player doesn't own Card Blockade");
                        return null;
                    }
                case (4):
                    if(d_Player.doesCardExists("Airlift")){
                        d_Player.removeCard("Airlift");
                        return new Airlift(d_Player, l_attackingCountry.getCountryId(), findOtherRandomCountry().getCountryId(),rand.nextInt(l_attackingCountry.getNumberOfArmies()));
                    }else {
                        System.out.println("Player doesn't own Card Airlift");
                        return null;
                    }
                case (5):
                    if(d_Player.doesCardExists("Diplomacy")){
                        d_Player.removeCard("Diplomacy");
                        return new Diplomacy(d_Player, getRandomPlayer());
                    }else {
                        System.out.println("Player doesn't own Card Diplomacy");
                        return null;
                    }
                case (6):
                    if(d_Player.doesCardExists("Bomb")){
                        d_Player.removeCard("Bomb");
                        return new Bomb(d_Player, targetCountryNeighbour().getOwnerPlayer(), targetCountryNeighbour().getCountryId());
                    }else {
                        System.out.println("Player doesn't own Card Bomb");
                        return null;
                    }
            }
    }
        return null;
    }
}
