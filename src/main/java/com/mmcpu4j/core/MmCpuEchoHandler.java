package com.mmcpu4j.core;

public class MmCpuEchoHandler implements MmCpuHandler {

	private static final String FORMAT = "%-4s: %5.2f, %s\n";
	
	public void mmcpuLoop(MmCpuTracker tracker, MmCpu mmcpu, long interval) {
		System.out.printf(FORMAT, mmcpu.getId(), mmcpu.getUsload(), "loop");
	}

	public void mmcpuOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold) {
		System.out.printf(FORMAT, mmcpu.getId(), mmcpu.getUsload(), "overload");
	}

	public void mmcpuContinuousOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold, int continuous) {
		System.out.printf(FORMAT, mmcpu.getId(), mmcpu.getUsload(), "continuous overload");
	}


}
