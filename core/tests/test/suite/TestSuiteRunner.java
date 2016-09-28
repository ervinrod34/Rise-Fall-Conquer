package test.suite;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.badlogic.gdx.Gdx;

public class TestSuiteRunner {
	
	private static final boolean RUNUNITTESTS = false;
	private final static String TAGNAME = TestSuiteRunner.class.getSimpleName();
	
	@SuppressWarnings("unused")
	public static void runTests(){
		if(RUNUNITTESTS == true){
			Result result = JUnitCore.runClasses(test.suite.TestSuite.class);
			for(Failure fail : result.getFailures()){
				Gdx.app.error(TAGNAME,fail.toString());
				Gdx.app.error(TAGNAME,fail.getTrace());
			}
			Gdx.app.log(TAGNAME, "Ignore: " + result.getIgnoreCount() + "/" + result.getRunCount());
			Gdx.app.log(TAGNAME, "Failure: " + result.getFailureCount() + "/" + result.getRunCount());
			Gdx.app.log(TAGNAME, "Success: " + (result.getRunCount() - result.getFailureCount()) + "/" + result.getRunCount());
			Gdx.app.log(TAGNAME, "Total Execution Time: " + result.getRunTime() + " miliseconds");
		}
	}
	
}
