package com.cscw.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.count.utils.crawler.TzggCrawler;
import com.count.utils.crawler.ZcjdCrawler;
import com.count.utils.crawler.ZwdtCrawler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-core.xml")
public class SiteTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void Test1(){
        CommonCrawler crawler = new CommonCrawler("crawl",true,"http://www.gdstc.gov.cn/HTML/zwgk/zwyw/.+html","www.gdstc.gov.cn","zheng_wu_dong_tai");
        crawler.addSeed("http://www.gdstc.gov.cn/zwgk/zwyw/index@1.htm");
        crawler.addRegex("http://www.gdstc.gov.cn/HTML/zwgk/zwyw/.+html");
        crawler.addRegex("http://www.gdstc.gov.cn/zwgk/zwyw/index@.+htm");
        crawler.setThreads(1);
        crawler.setTopN(100);
        // crawler.setResumable(true);
        try {
            crawler.start(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void Test2(){
        ZwdtCrawler zwdtCrawler=new ZwdtCrawler("zwdt", true);
        zwdtCrawler.setThreads(1);
        zwdtCrawler.setTopN(100);
        zwdtCrawler.setResumable(true);
        try {
            zwdtCrawler.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Test3(){
        TzggCrawler tzggCrawler=new TzggCrawler("tzgg", true);
        tzggCrawler.setThreads(1);
        tzggCrawler.setTopN(100);
        tzggCrawler.setResumable(true);
        try {
            tzggCrawler.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void Test4(){
        ZcjdCrawler zcjdCrawler=new ZcjdCrawler("zcjd", true);
        zcjdCrawler.setThreads(1);
        zcjdCrawler.setTopN(100);
        zcjdCrawler.setResumable(true);
        try {
            zcjdCrawler.start(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
