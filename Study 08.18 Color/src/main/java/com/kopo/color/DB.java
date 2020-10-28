package com.kopo.color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.sqlite.SQLiteConfig;

public class DB {
	private Connection connection;
	private String dbFileName;
	private String dbTableName;
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DB(String databaseFileName, String dbTableName) {
		this.dbFileName = databaseFileName;
		this.dbTableName = dbTableName;
	}
	
	public boolean open() {
		try {
			SQLiteConfig config = new SQLiteConfig();
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
		} catch(SQLException e) {
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
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void insertData(Color color) throws SQLException{
		String query = "INSERT INTO " + this.dbTableName
				+ "(code, selectedDate) VALUES('#" + color.code + "', datetime('now'));";
		java.sql.Statement statement = this.connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	public ArrayList<Color> selectData() throws SQLException{
		String query = "SELECT * FROM " + this.dbTableName + " WHERE ?";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, 1);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		ArrayList<Color> list = new ArrayList<Color>();
		while(resultSet.next()) {
			list.add(new Color(resultSet.getInt("idx"), resultSet.getString("code"), resultSet.getString("selecteddate")));
		}
		resultSet.close();
		preparedStatement.close();
		return list;
	}


}
