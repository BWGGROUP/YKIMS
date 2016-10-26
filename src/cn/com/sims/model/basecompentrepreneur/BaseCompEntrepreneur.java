package cn.com.sims.model.basecompentrepreneur;

import java.sql.Timestamp;

public class BaseCompEntrepreneur {
	private String	base_entrepreneur_code	;
	private String	base_comp_code	;
	private String	base_entrepreneur_state	;
	private String	base_entrepreneur_posiname	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	
	//创业者姓名
	private	String	base_entrepreneur_name	;
	private String base_comp_name;
	public BaseCompEntrepreneur() {
	}
	
	public BaseCompEntrepreneur(String	base_entrepreneur_code	,
			String	base_comp_code	,
			String	base_entrepreneur_state	,
			String base_entrepreneur_posiname,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime	) {
		this.base_entrepreneur_code	=	base_entrepreneur_code	;
		this.base_comp_code	=	base_comp_code	;
		this.base_entrepreneur_state	=	base_entrepreneur_state	;
		this.base_entrepreneur_posiname	=	base_entrepreneur_posiname	;
		this.deleteflag	=	deleteflag	;
		this.createid	=	createid	;
		this.createtime	=	createtime	;
		this.updid	=	updid	;
		this.updtime	=	updtime	;

	}

	public String getBase_entrepreneur_code() {
	    return base_entrepreneur_code;
	}

	public void setBase_entrepreneur_code(String base_entrepreneur_code) {
	    this.base_entrepreneur_code = base_entrepreneur_code;
	}

	public String getBase_comp_code() {
	    return base_comp_code;
	}

	public void setBase_comp_code(String base_comp_code) {
	    this.base_comp_code = base_comp_code;
	}

	public String getBase_entrepreneur_state() {
	    return base_entrepreneur_state;
	}

	public void setBase_entrepreneur_state(String base_entrepreneur_state) {
	    this.base_entrepreneur_state = base_entrepreneur_state;
	}

	public String getBase_entrepreneur_posiname() {
	    return base_entrepreneur_posiname;
	}

	public void setBase_entrepreneur_posiname(String base_entrepreneur_posiname) {
	    this.base_entrepreneur_posiname = base_entrepreneur_posiname;
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

	public String getBase_entrepreneur_name() {
	    return base_entrepreneur_name;
	}

	public void setBase_entrepreneur_name(String base_entrepreneur_name) {
	    this.base_entrepreneur_name = base_entrepreneur_name;
	}

	public String getBase_comp_name() {
	    return base_comp_name;
	}

	public void setBase_comp_name(String base_comp_name) {
	    this.base_comp_name = base_comp_name;
	}
	
	
	

}
