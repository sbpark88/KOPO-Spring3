package com.kopo.crud1;

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
		try {	// 이 이름을 가진 DB가 없을 수도 있기 때문에 try~catch
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// 기존 방식(dbFileName, tableName을 HomeController로부터 String 형태로 직접 전달 받음.)
	public DB (String dbFileName, String tableName) {
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
	
	public void close() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// CREATE (테이블 생성 쿼리 메소드)
	// Step 1. 쿼리를 직접 작성.
//	public boolean createTable() {
//		String query = "CREATE TABLE " + this.tableName + " (idx INT PRIMARY KEY, name TEXT, age INT, memo TEXT, created TEXT, updated TEXT)";
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
	
	// Step 2. 쿼리를 하나의 String으로 만들어서 한 번에 보낸다.
//	public boolean createTable() {
//		String fieldString = "";
//		fieldString = fieldString + "idx INT PRIMARY KEY";
//		fieldString = fieldString + ", name TEXT";
//		fieldString = fieldString + ", age INT";
//		fieldString = fieldString + ", memo TEXT";
//		fieldString = fieldString + ", created TEXT";
//		fieldString = fieldString + ", updated TEXT";
//		String query = "CREATE TABLE " + this.tableName + " (" + fieldString + ")";
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
	
	// Step 3. Generic T 객체 사용 & 기존 방식(dbFileName, tableName을 HomeController로부터 String 형태로 직접 전달 받음.)일 때 사용.
	public boolean createTable(T t) {	// 기존에는 User user라고 해서 User라는 객체가 들어온다 명시를 했다. T는 어떤 객체가 들어올지 모른단 뜻. DB를 DB<T>로 Generic(제네릭)을 사용한다.
		Class<?> dataClass = t.getClass();	// t의 Class Object를 가져와 return해서 dataClass라는 변수명에 저장.
		Field[] dataClassFields = dataClass.getDeclaredFields();	// dataClass라는 클래스의 변수들을 array로 return.
		
		String fieldString = "";
		
		for (Field field: dataClassFields) {	//  array로부터 for each 문을 돌면서 하나씩 분해해 쿼리에 넣을 문자열을 만든다.
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
			
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
		
		String query = "CREATE TABLE " + this.tableName + " (" + fieldString + ")";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// INSERT
	// Step 3. Generic T 객체 사용 & PreparedStatement 사용
	public boolean insertData(T t) {
		Class<?> dataClass = t.getClass();	// User.java
		Field[] dataClassFields = dataClass.getDeclaredFields();	// User.java의 내용을 읽는다.
		
		String fieldString = "";	// 변수명을 저장 (name, age, memo)...
		String valueString = "";	// 변수값을 저장 ("홍길동", 20, "안녕하세요")...
		
		// 값을 저장할 배열을 변수로 선언
		ArrayList<Object> preparedValue = new ArrayList<Object>();
		
		for (Field field : dataClassFields) {	// for each로 User.java의 내용을 한 줄씩 읽는다.
			// Step 1. 각 라인을 each로 읽어온다.
			String fieldName = field.getName();				// 변수명(name, age, memo)...
			String fieldType = field.getType().toString();	// 변수 타입(String, int, String)...
			
			if (fieldName.matches("idx")) {	// idx는 insert때는 auto increment로 필요가 없으니 넘어간다.
				continue;
			}
			
			// Step 2. fieldString 만들기
			if (!fieldString.isEmpty()) {
				fieldString += ",";
			}
			fieldString += fieldName;
			
			// Step 3. valueString(PreparedStatement를 위해 ?, ?, ?를 저장) & preparedValue(실제 값을 ArrayList 객체로 저장) 만들기
			if (!valueString.isEmpty()) {
				valueString = valueString + ",";
			}
			try {
				if (fieldName.matches("created")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					preparedValue.add(formatter.format(date));
				} else if (fieldName.matches("updated")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					preparedValue.add(formatter.format(date));
				} else {	// created, updated를 제외한 모든 변수는 들어온 값을 그대로 객체에 저장.
					preparedValue.add(field.get(t));
				}
				valueString += "?";
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			for (int i = 0; i < preparedValue.size(); i++) {
				preparedStatement.setObject(i + 1, preparedValue.get(i));
			}
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	
	// UPDATE
	// Step 3. Generic T 객체 사용 & PreparedStatement 사용
	public boolean updateData(T t) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();
		
		String setString = "";		// 변수값을 저장 ("장보고", 40, "수정된다")...
		String whereString = "";	// where 조건을 저장 idx=3

		// 값을 저장할 배열을 변수로 선언
		ArrayList<Object> preparedValue = new ArrayList<Object>();
		
		for (Field field : dataClassFields) {	// for each로 User.java의 내용을 한 줄씩 읽는다.
			// Step 1. 각 라인을 each로 읽어온다.
		    String fieldName = field.getName();				// 변수명(name, age, memo)...
		    String fieldType = field.getType().toString();	// 변수 타입(String, int, String)...
		    
		    // Step 2. whereString 만들기
		    if (fieldName.matches("idx")) {
		    	try {
					whereString += "idx=" + field.getInt(t);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    	continue;
		    }
		    
		    // Step 3. setString(PreparedStatement를 위해 ?, ?, ?를 저장) & preparedValue(실제 값을 ArrayList 객체로 저장) 만들기
		    if (fieldName.matches("created")) continue;
		    if (!setString.isEmpty()) {
		    	setString += ",";
		    }
		    try {
		    	if (fieldName.matches("updated")) {
		    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    		Date date = new Date(System.currentTimeMillis());
		    		preparedValue.add(formatter.format(date));
		    	} else {	// idx, updated를 제외한 모든 변수는 들어온 값을 그대로 객체에 저장.
		    		preparedValue.add(field.get(t));
		    	}
		    	setString += fieldName + "=?";
		    } catch (IllegalArgumentException e) {
		    	e.printStackTrace();
		    } catch (IllegalAccessException e) {
		    	e.printStackTrace();
		    }
		}

		String query = "UPDATE " + this.tableName + " SET " + setString + " WHERE " + whereString;
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			for (int i = 0; i < preparedValue.size(); i++) {
				preparedStatement.setObject(i + 1, preparedValue.get(i));
			}
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	
	// SELECT All
	// 참고 : HomeController에서 직접 호출할 때 User 객체의 toHtmlString Step 1~3과 함께 사용
//	public ArrayList<T> selectData() {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<T> resultDataSet = new ArrayList<T>();
//		try {
//			this.open();
//			Statement statement = this.connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(query);
//			while (resultSet.next()) {
//				T rowData = (T) dataClass.getDeclaredConstructor().newInstance();
//
//				for (Field field : dataClassFields) {
//					String fieldName = field.getName();
//					String fieldType = field.getType().toString();
//					
//					if (fieldName.matches("(DB_FILE_NAME|TABLE_NAME)")) {
//						continue;
//					}
//					if (fieldType.matches("(int)")) {
//						field.setInt(rowData, resultSet.getInt(fieldName));
//					} else if (fieldType.matches("(long)")) {
//						field.setLong(rowData, resultSet.getLong(fieldName));
//					} else if (fieldType.matches("(float|double)")) {
//						field.setDouble(rowData, resultSet.getDouble(fieldName));
//					} else if (fieldType.matches(".*String")) {
//						if (resultSet.getString(fieldName) == null) {
//							field.set(rowData, "");
//						} else {
//							field.set(rowData, resultSet.getString(fieldName));
//						}
//						field.set(rowData, resultSet.getString(fieldName));
//					}
//				}
//				resultDataSet.add(rowData);
//			}
//			resultSet.close();
//			statement.close();
//			this.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.close();
//		}
//		return resultDataSet;
//	}
	
	// Step 3. Generic T 객체 사용 & PreparedStatement 사용
	public String selectDataString(T t) {
		Class<?> dataClass = t.getClass();	// User.java
		Field[] dataClassFields = dataClass.getDeclaredFields();	// User.java의 내용을 읽는다.

		// 값을 저장할 배열을 변수로 선언
		ArrayList<T> resultDataSet = new ArrayList<T>();		
		
		String query = "SELECT * FROM " + this.tableName;
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
					} else if (fieldType.matches("(float|double)")) {	// class 객체 데이터에 실수형을 double로 써야한다. float으로 쓸거면 이 2개를 분리할 것.
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

			Method toHtmlStringMethod = dataClass.getDeclaredMethod("toHtmlString");	// dataClass는 메소드 시작 부분에서 User.java로 정의되어있다.
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
	
	// SELECT One
//	public HashMap<String, String> detailsData(int idx, T t) {
//		Class<?> dataClass = t.getClass();	// User.java
//		Field[] dataClassFields = dataClass.getDeclaredFields();	// User.java의 내용을 읽는다.
//		
//		// 값을 저장할 Map 객체를 변수로 선언
//		HashMap<String, String> resultData = new HashMap<String, String>();
//
//		String query = "SELECT * FROM " + this.tableName + " WHERE idx=" + idx;
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			if (result.next()) {
//				for (Field field : dataClassFields) {
//					String fieldName = field.getName();
//					String fieldType = field.getType().toString();
//					
//					if (result.getString(fieldName) == null) {
//						continue;
//					} else {
//						resultData.put(fieldName, result.getString(fieldName));
//					}
//				}
//			}
//			result.close();
//			statement.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultData;
//	}
	
	// 생성자를 이용한 방법. 파라미터를 여러개 받지 않고 객체 하나만 받기 위해 이렇게 한건데...
	// 어차피 Controller에서 생성자에 넣기 위해 입력을 하기 때문에 객체로 받는게 이득이 없다. 오히려 코드만 더 복잡해진다.
	// 해당 메소드 상세 조회도 그렇고, 아래 delete 메소드 역시 그냥 위에 로직처럼 (int idx, T t) 이렇게 생성자를 만들어 DB로 넘겨 받는게 더 편한 것 같다.  
	public HashMap<String, String> detailsData(T t) {
		Class<?> dataClass = t.getClass();	// User.java
		Field[] dataClassFields = dataClass.getDeclaredFields();	// User.java의 내용을 읽는다.
		
		String whereString = "";	// where 조건을 저장 idx=4
		
		// 값을 저장할 Map 객체를 변수로 선언
		HashMap<String, String> resultData = new HashMap<String, String>();

		for (Field field : dataClassFields) {	// for each로 User.java의 내용을 한 줄씩 읽는다.
			// Step 1. 각 라인을 each로 읽어온다.
			String fieldName = field.getName();				// 변수명(name, age, memo)...

		    // Step 2. whereString 만들기
		    if (fieldName.matches("idx")) {
		    	try {
					whereString += "idx=" + field.getInt(t);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    	continue;
		    }
		}

		String query = "SELECT * FROM " + this.tableName + " WHERE " + whereString;
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				for (Field field : dataClassFields) {
					String fieldName = field.getName();
					String fieldType = field.getType().toString();
					
					if (resultSet.getString(fieldName) == null) {
						continue;
					} else {
						resultData.put(fieldName, resultSet.getString(fieldName));
					}
				}
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}
	
	// DELETE
	// Step 3. Generic T 객체 사용 & PreparedStatement 사용
	public boolean deleteData(T t) {
		Class<?> dataClass = t.getClass();	// User.java
		Field[] dataClassFields = dataClass.getDeclaredFields();	// User.java의 내용을 읽는다.
		
		String whereString = "";	// where 조건을 저장 idx=4

		for (Field field : dataClassFields) {	// for each로 User.java의 내용을 한 줄씩 읽는다.
			// Step 1. 각 라인을 each로 읽어온다.
			String fieldName = field.getName();				// 변수명(name, age, memo)...

		    // Step 2. whereString 만들기
		    if (fieldName.matches("idx")) {
		    	try {
					whereString += "idx=" + field.getInt(t);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    	continue;
		    }
		}

		String query = "DELETE FROM " + this.tableName + " WHERE " + whereString;
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	
	
	// String이 정수로 형변환이 가능한지 아닌지를 검사하는 메소드
	public static boolean isIntegerString(String numericString) {
		try {
			int result = Integer.parseInt(numericString);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
	
	// String이 실수로 형변환이 가능한지 아닌지를 검사하는 메소드
	public static boolean isFloatString(String numericString) {
		try {
			double result = Double.parseDouble(numericString);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}
}
