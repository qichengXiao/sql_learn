package com.cscw.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cscw.common.ErrorCode;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.http.model.FormData;
import com.huisa.common.http.model.PostData;
/**
 * 系统对接工具类
 * @author liguohua
 *
 */
@SuppressWarnings("deprecation")
public class AbutmentUtil 
{
    private static Logger LOGGER = LoggerFactory.getLogger(AbutmentUtil.class);
    private static int reconnectCount=3;//请求失败后的重连次数
    /**
     * post请求，请求失败后会重连reconnectCount次
     * @param requestUrl   请求url
     * @param nameValueMap 请求参数的名值对的集合
     * 
     * @return
     * @throws ServiceException
     */
    public static NewHttpResult post(String requestUrl,Map<String,String>nameValueMap) throws ServiceException
    {
        FormData formData = new FormData();
        //请求的参数名值对不为空时，加入formData中
        if(nameValueMap!=null&&nameValueMap.size()>0){
            Set<String>nameSet=nameValueMap.keySet();
            for(String name:nameSet){
                formData.addParam(name, nameValueMap.get(name));
            }
        }
        PostData postData = new PostData(formData);
        NewHttpResult httpResult=null;
        try {
               //httpResult = HttpClientUtil.post(requestUrl, postData);
            httpResult =postMethod(requestUrl, postData);
        }catch (IOException e) {
            e.printStackTrace();
                LOGGER.error("网络异常！");
        } catch (Exception e) {
                LOGGER.error("网络异常！");
        } 
        httpResult=checkConnectResult(httpResult, requestUrl, postData);
        
        return httpResult;
    }
    
    /**
     * 处理对接的结果,实现重连
     * @throws ServiceException 
     */
    public static NewHttpResult checkConnectResult(NewHttpResult httpResult,String requestUrl, PostData postData) throws ServiceException
    {
          int count=0;      
          while((httpResult==null&&count<reconnectCount)||(httpResult.getStatusCode()!=200&&count<reconnectCount))
          {
            try {
                    //httpResult = HttpClientUtil.post(requestUrl, postData);
                    httpResult =postMethod(requestUrl, postData);
                    LOGGER.info(httpResult.getStringData());
                 }catch (IOException e) {
                    LOGGER.error("网络异常！");
                 }catch (Exception e) {
                    LOGGER.error("服务器异常！");
                 }  
            count++;
          }
          if(count>reconnectCount)
              throw new ServiceException(ErrorCode.CODE_NETWORK, "网络异常!");
        return httpResult;
    }
    /**
     * post方法
     * @param url
     * @param postData
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static NewHttpResult postMethod(String url, PostData postData) throws Exception
    {
        HttpClient httpClient = new DefaultHttpClient();
        //建立HttpPost对象
        HttpPost httPost = new HttpPost(url);
        // 设置请求数据
        // form
        if (postData != null && postData.getFormData() != null
                && !postData.getFormData().isEmpty()) {
        	//设置编码
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postData
                    .getFormData().getNvps(), Consts.UTF_8);
            formEntity.setChunked(false);
            httPost.setEntity(formEntity);
        }
        // byte[]
        else if (postData != null && postData.getData() != null
                && postData.getData().length > 0) {
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(
                    postData.getData());
            byteArrayEntity.setChunked(false);
            httPost.setEntity(byteArrayEntity);
        }
        // form
        else if (postData != null && postData.getNvps() != null
                && !postData.getNvps().isEmpty()) {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postData.getNvps(), Consts.UTF_8);
            formEntity.setChunked(false);
            httPost.setEntity(formEntity);
        }
        // stringValue
        else if (postData != null && postData.getStringValue() != null
                && postData.getStringValue().length() > 0) {
            StringEntity stringEntity = new StringEntity(
                    postData.getStringValue(), Consts.UTF_8);
            httPost.setEntity(stringEntity);
        }
        // file
        else if (postData != null && postData.getFile() != null) {
            FileEntity fileEntity = new FileEntity(postData.getFile());
            httPost.setEntity(fileEntity);
        }
        // inputStream
        else if (postData != null && postData.getIns() != null) {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    postData.getIns(), -1);// 消费所有内容
            inputStreamEntity.setChunked(false);
            httPost.setEntity(inputStreamEntity);
        } else if (postData != null && postData.getMpEntity() != null) {
            httPost.setEntity(postData.getMpEntity());
        }
        //发送HttpPost请求 ，返回HttpResponse对象
        HttpResponse response = httpClient.execute(httPost);
        
        // 处理重定向
        int n = 0;
        //返回响应的状态行
        int status = response.getStatusLine().getStatusCode();
        //3xx代表重定向
        while (status >= 300 && status <= 307 && n < 3) {
        	//返回与指定名称的第一个标题信息
            Header location = response.getFirstHeader("Location");
            String redirectUrl = location.getValue();
            HttpGet httpGet = new HttpGet(redirectUrl);
            //发送httprequest请求
            response = httpClient.execute(httpGet);
            status = response.getStatusLine().getStatusCode();
            n++;
        }
        //利用响应头中的encode对响应进行编码
        String encode = getEncode(response.getEntity());
        String responseBody=EntityUtils.toString(response.getEntity(), encode);
        NewHttpResult httpResult = new NewHttpResult(responseBody.getBytes(),
                response.getStatusLine().getStatusCode(),
                response.getAllHeaders(), encode,responseBody);
        return httpResult;
    }
    
    /**
     * 获取响应头中的encode
     * 
     * @param entity
     * @return
     */
    private static String getEncode(HttpEntity entity) {
        if (entity == null) {
            return null;
        }
        String encode = null;
        if (entity.getContentType() != null) {
            HeaderElement[] values = entity.getContentType().getElements();
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    NameValuePair[] nameValuePairs = values[i].getParameters();
                    if (nameValuePairs != null && nameValuePairs.length > 0) {
                        for (int j = 0; j < nameValuePairs.length; j++) {
                            if (nameValuePairs[j].getName().equalsIgnoreCase(
                                    "charset")) {
                                encode = nameValuePairs[j].getValue();
                                break;
                            }
                        }
                    }
                    if (encode != null) {
                        break;
                    }
                }
            }
        }
        return encode;
    }
}
