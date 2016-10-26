package cn.com.sims.util.email;

/** 
 * @File: EmailSenderServiceImpl.java 
 * @Package c
 * @Description: TODO 
 * @Copyright: Copyright(c)2012 
 * @Company: shbs  
 * @author rqq
 * @date 2014/08/18 
 * @version V1.0 
 **/

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import freemarker.template.Template;
/**  
 * @ClassName: EmailSenderServiceImpl.java 
 * @Description: 发送邮件 
 * @author rqq
 * @date 2014/08/18
 **/
@Service
public class EmailSenderServiceImpl implements IEmailSenderService {
	
	private JavaMailSender mailSender;
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private static final Logger logger = Logger.getLogger(EmailSenderServiceImpl.class);
	/**
	 * @Title: sendEmail
	 * @Description:发送邮件入口
	 * @param 传参   字符串List---邮件地址                     List 邮件对象==bean     所需的文件全称             
	 */
	public void sendEmail(List<String> mailList, List<EmailBean> mailBeanList,String f) throws Exception{
	//public void sendEmail(List<String> mailList,String f) throws Exception{
		logger.info("EmailSenderServiceImpl sendEmail 方法开始");
		try{
			if(mailList==null ||mailList.size()==0){
				if(!CommonUtil.emailFormat(CommonUtil.findNoteTxtOfXML("MAIL_TO"))){//验证邮箱格式
					return;
				 }
				MimeMessage mailMessage =  mailSender.createMimeMessage();
				MimeMessageHelper email=new MimeMessageHelper(mailMessage,true,"utf-8");
				email.setFrom(CommonUtil.findNoteTxtOfXML("MAIL_FROM"));//发件人
				email.setTo(CommonUtil.findNoteTxtOfXML("MAIL_TO"));//收件人
				email.setSubject(CommonUtil.findNoteTxtOfXML("TITLE"));//设置邮件主题
				String htmlText=getMailText(null,f);
				email.setText(htmlText,true);
				mailSender.send(mailMessage);
			}
			else{
			for (int i = 0; i < mailList.size(); i++) {
				if(!CommonUtil.emailFormat(mailList.get(i))){//验证邮箱格式
					continue;
				 }
	
				MimeMessage mailMessage =  mailSender.createMimeMessage();
				MimeMessageHelper email=new MimeMessageHelper(mailMessage,true,"utf-8");
				email.setFrom(CommonUtil.findNoteTxtOfXML("MAIL_FROM"));//发件人
				email.setTo(mailList.get(i));//收件人
				if("excelDownload.ftl".equals(f)){
					email.setSubject(CommonUtil.findNoteTxtOfXML("TITLE"));//设置邮件主题
				}else if("taskSendEmail.ftl".equals(f)){
					email.setSubject(CommonUtil.findNoteTxtOfXML("TITLEEMAIL"));//设置邮件主题
				}else if("getCcnPass.ftl".equals(f)){
					email.setSubject(CommonUtil.findNoteTxtOfXML("FORTPAWEMAIL"));//设置邮件主题
				}else if("Newuser.ftl".equals(f)){
					email.setSubject(CommonUtil.findNoteTxtOfXML("NEWUSER"));//设置邮件主题
				}
				
				String htmlText=getMailText(mailBeanList.get(i),f);
				email.setText(htmlText,true);
				if (mailBeanList.get(i).getMeailsendtime()!=null) {
					Date data=new Date();
					data.setTime(mailBeanList.get(i).getMeailsendtime().getTime());
					email.setSentDate(data);
				}
				mailSender.send(mailMessage);
			}
			}
			
		}catch(Exception e){
			logger.error("EmailSenderServiceImpl sendEmail 方法开始"+e);
			throw e;
		}
		logger.info("EmailSenderServiceImpl sendEmail 方法结束");
	}
	
	/**
	 * @Title:getMailText
	 * @Description:给邮件模板赋值
	 * @param   EmailBean 对象  和文件名
	 * @throws Exception 
	 * */
	@SuppressWarnings("unchecked")
	private String getMailText(EmailBean email,String ftlName) throws Exception{
		String htmlText="";
		Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate(ftlName);//对应文件名
		Map map=new HashMap();//Map传递数据
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");/*获得当前日期*/
		if(ftlName.equals("excelDownload.ftl")){//导出excel表格
			map.put("downloadTime", email.getDownloadTime());
			map.put("dateTime", email.getDateTime());
			map.put("accountName", email.getAccountName());
			map.put("downloadUrl", email.getDownloadUrl());
			map.put("nowDate", formatter.format(new Date()));
		}else if(ftlName.equals("taskSendEmail.ftl")){//融资计划定时发送邮件
			map.put("accountName", email.getAccountName());
			map.put("Fialantime", email.getFialantime());
			map.put("CompName", email.getCompName());
			map.put("text", email.getText());
		}else if("getCcnPass.ftl".equals(ftlName)||"Newuser.ftl".equals(ftlName)){
			map.put("accountName", email.getAccountName());
			map.put("useremail", email.getUseremail());
			map.put("newpaw", email.getNewpaw());
		}else if("meetingMessage.ftl".equals(ftlName)
				||"meetingUpdMessage.ftl".equals(ftlName)){
			/*duwenjie add 会议提醒*/
			map.put("accountName", email.getAccountName());
			map.put("address", email.getAddress());
			map.put("compName", email.getCompName());
			map.put("text", email.getText());
		}
		htmlText=FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
		return htmlText;
	}
	
	/**
	 * @Title:getFileName
	 * @Description:根据传过来的代号  确定文件名
	 * @param  i
	 * @return fileName
	 */
	private String getFileName(int i){
		String fileName="";
		if(i==1){
			fileName="updateDataError.ftl";
		}
		if(i==2){
			fileName="getCcnPass.ftl";
		}
		return fileName;
	}
	
	/**
	 * @Title:getMailTitle
	 * @Description:确定邮件主题
	 * @param  i
	 * @return mailtitle
	 */
	private String getMailTitle(int i){
		String dateNow=CommonUtil.getNowTime_tamp().toString().substring(0, 10);
		String mailtitle="";
		if(i==1){
			mailtitle=CommonUtil.findNoteTxtOfXML("EMAIL_ERROR_TITLE")+"-"+dateNow;
		}
		if(i==2){
			mailtitle=CommonUtil.findNoteTxtOfXML("EMAIL_GETPASS_TITLE")+"-"+dateNow;
		}
		return mailtitle;
	}
	
	/**
	 * 推送会议信息邮件
	 * @param loginemail 登录用户邮箱
	 * @param loginname 登录用户名称
	 * @param address 会议链接路径
	 * @param ftl 邮件模版
	 * @param list 发送邮箱用户集合
	 * @param comtext 邮件内容
	 * @author duwenjie
	 * @date 2016-3-22
	 */
	@Override
	public void sendMeetingEmail(String loginemail,
			String loginname,
			String address,
			String ftl,
			String comtext,
			List<SysUserInfo> list) throws Exception {
		logger.info("sendMeetingEmail 方法开始");
		MimeMessageHelper email=null;
		EmailBean emailBean=null;
		URL url=null;
		String wxcont="";
		HttpURLConnection con = null;
	    DataOutputStream out =null;
		try {
			if(loginemail!=null && list!= null && list.size()>0){
				for (SysUserInfo sysUserInfo : list) {
					/**
					 * 发送邮件
					 */
					if(sysUserInfo!=null 
							&& sysUserInfo.getSys_user_email()!=null 
							&& CommonUtil.emailFormat(sysUserInfo.getSys_user_email()) ){
						emailBean=new EmailBean();
						emailBean.setAccountName(sysUserInfo.getSys_user_name());
						emailBean.setAddress(address+CommonUtil.findNoteTxtOfXML("COMPUTER"));
						emailBean.setCompName(loginname);
						emailBean.setText(comtext);
						
						MimeMessage mailMessage =  mailSender.createMimeMessage();
						email=new MimeMessageHelper(mailMessage,true,"utf-8");
						email.setFrom(loginemail);//发件人
						email.setTo(sysUserInfo.getSys_user_email());//收件人   
						email.setSubject(CommonUtil.findNoteTxtOfXML("MEETINGTITIE"));//设置邮件主题
						String text=getMailText(emailBean, ftl);
						email.setText(text,true);
						mailSender.send(mailMessage);
						
					}
					
				}
				
				if(ftl.equals("meetingMessage.ftl")){
					wxcont=loginname+"添加了会议，查看详细内容请点击链接： "+address+CommonUtil.findNoteTxtOfXML("PHONE-BROWSER");
				}else if(ftl.equals("meetingUpdMessage.ftl")) {
					wxcont=loginname+"修改了会议，查看详细内容请点击链接： "+address+CommonUtil.findNoteTxtOfXML("PHONE-BROWSER");
				}else{
					wxcont="会议通知，查看详细内容请点击链接： "+address+CommonUtil.findNoteTxtOfXML("PHONE-BROWSER");
				}
				for (SysUserInfo sysUserInfo : list) {
					/**
					 * 发送微信
					 */
					if(sysUserInfo.getSys_user_wechatopen()!=null
						&&!"".equals(sysUserInfo.getSys_user_wechatopen()) ){
					    
			   		    	try {
			   		    		url = new URL(CommonUtil.findNoteTxtOfXML("WCHAT"));
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
			   				        	URLEncoder.encode(sysUserInfo.getSys_user_wechatopen(), "UTF-8")
			   				        	+"&Message="+URLEncoder.encode(sysUserInfo.getSys_user_name()+"您好！"+wxcont, "UTF-8");
			   				        out.writeBytes(content);
			   				        out.flush();
			   				        out.close(); 
			   					con.getInputStream();
			   					con.disconnect();
			   					
			   				} catch (Exception e) {
			   					e.printStackTrace();
			   					continue;
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
				
			}
			
		} catch (Exception e) {
			logger.error("sendMeetingEmail 方法异常",e);
			throw e;
		}
		logger.info("sendMeetingEmail 方法结束");
	}
	
	
	
	
	
	
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	

}
