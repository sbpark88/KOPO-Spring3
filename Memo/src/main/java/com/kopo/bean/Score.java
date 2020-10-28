package com.kopo.bean;

public class Score {
	public int idx;
	public String name;
	public double score1;
	public double score2;
	public String created;
	public String updated;
	
	public final String DB_FILE_NAME = "c:\\tomcat\\score.db";
	public final String TABLE_NAME = "score";

	public String toHtmlString() {
		StringBuffer htmlString = new StringBuffer();
		htmlString.append("<tr>");
		htmlString.append("<td>" + this.idx + "</td>");
		htmlString.append("<td>" + this.name + "</td>");
		htmlString.append("<td>" + this.score1 + "</td>");
		htmlString.append("<td>" + this.score2 + "</td>");
		htmlString.append("<td>" + "<a href='u1?idx=" + this.idx + "'>상세보기</a>" + "</td>");
		htmlString.append("</tr>");
		return htmlString.toString();
	}
}
