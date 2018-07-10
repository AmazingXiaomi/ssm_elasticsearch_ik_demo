package com.xiaomi.dao;

import java.util.List;

import com.xiaomi.bean.Usermsg;
import org.springframework.stereotype.Repository;

@Repository
public interface UsermsgMapper {

	List<Usermsg> getInfo();
}