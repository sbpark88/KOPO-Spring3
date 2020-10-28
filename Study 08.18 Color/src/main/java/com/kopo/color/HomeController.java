package com.kopo.color;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@ResponseBody
	@RequestMapping(value = "/insert_action", method = RequestMethod.GET)
	public HashMap<String, Object> insertAction(Locale locale, Model model
			, @RequestParam("code") String code) {
		try {
			DB db = new DB("c:\\tomcat\\colors.db", "colors");
			db.open();
			db.insertData(new Color(code));
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		HashMap<String, Object> inputResult = new HashMap<String, Object>();
		inputResult.put("result", "success");
		return inputResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/history_action", method = RequestMethod.GET)
	public ArrayList<Color> historyAction(Locale locale, Model model) {
		ArrayList<Color> list = null;
		try {
			DB db = new DB("c:\\tomcat\\colors.db", "colors");
			db.open();
			list = db.selectData();
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	
}
