package com.KOPO.SpringAndSqlite;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
		DataReader dataReader = new DataReader("C:\\tomcat\\ScoreTable.sqlite", "students");
		dataReader.open();	// DB를 사용하기 위해서는 Open 후에 꼭 Close를 해줘야한다.
		try {
//			dataReader.createTable();
//			dataReader.insertData();
			model.addAttribute("query_result", dataReader.selectData());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		} finally {
			dataReader.close();
		}
	
		return "home";
	}
	// "/"에 GET 방식으로 접속 > DataReader 인스턴스화 > DB 엔진 열고 > createTable()메소드를 시켜 쿼리를 보내고 > DB 엔진을 닫고 > 결과를 .jsp파일로 리턴
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Locale locale, Model model) {
		return "NewFile";
	}
	
}
