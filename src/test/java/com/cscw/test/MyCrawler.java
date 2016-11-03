package com.cscw.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.count.dao.site.SiteInfoDao;
import com.count.entity.po.site.SiteInfo;
import com.cscw.utils.HtmlCodeUtil;
import com.huisa.common.exception.ServiceException;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class MyCrawler extends BreadthCrawler {
	private static AtomicInteger count = new AtomicInteger();
	
	private SiteInfoDao siteInfoDao=new SiteInfoDao();
	/**
	 * @param crawlPath
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param autoParse
	 *            if autoParse is true,BreadthCrawler will auto extract links
	 *            which match regex rules from pag
	 */
	public MyCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addSeed("http://www.gdstc.gov.cn/zwgk/zwyw/index@1.htm");
		this.addRegex("http://www.gdstc.gov.cn/HTML/zwgk/zwyw/.+html");
		this.addRegex("http://www.gdstc.gov.cn/zwgk/zwyw/index@.+htm");
	}

	@Override
	public void visit(Page page, Links nextLinks) {

		String url = page.getUrl();
		if (Pattern.matches("http://www.gdstc.gov.cn/HTML/zwgk/zwyw/.+html",
				url)) {
			Document doc = page.getDoc();

			String title = doc.select("div[class=title] strong")
					.first().text();
			String tempSource = doc.select("div[class=time] span[class=p12]")
					.first().text();
			String content = doc.select("div[class=content]").first().html();
			
			content=HtmlCodeUtil.replaceImgSrc(content);
			String source=tempSource.split("    ")[0].split("来源： ")[1];
			String releaseTime=tempSource.split("    ")[1].split("发布日期：")[1];
			//String content=HtmlCode.replaceHtml(content);
			// String content =
			// doc.select("div[class=body yom-art-content clearfix]").first().text();

			// System.out.println("URL:" + url);
			System.out.println("title:" + title + ";source:" + source+";release_time:"+releaseTime);
			//System.out.println("title:" + title + ";source:" + source);
			System.out.println("content:" + content);
			count.addAndGet(1);
			System.out.println(count.get());
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDate = null;
            try {
                releaseDate = sdf.parse(releaseTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
			
			SiteInfo siteInfo=new SiteInfo();
			siteInfo.setSite("www.gdstc.gov.cn");
			siteInfo.setChannel("zheng_wu_gong_kai");
			siteInfo.setTitle(title);
			siteInfo.setSource(source);
			siteInfo.setContent(content);
			siteInfo.setUrl(url);
			siteInfo.setReleaseTime(releaseDate);
			siteInfo.setCreateTime(new Date());
			
			try {
                siteInfoDao.addSiteInfo(siteInfo);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
			// System.out.println("content:\n" + content);

			/* If you want to add urls to crawl,add them to nextLinks */
			/*
			 * WebCollector automatically filters links that have been fetched
			 * before
			 */
			/*
			 * If autoParse is true and the link you add to nextLinks does not
			 * match the regex rules,the link will also been filtered.
			 */
			// nextLinks.add("http://xxxxxx.com");
		}
	}

	public static void main(String[] args) throws Exception {
		MyCrawler crawler = new MyCrawler("crawl", true);
		crawler.setThreads(5);
		crawler.setTopN(100);
		crawler.setResumable(true);
		crawler.start(2);
		System.out.println(count.get());
	}
}
