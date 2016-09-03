package cn.com.sims.crawler;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.com.sims.common.commonUtil.HttpConnectionUtil;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.net.RandomProxyGenerator;

public interface ProxyCustom {
	 public RandomProxyGenerator proxyGenerator = new RandomProxyGenerator() {
		 Random random = new Random();
		 protected Proxys proxysGood = new Proxys();
		 @Override
		 public Proxy next(String url) {
		        synchronized (this.lock) {
		            if (this.proxys == null) {
		                return null;
		            }
		            if (this.proxys.isEmpty()) {
		            	 for (int i = 1; i <= 5; i++) {  
			            		/*从这些页面中爬取代理信息，加入proxyGenerator*/  
			            		try {
			            			addProxyCustom("http://www.kuaidaili.com/free/intr/" + i + "/");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return null;
								}
			            		 if (this.proxys.isEmpty()) {
			            			 return null;
			            		 }else{
			            			 return this.proxys.get(0);
			            		 }
	            		  }
		            	 return null;
		            }
		            if (this.proxys.size() == 1) {
		            	 for (int i = 1; i <= 5; i++) {  
			            		/*从这些页面中爬取代理信息，加入proxyGenerator*/
			            		try {
			            			addProxyCustom("http://www.kuaidaili.com/free/intr/" + i + "/");
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
			            		  }
		                return this.proxys.get(0);
		            }
		            try {
		            	/*小于５个，再次获取*/
		            	if(proxys.size()<5){
		            		
		            		 for (int i = 1; i <= 5; i++) {  
		            		/*从这些页面中爬取代理信息，加入proxyGenerator*/  
		            			 addProxyCustom("http://www.kuaidaili.com/free/intr/" + i + "/");
		            		  }
		            	}
		            	if(proxysGood.size()>0){
		            		return proxysGood.get(0);
		            	}else{
		            		
		            		int r = this.random.nextInt(this.proxys.size());
		            		return proxys.get(r);
		            	}
		            } catch (Exception ex) {
		                return null;
		            }
		        }
		        
		    }
		 
		 
            /*每当用一个代理爬取成功，会触发markGood方法*/  
            @Override  
            public void markGood(Proxy proxy, String url) { 
            	if(proxysGood != null && !proxysGood.contains(proxy)){
            		proxysGood.add(proxy);
            	}
                InetSocketAddress address = (InetSocketAddress) proxy.address();  
                System.out.println("Good Proxy:" + address.toString() + "   " + url);  
            }  
  
            /*每当用一个代理爬取失败，会触发markBad方法*/  
            @Override  
            public void markBad(Proxy proxy, String url) {  
                InetSocketAddress address = (InetSocketAddress) proxy.address();  
                System.out.println("Bad Proxy:" + address.toString() + "   " + url);  
                /*删除失败代理*/
                this.removeProxy(proxy);
                proxysGood.remove(proxy);
  
                /*可以利用markGood或者markBad给出的反馈，来调整随机代理生成器中的代理*/  
                /*可以动态添加或删除代理，这些操作都是线程安全的*/  
                //removeProxy(proxy);  
                  
                /*随机代理RandomProxyGenerator是一种比较差的策略， 
                  我们建议用户自己编写符合自己业务的ProxyGenerator。 
                  编写ProxyGenerator主要实现ProxyGenerator中的next方法。*/  
            }  
            
        	public  void addProxyCustom(String url) throws Exception {  
                /*HttpRequest是2.07版的新特性*/ 
//                HttpRequest request = new HttpRequest(url);
                /*重试3次*/  
                for (int i = 0; i <= 3; i++) {  
                    try {  
                    	String html = HttpConnectionUtil.getHttpContent(url, "UTF-8");
//                        String html = request.getResponse().getHtmlByCharsetDetect(); 
                        Document doc = Jsoup.parseBodyFragment(html);
                        System.out.println(html);
                        if(doc.getElementsByTag("tbody") == null){
                        	//动态ip获取失败
                        	return;
                        }
                        Elements els  = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
                        for(int ii = 0;ii<els.size();ii++){
                        	Elements els_s  = els.get(ii).getElementsByTag("td");
                        	
                        	if(els_s.get(0).text() != null && els_s.get(1).text() != null){
                        		this.addProxy(els_s.get(0).text(), Integer.valueOf(els_s.get(1).text()));  
                        		System.out.println(els_s.get(0).text()+": "+ Integer.valueOf(els_s.get(1).text()));
                        	}
                        }
                        
                        break;  
                    } catch (Exception ex) {  
                        ex.printStackTrace();  
                    }  
                }  
            } 
  
        };
}
