package com.kopo.student;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );

		return "home";
	}
	
	// 쿼리를 이용해 테이블 생성하기.
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
//		DB db = new DB<Color>("/Users/saebyul/SqliteDB/0826color.db", "color");
//		DB db = new DB<Color>("/Users/saebyul/SqliteDB/0826memo.db", "memo");
		
		if (db.open()) {
			if (db.createTable(new Student())) {
//			if (db.createTable(new Color())) {
//			if (db.createTable(new Memo())) {
				model.addAttribute("message", "테이블이 생성되었습니다.");
			} else {
				model.addAttribute("message", "테이블 생성에 실패하였습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// 데이터 입력하기. try를 사용한 잘못된 파라미터 체크 & 문자열 숫자 형변환 가능 여부 검사하는 방법.
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// DB에 쿼리 전송 및 데이터 유효성 검사
		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
		
		if (db.open()) {
			try {
				String name = request.getParameter("name");
				int middleScore = Integer.parseInt(request.getParameter("middleScore"));
				int finalScore = Integer.parseInt(request.getParameter("finalScore"));

				if (db.insertData(new Student(name, middleScore, finalScore))) {
					model.addAttribute("message", "새 데이터를 추가했습니다.");
				} else {
					model.addAttribute("message", "데이터 추가에 실패했습니다.");
				};
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "데이터가 올바르지 않습니다.");
			}

			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// 데이터 입력하기. try를 사용한 잘못된 파라미터 체크 & 문자열 숫자 형변환 가능 여부 검사하는 방법. (기능별 분리)
//	@RequestMapping(value = "/insert", method = RequestMethod.GET)
//	public String insert(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		// 변수 선언 및 초기화
//		String name = null;
//		int middleScore = 0;
//		int finalScore = 0;
//		
//		// 데이터 유효성 검사
//		try {
//			name = request.getParameter("name");
//			middleScore = Integer.parseInt(request.getParameter("middleScore"));
//			finalScore = Integer.parseInt(request.getParameter("finalScore"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		
//		// DB에 쿼리 전송
//		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
//		
//		if (db.open()) {
//			if (db.insertData(new Student(name, middleScore, finalScore))) {
//				model.addAttribute("message", "새 데이터를 추가했습니다.");
//			} else {
//				model.addAttribute("message", "데이터 추가에 실패했습니다.");
//			};
//
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}
	
	// 데이터 입력하기. if를 사용한 잘못된 파라미터 체크 & 문자열 숫자 형변환 가능 여부 검사하는 방법.
//	@RequestMapping(value = "/insert", method = RequestMethod.GET)
//	public String insert(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		// 데이터 유효성 검사
//		if (request.getParameter("name") == null) {
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		if (request.getParameter("middleScore") == null) {
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		if (!DB.isIntegerString(request.getParameter("middleScore"))) {
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		if (request.getParameter("finalScore") == null) {
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		if (!DB.isIntegerString(request.getParameter("finalScore"))) {
//			model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			return "message";
//		}
//		
//		// DB에 쿼리 전송
//		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
//		
//		if (db.open()) {
//			if (db.insertData(new Student(request.getParameter("name"),	// 파라미터 가져오기
//										  Integer.parseInt(request.getParameter("middleScore")),
//										  Integer.parseInt(request.getParameter("finalScore"))))) {
//				model.addAttribute("message", "새 데이터를 추가했습니다.");
//			} else {
//				model.addAttribute("message", "데이터 추가에 실패했습니다.");
//			};
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}
	
	// 데이터 수정하기. try를 사용한 잘못된 파라미터 체크 & 문자열 숫자 형변환 가능 여부 검사하는 방법.
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String update(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
//		
//		if (db.open()) {
//			try {
//				if (db.updateData(new Student(Integer.parseInt(request.getParameter("idx")),
//											  request.getParameter("name"),
//											  Integer.parseInt(request.getParameter("middleScore")),
//											  Integer.parseInt(request.getParameter("finalScore"))))) {
//					model.addAttribute("message", "데이터를 수정했습니다.");
//				} else {
//					model.addAttribute("message", "데이터 수정에 실패했습니다.");
//				};
//			} catch (Exception e) {
//				e.printStackTrace();
//				model.addAttribute("message", "데이터가 올바르지 않습니다.");
//			}
//			
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}
	
	// 데이터 수정하기. try를 사용한 잘못된 파라미터 체크 & 문자열 숫자 형변환 가능 여부 검사하는 방법. (기능별 분리)
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// 변수 선언 및 초기화
		int idx = 0;
		String name = null;
		int middleScore = 0;
		int finalScore = 0;
		
		// 데이터 유효성 검사
		try {
			idx = Integer.parseInt(request.getParameter("idx"));
			name = request.getParameter("name");
			middleScore = Integer.parseInt(request.getParameter("middleScore"));
			finalScore = Integer.parseInt(request.getParameter("finalScore"));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		
		// DB에 쿼리 전송
		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
		
		if (db.open()) {
			if (db.updateData(new Student(idx, name, middleScore, finalScore))) {
				model.addAttribute("message", "데이터를 추가했습니다.");
			} else {
				model.addAttribute("message", "데이터 수정에 실패했습니다.");
			};

			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	
	// 데이터 조회하기 (전체 조회) (String return이 잘 안 됨. 아래꺼 사용.)
//	@RequestMapping(value = "/select", method = RequestMethod.GET)
//	public String select(Locale locale, Model model) {		
//		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
//		
//		if (db.open()) {
//			try {
//				String htmlString = db.selectData(new Student());
//				model.addAttribute("htmlString", htmlString);	// Student()를 인스턴스화 해 새 객체로 만든다.
//			} catch (Exception e) {
//				e.printStackTrace();
//				model.addAttribute("htmlString", "데이터를 조회를 할 수 없습니다.");
//			}
//			
//			db.close();
//		} else {
//			model.addAttribute("htmlString", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "select";
//	}
	
	// 데이터 조회하기 (전체 조회)
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String select(Locale locale, Model model) {		
		DB db = new DB<Student>("/Users/saebyul/SqliteDB/0826student.db", "student");
		
		if (db.open()) {
			try {
				ArrayList<Student> resultData = db.selectData(new Student());
				model.addAttribute("htmlString", (new Student()).toHtmlString(resultData));	// Student()를 인스턴스화 해 새 객체로 만든다.
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("htmlString", "데이터를 조회를 할 수 없습니다.");
			}
			
			db.close();
		} else {
			model.addAttribute("htmlString", "DB파일을 사용할 수 없습니다.");
		}
		
		return "select";
	}
	

	
	// String이 숫자로 형변환이 가능한지 아닌지를 검사하는 메소드 (DB로 옮김)
//	private boolean isIntegerString(String numericString) {
//		try {
//			int result = Integer.parseInt(numericString);
//			return true;
//		} catch (Exception e) {
//			
//		}
//		return false;
//	}
	
}