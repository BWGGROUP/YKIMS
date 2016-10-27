package cn.com.sims.model.basemeetingnoteinfo;

import java.sql.Timestamp;

/**
 * 会议note信息
 * @author zzg
 *@date 2015-10-15
 */
public class BaseMeetingNoteInfo {

	private String	base_meetingnote_code	;
	private String	base_meeting_code	;
	private String	base_invesnote_content	;
	private String	sys_user_name	;
	private String	deleteflag	;
	private String	createid	;
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	public BaseMeetingNoteInfo(String base_meetingnote_code,
			String base_meeting_code, String base_invesnote_content,
			String sys_user_name, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime) {
		super();
		this.base_meetingnote_code = base_meetingnote_code;
		this.base_meeting_code = base_meeting_code;
		this.base_invesnote_content = base_invesnote_content;
		this.sys_user_name = sys_user_name;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}
	public BaseMeetingNoteInfo() {
		super();
	}
	public String getBase_meetingnote_code() {
		return base_meetingnote_code;
	}
	public void setBase_meetingnote_code(String base_meetingnote_code) {
		this.base_meetingnote_code = base_meetingnote_code;
	}
	public String getBase_meeting_code() {
		return base_meeting_code;
	}
	public void setBase_meeting_code(String base_meeting_code) {
		this.base_meeting_code = base_meeting_code;
	}
	public String getBase_invesnote_content() {
		return base_invesnote_content;
	}
	public void setBase_invesnote_content(String base_invesnote_content) {
		this.base_invesnote_content = base_invesnote_content;
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
