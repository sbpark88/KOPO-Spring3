package com.kopo.people;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		DB db = new DB<People>("/Users/saebyul/SqliteDB/0909people.db", "people");
		
		if (db.open()) {
			try {
				ArrayList<People> resultData = db.selectData(new People());
				model.addAttribute("htmlString", (new People()).toHtmlString(resultData));	// People()을 인스턴스화 해 새 객체로 만든다.
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("htmlString", "데이터를 조회를 할 수 없습니다.");
			}
			
			db.close();
		} else {
			model.addAttribute("htmlString", "DB파일을 사용할 수 없습니다.");
		}
		
		return "home";
	}
	
	// 쿼리를 이용해 테이블 생성하기.
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		DB db = new DB<People>("/Users/saebyul/SqliteDB/0909people.db", "people");
		
		if (db.open()) {
			if (db.createTable(new People())) {
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
	
	// insert 페이지 response
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
	
	// insert 액션
	@RequestMapping(value = "/insert_action", method = RequestMethod.POST)
	public String insertAction(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// DB에 쿼리 전송 및 데이터 유효성 검사
		DB db = new DB<People>("/Users/saebyul/SqliteDB/0909people.db", "people");
		
		if (db.open()) {
			try {
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				String address = request.getParameter("address");

				if (db.insertData(new People(name, phone, address))) {
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
		
		return "redirect:/";
	}
	
//	// update 액션 (기능별 분리)
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String update(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		// 변수 선언 및 초기화
//		int idx = 0;
//		String middleScore = null;
//		int middleScore = 0;
//		int finalScore = 0;
//		
//		// 데이터 유효성 검사
//		try {
//			idx = Integer.parseInt(request.getParameter("idx"));
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
//			if (db.updateData(new Student(idx, name, middleScore, finalScore))) {
//				model.addAttribute("message", "데이터를 추가했습니다.");
//			} else {
//				model.addAttribute("message", "데이터 수정에 실패했습니다.");
//			};
//
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}
	
}
