package com.kopo.student;

public class Color {
	int idx;
	String name;
	String code;
	
	Color() {
		
	}
	
	public Color(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public Color(int idx, String name, String code) {
		this.idx = idx;
		this.name = name;
		this.code = code;
	}
}
