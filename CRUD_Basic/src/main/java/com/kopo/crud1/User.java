package com.kopo.crud1;

import java.util.ArrayList;

public class User {
	public int idx;
	public String name;
	public int age;
	public String memo;
	public String created;
	public String updated;
	
	public User() {
		
	}
	
	public User(int idx) {
		this.idx = idx;
	}
	
	public User(String name, int age, String memo) {
		this.name = name;
		this.age = age;
		this.memo = memo;
	}
	
	public User(int idx, String name, int age, String memo) {
		this.idx = idx;
		this.name = name;
		this.age = age;
		this.memo = memo;
	}
	
	// Step 1. ArrayList<T> resultDataSet 객체를 받아 값을 꺼내 String으로 리턴 (HomeController에서 직접 호출할 때 DB ArrayList<T> selectData()와 함께 사용).
//	public String toHtmlString(ArrayList<?> resultDataSet) {
//		String htmlText = "";
//		for (int i = 0; i < resultDataSet.size(); i++) {
//			htmlText += "<tr>";
//			htmlText += "<td>" + ((User)resultDataSet.get(i)).idx + "</td>";
//			htmlText += "<td>" + ((User)resultDataSet.get(i)).name + "</td>";
//			htmlText += "<td>" + ((User)resultDataSet.get(i)).age + "</td>";
//			htmlText += "<td>" + ((User)resultDataSet.get(i)).created + "</td>";
//			htmlText += "<td>" + ((User)resultDataSet.get(i)).updated + "</td>";
//			htmlText += "<td>" + "<a href='u1?idx=" + this.idx + "'>수정하기</a>" + "</td>";
//			htmlText += "<td>" + "<a href='delete?idx=" + this.idx + "'>삭제하기</a>" + "</td>";
//			htmlText += "</tr>";
//		}
//		return htmlText;
//	}
	
	// Step 2. for each 사용 (HomeController에서 직접 호출할 때 DB ArrayList<T> selectData()와 함께 사용).
//	public String toHtmlString(ArrayList<?> resultDataSet) {
//		String htmlText = "";
//		for (Object each : resultDataSet) {
//			htmlText += "<tr>";
//			htmlText += "<td>" + ((User)each).idx + "</td>";
//			htmlText += "<td>" + ((User)each).name + "</td>";
//			htmlText += "<td>" + ((User)each).age + "</td>";
//			htmlText += "<td>" + ((User)each).created + "</td>";
//			htmlText += "<td>" + ((User)each).updated + "</td>";
//			htmlText += "<td>" + "<a href='u1?idx=" + this.idx + "'>수정하기</a>" + "</td>";
//			htmlText += "<td>" + "<a href='delete?idx=" + this.idx + "'>삭제하기</a>" + "</td>";
//			htmlText += "</tr>";
//		}
//		return htmlText;
//	}
	
	// Step 3. Generic을 사용해 좀 더 간단하게 줄이기 (HomeController에서 직접 호출할 때 DB ArrayList<T> selectData()와 함께 사용).
//	public String toHtmlString(ArrayList<User> resultDataSet) {
//		String htmlText = "";
//		for (User each : resultDataSet) {
//			htmlText += "<tr>";
//			htmlText += "<td>" + each.idx + "</td>";
//			htmlText += "<td>" + each.name + "</td>";
//			htmlText += "<td>" + each.age + "</td>";
//			htmlText += "<td>" + each.created + "</td>";
//			htmlText += "<td>" + each.updated + "</td>";
//			htmlText += "<td>" + "<a href='u1?idx=" + this.idx + "'>수정하기</a>" + "</td>";
//			htmlText += "<td>" + "<a href='delete?idx=" + this.idx + "'>삭제하기</a>" + "</td>";
//			htmlText += "</tr>";
//		}
//		return htmlText;
//	}
	
	// Step 4. StringBuffer 사용하기
	public String toHtmlString() {
		StringBuffer htmlString = new StringBuffer();
		htmlString.append("<tr>");
		htmlString.append("<td>" + this.idx + "</td>");
		htmlString.append("<td>" + this.name + "</td>");
		htmlString.append("<td>" + this.age + "</td>");
		htmlString.append("<td>" + this.memo + "</td>");
		htmlString.append("<td>" + this.created + "</td>");
		htmlString.append("<td>" + this.updated + "</td>");
		htmlString.append("<td>" + "<a href='u1?idx=" + this.idx + "'>수정하기</a>" + "</td>");
		htmlString.append("<td>" + "<a href='delete?idx=" + this.idx + "'>삭제하기</a>" + "</td>");
		htmlString.append("</tr>");
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
}
