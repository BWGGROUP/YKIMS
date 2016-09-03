package cn.com.sims.model.syswadjuri;

import java.sql.Timestamp;

/**
 * 系统筐权限信息
 * @author zzg
 *
 */
public class SysWadJuri {
	private String	sys_wad_code	;
	private String	sys_juri_code	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	public SysWadJuri(String sys_wad_code, String sys_juri_code,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime) {
		super();
		this.sys_wad_code = sys_wad_code;
		this.sys_juri_code = sys_juri_code;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}
	public SysWadJuri() {
		super();
	}
	public String getSys_wad_code() {
		return sys_wad_code;
	}
	public void setSys_wad_code(String sys_wad_code) {
		this.sys_wad_code = sys_wad_code;
	}
	public String getSys_juri_code() {
		return sys_juri_code;
	}
	public void setSys_juri_code(String sys_juri_code) {
		this.sys_juri_code = sys_juri_code;
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
