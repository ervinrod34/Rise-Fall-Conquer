package test.suite.map;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	test.suite.map.Map.class,
	test.suite.map.Tile.class,
	test.suite.map.TileID.class,
})
public class TestSuite {

}
