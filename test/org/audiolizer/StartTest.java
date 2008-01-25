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
	public void testIsArgGiven() {
		args = "-firstName firstValue -secondName second value".split(" ");
		start = new Start();
		assertTrue(start.isArgGiven(args, "firstName"));
		assertTrue(start.isArgGiven(args, "secondName"));
		assertFalse(start.isArgGiven(args, "firstValue"));
		assertFalse(start.isArgGiven(args, "second"));
		
	}
	
	@Test
	public void testGetArgValues() {
		args = "-firstName firstValue -secondName second value".split(" ");
		start = new Start();
		
		assertEquals(0, start.getArgValues(args, "does_not_exist").length);
		
		String[] firstValues = start.getArgValues(args, "firstName");
		assertEquals(1, firstValues.length);
		assertEquals("firstValue", firstValues[0]);
		String[] secondValues = start.getArgValues(args, "secondName");
		assertEquals(2, secondValues.length);
		assertEquals("second", secondValues[0]);
		assertEquals("value", secondValues[1]);
	}
	
	@Test
	public void testParseCommandlineArguments() {
		Start start = new Start();
		String argsString = "-sample ./samples/sample.wav -min 0";
		String[] args = argsString.split(" ");
		Start.parseArguments(args);
		//assertEquals("./samples/sample.wav", start.getSamplePath());
	}
}
