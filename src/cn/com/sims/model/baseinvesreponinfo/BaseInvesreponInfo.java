package cn.com.sims.model.baseinvesreponinfo;

import java.sql.Timestamp;

public class BaseInvesreponInfo {
	private 	String	base_investment_code;//投资机构id
	private 	String	sys_user_code;//易凯用户id
	private 	String	sys_user_name;//易凯用户姓名
	private 	String	deleteflag;//删除标识
	private 	String	createid;//创建者
	private 	Timestamp	createtime;//创建时间
	private 	String	updid;//更新者
	private 	Timestamp	updtime;//更新时间
	
	public BaseInvesreponInfo(){}
	
	public BaseInvesreponInfo(
			String	base_investment_code	,
			String	sys_user_code	,
			String	sys_user_name	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime	){
		this.base_investment_code	=	base_investment_code	;
		this.sys_user_code	=	sys_user_code	;
		this.sys_user_name	=	sys_user_name	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;

	}
	
	public String getBase_investment_code() {
		return base_investment_code;
	}
	public void setBase_investment_code(String base_investment_code) {
		this.base_investment_code = base_investment_code;
	}
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
	}
	public String getSys_user_name() {
		return sys_user_name;
	}
	public void setSys_user_name(String sys_user_name) {
		this.sys_user_name = sys_user_name;
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

}
