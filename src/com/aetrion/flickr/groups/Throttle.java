package com.aetrion.flickr.groups;

public class Throttle {
	private int count;
	private String mode;
	private int remaining;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getRemaining() {
		return remaining;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
}
