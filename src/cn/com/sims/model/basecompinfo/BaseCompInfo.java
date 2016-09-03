package cn.com.sims.model.basecompinfo;

import java.sql.Timestamp;

/**
 * 公司信息（基本表）
 * @author zzg
 *
 */
public class BaseCompInfo {
	private String base_comp_code;
	private String base_comp_name;
	private String base_comp_fullname;
	private String base_comp_ename;
	private String base_comp_stage;
	private String base_comp_img;
	private String base_comp_stem;
	private String base_comp_estate;
	private String base_comp_namep;
	private String base_comp_namef;
	private String Tmp_Company_Code;
	private String deleteflag;
	private String createid;
	private Timestamp createtime;
	private String updid;
	private Timestamp updtime;
	private long base_datalock_viewtype;
	private String base_datalock_pltype;
	private String sys_user_code;
	private String base_datalock_sessionid;
	private Timestamp base_datalock_viewtime;
	public BaseCompInfo() {

	}
	public BaseCompInfo(String base_comp_code, String base_comp_name,
			String base_comp_fullname, String base_comp_ename,
			String base_comp_stage, String base_comp_img,
			String base_comp_stem, String base_comp_estate,
			String base_comp_namep, String base_comp_namef,
			String tmp_Company_Code, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime,
			Long base_datalock_viewtype, String base_datalock_pltype,
			String sys_user_code, String base_datalock_sessionid,
			Timestamp base_datalock_viewtime) {
		super();
		this.base_comp_code = base_comp_code;
		this.base_comp_name = base_comp_name;
		this.base_comp_fullname = base_comp_fullname;
		this.base_comp_ename = base_comp_ename;
		this.base_comp_stage = base_comp_stage;
		this.base_comp_img = base_comp_img;
		this.base_comp_stem = base_comp_stem;
		this.base_comp_estate = base_comp_estate;
		this.base_comp_namep = base_comp_namep;
		this.base_comp_namef = base_comp_namef;
		Tmp_Company_Code = tmp_Company_Code;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		this.base_datalock_viewtype = base_datalock_viewtype;
		this.base_datalock_pltype = base_datalock_pltype;
		this.sys_user_code = sys_user_code;
		this.base_datalock_sessionid = base_datalock_sessionid;
		this.base_datalock_viewtime = base_datalock_viewtime;
	}
	public String getBase_comp_code() {
		return base_comp_code;
	}
	public void setBase_comp_code(String base_comp_code) {
		this.base_comp_code = base_comp_code;
	}
	public String getBase_comp_name() {
		return base_comp_name;
	}
	public void setBase_comp_name(String base_comp_name) {
		this.base_comp_name = base_comp_name;
	}
	public String getBase_comp_fullname() {
		return base_comp_fullname;
	}
	public void setBase_comp_fullname(String base_comp_fullname) {
		this.base_comp_fullname = base_comp_fullname;
	}
	public String getBase_comp_ename() {
		return base_comp_ename;
	}
	public void setBase_comp_ename(String base_comp_ename) {
		this.base_comp_ename = base_comp_ename;
	}
	public String getBase_comp_stage() {
		return base_comp_stage;
	}
	public void setBase_comp_stage(String base_comp_stage) {
		this.base_comp_stage = base_comp_stage;
	}
	public String getBase_comp_img() {
		return base_comp_img;
	}
	public void setBase_comp_img(String base_comp_img) {
		this.base_comp_img = base_comp_img;
	}
	public String getBase_comp_stem() {
		return base_comp_stem;
	}
	public void setBase_comp_stem(String base_comp_stem) {
		this.base_comp_stem = base_comp_stem;
	}
	public String getBase_comp_estate() {
		return base_comp_estate;
	}
	public void setBase_comp_estate(String base_comp_estate) {
		this.base_comp_estate = base_comp_estate;
	}
	public String getBase_comp_namep() {
		return base_comp_namep;
	}
	public void setBase_comp_namep(String base_comp_namep) {
		this.base_comp_namep = base_comp_namep;
	}
	public String getBase_comp_namef() {
		return base_comp_namef;
	}
	public void setBase_comp_namef(String base_comp_namef) {
		this.base_comp_namef = base_comp_namef;
	}
	public String getTmp_Company_Code() {
		return Tmp_Company_Code;
	}
	public void setTmp_Company_Code(String tmp_Company_Code) {
		Tmp_Company_Code = tmp_Company_Code;
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
	public long getBase_datalock_viewtype() {
		return base_datalock_viewtype;
	}
	public void setBase_datalock_viewtype(long base_datalock_viewtype) {
		this.base_datalock_viewtype = base_datalock_viewtype;
	}
	public String getBase_datalock_pltype() {
		return base_datalock_pltype;
	}
	public void setBase_datalock_pltype(String base_datalock_pltype) {
		this.base_datalock_pltype = base_datalock_pltype;
	}
	public String getSys_user_code() {
		return sys_user_code;
	}
	public void setSys_user_code(String sys_user_code) {
		this.sys_user_code = sys_user_code;
	}
	public String getBase_datalock_sessionid() {
		return base_datalock_sessionid;
	}
	public void setBase_datalock_sessionid(String base_datalock_sessionid) {
		this.base_datalock_sessionid = base_datalock_sessionid;
	}
	public Timestamp getBase_datalock_viewtime() {
		return base_datalock_viewtime;
	}
	public void setBase_datalock_viewtime(Timestamp base_datalock_viewtime) {
		this.base_datalock_viewtime = base_datalock_viewtime;
	}

}
