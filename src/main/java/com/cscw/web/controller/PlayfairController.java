package com.cscw.web.controller;

import java.nio.CharBuffer;
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


import com.cscw.service.UserService;
import com.cscw.web.common.AjaxData;
import com.cscw.web.common.MVCUtil;
import com.google.gson.JsonObject;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;


@Controller
@RequestMapping(value = "/playfair")
public class PlayfairController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "password/playfair";
	}

	
	//decrypt
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public void count(Model model) {
		String keyword = MVCUtil.getParam("keyword");
		String p = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(keyword.length()!=8){
			msg = "输入口令长度必须为8	";
		}
		if(keyword.contains("i")&&keyword.contains("j")	){
			msg="口令不能同时含有ij";
		}
		if(!StringUtils.isNotBlank(p)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
		//将j替换成i
		if(keyword.contains("j")){
			keyword=keyword.replaceAll("j", "i");
		}
		
		
		//生成矩阵
		char[][] juzhen = new char[5][5];
		char[] ca = keyword.toCharArray();
		for(int i = 0;i<10;i++){
			if(i<5)
				juzhen[0][i]=ca[i];
			else
				juzhen[1][i-5]=ca[i];
		}
		
	
	
//		ArrayList<ArrayList> juzhenList = new ArrayList<ArrayList>();
//		for (int i =0 ;i<5 ;i++){
//			juzhenList.add(new ArrayList<Character>());
//		}
		
		//填充剩下的值
		int t = 10;
		for(char i = 'a';i<='z';i++,t++){
			if(i=='j')
				continue;
			fill(juzhen,i, t);
		}
		
		char[] plain = p.toCharArray();
//		StringBuffer sb = new StringBuffer();
//		for(int i = 0  ;i<p.length();i=i+2	){
//			if(i<=p.length()-1){ 
//				Locate locat1 = find(juzhen,pArray[i]);
//				Locate locat2 = find(juzhen,pArray[i+1]);
//			}
//			else{ //i是最后一个字母
//				
//			}
//			
//			
//			sb.append(pArray[i]);
//		}
//		
		
		//替换双字母和最后的单字
		char replace= 'k';
		 CharBuffer xplain = CharBuffer.allocate(plain.length * 2);  
	        // 重新生成明文  
	        int len = 0;  
	        int i = 0;  
	        for(i = 0; i < plain.length - 1; i++){  
	            xplain.append(plain[i]);  
	            if(i != plain.length - 1){  
	                if(plain[i] == plain[i + 1]){  
	                    xplain.append(replace);  
	                }else{  
	                    xplain.append(plain[i + 1]);  
	                    i++;  
	                }  
	            }else{  
	                xplain.append(replace);  
	            }  
	            len += 2;  
	        }  
	        // 剩余最后一个字符  
	        if(i == plain.length - 1){  
	            xplain.append(plain[i]);  
	            xplain.append(replace);  
	            len += 2;  
	        }  
	        char[] xxplain = new char[len];  
	        xplain.position(0);  
	        xplain.get(xxplain);  
	        System.out.println("整理后的明文: " + new String(xxplain));  
	        
	      
		
		
		
		
//		JsonObject o = new JsonObject();
//		o.addProperty("password", new String(pArray));
//		ajaxdata = new AjaxData(true, o, null);
	}
	
	private void fill( char[][] juzhen , char c,int num){
		
		for(int i =0 ;i<5;i++){
			for(int j = 0 ;i<5;j++){
				if(juzhen[i][j]==c){
					return ;
				}
			}
		}
		juzhen[num/5][num%5]=c;
	}
	
	private Locate find( char[][] juzhen , char c){
		if(c=='j') c ='i';
		for(int i =0 ;i<5;i++){
			for(int j = 0 ;i<5;j++){
				if(juzhen[i][j]==c){
					return new Locate(i,j);
				}
			}
		}
		return null;
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
		char[] plain = "ttest".toCharArray();
		char replace= 'k';
		 CharBuffer xplain = CharBuffer.allocate(plain.length * 2);  
	        // 重新生成明文  
	        int len = 0;  
	        int i = 0;  
	        for(i = 0; i < plain.length - 1; i++){  
	            xplain.append(plain[i]);  
	            if(i != plain.length - 1){  
	                if(plain[i] == plain[i + 1]){  
	                    xplain.append(replace);  
	                }else{  
	                    xplain.append(plain[i + 1]);  
	                    i++;  
	                }  
	            }else{  
	                xplain.append(replace);  
	            }  
	            len += 2;  
	        }  
	        // 剩余最后一个字符  
	        if(i == plain.length - 1){  
	            xplain.append(plain[i]);  
	            xplain.append(replace);  
	            len += 2;  
	        }  
	        char[] xxplain = new char[len];  
	        xplain.position(0);  
	        xplain.get(xxplain);  
	        System.out.println("整理后的明文: " + new String(xxplain));  
	}
	
	private class Locate {
		private int i;
		private int j;
		public Locate(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}
		

	}

}
