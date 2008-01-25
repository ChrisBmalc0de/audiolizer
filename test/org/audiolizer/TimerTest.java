package org.audiolizer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TimerTest {

	Timer timer = new Timer();
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testRunningFlag() {	
		assertFalse(timer.isRunning());
		timer.start();	
		assertTrue(timer.isRunning());
		timer.stop();
		assertFalse(timer.isRunning());
	}
	
	@Test
	public void testElapsedSinceStart() throws Exception {
		timer.start();
		Thread.sleep(50);
		timer.stop();
		
		// A crappy test. Actual sleep time varies on my machine between 40 and 60ms,
		// so I just check that the value is inside some sensible range
		assertTrue(timer.elapsedSinceStartInMs() > 25);
		assertTrue(timer.elapsedSinceStartInMs() < 75);
	}
	
	@Test
	public void testGetElapsedTimeAndRestart() throws Exception {
		timer.start();
		long startTime = timer.getStartTime();
		assertTrue(startTime >= System.currentTimeMillis());
		
		Thread.sleep(50);
		
		assertTrue(timer.getElapsedTimeAndRestart() > 25);
		
		assertTrue(timer.getStartTime() > startTime);
	}
}
