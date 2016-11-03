package com.cscw.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import com.count.dao.site.SiteInfoDao;
import com.cscw.utils.HtmlCodeUtil;

public class CommonCrawler extends BreadthCrawler {
	private static AtomicInteger count = new AtomicInteger();
	
	private SiteInfoDao siteInfoDao=new SiteInfoDao();
	
	public static String matchUrl;
	
	public static String site;
	
	public static String channel;
	/**
	 * @param crawlPath
	 *            crawlPath is the path of the directory which maintains
	 *            information of this crawler
	 * @param autoParse
	 *            if autoParse is true,BreadthCrawler will auto extract links
	 *            which match regex rules from pag
	 */
	public CommonCrawler(String crawlPath, boolean autoParse,String matchUrl,String site,String channel) {
		super(crawlPath, autoParse);
		this.matchUrl=matchUrl;
		this.site=site;
		this.channel=channel;
/*		this.addSeed("http://www.gdstc.gov.cn/zwgk/zwyw/index@1.htm");
		this.addRegex("http://www.gdstc.gov.cn/HTML/zwgk/zwyw/.+html");
		this.addRegex("http://www.gdstc.gov.cn/zwgk/zwyw/index@.+htm");*/
	}

	@Override
	public void visit(Page page, Links nextLinks) {

		String url = page.getUrl();
		if (Pattern.matches(matchUrl,
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

			System.out.println("title:" + title + ";source:" + source+";release_time:"+releaseTime);
			System.out.println("content:" + content);
			count.addAndGet(1);
			
			/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDate = null;
            try {
                releaseDate = sdf.parse(releaseTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
			
			SiteInfo siteInfo=new SiteInfo();
			siteInfo.setSite(site);
			siteInfo.setChannel(channel);
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
            }*/
		}
	}

	


    public static void main(String[] args) throws Exception {
		CommonCrawler crawler = new CommonCrawler("crawl", true,"","","");
		crawler.setThreads(5);
		crawler.setTopN(100);
		crawler.setResumable(true);
		crawler.start(2);
		System.out.println(count.get());
	}
}
