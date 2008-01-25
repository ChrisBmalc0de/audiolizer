package org.audiolizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Start {

	private static final long MEASUREMENT_RESOLUTION = 1000;
	private String samplePath;

	// TODO: change this so that is just reads the names in a hash table with values in lists 
	static void parseArguments(String[] argv) {
		
		int i = 0;
		
		for (String arg : argv) {
			System.out.println(i++ + ":" + arg);
		}
	}
	
	public boolean isArgGiven(String[] args, String argNameWithoutDash) {
		
		if (getArgIndex(args, argNameWithoutDash) >= 0)
				return true;
		
		return false;
	}
	
	/**
	 * For example if args is {"-first", "firstValue", "-second", "secondValue" , "anotherOne"} 
	 * getArgValues(args, "second") returns {"secondValue", "anotherOne"}
	 * @param args
	 * @return
	 */
	public String[] getArgValues(String[] args, String argName) {
		
		List argValues = new ArrayList();
		int argIndex = getArgIndex(args, argName);
		
		if (argIndex >= 0) {
			int optIndex = argIndex + 1;
			
			while (optIndex < args.length) {
				if (!args[optIndex].startsWith("-")) {
					argValues.add(args[optIndex]);
				}
				else {
					break;
				}
				
				optIndex++;
			}
		}
		
		if (argValues.size() > 0) {
			String[] array = (String[])argValues.toArray(new String[argValues.size()]);
			return array;
			
			//return (String[]) argValues.toArray();
		}
		else
			return (new String[0]);
	}
	
	private int getArgIndex(String[] args, String argNameWithoutDash) {

		int index = 0;
		for (String arg: args) {
			if (arg.equals("-" + argNameWithoutDash))
				return index;
			
			index++;
		}

		return -1;
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


	public String getSamplePath() {
		return samplePath;
	}
}
