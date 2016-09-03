package cn.com.sims.model.viewInvestmentInfo;
/** 
 * @author  E-mail: 
 * @version ：2015年10月12日 
 * 类说明 
 */
public class investmentInfo {
	private String base_investment_name;
	  private String view_investment_typename;
	  private String view_investment_compcode;
	  private String companyname;
	  private String  base_investment_code;
	  private String invesfund;
	  private String view_investment_stagename;
	  private String view_investment_backcont;
	  
		public String getView_investment_stagename() {
		return view_investment_stagename;
	}
	public void setView_investment_stagename(String view_investment_stagename) {
		this.view_investment_stagename = view_investment_stagename;
	}
		public String getView_investment_backcont() {
		return view_investment_backcont;
	}
	public void setView_investment_backcont(String view_investment_backcont) {
		this.view_investment_backcont = view_investment_backcont;
	}
	public String getInvesfund() {
		return invesfund;
	}
	public void setInvesfund(String invesfund) {
		this.invesfund = invesfund;
	}
	public String getBase_investment_code() {
		return base_investment_code;
	}
	public void setBase_investment_code(String base_investment_code) {
		this.base_investment_code = base_investment_code;
	}
	public String getBase_investment_name() {
		return base_investment_name;
	}
	public void setBase_investment_name(String base_investment_name) {
		this.base_investment_name = base_investment_name;
	}
	public String getView_investment_typename() {
		return view_investment_typename;
	}
	public void setView_investment_typename(String view_investment_typename) {
		this.view_investment_typename = view_investment_typename;
	}
	public String getView_investment_compcode() {
		return view_investment_compcode;
	}
	public void setView_investment_compcode(String view_investment_compcode) {
		this.view_investment_compcode = view_investment_compcode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public investmentInfo(String base_investment_name,
			String view_investment_typename, String view_investment_compcode,
			String companyname,String base_investment_code) {
		super();
		this.base_investment_name = base_investment_name;
		this.view_investment_typename = view_investment_typename;
		this.view_investment_compcode = view_investment_compcode;
		this.companyname = companyname;
		this.base_investment_code = base_investment_code;
	}
	public investmentInfo() {
		super();
	}
	  
}
