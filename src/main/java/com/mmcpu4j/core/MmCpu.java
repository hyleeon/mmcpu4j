package com.mmcpu4j.core;

public final class MmCpu {
	
	// the CPU ID number
	private final String id;
	
	// as represented in /proc/stat
	private long u;
	private long n;
	private long s;
	private long i;
	private long w;
	private long x;
	private long y;
	private long z;
	
	// in the order of our display
	private long uSav;
	private long sSav;
	private long nSav;
	private long iSav;
	private long wSav;
	private long xSav;
	private long ySav;
	private long zSav;
	
	//
	private float usload;
	private volatile int overloadTimes;
	
	public MmCpu(String id) {
		this.id = id;
	}
	
	public MmCpu(MmCpu other) {
		this.id = other.id;
		this.i = other.i;
		this.iSav = other.iSav;
		this.n = other.n;
		this.nSav = other.nSav;
		this.overloadTimes = other.overloadTimes;
		this.s = other.s;
		this.sSav = other.sSav;
		this.u = other.u;
		this.uSav = other.uSav;
		this.usload = other.usload;
		this.w = other.w;
		this.wSav = other.wSav;
		this.x = other.x;
		this.xSav = other.xSav;
		this.y = other.y;
		this.ySav = other.ySav;
		this.z = other.z;
		this.zSav = other.zSav;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the u
	 */
	public long getU() {
		return u;
	}

	/**
	 * @return the n
	 */
	public long getN() {
		return n;
	}

	/**
	 * @return the s
	 */
	public long getS() {
		return s;
	}

	/**
	 * @return the i
	 */
	public long getI() {
		return i;
	}

	/**
	 * @return the w
	 */
	public long getW() {
		return w;
	}

	/**
	 * @return the x
	 */
	public long getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public long getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public long getZ() {
		return z;
	}

	/**
	 * @return the uSav
	 */
	public long getuSav() {
		return uSav;
	}

	/**
	 * @return the sSav
	 */
	public long getsSav() {
		return sSav;
	}

	/**
	 * @return the nSav
	 */
	public long getnSav() {
		return nSav;
	}

	/**
	 * @return the iSav
	 */
	public long getiSav() {
		return iSav;
	}

	/**
	 * @return the wSav
	 */
	public long getwSav() {
		return wSav;
	}

	/**
	 * @return the xSav
	 */
	public long getxSav() {
		return xSav;
	}

	/**
	 * @return the ySav
	 */
	public long getySav() {
		return ySav;
	}

	/**
	 * @return the zSav
	 */
	public long getzSav() {
		return zSav;
	}

	/**
	 * @return the usload
	 */
	public float getUsload() {
		return usload;
	}

	/**
	 * @return the overloadTimes
	 */
	public int getOverloadTimes() {
		return overloadTimes;
	}

	/**
	 * @param u the u to set
	 */
	void setU(long u) {
		this.u = u;
	}

	/**
	 * @param n the n to set
	 */
	void setN(long n) {
		this.n = n;
	}

	/**
	 * @param s the s to set
	 */
	void setS(long s) {
		this.s = s;
	}

	/**
	 * @param i the i to set
	 */
	void setI(long i) {
		this.i = i;
	}

	/**
	 * @param w the w to set
	 */
	void setW(long w) {
		this.w = w;
	}

	/**
	 * @param x the x to set
	 */
	void setX(long x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	void setY(long y) {
		this.y = y;
	}

	/**
	 * @param z the z to set
	 */
	void setZ(long z) {
		this.z = z;
	}

	/**
	 * @param uSav the uSav to set
	 */
	void setuSav(long uSav) {
		this.uSav = uSav;
	}

	/**
	 * @param sSav the sSav to set
	 */
	void setsSav(long sSav) {
		this.sSav = sSav;
	}

	/**
	 * @param nSav the nSav to set
	 */
	void setnSav(long nSav) {
		this.nSav = nSav;
	}

	/**
	 * @param iSav the iSav to set
	 */
	void setiSav(long iSav) {
		this.iSav = iSav;
	}

	/**
	 * @param wSav the wSav to set
	 */
	void setwSav(long wSav) {
		this.wSav = wSav;
	}

	/**
	 * @param xSav the xSav to set
	 */
	void setxSav(long xSav) {
		this.xSav = xSav;
	}

	/**
	 * @param ySav the ySav to set
	 */
	void setySav(long ySav) {
		this.ySav = ySav;
	}

	/**
	 * @param zSav the zSav to set
	 */
	void setzSav(long zSav) {
		this.zSav = zSav;
	}

	/**
	 * @param usload the usload to set
	 */
	void setUsload(float usload) {
		this.usload = usload;
	}

	/**
	 * @param overloadTimes the overloadTimes to set
	 */
	void setOverloadTimes(int overloadTimes) {
		this.overloadTimes = overloadTimes;
	}
}
