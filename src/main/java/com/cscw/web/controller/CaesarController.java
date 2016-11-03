package com.cscw.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.ResponseBody;












import com.cscw.entity.po.UserInfo;
import com.cscw.entity.vo.LoginInfoVo;
import com.cscw.entity.vo.QueryUserVo;
import com.cscw.service.UserService;
import com.cscw.web.common.AjaxData;
import com.cscw.web.common.MVCUtil;
import com.google.gson.JsonObject;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;


@Controller
@RequestMapping(value = "/caesar")
public class CaesarController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "password/caesar";
	}

	
	//decrypt
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public void count(Model model) {
		int num = MVCUtil.getIntParam("mingwen_num");
		String p = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(num<1||num>25){
			msg = "输入口令不规范	";
		}
		if(!StringUtils.isNotBlank(p)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		char[] pArray = p.toLowerCase().toCharArray();
		for(int i = 0 ;i<pArray.length;i++	){
			int ci = (int)pArray[i] - 'a';
			pArray[i]= (char) ((ci+num)%26) ;
		}
		JsonObject o = new JsonObject();
		o.addProperty("password", new String(pArray));
		ajaxdata = new AjaxData(true, o, null);
	}
	
	@RequestMapping(value = "/decrypt")
	@ResponseBody
	public void decrypt() {
		int num = MVCUtil.getIntParam("miwen_num");
		String p = MVCUtil.getParam("miwen");
		AjaxData ajaxdata;
		String msg = null;
		if(num<1||num>25){
			msg = "输入口令不规范	";
		}
		if(!StringUtils.isNotBlank(p)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		char[] pArray = p.toLowerCase().toCharArray();
		for(int i = 0 ;i<pArray.length;i++	){
			int ci = (int)pArray[i] - 'a';
			pArray[i]= (char) ((ci-num)%26) ;
		}
		JsonObject o = new JsonObject();
		o.addProperty("password", new String(pArray));
		ajaxdata = new AjaxData(true, o, null);
	}

	@Test
	public void test(){
		int num =3;
		String p ="abcd";
		AjaxData ajaxdata;
		String msg = null;
		if(num<1||num>25){
			msg = "输入口令不规范	";
		}
		if(!StringUtils.isNotBlank(p)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		char[] pArray = p.toLowerCase().toCharArray();
		for(int i = 0 ;i<pArray.length;i++	){
			int ci = (int)pArray[i] - 'a';
			pArray[i]= (char) ((ci+num)%26+'a') ;
		}
		System.out.println(new String(pArray));
	}
	
	

}
