package com.kopo.student;

public class Memo {
	int idx;
	String memo;
	
	Memo() {
		
	}
	
	Memo (String memo) {
		this.memo = memo;
	}
	
	Memo (int idx, String memo) {
		this.idx = idx;
		this.memo = memo;
	}
}
