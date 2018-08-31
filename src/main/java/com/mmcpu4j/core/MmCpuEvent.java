package com.mmcpu4j.core;

public class MmCpuEvent {
	
	public final MmCpuEventType eventType;
	
	public final MmCpu mmcpu;
	
	public MmCpuEvent(MmCpuEventType eventType, MmCpu mmcpu) {
		super();
		this.eventType = eventType;
		this.mmcpu = new MmCpu(mmcpu);
	}

	public static enum MmCpuEventType {
		EVENT_LOOP,
		EVENT_OVERLOAD,
		EVENT_CONTINUOUS_OVERLOAD
	}
}
