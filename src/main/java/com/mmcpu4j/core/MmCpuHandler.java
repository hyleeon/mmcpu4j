package com.mmcpu4j.core;

public interface MmCpuHandler {

	public void mmcpuLoop(MmCpuTracker tracker, MmCpu mmcpu, long interval);
	
	public void mmcpuOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold);
	
	public void mmcpuContinuousOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold, int continuous);
}
