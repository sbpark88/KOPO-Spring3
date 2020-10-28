package com.kopo.teamProject;

public class Teacher {
	String name;
	String id;
	String pw;
	String nclass;
	String address;
	String phone;
	String created;
	String modified;
	
	Teacher (){
	}
	
	Teacher(String id){
		this.id = id;
	}

	Teacher(String id, String pw){
		this.id = id;
		this.pw = pw;
	}
	
//	id 찾기 구현 부분
	Teacher(String name, String nclass, String phone){
		this.name = name;
		this.nclass = nclass;
		this.phone = phone;
	}
	
	
	Teacher(String name, String id, String nclass, String address, String phone){
		this.name = name;
		this.id = id;

		this.nclass = nclass;
		this.address = address;
		this.phone = phone;
	}

	Teacher(String name, String id, String pw, String nclass, String address, String phone, String created, String modified){
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.nclass = nclass;
		this.address = address;
		this.phone = phone;
		this.created = created;
		this.modified = modified;
		
		
	}

	Teacher(String name, String id, String pw, String nclass, String address, String phone){
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.nclass = nclass;
		this.address = address;
		this.phone = phone;
		
	}
	
	Teacher(String name, String id, String pw, String nclass, String address, String phone, String modified){
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.nclass = nclass;
		this.address = address;
		this.phone = phone;
		this.modified = modified;
		
	}

}
