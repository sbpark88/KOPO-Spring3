package com.kopo.horsegame;

public class Horse {
	int idx;
	String name;
	int power;
	
	Horse() {
		this.name = "디폴트 네임";
		this.power = 10;
	}
	
	Horse(String name, int power) {
		this.name = name;
		this.power = power;
	}
	
	Horse(int idx, String name, int power) {
		this.idx = idx;
		this.name = name;
		this.power = power;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
}
