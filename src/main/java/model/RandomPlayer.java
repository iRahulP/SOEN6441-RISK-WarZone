package model;

import java.util.Random;

/**
 * RandomPlayerStrategy class is ConcreteStrategy.
 * This strategy deploys on a random country, attacks random neighboring countries, and moves armies
 * randomly between its countries
 * @author  Rahul
 */
public class RandomPlayer extends PlayerStrategy {
    Random rand = new Random();
    CountryDetails d_RandomCountry, d_RandomNeighbour, d_RandomCountryWithArmies;
    CountryDetails l_attackingCountry, l_defendingCountry, l_advanceCountry;
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
     * @param d_RandomCountryWithArmies country
     * @return random CountryID
     */
    private CountryDetails targetCountryNeighbour(CountryDetails d_RandomCountryWithArmies) {
        CountryDetails temp = null;
        if(d_RandomCountryWithArmies == null){
            return null;
        }else {
            for (CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values()) {
                if (!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId())) {
                    temp = l_neighborCountry;
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * Finds random Country to deploy
     *
     * @return random CountryID
     */
    private CountryDetails findRandomCountry() {
        d_RandomCountry = null;
        Object[] values = d_Player.getOwnedCountries().values().toArray();
        int totalC = values.length - 1;
        if(d_Player.getOwnedCountries().size() != 0){
            Object randomValue = values[rand.nextInt(totalC + 1)];
            d_RandomCountry = (CountryDetails) randomValue;
        }
        return d_RandomCountry;
    }

    /**
     * Finds random Country to deploy other than the current source Country for Airlift
     *
     * @return random CountryID
     */
    private CountryDetails findOtherRandomCountry() {
        d_RandomCountry = null;
        if(d_RandomCountryWithArmies == null){
            //when there was no source country with any armies
            return d_RandomCountry;
        }
        else {
            CountryDetails temp = null;
            //get a random Source country
            d_RandomCountry = findRandomCountry();
            if (d_RandomCountry != d_RandomCountryWithArmies) {
                //if source different than target then return for airlift
                temp = d_RandomCountry;
            }
            return temp;
        }
    }

    /**
     * toAttack function implements attacks to the
     * neighboring country from random country
     * @param d_RandomCountryWithArmies country
     * @return country to attack
     */
    protected CountryDetails toAttack(CountryDetails d_RandomCountryWithArmies) {
        if (d_RandomCountryWithArmies == null) {
            //there was no country with armies
            return null;
        } else {
            d_RandomNeighbour = null;
            for (CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values()) {
                if (!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId())) {
                    d_RandomNeighbour = l_neighborCountry;
                    return d_RandomNeighbour;
                }
            }
            return d_RandomNeighbour;
        }
    }

    /**
     * toAdvance function implements advance to the
     * neighboring country from random owned country
     * @param d_RandomCountryWithArmies country
     * @return country to advance
     */
    protected CountryDetails toAdvance(CountryDetails d_RandomCountryWithArmies) {
        if (d_RandomCountryWithArmies == null) {
            //there was no country with armies
            return null;
        } else {
            d_RandomNeighbour = null;
            for (CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values()) {
                if (this.d_Player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId())) {
                    d_RandomNeighbour = l_neighborCountry;
                    break;
                }
            }
            return d_RandomNeighbour;
        }
    }

    @Override
    protected CountryDetails toMoveFrom() {
        return null;
    }

    /**
     * finds a Random Country owned by player which has armies to be advanced
     *
     * @return country to attack from
     */
    @Override
    protected CountryDetails toAttackFrom() {
        d_RandomCountryWithArmies = null;
        int l_maxArmies = 0, l_numArmies;
        if(this.d_Player.getOwnedCountries().size() != 0){
            for (CountryDetails l_countryDetails : this.d_Player.getOwnedCountries().values()) {
                l_numArmies = l_countryDetails.getNumberOfArmies();
                if (l_numArmies > l_maxArmies) {
                    d_RandomCountryWithArmies = l_countryDetails;
                    return d_RandomCountryWithArmies;
                }
            }
        }
        return d_RandomCountryWithArmies;
    }

    @Override
    protected CountryDetails toAttack() {
        return null;
    }

    /**
     * Gets a random Player for negotiation
     * @return Player
     */
    protected Player getRandomPlayer() {
        Player l_diffPlayer = null;
        boolean t = true;
        while (t) {
            //gets a random Map from Map
            Object[] values = d_Map.getCountries().values().toArray();
            Object randomValue = values[rand.nextInt(values.length)];
            d_RandomCountry = (CountryDetails) randomValue;
            if (!d_Player.getOwnedCountries().containsValue(d_RandomCountry) && d_RandomCountry.getOwnerPlayer() != null) {
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
     *
     * @return random Order
     */
    @Override
    public Order createOrder() {
        l_attackingCountry = toAttackFrom();
        l_defendingCountry = toAttack(l_attackingCountry);
        l_advanceCountry = toAdvance(l_attackingCountry);

        int rndOrder = rand.nextInt(7);
        int rnd_num_of_armies_pool = d_Player.getOwnedArmies();
        switch (rndOrder) {
            case (0):
                if(d_Player.getOwnedArmies() != 0) {
                    d_Player.setOwnedArmies(0);
                    CountryDetails ex = findRandomCountry();
                    if(ex != null){
                        return new Deploy(d_Player, ex.getCountryId(), rnd_num_of_armies_pool);
                    }else
                        return null;
                }else{
                    return null;
                }


            case (1):
                if (l_defendingCountry != null)
                    return new Advance(d_Player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(), rand.nextInt(l_attackingCountry.getNumberOfArmies()), l_defendingCountry.getOwnerPlayer());
                else
                    System.out.println("Neighbor does not exist for this country or Source Country doesn't have Armies");
                return null;


            case (2):
                if (l_advanceCountry != null)
                    return new Advance(d_Player, l_attackingCountry.getCountryId(), l_advanceCountry.getCountryId(), rand.nextInt(l_attackingCountry.getNumberOfArmies()), l_advanceCountry.getOwnerPlayer());
                else
                    System.out.println("Neighbor does not exist for this country or Source Country doesn't have Armies");
                return null;


            case (3):
                if (d_Player.doesCardExists("Blockade")) {
                    d_Player.removeCard("Blockade");
                    return new Blockade(d_Player, findRandomCountry().getCountryId());
                } else
                    System.out.println("Player doesn't own Card Blockade");
                return null;


            case (4):
                if(l_attackingCountry != null){
                    CountryDetails findOther = findOtherRandomCountry();
                    if (d_Player.doesCardExists("Airlift") && findOther != null) {
                        d_Player.removeCard("Airlift");
                        return new Airlift(d_Player, l_attackingCountry.getCountryId(), findOther.getCountryId(), rand.nextInt(l_attackingCountry.getNumberOfArmies()));
                    }
                }
                 else
                    System.out.println("Player doesn't own Card Airlift or Source Country has no Armies to Move");
                return null;


            case (5):
                if (d_Player.doesCardExists("Diplomacy")) {
                    d_Player.removeCard("Diplomacy");
                    return new Diplomacy(d_Player, getRandomPlayer());
                } else
                    System.out.println("Player doesn't own Card Diplomacy");
                return null;


            default:
                CountryDetails tmp = targetCountryNeighbour(l_attackingCountry);
                if (d_Player.doesCardExists("Bomb") && l_attackingCountry != null && tmp != null) {
                    d_Player.removeCard("Bomb");
                    return new Bomb(d_Player, tmp.getOwnerPlayer(), tmp.getCountryId());
                } else
                    System.out.println("Player doesn't own Card Bomb");
                return null;
            }
        }
}