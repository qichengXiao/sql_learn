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
@RequestMapping(value = "/hill")
public class HillController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "password/hill";
	}

	
	//decrypt
	@RequestMapping(value = "/getJuzhen")
	@ResponseBody
	public void getJuzhen(){
		
		int[][] juzhen = new int[3][3];
//		
//		HillJuzhen juzhen = new HillJuzhen();
//		juzhen.setK11((int) (Math.random()*26));
//		juzhen.setK12((int) (Math.random()*26));
//		juzhen.setK13((int) (Math.random()*26));
//		juzhen.setK21((int) (Math.random()*26));
//		juzhen.setK22((int) (Math.random()*26));
//		juzhen.setK23((int) (Math.random()*26));
//		juzhen.setK31((int) (Math.random()*26));
//		juzhen.setK32((int) (Math.random()*26));
//		juzhen.setK33((int) (Math.random()*26));
		for(int i = 0;i<juzhen.length;i++){
			for(int j=0;j<juzhen[i].length;j++){
				juzhen[i][j]=(int) (Math.random()*26);
			}
		}
		JSONObject o = new JSONObject();
		o.append("juzhen", juzhen);
		o.append("nijuzhen",getNiJuzhen(juzhen));
		MVCUtil.ajaxJson(o.toString());
	
	}
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public void count(HillJuzhen juzhen ) {
	
		String mingwen = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(juzhen==null){
			msg ="矩阵不能为空";
		}
		
		if(!StringUtils.isNotBlank(mingwen)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		//补充字符串，让其成为3的倍数
		int length = mingwen.length();
		StringBuffer mingwenBuffer = new StringBuffer(mingwen);
		StringBuffer miwenBuffer = new StringBuffer();
		if(length%3==1){
			mingwenBuffer.append("xx");
		}else if (length%3==2){
			mingwenBuffer.append("x");
		}
		
		char[] array = mingwenBuffer.toString().toCharArray();
		for(int i = 0;i<array.length;i=i+3){
			int C1 = juzhen.getK11()*array[i]+juzhen.getK12()*array[i+1]+juzhen.getK13()*array[i+2];
			int C2 =  juzhen.getK21()*array[i]+juzhen.getK22()*array[i+1]+juzhen.getK23()*array[i+2];
			int  C3 =  juzhen.getK31()*array[i]+juzhen.getK22()*array[i+1]+juzhen.getK33()*array[i+2];
			miwenBuffer.append((char)(C1+'a'));
			miwenBuffer.append((char)(C2+'a'));
			miwenBuffer.append((char)(C3+'a'));
		}
		
		JsonObject o = new JsonObject();
	//	o.addProperty("mingwen", mingwenBuffer.toString());
	//	o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("miwen",miwenBuffer.toString());
		ajaxdata = new AjaxData(true, o, null);
	}
	
	@RequestMapping(value = "/decrypt")
	@ResponseBody
	public void decrypt(HillJuzhen juzhen) {
		String mingwen = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(juzhen==null){
			msg ="矩阵不能为空";
		}
		
		if(!StringUtils.isNotBlank(mingwen)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		//补充字符串，让其成为3的倍数
		int length = mingwen.length();
		StringBuffer mingwenBuffer = new StringBuffer(mingwen);
		StringBuffer miwenBuffer = new StringBuffer();
		if(length%3==1){
			mingwenBuffer.append("xx");
		}else if (length%3==2){
			mingwenBuffer.append("x");
		}
		
		char[] array = mingwenBuffer.toString().toCharArray();
		for(int i = 0;i<array.length;i=i+3){
			int C1 = juzhen.getK11()*array[i]+juzhen.getK12()*array[i+1]+juzhen.getK13()*array[i+2];
			int C2 =  juzhen.getK21()*array[i]+juzhen.getK22()*array[i+1]+juzhen.getK23()*array[i+2];
			int  C3 =  juzhen.getK31()*array[i]+juzhen.getK22()*array[i+1]+juzhen.getK33()*array[i+2];
			miwenBuffer.append((char)(C1+'a'));
			miwenBuffer.append((char)(C2+'a'));
			miwenBuffer.append((char)(C3+'a'));
		}
		
		JsonObject o = new JsonObject();
	//	o.addProperty("mingwen", mingwenBuffer.toString());
	//	o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("mingwen",miwenBuffer.toString());
		ajaxdata = new AjaxData(true, o, null);
	}

	
	 private int[][] getNiJuzhen(int[][] juzhen) {
	        int[] temp = {1, 0, 0};
	        int[][] nijuzhen = new int[juzhen.length][juzhen[0].length];
	    //    boolean bool;
	        for (int num = 0; num < 3; num++) {
	          //  bool = false;
	            for (int i = 0; i < 26; i++) {
	                for (int j = 0; j < 26; j++) {
	                    for (int k = 0; k < 26; k++) {
	                        if ((i * juzhen[0][0] + j * juzhen[1][0] + k * juzhen[2][0]) % 26 == temp[num % 3]
	                                && ((i * juzhen[0][1] + j * juzhen[1][1] + k * juzhen[2][1]) % 26 == temp[(num + 2) % 3])
	                                && ((i * juzhen[0][2] + j * juzhen[1][2] + k * juzhen[2][2]) % 26 == temp[(num + 1) % 3])) {
	                            nijuzhen[num][0] = i;
	                            nijuzhen[num][1] = j;
	                            nijuzhen[num][2] = k;
	           //                 bool = true;
	                        }
	                    }
	                }
	            }
//	            if (!bool) {
//	                return null;
//	            }
	        }
	       
	        
	        
	        return nijuzhen;
	    }
}

