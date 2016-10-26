package cn.com.sims.model.baseupdloginfo;

import java.sql.Timestamp;

public class BaseUpdlogInfo {
	private	String	id;
	private	String	base_updlog_type;//操作模块类型code
	private	String	base_updlog_typename;//操作模块类型name
	private	String	base_ele_code;//操作模块的业务对应code
	private	String	base_ele_name;//操作模块的业务对应名称
	private String  base_updlog_opertype;//操作者类型
	private	String	sys_user_code;//操作者
	private	String	sys_user_name;//操作者名称
	private	String	base_updlog_opertypecode;//操作类型code
	private	String	base_updlog_opertypename;//操作显示内容
	private	String	base_updlog_opercontcode;//操作内容code
	private	String	base_updlog_opercont;//操作内容
	private	String	base_updlog_oridata;//原数据
	private	String	base_updlog_newdata;//更改后数据
	private	String	operaflag;//操作设备
	private	String	deleteflag;//删除标识
	private	String	createid;//创建者
	private	Timestamp	createtime;//创建时间
	private	String	updid;//更新者
	private	Timestamp	updtime;//更新时间
	
	public BaseUpdlogInfo(){
		
	}
	
	public BaseUpdlogInfo(String	id	,
			String	base_updlog_type	,
			String	base_updlog_typename	,
			String	base_ele_code	,
			String	base_ele_name	,
			String  base_updlog_opertype,
			String	sys_user_code	,
			String	sys_user_name	,
			String	base_updlog_opertypecode	,
			String	base_updlog_opertypename	,
			String	base_updlog_opercontcode	,
			String	base_updlog_opercont	,
			String	base_updlog_oridata	,
			String	base_updlog_newdata	,
			String	operaflag	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime){
		this.id	=	id;
		this.base_updlog_type	=	base_updlog_type;
		this.base_updlog_typename	=	base_updlog_typename;
		this.base_ele_code	=	base_ele_code;
		this.base_ele_name	=	base_ele_name;
		this.base_updlog_opertype = base_updlog_opertype;
		this.sys_user_code	=	sys_user_code;
		this.sys_user_name	=	sys_user_name;
		this.base_updlog_opertypecode	=	base_updlog_opertypecode;
		this.base_updlog_opertypename	=	base_updlog_opertypename;
		this.base_updlog_opercontcode	=	base_updlog_opercontcode;
		this.base_updlog_opercont	=	base_updlog_opercont;
		this.base_updlog_oridata	=	base_updlog_oridata;
		this.base_updlog_newdata	=	base_updlog_newdata;
		this.operaflag	=	operaflag;
		this.deleteflag	=	deleteflag;
		this.createid	=	createid;
		this.createtime	=	createtime;
		this.updid	=	updid;
		this.updtime	=	updtime;
	}
	
	
	public String getBase_updlog_opertype() {
		return base_updlog_opertype;
	}

	public void setBase_updlog_opertype(String base_updlog_opertype) {
		this.base_updlog_opertype = base_updlog_opertype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBase_updlog_type() {
		return base_updlog_type;
	}
	public void setBase_updlog_type(String base_updlog_type) {
		this.base_updlog_type = base_updlog_type;
	}
	public String getBase_updlog_typename() {
		return base_updlog_typename;
	}
	public void setBase_updlog_typename(String base_updlog_typename) {
		this.base_updlog_typename = base_updlog_typename;
	}
	public String getBase_ele_code() {
		return base_ele_code;
	}
	public void setBase_ele_code(String base_ele_code) {
		this.base_ele_code = base_ele_code;
	}
	public String getBase_ele_name() {
		return base_ele_name;
	}
	public void setBase_ele_name(String base_ele_name) {
		this.base_ele_name = base_ele_name;
	}
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
	}
	public String getSys_user_name() {
		return sys_user_name;
	}
	public void setSys_user_name(String sys_user_name) {
		this.sys_user_name = sys_user_name;
	}
	public String getBase_updlog_opertypecode() {
		return base_updlog_opertypecode;
	}
	public void setBase_updlog_opertypecode(String base_updlog_opertypecode) {
		this.base_updlog_opertypecode = base_updlog_opertypecode;
	}
	public String getBase_updlog_opertypename() {
		return base_updlog_opertypename;
	}
	public void setBase_updlog_opertypename(String base_updlog_opertypename) {
		this.base_updlog_opertypename = base_updlog_opertypename;
	}
	public String getBase_updlog_opercontcode() {
		return base_updlog_opercontcode;
	}
	public void setBase_updlog_opercontcode(String base_updlog_opercontcode) {
		this.base_updlog_opercontcode = base_updlog_opercontcode;
	}
	public String getBase_updlog_opercont() {
		return base_updlog_opercont;
	}
	public void setBase_updlog_opercont(String base_updlog_opercont) {
		this.base_updlog_opercont = base_updlog_opercont;
	}
	public String getBase_updlog_oridata() {
		return base_updlog_oridata;
	}
	public void setBase_updlog_oridata(String base_updlog_oridata) {
		this.base_updlog_oridata = base_updlog_oridata;
	}
	public String getBase_updlog_newdata() {
		return base_updlog_newdata;
	}
	public void setBase_updlog_newdata(String base_updlog_newdata) {
		this.base_updlog_newdata = base_updlog_newdata;
	}
	public String getOperaflag() {
		return operaflag;
	}
	public void setOperaflag(String operaflag) {
		this.operaflag = operaflag;
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
