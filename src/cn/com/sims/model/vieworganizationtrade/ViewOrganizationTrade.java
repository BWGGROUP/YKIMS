package cn.com.sims.model.vieworganizationtrade;

import java.sql.Timestamp;

public class ViewOrganizationTrade {
	private String	view_comp_id	;
	private String	base_trade_code	;
	private String	base_comp_code	;
	private String	base_comp_name	;
	private String	base_comp_fullname	;
	private String	base_comp_ename	;
	private String	base_comp_stage	;
	private String	base_comp_stagecont	;
	private String	base_comp_img	;
	private String	view_comp_baskcode	;
	private String	view_comp_baskcont	;
	private String	view_comp_inducode	;
	private String	view_comp_inducont	;
	private String	base_trade_money	;
	private String	base_trade_comnum	;
	private String	base_comp_ongam	;
	private String	base_comp_subpay	;
	private String	base_investment_code	;
	private String	base_investment_name	;
	private String	base_investor_code	;
	private String	base_investor_name	;
	private String	base_trade_collvote	;
	private String	base_trade_inmoney	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	
	public ViewOrganizationTrade(){
		
	}
	
	public ViewOrganizationTrade(String	view_comp_id	,
			String	base_trade_code	,
			String	base_comp_code	,
			String	base_comp_name	,
			String	base_comp_fullname	,
			String	base_comp_ename	,
			String	base_comp_stage	,
			String	base_comp_stagecont	,
			String	base_comp_img	,
			String	view_comp_baskcode	,
			String	view_comp_baskcont	,
			String	view_comp_inducode	,
			String	view_comp_inducont	,
			String	base_trade_money	,
			String	base_trade_comnum	,
			String	base_comp_ongam	,
			String	base_comp_subpay	,
			String	base_investment_code	,
			String	base_investment_name	,
			String	base_investor_code	,
			String	base_investor_name	,
			String	base_trade_collvote	,
			String	base_trade_inmoney	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime){
		this.view_comp_id	 =	view_comp_id	;
		this.base_trade_code	 =	base_trade_code	;
		this.base_comp_code	 =	base_comp_code	;
		this.base_comp_name	 =	base_comp_name	;
		this.base_comp_fullname	 =	base_comp_fullname	;
		this.base_comp_ename	 =	base_comp_ename	;
		this.base_comp_stage	 =	base_comp_stage	;
		this.base_comp_stagecont	 =	base_comp_stagecont	;
		this.base_comp_img	 =	base_comp_img	;
		this.view_comp_baskcode	 =	view_comp_baskcode	;
		this.view_comp_baskcont	 =	view_comp_baskcont	;
		this.view_comp_inducode	 =	view_comp_inducode	;
		this.view_comp_inducont	 =	view_comp_inducont	;
		this.base_trade_money	 =	base_trade_money	;
		this.base_trade_comnum	 =	base_trade_comnum	;
		this.base_comp_ongam	 =	base_comp_ongam	;
		this.base_comp_subpay	 =	base_comp_subpay	;
		this.base_investment_code	 =	base_investment_code	;
		this.base_investment_name	 =	base_investment_name	;
		this.base_investor_code	 =	base_investor_code	;
		this.base_investor_name	 =	base_investor_name	;
		this.base_trade_collvote	 =	base_trade_collvote	;
		this.base_trade_inmoney	 =	base_trade_inmoney	;
		this.deleteflag	 =	deleteflag	;
		this.createid	 =	createid	;
		this.createtime	 =	createtime	;
		this.updid	 =	updid	;
		this.updtime	 =	updtime	;

		
	}
	
	public String getView_comp_id() {
		return view_comp_id;
	}
	public void setView_comp_id(String view_comp_id) {
		this.view_comp_id = view_comp_id;
	}
	public String getBase_trade_code() {
		return base_trade_code;
	}
	public void setBase_trade_code(String base_trade_code) {
		this.base_trade_code = base_trade_code;
	}
	public String getBase_comp_code() {
		return base_comp_code;
	}
	public void setBase_comp_code(String base_comp_code) {
		this.base_comp_code = base_comp_code;
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
	public String getBase_comp_stage() {
		return base_comp_stage;
	}
	public void setBase_comp_stage(String base_comp_stage) {
		this.base_comp_stage = base_comp_stage;
	}
	public String getBase_comp_stagecont() {
		return base_comp_stagecont;
	}
	public void setBase_comp_stagecont(String base_comp_stagecont) {
		this.base_comp_stagecont = base_comp_stagecont;
	}
	public String getBase_comp_img() {
		return base_comp_img;
	}
	public void setBase_comp_img(String base_comp_img) {
		this.base_comp_img = base_comp_img;
	}
	public String getView_comp_baskcode() {
		return view_comp_baskcode;
	}
	public void setView_comp_baskcode(String view_comp_baskcode) {
		this.view_comp_baskcode = view_comp_baskcode;
	}
	public String getView_comp_baskcont() {
		return view_comp_baskcont;
	}
	public void setView_comp_baskcont(String view_comp_baskcont) {
		this.view_comp_baskcont = view_comp_baskcont;
	}
	public String getView_comp_inducode() {
		return view_comp_inducode;
	}
	public void setView_comp_inducode(String view_comp_inducode) {
		this.view_comp_inducode = view_comp_inducode;
	}
	public String getView_comp_inducont() {
		return view_comp_inducont;
	}
	public void setView_comp_inducont(String view_comp_inducont) {
		this.view_comp_inducont = view_comp_inducont;
	}
	public String getBase_trade_money() {
		return base_trade_money;
	}
	public void setBase_trade_money(String base_trade_money) {
		this.base_trade_money = base_trade_money;
	}
	public String getBase_trade_comnum() {
		return base_trade_comnum;
	}
	public void setBase_trade_comnum(String base_trade_comnum) {
		this.base_trade_comnum = base_trade_comnum;
	}
	public String getBase_comp_ongam() {
		return base_comp_ongam;
	}
	public void setBase_comp_ongam(String base_comp_ongam) {
		this.base_comp_ongam = base_comp_ongam;
	}
	public String getBase_comp_subpay() {
		return base_comp_subpay;
	}
	public void setBase_comp_subpay(String base_comp_subpay) {
		this.base_comp_subpay = base_comp_subpay;
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
	public String getBase_trade_collvote() {
		return base_trade_collvote;
	}
	public void setBase_trade_collvote(String base_trade_collvote) {
		this.base_trade_collvote = base_trade_collvote;
	}
	public String getBase_trade_inmoney() {
		return base_trade_inmoney;
	}
	public void setBase_trade_inmoney(String base_trade_inmoney) {
		this.base_trade_inmoney = base_trade_inmoney;
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
