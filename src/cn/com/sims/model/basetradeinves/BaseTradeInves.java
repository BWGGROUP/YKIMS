package cn.com.sims.model.basetradeinves;

import java.sql.Timestamp;

/**
 * 
 * @author RQQ
 * @date 2015-10-27
 */
public class BaseTradeInves {
    //交易id
	private	String	base_trade_code	;
	//投资机构id
	private	String	base_investment_code	;
	//投资人id
	private	String	base_investor_code	;
	//是否领投
	private	String	base_trade_collvote	;
	//金额
	private	String	base_trade_inmoney	;
	//是否对赌
	private	String	base_trade_ongam	;
	//是否分次付款
	private	String	base_trade_subpay	;
	//创建来源
	private String base_trade_stem;
	//当前状态
	private String base_trade_estate;
	//	删除标识,0：正常；1：删除
	private	String	deleteflag	;
	//	创建者
	private	String	createid	;
	//	创建时间：例2014-07-29 14:40:34
	private	Timestamp	createtime	;
	//	更新者
	private	String	updid	;	
	//	更新时间：例2014-07-29 14:40:34
	private	Timestamp	updtime	;	
	
	public BaseTradeInves(){}
	
	
	public String getBase_trade_code() {
	    return base_trade_code;
	}


	public void setBase_trade_code(String base_trade_code) {
	    this.base_trade_code = base_trade_code;
	}


	public String getBase_investment_code() {
	    return base_investment_code;
	}


	public void setBase_investment_code(String base_investment_code) {
	    this.base_investment_code = base_investment_code;
	}


	public String getBase_investor_code() {
	    return base_investor_code;
	}


	public void setBase_investor_code(String base_investor_code) {
	    this.base_investor_code = base_investor_code;
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

}
