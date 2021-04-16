package model;


import java.util.Random;

/**
 * AggresivePlayerStrategy class is ConcreteStrategy.
 * This strategy focuses on centralization of forces and then attacks.
 * @author  Aarthi
 */
public class AggresivePlayer extends PlayerStrategy{
    private int d_OrderVal,d_MaxArmies;
    CountryDetails d_StrongestCountry,d_DefendingCountry,d_MoveFromCountry,d_InitialCountry;
    boolean d_IsTest;
    public int d_TestReinforceArmies;

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
        d_InitialCountry = null;
        d_MaxArmies = 0;
        d_IsTest = false;
    }


    /**
     * Finds country with highest number of armies owned by player
     * strongest CountryDetails object
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

        if(l_maxArmies == 0)
            d_StrongestCountry = findInitialCountry();
    }

    /**
     *toAttack function implements attacks to the
     * neighboring country from strongest country
     * @return country to attack
     */
    protected CountryDetails toAttack()
    {
        if(d_StrongestCountry!=null) {
            for (CountryDetails l_neighborCountry : d_StrongestCountry.getNeighbours().values()) {
                if (!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry)) {
                    d_DefendingCountry = l_neighborCountry;
                    return d_DefendingCountry;
                }
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

        if(d_StrongestCountry!=null) {
            for (CountryDetails l_neighborCountry : d_StrongestCountry.getNeighbours().values()) {
                if (this.d_Player.getOwnedCountries().containsKey(l_neighborCountry)) {
                    int l_currentCountryArmies = l_neighborCountry.getNumberOfArmies();
                    if (l_currentCountryArmies >= l_maxArmies) {
                        l_maxArmies = l_currentCountryArmies;
                        d_MoveFromCountry = l_neighborCountry;
                        d_MaxArmies = l_maxArmies;
                    }
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

    /**
     * Sets the random value for tests
     * @param p_random input random value
     */
    public void setTestOrderValue(int p_random)
    {
        d_OrderVal = p_random;
    }

    /**
     * Sets the d_IsTest value to true in testcases
     * @param p_isTest input boolean value
     */
    public void isTest(boolean p_isTest)
    {
        d_IsTest = p_isTest;
    }

    /**
     * Finds the initial country where armies can be deployed.
     * @return Initial random country
     */
    private CountryDetails findInitialCountry() {
        for(CountryDetails l_country : this.d_Player.getOwnedCountries().values())
        {
            for (CountryDetails l_neighborCountry : l_country.getNeighbours().values()) {
                if(!this.d_Player.getOwnedCountries().containsKey(l_neighborCountry)){
                    d_InitialCountry = l_country;
                    return d_InitialCountry;
                }
            }

        }
        return d_InitialCountry;
    }

    @Override
    public Order createOrder() {

        CountryDetails l_attackingCountry,l_defendingCountry,l_moveFromCountry;
        l_attackingCountry = toAttackFrom(); //strongest country
        l_defendingCountry = toAttack();     //neighbor country not belonging to player
        l_moveFromCountry  = toMoveFrom();   //neighbor of strongest country with max armies

        Random l_random = new Random();

        if(!d_IsTest)
            setRandomOrderValue();

        switch(d_OrderVal) {
            case 0:
                if(d_Player.getOwnedArmies() == 0)
                {
                    System.out.println("Reinforcement armies are all used by player ");
                    break;
                }
                int l_reinforceArmies = l_random.nextInt(d_Player.getOwnedArmies());
                d_TestReinforceArmies = l_reinforceArmies;
                if(d_Player.getOwnedArmies() == 1)
                    l_reinforceArmies = 1;
                if (l_reinforceArmies != 0) {
                    if(d_StrongestCountry!=null) {
                        System.out.println("Armies deployed on country :" + d_StrongestCountry.getCountryId() + " " + l_reinforceArmies);
                        d_Player.setOwnedArmies(d_Player.getOwnedArmies() - l_reinforceArmies);
                        return new Deploy(d_Player, d_StrongestCountry.getCountryId(), l_reinforceArmies);
                    }else{
                        findInitialCountry();
                        if(d_InitialCountry!=null) {
                            d_StrongestCountry = d_InitialCountry;
                            System.out.println("Armies deployed on country :" + d_InitialCountry.getCountryId() + " " + l_reinforceArmies);
                            d_Player.setOwnedArmies(d_Player.getOwnedArmies() - l_reinforceArmies);
                            return new Deploy(d_Player, d_InitialCountry.getCountryId(), l_reinforceArmies);
                        }
                    }
                }
                else
                    System.out.println("Invalid value for deploying reinforcement armies : "+l_reinforceArmies);
                return null;

            case 1:
                //create attack Order
                if(l_attackingCountry.getNumberOfArmies() == 0)
                {
                    System.out.println("The number of armies in strongest country is 0 ,deploy before advance");
                    return null;
                }
                if(l_defendingCountry!=null) {

                    //check if a player has Bomb card.
                    //If yes randomly it can be used otherwise attack order takes place
                    if(d_Player.doesCardExists("Bomb")) {
                        Random l_randomCard = new Random();
                        int l_value = l_randomCard.nextInt(2);
                        if (l_value == 0) {
                            d_Player.removeCard("Bomb");
                            return new Bomb(d_Player, l_defendingCountry.getOwnerPlayer(), l_defendingCountry.getCountryId());
                        }
                        else
                            break;
                    }
                    int l_randomVal;
                    if(l_attackingCountry.getNumberOfArmies() > 0)
                        l_randomVal = l_random.nextInt(l_attackingCountry.getNumberOfArmies());
                    else
                        return null;
                    if(l_randomVal!=0)
                        return new Advance(d_Player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(),l_randomVal , d_DefendingCountry.getOwnerPlayer());
                    else
                        return null;
                }
                else
                    System.out.println("Neighbor does not exist for this country :"+ l_attackingCountry.getCountryId());
                return null;


            case 2:
                //move maximum armies from one country to strongest country
                if(l_moveFromCountry!=null) {
                    //check if a player has Airlift card.
                    //If yes randomly it can be used otherwise advance order takes place
                    if(d_Player.doesCardExists("Airlift")) {
                        Random l_randomCard = new Random();
                        int l_value = l_randomCard.nextInt(2);
                        if (l_value == 0) {
                            d_Player.removeCard("Airlift");
                            return new Airlift(d_Player, l_moveFromCountry.getCountryId(), l_attackingCountry.getCountryId(), d_MaxArmies);
                        } else
                            break;
                    }
                    return new Advance(d_Player, l_moveFromCountry.getCountryId(), l_attackingCountry.getCountryId(), d_MaxArmies, l_attackingCountry.getOwnerPlayer());
                } else
                    return null;


        }
        return null;
    }

}
