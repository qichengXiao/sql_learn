package com.cscw.test;

import java.util.Calendar;

import net.sf.json.JSONObject;

import org.junit.Test;

public class MyTest {
    @Test
    public void test1(){
       String a ="{ \"errcode\": 40001,\"errmsg\": \"invalid credential, access_token is invalid "
       		+ "or not latest hint: [lt6VmA0811rsz4]\"}\";}";
       System.out.println(a);
       result = JSONObject.fromObject(a);
    }
    
    
    
}
