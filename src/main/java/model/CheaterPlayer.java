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
    Player d_OtherPlayer;
    
    /**
     * Creates a player with the argument player name and sets default value for rest of the player fields.
     * @param p_player player that will be cheater player
     * @param p_map map that is going to be loaded.
     */
    public CheaterPlayer(Player p_player, GameMap p_map) {
		super(p_player, p_map);
	}

	@Override
	public Order createOrder() {

		this.d_Player.setOwnedArmies(0);

		HashMap<String, CountryDetails> l_countryList = new HashMap<String, CountryDetails>();
        for(String l_s : this.d_Player.getOwnedCountries().keySet()){
            l_countryList.put(l_s, this.d_Player.getOwnedCountries().get(l_s));
        }
        
        //conquering the enemy neighbor
        for(CountryDetails l_countries : l_countryList.values()){
            for(CountryDetails l_neighbours : l_countries.getNeighbours().values()){
            	if(!this.d_Player.getOwnedCountries().containsKey(l_neighbours.getCountryId().toLowerCase())) {
            		d_OtherPlayer= l_neighbours.getOwnerPlayer();
            		this.d_Player.getOwnedCountries().put(l_neighbours.getCountryId().toLowerCase(),l_neighbours);
            		d_OtherPlayer.getOwnedCountries().remove(l_neighbours.getCountryId().toLowerCase());
            		d_Player.addCard();
            		l_neighbours.setOwnerPlayer(this.d_Player);
            		
            		//if player owns all the countries
            		/*if(this.d_Player.getOwnedCountries().size() == d_Map.getCountries().size()) {
            			System.out.println(d_Player+ " has won the game");
            			System.exit(0);
            		}*/
            	}
            }
        }
        
        //double the armies that have neighbor enemy country
		for(CountryDetails l_newcountries : this.d_Player.getOwnedCountries().values()){
            for(CountryDetails l_newneighbours : l_newcountries.getNeighbours().values()){
            	//if(!(l_newneighbours.getOwnerPlayer().equals(d_Player))) {
            	if(!(this.d_Player.getOwnedCountries().containsKey(l_newneighbours.getCountryId().toLowerCase()))) {
            		l_newcountries.setNumberOfArmies(l_newcountries.getNumberOfArmies() * 2);
            	}
            }
        }
		
		return null;
	}

	@Override
	protected CountryDetails toAttackFrom() {
		return null;
	}

	@Override
	protected CountryDetails toAttack() {
		return null;
	}

	@Override
	protected CountryDetails toMoveFrom() {
		return null;
	}
    
    
}
