package model;

import java.util.HashMap;


/**
 * CheaterPlayerStrategy class is ConcreteStrategy.
 * This strategy focuses on player who does illegal moves for its benefits.
 * @author yashs
 */
public class CheaterPlayer extends PlayerStrategy{
	/**
     * Represents object of  RunGameEngine class
     */
    RunGameEngine d_RnGe;
  
    
    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     * @param p_player player that will be cheater player
     * @param p_map map that is going to be loaded.
     */
    public CheaterPlayer(Player p_player, GameMap p_map) {
		super(p_player, p_map);
		d_RnGe = new RunGameEngine();
		
	}

    /**
     * Reinforcement phase for cheater player
     * @param p_game Represents the state of the game
     * @param p_countryName Reinforce armies here
     * @param p_num Reinforce this many armies
     * @return true if reinforcement is successful
     */
    public boolean reinforce(GameData p_game, String p_countryName, int p_num){
        p_game.setActivePlayer(this.d_Player);
        for(CountryDetails c : this.d_Player.getOwnedCountries().values()){
            p_game.getLogger().info(this.d_Player.getPlayerName() + " reinforced " + c.getCountryId() + " with " + c.getNumberOfArmies());
            c.setNumberOfArmies(c.getNumberOfArmies() * 2);
        }
        return true;
    }
    
	@Override
	public Order createOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CountryDetails toAttackFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CountryDetails toAttack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CountryDetails toMoveFrom() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
