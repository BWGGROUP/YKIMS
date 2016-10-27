package cn.com.sims.util.email;

/** 
 * @File: IEmailSenderService.java 
 * @Package n.com.canon.traveler.common.service
 * @Description: TODO 
 * @Copyright: Copyright(c)2012 
 * @Company: shbs  
 * @author rqq
 * @date 2014/08/18 
 * @version V1.0 
 **/

import java.util.List;

import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.model.sysuserinfo.SysUserInfo;


/**  
 * @ClassName: IEmailSenderService.java 
 * @Description: 邮件模版
 * @author rqq
 * @date 2014/08/18
 **/
public interface IEmailSenderService {
	/** 
	 *@Title: sendEmail 
	 * @param 邮件接口  需要传参：存放邮件地址的List<String>    EmailBean对象List-->对应邮件变量              文件代号
	 * @throws Exception
	 */
	public void sendEmail(List<String> mailList, List<EmailBean> mailBeanList,String f) throws Exception;
//	public void sendEmail(List<String> mailList, String f) throws Exception;
	
	
	
	/**
	 * 推送会议信息邮件
	 * @param loginemail 登录用户邮箱
	 * @param loginname 登录用户名称
	 * @param address 会议链接路径
	 * @param ftl 邮件模版
	 * @param list 发送邮箱用户集合
	 * @param comtext 内容说明
	 * @author duwenjie
	 * @date 2016-3-22
	 */
	public void sendMeetingEmail(String loginemail,String loginname,String address,String ftl,String comtext,List<SysUserInfo> list)throws Exception;
	
}
