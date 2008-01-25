package org.audiolizer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Start {

	private static final long MEASUREMENT_RESOLUTION = 1000;
	private static final String SAMPLE = "sample";
	private static final String SAMPLE_DEFAULT = "./samples/sample.wav";
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
		if (value != null && valueAsList.size() > 0) {
			value = (String)valueAsList.get(0);
		}
		
		return value;
	}

	public static void main(String argv[]) throws Exception {
		
		parseArguments(argv);
		
		Audiolizer audiolizer = new SampleAudiolizer(0, 50, new File("./samples/sample.wav"));
		NetworkPerformanceMonitor monitor = new SigarNetworkPerformanceMonitor();
		
		while (true) {
			audiolizer.audiolize(monitor.getAllTrafficInKbPerSecond());
			try{Thread.sleep(MEASUREMENT_RESOLUTION);}catch(InterruptedException ie){}
		}
	}


}
