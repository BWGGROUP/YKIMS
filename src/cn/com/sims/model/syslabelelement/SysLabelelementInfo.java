package cn.com.sims.model.syslabelelement;

import java.sql.Timestamp;

public class SysLabelelementInfo {
	private String	sys_labelelement_code;
	private String	sys_label_code;
	private String	sys_labelelement_name;
	private String	sys_labelelement_state;
	private String	deleteflag;
	private String	createid;
	private Timestamp	createtime;
	private String	updid;
	private Timestamp	updtime;
	private int label_index;
	
	public SysLabelelementInfo(){
		
	}
	
	public SysLabelelementInfo(
			String	sys_labelelement_code	,
			String	sys_label_code	,
			String	sys_labelelement_name	,
			String	sys_labelelement_state	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime){
		this.sys_labelelement_code	=	sys_labelelement_code	;
		this.sys_label_code	=	sys_label_code	;
		this.sys_labelelement_name	=	sys_labelelement_name	;
		this.sys_labelelement_state	=	sys_labelelement_state	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;
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
	public String getSys_labelelement_name() {
		return sys_labelelement_name;
	}
	public void setSys_labelelement_name(String sys_labelelement_name) {
		this.sys_labelelement_name = sys_labelelement_name;
	}
	public String getSys_labelelement_state() {
		return sys_labelelement_state;
	}
	public void setSys_labelelement_state(String sys_labelelement_state) {
		this.sys_labelelement_state = sys_labelelement_state;
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
	public int getLabel_index() {
		return label_index;
	}

	public void setLabel_index(int label_index) {
		this.label_index = label_index;
	}

}
