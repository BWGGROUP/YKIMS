package cn.com.sims.model.sysstoredprocedure;

/**
 * 
 * @author shbs-tp004
 * @date 2015-10-14
 * 存储过程 参数
 */
public class SysStoredProcedure {
    	//对应code
	private String orgCode;
	//操作属性
	private String opreaType;
	//操作元素
	private String opreaElement;
	//用户code
	private String userCode;
	//存储过程执行结果
	private String  terror;
	//机构code（交易的融资（机构）信息使用）
	private String  investmentcode;
	//前月，（投资机构特征活跃使用）
	private int beforemonth ;
	//后月，（投资机构特征活跃使用）
	private int aftermonth  ;
	public SysStoredProcedure(){}
	
	public SysStoredProcedure(String orgCode,String opreaType,
		String opreaElement,String userCode,String terror
		,String investmentcode){
		this.orgCode=orgCode;
		this.opreaType=opreaType;
		this.opreaElement=opreaElement;
		this.userCode=userCode;
		this.terror=terror;
		this.investmentcode=investmentcode;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOpreaType() {
		return opreaType;
	}
	public void setOpreaType(String opreaType) {
		this.opreaType = opreaType;
	}
	public String getOpreaElement() {
		return opreaElement;
	}
	public void setOpreaElement(String opreaElement) {
		this.opreaElement = opreaElement;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getTerror() {
		return terror;
	}

	public void setTerror(String terror) {
		this.terror = terror;
	}

	public String getInvestmentcode() {
	    return investmentcode;
	}

	public void setInvestmentcode(String investmentcode) {
	    this.investmentcode = investmentcode;
	}

	public int getBeforemonth() {
	    return beforemonth;
	}

	public void setBeforemonth(int beforemonth) {
	    this.beforemonth = beforemonth;
	}

	public int getAftermonth() {
	    return aftermonth;
	}

	public void setAftermonth(int aftermonth) {
	    this.aftermonth = aftermonth;
	}
	
	
}
