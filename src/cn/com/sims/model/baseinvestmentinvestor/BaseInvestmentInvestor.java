package cn.com.sims.model.baseinvestmentinvestor;

import java.sql.Timestamp;

public class BaseInvestmentInvestor {
	private String	base_investor_code	;
	private String	base_investment_code	;
	private String	base_investor_state	;
	private String	base_investor_posiname	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	
	//投资人姓名
	private String	base_investor_name	;
	//投资机构简称
	private String	base_investment_name	;
	
	public BaseInvestmentInvestor() {
	}
	
	public BaseInvestmentInvestor(String	base_investor_code	,
			String	base_investment_code	,
			String	base_investor_state	,
			String base_investor_posiname,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime
			) {
		this.base_investor_code	=	base_investor_code	;
		this.base_investment_code	=	base_investment_code	;
		this.base_investor_state	=	base_investor_state	;
		this.base_investor_posiname	=	base_investor_posiname	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;

	}
	
	
	public String getBase_investor_code() {
		return base_investor_code;
	}
	public void setBase_investor_code(String base_investor_code) {
		this.base_investor_code = base_investor_code;
	}
	public String getBase_investment_code() {
		return base_investment_code;
	}
	public void setBase_investment_code(String base_investment_code) {
		this.base_investment_code = base_investment_code;
	}
	public String getBase_investor_state() {
		return base_investor_state;
	}
	public void setBase_investor_state(String base_investor_state) {
		this.base_investor_state = base_investor_state;
	}

	public String getBase_investor_posiname() {
		return base_investor_posiname;
	}

	public void setBase_investor_posiname(String base_investor_posiname) {
		this.base_investor_posiname = base_investor_posiname;
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

	public String getBase_investor_name() {
	    return base_investor_name;
	}

	public void setBase_investor_name(String base_investor_name) {
	    this.base_investor_name = base_investor_name;
	}

	public String getBase_investment_name() {
	    return base_investment_name;
	}

	public void setBase_investment_name(String base_investment_name) {
	    this.base_investment_name = base_investment_name;
	}

}
