package com.KOPO.SpringAndSqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class DataReader {
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
	
	// HomeController가 DataReader를 인스턴스화 할 때 생성자로써 DB파일의 이름(전체 디렉토리 포함), 테이블명을 받아온다.
	public DataReader(String databaseFileName, String dbTableName) {
		this.dbFileName = databaseFileName;
		this.dbTableName = dbTableName;
	}
	
	// DB를 Open하는 메소드. 홈 컨트롤러가 DB를 사용할 때 이 메소드를 호출한다.
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
	
	// DB를 Close하는 메소드. 홈 컨트롤러가 DB 사용을 끝내고 닫을 때 이 메소드를 호출한다. (사용 후에는 반드시 닫아줘야함!!)
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
	
	// 여기서부터는 HomeController가 일을 시키는 메소드들... 특정 기능을 하는 쿼리를 만들어 보내고 결과를 받아와 HomeController에게 결과를 return한다. 그러면 이 결과를 받아서 HomeController가 .jsp 파일을 이용해 Response를 return한다.
	public int createTable() throws Exception {
		if (this.connection == null) {
			throw new Exception("DB is not open");
		}
		String query = "CREATE TABLE " + this.dbTableName + "(idx INT PRIMARY KEY, name TEXT, score REAL);";
		java.sql.Statement statement = this.connection.createStatement();
		int result = statement.executeUpdate(query);
		statement.close();
		return result;
	}
	
	public int insertData() throws SQLException {
		String query = "INSERT INTO "  + this.dbTableName + " (idx, name, score) VALUES(4,'zosb',098);";
		java.sql.Statement statement = this.connection.createStatement();
		int result = statement.executeUpdate(query);
		statement.close();
		return result;
	}
	
	public boolean selectData() throws SQLException {
		boolean result = false;
		String query = "SELECT * FROM " + this.dbTableName + " WHERE ?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, 1);
		ResultSet resultSet = preparedStatement.executeQuery();
//		resultSet.next();
		if (resultSet.next()) {
			System.out.println(resultSet.getString("name"));
			result = true;
		}
		resultSet.close();
		preparedStatement.close();
		return result;
	}

}
