package factions;

import map.Tile;
/**
 * Used to manage the unit, resources, and tiles owned by a player
 * 
 * @author Porter
 *
 */
public class PlayerFaction extends Faction{

	public PlayerFaction(int id, Tile homeTile) {
		super(id, homeTile);
	}

	/**
	 * Claims the specified tile for the player and turns on the light for the given tile
	 * @param tile
	 */
	@Override
	public void claimTile(Tile tile){
		super.claimTile(tile);
		tile.setLight(true);
	}
	/**
	 * Unclaims the specified tile for the player and turns off the light for the given tile
	 * @param tile
	 */
	@Override
	public void unclaimTile(Tile tile){
		super.unclaimTile(tile);
		tile.setLight(false);
	}
}
