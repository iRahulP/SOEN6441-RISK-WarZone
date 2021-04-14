package model;

import java.util.Random;

/**
 * AggresivePlayerStrategy class is ConcreteStrategy.
 * This strategy focuses on centralization of forces and then attacks.
 * @author  Aarthi
 */
public class AggresivePlayer extends PlayerStrategy{
    private int d_OrderVal,d_maxArmies;
    CountryDetails d_StrongestCountry,d_DefendingCountry,d_MoveFromCountry;

    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    public AggresivePlayer(Player p_player, GameMap p_map) {
        super(p_player, p_map);
        d_StrongestCountry = null;
        d_DefendingCountry = null;
        d_maxArmies = 0;
    }


    /**
     * Finds country with highest number of armies owned by player
     * @return strongest CountryDetails object
     */
    private void findStrongestCountryDetails()
    {
        int l_maxArmies = 0, l_numArmies;
        for(CountryDetails l_countryDetails : this.d_Player.getOwnedCountries().values()) {
            l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies > l_maxArmies) {
                l_maxArmies = l_numArmies;
                d_StrongestCountry = l_countryDetails;
            }
        }
    }

    /**
     *toAttack function implements attacks to the
     * neighboring country from strongest country
     * @return country to attack
     */
    protected CountryDetails toAttack()
    {
        for(CountryDetails l_neighborCountry : d_StrongestCountry.getNeighbours().values())
        {
            if(!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
            {
                d_DefendingCountry = l_neighborCountry;
                return  d_DefendingCountry;
            }
        }

        //if no neighbors found to attack
        return null;
    }

    /**
     * This function finds the strongest country from where the attack
     * takes place.
     * @return country to attack from
     */
    protected CountryDetails toAttackFrom()
    {
        findStrongestCountryDetails();
        return d_StrongestCountry;
    }

    /**
     * This function moves the armies to the strongest country
     *to centralize the forces.
     * @return source country for advance
     */
    protected CountryDetails toMoveFrom()
    {
        int l_maxArmies =0;
        findStrongestCountryDetails();

        for(CountryDetails l_neighborCountry : d_StrongestCountry.getNeighbours().values())
        {
            if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
            {
                int l_currentCountryArmies = l_neighborCountry.getNumberOfArmies();
                if(l_currentCountryArmies > l_maxArmies) {
                    l_maxArmies = l_currentCountryArmies;
                    d_MoveFromCountry = l_neighborCountry;
                    d_maxArmies = l_maxArmies;
                }
            }
        }

        if(l_maxArmies == 0)
            return null;
        else
            return d_MoveFromCountry;
    }

    /**
     *  This function  sets  the randomOrderValue which can be 0,1,2
     *  These values 0,1,2 will be used for creating orders
     */
    public void setRandomOrderValue()
    {
        Random l_random =new Random();
        d_OrderVal = l_random.nextInt(3);
    }

    @Override
    public Order createOrder() {

        CountryDetails l_attackingCountry,l_defendingCountry,l_moveFromCountry;
        l_attackingCountry = toAttackFrom(); //strongest country
        l_defendingCountry = toAttack();     //neighbor country not belonging to player
        l_moveFromCountry  = toMoveFrom();   //neighbor of strongest country with max armies

        Random l_random = new Random();
        setRandomOrderValue();
        switch(d_OrderVal) {
            case 0:
                int l_reinforceArmies = l_random.nextInt(d_Player.getOwnedArmies());
                if (l_reinforceArmies != 0) {
                    return new Deploy(d_Player, d_StrongestCountry.getCountryId(), l_random.nextInt(20));
                }
                else
                    System.out.println("Invalid value for deploying reinforcement armies");
                return null;

            case 1:
                //create attack Order
                if(l_defendingCountry!=null) {
                    return new Advance(d_Player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(), l_random.nextInt(l_attackingCountry.getNumberOfArmies()), d_DefendingCountry.getOwnerPlayer());
                }
                else
                    System.out.println("Neighbor does not exist for this country");
                return null;


            case 2:
                //move maximum armies from one country to strongest country
                if(l_moveFromCountry!=null)
                    return new Advance(d_Player,l_moveFromCountry.getCountryId(),l_attackingCountry.getCountryId(),d_maxArmies,l_attackingCountry.getOwnerPlayer());
                else
                    return null;
        }
        return null;
    }

}
