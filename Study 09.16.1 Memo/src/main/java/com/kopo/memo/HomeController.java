package com.kopo.memo;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

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
	
	// 기존 방식. 홈과 각 jsp 페이지 자체를 호출하는거 제외하고 DB 디렉토리를 객체 클래스로 뺀 방식은 CommonController에 옮겼다.
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
		
		model.addAttribute("test", new Memo());	// jsp파일. 즉, HTML에서 객체를 사용하기 위해 test라는 변수에 담아 넘겨준다.
		
//		Memo memo = new Memo();
//	    memo.setIdx(10);
//	    System.out.println(memo.getIdx());
		
		if (db.open()) {
			ArrayList<Memo> list = db.selectData(new Memo());
			model.addAttribute("list", list);
			db.close();
		}
		return "home";
	}
	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public String create(Locale locale, Model model) {
//		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
//		
//		if (db.open()) {
//			if (db.createTable(new Memo())) {
//				model.addAttribute("message", "테이블이 생성되었습니다.");
//			} else {
//				model.addAttribute("message", "테이블 생성에 실패하였습니다.");
//			}
//			db.close();
//		} else {
//			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
//		}
//		
//		return "message";
//	}

	// insert를 위해 사용
	@RequestMapping(value = "/i1", method = RequestMethod.GET)
	public String i1(Locale locale, Model model) {
		return "i1";
	}
	// insert
//	@RequestMapping(value = "/insert", method = RequestMethod.GET)
//	public String insert(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
//		
//		if (db.open()) {
//			if (request.getParameter("title") != null
//					&& request.getParameter("memo") != null
//					&& db.insertData(new Memo(request.getParameter("title"), request.getParameter("memo")))) {				
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
	
	// select
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String select(Locale locale, Model model) {
//		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
//		
//		if (db.open()) {
//			String htmlString = db.selectStringData(new Memo());
//			model.addAttribute("list", htmlString);
//			db.close();
//		}
//		
//		return "list";
//	}
	
	// update를 위해 사용
	@RequestMapping(value = "/u1", method = RequestMethod.GET)
	public String u1(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
		
		if (db.open()) {
			if (request.getParameter("idx") != null && DB.isIntegerString(request.getParameter("idx"))) {
				Memo memo = (Memo) db.selectData(Integer.parseInt(request.getParameter("idx")), new Memo());
				model.addAttribute("idx", memo.idx);
				model.addAttribute("title", memo.title);
				model.addAttribute("memo", memo.memo);
			} else {
				model.addAttribute("message", "데이터 추가에 실패했습니다.");
			}
			db.close();
		} else {
			model.addAttribute("message", "DB파일을 사용할 수 없습니다.");
		}
		return "u1";
	}
	
	//update
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String update(Locale locale, Model model, HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
////			if (request.getParameter("idx") == null) {
////				model.addAttribute("message", "입력 데이터가 잘못 되었습니다.");
////				return "message";
////			}
//		
//		DB db = new DB<Memo>("/Users/saebyul/SqliteDB/0916memo.db", "memo");
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

