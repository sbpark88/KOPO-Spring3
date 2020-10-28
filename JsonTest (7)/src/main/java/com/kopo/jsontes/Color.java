package com.kopo.jsontes;

public class Color {
	int idx;
	String code;
	String selectedDate;
	
	Color(String code) {
		this.code = code;
	}
	
	Color(int idx, String code, String selectedDate) {
		this.idx = idx;
		this.code = code;
		this.selectedDate = selectedDate;
	}
	
	public int getIdx() {
		return this.idx;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getSelectedDate() {
		return this.selectedDate;
	}
}
