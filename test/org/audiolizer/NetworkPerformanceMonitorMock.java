package org.audiolizer;

import org.audiolizer.NetworkPerformanceMonitor;

public class NetworkPerformanceMonitorMock implements NetworkPerformanceMonitor {
	
	public static final float OUTBOUND_IN_KB_PER_SEC = 0;
	
	public float getAllTrafficInKbPerSecond() {
			return OUTBOUND_IN_KB_PER_SEC;
	}
}
