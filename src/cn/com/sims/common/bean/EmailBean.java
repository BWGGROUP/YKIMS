package cn.com.sims.common.bean;

import java.sql.Timestamp;

/** 
 * @File: TimingUdateData.java 
 * @Package cn.com.sims.common.bean
 * @Description: TODO 
 * @Copyright: Copyright(c)2012 
 * @Company: shbs  
 * @author qgx
 * @date 2014/08/18
 * @version V1.0 
 **/
/**  
 * @ClassName: TimingUdateData.java 
 * @Description: EmailBean
 * @author rqq
 * @date 2014/08/07 
 **/
public class EmailBean {

	private String accountId;//用户ID
	private String accountName;//真实姓名
	private String verifyCode;//验证码
	private Timestamp dateTime;//失效时间
	private Timestamp meailsendtime;//邮件发送时间
	private String address;//链接地址
	private String downloadTime;//下载时间
	private String downloadUrl;//下载地址url
	private String text;//融资内容
	private String fialantime;//融资计划日期
	private String compName;//公司名称
	private String useremail;//用户邮箱
	private String newpaw;//用户最新密码
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFialantime() {
		return fialantime;
	}
	public void setFialantime(String fialantime) {
		this.fialantime = fialantime;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getNewpaw() {
		return newpaw;
	}
	public void setNewpaw(String newpaw) {
		this.newpaw = newpaw;
	}
	public Timestamp getMeailsendtime() {
		return meailsendtime;
	}
	public void setMeailsendtime(Timestamp meailsendtime) {
		this.meailsendtime = meailsendtime;
	}
	
	
}
