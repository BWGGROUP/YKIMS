package cn.com.sims.util.task;

/** 
 * @File: TimingUdateData.java 
 * @Package cn.com.sims.common.action
 * @Description: TODO 
 * @Copyright: Copyright(c)2012 
 * @Company: shbs  
 * @author rqq
 * @date 2014/08/07 
 * @version V1.0 
 **/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.log4j.Logger;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.crawler.CompanyCrawler;
import cn.com.sims.crawler.CompanyForeignCrawler;
import cn.com.sims.crawler.InvesteventsCrawler;
import cn.com.sims.crawler.InvestfirmCrawler;
import cn.com.sims.crawler.InvestorCrawler;
import cn.com.sims.crawler.PersonCrawler;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.net.RequestConfig;

/**
 * @ClassName: TimingUdateData.java
 * @Description: 定时任务更新数据库
 * @author skl
 * @date 2015/09/07
 **/

public class TimingUdateData {
	private static final Logger logger = Logger
			.getLogger(TimingUdateData.class);
	public static  Map<String, String> mapsStr = new HashMap<String, String>(3) ;
	public static StringBuffer responseCookies = new StringBuffer();;
	/**
	 * @Title: work
	 * @Description:产定时任务更新数据库
	 * @return
	 */
	public void work() throws Exception {
		logger.info("TimingUdateData work 方法开始");
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":investorCrawler start");
			//如果登录成功，在抓取否则不再抓取
			 if(loginITJZ()){
				 //抓取投资人
				 InvestorCrawler investorCrawler = InvestorCrawler
						 .getInstanceInvestorCrawler();
				 investorCrawler.excuteCrawler();
			 }else{
				 System.out.println(CommonUtil.getNowdateTime() + ": InvestorCrawler登录失败");
			 }
			System.out.println(CommonUtil.getNowdateTime() + ": end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler start");
			if(loginITJZ()){
				//抓取国内公司
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawler();
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyForeignCrawler start");
			if(loginITJZ()){
				//抓取外国公司
				CompanyForeignCrawler companyForeignCrawler = CompanyForeignCrawler
						.getInstanceCompanyForeignCrawler();
				companyForeignCrawler.excuteCrawler();
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyForeignCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler start");
			if(loginITJZ()){
				//国内融资
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawler();
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			System.out.println(CommonUtil.getNowdateTime()
//					+ ":InvesteventsForeginCrawler start");
//			if(loginITJZ()){
//				InvesteventsForeginCrawler investeventsForeginCrawler = InvesteventsForeginCrawler
//						.getInstance();
//				investeventsForeginCrawler.excuteCrawler();
//			}else{
//				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsForeginCrawler 登录失败");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println(CommonUtil.getNowdateTime()
//					+ ":MergerCrawler start");
//			if(loginITJZ()){
//				国内并购
//				MergerCrawler mergerCrawler = MergerCrawler.getInstance();
//				mergerCrawler.excuteCrawler();
//			}else{
//				System.out.println(CommonUtil.getNowdateTime() + ":MergerCrawler 登录失败");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println(CommonUtil.getNowdateTime()
//					+ ":MergerForeginCrawler start");
//			国外并购
//			if(loginITJZ()){
//				MergerForeginCrawler mergerForeginCrawler = MergerForeginCrawler
//						.getInstance();
//				mergerForeginCrawler.excuteCrawler();
//			}else{
//				System.out.println(CommonUtil.getNowdateTime() + ":MergerForeginCrawler 登录失败");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvestfirmCrawler start");
			//抓取投资机构
			 if(loginITJZ()){
				InvestfirmCrawler investfirmCrawler = InvestfirmCrawler
						.getInstanceInvestfirmCrawler();
				investfirmCrawler.excuteCrawler();
			 }else{
				 System.out.println(CommonUtil.getNowdateTime() + ":investfirmCrawler 登录失败");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler start");
			//抓取创业者
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawler();
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("TimingUdateData 的 work 方法 结束");
	}
	
	/**
	 * 定时任务：抓取全部数据
	 */
	public void workAll() throws Exception {
		logger.info("TimingUdateData work 方法开始");
		
	
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler1 start");
			if(loginITJZ()){
				//抓取国内公司1
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawlerAll(1);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler2 start");
			if(loginITJZ()){
				//抓取国内公司2
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawlerAll(2);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler3 start");
			if(loginITJZ()){
				//抓取国内公司3
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawlerAll(3);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler4 start");
			if(loginITJZ()){
				//抓取国内公司4
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawlerAll(4);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyCrawler5 start");
			if(loginITJZ()){
				//抓取国内公司5
				CompanyCrawler companyCrawler = CompanyCrawler.getInstanceCompanyCrawler();
				companyCrawler.excuteCrawlerAll(5);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":CompanyForeignCrawler start");
			if(loginITJZ()){
				//抓取外国公司
				CompanyForeignCrawler companyForeignCrawler = CompanyForeignCrawler
						.getInstanceCompanyForeignCrawler();
				companyForeignCrawler.excuteCrawlerAll();
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":CompanyForeignCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":investorCrawler start");
			//如果登录成功，在抓取否则不再抓取
			 if(loginITJZ()){
				 //抓取投资人
				 InvestorCrawler investorCrawler = InvestorCrawler
						 .getInstanceInvestorCrawler();
				 investorCrawler.excuteCrawler();
			 }else{
				 System.out.println(CommonUtil.getNowdateTime() + ": InvestorCrawler登录失败");
			 }
			System.out.println(CommonUtil.getNowdateTime() + ": end");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler start");
			if(loginITJZ()){
				//国内融资1
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawlerAll(1);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler2 start");
			if(loginITJZ()){
				//国内融资2
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawlerAll(2);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler3 start");
			if(loginITJZ()){
				//国内融资3
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawlerAll(3);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler4 start");
			if(loginITJZ()){
				//国内融资4
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawlerAll(4);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvesteventsCrawler5 start");
			if(loginITJZ()){
				//国内融资5
				InvesteventsCrawler investeventsCrawler = InvesteventsCrawler
						.getInstance();
				investeventsCrawler.excuteCrawlerAll(5);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":InvesteventsCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":InvestfirmCrawler start");
			//抓取投资机构
			 if(loginITJZ()){
				InvestfirmCrawler investfirmCrawler = InvestfirmCrawler
						.getInstanceInvestfirmCrawler();
				investfirmCrawler.excuteCrawler();
			 }else{
				 System.out.println(CommonUtil.getNowdateTime() + ":investfirmCrawler 登录失败");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler1 start");
			//抓取创业者1
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawlerAll(1);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler2 start");
			//抓取创业者2
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawlerAll(2);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler3 start");
			//抓取创业者3
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawlerAll(3);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler4 start");
			//抓取创业者4
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawlerAll(4);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(CommonUtil.getNowdateTime()
					+ ":PersonCrawler5 start");
			//抓取创业者5
			if(loginITJZ()){
				PersonCrawler personCrawler = PersonCrawler.getInstance();
				personCrawler.excuteCrawlerAll(5);
			}else{
				System.out.println(CommonUtil.getNowdateTime() + ":PersonCrawler 登录失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("TimingUdateData 的 work 方法 结束");
	}
	
	
	
	public static boolean loginITJZ(){
//		JSONObject json = null;
		PrintWriter out = null;
		BufferedReader in = null;
		javax.net.ssl.HttpsURLConnection connection =null;
		boolean bool=false;
		try {
			
	        TrustManager[] trustAllCerts = new TrustManager[]{
	                new X509TrustManager() {
	                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                        return null;
	                    }

	                    public void checkClientTrusted(
	                            java.security.cert.X509Certificate[] certs, String authType) {
	                    }

	                    public void checkServerTrusted(
	                            java.security.cert.X509Certificate[] certs, String authType) {
	                    }
	                }
	        };        
	         try {
	                SSLContext sc = SSLContext.getInstance("SSL");
	                sc.init(null, trustAllCerts, new java.security.SecureRandom());
	                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	            } catch (Exception e) {
	                System.out.println("Error" + e);
	            }    
			
			    URL realUrl = new URL("https://www.itjuzi.com/user/login");
			    //http://itjuzi.com/user/ajax/login
			    
			    // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
			     connection = (javax.net.ssl.HttpsURLConnection)realUrl.openConnection(); 
			    //connection = (HttpURLConnection) realUrl.openConnection();// 此时cnnection只是为一个连接对象,待连接中
			   
			    
			    // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			    connection.setDoOutput(true);
//			    // 设置连接输入流为true
			    connection.setDoInput(true);
//			    
//			    connection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.125 Safari/537.36");
//			    connection.addRequestProperty("Cookie",front_identity)
			    
			    
			 // 设置通用的请求属性
		        connection.setRequestProperty("accept", "*/*");
		        connection.setRequestProperty("connection", "Keep-Alive");
		        connection.setRequestProperty("user-agent",
		                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			    
		        
			    // post请求缓存设为false
			    connection.setUseCaches(true);
			    connection.setRequestMethod("POST");
	            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	            connection.setRequestProperty("Content-Language", "zh-cn");  
	            connection.setRequestProperty("Connection", "keep-alive");  
	            connection.setRequestProperty("Cache-Control", "no-cache");
	            
			    // 设置请求方式为post
			    // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
			    connection.connect();
			    
			    // 获取URLConnection对象对应的输出流
			    out = new PrintWriter(connection.getOutputStream());
//			    // 发送请求参数
			    
//			    DataOutputStream out = new DataOutputStream(
//	                    connection.getOutputStream());
//	            JSONObject obj = new JSONObject();
//	            obj.element("identity", "qing.yang@china-ecapital.com");
//	            obj.element("uname", "qing.yang@china-ecapital.com");
//	            obj.element("create_account_email", "qing.yang@china-ecapital.com");
//	            obj.element("password", "Aa123456");
//	            obj.element("upwd", "Aa123456");
//	            obj.element("create_account_password", "Aa123456");
//	            obj.element("remember", "false");
//	            out.writeBytes(URLEncoder.encode(obj.toString(),"utf-8"));
			    
			    
	             out.print("identity=qing.yang@china-ecapital.com&password=Aa123456&remember=1");
//			    // flush输出流的缓冲
			    out.flush();
//			    //完成后刷新并关闭流
			    out.close(); // 重要且易忽略步骤 (关闭流,切记!) 
//			    System.out.println(CommonUtil.getNowdateTime()+""+URLEncoder.encode(s);
			    
			    // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
			    InputStream is = connection.getInputStream();
			    
			    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { 
			    String cookieskey = "Set-Cookie";  
			    Map<String, List<String>> maps = connection.getHeaderFields();  
			    List<String> coolist = maps.get(cookieskey);  
			    Iterator<String> it = coolist.iterator();  
			    StringBuffer sbu = new StringBuffer();  
			    while(it.hasNext()){  
			    	String responseCookie = it.next();
			    	sbu.append(responseCookie.substring(0, responseCookie.indexOf(";"))+"\r\n");  
			    	responseCookie = responseCookie.substring(0, responseCookie.indexOf(";"));
			    	responseCookies .append(responseCookie+";"); 
//			    	System.out.println("---cookie: "+responseCookie.split("=")[0]+"=="+responseCookie.split("=")[1]);
			    	mapsStr.put(responseCookie.split("=")[0], responseCookie.split("=")[1]);
			    }  
			    }
			    
//			    String responseCookie = connection.getHeaderField("Set-Cookie");// 取到所用的Cookie
//			    System.out.println("responseCookie"+responseCookie);
			    
			    
//			    byte[] buf = new byte[2048];
//	            int read;
//	            int sum = 0;
//	            int maxsize = 1024*1024;
//	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	            while ((read = is.read(buf)) != -1) {
//	                if (maxsize > 0) {
//	                    sum = sum + read;
//
//	                    if (maxsize > 0 && sum > maxsize) {
//	                        read = maxsize - (sum - read);
//	                        bos.write(buf, 0, read);
//	                        break;
//	                    }
//	                }
//	                bos.write(buf, 0, read);
//	            }

	            is.close();
//	            bos.toString();
//			   System.out.println( "==============bos: "+ bos.toString());
			   
//			   json = JSONObject.fromObject( bos.toString());
//			   bos.close();
//			    in = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
//			    String line = null;
//			    StringBuilder sb = new StringBuilder(); // 用来存储响应数据
//			    
//			    // 循环读取流,若不到结尾处
//			    while ((line = in.readLine()) != null) {
//			        sb.append(in.readLine());
//			    }
//			    System.out.println(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null){
				 connection.disconnect(); // 销毁连接
			}
			
			if(mapsStr.get("remember_code")!=null){
				bool=true;
			}
			
		}
		return bool;//(Boolean)json.get("success");
	}
	public boolean loginITJZ2(){
	 RequestConfig requestConfig = new RequestConfig();
     requestConfig.setMethod("POST");
     requestConfig.setDooutput(true);
     requestConfig.addHeader("uname", "sliang35@gmail.com");
     requestConfig.addHeader("upwd", "shengli");
     requestConfig.addHeader("remember", "false");
//     requestConfig.setUserAgent(userAgent);
     
//     requestConfig.setUserAgent("WebCollector");
//     requestConfig.setCookie("xxxxxxxxxxxxxx");
//     requestConfig.addHeader("xxx", "xxxxxxxxx");

     HttpRequest request;
	try {
		request = new HttpRequest("http://itjuzi.com/user/ajax/login?uname=sliang35@gmail.com&upwd=shengli&remember=false", requestConfig);
		HttpResponse response = request.getResponse();
		String html = response.getHtmlByCharsetDetect();
		
		System.out.println(html);
		System.out.println("response code=" + response.getCode());
		System.out.println("Server=" + response.getHeader("Server"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     return true;
	}
	
	
//	public static void storecoo(URI uri,String strcoo) {
//		
//		// 创建一个默认的 CookieManager
//		
//
//		// 将规则改掉，接受所有的 Cookie
//		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//
//		// 保存这个定制的 CookieManager
//		CookieHandler.setDefault(manager);
//
//		// 接受 HTTP 请求的时候，得到和保存新的 Cookie
//		HttpCookie cookie = new HttpCookie("Cookie: ", strcoo);
//		//cookie.setMaxAge(60000);//没这个也行。
//		manager.getCookieStore().add(uri, cookie);
//	}
//	
//	public static HttpCookie getcookies(){
//		
//		HttpCookie res = null;
//		// 使用 Cookie 的时候：
//		// 取出 CookieStore
//		CookieStore store = manager.getCookieStore();
//
//		// 得到所有的 URI
//		List<URI> uris = store.getURIs();
//		for (URI ur : uris) {
//			// 筛选需要的 URI
//			// 得到属于这个 URI 的所有 Cookie
//			List<HttpCookie> cookies = store.get(ur);
//			for (HttpCookie coo : cookies) {
//				res = coo;
//			}
//		}
//		return res;
//	}
}