package com.KOPO.CRUD_jQuery;

import java.util.ArrayList;

public class Contents {
	int idx;
	String selectedCat;
	String[] category = {"끄적끄적","고민","슬픔","축하해줘"};
	String title;
	String content;
	String nickname;
	String password;
	
	public Contents() {
		
	}
	public Contents(String selectedCat, String title, String content, String nickname, String password) {
		this.selectedCat = selectedCat;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
		this.password = password;
	}
	
}
