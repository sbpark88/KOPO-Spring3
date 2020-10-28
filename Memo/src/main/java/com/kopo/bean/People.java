package com.kopo.bean;

public class People {
	public int idx;
	public String name;
	public String phone;
	public int age;
	public String created;
	public String updated;
	
	public final String DB_FILE_NAME = "c:\\tomcat\\memo4.db";
	public final String TABLE_NAME = "pp";
	
	public String toHtmlString() {
		StringBuffer htmlString = new StringBuffer();
		htmlString.append("<tr>");
		htmlString.append("<td>" + this.idx + "</td>");
		htmlString.append("<td>" + this.name + "</td>");
		htmlString.append("<td>" + this.phone + "</td>");
		htmlString.append("<td>" + this.age + "</td>");
		htmlString.append("<td>" + "<a href='u1?idx=" + this.idx + "'>상세보기</a>" + "</td>");
		htmlString.append("</tr>");
		return htmlString.toString();
	}
}
