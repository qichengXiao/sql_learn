package com.cscw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 文件操作工具类
 * @author liguohua
 *
 */
public class FileUtils {
    /**
     * 读取文件
     * @param filePath 文件路径
     * @return
     */
    public static String readFile(String filePath)
    {
        StringBuffer sb=new StringBuffer();
        File file = new File(filePath);
        if(file.exists()&&file.isFile())
        {
            BufferedReader br=null;
            try {
                br= new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
                String str="";
                while((str=br.readLine())!=null)
                {
                    sb.append(str+"\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            return sb.toString();
    }
    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 操作结果
     */
    public static Boolean deleteFile(String filePath)
    {
        File file=new File(filePath);
        if(file.exists()&&file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }
    
}
