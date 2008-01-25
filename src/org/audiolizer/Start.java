package org.audiolizer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Start {

	private static final long MEASUREMENT_RESOLUTION = 1000;
	private static final String SAMPLE = "sample";
	private static final String SAMPLE_DEFAULT = "./samples/sample.wav";
	private static final String ZERO_TRAFFIC_LEVEL = "min";
	private static final float ZERO_TRAFFIC_LEVEL_DEFAULT = 0;
	private static final String FULL_TRAFFIC_LEVEL = "max";
	private static final float FULL_TRAFFIC_LEVEL_DEFAULT = 50;

	private static HashMap parsedArgs = new HashMap();
	
	/**
	 * Known parameters (and defaults)
	 * - sample (./samples/sample.wav)
	 */
	public static void parseArguments(String[] args) {

		List argValues = new ArrayList();

		String argName = "";
		for (int i = 0; i<args.length; i++) {
			if (args[i].startsWith("-")) {
				argName = args[i].substring(1, args[i].length());
				parsedArgs.put(argName, new ArrayList());
			}
			else {
				List valueList = (List) parsedArgs.get(argName);
				valueList.add(args[i]);
			}
		}
	}

	public static String getSamplePath() {
		String value = SAMPLE_DEFAULT;
		List valueAsList = (List)parsedArgs.get(SAMPLE);
		if (valueAsList != null && valueAsList.size() > 0) {
			value = (String)valueAsList.get(0);
		}
		
		return value;
	}
	
	public static float getZeroTrafficLevel() {
		float value = ZERO_TRAFFIC_LEVEL_DEFAULT;
		List valueAsList = (List)parsedArgs.get(ZERO_TRAFFIC_LEVEL);
		if (valueAsList != null && valueAsList.size() > 0) {
			value = Float.parseFloat((String)valueAsList.get(0));
		}
		
		return value;
	}
	
	public static float getFullTrafficLevel() {
		float value = FULL_TRAFFIC_LEVEL_DEFAULT;
		List valueAsList = (List)parsedArgs.get(FULL_TRAFFIC_LEVEL);
		if (valueAsList != null && valueAsList.size() > 0) {
			value = Float.parseFloat((String)valueAsList.get(0));
		}
		
		return value;
	}

	public static void main(String argv[]) throws Exception {
		
		parseArguments(argv);
		
		Audiolizer audiolizer = new SampleAudiolizer(getZeroTrafficLevel(), getFullTrafficLevel(), new File(getSamplePath()));
		NetworkPerformanceMonitor monitor = new SigarNetworkPerformanceMonitor();
		
		while (true) {
			audiolizer.audiolize(monitor.getAllTrafficInKbPerSecond());
			try{Thread.sleep(MEASUREMENT_RESOLUTION);}catch(InterruptedException ie){}
		}
	}


}
