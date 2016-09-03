package cn.com.sims.model.basemeetingparp;

import java.sql.Timestamp;

/**
 * 会议与会者信息
 * @author rqq
 *@date 2015-11-09
 */
public class BaseMeetingParp {
    	//会议id
	private String	base_meeting_code	;
	//与会者code
	private String	sys_user_code	;
	//与会者姓名
	private String	sys_user_name	;
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
