package org.audiolizer;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StartTest {

	private String args[];
	private Start start;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testParseCommandlineArguments() {
		Start start = new Start();
		String argsString = "-sample ./test/samples/sample.wav -min 0";
		String[] args = argsString.split(" ");
		Start.parseArguments(args);
		assertEquals("./test/samples/sample.wav", start.getSamplePath());
		//assertEquals("./samples/sample.wav", start.getSamplePath());
	}
}
