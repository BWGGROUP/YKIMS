package cn.com.sims.model.viewmeetingrele;
/**
 * 
 * @author zzg
 *@date 20155-10-14
 */
import java.sql.Timestamp;

public class ViewMeetingRele {
	private Long	view_meeting_releid	;
	private String	base_meeting_code	;
	private String	base_meeting_time	;
	private String	base_meeting_content	;
	private String	base_meeting_typecode	;
	private String	base_meeting_typename	;
	private String	view_meeting_usercode	;
	private String	view_meeting_usercodename	;
	private String	view_meeting_usercont	;
	private String	base_meeting_invicode	;
	private String	base_meeting_invicont	;
	private String	base_meeting_compcode	;
	private String	base_meeting_compcont	;
	private String	base_meeting_intorcode	;
	private String	base_meeting_baskcode	;
	private String  base_meeting_usercode	;
	private String  base_meeting_sharetype	;
	private String	deleteflag	;
	private String	createid	;
	private String  createName ;//dwj 2016-3-4 记录人中文名称
	private Timestamp	createtime	;
	private String	updid	;
	private Timestamp	updtime	;
	//会议详情是否可见
	private String visible;// 0不可见 1 可见
	//dwj 2016-3-4 会议详情是否当前登录人创建
	private String userstatus;// 0不是 1 是
	
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public Long getView_meeting_releid() {
		return view_meeting_releid;
	}
	public void setView_meeting_releid(Long view_meeting_releid) {
		this.view_meeting_releid = view_meeting_releid;
	}
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
	public String getBase_meeting_typecode() {
		return base_meeting_typecode;
	}
	public String getBase_meeting_typename() {
		return base_meeting_typename;
	}
	public void setBase_meeting_typecode(String base_meeting_typecode) {
		this.base_meeting_typecode = base_meeting_typecode;
	}
	public void setBase_meeting_typename(String base_meeting_typename) {
		this.base_meeting_typename = base_meeting_typename;
	}
	public String getView_meeting_usercode() {
		return view_meeting_usercode;
	}
	public void setView_meeting_usercode(String view_meeting_usercode) {
		this.view_meeting_usercode = view_meeting_usercode;
	}
	public String getView_meeting_usercodename() {
		return view_meeting_usercodename;
	}
	public void setView_meeting_usercodename(String view_meeting_usercodename) {
		this.view_meeting_usercodename = view_meeting_usercodename;
	}
	public String getView_meeting_usercont() {
		return view_meeting_usercont;
	}
	public void setView_meeting_usercont(String view_meeting_usercont) {
		this.view_meeting_usercont = view_meeting_usercont;
	}
	public String getBase_meeting_invicode() {
		return base_meeting_invicode;
	}
	public void setBase_meeting_invicode(String base_meeting_invicode) {
		this.base_meeting_invicode = base_meeting_invicode;
	}
	public String getBase_meeting_invicont() {
		return base_meeting_invicont;
	}
	public void setBase_meeting_invicont(String base_meeting_invicont) {
		this.base_meeting_invicont = base_meeting_invicont;
	}
	public String getBase_meeting_compcode() {
		return base_meeting_compcode;
	}
	public void setBase_meeting_compcode(String base_meeting_compcode) {
		this.base_meeting_compcode = base_meeting_compcode;
	}
	public String getBase_meeting_compcont() {
		return base_meeting_compcont;
	}
	public void setBase_meeting_compcont(String base_meeting_compcont) {
		this.base_meeting_compcont = base_meeting_compcont;
	}
	public String getBase_meeting_intorcode() {
		return base_meeting_intorcode;
	}
	public void setBase_meeting_intorcode(String base_meeting_intorcode) {
		this.base_meeting_intorcode = base_meeting_intorcode;
	}
	public String getBase_meeting_baskcode() {
		return base_meeting_baskcode;
	}
	public void setBase_meeting_baskcode(String base_meeting_baskcode) {
		this.base_meeting_baskcode = base_meeting_baskcode;
	}
	public String getBase_meeting_sharetype() {
		return base_meeting_sharetype;
	}
	public void setBase_meeting_sharetype(String base_meeting_sharetype) {
		this.base_meeting_sharetype = base_meeting_sharetype;
	}
	public String getBase_meeting_usercode() {
		return base_meeting_usercode;
	}
	public void setBase_meeting_usercode(String base_meeting_usercode) {
		this.base_meeting_usercode = base_meeting_usercode;
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

	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
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
	
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public ViewMeetingRele() {
		super();
	}
	public ViewMeetingRele(Long view_meeting_releid, String base_meeting_code,
			String base_meeting_time, String base_meeting_content,
			String view_meeting_usercode, String view_meeting_usercodename,
			String view_meeting_usercont, String base_meeting_invicode,
			String base_meeting_invicont, String base_meeting_compcode,
			String base_meeting_compcont, String base_meeting_intorcode,
			String base_meeting_baskcode, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime,
			String visible) {
		super();
		this.view_meeting_releid = view_meeting_releid;
		this.base_meeting_code = base_meeting_code;
		this.base_meeting_time = base_meeting_time;
		this.base_meeting_content = base_meeting_content;
		this.view_meeting_usercode = view_meeting_usercode;
		this.view_meeting_usercodename = view_meeting_usercodename;
		this.view_meeting_usercont = view_meeting_usercont;
		this.base_meeting_invicode = base_meeting_invicode;
		this.base_meeting_invicont = base_meeting_invicont;
		this.base_meeting_compcode = base_meeting_compcode;
		this.base_meeting_compcont = base_meeting_compcont;
		this.base_meeting_intorcode = base_meeting_intorcode;
		this.base_meeting_baskcode = base_meeting_baskcode;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
		this.visible = visible;
	}

}
