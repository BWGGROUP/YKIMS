package cn.com.sims.model.viewcompinfo;

import java.sql.Timestamp;

/** 
 * @author  yanglian: 
 * @version ：2015年10月10日 
 * 类说明 
 */
public class viewCompInfo {
	private long	view_comp_id	;
	private String	base_comp_code	;
	private String	base_comp_name	;
	private String	base_comp_fullname	;
	private String	base_comp_ename	;
	private String	base_comp_stage	;
	private String	base_comp_stagecont	;
	private String	base_comp_img	;
	private String	view_comp_baskcode	;
	private String	view_comp_baskcont	;
	private String	view_comp_inducode	;
	private String	view_comp_inducont	;
	private String	view_comp_person	;
	private String	base_comp_namep	;
	private String	base_comp_namef	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	public viewCompInfo() {
	}
	public viewCompInfo(long view_comp_id, String base_comp_code,
			String base_comp_name, String base_comp_fullname,
			String base_comp_ename, String base_comp_stage,
			String base_comp_stagecont, String base_comp_img,
			String view_comp_baskcode, String view_comp_baskcont,
			String view_comp_inducode, String view_comp_inducont,
			String view_comp_person, String base_comp_namep,
			String base_comp_namef, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime) {
		this.view_comp_id = view_comp_id;
		this.base_comp_code = base_comp_code;
		this.base_comp_name = base_comp_name;
		this.base_comp_fullname = base_comp_fullname;
		this.base_comp_ename = base_comp_ename;
		this.base_comp_stage = base_comp_stage;
		this.base_comp_stagecont = base_comp_stagecont;
		this.base_comp_img = base_comp_img;
		this.view_comp_baskcode = view_comp_baskcode;
		this.view_comp_baskcont = view_comp_baskcont;
		this.view_comp_inducode = view_comp_inducode;
		this.view_comp_inducont = view_comp_inducont;
		this.view_comp_person = view_comp_person;
		this.base_comp_namep = base_comp_namep;
		this.base_comp_namef = base_comp_namef;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}
	public long getView_comp_id() {
		return view_comp_id;
	}
	public void setView_comp_id(long view_comp_id) {
		this.view_comp_id = view_comp_id;
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
	public String getBase_comp_stagecont() {
		return base_comp_stagecont;
	}
	public void setBase_comp_stagecont(String base_comp_stagecont) {
		this.base_comp_stagecont = base_comp_stagecont;
	}
	public String getBase_comp_img() {
		return base_comp_img;
	}
	public void setBase_comp_img(String base_comp_img) {
		this.base_comp_img = base_comp_img;
	}
	public String getView_comp_baskcode() {
		return view_comp_baskcode;
	}
	public void setView_comp_baskcode(String view_comp_baskcode) {
		this.view_comp_baskcode = view_comp_baskcode;
	}
	public String getView_comp_baskcont() {
		return view_comp_baskcont;
	}
	public void setView_comp_baskcont(String view_comp_baskcont) {
		this.view_comp_baskcont = view_comp_baskcont;
	}
	public String getView_comp_inducode() {
		return view_comp_inducode;
	}
	public void setView_comp_inducode(String view_comp_inducode) {
		this.view_comp_inducode = view_comp_inducode;
	}
	public String getView_comp_inducont() {
		return view_comp_inducont;
	}
	public void setView_comp_inducont(String view_comp_inducont) {
		this.view_comp_inducont = view_comp_inducont;
	}
	public String getView_comp_person() {
		return view_comp_person;
	}
	public void setView_comp_person(String view_comp_person) {
		this.view_comp_person = view_comp_person;
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
