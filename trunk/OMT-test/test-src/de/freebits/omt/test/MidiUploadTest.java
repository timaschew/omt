package de.freebits.omt.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class MidiUploadTest extends SeamTest {

	@Test
	public void test_midiUpload() throws Exception {
		new FacesRequest("/midiUpload.xhtml") {
			@Override
			protected void updateModelValues() throws Exception {				
				//set form input to model attributes
				setValue("#{midiUpload.value}", "seam");
			}
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{midiUpload.midiUpload}");
			}
			@Override
			protected void renderResponse() {
				//check model attributes if needed
				assert getValue("#{midiUpload.value}").equals("seam");
			}
		}.run();
	}
}
