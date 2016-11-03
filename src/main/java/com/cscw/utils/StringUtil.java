package com.cscw.utils;

import java.util.Random;

public class StringUtil {

	/**
	 *  随机生成指定长度的字符串，length表示生成字符串的长度
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 } 
	

	/**
	 *  判断是否为空白字符串
	 * @param s  传入判断的字符串参数
	 * @return
	 */
	public static boolean isBlank(String s) {
	    if(s==null||s.equals(""))
	    	return true;
	    
	    return false;
	 } 
}
