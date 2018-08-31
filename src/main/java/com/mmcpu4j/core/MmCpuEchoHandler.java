package com.mmcpu4j.core;

public class MmCpuEchoHandler implements MmCpuHandler {

	public void mmcpuLoop(MmCpuTracker tracker, MmCpu mmcpu, long interval) {
		System.out.println("loop: "+mmcpu.getId() + "： "+mmcpu.getUsload());
	}

	public void mmcpuOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold) {
		System.out.println("overload: "+mmcpu.getId() + "： "+mmcpu.getUsload());
	}

	public void mmcpuContinuousOverload(MmCpuTracker tracker, MmCpu mmcpu, int threshold, int continuous) {
		System.out.println("continuous overload: "+mmcpu.getId() + "： "+mmcpu.getUsload());
	}


}
