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
@RequestMapping(value = "/rsa")
public class RsaController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
	   
	    int p ;
	    int q ; 
	    int n ,n2,d,e=3533;
	    
	    boolean flag=false;
	    //生成p、q  两个100-200的素数
	    p=createSuShu();
	    q=createSuShu();
	   //计算n
	    n=p*q;
	    //计算φ(n)
	    n2=(p-1)*(q-1);
	    //求d
	    int i = 1;
	    while( (n2*i+1)%e!=0){
	    	i++;
	    }
	    d=(n2*i+1)/e;
	    System.out.println("公钥e:"+ e +"n:"+n);
		System.out.println("秘钥d:"+ d +"n:"+n);
		model.addAttribute("e",e);
		model.addAttribute("n",n);
		model.addAttribute("d",d);
		model.addAttribute("p",p);
		model.addAttribute("q",q);
		
		return "password/rsa";
	}

	
	
	
	/*
	 * 加密
	 */
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public void encrypt(Model model) {
		int e = MVCUtil.getIntParam("e");
		int n = MVCUtil.getIntParam("n");
		String mingwen = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		//验证
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
	    char[] arrayc = mingwen.toCharArray();
	    int niwenNum ;
	    //加密
	    StringBuffer miwen = new StringBuffer(50);
	    for(int i = 0 ;i<arrayc.length;i++){
	    	niwenNum=modCount(arrayc[i], e, n);
	    	//规整为5位的字符串添加到字符串miwen中
	    	translateToFiveChar(miwen,niwenNum);
	    }
	    
	    JsonObject o  = new JsonObject();
	    o.addProperty("miwen", miwen.toString());
		o.addProperty("msg", "注意：密文是以五个字符单位的，一个明文字符转换一个五位的整型");
		ajaxdata = new AjaxData(true, o, null);
		MVCUtil.ajaxJson(ajaxdata);
		return;
	}
	
	/*
	 * 解密
	 */
	@RequestMapping(value = "/decrypt")
	@ResponseBody
	public void decrypt(Model model) {
		int d = MVCUtil.getIntParam("d");
		int n = MVCUtil.getIntParam("n");
		String miwen = MVCUtil.getParam("miwen");
		AjaxData ajaxdata;
		String msg = null;
		//验证
		if(n==0||d==0){
			msg = "请正确输出私钥d、n	";
		}
		
		if(!StringUtils.isNotBlank(miwen)){
			msg = "输入密文不能为空";
					
		}
		if (StringUtils.isNotBlank(msg)) {
			ajaxdata = new AjaxData(false, null, msg);
	           MVCUtil.ajaxJson(ajaxdata);
	           return;
	    }
	    char[] arrayc = miwen.toCharArray();
	    StringBuffer mingwen = new StringBuffer(50);
	    //以五个字符为单位
	    for(int i=0;i<arrayc.length;i=i+5){
	    	//转换成整形
	    	String s = ""+arrayc[i]+ arrayc[i+1]+ arrayc[i+2]+ arrayc[i+3]+ arrayc[i+4];
	    	int mi_num = Integer.parseInt(s);
	    	//解密
	    	char c = (char)modCount(mi_num, d, n);
	    	mingwen.append(c);
	    }
	    
	   
	    
	    JsonObject o  = new JsonObject();
	    o.addProperty("mingwen", mingwen.toString());
		o.addProperty("msg", "注意：密文是以五个字符单位的，一个明文字符转换一个五位的整型");
		ajaxdata = new AjaxData(true, o, null);
		MVCUtil.ajaxJson(ajaxdata);

	}
	
	
	/*
	 * 生成素数
	 */
	private int createSuShu(){
		int p ;
		 boolean flag=false;
		    do{
		    	p= 	(int)(Math.random()*100)+100;
		    	 flag =true;
		    	for(int i=2;i<p;i++){
					if(p%i==0){
						flag = false;
						break;
					}
				}
		    }while(flag==false);
		  return p;
	}
	
	
	/*
	 * 规定每位密文 占5个字符（因为p、q最大200 n最大40000，最多5位，所求的密文肯定小于n）
	 */
	
	private void translateToFiveChar(StringBuffer s ,int num){
		 String str = String.valueOf(num);
		 int length = 5-str.length();
		 for(int i = 0 ; i<length ; i ++ ){
	       s.append("0");
		 }
		 s.append(str);
	}

	/*
	 * 模指运算  
	 * @param a 明文或密文
	 * @param ed 明文为e 密文为d
	 * @param n  n
	 */

	 private int modCount(int a, int ed, int n) {
	        if (ed == 0) {
	            return 1;
	        }
	        int r = a % n;
	        int k = 1;
	        while (ed > 1) {
	            if ((ed & 1) != 0) {
	                k = (k * r) % n;
	            }
	            r = (r * r) % n;
	            ed >>= 1;
	        }
	        return (r * k) % n;
	    }
	 
	 @Test
	 public void test(){
		 int mi = modCount('a', 3533, 11413);
		 int ming = modCount(mi, 6597, 11413);
		 System.out.println(mi+""+(char)ming);
	 }
}
