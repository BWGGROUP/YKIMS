package cn.com.sims.model.basetradelabelinfo;

import java.sql.Timestamp;

/**
 * 
 * @author RQQ
 * @date 2015-10-27
 */
public class BaseTradelabelInfo {
    	//交易id
	private	String	base_trade_code;
	//标签元素code
	private	String	sys_labelelement_code;
	//标签code
	private	String	sys_label_code;
	//	删除标识,0：正常；1：删除
	private	String	deleteflag;
	//	创建者
	private	String	createid;
	//	创建时间：例2014-07-29 14:40:34
	private	Timestamp	createtime;
	//	更新者
	private	String	updid;	
	//	更新时间：例2014-07-29 14:40:34
	private	Timestamp	updtime;	
	
	public BaseTradelabelInfo(){}
	
	public BaseTradelabelInfo(
			String	base_trade_code	,
			String	sys_labelelement_code	,
			String	sys_label_code	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime){
		this.base_trade_code	=	base_trade_code;
		this.sys_labelelement_code	=	sys_labelelement_code;
		this.sys_label_code	=	sys_label_code;
		this.deleteflag	=	deleteflag;
		this.createid	=	createid;
		this.createtime	=	createtime;
		this.updid	=	updid;
		this.updtime	=	updtime;

	}
	
	public String getBase_trade_code() {
	    return base_trade_code;
	}


	public void setBase_trade_code(String base_trade_code) {
	    this.base_trade_code = base_trade_code;
	}

	public String getSys_labelelement_code() {
	    return sys_labelelement_code;
	}


	public void setSys_labelelement_code(String sys_labelelement_code) {
	    this.sys_labelelement_code = sys_labelelement_code;
	}


	public String getSys_label_code() {
	    return sys_label_code;
	}


	public void setSys_label_code(String sys_label_code) {
	    this.sys_label_code = sys_label_code;
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
