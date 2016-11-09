package com.cscw.web.controller;

import java.math.BigDecimal;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;


@Controller
@RequestMapping(value = "/playfair")
public class RsaController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
	    //生成两个100-200的素数
	    int p ;
	    int q ; 
	    int n ,n2,d,e=3;
	    
	    boolean flag=false;
	    do{
	    	p= 	(int)(Math.random())*100;
	    	 flag =false;
	    	for(int i=2;i<p;i++){
				if(p%i==0){
					flag = true;
				}
			}
	    }while(flag==false);
	    
	    do{
	    	q= 	(int)(Math.random())*100;
	    	 flag =false;
	    	for(int i=2;i<q;i++){
				if(q%i==0){
					flag = true;
				}
			}
	    }while(flag==false);
	   
	    n=p*q;
	    n2=(p-1)*(q-1);
	    
	    int i = 1;
	    int t;
	    do{
			   d=(n*i+1)/e;
			   i++;
			  t =(d*e)%n;
		  }while(t==1);
		   System.out.println("公钥e:"+ e +"n:"+n);
		   System.out.println("秘钥d:"+ d +"n:"+n);
	
		return "password/playfair";
	}

	
	//decrypt
	@Autowired(required = false)
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public void count(Model model) {
		int e = MVCUtil.getIntParam("e");
		int n = MVCUtil.getIntParam("n");
		String mingwen = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(n==0||e==0){
			msg = "请正确输出公钥e、n	";
		}
		
		if(!StringUtils.isNotBlank(mingwen)){
			msg = "输入明文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
	    char[] arrayc = mingwen.toLowerCase().toCharArray();
	   
	    int[] result = new int [arrayc.length];
	    for(int i = 0 ;i<arrayc.length;i++){
	    	int num = arrayc[i];
	    	int a = (int) (Math.pow(num, e));
	    	result[i]=( a  )%n;
	    }
	     
	    o.addProperty("mingwen", new String (getPlain(miwen.toCharArray(), juzhen)));
		o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("miwen",new String(miwen));
		ajaxdata = new AjaxData(true, o, null);
		MVCUtil.ajaxJson(ajaxdata);
		
	    //采用一个字符一个分组，这样能保证不会超出范围
	    

	}
	
	@RequestMapping(value = "/decrypt")
	@ResponseBody
	public void decrypt() {
		String keyword = MVCUtil.getParam("keyword");
		String miwen = MVCUtil.getParam("miwen");
		AjaxData ajaxdata;
		String msg = null;
		if(keyword.length()!=8){
			msg = "输入口令长度必须为8	";
		}
		if(keyword.contains("i")&&keyword.contains("j")	){
			msg="口令不能同时含有ij";
		}
		if(!StringUtils.isNotBlank(miwen)){
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
		for(int i = 0;i<8;i++){
			if(i<5)
				juzhen[0][i]=ca[i];
			else
				juzhen[1][i-5]=ca[i];
		}
		
		// 填充剩下的值
		int t = 7;
		for (char i = 'a'; i <= 'z'; i++, t++) {
			if (i == 'j')
				continue;
			fill(juzhen, i, t);
		}
		

		JsonObject o = new JsonObject();
		
		StringBuffer	juzhenString = new StringBuffer() ;
		for(char chararray[] :juzhen){
			for(char c :chararray){
				juzhenString.append(" ");
				juzhenString.append(c);
			}
			juzhenString.append("\n");
		}
		
		
		o.addProperty("mingwen", new String (getPlain(miwen.toCharArray(), juzhen)));
		o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("miwen",new String(miwen));
		ajaxdata = new AjaxData(true, o, null);
		MVCUtil.ajaxJson(ajaxdata);
	}
	@Test
	public void test(){
			int p=101 ;
		    int q=113 ; 
		    int n ,n2,d,e=3;
		    
		    boolean flag=false;
//		    do{
//		    	p= 	(int)(Math.random())*100;
//		    	 flag =false;
//		    	for(int i=2;i<p;i++){
//					if(p%i==0){
//						flag = true;
//					}
//				}
//		    }while(flag==false);
//		    
//		    do{
//		    	q= 	(int)(Math.random())*100;
//		    	 flag =false;
//		    	for(int i=2;i<q;i++){
//					if(q%i==0){
//						flag = true;
//					}
//				}
//		    }while(flag==false);
		   
		    n=p*q;
		    n2=(p-1)*(q-1);
		    
		    int i = 1;
		    int t ;
		   do{
			   d=(n*i+1)/e;
			   i++;
			  t =(d*e)%n;
		   }while(t==1);
			
		   System.out.println("e:"+ e +"n:"+n);
		   System.out.println("d:"+ d +"n:"+n);
	}
}
