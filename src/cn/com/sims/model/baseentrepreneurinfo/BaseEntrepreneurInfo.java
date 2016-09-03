package cn.com.sims.model.baseentrepreneurinfo;

import java.sql.Timestamp;

public class BaseEntrepreneurInfo {
    	//创业者id
	private	String	base_entrepreneur_code	;
	//创业者姓名
	private	String	base_entrepreneur_name	;
	//创业者手机
	private	String	base_entrepreneur_phone	;
	//创业者微信
	private	String	base_entrepreneur_wechat	;
	//创业者邮箱
	private	String	base_entrepreneur_email	;
	//创业者微博
	private	String	base_entrepreneur_weibo	;
	//创业者图片
	private	String	base_entrepreneur_img	;
	//创建来源
	private	String	base_entrepreneur_stem	;
	//当前状态
	private	String	base_entrepreneur_estate	;
	//姓名拼音全拼
	private	String	base_entrepreneur_namep	;
	//姓名拼音首字母
	private	String	base_entrepreneur_namepf	;
	//对应IT桔子id
	private	String	Tmp_Entrepreneur_Code	;
	//删除标识
	private	String	deleteflag	;
	//创建者
	private	String	createid	;
	//创建时间
	private	Timestamp	createtime	;
	//更新者
	private	String	updid	;
	//更新时间
	private	Timestamp	updtime	;
	
	//联系人职位
	private	String	base_entrepreneur_posiname	;
	
	public BaseEntrepreneurInfo(){}
	
	public BaseEntrepreneurInfo(
			String	base_entrepreneur_code	,
			String	base_entrepreneur_name	,
			String	base_entrepreneur_phone	,
			String	base_entrepreneur_wechat	,
			String	base_entrepreneur_email	,
			String	base_entrepreneur_weibo	,
			String	base_entrepreneur_img	,
			String	base_entrepreneur_stem	,
			String	base_entrepreneur_estate	,
			String	base_entrepreneur_namep	,
			String	base_entrepreneur_namepf	,
			String	Tmp_Entrepreneur_Code	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime){
		this.base_entrepreneur_code	=	base_entrepreneur_code	;
		this.base_entrepreneur_name	=	base_entrepreneur_name	;
		this.base_entrepreneur_phone	=	base_entrepreneur_phone	;
		this.base_entrepreneur_wechat	=	base_entrepreneur_wechat	;
		this.base_entrepreneur_email	=	base_entrepreneur_email	;
		this.base_entrepreneur_weibo	=	base_entrepreneur_weibo	;
		this.base_entrepreneur_img	=	base_entrepreneur_img	;
		this.base_entrepreneur_stem	=	base_entrepreneur_stem	;
		this.base_entrepreneur_estate	=	base_entrepreneur_estate	;
		this.base_entrepreneur_namep	=	base_entrepreneur_namep	;
		this.base_entrepreneur_namepf	=	base_entrepreneur_namepf	;
		this.Tmp_Entrepreneur_Code	=	Tmp_Entrepreneur_Code	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;

	}

	public String getBase_entrepreneur_code() {
	    return base_entrepreneur_code;
	}

	public void setBase_entrepreneur_code(String base_entrepreneur_code) {
	    this.base_entrepreneur_code = base_entrepreneur_code;
	}

	public String getBase_entrepreneur_name() {
	    return base_entrepreneur_name;
	}

	public void setBase_entrepreneur_name(String base_entrepreneur_name) {
	    this.base_entrepreneur_name = base_entrepreneur_name;
	}

	public String getBase_entrepreneur_phone() {
	    return base_entrepreneur_phone;
	}

	public void setBase_entrepreneur_phone(String base_entrepreneur_phone) {
	    this.base_entrepreneur_phone = base_entrepreneur_phone;
	}

	public String getBase_entrepreneur_wechat() {
	    return base_entrepreneur_wechat;
	}

	public void setBase_entrepreneur_wechat(String base_entrepreneur_wechat) {
	    this.base_entrepreneur_wechat = base_entrepreneur_wechat;
	}

	public String getBase_entrepreneur_email() {
	    return base_entrepreneur_email;
	}

	public void setBase_entrepreneur_email(String base_entrepreneur_email) {
	    this.base_entrepreneur_email = base_entrepreneur_email;
	}

	public String getBase_entrepreneur_weibo() {
	    return base_entrepreneur_weibo;
	}

	public void setBase_entrepreneur_weibo(String base_entrepreneur_weibo) {
	    this.base_entrepreneur_weibo = base_entrepreneur_weibo;
	}

	public String getBase_entrepreneur_img() {
	    return base_entrepreneur_img;
	}

	public void setBase_entrepreneur_img(String base_entrepreneur_img) {
	    this.base_entrepreneur_img = base_entrepreneur_img;
	}

	public String getBase_entrepreneur_stem() {
	    return base_entrepreneur_stem;
	}

	public void setBase_entrepreneur_stem(String base_entrepreneur_stem) {
	    this.base_entrepreneur_stem = base_entrepreneur_stem;
	}

	public String getBase_entrepreneur_estate() {
	    return base_entrepreneur_estate;
	}

	public void setBase_entrepreneur_estate(String base_entrepreneur_estate) {
	    this.base_entrepreneur_estate = base_entrepreneur_estate;
	}

	public String getBase_entrepreneur_namep() {
	    return base_entrepreneur_namep;
	}

	public void setBase_entrepreneur_namep(String base_entrepreneur_namep) {
	    this.base_entrepreneur_namep = base_entrepreneur_namep;
	}

	public String getBase_entrepreneur_namepf() {
	    return base_entrepreneur_namepf;
	}

	public void setBase_entrepreneur_namepf(String base_entrepreneur_namepf) {
	    this.base_entrepreneur_namepf = base_entrepreneur_namepf;
	}

	public String getTmp_Entrepreneur_Code() {
	    return Tmp_Entrepreneur_Code;
	}

	public void setTmp_Entrepreneur_Code(String tmp_Entrepreneur_Code) {
	    Tmp_Entrepreneur_Code = tmp_Entrepreneur_Code;
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

	public String getBase_entrepreneur_posiname() {
	    return base_entrepreneur_posiname;
	}

	public void setBase_entrepreneur_posiname(String base_entrepreneur_posiname) {
	    this.base_entrepreneur_posiname = base_entrepreneur_posiname;
	}
	
	
}
