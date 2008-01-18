package org.audiolizer;

import org.audiolizer.NetworkPerformanceMonitor;

import junit.framework.TestCase;

public class NetworkPerformanceMonitorTest extends TestCase {

	public void testGetOutboundTrafficCountInKbPerSecond() throws Exception {
		
		NetworkPerformanceMonitor npm = new NetworkPerformanceMonitorMock();
		
		assertEquals(NetworkPerformanceMonitorMock.OUTBOUND_IN_KB_PER_SEC, npm.getAllTrafficInKbPerSecond());
	}
}
