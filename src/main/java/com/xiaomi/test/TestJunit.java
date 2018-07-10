package com.xiaomi.test;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xiaomi.bean.Usermsg;
import com.xiaomi.service.TestService;

@RunWith(SpringJUnit4ClassRunner.class)	
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestJunit {
	private static Logger logger = Logger.getLogger(TestJunit.class);
	@Resource
	private TestService userService = null;


	@Test
	public void test1() {
		List<Usermsg> user = userService.getInfo();
		logger.info(JSON.toJSONString(user));
	}
}
