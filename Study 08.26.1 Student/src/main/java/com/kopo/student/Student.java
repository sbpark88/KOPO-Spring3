package com.kopo.student;

import java.util.ArrayList;

public class Student {
	int idx;
	String name;
	int middleScore;
	int finalScore;
	
	Student() {
		
	}
	
	public Student(String name, int middleScore, int finalScore) {
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;		
	}
	
	public Student(int idx, String name, int middleScore, int finalScore) {
		this.idx = idx;
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;		
	}
	
//	public String toHtmlString(ArrayList<?> resultDataSet) {
//		String htmlText = "";
//		for (Object student : resultDataSet) {
//			htmlText = htmlText + "<tr>";
//			htmlText = htmlText + "<td>" + ((Student)student).idx + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)student).name + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)student).middleScore + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)student).finalScore + "</td>";
//			htmlText = htmlText + "</tr>";
//		}
//		return htmlText;
//	}
	
	public String toHtmlString(ArrayList<Student> objects) {
		String htmlText = "";
		for (Student each : objects) {
			htmlText = htmlText + "<tr>";
			htmlText = htmlText + "<td>" + each.idx + "</td>";
			htmlText = htmlText + "<td>" + each.name + "</td>";
			htmlText = htmlText + "<td>" + each.middleScore + "</td>";
			htmlText = htmlText + "<td>" + each.finalScore + "</td>";
			htmlText = htmlText + "</tr>";
		}
		return htmlText;
	}
	
//	public String toHtmlString(ArrayList<?> students) {
//		String htmlText = "";
//		for (int i = 0; i < students.size(); i++) {
//			htmlText = htmlText + "<tr>";
//			htmlText = htmlText + "<td>" + ((Student)students.get(i)).idx + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)students.get(i)).name + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)students.get(i)).middleScore + "</td>";
//			htmlText = htmlText + "<td>" + ((Student)students.get(i)).finalScore + "</td>";
//			htmlText = htmlText + "</tr>";
//		}
//		return htmlText;
//	}
}
