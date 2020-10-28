package com.kopo.teamProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
				//sqlite�몴占� 占쎄텢占쎌뒠占쎈릭占쎈연, this.dbFIleName占쎈퓠 占쎌뿯占쎌젾占쎈쭆 db占쎈솁占쎌뵬�⑨옙 占쎈염野껓옙
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
		
		
		public void insertData(Teacher teacher) throws SQLException {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(System.currentTimeMillis());
			String insertQuery = "INSERT INTO " + this.dbTableName + " (name, id, pw, nclass, address, phone, created) values('" + teacher.name + "', '" + 
					teacher.id + "', '" + teacher.pw + "', '" + teacher.nclass + "', '" + teacher.address + "', '" + teacher.phone + "', '" + date + "');";
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(insertQuery);
			statement.close();
		}
		
		public String selectData() throws SQLException{
			boolean result = false; 
			String query = "SELECT * FROM " + this.dbTableName + " WHERE ?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1,1);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			String htmlText = "";
			while (resultSet.next()) {
				htmlText = htmlText + "<tr>";
				htmlText = htmlText + "<td>" + resultSet.getString("id") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("name") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("nclass") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("address") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("phone") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("created") + "</td>";
				htmlText = htmlText + "<td>" + resultSet.getString("modified") + "</td>";
				System.out.println(resultSet.getString("name"));
				result = true;
				htmlText = htmlText + "</tr>";
			}
			resultSet.close();
			preparedStatement.close();
			return htmlText;
		}
		
		public Teacher selectData(String id) throws SQLException{
			String query = "SELECT * FROM " + this.dbTableName + " WHERE id=?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			Teacher result = new Teacher();
			if (resultSet.next()) {
				result.id = resultSet.getString("id");
				result.name = resultSet.getString("name");
				//pw 수정
				result.pw = resultSet.getString("pw");
				result.nclass = resultSet.getString("nclass");
				result.address = resultSet.getString("address");
				result.phone = resultSet.getString("phone");
				
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
		public boolean login(String id, String pw) throws SQLException{
			boolean result = false; 
			String query = "SELECT * FROM " + this.dbTableName + " WHERE id=? AND pw=?;" ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, id );
			preparedStatement.setString(2, pw );
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
		
		//pw 찾기
		public boolean checkPw(Teacher teacher) throws SQLException{
			boolean result = false;
			String query = "SELECT * FROM " + this.dbTableName + " WHERE name=? AND id=? AND phone=?;" ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.name);
			preparedStatement.setString(2, teacher.id);
			preparedStatement.setString(3, teacher.phone);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
		
//		id 찾기 구현 부분 
		public boolean checkIdData(Teacher teacher) throws SQLException{
			boolean result = false;
			String query = "SELECT * FROM " + this.dbTableName + " WHERE name=? AND nclass=? AND phone=?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.name);
			preparedStatement.setString(2, teacher.nclass);
			preparedStatement.setString(3, teacher.phone);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = true;
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
//		id 찾기 구현 부분 
		public Teacher selectData(Teacher teacher) throws SQLException{
			String query = "SELECT * FROM " + this.dbTableName + " WHERE name=? AND nclass=? AND phone=?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.name);
			preparedStatement.setString(2, teacher.nclass);
			preparedStatement.setString(3, teacher.phone);
			ResultSet resultSet = preparedStatement.executeQuery();
			Teacher result = new Teacher();
			if (resultSet.next()) {
				result.id = resultSet.getString("id");
				result.name = resultSet.getString("name");
				//pw 수정
				result.pw = resultSet.getString("pw");
				result.nclass = resultSet.getString("nclass");
				result.address = resultSet.getString("address");
				result.phone = resultSet.getString("phone");
				
			}
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		

		public void updateData(Teacher teacher) throws SQLException {
			String query = "UPDATE " + this.dbTableName + " SET name=?, nclass=?, address=?, phone=?, modified=? WHERE id=?;";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(System.currentTimeMillis());
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.name);
			preparedStatement.setString(2, teacher.nclass);
			preparedStatement.setString(3, teacher.address);
			preparedStatement.setString(4, teacher.phone);
			preparedStatement.setString(5, date);
			preparedStatement.setString(6, teacher.id);
			preparedStatement.executeUpdate();
			
		}
		
		public void updatePw(Teacher teacher) throws SQLException {
			String query = "UPDATE " + this.dbTableName + " SET pw=?, modified=? WHERE id=?;";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(System.currentTimeMillis());
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.pw);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, teacher.id);
			preparedStatement.executeUpdate();
			
		}

		public String selectPw(Teacher teacher) throws SQLException{
			String query = "SELECT pw FROM " + this.dbTableName + " WHERE id=?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.id);
			ResultSet resultSet = preparedStatement.executeQuery(); // ResultSet은 쿼리 결과를 그대로 받아온다.
			String result = resultSet.getString("pw");
			resultSet.close();
			preparedStatement.close();
			return result;
		}
		
		
		public void deleteData(Teacher teacher) throws SQLException{
			String query = "DELETE FROM " + this.dbTableName +  " WHERE " + " id=?; " ;
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, teacher.id);
			preparedStatement.executeUpdate();
			
		}
	
}
	

