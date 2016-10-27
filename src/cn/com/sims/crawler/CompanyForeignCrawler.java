package cn.com.sims.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
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

public class CompanyForeignCrawler extends DeepCrawler {
	private  static CompanyForeignCrawler crawler;
	 public SoupLang soupLang;
	 private static int pageNum;

	public CompanyForeignCrawler(String crawlPath)
			throws ParserConfigurationException, SAXException, IOException {
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
			System.out
					.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
			return;
		}

		/*
		 * soupLang可以从文件、InputStream中读取SoupLang写的抽取脚本 如果从外部文件读取，soupLang=new
		 * SoupLang("文件路径")
		 */
		File f = new File(InvestfirmCrawler.class.getClassLoader().getResource("/soulangxml/companyForeign.xml").getPath().toString().replaceAll("%20", " "));   
        InputStream in = new FileInputStream(f);
        soupLang = new SoupLang(in);
//        soupLang = new SoupLang(
//		ClassLoader.getSystemResourceAsStream("soulangxml/companyForeign.xml"));

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
			nextLinks.addAllFromDocument(page.getDoc(), "p[class=title]>a[target=_blank]");//h4.media-heading
			return nextLinks;

		} else if (Pattern.matches(".*key=.*&token=.*", url)) {
			String urlss = "";
			if (url != null) {
				String[] urls = url.split("[?]");
				urlss = "%" + urls[1] + "%";
			}
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("temp2");
			try {
				String jsonStr = page.getHtml();
				JSONObject json = new JSONObject(jsonStr);
				/*
				 * JSONObject jsons=new JSONObject(json.get("Result"));
				 * System.out
				 * .println("jsons="+jsons+"EconKind="+jsons.getDouble(
				 * "EconKind"));
				 */
				String jsonh = json.get("Result").toString();
				JSONObject jsons = new JSONObject(jsonh);
				System.out.println("jsons=" + jsons + "no=" + jsons.get("No"));
				
				String Tmp_Company_regisnum = jsons.get("No").toString();
				System.out.println("Tmp_Company_regisnum1=" + Tmp_Company_regisnum);
				System.out.println("urlss=" + urlss);
				if (Tmp_Company_regisnum != null && !"".equals(Tmp_Company_regisnum)) {
					String Tmp_Company_operastate = jsons.get("Status")
							.toString();
					String Tmp_Company_legal = jsons.get("OperName").toString();
					String Tmp_Company_gtype = jsons.get("EconKind").toString();
					String Tmp_Company_datees = jsons.get("StartDate")
							.toString();
					
					String TermStart = jsons.get("TermStart")
							.toString();
					String Tmp_Company_reca = jsons.get("RegistCapi")
							.toString();
					String TeamEnd = jsons.get("TeamEnd")
							.toString();
					String Tmp_Company_operaper;
					if(TeamEnd==null || "".equals(TeamEnd)||"null".equals(TeamEnd)){
						Tmp_Company_operaper = TermStart +"至 无固定期限";
					}else{
						Tmp_Company_operaper = TermStart +"至"+TeamEnd;
					}
					String Tmp_Company_resi = jsons.get("Address").toString();
					String sql = "update Tmp_Company_Info set Tmp_Company_regisnum=?,Tmp_Company_operastate=?,Tmp_Company_legal=?,Tmp_Company_gtype=?,Tmp_Company_datees=?,Tmp_Company_reca=?,Tmp_Company_operaper=?,Tmp_Company_resi=? where Tmp_Company_regisnum like ?";
					String[] sqlParams = { Tmp_Company_regisnum,
							Tmp_Company_operastate, Tmp_Company_legal,
							Tmp_Company_gtype, Tmp_Company_datees,
							Tmp_Company_reca, Tmp_Company_operaper,
							Tmp_Company_resi, urlss };
					jdbcTemplate.update(sql, sqlParams);
				} else {
					String sql = "update Tmp_Company_Info set Tmp_Company_regisnum=null where Tmp_Company_regisnum like ?";
					String[] sqlParams = { urlss };
					jdbcTemplate.update(sql, sqlParams);
				}
			} catch (Exception e) {
				String sql = "update Tmp_Company_Info set Tmp_Company_regisnum=null where Tmp_Company_regisnum like ?";
				String[] sqlParams = { urlss };
				jdbcTemplate.update(sql, sqlParams);
			}

		} else {

			/*
			 * 本程序中之可能遇到2种页面，搜狗搜索页面和搜索结果对应的页面,
			 * 所以这个else{}中对应的是搜索结果对应的页面，我们要保存这些页面到本地
			 */
			Context c = soupLang.extract(page.getDoc());

			System.out.println(CommonUtil.getNowdateTime()+": "+ c);
			url = c.getString("url");
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("temp2");
			
			List l=null;
			try {
				l = c.getList("results");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Tmp_Company_Code=c.getString("url");
			if(l!=null && l.size()>0){
				for(int i = 0 ;i<l.size();i++){
					System.out.println(CommonUtil.getNowdateTime()+": "+l.get(i));
					String[] arrays=l.get(i).toString().split(",");
					String[] array=arrays[0].split("=");
					String[] arrayl=arrays[1].split("=");
					arrayl[1]=arrayl[1].replaceAll("}", "");
					String sqls="REPLACE INTO Tmp_Compterm_info (Tmp_Company_Code,Tmp_Entrepreneur_Code,Tmp_Compterm_Active,Tmp_Compterm_Position,deleteFlag,Tmp_Compterm_Note,createtime,updtime) VALUES (?,?,'0',?,'0',null,now(),date_add(utc_timestamp(), interval 8 hour) )";
					String[] paramss={Tmp_Company_Code,arrayl[1],array[1]};
					jdbcTemplate.update(sqls, paramss);
				}
			}
			// try {
			// FileUtils.writeFileWithParent("/home/hu/data/sogou/"+id.incrementAndGet()+".html",
			// content);
			// System.out.println("save page "+page.getUrl());
			// } catch (IOException ex) {
			// ex.printStackTrace();
			// }
			
			/** dwj 改 2016-4-18**/
//			Links nextLinks = new Links();
//			String iframe_url = page.getDoc()
//					.select("div#qichachablock>div>iframe").attr("abs:src");
//			iframe_url = iframe_url.replaceAll("html/#/", "open");
//			nextLinks.add(iframe_url);
//			return nextLinks;
		}
		return null;
	}

	
	public static CompanyForeignCrawler  getInstanceCompanyForeignCrawler() throws Exception {
		// 获取总页数
				Document doc = Jsoup
						.connect("https://www.itjuzi.com/company/foreign")
						.userAgent(
								"Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0").cookies(TimingUdateData.mapsStr)
						.get();

				Element as = doc.select("a[data-ci-pagination-page]").last();
				String href = as.attr("href");
				int pageNoIndex = 0;
				if (href != null && (pageNoIndex = href.lastIndexOf("=")) > 0) {
					String pageNo = href.substring(pageNoIndex + 1);
					pageNum = Integer.parseInt(pageNo);
				}

				crawler = new CompanyForeignCrawler("/tmp/crawler/CompanyForeignCrawler");
			   return crawler;
	}
	
	 static {
         File dir = new File("/tmp/crawler/CompanyForeignCrawler");
         if (dir.exists()) {
             
         } else {
             dir.mkdirs();
         }
     }
	 
	 public void  excuteCrawler() throws Exception{
		 JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("temp2");
		 String sql="SELECT tmp_log_page FROM Tmp_Crawler_log WHERE deleteflag='0' AND Tmp_log_type='Tmp_companyForeign'";
		 int pn=jdbcTemplate.queryForInt(sql);
		 int num=pageNum-pn;
		 if(num<1){
			 num=1;
		 }else {
			num++;
		 }
		 System.out.println("=========== CompanyForeignCrawler excuteCrawler start  ===");
		 for (int i = 1; i <= num; i++) {
				this.addSeed("http://itjuzi.com/company/foreign?page=" + i);;
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
		 System.out.println("=========== CompanyForeignCrawler excuteCrawler end  ===");
		 sql="UPDATE Tmp_Crawler_log SET tmp_log_page='"+pageNum+"' WHERE Tmp_log_type='Tmp_companyForeign' AND deleteflag='0'";
		 jdbcTemplate.update(sql);
	 }
	 
	 public void  excuteCrawlerAll() throws Exception{
		 
		 System.out.println("=========== CompanyForeignCrawler excuteCrawler start  ===");
		 for (int i = 1; i <= pageNum; i++) {
				this.addSeed("http://itjuzi.com/company/foreign?page=" + i);;
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
		 System.out.println("=========== CompanyForeignCrawler excuteCrawler end  ===");
	 }
	 
	public static void main(String[] args) throws Exception {
		if(TimingUdateData.loginITJZ()){
			CompanyForeignCrawler cfc=CompanyForeignCrawler.getInstanceCompanyForeignCrawler();
			cfc.excuteCrawler();
		}
		
//		// 获取总页数
//		Document doc = Jsoup
//				.connect("http://itjuzi.com/company/foreign")
//				.userAgent(
//						"Mozilla/5.0 (X11; Linux i686; rv:34.0) Gecko/20100101 Firefox/34.0").cookies(TimingUdateData.mapsStr)
//				.get();
//
//		Element as = doc.select("a[class=follow_link]").last();
//		String href = as.attr("href");
//		int pageNoIndex = 0;
//		int pageNum = 0;
//		if (href != null && (pageNoIndex = href.lastIndexOf("=")) > 0) {
//
//			String pageNo = href.substring(pageNoIndex + 1);
//			pageNum = Integer.parseInt(pageNo);
//			System.out.println(pageNo);
//		}
//
//		CompanyForeignCrawler crawler = new CompanyForeignCrawler("/tmp/crawler");
//
//		for (int i = 1; i <= pageNum; i++) {
//			crawler.addSeed("http://itjuzi.com/company/foreign?page=" + i);
//		}
//
//		/*
//		 * 遍历中第一层爬取搜狗的搜索结果页面， 第二层爬取搜索结果对应的页面, 所以这里要将层数设置为2
//		 */
//
//		crawler.setRetry(5);
//		crawler.setThreads(1);
//		crawler.setTopN(41);
//		crawler.start(3);
	}
}
