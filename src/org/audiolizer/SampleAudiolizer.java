package org.audiolizer;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

/**
 * This Audiolizer plays a sample to audiolize the the network traffic. The volume of the sample
 * is scaled to given min and max values, which are automatically adjusted if measurements are outside the ranges (
 *
 * Supports only PCM .wav file format
 * 
 * @author reynders
 *
 */
public class SampleAudiolizer implements Audiolizer {

	private static final int PAN_STEPS = 10;
	private static final int PAN_TIME_MS = 1000;
	private float minThroughput;
	private float maxThroughput;
    private File sampleFile;
	private Clip clip;
	private FloatControl volumeControl;
	private boolean initialized = false;
	private float currentTrafficThroughput = 0;

	
	/**
	 * Plays the sample, volume is calculated from the relative network throughput. If the throughput goes outside
	 * the given min or max, the min and max are adjusted.
	 * 
	 * @param minThroughput Minimum initial possible net throughput in kB/sec, usually 0
	 * @param maxThroughput Maximum initial possible net throughput in kB/sec, usually 50 is a good starting value
	 * @param sampleFile
	 * @throws Exception
	 */
	public SampleAudiolizer(float minThroughput, float maxThroughput, File sampleFile) throws Exception {
		this.minThroughput = minThroughput;
		this.maxThroughput = maxThroughput;
		this.sampleFile = sampleFile;
	}

	void initializeSample() throws Exception{
		
		// All this crap to get a simple playable sample, goddamit!
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sampleFile);
		AudioFormat audioFormat = audioInputStream.getFormat();		
		DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
        clip = (Clip) AudioSystem.getLine(info);
		clip.open(audioInputStream);
		volumeControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		initialized = true;
	}
	
	public void audiolize(float measuredTrafficThroughput) throws Exception{
		
		if (!initialized) {
			initializeSample();
		}
		
		//panVolumeTo(measuredTrafficThroughput);
		//currentTrafficThroughput = measuredTrafficThroughput;
		
	    volumeControl.setValue(toDecibel(convertToPercentageAndAdjustMinMax(measuredTrafficThroughput)));
	    
		if (!clip.isRunning()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	
		//try {Thread.sleep(4000);}catch(Exception e){}
	}
	
	private void panVolumeTo(float measuredTrafficThroughput) {
		
		float throughputDiff = measuredTrafficThroughput - currentTrafficThroughput;
		float throughput = currentTrafficThroughput;
		
		for (int i = 0; i < PAN_STEPS; i++) {
			throughput += (throughputDiff / PAN_STEPS);
			volumeControl.setValue(toDecibel(convertToPercentageAndAdjustMinMax(throughput)));
			try{Thread.sleep(PAN_TIME_MS/PAN_STEPS);}catch(InterruptedException ie){}
		}	
	}

	/**
	 * Converts the measured traffic into a value between 0-1, 0 being min traffic, 1 max, and
	 * automatically adjusting the min and max values if the measurement is outside them.
	 * 
	 * @param currentTrafficThroughput Measured traffic
	 * 
	 * @return
	 */
	public float convertToPercentageAndAdjustMinMax(float currentTrafficThroughput) {
			
		if (currentTrafficThroughput > maxThroughput)
		{
			maxThroughput = currentTrafficThroughput;
		}
		else if (currentTrafficThroughput < minThroughput)
		{
			currentTrafficThroughput = minThroughput;
		}
		
		float current = (currentTrafficThroughput / maxThroughput);
		
		return current;
	}
	
	/**
	 * Converts 0.0 - 1.0 to decibel.
	 * 
	 * "The relationship between a gain in decibels and the corresponding linear amplitude multiplier is:
     *  linearScalar = pow(10.0, gainDB/20.0)"
	 * 
	 * @param zeroToOne 0.0 - 1.0. If < 0 set to 0, if > 1 set to 1
	 */
	public static float toDecibel(float zeroToOne) {
	    if (zeroToOne < 0 )
	    {
	    	zeroToOne = 0;
	    }
	    else if (zeroToOne > 1) 
	    {
	    	zeroToOne = 1;
	    }
	    
	    float dB = (float)(Math.log(zeroToOne)/Math.log(10.0)*20.0);
	    
	    return dB;
	}

}
