package map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Generates a grid map, and handles populating it with resources and other items
 * 
 * @author Porter
 *
 */
public class Map {

	//Values for map size
	private static final int XSIZE = 20;
	private static final int YSIZE = 20;
	
	//Values for the tile sizes
	private static final int TILEWIDTH = 32;
	private static final int TILEHEIGHT = 32;
	
	private Tile [][] grid;
	
	/**
	 * Creates the map where the game will take place 
	 */
	public Map() {
		this.createGrid();
	}
	
	/**
	 * Creates the grid
	 */
	private void createGrid(){
		grid = new Tile [XSIZE][YSIZE];
		for(int x = 0; x < XSIZE; x++){
			for(int y = 0; y < YSIZE; y++){
				if(x % 4 == 0 && y % 5 == 0){
					grid[x][y] = new Tile(TileID.CASTLE, x*TILEWIDTH, y*TILEHEIGHT);
				}else{
					grid[x][y] = new Tile(TileID.GRASS, x*TILEWIDTH, y*TILEHEIGHT);
				}
			}
		}
	}
	/**
	 * Draws the whole map
	 * 
	 * @param batch
	 */
	public void drawMap(SpriteBatch batch){
		for(int x = 0; x < XSIZE; x++){
			for(int y = 0; y < YSIZE; y++){
				batch.draw(grid[x][y].getImg(), grid[x][y].getLocation().x, grid[x][y].getLocation().y);
			}
		}
	}
	/**
	 * Disposes of all textures in map
	 */
	public void disposeMap(){
		for(int x = 0; x < XSIZE; x++){
			for(int y = 0; y < YSIZE; y++){
				if(grid[x][y].getImg() != null){
					grid[x][y].getImg().dispose();
				}
			}
		}
	}
	
}
