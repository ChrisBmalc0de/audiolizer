package org.audiolizer;


import org.audiolizer.Audiolizer;
import org.junit.Before;
import org.junit.Test;

public class AudiolizerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAudiolize() throws Exception {
		Audiolizer audiolizer = new AudiolizerMock();

		// This just plays something. As I do not know a way to test this, no error means success
		audiolizer.audiolize(0);
	}
}
