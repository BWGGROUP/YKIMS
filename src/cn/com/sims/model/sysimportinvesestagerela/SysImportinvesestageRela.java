package cn.com.sims.model.sysimportinvesestagerela;

import java.sql.Timestamp;

public class SysImportinvesestageRela {
	private String	id;
	private String	sys_imporbefore_cont;
	private String	sys_imporafter_cont;
	private String	deleteflag;
	private String	createid;
	private Timestamp	createtime;
	private String	updid;
	private Timestamp	updtime;
	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public String getSys_imporbefore_cont() {
	    return sys_imporbefore_cont;
	}
	public void setSys_imporbefore_cont(String sys_imporbefore_cont) {
	    this.sys_imporbefore_cont = sys_imporbefore_cont;
	}
	public String getSys_imporafter_cont() {
	    return sys_imporafter_cont;
	}
	public void setSys_imporafter_cont(String sys_imporafter_cont) {
	    this.sys_imporafter_cont = sys_imporafter_cont;
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
