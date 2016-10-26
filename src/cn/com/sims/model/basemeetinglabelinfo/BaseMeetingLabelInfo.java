package cn.com.sims.model.basemeetinglabelinfo;

import java.sql.Timestamp;

public class BaseMeetingLabelInfo {
	private	String	base_meeting_code	;	//	交易id
	private	String	sys_labelelement_code	;	//	标签元素code
	private	String	sys_label_code	;	//	标签code
	private	String	deleteflag	;	//	0：正常；1：删除
	private	String	createid	;	//	创建者
	private	Timestamp	createtime	;	//	创建时间：例2014-07-29 14:40:34
	private	String	updid	;	//	更新者
	private	Timestamp	updtime	;	//	更新时间：例2014-07-29 14:40:34
	
	public String getBase_meeting_code() {
		return base_meeting_code;
	}
	public String getSys_labelelement_code() {
		return sys_labelelement_code;
	}
	public String getSys_label_code() {
		return sys_label_code;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public String getCreateid() {
		return createid;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public String getUpdid() {
		return updid;
	}
	public Timestamp getUpdtime() {
		return updtime;
	}
	public void setBase_meeting_code(String base_meeting_code) {
		this.base_meeting_code = base_meeting_code;
	}
	public void setSys_labelelement_code(String sys_labelelement_code) {
		this.sys_labelelement_code = sys_labelelement_code;
	}
	public void setSys_label_code(String sys_label_code) {
		this.sys_label_code = sys_label_code;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public void setUpdid(String updid) {
		this.updid = updid;
	}
	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}

}
