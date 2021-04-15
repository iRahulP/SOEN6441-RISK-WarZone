package model;

/**
 * Class containing logic for implementation of bomb order
 * @author Rahul
 *
 */
public class Bomb implements Order{
    private String d_CountryId;
    private Player d_Player;
    private Player d_CPlayer;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_player target player where bomb will effect
     * @param p_countryId adjacent opponent country where bomb card will take effect
     * @param p_cplayer souce player who will bomb
     */
    public Bomb(Player p_cplayer, Player p_player,String p_countryId) {
        d_Player = p_player;
        d_CPlayer = p_cplayer;
        d_CountryId = p_countryId;
    }

    /**
     * method which enacts the order
     * @return true if successful, else false
     */
    @Override
    public boolean execute() {
        //Check if Source player negotiating target Player
        if (d_CPlayer.d_NegotiateList.contains(d_Player)) {
            System.out.println(d_CPlayer.getPlayerName() + " has negotiated " + d_Player.getPlayerName());
            //skip execute
            return false;
        }
        CountryDetails l_c = d_Player.getOwnedCountries().get(d_CountryId.toLowerCase());
        if (l_c != null) {
            int l_existingArmies = l_c.getNumberOfArmies();
            double armies = Double.valueOf(l_existingArmies / 2);
            l_c.setNumberOfArmies((int) armies);
            return true;
        }
    return false;
    }

    /**
     * Getter for current player
     * @return d_player
     */
    public Player getD_player() {
        return d_Player;
    }

    /**
     * Setter for current player
     * @param d_player player
     */
    public void setD_player(Player d_player) {
        this.d_Player = d_player;
    }

    /**
     * Getter for ID of Country
     * @return d_countryId
     */
    public String getD_countryId() {
        return d_CountryId;
    }

    /**
     * Setter for ID of Country
     * @param d_countryId country ID
     */
    public void setD_countryId(String d_countryId) {
        this.d_CountryId = d_countryId;
    }

}
