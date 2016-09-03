package cn.com.sims.model.baseinvestmentinfo;

import java.sql.Timestamp;

public class baseInvestmentInfo {
	private	String	base_investment_code	;	//	投资机构id
	private	String	base_investment_name	;	//	投资机构简称
	private	String	base_investment_ename	;	//	投资机构英文名称
	private	String	base_investment_fullname	;	//	投资机构全称
	private	String	base_investment_img	;	//	投资机构图片
	private	String	base_investment_stem	;	//	创建来源
	private	String	base_investment_estate	;	//	当前状态
	private	String	base_investment_namep	;	//	机构简称拼音全拼
	private	String	base_investment_namepf	;	//	机构简称拼音首字母
	private	String	Tmp_Investment_Code	;	//	对应IT桔子id
	private	String	deleteflag	;	//	删除标识
	private	String	createid	;	//	创建者
	private	Timestamp	createtime	;	//	创建时间
	private	String	updid	;	//	更新者
	private	Timestamp	updtime	;	//	更新时间
	private	long	base_datalock_viewtype	;	//	排它锁
	private	String	base_datalock_pltype	;	//	PL锁状态
	private	String	sys_user_code	;	//	锁定者
	private	String	base_datalock_sessionid	;	//	锁定者sessionid
	private	String	base_datalock_viewtime	;	//	页面锁锁定时间

	
	public baseInvestmentInfo(){}
	
	public baseInvestmentInfo(
			String	base_investment_code	,
			String	base_investment_name	,
			String	base_investment_ename	,
			String	base_investment_fullname	,
			String	base_investment_img	,
			String	base_investment_stem	,
			String	base_investment_estate	,
			String	base_investment_namep	,
			String	base_investment_namepf	,
			String	Tmp_Investment_Code	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime	,
			long	base_datalock_viewtype	,
			String	base_datalock_pltype	,
			String	sys_user_code	,
			String	base_datalock_sessionid	,
			String	base_datalock_viewtime){
		this.base_investment_code	=	base_investment_code	;
		this.base_investment_name	=	base_investment_name	;
		this.base_investment_ename	=	base_investment_ename	;
		this.base_investment_fullname	=	base_investment_fullname	;
		this.base_investment_img	=	base_investment_img	;
		this.base_investment_stem	=	base_investment_stem	;
		this.base_investment_estate	=	base_investment_estate	;
		this.base_investment_namep	=	base_investment_namep	;
		this.base_investment_namepf	=	base_investment_namepf	;
		this.Tmp_Investment_Code	=	Tmp_Investment_Code	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;
		this.base_datalock_viewtype	=	base_datalock_viewtype	;
		this.base_datalock_pltype	=	base_datalock_pltype	;
		this.sys_user_code	=	sys_user_code	;
		this.base_datalock_sessionid	=	base_datalock_sessionid	;
		this.base_datalock_viewtime	=	base_datalock_viewtime	;

	}
	
	public String getBase_investment_code() {
		return base_investment_code;
	}
	public void setBase_investment_code(String base_investment_code) {
		this.base_investment_code = base_investment_code;
	}
	public String getBase_investment_name() {
		return base_investment_name;
	}
	public void setBase_investment_name(String base_investment_name) {
		this.base_investment_name = base_investment_name;
	}
	public String getBase_investment_ename() {
		return base_investment_ename;
	}
	public void setBase_investment_ename(String base_investment_ename) {
		this.base_investment_ename = base_investment_ename;
	}
	public String getBase_investment_fullname() {
		return base_investment_fullname;
	}
	public void setBase_investment_fullname(String base_investment_fullname) {
		this.base_investment_fullname = base_investment_fullname;
	}
	public String getBase_investment_img() {
		return base_investment_img;
	}
	public void setBase_investment_img(String base_investment_img) {
		this.base_investment_img = base_investment_img;
	}
	public String getBase_investment_stem() {
		return base_investment_stem;
	}
	public void setBase_investment_stem(String base_investment_stem) {
		this.base_investment_stem = base_investment_stem;
	}
	public String getBase_investment_estate() {
		return base_investment_estate;
	}
	public void setBase_investment_estate(String base_investment_estate) {
		this.base_investment_estate = base_investment_estate;
	}
	public String getBase_investment_namep() {
		return base_investment_namep;
	}
	public void setBase_investment_namep(String base_investment_namep) {
		this.base_investment_namep = base_investment_namep;
	}
	public String getBase_investment_namepf() {
		return base_investment_namepf;
	}
	public void setBase_investment_namepf(String base_investment_namepf) {
		this.base_investment_namepf = base_investment_namepf;
	}
	public String getTmp_Investment_Code() {
		return Tmp_Investment_Code;
	}
	public void setTmp_Investment_Code(String tmp_Investment_Code) {
		Tmp_Investment_Code = tmp_Investment_Code;
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
	public long getBase_datalock_viewtype() {
		return base_datalock_viewtype;
	}
	public void setBase_datalock_viewtype(long base_datalock_viewtype) {
		this.base_datalock_viewtype = base_datalock_viewtype;
	}
	public String getBase_datalock_pltype() {
		return base_datalock_pltype;
	}
	public void setBase_datalock_pltype(String base_datalock_pltype) {
		this.base_datalock_pltype = base_datalock_pltype;
	}
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
	}
	public String getBase_datalock_sessionid() {
		return base_datalock_sessionid;
	}
	public void setBase_datalock_sessionid(String base_datalock_sessionid) {
		this.base_datalock_sessionid = base_datalock_sessionid;
	}
	public String getBase_datalock_viewtime() {
		return base_datalock_viewtime;
	}
	public void setBase_datalock_viewtime(String base_datalock_viewtime) {
		this.base_datalock_viewtime = base_datalock_viewtime;
	}

  
}
