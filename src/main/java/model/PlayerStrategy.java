package model;

/**
 * PlayerStrategy acts as Strategy
 */
public abstract class PlayerStrategy {
    GameMap d_map;
    Player d_player;

    /**
     * default Constructor for PlayerStrategy
     * @param p_player player object
     * @param p_map map object
     */
    PlayerStrategy(Player p_player, GameMap p_map){
        d_player = p_player;
        d_map = p_map;
    }
    /**
     * method to create Orders based on specific Strategy for player
     */
    public abstract Order createOrder();
}
