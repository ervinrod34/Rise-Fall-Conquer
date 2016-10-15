package factions;

import map.Map;
import map.Tile;
/**
 * Used to manage the unit, resources, and tiles owned by a player
 * 
 * @author Porter
 *
 */
public class PlayerFaction extends Faction{

	public PlayerFaction(int id, Tile homeTile, Map m) {
		super(id, homeTile, m);
	}

	/**
	 * Claims the specified tile for the player and turns on the light for the given tile
	 * @param tile
	 */
	@Override
	public void claimTile(Tile tile){
		super.claimTile(tile);
		tile.setLight(true);
		tile.setLightRadius(3);
//		for(Tile t : mBoard.getTilesInRange(tile, 2, null)){
//			t.setLight(true);
//		}
	}
	/**
	 * Unclaims the specified tile for the player and turns off the light for the given tile
	 * @param tile
	 */
	@Override
	public void unclaimTile(Tile tile){
		super.unclaimTile(tile);
		tile.setLight(false);
//		for(Tile t : mBoard.getTilesInRange(tile, 2, null)){
//			t.setLight(false);
//		}
	}
}
