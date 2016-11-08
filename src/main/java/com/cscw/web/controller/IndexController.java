package com.cscw.web.controller;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.ResponseBody;






import com.cscw.entity.po.HillJuzhen;
import com.cscw.service.UserService;
import com.cscw.web.common.AjaxData;
import com.cscw.web.common.MVCUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;


@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "portal/index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		
		return "portal/index";
	}
	
	@RequestMapping(value = "/portal/home", method = RequestMethod.GET)
	public String home(Model model) {
		
		return "portal/home";
	}

	

}

