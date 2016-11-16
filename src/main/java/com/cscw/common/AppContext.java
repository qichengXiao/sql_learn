package com.cscw.common;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


import com.cscw.service.common.FileService;
import com.cscw.service.common.SysconfigService;

import com.cscw.utils.SpringContextHolder;
import com.huisa.common.exception.ServiceException;

/**
 * 系统上下文配置
 * 
 * @author moyuhui
 * 
 */
public class AppContext {
	private static final Logger logger = LoggerFactory
			.getLogger(AppContext.class);
	public static boolean isstarted = false;
	public static boolean logswitch = true;// 请求和响应debug日志开关 （默认关闭）
	private static SysconfigService sysconfigService;

	private static ScheduledExecutorService scheduler = null;
//	private static String filedomain = "http://wx.gdstc.gov.cn/static/file/";
	private static String filedomain = "http://weixin.goamob.com/static/file/";
	private static String imagepath = "/usr/local/tomcat7gdkjtwx/webapps/file/";
	private static String filepath = "E:/LGH/sun-government/src/main/webapp/upload";

	private static FileService fileService;
	private static WebApplicationContext webApplicationContext;// web应用的上下文

	private static String surveyURL;
	
	public static void init() throws Exception {
		//系统服务，从数据库中得到配置信息
		AppContext.sysconfigService = SpringContextHolder
				.getBean(SysconfigService.class);

		//提供对文件操作的服务
		AppContext.fileService = SpringContextHolder.getBean(FileService.class);
		AppContext.webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();

		//测试项目的根路径
		// /E:/tomcat/apache-tomcat-7.0.65/apache-tomcat-7.0.65/webapps/ROOT/WEB-INF/classes/
		// /usr/tomcat/webapps/ROOT/WEB-INF/classes/
		String url = AppContext.class.getClassLoader().getResource("").getPath();
		url = url.substring(0,url.indexOf("WEB-INF"));
		url += "static/file/";
		System.out.println("imagepath = " + url);
		imagepath = url;
		
		// 初始化
		//refreshConfig();
		// 定时器，更新系统配置
		scheduler = Executors.newScheduledThreadPool(3);
		scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
			//	sysconfigService.updateConfig();
			//		AppContext.refreshConfig();
				} catch (Throwable e) {
					logger.error("AppContext.scheduler faild!", e);
				}
			}
		}, 2, 2, TimeUnit.MINUTES);
		
	

		isstarted = true;
	}

	public static void destroy() {
		isstarted = false;
	}

	public static void refreshConfig() {
		// 请求和响应debug日志开关
		if (sysconfigService.getInt("LOG_SWITCH") != null) {
			if (sysconfigService.getInt("LOG_SWITCH") == 1) {
				logswitch = true;
			} else {
				logswitch = false;
			}
		}

		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_PATH"))) {
			filepath = sysconfigService.getValue("FILE_PATH");
		}
		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_DOMAIN"))) {
			filedomain = sysconfigService.getValue("FILE_DOMAIN");
		}
		if (StringUtils.isNotBlank(sysconfigService.getValue("SURVEY_URL"))) {
			surveyURL = sysconfigService.getValue("SURVEY_URL");
		}
	}

	public static String getValueFromSysConfig(String pkey) {
		return sysconfigService.getValue(pkey);
	}

	public static Integer getIntegerFromSysConfig(String pkey) {
		return sysconfigService.getInt(pkey);
	}

	public static String getFileUrl(String fileUri) {
		if (StringUtils.isBlank(fileUri)) {
			return "";
		} else if (!fileUri.startsWith("http")) {
			return AppContext.filedomain + fileUri;
		}
		return fileUri;
	}

	public static String getFiledomain() {
		return filedomain;
	}

	public static String getFilepath() {
		return filepath;
	}
	public static String getImagepath() {
		return imagepath;
	}
	public static String getSurveyURL(){
		return surveyURL;
	}
	
}
