package com.kopo.crud1;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "home";
	}
	
	// '<a href="home">홈으로</a>'를 위해 사용.
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String alternativehome(Locale locale, Model model) {
		
		return "redirect:/";
	}
	
	// insert ( i1 ) 페이지
	@RequestMapping(value = "/i1", method = RequestMethod.GET)
	public String i1(Locale locale, Model model) {
		return "i1";
	}	
	
	// select all ( list ) 페이지
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String select(Locale locale, Model model) {
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		if (db.open()) {
			try {
				// DB의 ArrayList<T> resultDataSet, User 객체의 toHtmlString Step 1~3과 함께 사용.
//				ArrayList<User> resultData = db.selectData(new User());
//				model.addAttribute("htmlString", (new User()).toHtmlString(resultData));	// User()를 인스턴스화 해 새 객체로 만든다.
				
				String listString = db.selectDataString(new User());
				model.addAttribute("list", listString);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("list", "데이터를 조회할 수 없습니다.");
			}
		} else {
			model.addAttribute("list", "DB파일을 사용할 수 없습니다.");
		}
		db.close();
		return "list";
	}
	
	// update ( u1 ) 페이지
	@RequestMapping(value = "/u1", method = RequestMethod.GET)
	public String u1(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (request.getParameter("idx") == null || !DB.isIntegerString(request.getParameter("idx"))) {
			model.addAttribute("message", "데이터가 선택되지 않았습니다.");
			return "message";
		}
		
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		if (db.open()) {
			try {
//				HashMap resultData = db.detailsData(Integer.parseInt(request.getParameter("idx")), new User());
				HashMap resultData = db.detailsData(new User(Integer.parseInt(request.getParameter("idx"))));
				model.addAllAttributes(resultData);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("list", "데이터를 조회할 수 없습니다.");
			}
		} else {
			model.addAttribute("list", "DB파일을 사용할 수 없습니다.");
		}
		db.close();

		return "u1";
	}
	
}
