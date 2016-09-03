package cn.com.sims.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.jdbc.core.JdbcTemplate;
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
import cn.edu.hfut.dmic.webcollector.util.FileUtils;

public class InvestfirmCrawler  extends DeepCrawler {

	 private  static InvestfirmCrawler crawler;
	 public SoupLang soupLang;
	 private static int pageNum;
	 
   public InvestfirmCrawler(String crawlPath)  throws ParserConfigurationException, SAXException, IOException {
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
    	File f = new File(InvestfirmCrawler.class.getClassLoader().getResource("/soulangxml/investfirm.xml").getPath().toString().replaceAll("%20", " "));   
        InputStream in = new FileInputStream(f);
        soupLang = new SoupLang(in);
//       soupLang = new SoupLang(
//				ClassLoader.getSystemResourceAsStream("soulangxml/investfirm.xml"));
   }
   
   /*用一个自增id来生成唯一文件名*/
   AtomicInteger id=new AtomicInteger(0);

   @Override
   public Links visitAndGetNextLinks(Page page) {
       String url = page.getUrl();
       /*如果是搜狗的页面*/
       if (Pattern.matches(".*page=[0-9]{1,10}",url)) {
       	
           Links nextLinks=new Links();
           /*将所有搜索结果条目的超链接返回，爬虫会在下一层爬取中爬取这些链接*/
	   //ul.media-body.detail-info>li:first-child
           nextLinks.addAllFromDocument(page.getDoc(), "i[class=cell maincell]>p[class=title]>a");
//           nextLinks.add("http://itjuzi.com/investfirm/7");
          
           return nextLinks;
       }else {
           /*本程序中之可能遇到2种页面，搜狗搜索页面和搜索结果对应的页面,
             所以这个else{}中对应的是搜索结果对应的页面，我们要保存这些页面到本地
           */
//    	   System.out.println("  html:"+page.getDoc().html());
       	Context c = soupLang.extract(page.getDoc());
       	System.out.println(CommonUtil.getNowdateTime()+": "+ c);
		List l=null;
	 	List s=null;
		JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("temp2");
		try {
			s = c.getList("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String Tmp_Investment_Codes=c.getString("url");
		String Tmp_Investor_Code=null;
		String Tmp_Investor_Position=c.getString("Tmp_Investor_Position");
		if(s!=null && s.size()>0){
			for(int i = 0 ;i<s.size();i++){
				String[] array=s.get(i).toString().split(",");
				for(int k=0;k<array.length;k++){
					if(array[k].contains("Tmp_Investor_Code")){
						Tmp_Investor_Code =array[k].split("=")[1].replaceAll("}", "");
					}else if(array[k].contains("Tmp_Investor_Position")){
						Tmp_Investor_Position=array[k].split("=")[1].replaceAll("}", "");
					}
				}
				
				String sql="REPLACE INTO Tmp_Investment_Investor (Tmp_Investment_Code, Tmp_Investor_Code, Tmp_Inment_Istor_Active, Tmp_Investor_Position, Tmp_Inment_Istor_Note,	  deleteFlag, createtime, updtime) VALUES (?,?,'0',?,null,'0',now(),date_add(utc_timestamp(), interval 8 hour) )";
				String[] params={Tmp_Investment_Codes,Tmp_Investor_Code,Tmp_Investor_Position};
				jdbcTemplate.update(sql, params);
				
				
			}
		}
//		try {
//			l = c.getList("results");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String Tmp_Investment_Code=c.getString("url");
//		String Tmp_Company_Code=c.getString("Tmp_Company_Code");
//		String Tmp_Inves_Record_Round=c.getString("Tmp_Inves_Record_Round");
//		String Tmp_Investor_Codes=null;
//		if(l!=null && l.size()>0){
//			
//			for(int i = 0 ;i<l.size();i++){
//				System.out.println(CommonUtil.getNowdateTime()+": "+l.get(i));
//				String[] arrays=l.get(i).toString().split(",");
//				for(int j=0;j<arrays.length;j++){
//					if(arrays[j].contains("Tmp_Company_Code")){
//						Tmp_Company_Code =arrays[j].split("=")[1].replaceAll("}", "");
//					}else if(arrays[j].contains("Tmp_Investor_Codes")){
//						Tmp_Investor_Codes=arrays[j].split("=")[1].replaceAll("}", "");
//					}else if(arrays[j].contains("Tmp_Inves_Record_Round")){
//						Tmp_Inves_Record_Round=arrays[j].split("=")[1].replaceAll("}", "");
//					}
//				}
				
				
//				String sql="update Tmp_Investment_Recordrela set Tmp_Investor_Code = ? where Tmp_Investment_Code=? and Tmp_Company_Code=? and Tmp_Inves_Record_Round=?";
//				String[] params={Tmp_Investor_Codes,url,Tmp_Company_Code,Tmp_Inves_Record_Round};
				//jdbcTemplate.update(sql, params);
//			}
//		}
		
       	
//           try {
//               FileUtils.writeFileWithParent("/home/hu/data/sogou/"+id.incrementAndGet()+".html", content);
//               System.out.println("save page "+page.getUrl());
//           } catch (IOException ex) {
//               ex.printStackTrace();
//           }
       }
       return null;
   }

   public static InvestfirmCrawler  getInstanceInvestfirmCrawler() throws Exception {
	 //获取总页数
	   	Document doc=Jsoup.connect("https://www.itjuzi.com/investfirm")
	                 .userAgent("Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0")
	                 .cookies(TimingUdateData.mapsStr)
	                 .get();
	         
		Element as = doc.select("a[data-ci-pagination-page]").last();
	    String href = as.attr("href");
	   	int pageNoIndex = 0;
	   	if(href != null && (pageNoIndex =  href.lastIndexOf("="))>0){
	   		String pageNo  = href.substring(pageNoIndex+1);
	   		pageNum = Integer.parseInt(pageNo);
	   		System.out.println(pageNo);
	   	}
	         
	   	crawler = new InvestfirmCrawler("/tmp/crawler/InvestfirmCrawler");
	      
	       return crawler;
   }
   
   static {
	    File dir = new File("/tmp/crawler/InvestfirmCrawler");
	    if (dir.exists()) {
	        
	    } else {
	        dir.mkdirs();
	    }
	}
   
   public void  excuteCrawler() throws Exception{
	   System.err.println("=====================InvestfirmCrawler start------------");
	   for (int i = 1; i <= pageNum; i++) {
			   this.addSeed("https://www.itjuzi.com/investfirm?page="+i);
	   }
	       /*遍历中第一层爬取搜狗的搜索结果页面，
	         第二层爬取搜索结果对应的页面,
	         所以这里要将层数设置为2
	       */
	       
	   this.setRetry(cn.com.sims.common.commonUtil.CommonUtil.retryNo);
	   this.setThreads(1);
	   this.setTopN(100000);
	   HttpRequester httpRequester = new HttpRequesterImpl();
	   httpRequester.setCookie(TimingUdateData.responseCookies.toString()) ;
	   this.setHttpRequester(httpRequester);
	   this.start(2);
	   System.err.println("=====================InvestfirmCrawler end------------");
   }
   
   public static void main(String[] args) throws Exception {
	   if(TimingUdateData.loginITJZ()){
		   InvestfirmCrawler ji = InvestfirmCrawler.getInstanceInvestfirmCrawler();
		   ji.excuteCrawler();
	   }
   	//获取总页数
//   	  Document doc=Jsoup.connect("http://itjuzi.com/investfirm")
//                 .userAgent("Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0").cookies(TimingUdateData.mapsStr)
//                 .get();
//         
//         Element as = doc.select("a[class=follow_link]").last();
//      	String href = as.attr("href");
//   	int pageNoIndex = 0;
//   	int pageNum = 0;
//   	if(href != null && (pageNoIndex =  href.lastIndexOf("="))>0){
//   		
//   		String pageNo  = href.substring(pageNoIndex+1);
//   		pageNum = Integer.parseInt(pageNo);
//   		System.out.println(pageNo);
//   	}
//         
//   	InvestfirmCrawler crawler = new InvestfirmCrawler("/tmp/crawler");
//       
//       for(int i = 1 ;i<= pageNum;i++){
//       	crawler.addSeed("http://itjuzi.com/investfirm?page="+i);
//       }
//       
//       /*遍历中第一层爬取搜狗的搜索结果页面，
//         第二层爬取搜索结果对应的页面,
//         所以这里要将层数设置为2
//       */
//       
//       crawler.setRetry(5);
//       crawler.setThreads(1);
//       crawler.setTopN(20000);
//       crawler.start(2);
   }

}
