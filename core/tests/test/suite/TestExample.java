package test.suite;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestExample {

	/**
	 * Happens before any test executes in this file
	 * 
	 * USAGE: When you need to set up shared class variables
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Happens after every test is done executing in this file
	 * 
	 * USAGE: When you need to release shared resources like a file
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Naming scheme for the junit tests will be a (_ID_SomethingMeaningful)
	 */
	@Test
	public void _TESTID_SOMETHINGMEANINGFUL() {
		Assert.assertTrue(123 == 123);
	}
	
	@Test
	public void _1_name() {
		if(true == false){
			fail("Not yet implemented");
		}
	}

}
