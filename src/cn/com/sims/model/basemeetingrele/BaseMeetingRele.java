package cn.com.sims.model.basemeetingrele;

import java.sql.Timestamp;

/**
 * 会议分享信息
 * @author rqq
 *@date 2015-11-09
 */
public class BaseMeetingRele {
    	//会议id
	private String	base_meeting_code	;
	//相关类型:１：机构２：公司
	private String	base_meeting_rele	;
	/**相关类型为机构时则为机构code;
	相关类型为公司时则为公司code;**/
	private String	base_meeting_relecode	;
	/**相关类型为机构时则为机构简称;
		相关类型为公司时则为公司简称;**/
	private String	base_meeting_relename	;
	//相关联系人code
	private String	base_meeting_relepcode	;
	//相关联系人姓名
	private String	base_meeting_relepname	;
	//相关联系人职位名称
	private String	base_meeting_relepposi	;
	//删除标识
	private String	deleteflag	;
	//创建者
	private String	createid	;
	//创建时间
	private Timestamp	createtime	;
	//更新者
	private String	updid	;
	//更新时间
	private Timestamp	updtime	;
	public String getBase_meeting_code() {
	    return base_meeting_code;
	}
	public void setBase_meeting_code(String base_meeting_code) {
	    this.base_meeting_code = base_meeting_code;
	}
	public String getBase_meeting_rele() {
	    return base_meeting_rele;
	}
	public void setBase_meeting_rele(String base_meeting_rele) {
	    this.base_meeting_rele = base_meeting_rele;
	}
	public String getBase_meeting_relecode() {
	    return base_meeting_relecode;
	}
	public void setBase_meeting_relecode(String base_meeting_relecode) {
	    this.base_meeting_relecode = base_meeting_relecode;
	}
	public String getBase_meeting_relename() {
	    return base_meeting_relename;
	}
	public void setBase_meeting_relename(String base_meeting_relename) {
	    this.base_meeting_relename = base_meeting_relename;
	}
	public String getBase_meeting_relepcode() {
	    return base_meeting_relepcode;
	}
	public void setBase_meeting_relepcode(String base_meeting_relepcode) {
	    this.base_meeting_relepcode = base_meeting_relepcode;
	}
	public String getBase_meeting_relepname() {
	    return base_meeting_relepname;
	}
	public void setBase_meeting_relepname(String base_meeting_relepname) {
	    this.base_meeting_relepname = base_meeting_relepname;
	}
	public String getBase_meeting_relepposi() {
	    return base_meeting_relepposi;
	}
	public void setBase_meeting_relepposi(String base_meeting_relepposi) {
	    this.base_meeting_relepposi = base_meeting_relepposi;
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
