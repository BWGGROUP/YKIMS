package cn.com.sims.model.viewtradeser;

import java.sql.Timestamp;

/**
 * 
 * @author zzg
 *@date 2015-10-20
 *交易详情(业务数据)搜索使用
 */
public class ViewTradSer {
	private Long	view_trade_serid	;
	private String	base_trade_code	;
	private String	base_trade_date	;
	private String	base_trade_stage	;
	private String	base_trade_stagecont	;
	private String	base_trade_money	;
	private String	base_trade_comnum	;
	private String	base_comp_code	;
	private String	base_comp_name	;
	private String	base_comp_fullname	;
	private String	base_comp_ename	;
	private String	base_comp_stage	;
	private String	base_comp_stagecont	;
	private String	base_comp_img	;
	private String	view_trade_baskcode	;
	private String	view_trade_baskcont	;
	private String	view_trade_inducode	;
	private String	view_trade_inducont	;
	private String	view_investment_code	;
	private String	view_investment_cont	;
	private String	view_investment_name	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	private String base_trade_comnumtype;
	
	public ViewTradSer(Long view_trade_serid, String base_trade_code,
			String base_trade_date, String base_trade_stage,
			String base_trade_stagecont, String base_trade_money,
			String base_trade_comnum, String base_comp_code,
			String base_comp_name, String base_comp_fullname,
			String base_comp_ename, String base_comp_stage,
			String base_comp_stagecont, String base_comp_img,
			String view_trade_baskcode, String view_trade_baskcont,
			String view_trade_inducode, String view_trade_inducont,
			String view_investment_code, String view_investment_cont,
			String view_investment_name, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime,String base_trade_comnumtype) {
		super();
		this.view_trade_serid = view_trade_serid;
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
		this.view_investment_code = view_investment_code;
		this.view_investment_cont = view_investment_cont;
		this.view_investment_name = view_investment_name;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		this.base_trade_comnumtype=base_trade_comnumtype;
	}
	public ViewTradSer() {
		super();
	}
	public Long getView_trade_serid() {
		return view_trade_serid;
	}
	public void setView_trade_serid(Long view_trade_serid) {
		this.view_trade_serid = view_trade_serid;
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
	public String getView_investment_name() {
		return view_investment_name;
	}
	public void setView_investment_name(String view_investment_name) {
		this.view_investment_name = view_investment_name;
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
