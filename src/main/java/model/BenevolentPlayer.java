package model;

public class BenevolentPlayer extends PlayerStrategy{

    CountryDetails d_AttackingCountry,d_DefendingCountry;
    /**
     * default Constructor for PlayerStrategy
     *
     * @param p_player player object
     * @param p_map    map object
     */
    BenevolentPlayer(Player p_player, GameMap p_map) {
        super(p_player, p_map);
        d_AttackingCountry = null;
        d_DefendingCountry = null;
    }

    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    protected CountryDetails toAttack() {
        for(CountryDetails l_neighborCountry : d_AttackingCountry.getNeighbours().values())
        {
            if(this.d_Player.getOwnedCountries().containsKey(l_neighborCountry))
            {
                d_DefendingCountry = l_neighborCountry;
                return  d_DefendingCountry;
            }
        }

        //if no neighbors found to attack
        return null;
    }

    @Override
    protected CountryDetails toAttackFrom() {
        findWeakestCountryDetails();
        return d_AttackingCountry;
    }

    private void findWeakestCountryDetails() {
        int l_minArmies = 0;
        int l_numArmies;
        for(CountryDetails l_countryDetails : this.d_Player.getOwnedCountries().values()) {
            l_numArmies = l_countryDetails.getNumberOfArmies();
            if( l_numArmies < l_minArmies) {
                l_minArmies = l_numArmies;
                d_DefendingCountry = l_countryDetails;
            }
        }
    }

    @Override
    protected CountryDetails toMoveFrom() {

        return null;
    }
}
