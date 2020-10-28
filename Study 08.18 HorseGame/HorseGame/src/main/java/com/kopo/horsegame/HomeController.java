package com.kopo.horsegame;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return "home";
	}
	
	// 말 정보 입력 페이지
	@RequestMapping(value = "/input_horse", method = RequestMethod.GET)
	public String inputHorse(Locale locale, Model model) {
		return "input_horse";
	}

	// 말 정보 입력 액션
	@RequestMapping(value = "/horse_insert", method = RequestMethod.POST)
	public String horseInsert(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");	// POST 방식 한글 입력 문제 해결(@RequestParam("name") String name 으로는 한글이 깨진다.)
		
		String name = request.getParameter("name");
		int power = Integer.parseInt(request.getParameter("power"));

		DB db = new DB("c:\\tomcat\\game.db", "horse");
//		DB db = new DB("//Users//saebyul//SqliteDB//game.db", "horse");
		db.open();
		try {
			db.insertData(new Horse(name, power));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();
		return "redirect:input_horse";
	}
	
	// 말 전체 조회 페이지(접속시 데이터 불러와 뿌려주는 액션 포함)
	@RequestMapping(value = "/horse_list", method = RequestMethod.GET)
	public String horseList(Locale locale, Model model) {
		try {
			DB db = new DB("c:\\tomcat\\game.db", "horse");
//			DB db = new DB("//Users//saebyul//SqliteDB//game.db", "horse");
			db.open();
			ArrayList<Horse> horses = db.selectData();
			StringBuffer resultString = new StringBuffer();
			for (int i = 0; i < horses.size(); i++) {
				resultString.append("<tr>");
				resultString.append("<td>" + horses.get(i).idx + "</td>");
				resultString.append("<td>" + horses.get(i).name + "</td>");
				resultString.append("<td>" + horses.get(i).power + "</td>");
				resultString.append("<td><a href='/horsegame/update?idx=" + horses.get(i).idx + "'>수정</a></td>");
				resultString.append("<td><a href='/horsegame/delete_action?idx=" + horses.get(i).idx + "'>삭제</a></td>");
				resultString.append("</tr>");
			}
			model.addAttribute("list_string", resultString);
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "horse_list";
	}
	
	// 말 업데이트 페이지
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateHorse(Locale locale, Model model,
							@RequestParam("idx") int idx) {

		DB db = new DB("c:\\tomcat\\game.db", "horse");
//		DB db = new DB("//Users//saebyul//SqliteDB//game.db", "horse");
		db.open();
		try {
			Horse horse = db.selectData(idx);
			model.addAttribute("idx", horse.idx);
			model.addAttribute("name", horse.name);
			model.addAttribute("power", horse.power);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();

		return "update";
	}
	
	// 말 업데이트 액션
	@RequestMapping(value = "/update_action", method = RequestMethod.POST)
	public String updateAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = (String) request.getParameter("name");
		int power = Integer.parseInt(request.getParameter("power"));
		
		Horse horse = new Horse(idx, name, power);
		
		DB db = new DB("c:\\tomcat\\game.db", "horse");
//		DB db = new DB("//Users//saebyul//SqliteDB//game.db", "horse");
		db.open();		
		try {
			db.updateData(horse);
		} catch (Exception e) {
			model.addAttribute("message", "정보 수정 실패. 다시 시도하세요.");
			return "updateFail";
		}
		db.close();
		return "redirect:horse_list";
	}
	
	// 말 삭제 액션
	@RequestMapping(value = "/delete_action", method = RequestMethod.GET)
	public String deleteHorse(Locale locale, Model model, @RequestParam("idx") int idx) {
		DB db = new DB("c:\\tomcat\\game.db", "horse");
//		DB db = new DB("//Users//saebyul//SqliteDB//game.db", "horse");
		
		db.open();
		try {
			String name = "";
			int power = 0;
			db.deleteData(new Horse(idx, name, power));
		} catch (SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", "정보 삭제 실패. 다시 시도하세요.");
			return "updateFail";
		}
		db.close();
		return "redirect:horse_list";
	}
}
