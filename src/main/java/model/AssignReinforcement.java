package model;

/**
 * Handles tasks related to Assign_Reinforcement Phase which assigns armies according to WarZone rules.
 *
 */
public class AssignReinforcement {
    /**
     * Method assigns to each player the correct number of reinforcement armies according to the Warzone rules.
     * @param p_player current player
     * @return true if successfully assigned, else false
     */
    public static boolean assignReinforcementArmies(Player p_player){
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
        return true;
    }

    /**
     * deploy function allows Player to Place Armies by reducing the number of armies in the
     * player’s reinforcement pool
     * @param player tracks current Player
     * @param countryId armies deployed here
     * @param num unit of armies to be deployed
     * @return true if successfully deployed, else false
     */
    public boolean deploy(Player player, String countryId, int num){

        return false;
    }
}
