package com.KOPO.CRUD_jQuery;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "redirect:/resources/home.html";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/search_category", method = RequestMethod.GET)
	public String[] searchCategory(Locale locale, Model model) {
		Contents contents = new Contents();
		String response[] = contents.category;
					
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/content_write", method = RequestMethod.POST)
	public int contentWrite(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String selectedCat = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		System.out.println(title);
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//freeboard.db", "contents");
		db.open();
		try {
			Contents contents = new Contents(selectedCat, title, content, nickname, password);
			db.insertData(contents); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		db.close();
		
		return 0;
	}
	
//	@ResponseBody
//	@RequestMapping(value = "/content_write", method = RequestMethod.POST)
//	public int contentWrite(Locale locale, Model model, HttpServletRequest request, @RequestParam Map<String, String> testmap) throws UnsupportedEncodingException {
//		request.setCharacterEncoding("UTF-8");
//		
//		String aaaa = testmap.get("category");
//		System.out.println(aaaa);
//			
//		return 0;
//	}
	

//	@ResponseBody
//	@RequestMapping(value = "/info", method = RequestMethod.GET)
//	public HashMap<String, Object> info(Locale locale, Model model) {
//		HashMap<String, Object> infoData = new HashMap<String, Object>();
//		infoData.put("name", "JSON TEST app");
//		infoData.put("version", 1);
//		infoData.put("version_code", "1.0.1");
//		return infoData;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/history_action", method = RequestMethod.GET)
//	public ArrayList<Color> historyAction(Locale locale, Model model) {
//		ArrayList<Color> list = null;
//		try {
//			DB db = new DB("c:\\tomcat\\colors.db", "colors");
//			db.open();
//			list = db.selectData();
//			db.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}		
//		return list;
//	}
	
}