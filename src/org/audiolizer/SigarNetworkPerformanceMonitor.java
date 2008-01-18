package org.audiolizer;

import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;

public class SigarNetworkPerformanceMonitor implements
		NetworkPerformanceMonitor {

	private Sigar sigar = new Sigar();
	private long previousMeasuredTotalTraffic = 0;
	private long previousMeasurementTime = 0;
	private boolean firstRun = true;
	
	public SigarNetworkPerformanceMonitor() throws Exception{
		this(new Sigar());
	}

	public SigarNetworkPerformanceMonitor(Sigar sigar) throws Exception{
		this.sigar = sigar;
	}

	public float getAllTrafficInKbPerSecond() throws Exception{
			
		return (calculateAverageTrafficSinceLastMeasurementInBytesPerSec()/1024);		
	}
	
	long calculateAverageTrafficSinceLastMeasurementInBytesPerSec() throws Exception {
		
		long trafficNow = calculateTotalTrafficSoFar();
		
		if (firstRun) {			
			previousMeasuredTotalTraffic = trafficNow;
			previousMeasurementTime = System.currentTimeMillis();
			firstRun = false;
		}
		
		long trafficDiff = trafficNow - previousMeasuredTotalTraffic;
		previousMeasuredTotalTraffic = trafficNow;
		
		long timeNow = System.currentTimeMillis();
		long timeDiff = timeNow - previousMeasurementTime + 1; // the +1 is to make sure diff is never 0
		previousMeasurementTime = timeNow; 
		
		return ((trafficDiff / timeDiff) * 1000);		
	}

	long calculateTotalTrafficSoFar() throws Exception {
		
		long total = 0;
		NetInterfaceStat netInterfaceStat;
		
		for (String nif : sigar.getNetInterfaceList()) {
			netInterfaceStat = sigar.getNetInterfaceStat(nif);
			total += netInterfaceStat.getRxBytes();
			total += netInterfaceStat.getTxBytes();
		}

		return total;
	}

}
