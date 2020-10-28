package com.kopo.finalexam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.sqlite.SQLiteConfig;

import com.kopo.finalexam.User;

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
		
		// 회원가입
		public void signup(User user) throws SQLException {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(System.currentTimeMillis());
			String query = "INSERT INTO " + this.dbTableName + " (id,pw,name,address,phone,petname,created,modified) VALUES(?,?,?,?,?,?,?,?);";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.id);
			preparedStatement.setString(2, user.pw);
			preparedStatement.setString(3, user.name);
			preparedStatement.setString(4, user.address);
			preparedStatement.setString(5, user.phone);
			preparedStatement.setString(6, user.petname);
			preparedStatement.setString(7, date);
			preparedStatement.setString(8, date);
			preparedStatement.executeUpdate();
		}

		// 회원가입
		public String signin(String id, String pw) throws SQLException {
			String query = "SELECT level FROM " + this.dbTableName + " WHERE id=? AND pw=?;" ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			String result = null;
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getString("level");
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
		// 관리자 페이지 접속시
		public ArrayList<User> selectAllData() throws SQLException{
			String query = "SELECT * FROM " + this.dbTableName + " WHERE ?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			ArrayList<User> list = new ArrayList<User>();
			while(resultSet.next()) {
				list.add(new User(resultSet.getString("id"),
								  resultSet.getInt("level"),
								  resultSet.getString("name"),
								  resultSet.getString("address"),
								  resultSet.getString("phone"),
								  resultSet.getString("petname"),
								  resultSet.getString("created"),
								  resultSet.getString("modified")));
			}
			resultSet.close();
			preparedStatement.close();
			return list;
		}
		
		// 관리자 페이지 업데이트
		public void updateManger(User user) throws SQLException{
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGG");
			String query = "UPDATE " + this.dbTableName + " SET level=?, name=?, address=?, petname=?, phone=?, modified=? WHERE id=?;";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(System.currentTimeMillis());
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setLong(1, user.level);
			preparedStatement.setString(2, user.name);
			preparedStatement.setString(3, user.address);
			preparedStatement.setString(4, user.petname);
			preparedStatement.setString(5, user.phone);
			preparedStatement.setString(6, date);
			preparedStatement.setString(7, user.id);
			preparedStatement.executeUpdate();
			System.out.println(query);
			System.out.println(user.id);
		}
		
		

		
		
		
		

}
