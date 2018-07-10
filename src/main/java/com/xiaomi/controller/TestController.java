package com.xiaomi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaomi.bean.Usermsg;
import com.xiaomi.service.TestService;

@Controller
@RequestMapping(value = "/myName")
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public String test() {
		List<Usermsg> list = testService.getInfo();
		return list.toString();
	}
	
}
