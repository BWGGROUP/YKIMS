package cn.com.sims.model.basetradeinfo;

import java.sql.Timestamp;

/**
 * 
 * @author duwenjie
 * @date 2015-10-26
 */
public class BaseTradeInfo {
	private	String	base_trade_code	;	//	交易id，系统生成，以T开头
	private	String	base_comp_code	;	//	公司id
	private	String	base_trade_date	;	//	投资日期：yyyy-mm-dd
	private	String	base_trade_stage	;	//	阶段
	private	String	base_trade_money	;	//	融资金额
	private	String	base_trade_comnum	;	//	公司估值
	private	String	base_trade_stem	;	//	创建来源：1：IT桔子创建；2：易凯创建
	private	String	base_trade_estate	;	//	当前状态：1：IT桔子创建；2：易凯创建；3：易凯修改；4：IT桔子修改；
	private	String	Tmp_Inves_Record_ID	;	//	对应IT桔子id
	private	String	deleteflag	;	//	0：正常；1：删除
	private	String	createid	;	//	创建者
	private	Timestamp	createtime	;	//	创建时间：例2014-07-29 14:40:34
	private	String	updid	;	//	更新者
	private	Timestamp	updtime	;	//	更新时间：例2014-07-29 14:40:34
	private	long	base_datalock_viewtype	;	//	排它锁（版本号）
	private	String	base_datalock_pltype	;	//	PL锁状态：0：等待处理；1：已处理；2：处理失败
	private String  base_trade_comnumtype;	//公司估值类型
	
	
	public BaseTradeInfo(){}
	
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
	public String getBase_trade_stem() {
		return base_trade_stem;
	}
	public void setBase_trade_stem(String base_trade_stem) {
		this.base_trade_stem = base_trade_stem;
	}
	public String getBase_trade_estate() {
		return base_trade_estate;
	}
	public void setBase_trade_estate(String base_trade_estate) {
		this.base_trade_estate = base_trade_estate;
	}
	public String getTmp_Inves_Record_ID() {
		return Tmp_Inves_Record_ID;
	}
	public void setTmp_Inves_Record_ID(String tmp_Inves_Record_ID) {
		Tmp_Inves_Record_ID = tmp_Inves_Record_ID;
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

	public String getBase_trade_comnumtype() {
		return base_trade_comnumtype;
	}

	public void setBase_trade_comnumtype(String base_trade_comnumtype) {
		this.base_trade_comnumtype = base_trade_comnumtype;
	}

}
