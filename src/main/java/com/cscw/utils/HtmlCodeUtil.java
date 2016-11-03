package com.cscw.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlCodeUtil {
    /**
     * 给&,<,>,\',\"进行编码
     * @param str
     * @return
     */
	public static String encode(String str){

        String    s    =    "";  
        if    (str.length()    ==    0)    return    "";  
        s    =    str.replaceAll("&",    "&amp;");  
        s    =    s.replaceAll("<",        "&lt;");  
        s    =    s.replaceAll(">",        "&gt;");  
        s    =    s.replaceAll("\'",      "'");  
        s    =    s.replaceAll("\"",      "&quot;");  
        return    s;  
	}
	 /**
     * 给&,<,>,\',\"进行解码
     * @param str
     * @return
     */
	public static String decode(String str){

        String    s    =    "";  
        if    (str.length()    ==    0)    return    "";  
        s    =    str.replaceAll("&amp;",    "&");  
        s    =    s.replaceAll("&lt;",        "<");  
        s    =    s.replaceAll("&gt;",        ">");  
        s    =    s.replaceAll("'",      "\'");  
        s    =    s.replaceAll("&quot;",      "\"");  
        return    s;  
	}
	public static String url(String str){
		return str.replace("$param$", "?").replace("$and$", "&").replace("$percent$", "%");
	}
	/**
	 * 替换字符串中的html标签
	 * @param html
	 * @return
	 */
	public static String replaceHtml(String html){ 
        String regEx="<.+?>"; //表示标签 
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(html); 
        String s=m.replaceAll(""); 
        return s; 
    }

	/**
	 * 替换图片的src 
	 * @param html
	 * @return
	 */
    public static String replaceImgSrc(String html){ 
        String regEx="src=\""; //src=" 
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(html); 
        String s=m.replaceAll("src=\"http://www.gdstc.gov.cn"); 
        return s; 
    }
    /**
     * 替换img的src和href的url
     */
    public static Document replaceUrlByAbsUrl(Document doc)
    { 
        Elements imgs = doc.select("img[src]");
        for (Element img : imgs) {
            String imgUrl = img.absUrl("src");
            img.attr("src", imgUrl);
        }
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String hrefUrl =link.absUrl("href");
            link.attr("href", hrefUrl);
        }
        return doc;
    }
    
    /**
     * 替换通知公告中的img标签(只能用于特定的情况)
     */
    public static String replaceBadHtml(String html)
    { 
        String regEx="<img [\\s]*src=['\"]([^'\"]+)[^>]*>"; //表示标签 
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(html); 
        String s=m.replaceAll("@"); 
        return s;
    }
    
    /**
     * 替换工作提醒中的坏标签(只能用于特定的情况)
     */
    public static String replaceATab(String html)
    { 
        String regEx="<a[^>]*>"; //表示标签 
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(html); 
        String s=m.replaceAll(""); 
        
        regEx="</a>";
        p=Pattern.compile(regEx);
        m=p.matcher(s);
        s=m.replaceAll("");
        
        regEx="<span>";
        p=Pattern.compile(regEx);
        m=p.matcher(s);
        s=m.replaceAll("");
        
        regEx="</span>";
        p=Pattern.compile(regEx);
        m=p.matcher(s);
        s=m.replaceAll("");
        return s;
    }
}
