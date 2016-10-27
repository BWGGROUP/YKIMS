package cn.com.sims.model.baseinvestornoteinfo;

import java.sql.Timestamp;

/**
 * @author yl
 * @date ：2015年10月16日 投资人note信息
 */
public class BaseInvestornoteInfo {
	private String base_investornote_code;//投资人noteid
	private String base_investor_code;//投资人id
	private String base_invesnote_content;//内容
	private String sys_user_name;//创建者用户姓名
	private String deleteflag;//删除标识
	private String createid;//创建者
	private Timestamp createtime;//创建时间
	private String updid;//更新者
	private Timestamp updtime;//更新时间

	public BaseInvestornoteInfo() {

	}

	public BaseInvestornoteInfo(String base_investornote_code,
			String base_investor_code, String base_invesnote_content,
			String sys_user_name, String deleteflag, String createid,
			Timestamp createtime, String updid, Timestamp updtime) {

		this.base_investornote_code = base_investornote_code;
		this.base_investor_code = base_investor_code;
		this.base_invesnote_content = base_invesnote_content;
		this.sys_user_name = sys_user_name;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}

	public String getBase_investornote_code() {
		return base_investornote_code;
	}

	public void setBase_investornote_code(String base_investornote_code) {
		this.base_investornote_code = base_investornote_code;
	}

	public String getBase_investor_code() {
		return base_investor_code;
	}

	public void setBase_investor_code(String base_investor_code) {
		this.base_investor_code = base_investor_code;
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
