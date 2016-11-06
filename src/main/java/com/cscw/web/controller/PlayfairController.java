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
import com.google.gson.JsonArray;
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
		String mingwen = MVCUtil.getParam("mingwen");
		AjaxData ajaxdata;
		String msg = null;
		if(keyword.length()!=8){
			msg = "输入口令长度必须为8	";
		}
		if(keyword.contains("i")&&keyword.contains("j")	){
			msg="口令不能同时含有ij";
		}
		if(!StringUtils.isNotBlank(mingwen)){
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
		
		
		//替换双字母和最后的单字
		mingwen = fixMingwen(mingwen);
		//转换成密文
	    char[] miwen = getMiwen(mingwen.toCharArray(), juzhen);  
		
		JsonObject o = new JsonObject();
		
		StringBuffer	juzhenString = null ;
		for(char chararray[] :juzhen){
			for(char c :chararray){
				juzhenString.append(c);
			}
			juzhenString.append("\n");
		}
		
		
		o.addProperty("mingwen", mingwen);
		o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("miwen",new String(miwen));
		ajaxdata = new AjaxData(true, o, null);
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
		
		StringBuffer	juzhenString = null ;
		for(char chararray[] :juzhen){
			for(char c :chararray){
				juzhenString.append(c);
			}
			juzhenString.append("\n");
		}
		
		
		o.addProperty("mingwen", new String (getPlain(miwen.toCharArray(), juzhen)));
		o.addProperty("juzhen", juzhenString.toString());
		o.addProperty("miwen",new String(miwen));
		ajaxdata = new AjaxData(true, o, null);
	}
	private void fill( char[][] juzhen , char c,int num){
		int i=0;
		int j=0;
		for( ;i<5;i++){
			for(j=0;j<5;j++){
				if(juzhen[i][j]==c){
					return ;
				}
				if(juzhen[i][j]==0){
					juzhen[i][j]=c;
					return;
				}
				
			}
			
		}
		juzhen[i][j]=c;
	}
	

	
	public String fixMingwen(String mingwen){
		//替换双字母和最后的单字
		char[] plain = mingwen.toCharArray();
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

		return new String(xxplain);
	}
	 private static char[] getMiwen(char[] mingwen, char[][] juzhen){  
	        char[] cipher = new char[mingwen.length];  
	        int index = 0;  
	        for(int i = 0; i < mingwen.length - 1; i += 2){  
	            int row1, row2, col1, col2;  
	            String[] pos1, pos2;  
	            pos1 = getPosition(juzhen, mingwen[i]);  
	            pos2 = getPosition(juzhen, mingwen[i + 1]);  
	            if(pos1 == null || pos2 == null){  
	                throw new RuntimeException("明文中包含无效字符!!!");  
	            }  
	            row1 = Integer.parseInt(pos1[0]);  
	            col1 = Integer.parseInt(pos1[1]);  
	            row2  =Integer.parseInt(pos2[0]);  
	            col2 = Integer.parseInt(pos2[1]);  
	            if(row1 == row2){  
	                // 同一行的情况  
	                if(col1 == juzhen[0].length - 1){  
	                    cipher[index++] = juzhen[row1][0];  
	                    cipher[index++] = juzhen[row1][col2 + 1];  
	                }else if(col2 == juzhen[0].length - 1){  
	                    cipher[index++] = juzhen[row1][col1 + 1];  
	                    cipher[index++] = juzhen[row1][0];  
	                }else{  
	                    cipher[index++] = juzhen[row1][col1 + 1];  
	                    cipher[index++] = juzhen[row1][col2 + 1];  
	                }  
	            }else if(col1 == col2){  
	                //同一列的情况  
	                if(row1 == juzhen.length - 1){  
	                    cipher[index++] = juzhen[0][col1];  
	                    cipher[index++] = juzhen[row2 + 1][col1];  
	                }else if(col2 == juzhen.length - 1){  
	                    cipher[index++] = juzhen[row1 + 1][col1];  
	                    cipher[index++] = juzhen[0][col1];  
	                }else{  
	                    cipher[index++] = juzhen[row1 + 1][col1];  
	                    cipher[index++] = juzhen[row2 + 1][col2];  
	                }  
	            }else{  
	                cipher[index++] = juzhen[row1][col2];  
	                cipher[index++] = juzhen[row2][col1];  
	            }  
	        }  
	        return cipher;  
	    }
	 /** 
	     * 返回字符在矩阵中的位置 
	     * @param matrix 
	     * @param ch 
	     * @return 
	     */  
	    private static String[] getPosition(char[][] matrix, char ch){  
	        String[] pos = new String[]{null, null};  
	        for(int i = 0; i < matrix.length; i++){  
	            for(int j = 0; j < matrix[0].length; j++){  
	                if((matrix[i][j] == ch) || (matrix[i][j] == 'j' && ch == 'i') || (matrix[i][j] == 'i' && ch == 'j')){  
	                    pos[0] = i + "";  
	                    pos[1] = j + "";  
	                    return pos;  
	                }  
	            }  
	        }  
	        return null;  
	    }  

	    /** 
	     * 根据密文和解密矩阵返回明文 
	     * @param cipher 
	     * @param matrix 
	     * @return 
	     */  
	    private static char[] getPlain(char[] cipher, char[][] matrix){  
	        char[] plain = new char[cipher.length];  
	        int index = 0;  
	        for(int i = 0; i < cipher.length; i += 2){  
	            int row1, row2, col1, col2;  
	            String[] pos1, pos2;  
	            pos1 = getPosition(matrix, cipher[i]);  
	            pos2 = getPosition(matrix, cipher[i + 1]);  
	            if(pos1 == null || pos2 == null){  
	                throw new RuntimeException("m密文中包含无效字符!!!");  
	            }  
	            row1 = Integer.parseInt(pos1[0]);  
	            col1 = Integer.parseInt(pos1[1]);  
	            row2  =Integer.parseInt(pos2[0]);  
	            col2 = Integer.parseInt(pos2[1]);  
	            if(row1 == row2){  
	                // 同一行的情况  
	                if(col1 == 0){  
	                    plain[index++] = matrix[row1][matrix[0].length - 1];  
	                    plain[index++] = matrix[row1][col2 - 1];  
	                }else if(col2 == 0){  
	                    plain[index++] = matrix[row1][col1 - 1];  
	                    plain[index++] = matrix[row1][matrix[0].length - 1];  
	                }else{  
	                    plain[index++] = matrix[row1][col1 - 1];  
	                    plain[index++] = matrix[row1][col2 - 1];  
	                }  
	              
	            }else if(col1 == col2){  
	                // 同一列的情况  
	                if(row1 == 0){  
	                    plain[index++] = matrix[matrix.length - 1][col1];  
	                    plain[index++] = matrix[row2 - 1][col1];  
	                }else if(row2 == 0){  
	                    plain[index++] = matrix[row1 - 1][col1];  
	                    plain[index++] = matrix[matrix.length - 1][col1];  
	                }else{  
	                    plain[index++] = matrix[row1 - 1][col1];  
	                    plain[index++] = matrix[row2 - 1][col2];  
	                }  
	            }else{  
	                plain[index++] = matrix[row1][col2];  
	                plain[index++] = matrix[row2][col1];  
	            }  
	        }  
	        return plain;  
	    }  
}
