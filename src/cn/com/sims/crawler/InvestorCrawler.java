/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cn.com.sims.crawler;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.crawler.souplang.Context;
import cn.com.sims.crawler.souplang.SoupLang;
import cn.com.sims.crawler.util.JDBCHelper;
import cn.com.sims.util.task.TimingUdateData;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequester;
import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.SAXException;

/**
 * Demo演示爬取搜索的搜索结果 分页爬取，并下载搜索结果中的每个网页到本地
 *
 * @author skl
 */
public class InvestorCrawler extends DeepCrawler {

	private static InvestorCrawler crawler;
	public SoupLang soupLang;
	private static int pageNum;

	public InvestorCrawler(String crawlPath) throws ParserConfigurationException,
			SAXException, IOException {
		super(crawlPath);

		try {
			/* 用JDBCHelper在JDBCTemplate池中建立一个名为temp2的JDBCTemplate */
			JDBCHelper.createMysqlTemplate(
							"temp2",
							ConstantUrl.ITSIMSDBURL,
							ConstantUrl.ITSIMSDBUSERNAME,
							ConstantUrl.ITSIMSDBUSERPAW, 
							ConstantUrl.ITSIMSDBINITIALSIZE,
							ConstantUrl.ITSIMSDMAXACTIVE);

			System.out.println("成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
			return;
		}

		/*
		 * soupLang可以从文件、InputStream中读取SoupLang写的抽取脚本 如果从外部文件读取，soupLang=new
		 * SoupLang("文件路径")
		 */
		File f = new File(InvestorCrawler.class.getClassLoader().getResource("/soulangxml/investor.xml").getPath().toString().replaceAll("%20", " ")); ///soulangxml/investor.xml  
        InputStream in = new FileInputStream(f);   
        soupLang = new SoupLang(in);
//        soupLang = new SoupLang(
//		ClassLoader.getSystemResourceAsStream("soulangxml/investor.xml"));
	}

	/* 用一个自增id来生成唯一文件名 */
	AtomicInteger id = new AtomicInteger(0);

	@Override
	public Links visitAndGetNextLinks(Page page) {
		String url = page.getUrl();
		/* 如果是搜狗的页面 */
		if (Pattern.matches(".*page=[0-9]{1,10}", url)) {

			Links nextLinks = new Links();
			/* 将所有搜索结果条目的超链接返回，爬虫会在下一层爬取中爬取这些链接 */
			nextLinks.addAllFromDocument(page.getDoc(), "b[class=title]>a[class=name][target=_blank]");
			return nextLinks;
		} else {
			/*
			 * 本程序中之可能遇到2种页面，搜狗搜索页面和搜索结果对应的页面,
			 * 所以这个else{}中对应的是搜索结果对应的页面，我们要保存这些页面到本地
			 */
//			page.getDoc();
			Context c = soupLang.extract(page.getDoc());
			System.out.println(CommonUtil.getNowdateTime()+": "+ c);
			// try {
			// FileUtils.writeFileWithParent("/home/hu/data/sogou/"+id.incrementAndGet()+".html",
			// content);
			// System.out.println("save page "+page.getUrl());
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// }
		}
		return null;
	}

	public static  InvestorCrawler getInstanceInvestorCrawler() throws Exception {
		// 获取总页数
		Document doc = Jsoup
				.connect("https://www.itjuzi.com/investor")
				.userAgent("Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0")
				.cookies(TimingUdateData.mapsStr)
				.get();

		Element as = doc.select("a[data-ci-pagination-page]").last();
		String href = as.attr("href");
		int pageNoIndex = 0;
		if (href != null && (pageNoIndex = href.lastIndexOf("=")) > 0) {

			String pageNo = href.substring(pageNoIndex + 1);
			pageNum = Integer.parseInt(pageNo);
		}

		crawler = new InvestorCrawler("/tmp/crawler/InvestorCrawler");

		return crawler;
	}
	 static {
		    File dir = new File("/tmp/crawler/InvestorCrawler");
		    if (dir.exists()) {
		        
		    } else {
		        dir.mkdirs();
		    }
		}
	public void excuteCrawler() throws Exception{
		System.out.println("================================InvestorCrawler start excuteCrawler");
		for (int i = 1; i <= pageNum; i++) {
			this.addSeed("https://www.itjuzi.com/investor?page=" + i);
		}
		/*
		 * 遍历中第一层爬取搜狗的搜索结果页面， 第二层爬取搜索结果对应的页面, 所以这里要将层数设置为2
		 */

		this.setRetry(cn.com.sims.common.commonUtil.CommonUtil.retryNo);
		this.setThreads(1);
		this.setTopN(100000);
		HttpRequester httpRequester = new HttpRequesterImpl();
		httpRequester.setCookie(TimingUdateData.responseCookies.toString()) ;
		this.setHttpRequester(httpRequester);
		this.start(2);
		System.out.println("==========================================InvestorCrawler end excuteCrawler");
	}
	
	public static void main(String[] args) throws Exception {
		if(TimingUdateData.loginITJZ()){
			InvestorCrawler ji = InvestorCrawler.getInstanceInvestorCrawler();
			ji.excuteCrawler();
		}
	}
}
