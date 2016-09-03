package cn.com.sims.model.basecompnoteinfo;

import java.sql.Timestamp;

/**
 * @author yl
 * @date ：2015年10月27日 类说明
 */
public class BaseCompnoteInfo {
	private String base_compnote_code;// 公司noteid
	private String base_comp_code;// 公司id
	private String base_compnote_content;// 内容
	private String sys_user_name;// 创建者用户姓名
	private String deleteflag;// 删除标识
	private String createid;// 创建者
	private Timestamp createtime;// 创建时间
	private String updid;// 更新者
	private Timestamp updtime;// 更新时间

	public BaseCompnoteInfo() {
		super();
	}

	public BaseCompnoteInfo(String base_compnote_code, String base_comp_code,
			String base_compnote_content, String sys_user_name,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime) {
		super();
		this.base_compnote_code = base_compnote_code;
		this.base_comp_code = base_comp_code;
		this.base_compnote_content = base_compnote_content;
		this.sys_user_name = sys_user_name;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}

	public String getBase_compnote_code() {
		return base_compnote_code;
	}

	public void setBase_compnote_code(String base_compnote_code) {
		this.base_compnote_code = base_compnote_code;
	}

	public String getBase_comp_code() {
		return base_comp_code;
	}

	public void setBase_comp_code(String base_comp_code) {
		this.base_comp_code = base_comp_code;
	}

	public String getBase_compnote_content() {
		return base_compnote_content;
	}

	public void setBase_compnote_content(String base_compnote_content) {
		this.base_compnote_content = base_compnote_content;
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
