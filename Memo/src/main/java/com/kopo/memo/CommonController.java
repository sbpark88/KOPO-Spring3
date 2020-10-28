package com.kopo.memo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.DB;
import com.kopo.bean.Memo;

@Controller
public class CommonController {
	
	DB db = new DB(new Memo());	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Locale locale, Model model) {
		if (db.createTable()) {
			model.addAttribute("message", "테이블이 생성되었습니다.");
		} else {
			model.addAttribute("message", "테이블 생성에 실패하였습니다.");
		}		
		return "message";
	}

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (db.insertData(request)) {
			model.addAttribute("message", "새 데이터를 추가했습니다.");
		} else {
			model.addAttribute("message", "데이터 추가에 실패했습니다.");
		}		
		return "message";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (db.updateData(request)) {
			model.addAttribute("message", "데이터를 수정했습니다.");
		} else {
			model.addAttribute("message", "데이터 수정에 실패했습니다.");
		}		
		return "message";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String select(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
//		ArrayList resultData = db.selectData();
		String listString = db.selectDataString();
		model.addAttribute("list", listString);
		return "list";
	}

	@RequestMapping(value = "/select_api", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList selectApi(Locale locale, Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return db.selectData();
	}
}
