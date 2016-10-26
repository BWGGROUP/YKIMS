package cn.com.sims.model.basemeetingshare;

import java.sql.Timestamp;

/**
 * 会议分享信息
 * @author rqq
 *@date 2015-11-09
 */
public class BaseMeetingShare {
    	//会议id
	private String	base_meeting_code	;
	//会议时间
	private String	base_meeting_sharecode	;
	//分享类别
	private String base_meeting_sharetype;
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
	public String getBase_meeting_sharecode() {
	    return base_meeting_sharecode;
	}
	public void setBase_meeting_sharecode(String base_meeting_sharecode) {
	    this.base_meeting_sharecode = base_meeting_sharecode;
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
	public String getBase_meeting_sharetype() {
		return base_meeting_sharetype;
	}
	public void setBase_meeting_sharetype(String base_meeting_sharetype) {
		this.base_meeting_sharetype = base_meeting_sharetype;
	}
	
	
	
}
