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
    RandomPlayer(Player p_player, GameMap p_map) {
        super(p_player, p_map);
        d_RandomCountry = null;
        d_RandomNeighbour = null;
        d_RandomCountryWithArmies = null;
    }


    /**
     * Finds random Country to deploy
     * @return random CountryID
     */
    private CountryDetails findRandomCountryToDeploy()
    {
        Object[] values = d_Player.getOwnedCountries().values().toArray();
        Object randomValue = values[rand.nextInt(values.length)];
        d_RandomCountry = (CountryDetails)randomValue;
        return d_RandomCountry;
    }

    /**
     * toAttack function implements attacks to the
     * neighboring country from random country
     * @return country to attack
     */
    protected CountryDetails toAttack()
    {
        for(CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values())
        {
            if(!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
            {
                d_RandomNeighbour = l_neighborCountry;
                return  d_RandomNeighbour;
            }
        }

        //if no neighbors found to attack
        return null;
    }

    /**
     * toAdvance function implements advance to the
     * neighboring country from random owned country
     * @return country to advance
     */
    protected CountryDetails toAdvance()
    {
        for(CountryDetails l_neighborCountry : d_RandomCountryWithArmies.getNeighbours().values())
        {
            if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
            {
                d_RandomNeighbour = l_neighborCountry;
                return  d_RandomNeighbour;
            }
        }

        //if no neighbors found to advance
        return null;
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
     * Creates a Random Order as per Strategy
     * @return random Order
     */
    @Override
    public Order createOrder() {
        CountryDetails l_attackingCountry,l_defendingCountry,l_advanceCountry;
        l_attackingCountry = toAttackFrom();
        l_defendingCountry = toAttack();
        l_advanceCountry = toAdvance();

        int rndOrder = rand.nextInt(3);
        int rnd_num_of_armies_pool = d_Player.getOwnedArmies();
        //returns one of the orders randomly with a Probbility of 80% else null
        if (rand.nextInt(5) != 0) {
            switch (rndOrder) {
                case (0):
                    return new Deploy(d_Player, findRandomCountryToDeploy().getCountryId(), rnd_num_of_armies_pool);
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
            }
    }
        return null;
    }
}
