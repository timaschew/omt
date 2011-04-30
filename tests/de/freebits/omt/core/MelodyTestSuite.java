package de.freebits.omt.core;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import de.freebits.omt.core.melody.ContourHelperTest;

@RunWith(Suite.class)
@SuiteClasses({ ContourHelperTest.class })
public class MelodyTestSuite {

	@BeforeClass
	public static void initializeSuite() {
		System.out.println("TestSuite started");
	}

}
