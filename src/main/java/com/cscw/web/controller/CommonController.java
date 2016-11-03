package com.cscw.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cscw.common.AppContext;
import com.cscw.service.common.FileService;
import com.cscw.web.common.AjaxData;
import com.cscw.web.common.EditorAjaxData;
import com.cscw.web.common.MVCUtil;
import com.google.gson.JsonObject;

/**
 * 提供公共服务接口的controller
 * @author liguohua
 *
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
    
    /**
     * 文件上传
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	@ResponseBody
	public String fileupload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		AjaxData ajaxdata = null;
		try {
			MultipartFile mf = multipartRequest.getFile("file");
			String fileuri = FileService.saveImage(mf.getBytes(), mf.getOriginalFilename(),"image/article_image/article_");
			JsonObject data = new JsonObject();
			data.addProperty("fileuri", fileuri);
			data.addProperty("fileurl", AppContext.getFileUrl(fileuri));
			ajaxdata = new AjaxData(true, data, null);
		} catch (Exception e) {
			ajaxdata = new AjaxData(false, null, "文件上传失败！" + e.getMessage());
			LOGGER.error(e.getMessage());
		}

		MVCUtil.ajaxJson(ajaxdata);
		return null;
	}
    
    @RequestMapping(value = "/ajax/add_image_upload", method = RequestMethod.POST)
	@ResponseBody
	public JsonObject addImageUpload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	System.out.println(request.getAttributeNames());
    	
    	  Enumeration paramNames = request.getParameterNames();  
    	    while (paramNames.hasMoreElements()) {  
    	      String paramName = (String) paramNames.nextElement();  
    	      System.out.println(paramName);
    	    }
		EditorAjaxData d = null;
		try {
			MultipartFile mf = multipartRequest.getFile("imgFile");
			BufferedImage sourceImg =ImageIO.read(mf.getInputStream());
			String fileuri = FileService.saveImage(mf.getBytes(), mf.getOriginalFilename(),"image/article_image/article_");
		
			String width = null;
			if(sourceImg.getWidth()>500){
				width="500";
			}else{
				width=sourceImg.getWidth()+"";
			}
			d = new EditorAjaxData(true, AppContext.getFiledomain()+fileuri, "messagemy",width);
		} catch (Exception e) {
			//ajaxdata = new AjaxData(false, null, "文件上传失败！" + e.getMessage());
			LOGGER.error(e.getMessage());
		}
		
		return d.getData();
	}
}
