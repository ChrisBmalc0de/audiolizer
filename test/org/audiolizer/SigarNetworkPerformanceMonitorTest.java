package org.audiolizer;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.easymock.classextension.EasyMock.*;


import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SigarNetworkPerformanceMonitorTest {

	private static SigarNetworkPerformanceMonitor monitor;
	private static Sigar mockSigar; 
	private static NetInterfaceStat mockNetInterfaceStat_eth0;
	private static NetInterfaceStat mockNetInterfaceStat_eth1;
	private static String[] nifs = {"eth0", "eth1"};
	private IMocksControl control;
	
	@Before
	public void setUp() throws Exception {
		control = EasyMock.createControl();
		mockSigar = control.createMock(Sigar.class);
		mockNetInterfaceStat_eth0 = control.createMock(NetInterfaceStat.class);
		mockNetInterfaceStat_eth1 = control.createMock(NetInterfaceStat.class);
		
		monitor = new SigarNetworkPerformanceMonitor(mockSigar);		
	}
	
	@Test
	public void testCalculateAverageTrafficSinceLastMeasurementInBytesPerSec() throws Exception {

		expect(mockSigar.getNetInterfaceList()).andReturn(nifs);
		
		expect(mockSigar.getNetInterfaceStat(nifs[0])).andReturn(mockNetInterfaceStat_eth0);
		expect(mockNetInterfaceStat_eth0.getRxBytes()).andReturn(0l);
		expect(mockNetInterfaceStat_eth0.getTxBytes()).andReturn(0l);

		expect(mockSigar.getNetInterfaceStat(nifs[1])).andReturn(mockNetInterfaceStat_eth1);
		expect(mockNetInterfaceStat_eth1.getRxBytes()).andReturn(0l);
		expect(mockNetInterfaceStat_eth1.getTxBytes()).andReturn(0l);

		control.replay();
		
		assertEquals(0, monitor.calculateAverageTrafficSinceLastMeasurementInBytesPerSec());
		
		control.verify();
		control.reset();

		
	}
	
	@Test
	public void testCalculateTotalTrafficSoFar() throws Exception {

		assertCalculateTotalTrafficSoFar(0);
		assertCalculateTotalTrafficSoFar(1024);
	}
	
	private void assertCalculateTotalTrafficSoFar(long nifTrafficInBytes) throws Exception {
		expect(mockSigar.getNetInterfaceList()).andReturn(nifs);
		
		expect(mockSigar.getNetInterfaceStat(nifs[0])).andReturn(mockNetInterfaceStat_eth0);
		expect(mockNetInterfaceStat_eth0.getRxBytes()).andReturn(nifTrafficInBytes);
		expect(mockNetInterfaceStat_eth0.getTxBytes()).andReturn(nifTrafficInBytes);

		expect(mockSigar.getNetInterfaceStat(nifs[1])).andReturn(mockNetInterfaceStat_eth1);
		expect(mockNetInterfaceStat_eth1.getRxBytes()).andReturn(nifTrafficInBytes);
		expect(mockNetInterfaceStat_eth1.getTxBytes()).andReturn(nifTrafficInBytes);

		control.replay();
		
		assertEquals((4*nifTrafficInBytes), monitor.calculateTotalTrafficSoFar());
		
		control.verify();
		control.reset();
	}

}
