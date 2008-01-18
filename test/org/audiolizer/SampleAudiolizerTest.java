package org.audiolizer;

import java.io.File;

import static org.junit.Assert.*;

import org.audiolizer.SampleAudiolizer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SampleAudiolizerTest {

	private static final float MIN_THROUGHPUT = 0;
	private static final float MAX_THROUGHPUT = 100;
	private static final long TIME_TO_WAIT_FOR_THE_SAMPLE_TO_PLAY = 3000;
	private static final String SAMPLE_FILE_NAME = "./samples/test.wav";
	private static final File SAMPLE_FILE = new File(SAMPLE_FILE_NAME);
	private static SampleAudiolizer sampleAudiolizer;

	
	@BeforeClass
	public static void init() throws Exception {
		sampleAudiolizer = new SampleAudiolizer(MIN_THROUGHPUT, MAX_THROUGHPUT, SAMPLE_FILE);
	}

	@Test
	public void testLoadSample() {
		assertEquals(true, SAMPLE_FILE.canRead());		
	}
	
	@Test
	public void testPercentageToDecibel() {
		assertEquals(Float.NEGATIVE_INFINITY, SampleAudiolizer.toDecibel(0));
		assertEquals(Float.NEGATIVE_INFINITY, SampleAudiolizer.toDecibel(-0.1f));
		assertEquals(0f, SampleAudiolizer.toDecibel(1.1f));
		assertEquals(0f, SampleAudiolizer.toDecibel(1));
	}
	
	@Test
	public void testCalculateVolumeAndAdjustMinMax() throws Exception {
		
		assertEquals(0f, sampleAudiolizer.convertToPercentageAndAdjustMinMax(MIN_THROUGHPUT));
		assertEquals(1f, sampleAudiolizer.convertToPercentageAndAdjustMinMax(MAX_THROUGHPUT));
		assertEquals(0.5f, sampleAudiolizer.convertToPercentageAndAdjustMinMax(MAX_THROUGHPUT/2));
		
		assertEquals(0f, sampleAudiolizer.convertToPercentageAndAdjustMinMax(MIN_THROUGHPUT - 0.1f));
		assertEquals(1f, sampleAudiolizer.convertToPercentageAndAdjustMinMax(MAX_THROUGHPUT + 0.1f));
	}
	
	@Test
	public void testChangingThroughPutChangesVolume() throws Exception {
		
		float currentTrafficThroughput = MIN_THROUGHPUT;
		float tenth = ((MAX_THROUGHPUT - MIN_THROUGHPUT) / 10);
		
		for (int i = 0; i < 10; i++) {
			sampleAudiolizer.audiolize(currentTrafficThroughput);
			currentTrafficThroughput += tenth;
			try	{Thread.sleep(TIME_TO_WAIT_FOR_THE_SAMPLE_TO_PLAY / 10);}catch (InterruptedException ie){}
			
		}
	}
	
}
