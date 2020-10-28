package com.kopo.finalexam;

public class User {
	String id;
	int level;
	String pw;
	String name;
	String address;
	String phone;
	String petname;
	String created;
	String modified;
	
	// 회원가입시 사용
	public User(String id, String pw, String name, String address, String phone, String petname) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.petname = petname;				
	}
	
	// 관리자 페이지 (정상 로그인)
	public User(String id, int level, String name, String address, String phone, String petname, String created, String modified) {
		this.id = id;
		this.level = level;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.petname = petname;
		this.created = created;
		this.modified = modified;				
	}
	
	// 관리자 페이지 (권한 부족)
	public User(int level) {
		this.level = level;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	// getter 모음
	public String getId() {
		return this.id;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public String getPetname(){
		return this.petname;
	}
	
	public String getCreated () {
		return this.created;
	}
	
	public String getModified () {
		return this.modified;
	}
}
