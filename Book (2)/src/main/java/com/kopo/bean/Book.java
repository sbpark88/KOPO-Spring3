package com.kopo.bean;

public class Book {
	public int idx;
	public String name;
	public String phone;
	
	public String created;
	public String updated;
	
	public final String DB_FILE_NAME = "c:\\tomcat\\book.db";
	public final String TABLE_NAME = "book";
	
	public int getIdx() {
		return this.idx;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getCreated() {
		return this.created;
	}
	
	public String getUpdated() {
		return this.updated;
	}
}
