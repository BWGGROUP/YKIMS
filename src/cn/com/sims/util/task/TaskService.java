package cn.com.sims.util.task;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;
import cn.com.sims.dao.financing.financingsearch.FinancingSearchDao;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.util.email.IEmailSenderService;
import cn.com.sims.dao.sysuserinfo.SysUserInfoDao;
import cn.com.sims.common.commonUtil.*;
/** 
 * @author  yl
 * @date ：2015年10月30日 
 * 类说明 
 */
public class TaskService {
	@Resource
	IEmailSenderService emailSenderService;// 邮件service
	@Resource
	FinancingSearchDao financingSearchDao;//融资计划dao
	@Resource
	SysUserInfoDao SysUserInfoDao;//用户dao
	@Resource
	CompanyDetailDao companyDetailDao;//公司dao
	private static final Logger logger = Logger
			.getLogger(TaskService.class);
   public void sendEmail(){
	   logger.info("TaskService()方法定时发送邮件Start");
	   List<BaseFinancplanInfo> financingList = new ArrayList<BaseFinancplanInfo>();
	   try{
		   String nowdate = CommonUtil.getDayTime_tamp().toString();
		   SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");  
			EmailBean emailBean = new EmailBean();
			BaseFinancplanEmail basefinancplanemail = new BaseFinancplanEmail();
		   //获取提醒时间大于等于现在时间并且待分发的邮件
		   financingList = financingSearchDao.findFinancing("BaseFinancplanInfo.findFinancingfortask",nowdate);
		   if(financingList!=null && financingList.size() >0 ){
			   for(int i=0;i<financingList.size();i++){
			       //更新数据库状态
			    basefinancplanemail.setBase_financplan_code(financingList.get(i).getBase_financplan_code());		
				basefinancplanemail.setBase_financplan_email(financingList.get(i).getSys_user_code());
				basefinancplanemail.setBase_financplan_sendwstate("1");
				basefinancplanemail.setUpdtime(new Timestamp(new Date().getTime()));
				basefinancplanemail.setUpdid(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
				basefinancplanemail.setBase_financplan_code(financingList.get(i).getBase_financplan_code());
				financingSearchDao.updateFinlanemail("BaseFinancplanEmail.updateFinlan",basefinancplanemail);
			       	long time = Long.parseLong(financingList.get(i).getBase_financplan_date());
				      String d = format.format(new Date(time));  
				      String compName =companyDetailDao.findNameByCode("viewCompInfo.findNameByCode",financingList.get(i).getBase_comp_code());
					if(financingList.get(i).getSys_user_email()!=null
						&&!"".equals(financingList.get(i).getSys_user_email()) ){
						try{
							List<String> mailList = new ArrayList<String>(); /* 存储邮箱地址 */
							List<EmailBean> mailBeanList = new ArrayList<EmailBean>();/* 邮箱中的变量 */
							emailBean.setAccountName(financingList.get(i).getSys_user_name());
							emailBean.setCompName(compName);
							emailBean.setText(financingList.get(i).getBase_financplan_cont());
							emailBean.setFialantime(d);
							mailList.add(financingList.get(i).getSys_user_email());
							mailBeanList.add(emailBean);
							
							emailSenderService.sendEmail(mailList, mailBeanList,
									"taskSendEmail.ftl");
							}catch(Exception e){
							}
						
					}
					
							/**
							 * 发送微信
							 */
					
					if(financingList.get(i).getSys_user_wechatopen()!=null
						&&!"".equals(financingList.get(i).getSys_user_wechatopen()) ){
					    HttpURLConnection con = null;
					    DataOutputStream out =null;
			   		    	try {
			   		    	URL url = new URL(CommonUtil.findNoteTxtOfXML("WCHAT"));
			   					con = (HttpURLConnection) url.openConnection();
			   					con.setDoOutput(true);
			   				        // Read from the connection. Default is true.
			   					con.setDoInput(true);
			   					con.setRequestMethod("POST"); 
			   					con.setUseCaches(false);
			   					con.setInstanceFollowRedirects(true);
			   					con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			   					con.setRequestProperty("SIMS_SEND_MESSAGE", "SIMS_SEND_MESSAGE_HEADER");
			   					con.connect();
			   					out = new DataOutputStream(con
			   				                .getOutputStream());
			   				        String content = "ToUsers="+
			   				        	URLEncoder.encode(financingList.get(i).getSys_user_wechatopen(), "UTF-8")
			   				        	+"&Message="+URLEncoder.encode("您好,您于"+emailBean.getFialantime()+"有一项"+emailBean.getCompName()+"公司的融资计划，内容为："+emailBean.getText(), "UTF-8");
			   				        out.writeBytes(content);

			   				        out.flush();
			   				        out.close(); 
			   					con.getInputStream();
			   					con.disconnect();
			   					
			   				} catch (Exception e) {
			   					e.printStackTrace();
			   				}finally{
			   				 if(out!=null){
			   				  out.flush();
			   				        out.close(); 
				   				 }
			   				 if(con!=null){
			   				  con.disconnect();
			   				 }
			   				}
					}
					
					}
			   logger.info("TaskService()没有需要发送邮件提醒的融资计划");
			   }
		  
		   logger.info("TaskService()方法结束");
	   }catch(Exception e){
		   logger.error("TaskService()方法运行异常"+e);
		   e.printStackTrace();
	   }

   }

   public void deleteExportFile(){
	   logger.info("deleteExportFile()方法定时删除文件Start");
	   
	   try{
		   String xlsDownloadPath = CommonUtil.findNoteTxtOfXML("INVESTMENT_DOWN");
		   String expiredTime = CommonUtil.findNoteTxtOfXML("Email_FAILURE_TIME");
		   DeleteDirectory.deleteDir(new File(xlsDownloadPath), Long.parseLong(expiredTime));
	   }catch(Exception e){
		   logger.error("deleteExportFile()方法定时删除文件异常",e);
	   }
	   logger.info("deleteExportFile()方法定时删除文件End");
   }
}
