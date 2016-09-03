package cn.com.sims.model.basetradenoteinfo;

import java.sql.Timestamp;

/**
 * 交易note备注
 * @author duwenjie
 * 
 */
public class BaseTradenoteInfo {
	private	String	base_tradenote_code	;	//	交易noteid，系统生成，以TN开头
	private	String	base_trade_code	;	//	交易id
	private	String	base_tradenote_content	;	//	内容：画面显示
	private	String	sys_user_name	;	//	创建者用户姓名：画面显示
	private	String	deleteflag	;	//	0：正常；1：删除
	private	String	createid	;	//	创建者
	private	Timestamp	createtime	;	//	创建时间：例2014-07-29 14:40:34　　画面显示
	private	String	updid	;	//	更新者
	private	Timestamp	updtime	;	//	更新时间：例2014-07-29 14:40:34
	
	public  BaseTradenoteInfo() {
		
	}
	
	public String getBase_tradenote_code() {
		return base_tradenote_code;
	}
	public void setBase_tradenote_code(String base_tradenote_code) {
		this.base_tradenote_code = base_tradenote_code;
	}
	public String getBase_trade_code() {
		return base_trade_code;
	}
	public void setBase_trade_code(String base_trade_code) {
		this.base_trade_code = base_trade_code;
	}
	public String getBase_tradenote_content() {
		return base_tradenote_content;
	}
	public void setBase_tradenote_content(String base_tradenote_content) {
		this.base_tradenote_content = base_tradenote_content;
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
