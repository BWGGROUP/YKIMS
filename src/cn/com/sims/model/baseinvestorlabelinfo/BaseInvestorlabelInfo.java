package cn.com.sims.model.baseinvestorlabelinfo;

import java.sql.Timestamp;

/** 
 * @author  yl
 * @date ：2015年10月22日 
 * 投资人标签信息
 */
public class BaseInvestorlabelInfo {
	private String	base_investor_code	;//	投资人id
	private String	sys_labelelement_code	;//	标签元素code
	private String	sys_label_code	;//	标签code
	private String	deleteflag	;//	删除标识
	private String	createid	;//	创建者
	private Timestamp	createtime	;//	创建时间
	private String	updid	;//	更新者
	private Timestamp	updtime	;//	更新时间
	public BaseInvestorlabelInfo(){};
	public BaseInvestorlabelInfo(String base_investor_code,
			String sys_labelelement_code, String sys_label_code,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime) {
		super();
		this.base_investor_code = base_investor_code;
		this.sys_labelelement_code = sys_labelelement_code;
		this.sys_label_code = sys_label_code;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}
	public String getBase_investor_code() {
		return base_investor_code;
	}
	public void setBase_investor_code(String base_investor_code) {
		this.base_investor_code = base_investor_code;
	}
	public String getSys_labelelement_code() {
		return sys_labelelement_code;
	}
	public void setSys_labelelement_code(String sys_labelelement_code) {
		this.sys_labelelement_code = sys_labelelement_code;
	}
	public String getSys_label_code() {
		return sys_label_code;
	}
	public void setSys_label_code(String sys_label_code) {
		this.sys_label_code = sys_label_code;
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
