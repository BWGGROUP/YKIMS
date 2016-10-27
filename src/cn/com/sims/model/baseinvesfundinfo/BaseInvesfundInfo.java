package cn.com.sims.model.baseinvesfundinfo;

import java.sql.Timestamp;

/**
 * 投资机构基金信息
 * @author shbs-tp004 duwenjie
 *
 */
public class BaseInvesfundInfo {
	private String	base_invesfund_code	;
	private String	base_investment_code	;
	private String	base_invesfund_name	;
	private String	base_invesfund_currency	;
	private String	base_invesfund_currencyname	;
	private String	base_invesfund_scalecode	;
	private String	base_invesfund_scale	;
	private String	base_investor_code	;
	private String	base_investor_name	;
	private String	base_invesfund_begintime	;
	private String	base_invesfund_endtime	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp createtime	;
	private String	updid	;
	private Timestamp updtime	;
	private String base_invesfund_state;
	
	public BaseInvesfundInfo(){
		
	}
	
	public BaseInvesfundInfo(String	base_invesfund_code	,
			String	base_investment_code	,
			String	base_invesfund_name	,
			String	base_invesfund_currency	,
			String	base_invesfund_currencyname	,
			String	base_invesfund_scalecode	,
			String	base_invesfund_scale	,
			String	base_investor_code	,
			String	base_investor_name	,
			String	base_invesfund_begintime	,
			String	base_invesfund_endtime	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime,
			String base_invesfund_state){
		this.base_invesfund_code	=	base_invesfund_code	;
		this.base_investment_code	=	base_investment_code;
		this.base_invesfund_name	=	base_invesfund_name	;
		this.base_invesfund_currency	=	base_invesfund_currency	;
		this.base_invesfund_currencyname	=	base_invesfund_currencyname	;
		this.base_invesfund_scalecode	=	base_invesfund_scalecode;
		this.base_invesfund_scale	=	base_invesfund_scale;
		this.base_investor_code	=	base_investor_code;
		this.base_investor_name	=	base_investor_name;
		this.base_invesfund_begintime	=	base_invesfund_begintime;
		this.base_invesfund_endtime	=	base_invesfund_endtime;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;
		this.base_invesfund_state = base_invesfund_state;
		
		
	}
	
	public String getBase_invesfund_code() {
		return base_invesfund_code;
	}
	public void setBase_invesfund_code(String base_invesfund_code) {
		this.base_invesfund_code = base_invesfund_code;
	}
	public String getBase_investment_code() {
		return base_investment_code;
	}
	public void setBase_investment_code(String base_investment_code) {
		this.base_investment_code = base_investment_code;
	}
	public String getBase_invesfund_name() {
		return base_invesfund_name;
	}
	public void setBase_invesfund_name(String base_invesfund_name) {
		this.base_invesfund_name = base_invesfund_name;
	}
	public String getBase_invesfund_currency() {
		return base_invesfund_currency;
	}
	public void setBase_invesfund_currency(String base_invesfund_currency) {
		this.base_invesfund_currency = base_invesfund_currency;
	}
	public String getBase_invesfund_currencyname() {
		return base_invesfund_currencyname;
	}
	public void setBase_invesfund_currencyname(String base_invesfund_currencyname) {
		this.base_invesfund_currencyname = base_invesfund_currencyname;
	}
	public String getBase_invesfund_scalecode() {
		return base_invesfund_scalecode;
	}
	public void setBase_invesfund_scalecode(String base_invesfund_scalecode) {
		this.base_invesfund_scalecode = base_invesfund_scalecode;
	}
	public String getBase_invesfund_scale() {
		return base_invesfund_scale;
	}
	public void setBase_invesfund_scale(String base_invesfund_scale) {
		this.base_invesfund_scale = base_invesfund_scale;
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
	public String getBase_invesfund_begintime() {
		return base_invesfund_begintime;
	}
	public void setBase_invesfund_begintime(String base_invesfund_begintime) {
		this.base_invesfund_begintime = base_invesfund_begintime;
	}
	public String getBase_invesfund_endtime() {
		return base_invesfund_endtime;
	}
	public void setBase_invesfund_endtime(String base_invesfund_endtime) {
		this.base_invesfund_endtime = base_invesfund_endtime;
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
	public String getBase_invesfund_state() {
		return base_invesfund_state;
	}

	public void setBase_invesfund_state(String base_invesfund_state) {
		this.base_invesfund_state = base_invesfund_state;
	}


}