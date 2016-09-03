package cn.com.sims.model.sysuserinfo;

import java.sql.Timestamp;
import java.util.List;

import cn.com.sims.model.sysuserwad.SysUserWad;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
public class SysUserInfo {

	private String	sys_user_code	;
	private String	sys_user_paw	;
	private String	sys_user_name	;
	private String	sys_user_ename	;
	private String	sys_user_email	;
	private String	sys_user_wechatnum	;
	private String	sys_user_wechatopen	;
	private String	sys_user_wechatflag	;
	private String	sys_user_addmnum	;
	private String	sys_user_cmnum	;
	private String	sys_user_addreconum	;
	private String	sys_user_updreconum	;
	private String	sys_user_phone	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	
	//用户筐信息
	private List<SysUserWad> sysUserWadslisList;
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
	}
	public String getSys_user_paw() {
		return sys_user_paw;
	}
	public void setSys_user_paw(String sys_user_paw) {
		this.sys_user_paw = sys_user_paw;
	}
	public String getSys_user_name() {
		return sys_user_name;
	}
	public void setSys_user_name(String sys_user_name) {
		this.sys_user_name = sys_user_name;
	}
	public String getSys_user_ename() {
		return sys_user_ename;
	}
	public void setSys_user_ename(String sys_user_ename) {
		this.sys_user_ename = sys_user_ename;
	}
	public String getSys_user_email() {
		return sys_user_email;
	}
	public void setSys_user_email(String sys_user_email) {
		this.sys_user_email = sys_user_email;
	}
	public String getSys_user_wechatnum() {
		return sys_user_wechatnum;
	}
	public void setSys_user_wechatnum(String sys_user_wechatnum) {
		this.sys_user_wechatnum = sys_user_wechatnum;
	}
	public String getSys_user_wechatopen() {
		return sys_user_wechatopen;
	}
	public void setSys_user_wechatopen(String sys_user_wechatopen) {
		this.sys_user_wechatopen = sys_user_wechatopen;
	}
	public String getSys_user_wechatflag() {
		return sys_user_wechatflag;
	}
	public void setSys_user_wechatflag(String sys_user_wechatflag) {
		this.sys_user_wechatflag = sys_user_wechatflag;
	}
	public String getSys_user_addmnum() {
		return sys_user_addmnum;
	}
	public void setSys_user_addmnum(String sys_user_addmnum) {
		this.sys_user_addmnum = sys_user_addmnum;
	}
	public String getSys_user_cmnum() {
		return sys_user_cmnum;
	}
	public void setSys_user_cmnum(String sys_user_cmnum) {
		this.sys_user_cmnum = sys_user_cmnum;
	}
	public String getSys_user_addreconum() {
		return sys_user_addreconum;
	}
	public void setSys_user_addreconum(String sys_user_addreconum) {
		this.sys_user_addreconum = sys_user_addreconum;
	}
	public String getSys_user_updreconum() {
		return sys_user_updreconum;
	}
	public void setSys_user_updreconum(String sys_user_updreconum) {
		this.sys_user_updreconum = sys_user_updreconum;
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

	public String getSys_user_phone() {
		return sys_user_phone;
	}
	public void setSys_user_phone(String sys_user_phone) {
		this.sys_user_phone = sys_user_phone;
	}
	public SysUserInfo() {
		super();
	}
	public List<SysUserWad> getSysUserWadslisList() {
		return sysUserWadslisList;
	}
	public void setSysUserWadslisList(List<SysUserWad> sysUserWadslisList) {
		this.sysUserWadslisList = sysUserWadslisList;
	}
	public SysUserInfo(String sys_user_code, String sys_user_paw,
			String sys_user_name, String sys_user_ename, String sys_user_email,
			String sys_user_wechatnum, String sys_user_wechatopen,
			String sys_user_wechatflag, String sys_user_addmnum,
			String sys_user_cmnum, String sys_user_addreconum,
			String sys_user_updreconum, String sys_user_phone,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime, List<SysUserWad> sysUserWadslisList) {
		super();
		this.sys_user_code = sys_user_code;
		this.sys_user_paw = sys_user_paw;
		this.sys_user_name = sys_user_name;
		this.sys_user_ename = sys_user_ename;
		this.sys_user_email = sys_user_email;
		this.sys_user_wechatnum = sys_user_wechatnum;
		this.sys_user_wechatopen = sys_user_wechatopen;
		this.sys_user_wechatflag = sys_user_wechatflag;
		this.sys_user_addmnum = sys_user_addmnum;
		this.sys_user_cmnum = sys_user_cmnum;
		this.sys_user_addreconum = sys_user_addreconum;
		this.sys_user_updreconum = sys_user_updreconum;
		this.sys_user_phone = sys_user_phone;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		this.sysUserWadslisList = sysUserWadslisList;
	}
	
}
