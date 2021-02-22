package model;

/**
 * Handles tasks related to Assign_Reinforcement Phase which assigns armies according to WarZone rules.
 *
 */
public class AssignReinforcement {
    /**
     * Method assigns to each player the correct number of reinforcement armies according to the Warzone rules.
     * @param p_player current player
     */
    public static void assignReinforcementArmies(Player p_player){
        int l_totalControlValue = 0;
        int l_totalReinforcementArmies;
        if(p_player.getOwnedCountries().size() >= 9){
            if(p_player.getOwnedContinents().size()> 0){
                for(Continent c:p_player.getOwnedContinents().values()){
                    l_totalControlValue += c.getControlValue();
                }
                l_totalReinforcementArmies = (int)(p_player.getOwnedCountries().size()/3) + l_totalControlValue;
            }
            else{
                l_totalReinforcementArmies = (int)(p_player.getOwnedCountries().size()/3);
            }
        }
        else{
            l_totalReinforcementArmies = 3;
        }
        p_player.setOwnedArmies(l_totalReinforcementArmies);
    }
}

//    /**
//     * deploy function allows Player to Place Armies by reducing the number of armies in the
//     * player’s reinforcement pool
//     * @param p_player tracks current Player
//     * @param p_countryId armies deployed here
//     * @param p_numArmies unit of armies to be deployed
//     * @return true if successfully deployed, else false
//     */
//    public boolean deploy(Player p_player, String p_countryId, int p_numArmies){
//        if(p_player.getOwnedCountries().containsKey(p_countryId.toLowerCase()))
//        {
//            if(p_player.getOwnedArmies() >= p_numArmies)
//            {
//                CountryDetails c= p_player.getOwnedCountries().get(p_countryId.toLowerCase());
//                int existingArmies = c.getNumberOfArmies();
//                existingArmies += p_numArmies;
//                c.setNumberOfArmies(existingArmies);
//                p_player.setOwnedArmies(p_player.getOwnedArmies()-p_numArmies);
//                return true;
//            }
//            else{
//                System.out.println("You don't have enough armies");
//                return false;
//            }
//        }
//        else{
//            System.out.println("You don't own this country");
//            return false;
//        }
//    }
//}
