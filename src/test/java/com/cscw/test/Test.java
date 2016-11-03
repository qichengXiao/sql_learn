package com.cscw.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huisa.common.database.BaseDao;
import com.huisa.common.exception.ServiceException;

public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) throws Exception {
		// 生成javabean
		try {
			new BaseDao()
					.generateJavaBean(
							"site_info",
							"E:/LGH/sun-goverment/src/main/java/com/cscw/entity/po",
							"com.cscw.entity.po");
		} catch (ServiceException e) {
		}
	}
}
