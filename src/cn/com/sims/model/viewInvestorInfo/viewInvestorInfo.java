package cn.com.sims.model.viewInvestorInfo;

import java.sql.Timestamp;

public class viewInvestorInfo {
   private String view_investor_id;
   private String base_investor_code;
   private String base_investor_name;
   private String base_investor_phone;
   private String base_investor_wechat;
   private String base_investor_email;
   private String base_investor_weibo;
   private String base_investor_img;
   private String view_investor_inducode;
   private String view_investor_inducont;
   private String view_investor_payattcode;
   private String view_investor_payattcont;
   private String view_investment_code;
   private String view_investment_cont;
   private String base_investor_namep;
   private String base_investor_namepf;
   private String deleteflag;
   private String createid;
   private Timestamp createtime;
   private String updid;
   private Timestamp updtime;
   
   //职位名称
   private String base_investor_posiname;
   
public String getView_investor_id() {
	return view_investor_id;
}
public void setView_investor_id(String view_investor_id) {
	this.view_investor_id = view_investor_id;
}
public String getBase_investor_code() {
	return base_investor_code;
}
public void setBase_investor_code(String base_investor_code) {
	this.base_investor_code = base_investor_code;
}
public String getBase_investor_name() {
	return base_investor_name;
}
public void setBase_investor_name(String base_investor_name) {
	this.base_investor_name = base_investor_name;
}
public String getBase_investor_phone() {
	return base_investor_phone;
}
public void setBase_investor_phone(String base_investor_phone) {
	this.base_investor_phone = base_investor_phone;
}
public String getBase_investor_wechat() {
	return base_investor_wechat;
}
public void setBase_investor_wechat(String base_investor_wechat) {
	this.base_investor_wechat = base_investor_wechat;
}
public String getBase_investor_email() {
	return base_investor_email;
}
public void setBase_investor_email(String base_investor_email) {
	this.base_investor_email = base_investor_email;
}
public String getBase_investor_weibo() {
	return base_investor_weibo;
}
public void setBase_investor_weibo(String base_investor_weibo) {
	this.base_investor_weibo = base_investor_weibo;
}
public String getBase_investor_img() {
	return base_investor_img;
}
public void setBase_investor_img(String base_investor_img) {
	this.base_investor_img = base_investor_img;
}
public String getView_investor_inducode() {
	return view_investor_inducode;
}
public void setView_investor_inducode(String view_investor_inducode) {
	this.view_investor_inducode = view_investor_inducode;
}
public String getView_investor_inducont() {
	return view_investor_inducont;
}
public void setView_investor_inducont(String view_investor_inducont) {
	this.view_investor_inducont = view_investor_inducont;
}
public String getView_investor_payattcode() {
	return view_investor_payattcode;
}
public void setView_investor_payattcode(String view_investor_payattcode) {
	this.view_investor_payattcode = view_investor_payattcode;
}
public String getView_investor_payattcont() {
	return view_investor_payattcont;
}
public void setView_investor_payattcont(String view_investor_payattcont) {
	this.view_investor_payattcont = view_investor_payattcont;
}
public String getView_investment_code() {
	return view_investment_code;
}
public void setView_investment_code(String view_investment_code) {
	this.view_investment_code = view_investment_code;
}
public String getView_investment_cont() {
	return view_investment_cont;
}
public void setView_investment_cont(String view_investment_cont) {
	this.view_investment_cont = view_investment_cont;
}
public String getBase_investor_namep() {
	return base_investor_namep;
}
public void setBase_investor_namep(String base_investor_namep) {
	this.base_investor_namep = base_investor_namep;
}
public String getBase_investor_namepf() {
	return base_investor_namepf;
}
public void setBase_investor_namepf(String base_investor_namepf) {
	this.base_investor_namepf = base_investor_namepf;
}
public String getDeleteflag() {
	return deleteflag;
}
public void setDeleteflag(String deleteflag) {
	this.deleteflag = deleteflag;
}
public String getCreateid() {
	return createid;
}
public void setCreateid(String createid) {
	this.createid = createid;
}
public Timestamp getCreatetime() {
	return createtime;
}
public void setCreatetime(Timestamp createtime) {
	this.createtime = createtime;
}
public String getUpdid() {
	return updid;
}
public void setUpdid(String updid) {
	this.updid = updid;
}
public Timestamp getUpdtime() {
	return updtime;
}
public void setUpdtime(Timestamp updtime) {
	this.updtime = updtime;
}

public String getBase_investor_posiname() {
    return base_investor_posiname;
}
public void setBase_investor_posiname(String base_investor_posiname) {
    this.base_investor_posiname = base_investor_posiname;
}
public viewInvestorInfo(){
	
}
public viewInvestorInfo(String view_investor_id, String base_investor_code,
		String base_investor_name, String base_investor_phone,
		String base_investor_wechat, String base_investor_email,
		String base_investor_weibo, String base_investor_img,
		String view_investor_inducode, String view_investor_inducont,
		String view_investor_payattcode, String view_investor_payattcont,
		String view_investment_code, String view_investment_cont,
		String base_investor_namep, String base_investor_namepf,
		String deleteflag, String createid, Timestamp createtime, String updid,
		Timestamp updtime) {
	this.view_investor_id = view_investor_id;
	this.base_investor_code = base_investor_code;
	this.base_investor_name = base_investor_name;
	this.base_investor_phone = base_investor_phone;
	this.base_investor_wechat = base_investor_wechat;
	this.base_investor_email = base_investor_email;
	this.base_investor_weibo = base_investor_weibo;
	this.base_investor_img = base_investor_img;
	this.view_investor_inducode = view_investor_inducode;
	this.view_investor_inducont = view_investor_inducont;
	this.view_investor_payattcode = view_investor_payattcode;
	this.view_investor_payattcont = view_investor_payattcont;
	this.view_investment_code = view_investment_code;
	this.view_investment_cont = view_investment_cont;
	this.base_investor_namep = base_investor_namep;
	this.base_investor_namepf = base_investor_namepf;
	this.deleteflag = deleteflag;
	this.createid = createid;
	this.createtime = createtime;
	this.updid = updid;
	this.updtime = updtime;
}
   
}
