package com.kopo.memo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {
	
	DB db = new DB(new Memo());
	DB db2 = new DB(new People());
	
	// create table
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {		
		if (db.open()) {
			if (db.createTable()) {
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
	
	// 이런식으로 DB를 2개 만들어 2개 이상의 테이블을 사용할 수 있다.
	@RequestMapping(value = "/create2", method = RequestMethod.GET)
	public String create2(Locale locale, Model model) {		
		if (db2.open()) {
			if (db2.createTable()) {
				model.addAttribute("message", "테이블이 생성되었습니다.");
			} else {
				model.addAttribute("message", "테이블 생성에 실패하였습니다.");
			}
			db2.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// insert
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insert(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (db.open()) {
			if (db.insertData(request)) {				
				model.addAttribute("message", "새 데이터를 추가했습니다.");
			} else {
				model.addAttribute("message", "데이터 추가에 실패했습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// update
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	//		if (request.getParameter("idx") == null) {
	//			model.addAttribute("message", "입력 데이터가 잘못 되었습니다.");
	//			return "message";
	//		}
		
		if (db.open()) {
			if (db.updateData(request)) {				
				model.addAttribute("message", "데이터를 수정했습니다.");
			} else {
				model.addAttribute("message", "데이터 수정에 실패했습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "message";
	}
	
	// select
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String select(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (db.open()) {
			try {
				String listString = db.selectDataString();
				model.addAttribute("list", listString);				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("htmlString", "데이터를 조회를 할 수 없습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		
		return "list";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (db.deleteSelectedOne(request)) {
			model.addAttribute("message", "데이터를 삭제했습니다.");
		} else {
			model.addAttribute("message", "데이터 삭제에 실패했습니다.");
		}		
		return "message";
	}

	
	
	
}