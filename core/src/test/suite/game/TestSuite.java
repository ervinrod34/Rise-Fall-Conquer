package test.suite.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	test.suite.game.MyGdxGame.class,
	test.suite.game.Navigator.class,
})
public class TestSuite {

}
