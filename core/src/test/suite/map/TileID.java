package test.suite.map;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ApplicationAdapter;

public class TileID extends ApplicationAdapter{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void _1_getImg_VaildTexturePath_() {
		try{
			for(map.TileID tile : map.TileID.values()){
				Assert.assertNotNull(tile.getImg());
			}
		}catch(ExceptionInInitializerError e){
			fail("Enum failed to load textures.");
		}
	}

	@Test
	public void _2_getRandomTileID_OutOfBounds_() {
		for(int i = 0; i < 1000; i++){
			try{
				map.TileID.getRandomTileID();
			}catch(ArrayIndexOutOfBoundsException e){
				fail("Out Of Bounds");
			}
		}
	}
}
