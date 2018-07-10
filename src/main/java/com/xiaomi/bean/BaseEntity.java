package com.xiaomi.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * entity 父类
 * @author liutq
 *
 */
@SuppressWarnings("serial")
public class BaseEntity implements Serializable{

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,
						ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
