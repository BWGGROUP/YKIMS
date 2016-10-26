package cn.com.sims.importITdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.investment.investmentDetailInfoService.IInvestmentDetailInfoService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;


@Controller
public class ThreadAction {
	@Resource
	IInvestmentDetailInfoService iInvestmentDetailInfoService;//投资机构详情
	
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	
	@Resource
	SysUserInfoService SysUserInfoService;//系统用户

	@Resource
	ISysLabelelementInfoService dic;//字典
	
	
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	
	@Resource
	IBaseInvestmentInfoService baseInvestmentInfoService;//投资机构信息基础层
	
	/* log */
	private static final Logger logger = Logger.getLogger(ThreadAction.class);
	
	
	/**
	 * @author rqq
	 * @date 2016/06/25
	 * 线程模拟,模拟m个人点击n次，每个人x秒点击一次，每个人之间的时间间隔是y秒
	 */
	@RequestMapping(value="threadtest")
	public void threadtest(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("threadnum") String threadnumstr,
								//线程个数 m个人
			@RequestParam("threadcount") String threadcountstr,
								//线程执行次数　m个人请求n次
			@RequestParam("threadcounttime") String threadcounttimestr,
								//线程执行次数时间间隔 　n次请求时间间隔x　毫秒
			@RequestParam("threadnumtime") String threadnumtimestr,
								//单次每个线程之间执行时间间隔　m个人请求时间间隔y毫秒
			@RequestParam("threadfuncount") String threadfuncountstr
								//单个线程内容方法执行次数　　线程执行的方法多少次
						) throws Exception{
		logger.info("ThreadAction threadtest方法Start");
		Long time11=System.currentTimeMillis();
		String mesString="success";
		try {
		       	String namelog="mytherad-log";
	        	String nameid="mytherad-id";
			String fileName=FileOperation.createFile(namelog);
			String fileName1=FileOperation.createFile(nameid);
		  //线程个数 m个人
		   Long threadnum=Long.valueOf(threadnumstr);
		 //线程执行次数　m个人请求n次
		   Long threadcount=Long.valueOf(threadcountstr);
		 //线程执行次数时间间隔 　n次请求时间间隔x　秒	
			int threadcounttime=Integer.valueOf(threadcounttimestr);
		//单次每个线程之间执行时间间隔　m个人请求时间间隔y秒
//			int threadnumtime=Integer.valueOf(threadnumtimestr);
		//单个线程内容方法执行次数　　线程执行的方法多少次
			int threadfuncount=Integer.valueOf(threadfuncountstr);
			//创建ｍ个线程
			
			for(Long m=(long) 0;m<threadnum;m++){
			    Long m1=m+1;
			    String name="mytherad"+m1.toString();
			    Mytherad mytherad=new Mytherad(name,threadcount,threadcounttime,threadfuncount,fileName,fileName1);
			    new Thread(mytherad).start();
			}
		} catch (Exception e) {
			logger.error("ThreadAction threadtest发生异常",e);
			e.printStackTrace();
		}
		Long time22=System.currentTimeMillis()-time11;	
		System.out.println("执行时间差："+time22);
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("mesString", mesString);
			resultJSON.put("time22", time22);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("ThreadAction threadtest发生异常",e);
			e.printStackTrace();
			throw e;
		} catch (JSONException e) {
			logger.error("ThreadAction threadtest发生异常",e);
			e.printStackTrace();
			throw e;
		}
	}

	    /**
	     * @author 
	     * 取得线程的名称,执行需要验证的方法
	     * */
	    class Mytherad implements Runnable {
		 private String name;
		 private Long n;
		 private int x;
		 private int a;
		 private String fileName;
		 private String fileName1;
		public Mytherad() {
		    
		    }
		 
		    public Mytherad(String name,Long n,int x,int a,String fileName,String fileName1) {
		        this.name = name;
		        this.n = n;//线程执行次数
		        this.x = x;//执行间隔
		        this.a = a;//方法调用次数
		        this.fileName = fileName;
		        this.fileName1 = fileName1;
		    }

	        public void run() {
	            try {
			 FileWriter fileWritter = new FileWriter(fileName,true);
			    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	            
	            for (Long j = (long) 0; j < n; j++) {
	        			long j1=j+1;
        	            for (int i =  0; i < a; i++) {
        	        		int i1=i+1;
        	        		System.out.println("现在执行开始线程："+name+"第"+j1+"次执行线程"+"第"+i1+"次执行方法开始时间："+System.currentTimeMillis());
        	        		bufferWritter.write("现在执行开始线程："+name+"第"+j1+"次执行线程"+"第"+i1+"次执行方法开始时间："+System.currentTimeMillis());
        	        		bufferWritter.write("\n");
        	        		String stringname=name+"第"+j1+"次执行线程"+"第"+i1+"次执行方法：";
//        	             addcouponbyid(stringname,fileName,fileName1);
        	             addcouponbyid();
        	             System.out.println("现在线程："+name+"第"+j1+"次执行线程"+"第"+i1+"次执行方法结束时间："+System.currentTimeMillis());
	        				bufferWritter.write("现在线程："+name+"第"+j1+"次执行线程"+"第"+i1+"次执行方法结束时间："+System.currentTimeMillis());
	        				bufferWritter.write("\n");
        	            }
				    		Thread.sleep(x);
    
        	            System.out.println("线程："+name+"第"+j1+"次执行线程执行完毕结束时间："+System.currentTimeMillis());
							bufferWritter.write("线程："+name+"第"+j1+"次执行线程执行完毕结束时间："+System.currentTimeMillis());
							bufferWritter.write("\n");
        	            
	            }
	            System.out.println("线程："+name+"执行完毕结束时间："+System.currentTimeMillis());
			bufferWritter.write("线程："+name+"执行完毕结束时间："+System.currentTimeMillis());
			bufferWritter.write("\n");
			bufferWritter.close();
	            } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
	     
	    }
	    }
		 /**
	     * @author rqq
	     * @date 2015/09/06 用户注册奖励用户优惠券
	     */
//	    public void addcouponbyid(String stringname,String fileName,String fileName1) {
		public void addcouponbyid() {
		    String orgCode="IN568309547214941631";
		    String orgName="万达信息";
		    String logintype="MB";
		    String fName="万达信息";
		    String cName="万达信息";
		    String eName="万达信息";
		    viewInvestmentInfo orgInfo=null;//定义投资机构详情
			baseInvestmentInfo baseInfo=null;//定义投资机构信息
			String[] pinYin=CommonUtil.getPinYin(cName);//获取机构名称全拼,简拼
			int version=1;
//		String coupon_message="nocoupon";
		logger.info("ThreadAction.addcouponbyid()方法Start");
//		Map<String, Object> couponmap=new HashMap<String, Object>();
//		String codeidString="fail";
//		Long time1=System.currentTimeMillis();
		try {
//		    FileWriter fileWritter = new FileWriter(fileName,true);
//		    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//		    FileWriter fileWritter1 = new FileWriter(fileName1,true);
//		    BufferedWriter bufferWritter1 = new BufferedWriter(fileWritter1);
//		bufferWritter.write("现在执行开始："+stringname+System.currentTimeMillis());
//		bufferWritter.write("\n");
//		System.out.println("现在执行开始："+stringname+System.currentTimeMillis());
//		baseInvestmentInfoService.tranModifyBaseInvestment(orgCode, cName, eName, fName, null, null, null, pinYin[0], pinYin[1], null, null, null, null, "admin", new Timestamp(new Date().getTime()), 1, "0", null, null, null);
		
		
		/*调用存储过程*/
		storedProcedureService.callViewinvestment(new SysStoredProcedure(orgCode, "upd", CommonUtil.findNoteTxtOfXML("basic"),"admin","",""));
		
		/*查询投资机构详情*/
		orgInfo=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);//投资机构详情
		
		
		/*查询投资机构信息*/
		baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
		
		/*获取最新排他锁版本号*/
//		version=baseInfo.getBase_datalock_viewtype();
		    
		
//		couponmap.put("coupon_message", coupon_message);
//		Long time2=System.currentTimeMillis()-time1;
//		System.out.println("现在执行结束的是："+stringname+System.currentTimeMillis());
//		System.out.println("现在执行时间差："+time2+"");
//
//		    bufferWritter.write("现在执行结束的是："+stringname+System.currentTimeMillis());
//		
//		bufferWritter.write("\n");
//		bufferWritter.write("现在执行时间差："+time2+"");
//		bufferWritter.write("\n");
//		bufferWritter.close();
//		bufferWritter1.close();
		} catch (Exception e) {
			logger.error("ThreadAction.addcouponbyid() 发生异常",e);
//			coupon_message="error";
			e.printStackTrace();
		};
		
	} 
	  
}
