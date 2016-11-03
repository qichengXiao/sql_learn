package com.cscw.service.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 提供生成静态化页面的操作
 * @author liguohua
 *
 */
@Service
public class FreemarkerService{
    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerService.class);
    private static Configuration configuration;
    
  

    /**
     * 初始化模板配置
     * @param templatePath
     */
    public void initConfiguration(String templatePath){
            configuration = new Configuration();
            try {
                configuration.setDirectoryForTemplateLoading(new File(templatePath));
                configuration.setDefaultEncoding("UTF-8");
                configuration.setEncoding(Locale.CHINA, "UTF-8");
                configuration.setWhitespaceStripping(true);
                configuration.setClassicCompatible(true);
                configuration.setURLEscapingCharset("UTF-8");
                configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                configuration.setTemplateUpdateDelay(5);
                configuration.setURLEscapingCharset("UTF-8");
                configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
                configuration.setDateFormat("yyyy-MM-dd");
                configuration.setTimeFormat("HH:mm:ss");
                configuration.setNumberFormat("0.######");
                configuration.setWhitespaceStripping(true);
                configuration.setClassicCompatible(true);              
            } catch (IOException e) {
                LOGGER.error("初始化模板路径失败", e);
            }          
    }
    
    /**
     * 生成静态化的html
     * @param dataModel 要填充的数据
     * @param templatePath 模板路径
     * @param htmlPath  生成html文件的路径
     * @param templateName  模板名称
     * @param htmlName  生成html文件的名称
     */
    public void createHtml(Map<String, Object> dataModel,String templatePath,String htmlPath,String templateName,String htmlName){
        if(configuration==null){
            initConfiguration(templatePath);
        }   
        File htmlRealPath = new File(htmlPath);
        if (!htmlRealPath.exists()) {
            htmlRealPath.mkdirs();
        }
        File htmlFile = new File(htmlRealPath,htmlName);
        Writer out=null;
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile),"UTF-8"));
            Template temp = configuration.getTemplate(templateName);
            temp.process(dataModel, out);
        } catch (IOException e) {
            LOGGER.error(String.format("静态化页面[%s]失败", htmlName), e);
        } catch (TemplateException e) {
            LOGGER.error(String.format("静态化页面[%s]失败", htmlName), e);
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
    }
}
