package com.kopo.horsegame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean close() {
		if (this.connection == null) {
			return true;
		}
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// 여기까지는 기본 필요 요소
	
	// 말 정보 입력
	public int insertData(Horse horse) throws SQLException {
		String query = "INSERT INTO " + this.dbTableName
				+ "(name, power) VALUES('" + horse.name + "', " + horse.power + ");";
		Statement statement = this.connection.createStatement();
		int result = statement.executeUpdate(query);
		statement.close();
		return result;
	}
	
	// 전체(while) 조회 & ArrayList 사용
	public ArrayList<Horse> selectData() throws SQLException {
		String query = "SELECT * FROM " + this.dbTableName + " WHERE ?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, 1);
		
		ArrayList<Horse> arrHorse = new ArrayList<Horse>();
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			arrHorse.add(new Horse(resultSet.getInt("idx"), resultSet.getString("name"), resultSet.getInt("power")));
		}
		resultSet.close();
		preparedStatement.close();
		return arrHorse;		
	}
	
	// idx로 하나만(if) 조회 & 객체 그대로 사용
	public Horse selectData(int idx) throws SQLException {
		String query = "SELECT * FROM " + this.dbTableName + " WHERE idx=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, idx);
		ResultSet resultSet = preparedStatement.executeQuery();
		Horse result = new Horse();
		if (resultSet.next()) {
			result.idx = resultSet.getInt("idx");
			result.name = resultSet.getString("name");
			result.power = resultSet.getInt("power");
		}
		resultSet.close();
		preparedStatement.close();
		return result;
	}
	
	// 말 업데이트 액션
	public void updateData(Horse horse) throws SQLException {
		String query = "UPDATE " + this.dbTableName + " SET name=?, power=? WHERE idx=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, horse.name);
		preparedStatement.setInt(2, horse.power);
		preparedStatement.setInt(3, horse.idx);
		preparedStatement.executeUpdate();
	}

	// 말 삭제 액션
	public void deleteData(Horse horse) throws SQLException {
		String query = "DELETE FROM " + this.dbTableName + " WHERE idx=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, horse.idx);
		preparedStatement.executeUpdate();
	}

}
