package model;

import java.util.Collection;
import java.util.Random;

public class BenevolentPlayer extends PlayerStrategy{

    CountryDetails d_SourceCountry,d_WeakCountry;

    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    BenevolentPlayer(Player p_player, GameMap p_map) {
        super(p_player, p_map);
        d_SourceCountry = null;
        d_WeakCountry = null;
    }

    private CountryDetails findWeakestCountryDetails() {
        Collection<CountryDetails> l_countries=d_Player.getOwnedCountries().values();
        //CountryDetails l_weakCountry = null;
        int l_minArmies = 100;
        for(CountryDetails l_countryDetails : l_countries) {
            int l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies < l_minArmies) {
                l_minArmies = l_numArmies;
                d_WeakCountry = l_countryDetails;
            }
        }

        return d_WeakCountry;
    }


    @Override
    protected CountryDetails toMoveFrom() {
        findWeakestCountryDetails();
        return d_SourceCountry;
    }

    /**
     * toAdvance function implements advance to the
     * neighboring country from random owned country
     * @return country to advance
     */
    protected CountryDetails toAdvance()
    {
        for(CountryDetails l_neighborCountry : d_SourceCountry.getNeighbours().values())
        {
            if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry) && (l_neighborCountry == d_WeakCountry))
            {
                d_WeakCountry = l_neighborCountry;
                return d_WeakCountry;
            }
        }

        //if no neighbors found to advance
        return null;
    }

    @Override
    public Order createOrder() {

        CountryDetails l_sourceCountry, l_advanceCountry;
        l_sourceCountry = toMoveFrom();
        l_advanceCountry = toAdvance();
        Random l_random = new Random();

        int l_rndOrder = l_random.nextInt(2);

        switch(l_rndOrder) {
            case 0:
                if (l_random.nextInt(5) != 0) {
                    return new Deploy(d_Player, d_WeakCountry.getCountryId(), l_random.nextInt(20));
                }
                break;
            case 1:
                //create attack Order
                if(d_WeakCountry!=null)
                    return new Advance(d_Player, l_sourceCountry.getCountryId(), d_WeakCountry.getCountryId(), l_random.nextInt(6), d_WeakCountry.getOwnerPlayer());
                else
                    System.out.println("Neighbor does not exist for this country");
                break;
        }

        return null;
    }

    @Override
    protected CountryDetails toAttackFrom() {
        return null;
    }

    @Override
    protected CountryDetails toAttack() {
        return null;
    }

}
