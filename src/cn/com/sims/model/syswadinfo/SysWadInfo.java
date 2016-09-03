package cn.com.sims.model.syswadinfo;

import java.sql.Timestamp;

/**
 * 系统筐信息
 * @author zgg
 *@date 2015-10-30
 */
public class SysWadInfo {
	private String	sys_wad_code	;
	private String	sys_wad_name	;
	private String	sys_wad_type	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	public String getSys_wad_code() {
		return sys_wad_code;
	}
	public void setSys_wad_code(String sys_wad_code) {
		this.sys_wad_code = sys_wad_code;
	}
	public String getSys_wad_name() {
		return sys_wad_name;
	}
	public void setSys_wad_name(String sys_wad_name) {
		this.sys_wad_name = sys_wad_name;
	}
	public String getSys_wad_type() {
		return sys_wad_type;
	}
	public void setSys_wad_type(String sys_wad_type) {
		this.sys_wad_type = sys_wad_type;
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
	public SysWadInfo(String sys_wad_code, String sys_wad_name,
			String sys_wad_type, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime) {
		super();
		this.sys_wad_code = sys_wad_code;
		this.sys_wad_name = sys_wad_name;
		this.sys_wad_type = sys_wad_type;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}
	public SysWadInfo() {
		super();
	}

}
