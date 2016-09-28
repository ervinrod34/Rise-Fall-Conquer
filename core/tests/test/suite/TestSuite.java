package test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	test.suite.game.TestSuite.class,
	test.suite.map.TestSuite.class,
})
public class TestSuite {

}
