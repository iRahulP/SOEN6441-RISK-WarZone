package model;

/**
 * PlayerStrategy acts as Strategy
 */
public abstract class PlayerStrategy {
    GameMap d_Map;
    Player d_Player;

    /**
     * default Constructor for PlayerStrategy
     * @param p_player player object
     * @param p_map map object
     */
    PlayerStrategy(Player p_player, GameMap p_map){
        d_Player = p_player;
        d_Map = p_map;
    }

    /**
     * method to create Orders based on specific Strategy for player
     * @return Order
     */
    public abstract Order createOrder();

    /**
     * method to return the Country from where attack takes place
     * @return Country
     */
    protected abstract CountryDetails toAttackFrom();

    /**
     * method to return the Country on which attack happens
     * @return Country
     */
    protected abstract CountryDetails toAttack();

    /**
     * method to return Country to move armies from
     * @return Country
     */
    protected abstract CountryDetails toMoveFrom();
}
