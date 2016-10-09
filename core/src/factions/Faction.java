package factions;

import java.util.ArrayList;

import map.Tile;
/**
 * Used to manage the unit, resources, and tiles owned by a faction
 * 
 * @author Porter
 *
 */
public class Faction {

	// Unique Id for the faction
	private int Id;
	// Home city for the faction
	private Tile HomeTile;
	// List of claimed tiles by the faction
	private ArrayList<Tile> ClaimedTiles;
	
	public Faction(int id, Tile homeTile) {
		this.Id = id;
		ClaimedTiles = new ArrayList<Tile>();
		this.HomeTile = homeTile;
		this.claimTile(HomeTile);
	}

	/**
	 * Claims the specified tile for the faction
	 * @param tile
	 */
	public void claimTile(Tile tile){
		tile.setClaim(Id);
		ClaimedTiles.add(tile);
	}
	/**
	 * Unclaims the specified tile for the faction
	 * @param tile
	 */
	public void unclaimTile(Tile tile){
		tile.setClaim(0);
		ClaimedTiles.remove(tile);
	}
	public Tile getHomeTile() {
		return HomeTile;
	}

}
