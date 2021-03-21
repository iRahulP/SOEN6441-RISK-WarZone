package model;

public class Airlift implements Order{
    private int d_NumArmies;
    private String d_SourceCountryId;
    private String d_TargetCountryId;
    private Player d_Player;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_player current player issuing deploy order
     * @param p_sourceCountryId country from which armies will be airlifted
     * @param p_targetCountryId country to which armies will be deployed
     * @param p_numArmies total armies which will be airlifted
     */
    public Airlift(Player p_player,String p_sourceCountryId, String p_targetCountryId, int p_numArmies) {
        d_Player = p_player;
        d_SourceCountryId = p_sourceCountryId;
        d_TargetCountryId = p_targetCountryId;
        d_NumArmies = p_numArmies;
    }

    /**
     * method which enacts the order
     * @return true if successful, else false
     */
    @Override
    public boolean execute() {

        CountryDetails l_cSource= d_Player.getOwnedCountries().get(d_SourceCountryId.toLowerCase());
        int l_existingSourceArmies = l_cSource.getNumberOfArmies();
        l_existingSourceArmies -= d_NumArmies;
        l_cSource.setNumberOfArmies(l_existingSourceArmies);

        CountryDetails l_cTarget= d_Player.getOwnedCountries().get(d_TargetCountryId.toLowerCase());
        int l_existingTargetArmies = l_cTarget.getNumberOfArmies();
        l_existingTargetArmies += d_NumArmies;
        l_cTarget.setNumberOfArmies(l_existingTargetArmies);

        return true;
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
     * Getter for ID of Source Country
     * @return d_SourceCountryId
     */
    public String getD_SourceCountryId() { return d_SourceCountryId; }

    /**
     * Setter for ID of Source Country
     * @param d_SourceCountryId country ID
     */
    public void setD_SourceCountryId(String d_SourceCountryId) { this.d_SourceCountryId = d_SourceCountryId; }

    /**
     * Getter for ID of Target Country
     * @return d_SourceCountryId
     */
    public String getD_TargetCountryId() { return d_TargetCountryId; }

    /**
     * Setter for ID of Target Country
     * @param d_TargetCountryId country ID
     */
    public void setD_TargetCountryId(String d_TargetCountryId) { this.d_TargetCountryId = d_TargetCountryId; }

    /**
     * Setter for number of armies
     * @return d_numArmies
     */
    public int getD_numArmies() {
        return d_NumArmies;
    }

    /**
     * Setter for number of armies
     * @param d_numArmies number of armies
     */
    public void setD_numArmies(int d_numArmies) {
        this.d_NumArmies = d_numArmies;
    }
}
