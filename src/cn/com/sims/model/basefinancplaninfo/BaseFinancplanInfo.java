package cn.com.sims.model.basefinancplaninfo;

import java.sql.Timestamp;

/**
 * 公司融资计划（基本表）
 * @author zzg
 *
 */
public class BaseFinancplanInfo {
	private String	base_financplan_code	;
	private String	base_comp_code	;
	private String	base_financplan_date	;
	private String	base_financplan_cont	;
	private String	base_financplan_remindate	;
	private String	base_financplan_sendef	;
	private String	base_financplan_sendwf	;
	private String	base_financplan_sendestate	;
	private String	base_financplan_sendwstate	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	
//公司基本表内容
	private String base_comp_name;
	private String base_comp_fullname;
	private String base_comp_ename;
	
	private String sys_user_name;
	private String sys_user_email;
	private String sys_user_wechatopen;
	private String sys_user_code;
	public BaseFinancplanInfo() {
	
	}
	public BaseFinancplanInfo(String base_financplan_code,
			String base_comp_code, String base_financplan_date,
			String base_financplan_cont, String base_financplan_remindate,
			String base_financplan_sendef, String base_financplan_sendwf,
			String base_financplan_sendestate,
			String base_financplan_sendwstate, String deleteflag,
			String createid, Timestamp createtime, String updid,
			Timestamp updtime, String base_comp_name,
			String base_comp_fullname, String base_comp_ename) {
		super();
		this.base_financplan_code = base_financplan_code;
		this.base_comp_code = base_comp_code;
		this.base_financplan_date = base_financplan_date;
		this.base_financplan_cont = base_financplan_cont;
		this.base_financplan_remindate = base_financplan_remindate;
		this.base_financplan_sendef = base_financplan_sendef;
		this.base_financplan_sendwf = base_financplan_sendwf;
		this.base_financplan_sendestate = base_financplan_sendestate;
		this.base_financplan_sendwstate = base_financplan_sendwstate;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		this.base_comp_name = base_comp_name;
		this.base_comp_fullname = base_comp_fullname;
		this.base_comp_ename = base_comp_ename;
	}

	public String getBase_financplan_code() {
		return base_financplan_code;
	}
	public void setBase_financplan_code(String base_financplan_code) {
		this.base_financplan_code = base_financplan_code;
	}
	public String getBase_comp_code() {
		return base_comp_code;
	}
	public void setBase_comp_code(String base_comp_code) {
		this.base_comp_code = base_comp_code;
	}
	public String getBase_financplan_date() {
		return base_financplan_date;
	}
	public void setBase_financplan_date(String base_financplan_date) {
		this.base_financplan_date = base_financplan_date;
	}
	public String getBase_financplan_cont() {
		return base_financplan_cont;
	}
	public void setBase_financplan_cont(String base_financplan_cont) {
		this.base_financplan_cont = base_financplan_cont;
	}
	public String getBase_financplan_remindate() {
		return base_financplan_remindate;
	}
	public void setBase_financplan_remindate(String base_financplan_remindate) {
		this.base_financplan_remindate = base_financplan_remindate;
	}
	public String getBase_financplan_sendef() {
		return base_financplan_sendef;
	}
	public void setBase_financplan_sendef(String base_financplan_sendef) {
		this.base_financplan_sendef = base_financplan_sendef;
	}
	public String getBase_financplan_sendwf() {
		return base_financplan_sendwf;
	}
	public void setBase_financplan_sendwf(String base_financplan_sendwf) {
		this.base_financplan_sendwf = base_financplan_sendwf;
	}
	public String getBase_financplan_sendestate() {
		return base_financplan_sendestate;
	}
	public void setBase_financplan_sendestate(String base_financplan_sendestate) {
		this.base_financplan_sendestate = base_financplan_sendestate;
	}
	public String getBase_financplan_sendwstate() {
		return base_financplan_sendwstate;
	}
	public void setBase_financplan_sendwstate(String base_financplan_sendwstate) {
		this.base_financplan_sendwstate = base_financplan_sendwstate;
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

	public String getBase_comp_name() {
		return base_comp_name;
	}

	public void setBase_comp_name(String base_comp_name) {
		this.base_comp_name = base_comp_name;
	}

	public String getBase_comp_fullname() {
		return base_comp_fullname;
	}

	public void setBase_comp_fullname(String base_comp_fullname) {
		this.base_comp_fullname = base_comp_fullname;
	}

	public String getBase_comp_ename() {
		return base_comp_ename;
	}

	public void setBase_comp_ename(String base_comp_ename) {
		this.base_comp_ename = base_comp_ename;
	}
	public String getSys_user_name() {
	    return sys_user_name;
	}
	public void setSys_user_name(String sys_user_name) {
	    this.sys_user_name = sys_user_name;
	}
	public String getSys_user_email() {
	    return sys_user_email;
	}
	public void setSys_user_email(String sys_user_email) {
	    this.sys_user_email = sys_user_email;
	}
	public String getSys_user_wechatopen() {
	    return sys_user_wechatopen;
	}
	public void setSys_user_wechatopen(String sys_user_wechatopen) {
	    this.sys_user_wechatopen = sys_user_wechatopen;
	}
	public String getSys_user_code() {
	    return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
	    this.sys_user_code = sys_user_code;
	}

}
