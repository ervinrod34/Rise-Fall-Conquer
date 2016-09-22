package map;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Generates a grid map, and handles populating it with resources and other items
 * 
 * @author Porter
 *
 */
public class Map {

	//Values for map size
	private static final int XSIZE = 50;
	private static final int YSIZE = 50;
	
	//Values for the tile sizes
	private static final int TILEWIDTH = 54;
	private static final int TILEHEIGHT = 63;
	
	private Tile [][] grid;
	
	private BitmapFont Font;
	
	/**
	 * Creates the map where the game will take place 
	 */
	public Map() {
		this.createGrid();
		Font = new BitmapFont();
		Font.getData().setScale(0.5f, 0.5f);
		Font.setColor(Color.BLACK);
	}
	
	/**
	 * Creates the grid
	 */
	private void createGrid(){
		grid = new Tile [XSIZE][YSIZE];
		for(int x = 0; x < XSIZE; x++){
			for(int y = 0; y < YSIZE; y++){
				//Make random tiles
				TileID randType = TileID.getRandomTileID();
				//Shift odd rows of hexagon's to the right
				if(y % 2 == 1){
					grid[x][y] = new Tile(randType, x*TILEWIDTH + TILEWIDTH/2, y*(TILEHEIGHT * 3/4));
				}else{
					grid[x][y] = new Tile(randType, x*TILEWIDTH, y*(TILEHEIGHT * 3/4));
				}
				grid[x][y].setGridLocation(x, y);
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
//				Font.draw(batch, (int)grid[x][y].getGridLocation().x + "," + (int)grid[x][y].getGridLocation().y,
//						grid[x][y].getLocation().x + 10, grid[x][y].getLocation().y + TILEHEIGHT/2 + 5);
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
