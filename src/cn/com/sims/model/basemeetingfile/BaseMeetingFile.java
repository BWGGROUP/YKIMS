package cn.com.sims.model.basemeetingfile;

import java.sql.Timestamp;

/**
 * 会议上传附件信息
 * @author dwj
 * @date 2016-5-5
 */
public class BaseMeetingFile {

	private String 	base_file_code;
	private	String	base_meeting_code	;
	private	String	base_meeting_filename	;
	private	String	base_meeting_src	;
	private	String	deleteflag	;
	private	String	createid	;
	private	Timestamp	createtime	;
	private	String	updid	;
	private	Timestamp	updtime	;
	
	public String getBase_file_code() {
		return base_file_code;
	}
	public void setBase_file_code(String base_file_code) {
		this.base_file_code = base_file_code;
	}
	public String getBase_meeting_code() {
		return base_meeting_code;
	}
	public void setBase_meeting_code(String base_meeting_code) {
		this.base_meeting_code = base_meeting_code;
	}
	public String getBase_meeting_filename() {
		return base_meeting_filename;
	}
	public void setBase_meeting_filename(String base_meeting_filename) {
		this.base_meeting_filename = base_meeting_filename;
	}
	public String getBase_meeting_src() {
		return base_meeting_src;
	}
	public void setBase_meeting_src(String base_meeting_src) {
		this.base_meeting_src = base_meeting_src;
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
