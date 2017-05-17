package com.sirding.match;

import java.io.Serializable;

/**
 * @Described	: 背包
 * @project		: com.sirding.match.Pack
 * @author 		: zc.ding
 * @date 		: 2016年12月26日
 */
public class Pack implements Serializable, Comparable<Pack> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double capacity;		//容量
	private int gapTime;			//间隔时间
	private double interest;		//利息
	
	public Pack(){
		//
	}
	
	public Pack(double capacity, int gapTime, double interest){
		this.capacity = capacity;
		this.gapTime = gapTime;
		this.interest = interest;
	}
	
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public int getGapTime() {
		return gapTime;
	}
	public void setGapTime(int gapTime) {
		this.gapTime = gapTime;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}

	@Override
	public int compareTo(Pack o) {
		return (int)(o.capacity - this.capacity);
	}
}
