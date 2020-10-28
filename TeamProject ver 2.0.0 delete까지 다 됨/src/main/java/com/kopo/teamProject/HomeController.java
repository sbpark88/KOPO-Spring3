package com.kopo.teamProject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.SQLException;
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
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {

		} else {
			db.open();
			try {
				String result = db.selectData();
				model.addAttribute("teacherList", result);
				
			} catch (Exception e) {
			}
			db.close();
			
		}

		return "home";
	}
	

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model) {
		return "insert";
	}
	

	@RequestMapping(value = "/insert_action", method = RequestMethod.POST)
	public String insertAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nclass = request.getParameter("nclass");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
//		
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
//				 teacher.pw = hexString.toString();
			}
			System.out.println(hexString.toString());
			Teacher teacher = new Teacher(name, id, pw, nclass, address, phone);
			db.insertData(teacher); //패스워드를 sha-256 기반으로 hexString 함. 

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		db.close();
		return "redirect:/";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model, HttpServletRequest request) {
	      HttpSession session = request.getSession();
	      String isLogin = (String)session.getAttribute("is_login");
	      if(isLogin != null && isLogin.equals("true")) {
	    	  return "success";
	      }
	      else {
	    	  return "login";
	      }
	}

//	id 찾기 구현 부분 1
	@RequestMapping(value = "/findId", method = RequestMethod.GET)
	public String findId(Locale locale, Model model) {
		return "findId";
	}
	
//	id 찾기 구현 부분 2
	@RequestMapping(value = "/findId_action", method = RequestMethod.POST)
	public String findIdAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		boolean findId = false;
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String nclass = request.getParameter("nclass");
		String phone = request.getParameter("phone");
		Teacher check = new Teacher(name, nclass, phone);
		System.out.println(check.name);
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		
		findId = db.checkIdData(check);
		if (findId) {
			Teacher teacher = db.selectData(check);
			model.addAttribute("name", teacher.name);
			model.addAttribute("id", teacher.id);
			db.close();
			return "success_findId";
		}
		else {
			db.close();
			return "findId";
		}
	}
	
	@RequestMapping(value = "/findPw", method = RequestMethod.GET)
	public String findPw(Locale locale, Model model) {
		return "findPw";
	}
	

	@RequestMapping(value = "/findPw_action", method = RequestMethod.POST)
	public String findPwAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		request.setCharacterEncoding("UTF-8");
		boolean findPw = false;
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		System.out.println(id);
		Teacher check = new Teacher(name, id, "", "", "", phone);
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
	    db.open();
	    findPw = db.checkPw(check);
	    System.out.println(findPw);
	    if (findPw) {
	    	model.addAttribute("id", check.id);
	    	return "success_findPW";
	    } else {
	    	return "findPw";
	    }
	
		
	}
	@RequestMapping(value = "/modifiedPw", method = RequestMethod.POST)
	public String modifiedPw(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException, SQLException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
	      db.open();
	      
	      boolean result = false;
//	      password를 sha256을 이용해서 해시처리함 (보안)
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
	         Teacher modify = new Teacher(id, hexString.toString());
	         db.updatePw(modify);
	         

	      } catch (Exception ex) {
	         throw new RuntimeException(ex);
	      }
	      db.close();
	      
	      return "modifiedPw";
	   }
		
		
		
	
	
	   @RequestMapping(value = "/login_action", method = RequestMethod.POST)
	   public String login_action(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	      request.setCharacterEncoding("UTF-8");
	      String id = request.getParameter("id");
	      String pw = request.getParameter("pw");
//	      DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
	      DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
	      db.open();
	      
	      boolean result = false;
//	      password를 sha256을 이용해서 해시처리함 (보안)
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
	         result = db.login(id, hexString.toString());
	         
////	         로그인 성공 시 로그인된 계정의 id와 name을 표시
//	         String htmlText = db.selectData(id, hexString.toString());
//	         model.addAttribute("loginInfo", htmlText);

	      } catch (Exception ex) {
	         throw new RuntimeException(ex);
	      }
	      db.close();
	      
//	      로그인 성공 시 result = true & is_login = true
	      if (result) {
	         HttpSession session = request.getSession();
	         session.setAttribute("is_login", "true");
	         session.setAttribute("id", id);
	         model.addAttribute("id", id);
	         return "success";
	      } else {
	         return "fail";
	      }
	   }
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model, HttpServletRequest request ) {
			HttpSession session = request.getSession();
			session.invalidate();
			return "home";
	}
	
	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public String privatePage(Locale locale, Model model, HttpServletRequest request ) {
			HttpSession session = request.getSession();
			String isLogin = (String) session.getAttribute("is_login");
			if (isLogin != null && isLogin.equals("true")) {
				return "private";
			}
			return "login";
	}
	

	@RequestMapping(value = "/validation", method = RequestMethod.GET)
	public String validation(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}
		model.addAttribute("destination", "verify_action");
		return "validation";
	}
	
	@RequestMapping(value = "/verify_action", method = RequestMethod.POST)
	public String verifyAction(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}
		String inputPw = request.getParameter("pw");
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");

		db.open();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(inputPw.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				 hexString.append(hex);
				 inputPw = hexString.toString();
			}
			Teacher teacher = db.selectData(id);
			String dbPw = teacher.pw;

			if (inputPw.equals(dbPw)) {
				db.close();
				return "redirect:/update";
			}
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
			
		}
		db.close();
		
		model.addAttribute("title", "인증 실패");
		model.addAttribute("message", "비밀번호를 다시 확인하세요.");
		return "updateFail";
	}
	
	@RequestMapping(value = "/validation_pw", method = RequestMethod.GET)
	public String validationPw(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}
		model.addAttribute("destination", "verify_action_pw");
		return "validation";
	}
	
	@RequestMapping(value = "/verify_action_pw", method = RequestMethod.POST)
	public String verifyActionPw(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}
		String inputPw = request.getParameter("pw");
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");

		db.open();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(inputPw.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				 hexString.append(hex);
				 inputPw = hexString.toString();
			}
			Teacher teacher = db.selectData(id);
			String dbPw = teacher.pw;

			if (inputPw.equals(dbPw)) {
				db.close();
				return "redirect:/updatePw";
			}
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
			
		}
		db.close();
		
		model.addAttribute("title", "인증 실패");
		model.addAttribute("message", "비밀번호를 다시 확인하세요.");
		return "updateFail";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		// 로그인 상태 여부 확인.
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}

//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		try {
			Teacher teacher = db.selectData(id);
			model.addAttribute("name", teacher.name);
			model.addAttribute("id", teacher.id);
			model.addAttribute("pw", teacher.pw);
			model.addAttribute("nclass", teacher.nclass);
			model.addAttribute("address", teacher.address);
			model.addAttribute("phone", teacher.phone);
		} catch (Exception e) {
			// TODO: handle exception
		}
		db.close();
		
		return "update";
	}
	
	@RequestMapping(value = "/updatePw", method = RequestMethod.GET)
	public String updatePw(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		// 로그인 상태 여부 확인.
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}

//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		try {
			Teacher teacher = db.selectData(id);
			model.addAttribute("name", teacher.name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		db.close();
		
		return "updatePw";
	}
	
	@RequestMapping(value = "/update_action", method = RequestMethod.POST)
	public String updateAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		String name = (String) request.getParameter("name");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pw = "";
	    String nclass = (String) request.getParameter("nclass");
		String address = (String) request.getParameter("address");
		String phone = (String) request.getParameter("phone");
		String created = "";
		String modified = "";
		Teacher teacher = new Teacher(name, id, pw, nclass, address, phone, created, modified);
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		
		try {
			// 위에 /update에서 로그인 상태 여부 확인과 아마 같은 동작 예상함... 로그아웃으로 세션 지워졌으면 에러 나니까 try catch로 처리.
			request.getSession();
			db.updateData(teacher);
			
		} catch (Exception e) {
			model.addAttribute("title", "정보 수정 실패");
			model.addAttribute("message", "정보 수정에 실패했습니다. 당신은 현재 로그인 상태가 아닙니다. 로그인을 하고 다시 수정해주세요.");
			return "updateFail";
		}
		db.close();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update_pw_action", method = RequestMethod.POST)
	public String updatePwAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		String name = "";
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String pw1 = (String)request.getParameter("password1");
		String pw2 = (String)request.getParameter("password2");
		String pw = "";
		String dbPw = "";
		String nclass = (String) request.getParameter("nclass");
		String address = (String) request.getParameter("address");
		String phone = (String) request.getParameter("phone");
		String created = "";
		String modified = "";
		Teacher teacher = new Teacher(name, id, pw, nclass, address, phone, created, modified);
		
//		DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
		DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
		db.open();
		
		// 새 비밀번호가 유효한 형태인지 확인.
		if ((pw1.equals("")) & (pw2.equals(""))) {
			model.addAttribute("message", "비밀번호는 빈 값일 수 없습니다.");
			return "passwdFail";
		} else if (!pw1.equals(pw2)) {
			model.addAttribute("message", "새 비밀번호가 서로 다릅니다.");
			return "passwdFail";
		} else {
			pw = pw1;
		}
		
		// DB에 저장된 비밀번호와 새 비밀번호를 비교.
		
		try {
			// 세션 유지 확인.
			request.getSession();
			// 새 비밀번호 SHA-256 암호화.
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
			// DB 비밀번호 불러오기.
			dbPw = db.selectPw(teacher);
			
			if (pw.equals(dbPw)) {
				model.addAttribute("message", "새 비밀번호는 기존 비밀번호와 동일할 수 없습니다.");
				return "passwdFail";
			}
			teacher = new Teacher(name, id, pw, nclass, address, phone, created, modified);
			db.updatePw(teacher);

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		
		try {
			// 위에 /update에서 로그인 상태 여부 확인과 아마 같은 동작 예상함... 로그아웃으로 세션 지워졌으면 에러 나니까 try catch로 처리.
			
			
		} catch (Exception e) {
			model.addAttribute("title", "정보 수정 실패");
			model.addAttribute("message", "정보 수정에 실패했습니다. 당신은 현재 로그인 상태가 아닙니다. 로그인을 하고 다시 수정해주세요.");
			return "updateFail";
		}
		db.close();
		return "redirect:/";
	    
	}


	
	@RequestMapping(value = "/delete_view", method = RequestMethod.GET)
	   public String delete_view(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		// 로그인 상태 여부 확인.
		String isLogin = (String)session.getAttribute("is_login");
		if(isLogin == null) {
			return "login";
		}
//	      DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
	      DB db = new DB("//Users//saebyul//SqliteDB//admin.sqlite", "admin");
	      db.open();
	      try {
	         Teacher teacher = db.selectData(id);
	         model.addAttribute("name", teacher.name);
	         model.addAttribute("nclass", teacher.nclass);
	         model.addAttribute("address", teacher.address);
	         model.addAttribute("phone", teacher.phone);
	         
	      } catch (Exception e) {
	         // TODO: handle exception
	      }
	      db.close();
	      return "delete";
	   }
	   

	   @RequestMapping(value = "/delete_action", method = RequestMethod.POST)
	   public String deleteAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	      request.setCharacterEncoding("UTF-8");
	      HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
	      model.addAttribute("id", id);
	      DB db = new DB("C:\\tomcat\\admin.sqlite", "admin") ;
	      db.open();
	      try {
	         Teacher teacher = new Teacher(id);
	         db.deleteData(teacher);
	         
	      } catch (Exception e) {
	         // TODO: handle exception
	      }
	      db.close();
	      return "success";
	   }
	   
	   @RequestMapping(value = "/login_Update_certify", method = RequestMethod.GET)
	   public String loginUpdateCertify(Locale locale, Model model) {
	   
	      return "loginUpdatecertification";
	   }
	      
	   //   public String login_action(Locale locale, Model model
//	            , HttpServletRequest request, @RequestParam("id") String id, @RequestParam("password") String password) {
	   @RequestMapping(value = "/update_certify_action", method = RequestMethod.POST)
	   public String login_update_certify_action(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
	      request.setCharacterEncoding("UTF-8");
	      String id = request.getParameter("id");
	      String pw = request.getParameter("pw");
	      DB db = new DB("C:\\tomcat\\admin.sqlite", "admin");
	      db.open();
	      
	      boolean result = false;
//	         password를 sha256을 이용해서 해시처리함 (보안)
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
	         result = db.login(id, hexString.toString());
	         
////	            로그인 성공 시 로그인된 계정의 id와 name을 표시
//	            String htmlText = db.selectData(id, hexString.toString());
//	            model.addAttribute("loginInfo", htmlText);
	         Teacher teacher = db.selectData(id);
	         model.addAttribute("id", id);
	         model.addAttribute("name", teacher.name);
	         model.addAttribute("address", teacher.address);
	         model.addAttribute("nclass", teacher.nclass);
	         model.addAttribute("phone", teacher.phone);
	      } catch (Exception ex) {
	         throw new RuntimeException(ex);
	      }
	      db.close();
	      System.out.println("여기는 업데이트 로그인");
	      System.out.println(id);
	      
//	         로그인 성공 시 result = true & is_login = true
	      if (result) {
	         HttpSession session = request.getSession();
	         session.setAttribute("is_login", "true");

	         return "update";
	      } else {
	         return "fail";
	      }
	   }
}