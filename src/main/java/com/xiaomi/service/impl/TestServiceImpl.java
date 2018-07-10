package com.xiaomi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaomi.bean.Usermsg;
import com.xiaomi.dao.UsermsgMapper;
import com.xiaomi.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private UsermsgMapper usermsgMapper;

	@Override
	public List<Usermsg> getInfo() {
		// TODO Auto-generated method stub
		return usermsgMapper.getInfo();
	}

}
