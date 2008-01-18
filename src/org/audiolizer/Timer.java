package org.audiolizer;

public class Timer {

	private boolean running = false;
	private long startTime;

	public long elapsedSinceStartInMs() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		return elapsedTime;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning () {
		return running;
	}
}
