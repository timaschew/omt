package de.freebits.omt.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class TestFormTest extends SeamTest {

	@Test
	public void test_testForm() throws Exception {
		new FacesRequest("/testForm.xhtml") {
			@Override
			protected void updateModelValues() throws Exception {				
				//set form input to model attributes
				setValue("#{testForm.value}", "seam");
			}
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{testForm.testForm}");
			}
			@Override
			protected void renderResponse() {
				//check model attributes if needed
				assert getValue("#{testForm.value}").equals("seam");
			}
		}.run();
	}
}
