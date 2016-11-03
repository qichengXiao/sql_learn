package com.cscw.web.protocol.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 用于配置生成日期类型的json字符串的格式
 * @author liguohua
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format;  
    
    public DateJsonValueProcessor(String format) {
        super();
        this.format = format;
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig arg1) {
        String[] obj = {};  
        if (value instanceof Date[])  
        {  
            SimpleDateFormat sf = new SimpleDateFormat(format);  
            Date[] dates = (Date[]) value;  
            obj = new String[dates.length];  
            for (int i = 0; i < dates.length; i++)  
            {  
                obj[i] = sf.format(dates[i]);  
            }  
        }  
        return obj;  
    }

    @Override
    public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
        if (value instanceof Date)  
        {  
            String str = new SimpleDateFormat(format).format((Date)value);  
            return str;  
        }  
        return value;  
    }

}
