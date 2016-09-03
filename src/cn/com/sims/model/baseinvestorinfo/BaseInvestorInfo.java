package cn.com.sims.model.baseinvestorinfo;

import java.sql.Timestamp;

/**
 * @author yl
 * @date ：2015年10月20日 投资人信息表（基础表）
 */
public class BaseInvestorInfo {
	private String base_investor_code;// 投资人id
	private String base_investor_name;// 投资人姓名
	private String base_investor_phone;// 投资人手机
	private String base_investor_wechat;// 投资人微信
	private String base_investor_email;// 投资人邮箱
	private String base_investor_weibo;// 投资人微博
	private String base_investor_img;// 投资人图片
	private String base_investor_stem;// 创建来源
	private String base_investor_estate;// 当前状态
	private String base_investor_namep;// 姓名拼音全拼
	private String base_investor_namepf;// 姓名拼音首字母
	private String Tmp_Investor_Code;// 对应IT桔子id
	private String deleteflag;// 删除标识
	private String createid;// 创建者
	private Timestamp createtime;// 创建时间
	private String updid;// 更新者
	private Timestamp updtime;// 更新时间
	private long base_datalock_viewtype;// 排它锁
	private String base_datalock_pltype;// PL锁状态

	public String getBase_investor_code() {
		return base_investor_code;
	}

	public void setBase_investor_code(String base_investor_code) {
		this.base_investor_code = base_investor_code;
	}

	public String getBase_investor_name() {
		return base_investor_name;
	}

	public void setBase_investor_name(String base_investor_name) {
		this.base_investor_name = base_investor_name;
	}

	public String getBase_investor_phone() {
		return base_investor_phone;
	}

	public void setBase_investor_phone(String base_investor_phone) {
		this.base_investor_phone = base_investor_phone;
	}

	public String getBase_investor_wechat() {
		return base_investor_wechat;
	}

	public void setBase_investor_wechat(String base_investor_wechat) {
		this.base_investor_wechat = base_investor_wechat;
	}

	public String getBase_investor_email() {
		return base_investor_email;
	}

	public void setBase_investor_email(String base_investor_email) {
		this.base_investor_email = base_investor_email;
	}

	public String getBase_investor_weibo() {
		return base_investor_weibo;
	}

	public void setBase_investor_weibo(String base_investor_weibo) {
		this.base_investor_weibo = base_investor_weibo;
	}

	public String getBase_investor_img() {
		return base_investor_img;
	}

	public void setBase_investor_img(String base_investor_img) {
		this.base_investor_img = base_investor_img;
	}

	public String getBase_investor_stem() {
		return base_investor_stem;
	}

	public void setBase_investor_stem(String base_investor_stem) {
		this.base_investor_stem = base_investor_stem;
	}

	public String getBase_investor_estate() {
		return base_investor_estate;
	}

	public void setBase_investor_estate(String base_investor_estate) {
		this.base_investor_estate = base_investor_estate;
	}

	public String getBase_investor_namep() {
		return base_investor_namep;
	}

	public void setBase_investor_namep(String base_investor_namep) {
		this.base_investor_namep = base_investor_namep;
	}

	public String getBase_investor_namepf() {
		return base_investor_namepf;
	}

	public void setBase_investor_namepf(String base_investor_namepf) {
		this.base_investor_namepf = base_investor_namepf;
	}

	public String getTmp_Investor_Code() {
		return Tmp_Investor_Code;
	}

	public void setTmp_Investor_Code(String tmp_Investor_Code) {
		Tmp_Investor_Code = tmp_Investor_Code;
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

}
