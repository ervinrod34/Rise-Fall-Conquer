package factions;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import map.Map;
import map.Tile;
import map.TileID;
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
	
	private Color cTerritory;
	
	private Map mBoard;
	
	public Faction(int id, Tile homeTile, Map m, Color c) {
		this.Id = id;
		this.mBoard = m;
		ClaimedTiles = new ArrayList<Tile>();
		this.HomeTile = homeTile;
		this.claimTile(HomeTile);
		cTerritory = c;
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
	
	/**
	 * Draws the territory own by this faction
	 * 
	 * @param batch
	 */
	public void drawTerritory(SpriteBatch batch){
		for(Tile tile : ClaimedTiles){
			Color c = batch.getColor();
			batch.setColor(cTerritory);
			batch.draw(TileID.TERRITORY.getImg(), tile.getLocation().x, tile.getLocation().y);
			batch.setColor(c);
		}
	}
	public Tile getHomeTile() {
		return HomeTile;
	}

}
