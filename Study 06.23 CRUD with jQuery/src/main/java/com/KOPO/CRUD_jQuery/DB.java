package com.KOPO.CRUD_jQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import org.sqlite.SQLiteConfig;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;

public class DB {
	private Connection connection;
	private String dbfileName;
	private String dbTableName;
	static {
		try {
			Class.forName("org.sqlite.JDBC");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public DB(String databaseFileName, String dbTableName) {
		this.dbfileName = databaseFileName;
		this.dbTableName = dbTableName;
	}
	public boolean open() {
		try {
			SQLiteConfig config = new SQLiteConfig();
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + 
			this.dbfileName, config.toProperties());
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean close() {
		if(this.connection == null) {
			return true;
		}
		try { 
			this.connection.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void insertData(Contents contents) throws SQLException {
		String query = "INSERT INTO " + this.dbTableName + " (category, title, description, nickname, password) VALUES(?,?,?,?,?);";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, contents.category[0]);
		preparedStatement.setString(2, contents.title);
		preparedStatement.setString(3, contents.content);
		preparedStatement.setString(4, contents.nickname);
		preparedStatement.setString(5, contents.password);
		int result = preparedStatement.executeUpdate();		
		preparedStatement.close();
	}

}