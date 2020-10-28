package com.kopo.memo;

public class Memo {
//	private int idx;
//	private String title;
//	private String memo;
	
	public int idx;
	public String title;
	public String memo;
	public String created;
	public String updated;
	
	public final String DB_FILE_NAME = "/Users/saebyul/SqliteDB/0916memo2.db";
	public final String TABLE_NAME = "memo";
	
	public Memo() {
		
	}
	
	public Memo(String title, String memo) {
		this.title = title;
		this.memo = memo;
	}
	
	public Memo(int idx, String title, String memo) {
		this.idx = idx;
		this.title = title;
		this.memo = memo;
	}
	
	public String toHtmlString() {
		StringBuffer htmlString = new StringBuffer();
		htmlString.append("<tr>");
		htmlString.append("<td>" + this.idx + "</td>");
		htmlString.append("<td>" + this.title + "</td>");
		htmlString.append("<td>" + this.memo + "</td>");
		htmlString.append("<td>" + "<a href=u1?idx=" + this.idx + ">상세보기</a>" + "</td>");
		htmlString.append("</tr>");
		
		return htmlString.toString();
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
