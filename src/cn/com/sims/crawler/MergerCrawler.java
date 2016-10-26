package cn.com.sims.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


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

public class MergerCrawler  extends DeepCrawler{

	private  static MergerCrawler crawler;
	 public SoupLang soupLang;
	 private static int pageNum;
	 
public MergerCrawler(String crawlPath)  throws ParserConfigurationException, SAXException, IOException {
    super(crawlPath);
    
    try {
	/* 用JDBCHelper在JDBCTemplate池中建立一个名为temp2的JDBCTemplate */
	JDBCHelper
			.createMysqlTemplate(
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
    
    /*soupLang可以从文件、InputStream中读取SoupLang写的抽取脚本
    如果从外部文件读取，soupLang=new SoupLang("文件路径")*/
//    File f = new File(InvestfirmCrawler.class.getClassLoader().getResource("/soulangxml/merger.xml").getPath());   
//    InputStream in = new FileInputStream(f);
//    soupLang = new SoupLang(in);

	soupLang = new SoupLang(
				ClassLoader.getSystemResourceAsStream("soulangxml/merger.xml"));
}

/*用一个自增id来生成唯一文件名*/
AtomicInteger id=new AtomicInteger(0);

@Override
public Links visitAndGetNextLinks(Page page) {
    String url = page.getUrl();
    /*如果是搜狗的页面*/
    if (Pattern.matches(".*page=[0-9]{1,10}",url)) {
    	
        Links nextLinks=new Links();
        /*将所有搜索结果条目的超链接返回，爬虫会在下一层爬取中爬取这些链接     span.invse_id*/
        //Elements es = page.getDoc().select("i[class=cell maincell]>p[class=title]>a");
       // for(Element e :es){
       //	 				nextLinks.add(e.text());
        //				}
        nextLinks.addAllFromDocument(page.getDoc(), "i[class=cell maincell]>p[class=title]>a");
        return nextLinks;
    }else {
        /*本程序中之可能遇到2种页面，搜狗搜索页面和搜索结果对应的页面,
          所以这个else{}中对应的是搜索结果对应的页面，我们要保存这些页面到本地
        */
    	Context c = soupLang.extract(page.getDoc());
    	System.out.println(CommonUtil.getNowdateTime()+": "+ c);
    }
    return null;
}

public static  MergerCrawler getInstance() throws Exception {
	//获取总页数   ?location=in_com
	  Document doc=Jsoup.connect("https://www.itjuzi.com/merger")
            .userAgent("Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0").cookies(TimingUdateData.mapsStr)
            .get();
     	//a[class=follow_link]
    	Element as = doc.select("a[data-ci-pagination-page]").last();
 	String href = as.attr("href");
	int pageNoIndex = 0;
	if(href != null && (pageNoIndex =  href.lastIndexOf("="))>0){
		
		String pageNo  = href.substring(pageNoIndex+1);
		pageNum = Integer.parseInt(pageNo);
		System.out.println(pageNo);
	}
    
	 crawler = new MergerCrawler("/tmp/crawler/MergerCrawler");

	return crawler;
}
static {
    File dir = new File("/tmp/crawler/MergerCrawler");
    if (dir.exists()) {
        
    } else {
        dir.mkdirs();
    }
}
public void  excuteCrawler() throws Exception{
	System.out.println("======================InvestorCrawler start excuteCrawler");
	for (int i = 1; i <= pageNum; i++) {
		this.addSeed("https://www.itjuzi.com/merger?page="+i);
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
	System.out.println("===================InvestorCrawler end excuteCrawler");
}

public static void main(String[] args) throws Exception {
//	Class<?> ct=CompanyCrawler.class;
//	System.out.println(ct);
//	Boolean cc = true;
//	Class<?> ct2= cc.getClass();
//	System.out.println(ct2);
//	Class<?> ct3=Class.forName("java.lang.Integer");
//	System.out.println(ct3);
//	Class<?> ct4=Long.TYPE;
//	System.out.println(ct4);
	
	
//	Field[] fields=CompanyCrawler.class.getFields();
//	for (Field field : fields) {
//		System.out.println(field);
//	}
//	
//	fields=CompanyCrawler.class.getDeclaredFields();
//	for (Field field : fields) {
//		System.err.println(field);
//	}
	
	Method[] methods=CompanyCrawler.class.getMethods();
	for (Method method : methods) {
		System.out.println(method);
	}
	
	methods=CompanyCrawler.class.getDeclaredMethods();
	for (Method method : methods) {
		System.err.println(method);
	}
	
	
	
	
	
	
	
	
	
	
//	if(TimingUdateData.loginITJZ()){
//		MergerCrawler mcCrawler=MergerCrawler.getInstance();
//		mcCrawler.excuteCrawler();
//	}
	
	//获取总页数
//	  Document doc=Jsoup.connect("http://itjuzi.com/merger?location=in_com")
//              .userAgent("Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0").cookies(TimingUdateData.mapsStr)
//              .get();
//      
//      Element as = doc.select("a[class=follow_link]").last();
//   	String href = as.attr("href");
//	int pageNoIndex = 0;
//	int pageNum = 0;
//	if(href != null && (pageNoIndex =  href.lastIndexOf("="))>0){
//		
//		String pageNo  = href.substring(pageNoIndex+1);
//		pageNum = Integer.parseInt(pageNo);
//		System.out.println(pageNo);
//	}
//      
//	MergerCrawler crawler = new MergerCrawler("/tmp/crawler");
//    for(int i = 1 ;i<= 1;i++){
//   		 crawler.addSeed("http://itjuzi.com/merger?location=in_com&page="+i);
//    }
    
    /*遍历中第一层爬取搜狗的搜索结果页面，
      第二层爬取搜索结果对应的页面,
      所以这里要将层数设置为2
    */
    
//    crawler.setRetry(5);
//    crawler.setThreads(1);
//    crawler.setTopN(20000);
//    crawler.start(2);
}



}
