package com.kopo.memo;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.DB;
import com.kopo.bean.Memo;
import com.kopo.bean.Score;

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
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
				
		return "home";
	}
	
	DB db = new DB(new Memo());	

	@RequestMapping(value = "/u1", method = RequestMethod.GET)
	public String u1(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HashMap resultData = db.detailsData(request);
		model.addAllAttributes(resultData);

		return "u1";
	}
	
	
//	@RequestMapping(value = "/insert", method = RequestMethod.GET)
//	public String insert(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		DB db = new DB<Memo>("c:\\tomcat\\memo.db", "memo");
//		
//		if (db.open()) {
//			if (request.getParameter("title") != null
//					&& request.getParameter("memo") != null
//					&& db.insertData(
//							new Memo(request.getParameter("title"), request.getParameter("memo"))
//							)) {				
//				model.addAttribute("message", "새 데이터를 추가했습니다.");
//			} else {
//				model.addAttribute("message", "데이터 추가에 실패했습니다.");
//			}
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}

	@RequestMapping(value = "/i1", method = RequestMethod.GET)
	public String i1(Locale locale, Model model) {
		return "i1";
	}
	
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String select(Locale locale, Model model) {
//		DB db = new DB<Memo>("c:\\tomcat\\memo.db", "memo");
//		
//		if (db.open()) {
//			String htmlString = db.selectDataString(new Memo());
//			model.addAttribute("list", htmlString);
//			db.close();
//		}
//		
//		return "list";
//	}

//	@RequestMapping(value = "/u1", method = RequestMethod.GET)
//	public String u1(Locale locale, Model model, HttpServletRequest request) {
//		DB db = new DB<Memo>("c:\\tomcat\\memo.db", "memo");
//		
//		if (db.open()) {
//			if (request.getParameter("idx") != null && DB.isIntegerString(request.getParameter("idx"))) {
//				Memo memo = (Memo) db.selectData(Integer.parseInt(request.getParameter("idx")), new Memo());
//				model.addAttribute("idx", memo.idx);
//				model.addAttribute("title", memo.title);
//				model.addAttribute("memo", memo.memo);
//			}
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		return "u1";
//	}
	
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String update(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//				
//		DB db = new DB<Memo>("c:\\tomcat\\memo.db", "memo");
//		
//		if (db.open()) {
//			if (request.getParameter("idx") != null && DB.isIntegerString(request.getParameter("idx"))
//					&& request.getParameter("title") != null
//					&& request.getParameter("memo") != null
//					&& db.updateData(new Memo(Integer.parseInt(request.getParameter("idx"))
//							, request.getParameter("title"), request.getParameter("memo")))) {				
//				model.addAttribute("message", "데이터를 수정했습니다.");
//			} else {
//				model.addAttribute("message", "데이터 수정에 실패했습니다.");
//			}
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}
}
