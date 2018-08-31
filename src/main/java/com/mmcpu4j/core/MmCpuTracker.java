package com.mmcpu4j.core;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MmCpuTracker {
	
	private static Object lock = new Object();
	private static boolean started = false;
	
	private Map<String, MmCpu> mmCpus = new TreeMap<String, MmCpu>();
	
	private LinkedBlockingQueue<MmCpuEvent> queue = new LinkedBlockingQueue<MmCpuEvent>(64);
	
	private RandomAccessFile input;
	//
	
	private final Executor executor;
	
	private MmCpuHandler handler;
	
	private long interval;
	
	private int threshold;
	
	private int continuous;
	
	public MmCpuTracker(MmCpuHandler handler) {
		this(null, handler, 2000, 75, 3);
	}
	
	public MmCpuTracker(MmCpuHandler handler, 
			long interval, int threshold, int continuous) {
		this(null, handler, interval, threshold, continuous);
	}
	
	public MmCpuTracker(Executor executor, MmCpuHandler handler, 
			long interval, int threshold, int continuous) {
		
		if(executor == null) {
			executor = Executors.newFixedThreadPool(1);
		}
		this.executor = executor;
		this.handler = handler;
		this.interval = interval;
		this.threshold = threshold;
		this.continuous = continuous;
		
		//
		try {
			input = new RandomAccessFile("/proc/stat", "r");
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		if(!started) {
			synchronized (lock) {
				if(!started) {
					started = true;
					//
					executor.execute(EventHandleLooper);
					//
					ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
					es.scheduleWithFixedDelay(CpuRefreshLooper, 0, interval, TimeUnit.MILLISECONDS);
				}
			}
		}
	}
	
	public void cpusRefresh() throws Exception {
		
		if(input == null) {
			return;
		}
		
		List<String> lines = new ArrayList<String>();
		String linex;
		while((linex = input.readLine()) != null) {
			lines.add(linex);
		}
		input.seek(0);
		//		
		for(String line : lines) {
			line = line.trim();
			if(line.startsWith("cpu")) {
				//
				int sIndext = 1;
				//
				String[] items = line.split("\\s");
				String id = items[0].trim();
				if(id.equalsIgnoreCase("cpu")) {
					sIndext = 2;
				}
				MmCpu mmCpu = mmCpus.get(id);
				if(mmCpu == null) {
					mmCpu = new MmCpu(id);
					mmCpus.put(id, mmCpu);
				}
				cpusRefresh0(mmCpu, items, sIndext);
			}
		}
	}
	
	public Map<String, MmCpu> getMmCpus() {
		return Collections.unmodifiableMap(mmCpus);
	}
	
	/**
	 * @return the executor
	 */
	Executor getExecutor() {
		return executor;
	}
	
	/**
	 * @return the handler
	 */
	MmCpuHandler getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	void setHandler(MmCpuHandler handler) {
		this.handler = handler;
	}

	/**
	 * @return the interval
	 */
	long getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	void setInterval(long interval) {
		this.interval = interval;
	}

	/**
	 * @return the threshold
	 */
	int getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/**
	 * @return the continuous
	 */
	int getContinuous() {
		return continuous;
	}

	/**
	 * @param continuous the continuous to set
	 */
	void setContinuous(int continuous) {
		this.continuous = continuous;
	}

	private void cpusRefresh0(MmCpu mmcpu, String[] items, int sIndex) {
		//
		mmcpu.setuSav(mmcpu.getU());
		mmcpu.setnSav(mmcpu.getN());
		mmcpu.setsSav(mmcpu.getS());
		mmcpu.setiSav(mmcpu.getI());
		mmcpu.setwSav(mmcpu.getW());
		mmcpu.setxSav(mmcpu.getX());
		mmcpu.setySav(mmcpu.getY());
		mmcpu.setzSav(mmcpu.getZ());
		//
		mmcpu.setU(Long.parseLong(items[sIndex++]));
		mmcpu.setN(Long.parseLong(items[sIndex++]));
		mmcpu.setS(Long.parseLong(items[sIndex++]));
		mmcpu.setI(Long.parseLong(items[sIndex++]));
		mmcpu.setW(Long.parseLong(items[sIndex++]));
		mmcpu.setX(Long.parseLong(items[sIndex++]));
		mmcpu.setY(Long.parseLong(items[sIndex++]));
		mmcpu.setX(Long.parseLong(items[sIndex++]));
		//
		cpusLoadCal(mmcpu);
	}
	
	private void cpusLoadCal(MmCpu mmcpu) {
		long u_frme, s_frme, n_frme, i_frme, w_frme, x_frme, y_frme, z_frme, tot_frme;
		float scale;
		//
		u_frme = mmcpu.getU() - mmcpu.getuSav();
		s_frme = mmcpu.getS() - mmcpu.getsSav();
		n_frme = mmcpu.getN() - mmcpu.getnSav();
		i_frme = mmcpu.getI() - mmcpu.getiSav();
		w_frme = mmcpu.getW() - mmcpu.getwSav();
		x_frme = mmcpu.getX() - mmcpu.getxSav();
		y_frme = mmcpu.getY() - mmcpu.getySav();
		z_frme = mmcpu.getZ() - mmcpu.getzSav();
		//
	   tot_frme = u_frme + s_frme + n_frme + i_frme + w_frme + x_frme + y_frme + z_frme;
	   if (tot_frme < 1) tot_frme = 1;
	   scale = 100.0f / (float)tot_frme;
	   //
	   long cpuload = u_frme + s_frme;
	   mmcpu.setUsload((float)cpuload * scale);
	   //
	   dispatch(mmcpu);
	}
	
	private void dispatch(MmCpu mmcpu) {
		queue.offer(new MmCpuEvent(MmCpuEvent.MmCpuEventType.EVENT_LOOP, mmcpu));
		float fload = mmcpu.getUsload();
		float ft = (float)threshold;
		if(fload > ft) {
			queue.offer(new MmCpuEvent(MmCpuEvent.MmCpuEventType.EVENT_OVERLOAD, mmcpu));
			int c = mmcpu.getOverloadTimes() + 1;
			mmcpu.setOverloadTimes(c);
			if(c > continuous) {
				queue.offer(new MmCpuEvent(MmCpuEvent.MmCpuEventType.EVENT_CONTINUOUS_OVERLOAD, mmcpu));
			}
		} else {
			mmcpu.setOverloadTimes(0);
		}
	}
	
	private Runnable CpuRefreshLooper = new Thread("CpuRefreshLooper") {
		
		
		public void run() {
			try {
				cpusRefresh();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private Runnable EventHandleLooper = new Thread("EventHandleLooper") {
		
		public void run() {
			for(;;) {
				MmCpuEvent event = null;
				try {
					event = queue.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(event != null) {
					switch(event.eventType) {
					case EVENT_LOOP:
						handler.mmcpuLoop(MmCpuTracker.this, event.mmcpu, interval);
						break;
					case EVENT_OVERLOAD:
						handler.mmcpuOverload(MmCpuTracker.this, event.mmcpu, threshold);
						break;
					case EVENT_CONTINUOUS_OVERLOAD:
						handler.mmcpuContinuousOverload(MmCpuTracker.this, event.mmcpu, threshold, continuous);
						break;
					default:
						break;
					}
				}
			}
		}
	};
}
