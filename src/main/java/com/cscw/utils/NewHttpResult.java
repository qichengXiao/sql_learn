package com.cscw.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.ParseException;

public class NewHttpResult 
{
    private byte[] data = null;
    private int statusCode;
    private Header[] headers = null;
    private String encode = null;
    private String responseBody=null;

    public NewHttpResult(byte[] data, int statusCode, Header[] headers,
            String encode,String responseBody) {
        this.data = data;
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseBody=responseBody;
        if (StringUtils.isBlank(encode)) {
            this.encode = "utf-8";
        } else {
            this.encode = encode;
        }
    }

    /**
     * 获取http请求返回的数据byte[]
     * 
     * @return
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 获取http请求返回结果码
     * 
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 获取http请求返回响应头信息
     * 
     * @return
     */
    public Header[] getHeaders() {
        return headers;
    }

    /**
     * 获取经响应头中的encode进行编码过的响应的字符串数据
     * 
     * @return
     */
    public String getStringData() {
        return responseBody;
    }

    /**
     * 根据自定义的编码获取字符串数据
     * 
     * @param encode
     * @return
     */
    public String getStringData(String encode) {
        String data_s = null;
        try {
            if (responseBody != null) {
                //用默认字符编码解码字符串。
                byte[] bs = responseBody.getBytes();
                //用新的字符编码生成字符串
                data_s=new String(bs, encode);
               }
        } catch (UnsupportedEncodingException e) {
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data_s;
    }
}
