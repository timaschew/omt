package de.freebits.omt.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class PerformanceAnalyzerTest extends SeamTest {

	@Test
	public void test_performanceAnalyzer() throws Exception {
		new FacesRequest("/performanceAnalyzer.xhtml") {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{performanceAnalyzer.performanceAnalyzer}");
			}
		}.run();
	}
}
