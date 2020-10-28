package com.kopo.people;

import java.util.ArrayList;

public class People {
	int idx;
	String name;
	String phone;
	String address;
	
	People() {
		
	}
	
	// public People(String name, String phone, String address)로 써도 되고, 접근 권한을 주고 싶은 만큼 바꾸는건 자유롭다.
	People(String name, String phone, String address) {
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	People(int idx, String name, String phone, String address) {
		this.idx = idx;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	// 데이터 객체를 읽어 String으로 return (데이터를 담을 때 단순 String을 사용)
//	public String toHtmlString(ArrayList<People> objects) {
//		String htmlText = "";
//		for (People each : objects) {
//			htmlText = htmlText + "<tr>";
//			htmlText = htmlText + "<td>" + each.idx + "</td>";
//			htmlText = htmlText + "<td>" + each.name + "</td>";
//			htmlText = htmlText + "<td>" + each.phone + "</td>";
//			htmlText = htmlText + "<td>" + each.address + "</td>";
//			htmlText = htmlText + "</tr>";
//		}
//		return htmlText;
//	}
	
	// 데이터 객체를 읽어 String으로 return (데이터를 담을 때 StringBuffer를 사용)
	public String toHtmlString(ArrayList<People> objects) {
		StringBuffer htmlString = new StringBuffer();
		for (People each : objects) {
			htmlString.append("<tr>");
			htmlString.append("<td>" + each.idx + "</td>");
			htmlString.append("<td>" + each.name + "</td>");
			htmlString.append("<td>" + each.phone + "</td>");
			htmlString.append("<td>" + each.address + "</td>");
			htmlString.append("</tr>");
		}
		return htmlString.toString();
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
