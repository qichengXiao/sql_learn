package com.cscw.service.common;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cscw.common.AppContext;
import com.cscw.common.ErrorCode;
import com.google.gson.JsonObject;
import com.huisa.common.exception.Errorcode;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.util.DateCoverd;

/**
 * 提供文件操作的服务
 * @author liguohua
 *
 */
@Service
public class FileService {

    /**
     * 保存照片(.jpg)
     */
	public static JsonObject saveImageFile(byte[] fileBytes, String directory)
			throws ServiceException {
		try {
			String typedir = directory + "/";
			String date = DateCoverd.toString(new Date(), "yyyyMMdd") + "/";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ newfilename.substring(newfilename.length() - 2) + "/";
			String suffix = ".jpg";
			newfilename = newfilename + suffix;
			// uri
			String fileuri = parenturi + newfilename;
			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			String filepath = AppContext.getFilepath() + fileuri;
			// 保存文件
			FileUtils.writeByteArrayToFile(new File(filepath), fileBytes);
			JsonObject reponsedata = new JsonObject();
			reponsedata.addProperty("image_uri", fileuri);
			return reponsedata;
		} catch (Exception e) {
			throw new ServiceException(Errorcode.CODE_JAVA, "upload faild!", e);
		}
	}
	/**
	 * 保存文件
	 */
	public static String saveFile(MultipartFile mf, String filename)
			throws ServiceException {
		String fileuri = "";
		try {
			String typedir = "video/";
			String date = DateCoverd.toString(new Date(), "yyyyMMdd") + "/";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date
					+ newfilename.substring(newfilename.length() - 2) + "/";
			String suffix = "";
			if (filename.contains(".")) {
				suffix = filename.substring(filename.lastIndexOf("."));
			}
			newfilename = newfilename + suffix;

			// uri
			fileuri = parenturi + newfilename;

			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			String filepath = AppContext.getFilepath() + fileuri; 
			//web/mintour/upload/file/                   video/date/xx/name.mp4
			
			// 保存文件
			mf.transferTo(new File(filepath)); 
		} catch (Exception e) {
			throw new ServiceException(ErrorCode.CODE_JAVA, "upload faild!", e);
		}
		return fileuri;
	}
	/**
	 * 保存创新创业大赛视频
	 */
	public static String saveVideo(MultipartFile mf, String filename,String teamName)
			throws ServiceException {
		String fileuri = "";
		String filepath =""; 
		try {
			String typedir = "video/";
			String date = DateCoverd.toString(new Date(), "yyyyMMdd") + "/";
			String newfilename = UUID.randomUUID().toString().replace("-", "");
			String parenturi = typedir + date;
			String suffix = "";
			if (filename.contains(".")) {
				suffix = filename.substring(filename.lastIndexOf("."));
			}
			newfilename = teamName + newfilename  + suffix;

			// uri
			fileuri = parenturi + newfilename;

			// 父目录
			String parentpath = AppContext.getFilepath() + parenturi;
			File parent = new File(parentpath);
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 文件地址
			 filepath = AppContext.getFilepath() + fileuri; 
			//web/mintour/upload/file/                   video/date/UUIDteamName.mp4
			
			// 保存文件
			mf.transferTo(new File(filepath)); 
		} catch (Exception e) {
			throw new ServiceException(ErrorCode.CODE_JAVA, "upload faild!", e);
		}
		return filepath;
	}
	
	/**
	 * 删除文件
	 * @param filePath
	 */
	public void deleteFile(String filePath)
	{
	    com.cscw.utils.FileUtils.deleteFile(filePath);
	}
	/*
	 * 上传  官网首页图片
	 *  @param filename
	 *  @param typedir  类似于image/home_page_image/home_
	 *  @return  uri
	 */
	public static String saveImage(byte[] fileBytes,String filename, String typedir)
            throws ServiceException {
        try {
         //   String typedir = "image/home_page_image/home_";
            String parenturi = typedir;
            String suffix = "";
            String newfilename = filename + suffix;
            // uri
            String fileuri = parenturi + newfilename;
            // 父目录
            String parentpath = AppContext.getFilepath() + parenturi;
            File parent = new File(parentpath);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            // 文件地址
            String filepath = AppContext.getFilepath() + fileuri;
            // 保存文件
            FileUtils.writeByteArrayToFile(new File(filepath), fileBytes);
            return fileuri;
        } catch (Exception e) {
            throw new ServiceException(Errorcode.CODE_JAVA, "upload faild!", e);
        }
    }
	
	
}
