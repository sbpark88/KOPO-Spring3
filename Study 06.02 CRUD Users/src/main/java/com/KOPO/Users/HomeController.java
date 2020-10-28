package com.KOPO.Users;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Date;
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

	// 1. 메인 홈
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		// DB 테이블 존재 유무를 콘솔에 출력.
		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
		File f = new File(dbUrl);
		if (f.exists()) {
			System.out.println("DB 테이블 있음.");
		} else {
			System.out.println("DB 테이블 없음.");
		}
		
		// DB 에서 읽어온 데이터를 테이블로 뿌리기
		DataReader dataReader = new DataReader(dbUrl, "users");
		dataReader.open();
		try {
			String result = dataReader.selectData();
			model.addAttribute("userInfo", result);
		} catch (Exception e) {

		}
		dataReader.close();
		
		return "home";
	}
	
	// 2. 회원가입
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Locale locale, Model model) {

		return "signup";
	}
	
	// signup 페이지에 '회원가입'버튼을 만들고, 그 버튼을 누를 경우 무언가 기능을 실행시키도록 명령할 request를 만든다.
	// 데이터를 감추기 위해 request 방식은 POST 방식으로 바꾼다.
	@RequestMapping(value = "/signup_action", method = RequestMethod.POST)	// 어떤 Request를 받으면 동작할건가?
	public String signupAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");		// UTF-8 인코딩.
		String name = request.getParameter("name");		// name이라는 파라미터의 값을 얻는다.
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// DataReader에 Insert 쿼리를 보내는 메소드를 만들고 그것을 실행하도록 명령을 내린다.
		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
		DataReader dataReader = new DataReader(dbUrl, "users");
		
		dataReader.open();		// DataReader를 연다.
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	// SHA 알고리즘 클래스 호출.
			byte[] hash = digest.digest(password.getBytes("UTF-8"));	// 암호화 할 데이터를 "UTF-8"로 읽어 위에서 만든 알고리즘을 이용해 해쉬를 한다.
			StringBuffer hexString = new StringBuffer();				// 문자열을 잠시 담기 위한 버퍼 호출.
			
			// 해쉬한 문자열을 16진수(hex)로 바꿔주는 로직.
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			
			// 이 위에는 password 변수가 담은 값을 SHA-256을 하는 과정이고, 실제 DB에 Insert 쿼리를 보내는건 아래 한 줄이다.
			dataReader.insertData(name, id, hexString.toString());		// password 변수 대신 hexString.toString()을 넣어야 SHA-256 암호화 한 데이터를 보낸다.
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);		// RuntimeException을 추가한다.
		}
		dataReader.close();		// DataReader를 닫는다.
		
		return "signup";	// 어떤 View를 통해 Response를 보낼 것인가?
	}
	
	// 3. 로그인
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin(Locale locale, Model model) {

		return "signin";
	}
	
	// @RequestParam으로 받을 경우 한글이 깨진다. (영어는 괜찮음. 이 경우는 id, password 모두 영어만 있어서 괜찮지만 위 signupAction처럼 HttpServletRequest request로 통일시키자.
//	@RequestMapping(value = "/signin_action", method = RequestMethod.POST)
//	public String signinAction(Locale locale, Model model, HttpServletRequest request, 
//			@RequestParam("id") String id,
//			@RequestParam("password") String password) {
//		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
//		DataReader dataReader = new DataReader(dbUrl, "users");
//		boolean result = false;
//		
//		dataReader.open();		// DataReader를 연다.
//		try {
//			MessageDigest digest = MessageDigest.getInstance("SHA-256");
//			byte[] hash = digest.digest(password.getBytes("UTF-8"));
//			StringBuffer hexString = new StringBuffer();
//			
//			// 해쉬한 문자열을 16진수(hex)로 바꿔주는 로직.
//			for (int i = 0; i < hash.length; i++) {
//				String hex = Integer.toHexString(0xff & hash[i]);
//				if (hex.length() == 1) {
//					hexString.append('0');
//				}
//				hexString.append(hex);
//			}
//
//			result = dataReader.signin(id, hexString.toString());		// DB에 SHA-256으로 해시한 암호가 저장되어 있으니 사용자가 입력한 암호 역시 SHA-256으로 해시해서 보내야 일치하는지 판별이 가능하다. 
//			
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);		// RuntimeException을 추가한다.
//		}
//		dataReader.close();		// DataReader를 닫는다.
//
//		if (result) {
//			HttpSession session = request.getSession();
//			session.setAttribute("is_login", "true");
//			return "success";			
//		} else {
//			return "fail";
//		}
//	}
	
	@RequestMapping(value = "/signin_action", method = RequestMethod.POST)
	public String signinAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");		// UTF-8 인코딩.
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
		DataReader dataReader = new DataReader(dbUrl, "users");
		boolean result = false;
		
		dataReader.open();		// DataReader를 연다.
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			
			// 해쉬한 문자열을 16진수(hex)로 바꿔주는 로직.
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			result = dataReader.signin(id, hexString.toString());		// DB에 SHA-256으로 해시한 암호가 저장되어 있으니 사용자가 입력한 암호 역시 SHA-256으로 해시해서 보내야 일치하는지 판별이 가능하다. 
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);		// RuntimeException을 추가한다.
		}
		dataReader.close();		// DataReader를 닫는다.

		if (result) {
			HttpSession session = request.getSession();
			session.setAttribute("is_login", "true");
			return "success";			
		} else {
			return "fail";
		}
	}
	
	// 3.2 로그인 세션 확인 및 로그아웃
	// 로그인 세션 확인 페이지로 이동.
	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public String privatePage(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String isLogin = (String)session.getAttribute("is_login");
		if (isLogin != null && isLogin.equals("true")) {
			return "private";
		}
		return "signin";		
	}
	
	// 세션 로그아웃.
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String logout(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();	// 세션을 무효화한다.
		return "signin";
	}
	
	// 4. 정보수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(Locale locale, Model model, @RequestParam("idx") String idx) {
		model.addAttribute("idx", idx);
		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
		DataReader dataReader = new DataReader(dbUrl, "users");
		dataReader.open();
		try {
			User sUser = dataReader.selectData(idx);	// 자료형:User 클래스, 변수명:sUser, 값:DataReader가 SELECT한 idx 번호의 객체 row.
			model.addAttribute("name", sUser.name);
			model.addAttribute("id", sUser.id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		dataReader.close();

		return "modify";
	}
	
	@RequestMapping(value = "/modify_action", method = RequestMethod.POST)
	public String modifyAction(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");		// UTF-8 인코딩.
		String idx = request.getParameter("idx");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		String dbUrl = "//Users//saebyul//SqliteDB//users.sqlite";
		DataReader dataReader = new DataReader(dbUrl, "users");
		
		dataReader.open();		// DataReader를 연다.
		try {
			dataReader.updateData(idx, name, id);			
		} catch (Exception ex) {
			throw new RuntimeException(ex);		// RuntimeException을 추가한다.
		}
		dataReader.close();		// DataReader를 닫는다.

		return "modify";
	}
	
}