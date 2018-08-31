package com.mmcpu4j.core;

import junit.framework.TestCase;

public class TestMmCpuLoader extends TestCase {
	
	private MmCpuTracker loader;
	
	@Override
	protected void setUp() throws Exception {
		//
		loader = new MmCpuTracker(new EchoHandler(), 2000, 50, 3);
		//
		super.setUp();
	}

	public void testSummaryShow() {
		
		
		for(;;) {
			try {
				loader.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
