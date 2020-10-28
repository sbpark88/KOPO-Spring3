package com.kopo.memo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.sqlite.SQLiteConfig;

public class DB<T> {
	private String dbFileName;
	private String tableName;
	private Connection connection;
	private T t;
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DB() {
	}

	public DB(T t) {
		this.t = t;

		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		for (Field field : dataClassFields) {
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
			try {
				if (fieldName.matches("DB_FILE_NAME")) {
					this.dbFileName = field.get(t).toString();
				} else if (fieldName.matches("TABLE_NAME")) {
					this.tableName = field.get(t).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public DB(String dbFileName, String tableName) {
		this.dbFileName = dbFileName;
		this.tableName = tableName;
	}

	public boolean open() {
		SQLiteConfig config = new SQLiteConfig();
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean open(String dbFileName) {
		SQLiteConfig config = new SQLiteConfig();
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + dbFileName, config.toProperties());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public void createTable() {
//		String fieldString = "";
//		fieldString = fieldString + "idx INT PRIMARY KEY AUTOINCREMENT";
//		fieldString = fieldString + ", name TEXT";
//		fieldString = fieldString + ", middleScore INT";
//		fieldString = fieldString + ", finalScore INT";
//		String query = "CREATE TABLE " + this.tableName + " (" + fieldString + ")";
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

//	public boolean createTable(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String fieldString = "";
//		String dbFileName = "";
//		String tableName = "";
//
//		for (Field field : dataClassFields) {
//			String fieldName = field.getName();
//			String fieldType = field.getType().toString();
//			try {
//				if (fieldName.matches("DB_FILE_NAME")) {
//					dbFileName = field.get(t).toString();
//					continue;
//				} else if (fieldName.matches("TABLE_NAME")) {
//					tableName = field.get(t).toString();
//					continue;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//			if (!fieldString.isEmpty()) {
//				fieldString = fieldString + ",";
//			}
//			fieldString = fieldString + fieldName;
//			if (fieldName.matches("idx")) {
//				fieldString = fieldString + " INTEGER PRIMARY KEY AUTOINCREMENT";
//			} else if (fieldType.matches("(int|long|short)")) {
//				fieldString = fieldString + " INTEGER";
//			} else if (fieldType.matches("(float|double)")) {
//				fieldString = fieldString + " REAL";
//			} else if (fieldType.matches(".*String")) {
//				fieldString = fieldString + " TEXT";
//			}
//		}
//
//		String query = "CREATE TABLE " + tableName + " (" + fieldString + ")";
//		try {
//			this.open(dbFileName);
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//			this.close();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public boolean createTable() {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String fieldString = "";
		String dbFileName = "";
		String tableName = "";

		for (Field field : dataClassFields) {
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
			try {
				if (fieldName.matches("DB_FILE_NAME")) {
					dbFileName = field.get(t).toString();
					continue;
				} else if (fieldName.matches("TABLE_NAME")) {
					tableName = field.get(t).toString();
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			if (!fieldString.isEmpty()) {
				fieldString = fieldString + ",";
			}
			fieldString = fieldString + fieldName;
			if (fieldName.matches("idx")) {
				fieldString = fieldString + " INTEGER PRIMARY KEY AUTOINCREMENT";
			} else if (fieldType.matches("(int|long|short)")) {
				fieldString = fieldString + " INTEGER";
			} else if (fieldType.matches("(float|double)")) {
				fieldString = fieldString + " REAL";
			} else if (fieldType.matches(".*String")) {
				fieldString = fieldString + " TEXT";
			}
		}

		String query = "CREATE TABLE " + tableName + " (" + fieldString + ")";
		try {
			this.open(dbFileName);
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			this.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

//	public void insertData(Student student) {
//		String fieldString = "";
//		fieldString = fieldString + "name";
//		fieldString = fieldString + ", middleScore";
//		fieldString = fieldString + ", finalScore";
//		String valueString = "";
//		valueString = valueString + "'" + student.name + "'";
//		valueString = valueString + ", " + student.middleScore;
//		valueString = valueString + ", " + student.finalScore;
//		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

//	public boolean insertData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//		
//		String fieldString = "";
//		String valueString = "";
//
//		for (Field field: dataClassFields) {
//			String fieldName = field.getName();
//			String fieldType = field.getType().toString();
//			if (fieldName.matches("idx")) {
//				continue;
//			}
//			if (!fieldString.isEmpty()) {
//				fieldString = fieldString + ",";
//			}
//			if (!valueString.isEmpty()) {
//				valueString = valueString + ",";
//			}
//			fieldString = fieldString + fieldName;
//			try {
//				if (fieldType.matches(".*String")) {
//					valueString = valueString + "'" + field.get(t) + "'";
//				} else {
//					valueString = valueString + field.get(t);
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

//	public boolean insertData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String fieldString = "";
//		String valueString = "";
//
//		class PreparedValue {
//			int type = 0;
//			int intValue = 0;
//			double floatValue = 0;
//			String stringValue = "";
//
//			PreparedValue(int intValue) {
//				this.type = 1;
//				this.intValue = intValue;
//			}
//
//			PreparedValue(double floatValue) {
//				this.type = 2;
//				this.floatValue = floatValue;
//			}
//
//			PreparedValue(String stringValue) {
//				this.type = 3;
//				this.stringValue = stringValue;
//			}
//		}
//
//		ArrayList<PreparedValue> preparedValue = new ArrayList<PreparedValue>();
//
//		for (Field field : dataClassFields) {
//			String fieldName = field.getName();
//			String fieldType = field.getType().toString();
//			if (fieldName.matches("idx")) {
//				continue;
//			}
//			if (!fieldString.isEmpty()) {
//				fieldString = fieldString + ",";
//			}
//			if (!valueString.isEmpty()) {
//				valueString = valueString + ",";
//			}
//			fieldString = fieldString + fieldName;
//			try {
//				if (fieldType.matches("(int|long|short)")) {
//					preparedValue.add(new PreparedValue(field.getInt(t)));
//					valueString = valueString + "?";
//				} else if (fieldType.matches("(float|double)")) {
//					preparedValue.add(new PreparedValue(field.getDouble(t)));
//					valueString = valueString + "?";
//				} else if (fieldType.matches(".*String")) {
//					preparedValue.add(new PreparedValue(field.get(t).toString()));
//					valueString = valueString + "?";
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
//
//		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
//		try {
//			PreparedStatement statement = this.connection.prepareStatement(query);
//			for (int i = 0; i < preparedValue.size(); i++) {
//				if (preparedValue.get(i).type == 1) {
//					statement.setInt(i + 1, preparedValue.get(i).intValue);
//				} else if (preparedValue.get(i).type == 2) {
//					statement.setDouble(i + 1, preparedValue.get(i).floatValue);
//				} else if (preparedValue.get(i).type == 3) {
//					statement.setString(i + 1, preparedValue.get(i).stringValue);
//				}
//			}
//			statement.executeUpdate();
//			statement.close();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public boolean insertData(HttpServletRequest request) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String fieldString = "";
		String valueString = "";

		ArrayList<Object> preparedValue = new ArrayList<Object>();

		for (Field field : dataClassFields) {
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
			if (fieldName.matches("idx")) {
				continue;
			}
			if (request.getParameter(fieldName) == null && !fieldName.matches("(created|updated)")) {
				continue;
			}
			if (fieldType.matches("(int|long|short)") && !this.isIntegerString(request.getParameter(fieldName))) {
				return false;
			} else if (fieldType.matches("(float|double)") && !this.isFloatString(request.getParameter(fieldName))) {
				return false;
			}
			if (!fieldString.isEmpty()) {
				fieldString = fieldString + ",";
			}
			if (!valueString.isEmpty()) {
				valueString = valueString + ",";
			}
			fieldString = fieldString + fieldName;
			valueString = valueString + "?";
			if (fieldName.matches("created")) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				preparedValue.add(formatter.format(date));
			} else if (fieldName.matches("updated")) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				preparedValue.add(formatter.format(date));
			} else {
				preparedValue.add(request.getParameter(fieldName));
			}
		}

		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
		try {
			this.open();
			PreparedStatement statement = this.connection.prepareStatement(query);
			for (int i = 0; i < preparedValue.size(); i++) {
				statement.setObject(i + 1, preparedValue.get(i));
			}
			statement.executeUpdate();
			statement.close();
			this.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}

//	public void updateData(int idx, String memo) {
//		String setString = "memo='" + memo + "'";
//		String whereString = "idx=" + idx;
//		String query = "UPDATE " + this.tableName + " SET " + setString + " WHERE " + whereString;
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

//	public boolean updateData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//		String setString = "";
//		String whereString = "";
//		for (Field field : dataClassFields) {
//			if (!setString.isEmpty()) {
//				setString = setString + ",";
//			}
//			String fieldName = field.getName();
//			String fieldType = field.getType().toString();
//			try {
//				if (fieldName.matches("idx")) {
//					whereString = "idx=" + field.get(t);
//				} else if (fieldType.matches(".*String")) {
//					setString = setString + fieldName + "=" + "'" + field.get(t) + "'";
//				} else {
//					setString = setString + fieldName + "=" + field.get(t);
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		String query = "UPDATE " + this.tableName + " SET " + setString + " WHERE " + whereString;
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

//	public boolean updateData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//		String setString = "";
//		String whereString = "";
//
//		ArrayList<Object> preparedValue = new ArrayList<Object>();
//
//		for (Field field : dataClassFields) {
//			if (!setString.isEmpty()) {
//				setString = setString + ",";
//			}
//			String fieldName = field.getName();
//			String fieldType = field.getType().toString();
//			try {
//				if (fieldName.matches("idx")) {
//					whereString = "idx=" + field.get(t);
//				} else {
//					preparedValue.add(field.get(t));
//					setString = setString + fieldName + "=?";
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		String query = "UPDATE " + this.tableName + " SET " + setString + " WHERE " + whereString;
//		try {
//			PreparedStatement statement = this.connection.prepareStatement(query);
//			for (int i = 0; i < preparedValue.size(); i++) {
//				statement.setObject(i + 1, preparedValue.get(i));
//			}
//			statement.executeUpdate();
//			statement.close();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public boolean updateData(HttpServletRequest request) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();
		String setString = "";
		String whereString = "";

		ArrayList<Object> preparedValue = new ArrayList<Object>();

		for (Field field : dataClassFields) {
			String fieldName = field.getName();
			String fieldType = field.getType().toString();

			if (request.getParameter(fieldName) == null && !fieldName.matches("(updated)")) {
				continue;
			}
			if (fieldType.matches("(int|long|short)") && !this.isIntegerString(request.getParameter(fieldName))) {
				return false;
			} else if (fieldType.matches("(float|double)") && !this.isFloatString(request.getParameter(fieldName))) {
				return false;
			}			
			
			if (!setString.isEmpty()) {
				setString = setString + ",";
			}
			try {
				if (fieldName.matches("idx")) {
					whereString = "idx=" + request.getParameter("idx");
				} else {
					if (fieldName.matches("updated")) {
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = new Date(System.currentTimeMillis());
						preparedValue.add(formatter.format(date));
					} else {
						preparedValue.add(request.getParameter(fieldName));
					}
					setString = setString + fieldName + "=?";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String query = "UPDATE " + this.tableName + " SET " + setString + " WHERE " + whereString;
		try {
			this.open();
			PreparedStatement statement = this.connection.prepareStatement(query);
			for (int i = 0; i < preparedValue.size(); i++) {
				statement.setObject(i + 1, preparedValue.get(i));
			}
			statement.executeUpdate();
			statement.close();
			this.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	
	public boolean deleteSelectedOne(HttpServletRequest request) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();
		String whereString = "";

		ArrayList<Object> preparedValue = new ArrayList<Object>();

		for (Field field : dataClassFields) {
			String fieldName = field.getName();

			try {
				if (fieldName.matches("idx")) {
					whereString = "idx=" + request.getParameter("idx");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String query = "DELETE FROM " + this.tableName + " WHERE " + whereString;
		try {
			this.open();
			PreparedStatement statement = this.connection.prepareStatement(query);
			for (int i = 0; i < preparedValue.size(); i++) {
				statement.setObject(i + 1, preparedValue.get(i));
			}
			statement.executeUpdate();
			statement.close();
			this.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	

//	public String selectData() {
//		String query = "SELECT * FROM " + this.tableName;
//		String htmlTxt = "";
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()) {
//				htmlTxt = htmlTxt + "<tr>";
//				htmlTxt = htmlTxt + "<td>" + result.getInt("idx") + "</td>";
//				htmlTxt = htmlTxt + "<td>" + result.getString("name") + "</td>";
//				htmlTxt = htmlTxt + "<td>" + result.getInt("middleScore") + "</td>";
//				htmlTxt = htmlTxt + "<td>" + result.getInt("finalScore") + "</td>";
//				htmlTxt = htmlTxt + "</tr>";
//			}
//			result.close();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return htmlTxt;
//	}

//	public ArrayList<T> selectData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<T> resultDataSet = new ArrayList<T>(); 
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()) {
//				T rowData = (T)dataClass.getDeclaredConstructor().newInstance();
//
//				for(Field field : dataClassFields) {
//					String fieldName = field.getName();
//					String fieldType = field.getType().toString();
//						
//					if (fieldType.matches("(int)")) {
//						field.setInt(rowData, result.getInt(fieldName));
//					} else if (fieldType.matches("(long)")) {
//						field.setLong(rowData, result.getLong(fieldName));
//					} else if (fieldType.matches("(float|double)")) {
//						field.setDouble(rowData, result.getDouble(fieldName));
//					} else if (fieldType.matches(".*String")) {
//						field.set(rowData, result.getString(fieldName));
//					}
//				}
//				resultDataSet.add(rowData);
//			}
//			result.close();
//			statement.close();
//
////			Method toHtmlStringMethod = dataClass.getDeclaredMethod("toHtmlString");
////			String htmlTxt = (String) toHtmlStringMethod.invoke(t, resultDataSet);
////			return htmlTxt;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultDataSet;
//	}

//	public ArrayList<T> selectData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<T> resultDataSet = new ArrayList<T>();
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while (result.next()) {
//				T rowData = (T) dataClass.getDeclaredConstructor().newInstance();
//
//				for (Field field : dataClassFields) {
//					String fieldName = field.getName();
//					String fieldType = field.getType().toString();
//
//					if (fieldType.matches("(int)")) {
//						field.setInt(rowData, result.getInt(fieldName));
//					} else if (fieldType.matches("(long)")) {
//						field.setLong(rowData, result.getLong(fieldName));
//					} else if (fieldType.matches("(float|double)")) {
//						field.setDouble(rowData, result.getDouble(fieldName));
//					} else if (fieldType.matches(".*String")) {
//						field.set(rowData, result.getString(fieldName));
//					}
//				}
//				resultDataSet.add(rowData);
//			}
//			result.close();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultDataSet;
//	}

	public ArrayList<T> selectData() {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String query = "SELECT * FROM " + this.tableName;
		ArrayList<T> resultDataSet = new ArrayList<T>();
		try {
			this.open();
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				T rowData = (T) dataClass.getDeclaredConstructor().newInstance();

				for (Field field : dataClassFields) {
					String fieldName = field.getName();
					String fieldType = field.getType().toString();
					
					if (fieldName.matches("(DB_FILE_NAME|TABLE_NAME)")) {
						continue;
					}
					if (fieldType.matches("(int)")) {
						field.setInt(rowData, result.getInt(fieldName));
					} else if (fieldType.matches("(long)")) {
						field.setLong(rowData, result.getLong(fieldName));
					} else if (fieldType.matches("(float|double)")) {
						field.setDouble(rowData, result.getDouble(fieldName));
					} else if (fieldType.matches(".*String")) {
						if (result.getString(fieldName) == null) {
							field.set(rowData, "");
						} else {
							field.set(rowData, result.getString(fieldName));
						}
						field.set(rowData, result.getString(fieldName));
					}
				}
				resultDataSet.add(rowData);
			}
			result.close();
			statement.close();
			this.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.close();
		}
		return resultDataSet;
	}

	public String selectDataString() {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String query = "SELECT * FROM " + this.tableName;
		ArrayList<T> resultDataSet = new ArrayList<T>();
		try {
			this.open();
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				T rowData = (T) dataClass.getDeclaredConstructor().newInstance();

				for (Field field : dataClassFields) {
					String fieldName = field.getName();
					String fieldType = field.getType().toString();

					if (fieldName.matches("(DB_FILE_NAME|TABLE_NAME)")) {
						continue;
					}
					if (fieldType.matches("(int|short)")) {
						field.setInt(rowData, result.getInt(fieldName));
					} else if (fieldType.matches("(long)")) {
						field.setLong(rowData, result.getLong(fieldName));
					} else if (fieldType.matches("(float|double)")) {
						field.setDouble(rowData, result.getDouble(fieldName));
					} else if (fieldType.matches(".*String")) {		// insert, update와 달리 select에서는 화면에 'null'이라는 문자가 보이지 않도록 처리한다.
						if (result.getString(fieldName) == null) {
							field.set(rowData, "");
						} else {
							field.set(rowData, result.getString(fieldName));
						}
					}
				}
				resultDataSet.add(rowData);
			}
			result.close();
			statement.close();
			this.close();

			Method toHtmlStringMethod = dataClass.getDeclaredMethod("toHtmlString");
			StringBuffer htmlString = new StringBuffer();
			for (T row : resultDataSet) {
				htmlString.append((String) toHtmlStringMethod.invoke(row));
			}
			return htmlString.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.close();
		}
		return "";
	}

	public HashMap<String, String> detailsData(HttpServletRequest request) {
		HashMap<String, String> resultData = new HashMap<String, String>();
		if (request.getParameter("idx") == null || !this.isIntegerString(request.getParameter("idx"))) {	// 원래 && 였는데 ||로 바꿨다. 그게 맞는 것 같아서...
			return resultData;
		}
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String query = "SELECT * FROM " + this.tableName + " WHERE idx=" + request.getParameter("idx");
		try {
			this.open();
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			if (result.next()) {
				for (Field field : dataClassFields) {
					String fieldName = field.getName();
					String fieldType = field.getType().toString();
					
					if (fieldName.matches("(DB_FILE_NAME|TABLE_NAME)")) {
						continue;
					}
					
					if (result.getString(fieldName) == null) {
						continue;
					} else {
						resultData.put(fieldName, result.getString(fieldName));
					}
				}
			}
			result.close();
			statement.close();
			this.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.close();
		}
		return resultData;
	}
	

	
//	public String selectDataString(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<T> resultDataSet = new ArrayList<T>();
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while (result.next()) {
//				T rowData = (T) dataClass.getDeclaredConstructor().newInstance();
//
//				for (Field field : dataClassFields) {
//					String fieldName = field.getName();
//					String fieldType = field.getType().toString();
//
//					if (fieldType.matches("(int)")) {
//						field.setInt(rowData, result.getInt(fieldName));
//					} else if (fieldType.matches("(long)")) {
//						field.setLong(rowData, result.getLong(fieldName));
//					} else if (fieldType.matches("(float|double)")) {
//						field.setDouble(rowData, result.getDouble(fieldName));
//					} else if (fieldType.matches(".*String")) {
//						field.set(rowData, result.getString(fieldName));
//					}
//				}
//				resultDataSet.add(rowData);
//			}
//			result.close();
//			statement.close();
//
//			Method toHtmlStringMethod = dataClass.getDeclaredMethod("toHtmlString");
//			StringBuffer htmlString = new StringBuffer();
//			for (T row : resultDataSet) {
//				htmlString.append((String) toHtmlStringMethod.invoke(row));
//			}
//			return htmlString.toString();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}

	public T selectData(int idx, T t) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();

		String query = "SELECT * FROM " + this.tableName + " WHERE idx=" + idx;
		try {
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			T rowData = (T) dataClass.getDeclaredConstructor().newInstance();
			if (result.next()) {
				for (Field field : dataClassFields) {
					String fieldName = field.getName();
					String fieldType = field.getType().toString();

					if (fieldType.matches("(int)")) {
						field.setInt(rowData, result.getInt(fieldName));
					} else if (fieldType.matches("(long)")) {
						field.setLong(rowData, result.getLong(fieldName));
					} else if (fieldType.matches("(float|double)")) {
						field.setDouble(rowData, result.getDouble(fieldName));
					} else if (fieldType.matches(".*String")) {
						field.set(rowData, result.getString(fieldName));
					}
				}
			}
			result.close();
			statement.close();

			return rowData;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) (new Object());
	}

	public static boolean isIntegerString(String numericString) {
		try {
			int result = Integer.parseInt(numericString);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isFloatString(String numericString) {
		try {
			double result = Double.parseDouble(numericString);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
