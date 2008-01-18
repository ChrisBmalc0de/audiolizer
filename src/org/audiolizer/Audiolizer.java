package org.audiolizer;

/**
 * Audiolizes network traffic samples, umh, rates?!?. Usually scales sound somehow between given
 * min and max traffic throughput values, or automatically. Details are left to the implementor
 * of this interface
 * 
 * @author reynders
 *
 */
public interface Audiolizer {
	
	public void audiolize(float measuredTrafficThroughput) throws Exception;

}
