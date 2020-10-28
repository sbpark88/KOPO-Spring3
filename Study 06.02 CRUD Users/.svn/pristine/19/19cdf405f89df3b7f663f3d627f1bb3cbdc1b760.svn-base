package com.KOPO.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import org.sqlite.SQLiteConfig;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataReader {
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
	public DataReader(String databaseFileName, String dbTableName) {
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
	
	// 1. 메인 홈
	public String selectData() throws SQLException {
		boolean result = false;
		String query = "SELECT * FROM " + this.dbTableName + " WHERE ?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, 1);
		ResultSet resultSet = preparedStatement.executeQuery();
		String htmlText = "";
		while (resultSet.next()) {
			String idx = resultSet.getString("idx");
			String name = resultSet.getString("name");
			String id = resultSet.getString("id");
			htmlText = htmlText 
					+ String.format("<tr>"
							+ "<td>%s</td>"
							+ "<td>%s</td>"
							+ "<td>%s</td>"
							+ "<td><a href='/Users/modify?idx=%s'>수정하기</a></td>",
							idx, name, id, idx);
		}
		resultSet.close();
		preparedStatement.close();
		return htmlText;
	}
	
	// 2. 회원가입
	// signupAction에 대한 Insert 쿼리를 날려주는 메소드를 생성한다.
//	public int insertData(String name, String id, String password) throws SQLException {
//		String query = "INSERT INTO " + this.dbTableName + " (name, id, password) VALUES('" + name + "', '" + id + "', '" + password + "');";
//		Statement statement = this.connection.createStatement();	// Statement를 이용해 접속 엔진을 연다.
//		int result = statement.executeUpdate(query);	// 엔진을 이용해 쿼리를 보낸다.
//		statement.close();		// 접속 엔진을 닫는다.
//		
//		return result;
//	}
	
	// signupAction을 Statement에서 PreparedStatement로 바꿔보자.
	public int insertData(String name, String id, String password) throws SQLException {
		String query = "INSERT INTO " + this.dbTableName + " (name, id, password) VALUES(?, ?, ?);";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);	// PreparedStatement를 이용해 접속 엔진을 연다.
		preparedStatement.setString(1, name);	// 1번째 물음표에 name을 넣는다.
		preparedStatement.setString(2, id);		// 2번째 물음표에 id를 넣는다.
		preparedStatement.setString(3, password);
		int result = preparedStatement.executeUpdate();	// 엔진을 이용해 쿼리를 보낸다.
		preparedStatement.close();		// 접속 엔진을 닫는다.

		return result;
	}
	
	// 3. 로그인
	// signinAction에 대한 Select 쿼리를 날려주는 메소드를 생성한다.
	public boolean signin(String id, String password) throws SQLException {
		boolean result = false;
		String query = "SELECT * FROM " + this.dbTableName + " WHERE id=? AND password=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery(); // ResultSet은 쿼리 결과를 그대로 받아온다.
		if (resultSet.next()) {
			result = true;	// ResultSet을 이용해 일치하면 return값인 result에 boolean 형태의 값을 넣너준다.
		}
		resultSet.close();				// 반드시 닫아준다!
		preparedStatement.close();		// 반드시 닫아준다!
		
		return result;
	}
	
	// 4. 정보수정 (객체를 return하는 selectData를 만든다. 위 selectData는 인풋 파라미터가 없었다. 다형성을 이용해 구분한다.)
	public User selectData(String idx) throws SQLException {
		boolean result = false;
		String query = "SELECT * FROM " + this.dbTableName + " WHERE idx=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, idx);
		ResultSet resultSet = preparedStatement.executeQuery();
		User selectedUser = new User();
		if (resultSet.next()) {
			selectedUser.idx = resultSet.getInt("idx");
			selectedUser.name = resultSet.getString("name");
			selectedUser.id = resultSet.getString("id");
		}
		resultSet.close();
		preparedStatement.close();
		return selectedUser;
	}
	
	public void updateData(String idx, String name, String id) throws SQLException {
		String query = "UPDATE " + this.dbTableName + " SET name=?, id=? WHERE idx=?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, id);
		preparedStatement.setString(3, idx);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
//	public void updateData(String idx, String name, String id) throws SQLException {
//		String query = "UPDATE " + this.dbTableName + " SET name='" + name + "', id='" + id + "' WHERE idx=" + idx + ";";
//		Statement statement = this.connection.createStatement();
//		int result = statement.executeUpdate(query);
//		statement.close();
//	}
	
	

}
