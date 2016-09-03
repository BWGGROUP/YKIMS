package cn.com.sims.model.basemeetinginfo;

import java.sql.Timestamp;

/**
 * 会议信息
 * @author rqq
 *@date 2015-11-09
 */
public class BaseMeetingInfo {
    	//会议id
	private String	base_meeting_code	;
	//会议时间
	private String	base_meeting_time	;
	//会议内容
	private String	base_meeting_content	;
	//排它锁
	private long	base_datalock_viewtype	;
	//PL锁状态
	private String	base_datalock_pltype	;	
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
	public String getBase_meeting_time() {
	    return base_meeting_time;
	}
	public void setBase_meeting_time(String base_meeting_time) {
	    this.base_meeting_time = base_meeting_time;
	}
	public String getBase_meeting_content() {
	    return base_meeting_content;
	}
	public void setBase_meeting_content(String base_meeting_content) {
	    this.base_meeting_content = base_meeting_content;
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
