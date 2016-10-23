package map;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.mygdx.game.MyGdxGame;

import box2dLight.RayHandler;
import factions.Faction;
import tools.BasicAnimation;
import tools.BasicAnimationID;

/**
 * Generates a grid map, and handles populating it with resources and other
 * items
 * 
 * @author Porter
 *
 */
public class Map {

	public enum Directions{
		LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT;
	}
	// Values for map size
	private static final int XSIZE = 50;
	private static final int YSIZE = 50;

	// Values for the tile sizes
	private static final int TILEWIDTH = 54;
	private static final int TILEHEIGHT = 63;
	private static final int X_OFFSET = (1280/2)*12;
	private static final int Y_OFFSET = (720/2)*12;
	
	// Map width and height
	private int mWidth = XSIZE*TILEWIDTH;
	private int mHeight = YSIZE*TILEHEIGHT;
	
	private Tile[][] grid;
	private BitmapFont Font;

	// Light manager 
	private RayHandler rayHandler;
	
	/**
	 * Creates the map where the game will take place
	 */
	public Map(OrthographicCamera oGameCam, String filename) {
		this.createLighting(oGameCam);
		this.createGrid(filename);
		Font = new BitmapFont();
		Font.getData().setScale(0.5f, 0.5f);
		Font.setColor(Color.BLACK);
	}
	
	/**
	 * Get the width of the map
	 * @return
	 */
	public int getmWidth() {
		return mWidth;
	}

	/**
	 * Get the height of the map
	 */
	public int getmHeight() {
		return mHeight;
	}


	/**
	 * Sets the lighting for the map
	 * @param oGameCam
	 */
	private void createLighting(OrthographicCamera oGameCam){
		World world = new World(new Vector2(0,0),false);
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(oGameCam);
		rayHandler.setAmbientLight((1/(float)255)*5f, (1/(float)255)*5f, (1/(float)255)*5f, 1);
		RayHandler.useDiffuseLight(true);
	}
	
	/**
	 * Gives a random home tile on the map
	 * 
	 * @return
	 */
	public Tile getRandomHomeTile(){
		ArrayList<Tile> HomeTiles = new ArrayList<Tile>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if(grid[x][y].getResource() != null && grid[x][y].getResourceID() == ResourceID.HOME && grid[x][y].getClaim() == 0){
					HomeTiles.add(grid[x][y]);
				}
			}
		}
		if(HomeTiles.size() != 0){
			Random rand = new Random();
			return HomeTiles.get(rand.nextInt(HomeTiles.size()));
		}
		return null;
	}
	/**
	 * Reads in the map.json file
	 * 
	 * @param filepath
	 * @return
	 */
	private ArrayList<Tiled_Layer> readTiledJson(String filepath) {
		Json json = new Json();
		JsonValue root = null;
		String path = MyGdxGame.ASSET_PATH + "maps" + File.separator + filepath;
		try{
			root = json.fromJson(null,Gdx.files.internal(path));
		}catch(GdxRuntimeException e){
			Gdx.app.error(this.getClass().getName(), "Could not load texture: " + Gdx.files.internal(path).file().getAbsolutePath());
		}
		//JsonValue root = json.fromJson(null, Gdx.files.internal(filepath));
		JsonValue layers = root.get("layers");
		String tmpJson = layers.toJson(OutputType.json);
		@SuppressWarnings("unchecked")
		ArrayList<JsonValue> jList = json.fromJson(ArrayList.class, tmpJson);
		ArrayList<Tiled_Layer> tList = new ArrayList<Tiled_Layer>();
		for(JsonValue j : jList){
			tmpJson = j.toJson(OutputType.json);
			tList.add(json.fromJson(Tiled_Layer.class, tmpJson));
		}
		return tList;
	}

	/**
	 * Creates the grid
	 */
	private void createGrid(String filename) {
		ArrayList<Tiled_Layer> tLayers = readTiledJson(filename);
		int[][] data = tLayers.get(0).getmapData();
		int[][] rdata = tLayers.get(1).getmapData();
		int xSize = data[0].length;
		int ySize = data.length;
		grid = new Tile[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				// Shift odd rows of hexagon's to the right
				if (y % 2 == 1) {
					grid[x][y] = new Tile(data[x][y], rdata[x][y], x * TILEWIDTH + TILEWIDTH / 2, y * (TILEHEIGHT * 3 / 4), rayHandler);
				} else {
					grid[x][y] = new Tile(data[x][y], rdata[x][y], x * TILEWIDTH, y * (TILEHEIGHT * 3 / 4), rayHandler);
				}
				grid[x][y].setGridLocation(x, y);
			}
		}
	}

	/**
	 * Draws the lighting for the map
	 * 
	 * @param oGameCam
	 */
	public void drawMapLighting(OrthographicCamera oGameCam){
		rayHandler.setCombinedMatrix(oGameCam);
		rayHandler.updateAndRender();
	}
	
	/**
	 * Draws the MiniMap without Markers
	 * 
	 * @param batch
	 */
	public void drawMiniMap(SpriteBatch batch) {
		//loop through the whole grid
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				//draw the tiles
				batch.draw(grid[x][y].getTileImg()
						, (grid[x][y].getLocation().x)- X_OFFSET
						, (grid[x][y].getLocation().y)- Y_OFFSET );
				
//				if(grid[x][y].getResource() != null){
//						batch.draw(grid[x][y].getResourceImg()
//								, grid[x][y].getLocation().x - X_OFFSET
//								, grid[x][y].getLocation().y - Y_OFFSET);
//				}
			}
		}
	}
	
	/**
	 * Draws markers on the MiniMap (more visible)
	 * @param batch
	 */
	public void drawMarkers(ShapeRenderer sh, ArrayList<Faction> list) {	
		//loop through the factions
		for(Faction f : list) {
			//get the color of that faction's territory
			sh.setColor(f.getcTerritory());
			//draw the marker
			sh.rect(f.getHomeTile().getLocation().x - X_OFFSET
					, f.getHomeTile().getLocation().y - Y_OFFSET
					,100,100);
		}
	}
	
	/**
	 * This only draws tiles within the viewport
	 * @param batch
	 * @param oGameCam
	 */
	public void drawView(SpriteBatch batch, OrthographicCamera oGameCam) {

		// scale viewport according to zoom
		float width = oGameCam.viewportWidth * oGameCam.zoom;
		float height = oGameCam.viewportHeight * oGameCam.zoom;

		// this prevents us from seeing tiles load in
		width += (TILEWIDTH * 2);
		height += (TILEHEIGHT * 2);

		// makes a vector3 that moves the center of camera to the bottom left of
		// viewport
		Vector3 camPos = new Vector3(oGameCam.position.x - width / 2, oGameCam.position.y - height / 2, 0);

		// make a rectangle based on above vector3
		Rectangle camRect = new Rectangle(camPos.x, camPos.y, width, height);

		ArrayList<BasicAnimation> animations = new ArrayList<BasicAnimation>();
		// loop through the tile grid
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {

				// make a rectangle based on a given tile from the grid
				Rectangle tileRect = new Rectangle(grid[x][y].getLocation().x, grid[x][y].getLocation().y,
						grid[x][y].getTileImg().getWidth(), grid[x][y].getTileImg().getHeight());

				// check if tile is in viewport
				if (camRect.contains(tileRect)) {
					batch.draw(grid[x][y].getTileImg(), grid[x][y].getLocation().x, grid[x][y].getLocation().y);

					// if there's a resource, draw it
					if (grid[x][y].getResource() != null) {
						batch.draw(grid[x][y].getResourceImg(), grid[x][y].getLocation().x, grid[x][y].getLocation().y);
					}
					
					// if there's a animation, draw it
					if (grid[x][y].getbAnimation() != null) {
						animations.add(grid[x][y].getbAnimation());
						//grid[x][y].getbAnimation().draw(batch);
					}
				}
			}
		}
		
		for(BasicAnimation a : animations){
			a.draw(batch);
		}
	}

	/**
	 * Disposes of all textures in map
	 */
	public void disposeMap() {
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				if (grid[x][y].getTileImg() != null) {
					grid[x][y].getTileImg().dispose();
				}
				if (grid[x][y].getResourceImg() != null) {
					grid[x][y].getResourceImg().dispose();
				}
			}
		}
	}
	public RayHandler getrayHandler(){
		return rayHandler;
	}
	
	public boolean getClickedTile(Vector2 xyz) {
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				if(grid[x][y].containsPoint(xyz)) {
					grid[x][y].setLight(true);
					grid[x][y].setbAnimation(BasicAnimationID.PARTICLE_SPARKS);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Gives the clicked tile
	 * 
	 * @param xyz
	 * @return
	 */
	public Tile getClickedTile2(Vector2 xyz) {
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				if(grid[x][y].containsPoint(xyz)) {
					return grid[x][y];
				}
			}
		}
		return null;
	}
	
	/**
	 * Get all neighbor tiles for the given tile 
	 * @param t
	 * @return
	 */
	public ArrayList<Tile> getNeighborTiles(Tile t){
		ArrayList<Tile> list = new ArrayList<Tile>();
		for(Directions d : Directions.values()){
			Tile t2 = getNeighborTile(t, d);
			if(t2 != null){
				list.add(t2);
			}
		}
		return list;
	}
	
	/**
	 * Gives the neighbor tile for the given direction
	 * 
	 * @param t
	 * @param d
	 * @return
	 */
	public Tile getNeighborTile(Tile t, Directions d){
		for (int x = 0; x < XSIZE; x++) {
			for (int y = 0; y < YSIZE; y++) {
				if(grid[x][y] == t){
					if(y % 2 == 0){
						// Even row
						try {
							switch (d) {
							case LEFT:
								return grid[x-1][y];
							case RIGHT:
								return grid[x+1][y];
							case UPLEFT:
								return grid[x-1][y-1];
							case UPRIGHT:
								return grid[x][y-1];
							case DOWNLEFT:
								return grid[x-1][y+1];
							case DOWNRIGHT:
								return grid[x][y+1];
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return null;
						}
					}else{
						// Odd row
						try {
							switch (d) {
							case LEFT:
								return grid[x-1][y];
							case RIGHT:
								return grid[x+1][y];
							case UPLEFT:
								return grid[x][y-1];
							case UPRIGHT:
								return grid[x+1][y-1];
							case DOWNLEFT:
								return grid[x][y+1];
							case DOWNRIGHT:
								return grid[x+1][y+1];
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return null;
						}
					}
				}
			}
		}
		Gdx.app.error(this.getClass().getName(), "Given tile was not found in map board!");
		return null;
	}
	
	/**
	 * Get all tiles in range of given tile
	 * 
	 * @param t	Starting tile
	 * @param range	The distance
	 * @param rangeList	Give it null
	 * @return	An arraylist of all the tiles in the range
	 */
	public ArrayList<Tile> getTilesInRange(Tile t, int range, ArrayList<Tile> rangeList){
		if(range <= 0 || t == null){
			return null;
		}
		if(rangeList == null){
			rangeList = new ArrayList<Tile>();
		}
		for(Tile t2 : getNeighborTiles(t)){
			getTilesInRange(t2, range - 1, rangeList);
			if(rangeList.contains(t2) == false){
				rangeList.add(t2);
			}
		}
		return rangeList;
	}
}
