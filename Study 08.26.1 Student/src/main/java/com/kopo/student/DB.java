package com.kopo.student;

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
	
	// 파라미터 없는 생성자 추가
	public DB() {
		
	}
	
	// dbfileName이랑 tableName을 HomeController가 new Memo()만 넣어서 보내면 Memo 객체 내에 입력된 정보를 사용하도록 변경.
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
	
	// 파라미터를 받아서 open하는거 생성
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
	
	// 테이블을 생성하자.
	// Step 1. create table 쿼리 실행
//	public void createTable() {
//		String query = "CREATE TABLE " + this.tableName + " (idx INT PRIMARY KEY, name TEXT, middleScore INT, finalScore INT)";
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	// Step 2. create table 쿼리 실행
//	public void createTable() {
//		String fieldString = "";
//		fieldString = fieldString + "idx INT PRIMARY KEY";
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
	
	// Step 3. create table 쿼리 실행
	// 기존 방식(dbFileName, tableName을 HomeController로부터 String 형태로 직접 전달 받음.)일 때 사용.
	public boolean createTable(T t) {	// 기존에는 Student student라고 해서 Student라는 객체가 들어온다 명시를 했다. T는 어떤 객체가 들어올지 모른단 뜻. DB를 DB<T>로 Generic(제네릭)을 사용한다.
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
	
	// 새 방식(DB 정보를 class 객체로 빼놓은 경우 사용)
	public boolean createTable() {				// 파라미터를 따로 받지 않는다.
		Class<?> dataClass = t.getClass();		// HomeController에 이미 DB db = new DB(new Memo());를 통해 DB가 인스턴스화 되어있다. 그 인스턴스를 사용.
		Field[] dataClassFields = dataClass.getDeclaredFields();
		
		String fieldString = "";
		String dbFileName = "";			// DB db = new DB(new Memo()); 에서 dbFileName과 tableName을 찾아서 변수에 저장하기 위해 선언.
		String tableName = "";
		
		for (Field field: dataClassFields) {
			// 클래스에서 변수명(fieldName)과 변수 타입(fieldType)을 읽어온다.
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
	
			// 인스턴스 된 DB db = new DB(new Memo());로부터 클래스에서 DB 정보를 읽어온다.
			try {
				if (fieldName.matches("DB_FILE_NAME")) {	// DB 디렉토리를 추출.
					dbFileName = field.get(t).toString();
					continue;
				} else if (fieldName.matches("TABLE_NAME")) {	// 테이블 이름을 추출.
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
			this.open(dbFileName);		// DB open과 close를 쿼리 메소드 내에서 한다. dbFileName은 위에서 객체로부터 추출한 정보를 이용.
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			this.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			this.close();
		}
		return false;
	}
	
	
	// insert 쿼리 실행
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
//			
//			// DB 컬럼에 넣을 값. 값이 문자열이면 ' ' 로 감싸주고, 그렇지 않으면 그냥 입력.
//			try {
//				if (fieldType.matches(".*String")) {	// 문자열이면 ' '로 감싸준다.
//					valueString = valueString + "'" + field.get(t) + "'";
//				} else {								// 아니면 그냥 넣는다.
//					valueString = valueString + field.get(t);
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		// 위에서 구한 fieldString(name, middleScore, finalScore...)와 valueString("홍길동", 100, 80)을 넣어 쿼리문을 만든다.
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
	
	// insert 쿼리 실행 (Inner Class를 활용한 preparedStatement)
	public boolean insertData(T t) {
		Class<?> dataClass = t.getClass();	// Student.java
		Field[] dataClassFields = dataClass.getDeclaredFields();	// Student.java의 내용을 읽는다.
		
		String fieldString = "";	// 변수명을 저장 (name, middleScore, finalScore)...
		String valueString = "";	// 변수값을 저장 ("홍길동", 100, 80)...
		
		// Inner Class 만들기
		class PreparedValue {
			int type = 0;
			int intValue = 0;
			double floatValue = 0;
			String stringValue = "";
			
			PreparedValue(int intValue) {
				this.type = 1;
				this.intValue = intValue;
			}
			
			PreparedValue(double floatValue) {
				this.type = 2;
				this.floatValue = floatValue;
			}
			
			PreparedValue(String stringValue) {
				this.type = 3;
				this.stringValue = stringValue;
			}
		}
		
		// 값을 저장할 배열을 변수로 선언
		ArrayList<PreparedValue> preparedValue = new ArrayList<PreparedValue>();
		
		// for each를 이용해 
		for (Field field: dataClassFields) {	// Student.java의 내용을 한 줄씩 읽는다.
			// Step 1. 각 라인을 each로 읽어온다.
			String fieldName = field.getName();	// dataClassFields에서 각각 field(each)로 읽어온다... name, middleScore, finalScore
			String fieldType = field.getType().toString();	// 위와 마찬가지로 field(each)로 읽어온다... String, int, int
			
			if (fieldName.matches("idx")) {	// idx는 insert때는 auto increment로 필요가 없으니 넘어간다. 
				continue;
			}
			
			
			// Step 2. fieldString 만들기
			if (!fieldString.isEmpty()) {	// 최초 인입 이후 두 번째 for문 돌 때 콤마를 추가해준다. (name, middleScore, finalScore)...
				fieldString = fieldString + ",";	// fieldString 자기 자신에게 ","를 하나씩 추가해준다.
			}
			fieldString = fieldString + fieldName;	// 최초 인입 for문부터 fieldString 자기 자신에게 fieldName(name, middleScore, finalScore)를 하나씩 추가해준다.
			
			
			// Step 3. valueString 만들기
			if (!valueString.isEmpty()) {	// 최초 인입 이후 두 번째 for문 돌 때 콤마를 추가해준다. ("홍길동", 100, 80)...
				valueString = valueString + ",";	// 더한 결과는 값1, 값2, 값3... 의 형태가 된다.
			}
			// 최초 인입 for문부터 preparedValue라는 ArrayList에 데이터 타입과 값을 담고,
			// valueString 자기 자신에게는 그냥 아무 의미 없는 나중에 preparedStatement에 값을 넣기 위해 개수만 맞 ?, ?, ?를 하나씩 추가해준다.
			try {
				// preparedValue라는 ArrayList에 데이터 타입과 값을 담는다. (preparedStatement.setString(1, name);, preparedStatement.setInt(2, middleScore); 이 부분에 해당하는 값과 데이터 타입을 담는다.)
				if (fieldType.matches("(int|long|short)")) {
					preparedValue.add(new PreparedValue(field.getInt(t)));
				} else if (fieldType.matches("(float|double)")) {
					preparedValue.add(new PreparedValue(field.getDouble(t)));
				} else if (fieldType.matches(".*String")) {
					preparedValue.add(new PreparedValue(field.get(t).toString()));
				}
				valueString = valueString + "?";	// 기존의 String query = "INSERT INTO " + this.dbTableName + " (name, middleScore, finalScore) VALUES (?, ?, ?);"; 에서 ??? 이걸 넣어주기 위한 로직.
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		// 위에서 구한 fieldString(name, middleScore, finalScore...)와 valueString("홍길동", 100, 80)을 넣어 쿼리문을 만든다.
		String query = "INSERT INTO " + this.tableName + " (" + fieldString + ") VALUES(" + valueString + ")";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);	// 쿼리문을 컴파일
			
			// preparedValue라는 변수명을 가진 ArrayList에는 각각의 값이 객체로 들어가있다. << due to : preparedValue.add(new PreparedValue(field.getInt(t))) >>
			// 이 객체를 하나씩 꺼내와 이너클래스의 타입을 이용해 int면 setInt로 i + 1 번째에 preparedValue로부터 get 한 값을 넣는다.
			for (int i = 0; i < preparedValue.size(); i++) {
				if (preparedValue.get(i).type == 1) {
					preparedStatement.setInt(i + 1, preparedValue.get(i).intValue);
				} else if (preparedValue.get(i).type == 2) {
					preparedStatement.setDouble(i + 1, preparedValue.get(i).floatValue);
				} else if (preparedValue.get(i).type == 3) {
					preparedStatement.setString(i + 1, preparedValue.get(i).stringValue);
				}
			}
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// request 그대로 받아오는 방법.
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
			// 컬럼이 created, updated가 아니다 && null값이 들어온 경우 > continue 시켜라 > 즉, idx, age, memo에 null값이 있어도 허용해라.
			if (request.getParameter(fieldName) == null && !fieldName.matches("(created|updated)")) {
				continue;
			}
			// 이렇게 하는 경우는 없지만, 모든 컬럼값에 null을 하나도 허용하지 않으려면, (필수 입력값을 설정하는 경우는 이렇게 하면 되긴 할 것 같은데... 메시지 때문에 추가적인 처리가 필요하겠지... update에서는 특정 값만 업데이트 하는 경우도 있으므로 거의 필요 없을 것 같다.)
			// 즉, idx, created, updated를 제외한 모든 컬럼 title, memo에 null을 허용하지 않는다.
//			if (request.getParameter(fieldName) == null && !fieldName.matches("(idx|created|updated)")) {
//				return false;		
//			}
	
			// 이거는 HomeController에서 데이터 체크를 안 하고 올 때.
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
	
	// request 그대로 받아오는 방법.
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
	
	// update 쿼리를 만들자
	// Step 1. update 쿼리 실행
//	public void updateData(int idx, String memo) {
//		String query = "UPDATE" + this.tableName + " SET memo='" + memo + "' WHERE idx=" + idx;
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	// Step 2. update 쿼리 실행
//	public void updateData(int idx, String memo) {
//		String setString = "memo='" + memo + "'";
//		String whereString = "idx=" + idx;
//		String query = "UPDATE" + this.tableName + " SET " + setString + " WHERE " + whereString;
//		try {
//			Statement statement = this.connection.createStatement();
//			statement.executeUpdate(query);
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	// Step 3. update 쿼리 실행
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
	
	// Step final.
	// update 쿼리 실행 (Inner Class를 활용한 preparedStatement)
	public boolean updateData(T t) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();
		
		String whereString = "";	// where 조건을 저장 idx=7
		String valueString = "";	// 변수값을 저장 ("홍길동", 100, 80)...
		
		// Inner Class 만들기		
		class PreparedValue {
			boolean isString;
			Object value;
			
			PreparedValue(boolean isString, Object value) {
				this.isString = isString;
				this.value = value;				
			}
		}

		ArrayList<PreparedValue> preparedValue = new ArrayList<PreparedValue>();
		
		// for each를 이용해 
		for (Field field : dataClassFields) {
			// Step 1. 각 라인을 each로 읽어온다.
			String fieldName = field.getName();
			String fieldType = field.getType().toString();
			
			// Step 2. valueString 만들기 (insert에서는 이 과정을 Step 3.에서 했었다.)
			if (!valueString.isEmpty()) {
				valueString = valueString + ",";
			}
			
			try {
				// idx는 value와 구분한다. idx일 때는 valueString = valueString + fieldName + "=?"; 는 실행이 되면 안 되므로 continue;
				if (fieldName.matches("idx")) {
					whereString = "idx=" + field.get(t);
					continue;
				}
				
				if (fieldType.matches(".*String")) {
					preparedValue.add(new PreparedValue(true, field.get(t)));
				} else {
					preparedValue.add(new PreparedValue(false, field.get(t)));
				}
				valueString = valueString + fieldName + "=?";	// 기존의 String query = "UPDATE " + this.dbTableName + " SET name=?, middleScore=?, finalScore=? WHERE idx=?;";
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String query = "UPDATE " + this.tableName + " SET " + valueString + " WHERE " + whereString;
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);	// 쿼리문을 컴파일
			
			for (int i = 0; i < preparedValue.size(); i++) {
				if (preparedValue.get(i).isString) {
					preparedStatement.setObject(i + 1, preparedValue.get(i).value.toString());
				} else {
					preparedStatement.setObject(i + 1, preparedValue.get(i).value);
				}
			}
			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// select 쿼리를 만들자
	// Step 1. select 쿼리 실행
//	public String selectData() {
//		String query = "SELECT * FROM " + this.tableName;
//		String htmlText = "";
//		
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()) {
//				htmlText = htmlText + "<tr>";
//				htmlText = htmlText + "<td>" + result.getInt("idx") + "</td>";
//				htmlText = htmlText + "<td>" + result.getString("name") + "</td>";
//				htmlText = htmlText + "<td>" + result.getInt("middleScore") + "</td>";
//				htmlText = htmlText + "<td>" + result.getInt("finalScore") + "</td>";
//				htmlText = htmlText + "</tr>";
//			}
//			result.close();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return htmlText;
// 	}
	
	// Step 2. select 쿼리 실행
//	public String selectData(T t) {
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<Student> resultDataSet = new ArrayList<Student>();
//		
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()) {	// Field를 알아야지 가져올 수 있다.
//				resultDataSet.add(new Student(result.getInt("idx"),
//											  result.getString("name"),
//											  result.getInt("middleScore"),
//											  result.getInt("finalScore")));
//			}
//			result.close();
//			statement.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		String htmlText = (new Student()).toThmlString(resultDataSet);
//		return htmlText;		
// 	}
	
	// Step 3. select 쿼리 실행 (메소드를 이용해 String으로 return하는 방법)
//	public String selectData(T t) {
//		Class<?> dataClass = t.getClass();
//		Field[] dataClassFields = dataClass.getDeclaredFields();
//		
//		String query = "SELECT * FROM " + this.tableName;
//		ArrayList<T> resultDataSet = new ArrayList<T>();
//				
//		try {
//			Statement statement = this.connection.createStatement();
//			ResultSet result = statement.executeQuery(query);
//			while(result.next()) {
//				T rowData = (T)dataClass.getDeclaredConstructor().newInstance();	// unknown 클래스를 가지고 객체를 생성하는 방법.
//				
//				// for each를 이용해 
//				for (Field field: dataClassFields) {	// Student.java의 내용을 한 줄씩 읽는다.
//					// Step 1. 각 라인을 each로 읽어온다.
//					String fieldName = field.getName();	// dataClassFields에서 각각 field(each)로 읽어온다... name, middleScore, finalScore
//					String fieldType = field.getType().toString();	// 위와 마찬가지로 field(each)로 읽어온다... String, int, int
//						
//					if (fieldType.matches("(int|short)")) {
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
//			// DB는 건드리지 않고 Student 클래스 파일만 건드려 반응형으로 이용할거다. 
//			// Class<?> dataClass 에서 클래스를 읽어오고, Field[] dataClassFields에서 field를 읽어왔다면, 이번에는 메소드를 읽는다.
//			Method toHtmlStringMethod = dataClass.getDeclaredMethod("toHtmlString");
//			StringBuffer htmlString = new StringBuffer();
//			for (T row : resultDataSet) {
//				htmlString.append((String) toHtmlStringMethod.invoke(row));
//			}
//			return htmlString.toString();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";		
// 	}
	
	// Step 3. select 쿼리 실행
	public ArrayList<T> selectData(T t) {
		Class<?> dataClass = t.getClass();
		Field[] dataClassFields = dataClass.getDeclaredFields();
		
		String query = "SELECT * FROM " + this.tableName;
		ArrayList<T> resultDataSet = new ArrayList<T>();
				
		try {
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				T rowData = (T)dataClass.getDeclaredConstructor().newInstance();	// unknown 클래스를 가지고 객체를 생성하는 방법.
				
				// for each를 이용해 
				for (Field field: dataClassFields) {	// Student.java의 내용을 한 줄씩 읽는다.
					// Step 1. 각 라인을 each로 읽어온다.
					String fieldName = field.getName();	// dataClassFields에서 각각 field(each)로 읽어온다... name, middleScore, finalScore
					String fieldType = field.getType().toString();	// 위와 마찬가지로 field(each)로 읽어온다... String, int, int
						
					if (fieldType.matches("(int|short)")) {
						field.setInt(rowData, result.getInt(fieldName));
					} else if (fieldType.matches("(long)")) {
						field.setLong(rowData, result.getLong(fieldName));
					} else if (fieldType.matches("(float|double)")) {
						field.setDouble(rowData, result.getDouble(fieldName));
					} else if (fieldType.matches(".*String")) {
						field.set(rowData, result.getString(fieldName));
					}
				}
				resultDataSet.add(rowData);
			}
			result.close();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	// 업데이트를 하기 위한 메소드. 각 객체의 상세 정보를 불러온다.
	public HashMap<String, String> detailsData(HttpServletRequest request) {
		HashMap<String, String> resultData = new HashMap<String, String>();
		if (request.getParameter("idx") == null && !this.isIntegerString(request.getParameter("idx"))) {
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

	
	
//	
//	class PreparedValue {
//		int type = 0;
//		
//	}
//	
//	ArrayList<PreparedValue> preparedValue = new ArrayList<PreparedValue>();
//	
//	for (Field field: dataClassFields) {
//		
//	}
	

}