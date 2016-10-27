package cn.com.sims.model.sysuserwad;

import java.sql.Timestamp;
import java.util.List;

import cn.com.sims.model.syswadjuri.SysWadJuri;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
public class SysUserWad {
	private String	sys_wad_code	;
	private String	sys_user_code	;
	private String	deleteflag	;
	private String	createid	;
	private String	sys_wad_name	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	//筐对应的权限
	private List<SysWadJuri> SysWadJuri;
	public String getSys_wad_code() {
		return sys_wad_code;
	}
	public void setSys_wad_code(String sys_wad_code) {
		this.sys_wad_code = sys_wad_code;
	}
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
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
	public List<SysWadJuri> getSysWadJuri() {
		return SysWadJuri;
	}
	public void setSysWadJuri(List<SysWadJuri> sysWadJuri) {
		SysWadJuri = sysWadJuri;
	}

	public String getSys_wad_name() {
		return sys_wad_name;
	}
	public void setSys_wad_name(String sys_wad_name) {
		this.sys_wad_name = sys_wad_name;
	}
	public SysUserWad(String sys_wad_code, String sys_user_code,
			String deleteflag, String createid, String sys_wad_name,
			Timestamp createtime, String updid, Timestamp updtime,
			List<cn.com.sims.model.syswadjuri.SysWadJuri> sysWadJuri) {
		super();
		this.sys_wad_code = sys_wad_code;
		this.sys_user_code = sys_user_code;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.sys_wad_name = sys_wad_name;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		SysWadJuri = sysWadJuri;
	}
	public SysUserWad() {
		super();
	}

}
