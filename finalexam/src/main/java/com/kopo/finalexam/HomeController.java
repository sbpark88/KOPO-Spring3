package com.kopo.finalexam;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kopo.finalexam.DB;
import com.kopo.finalexam.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// 메인 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
	    String isLogin = (String)session.getAttribute("is_login");
	    String level = (String)session.getAttribute("level");
	    if(isLogin != null && isLogin.equals("true")) {
	    	if (level.equals("6")) {
	    		return "redirect:/manager";
	    	} else {
	    		return "redirect:/info";
	    	}
	    }
	    else {
	    	return "home";
	    }
	}
	
	// 개인정보 페이지
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Locale locale, Model model, HttpServletRequest request) {
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		
		return "info";
	}
	
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String isLogin = (String)session.getAttribute("is_login");
		String level = (String)session.getAttribute("level");
		
		if(isLogin == null) {
			return "redirect:/";
		} else {
			if (level.equals("6")) {
								
				return "manager";
			} else {
		        return "redirect:/adminfail";
			}
		}
	}
	
	

	// 회원가입 페이지
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Locale locale, Model model, HttpServletRequest request) {
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		
		return "signup";
	}
	
	// 로그인 실패 페이지
	@RequestMapping(value = "/signinfail", method = RequestMethod.GET)
	public String signinfail(Locale locale, Model model, HttpServletRequest request) {
		
		return "signinfail";
	}
	
	// 관리자 인증 실패(권한 부족한 일반 회원) 페이지
	@RequestMapping(value = "/adminfail", method = RequestMethod.GET)
	public String adminfail(Locale locale, Model model, HttpServletRequest request) {
		
		return "adminfail";
	}
	
	// 회원가입 액션
	@RequestMapping(value = "/signup_action", method = RequestMethod.POST)
	public String signuptAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String petname = request.getParameter("petname");

//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pw.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				 hexString.append(hex);
				 pw = hexString.toString();

			}
			System.out.println(hexString.toString());
			User user = new User(id, pw, name, address, phone, petname);
			db.signup(user); //패스워드를 sha-256 기반으로 hexString 함. 

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		db.close();
		return "redirect:/";
	}
	
	// 로그인 액션
	@RequestMapping(value = "/signin_action", method = RequestMethod.POST)
	   public String signin_action(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	      request.setCharacterEncoding("UTF-8");
	      String id = request.getParameter("id");
	      String pw = request.getParameter("pw");
	      
//			DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		  DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
	      db.open();
	      
	      boolean result = false;
	      String level;
	      try {
	         MessageDigest digest = MessageDigest.getInstance("SHA-256");
	         byte[] hash = digest.digest(pw.getBytes("UTF-8"));
	         StringBuffer hexString = new StringBuffer();

	         for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if (hex.length() == 1)
	               hexString.append('0');
	            hexString.append(hex);
	         }
//	         result = db.signin(id, hexString.toString());
	         level = db.signin(id,hexString.toString());
	         if (level != null) {
	        	 result = true;
	         }

	      } catch (Exception ex) {
	         throw new RuntimeException(ex);
	      }
	      db.close();
	      
	      if (result) {
	         HttpSession session = request.getSession();
	         session.setAttribute("is_login", "true");
	         session.setAttribute("id", id);
	         session.setAttribute("level", level);
	         model.addAttribute("id", id);
	         if (level.equals("6")) {
	        	 return "redirect:/manager";
	         } else {
	        	 return "redirect:/info";
	         }
	         
	      } else {
	         return "redirect:/signinfail";
	      }
	   }
	
	// 로그아웃 액션
	@RequestMapping(value = "/signout_action", method = RequestMethod.GET)
	public String logout(Locale locale, Model model, HttpServletRequest request ) {
			HttpSession session = request.getSession();
			session.invalidate();
			return "redirect:/";
	}
	
	// 관리자 페이지 데이터 불러오기 액션
	@ResponseBody
	@RequestMapping(value = "/manager_select", method = RequestMethod.GET)
	public ArrayList<User> managerSelect(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String isLogin = (String)session.getAttribute("is_login");
		String level = (String)session.getAttribute("level");
		
		ArrayList<User> list = null;
		try {
//			DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
			DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
			db.open();
			list = db.selectAllData();
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 관리자 페이지 수정 액션
	@ResponseBody
	@RequestMapping(value = "/manager_update", method = RequestMethod.POST)
	public HashMap<String, Object> managerUpdate(Locale locale, Model model, 
			HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@들어오나?");
		int level = Integer.parseInt(request.getParameter("level"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String petname = request.getParameter("petname");
		String created = "";
		String modified = "";
		User user = new User(id, level, name, address, phone, petname, created, modified);
		System.out.println(id);
		System.out.println(user.id);
		
		try {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//			DB db = new DB("c:\\tomcat\\admin.sqlite", "admin");
			DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
			db.open();
			db.updateManger(user);
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("result", "success");
		return response;
	}
	
	// 관리자 페이지 삭제 액션
//	@ResponseBody
//	@RequestMapping(value = "/history_action", method = RequestMethod.GET)
//	public ArrayList<Color> historyAction(Locale locale, Model model) {
//		ArrayList<Color> list = null;
//		try {
////			DB db = new DB("c:\\tomcat\\colors.db", "colors");
//			DB db = new DB("//Users//saebyul//SqliteDB//colors.db", "colors");
//			db.open();
//			list = db.selectData();
//			db.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}		
//		return list;
//	}
	
	
	
	
	
	
	
	
	
}
