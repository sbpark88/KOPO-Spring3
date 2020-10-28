package com.kopo.crud1;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {
	// CREATE 쿼리
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		if (db.open()) {
			if (db.createTable(new User())) {
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
	
	// INSERT 쿼리
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		// 데이터 유효성 검사
		if (request.getParameter("name") == null) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		if (request.getParameter("age") == null || !DB.isIntegerString(request.getParameter("age"))) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		if (request.getParameter("memo") == null) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		
		if (db.open()) {
			if (db.insertData(new User(request.getParameter("name"),	// 파라미터 가져오기
										Integer.parseInt(request.getParameter("age")),
										request.getParameter("memo")))) {
				model.addAttribute("message", "새 데이터를 추가했습니다.");
			} else {
				model.addAttribute("message", "데이터 추가에 실패했습니다.");
			};
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// UPDATE 쿼리
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		// 데이터 유효성 검사
		if (request.getParameter("idx") == null || !DB.isIntegerString(request.getParameter("idx"))) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		if (request.getParameter("name") == null) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		if (request.getParameter("age") == null || !DB.isIntegerString(request.getParameter("age"))) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		if (request.getParameter("memo") == null) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		
		if (db.open()) {
			if (db.updateData(new User(Integer.parseInt(request.getParameter("idx")),	// 파라미터 가져오
										request.getParameter("name"),
										Integer.parseInt(request.getParameter("age")),
										request.getParameter("memo")))) {
				model.addAttribute("message", "데이터를 수정했습니다.");
			} else {
				model.addAttribute("message", "데이터 수정에 실패했습니다.");
			};
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// DELETE 쿼리
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		DB db = new DB<User>("/Users/saebyul/SqliteDB/CRUD_Summary.db", "user1");
		
		// 데이터 유효성 검사
		if (request.getParameter("idx") == null || !DB.isIntegerString(request.getParameter("idx"))) {
			model.addAttribute("message", "데이터가 올바르지 않습니다.");
			return "message";
		}
		
		if (db.open()) {
			if (db.deleteData(new User(Integer.parseInt(request.getParameter("idx"))))) {
				model.addAttribute("message", "데이터를 삭제했습니다.");
			} else {
				model.addAttribute("message", "데이터 삭제에 실패했습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}

}
