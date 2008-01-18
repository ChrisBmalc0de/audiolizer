package org.audiolizer;

import java.io.File;

public class Start {

	private static final long MEASUREMENT_RESOLUTION = 1000;

	public static void main(String argv[]) throws Exception {
		Audiolizer audiolizer = new SampleAudiolizer(0, 50, new File("./samples/sample.wav"));
		NetworkPerformanceMonitor monitor = new SigarNetworkPerformanceMonitor();
		
		while (true) {
			audiolizer.audiolize(monitor.getAllTrafficInKbPerSecond());
			try{Thread.sleep(MEASUREMENT_RESOLUTION);}catch(InterruptedException ie){}
		}
	}
}
