package model;

public class Order {

    private String d_player;
    private int d_numArmies;
    private String d_countryId;

    /**
     * This constructor will initialize the order object with deploy details.
     * @param p_player current player issuing deploy order
     * @param p_countryId country where armies will be deployed
     * @param p_numArmies total armies which will be deployed
     */
    public Order(String p_player,String p_countryId,int p_numArmies) {
        d_player = p_player;
        d_countryId = p_countryId;
        d_numArmies = p_numArmies;
    }

    /**
     * method which enacts the order
     * @return true if successful, else false
     */
    public boolean execute(){

        return true;
    }

}
