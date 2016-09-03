package cn.com.sims.model.basefinancplanemail;

import java.sql.Timestamp;

/**
 * @author yl
 * @date ：2015年10月30日 类说明
 */
public class BaseFinancplanEmail {
	private String base_financplan_code;// 融资计划id
	private String base_financplan_email;// 邮箱
	private String base_financplan_sendwstate;// 定时发送邮件状态
	private String deleteflag;// 删除标识
	private String createid;// 创建者
	private Timestamp createtime;// 创建时间
	private String updid;// 更新者
	private Timestamp updtime;// 更新时间
	private String base_financplan_remindate;

	public BaseFinancplanEmail() {
		super();
	}

	public BaseFinancplanEmail(String base_financplan_code,
			String base_financplan_email, String base_financplan_sendwstate,
			String deleteflag, String createid, Timestamp createtime,
			String updid, Timestamp updtime) {
		super();
		this.base_financplan_code = base_financplan_code;
		this.base_financplan_email = base_financplan_email;
		this.base_financplan_sendwstate = base_financplan_sendwstate;
		this.deleteflag = deleteflag;
		this.createid = createid;
		this.createtime = createtime;
		this.updid = updid;
		this.updtime = updtime;
	}

	public String getBase_financplan_code() {
		return base_financplan_code;
	}

	public void setBase_financplan_code(String base_financplan_code) {
		this.base_financplan_code = base_financplan_code;
	}

	public String getBase_financplan_email() {
		return base_financplan_email;
	}

	public void setBase_financplan_email(String base_financplan_email) {
		this.base_financplan_email = base_financplan_email;
	}

	public String getBase_financplan_sendwstate() {
		return base_financplan_sendwstate;
	}

	public void setBase_financplan_sendwstate(String base_financplan_sendwstate) {
		this.base_financplan_sendwstate = base_financplan_sendwstate;
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

	public String getBase_financplan_remindate() {
		return base_financplan_remindate;
	}

	public void setBase_financplan_remindate(String base_financplan_remindate) {
		this.base_financplan_remindate = base_financplan_remindate;
	}

}
