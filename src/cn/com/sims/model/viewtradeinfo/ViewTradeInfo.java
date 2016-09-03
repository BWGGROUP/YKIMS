package cn.com.sims.model.viewtradeinfo;

import java.sql.Timestamp;

/**
 * @author yanglian data ：2015-10-15 机构交易详情
 */
public class ViewTradeInfo {
	private long	view_trade_id	;	//	id
	private String	base_trade_code	;	//	交易id
	private String	base_trade_date	;	//	投资日期
	private String	base_trade_stage	;	//	交易阶段code
	private String	base_trade_stagecont	;	//	交易阶段内容
	private String	base_trade_money	;	//	融资金额
	private String	base_trade_comnum	;	//	公司估值
	private String	base_trade_comnumtype	;//公司估值类型：0：获投前；1：获投后
	private String	base_comp_code	;	//	公司id
	private String	base_comp_name	;	//	公司简称
	private String	base_comp_fullname	;	//	公司全称
	private String	base_comp_ename	;	//	公司英文名称
	private String	base_comp_stage	;	//	公司阶段code
	private String	base_comp_stagecont	;	//	公司阶段内容
	private String	base_comp_img	;	//	公司图片
	private String	view_trade_baskcode	;	//	交易筐code
	private String	view_trade_baskcont	;	//	交易筐内容
	private String	view_trade_inducode	;	//	交易行业code
	private String	view_trade_inducont	;	//	交易行业内容
	private String	base_investment_code	;	//	投资机构id
	private String	base_investment_name	;	//	投资机构简称
	private String	base_investor_code	;	//	投资人id
	private String	base_investor_name	;	//	投资人姓名
	private String	base_trade_collvote	;	//	是否领投
	private String	base_trade_inmoney	;	//	金额
	private String	base_trade_ongam	;	//	是否对赌
	private String	base_trade_subpay	;	//	是否分次付款
	private String	deleteflag	;	//	删除标识
	private String	createid	;	//	创建者
	private Timestamp	createtime	;	//	创建时间
	private String	updid	;	//	更新者
	private Timestamp	updtime	;	//	更新时间


	public ViewTradeInfo() {

	}

	public ViewTradeInfo(long view_trade_id, String base_trade_code,
			String base_trade_date, String base_trade_stage,
			String base_trade_stagecont, String base_trade_money,
			String base_trade_comnum, String base_comp_code,
			String base_comp_name, String base_comp_fullname,
			String base_comp_ename, String base_comp_stage,
			String base_comp_stagecont, String base_comp_img,
			String view_trade_baskcode, String view_trade_baskcont,
			String view_trade_inducode, String view_trade_inducont,
			String base_investment_code, String base_investment_name,
			String base_investor_code, String base_investor_name,
			String base_trade_collvote, String base_trade_inmoney,
			String base_trade_ongam, String base_trade_subpay,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime) {
		this.view_trade_id = view_trade_id;
		this.base_trade_code = base_trade_code;
		this.base_trade_date = base_trade_date;
		this.base_trade_stage = base_trade_stage;
		this.base_trade_stagecont = base_trade_stagecont;
		this.base_trade_money = base_trade_money;
		this.base_trade_comnum = base_trade_comnum;
		this.base_comp_code = base_comp_code;
		this.base_comp_name = base_comp_name;
		this.base_comp_fullname = base_comp_fullname;
		this.base_comp_ename = base_comp_ename;
		this.base_comp_stage = base_comp_stage;
		this.base_comp_stagecont = base_comp_stagecont;
		this.base_comp_img = base_comp_img;
		this.view_trade_baskcode = view_trade_baskcode;
		this.view_trade_baskcont = view_trade_baskcont;
		this.view_trade_inducode = view_trade_inducode;
		this.view_trade_inducont = view_trade_inducont;
		this.base_investment_code = base_investment_code;
		this.base_investment_name = base_investment_name;
		this.base_investor_code = base_investor_code;
		this.base_investor_name = base_investor_name;
		this.base_trade_collvote = base_trade_collvote;
		this.base_trade_inmoney = base_trade_inmoney;
		this.base_trade_ongam = base_trade_ongam;
		this.base_trade_subpay = base_trade_subpay;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}



	public long getView_trade_id() {
		return view_trade_id;
	}

	public void setView_trade_id(long view_trade_id) {
		this.view_trade_id = view_trade_id;
	}

	public String getBase_trade_code() {
		return base_trade_code;
	}

	public void setBase_trade_code(String base_trade_code) {
		this.base_trade_code = base_trade_code;
	}

	public String getBase_trade_date() {
		return base_trade_date;
	}

	public void setBase_trade_date(String base_trade_date) {
		this.base_trade_date = base_trade_date;
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

	public String getBase_trade_stage() {
		return base_trade_stage;
	}

	public void setBase_trade_stage(String base_trade_stage) {
		this.base_trade_stage = base_trade_stage;
	}

	public String getBase_trade_stagecont() {
		return base_trade_stagecont;
	}

	public void setBase_trade_stagecont(String base_trade_stagecont) {
		this.base_trade_stagecont = base_trade_stagecont;
	}

	public String getBase_comp_img() {
		return base_comp_img;
	}

	public void setBase_comp_img(String base_comp_img) {
		this.base_comp_img = base_comp_img;
	}

	public String getView_trade_baskcode() {
		return view_trade_baskcode;
	}

	public void setView_trade_baskcode(String view_trade_baskcode) {
		this.view_trade_baskcode = view_trade_baskcode;
	}

	public String getView_trade_baskcont() {
		return view_trade_baskcont;
	}

	public void setView_trade_baskcont(String view_trade_baskcont) {
		this.view_trade_baskcont = view_trade_baskcont;
	}

	public String getView_trade_inducode() {
		return view_trade_inducode;
	}

	public void setView_trade_inducode(String view_trade_inducode) {
		this.view_trade_inducode = view_trade_inducode;
	}

	public String getView_trade_inducont() {
		return view_trade_inducont;
	}

	public void setView_trade_inducont(String view_trade_inducont) {
		this.view_trade_inducont = view_trade_inducont;
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

	public String getBase_trade_ongam() {
		return base_trade_ongam;
	}

	public void setBase_trade_ongam(String base_trade_ongam) {
		this.base_trade_ongam = base_trade_ongam;
	}

	public String getBase_trade_subpay() {
		return base_trade_subpay;
	}

	public void setBase_trade_subpay(String base_trade_subpay) {
		this.base_trade_subpay = base_trade_subpay;
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

	public String getBase_trade_comnumtype() {
	    return base_trade_comnumtype;
	}

	public void setBase_trade_comnumtype(String base_trade_comnumtype) {
	    this.base_trade_comnumtype = base_trade_comnumtype;
	}

}
