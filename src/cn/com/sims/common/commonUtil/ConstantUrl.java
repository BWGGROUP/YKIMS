package cn.com.sims.common.commonUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 常量
 * @author 
 * @version 1.0
 */
public class ConstantUrl {
	
	/**
	 * test
	 */
	public static String TEST = ConfigUtil.get("test");
	
	/**
	 * IT桔子抓取数据存储的数据库路径
	 */
	public static String ITSIMSDBURL = ConfigUtil.get("itsimsdburl");
	
	/**
	 * IT桔子抓取数据存储的数据库用户名
	 */
	public static String ITSIMSDBUSERNAME = ConfigUtil.get("itsimsdbusername");
	
	/**
	 * IT桔子抓取数据存储的数据库用户密码
	 */
	public static String ITSIMSDBUSERPAW = ConfigUtil.get("itsimsdbuserpaw");
	/**
	 * IT桔子抓取数据存储的数据库初始化连接
	 */
	public static int ITSIMSDBINITIALSIZE =Integer.parseInt(ConfigUtil.get("itsimsdbinitialSize"));
	
	/**
	 * IT桔子抓取数据存储的数据库最大连接数
	 */
	public static int ITSIMSDMAXACTIVE = Integer.parseInt(ConfigUtil.get("itsimsdmaxActive"));
	
	/**
	 * 跳转到投资机构搜素页面
	 */
	public static String GOTOSEARCHMAIN = ConfigUtil.get("gotoSearchMain");
	/**
	 * 跳转到投资机构搜素页面 PC
	 */
	public static String GOTOSEARCHMAIN_PC = ConfigUtil.get("gotoSearchInvestment_pc");
	/**
	 * 跳转到投资机构ｌｉｓｔ页面
	 */
	public static String GOTOSEARCHINVESTMENTLIST = ConfigUtil.get("gotoSearchInvestmentList");
	/**
	 * 跳转到投资机构ｌｉｓｔ页面PC
	 */
	public static String GOTOSEARCHINVESTMENTLIST_PC = ConfigUtil.get("gotoSearchInvestmentList_pc");
	
	/*跳转到融资计划搜索
	 */
	public static String FANINCINGSEARCH=ConfigUtil.get("financingsearch");
	/*跳转到融资计划搜索
	 */
	public static String FANINCINGSEARCH_PC=ConfigUtil.get("financingsearch_pc");
	/*跳转到登录（mobile）
	 */
	public static String LOGIN_MOBILE=ConfigUtil.get("login_moblie");
	/*跳转到登录（pc）
	 */
	public static String LOGIN_PC=ConfigUtil.get("login_pc");
	/*跳转到会议搜索（mobile）
	 */
	public static String MEETSEARCH=ConfigUtil.get("meetingserach_mobile");
	/*跳转到会议搜索（mobile）
	 */
	public static String MEETSEARCH_PC=ConfigUtil.get("meetingserach_pc");
	/*跳转到会议详情（mobile）
	 */
	public static String MEETINFO=ConfigUtil.get("meetinginfo_mobile");
	/*跳转到会议详情（PC）
	 */
	public static String MEETINFO_PC=ConfigUtil.get("meetinginfo_pc");
	/**
	 * 投资机构详情(MB)
	 * investmentDetailInfo
	 */
	public static String investmentDetailInfo = ConfigUtil.get("investmentDetailInfo");
	
	/**
	 * 投资机构详情(PC)
	 * investmentDetailInfoPC
	 */
	public static String investmentDetailInfoPC= ConfigUtil.get("investmentDetailInfoPC");
	
	/**
	 * 投资机构添加(MB)
	 */
	public static String investmentAdd_MB = ConfigUtil.get("investmentAdd");
	
	/**
	 * 投资机构添加(PC)
	 */
	public static String investmentAdd_PC = ConfigUtil.get("investmentAddPC");
	
	/**
	 * 跳转到投资机构高级筛选页面
	 */
	public static String GOTOSEARCHINVESTMENT = ConfigUtil.get("gotoSearchInvestment");
	/**
	 * 跳转到投资机人详情页面
	 */
	public static String INVESTORDEATIL = ConfigUtil.get("investorDeatil");
	/**
	 * 跳转到投资机人详情页面(PC)
	 */
	public static String INVESTORDEATIL_PC = ConfigUtil.get("investorDeatil_pc");
	/**
		 * 跳转到投资机人详情页面
	 */
	public static String INVESTORADD = ConfigUtil.get("investorAdd");
	/**
	 * 跳转到投资机人详情页面PC
 */
public static String INVESTORADD_PC = ConfigUtil.get("investorAdd_pc");
	/**
	 * 跳转到交易搜索页面（mobile）
	 */
	public static String TRADESERECH_M = ConfigUtil.get("tradeserach_mobile");
	/**
	 * 跳转到交易搜索页面（pc）
	 */
	public static String TRADESERECH_PC = ConfigUtil.get("tradeserach_pc");
	
	/**
	 * 跳转到交易详情页面(mobile)
	 */
	public static String TRADEDETIAL_M = ConfigUtil.get("tradedetial_mobile");
	/**
	 * 跳转到交易详情页面(pc)
	 */
	public static String TRADEDETIAL_P = ConfigUtil.get("tradedetail_pc");
	
	
	/**
	 * 跳转到交易添加页面(mobile)
	 */
	public static String TRADEADD_M = ConfigUtil.get("tradeadd_mobile");
	/**
	 * 跳转到交易添加页面(pc)
	 */
	public static String TRADEADD_PC = ConfigUtil.get("tradeadd_pc");
	
	/**
	 * 跳转到会议添加页面(mobile)
	 */
	public static String MEETINGADD_M = ConfigUtil.get("meetingadd_mobile");
	/**
	 * 跳转到会议添加页面(PC)
	 */
	public static String MEETINGADD_PC = ConfigUtil.get("meetingadd_pc");
	
	/**
	 * 跳转到用户个人信息（mobile）
	 */
	public static String USERINFO_M = ConfigUtil.get("userinfo_mobile");
	/**
	 * 跳转到用户个人信息（PC）
	 */
	public static String USERINFO_PC = ConfigUtil.get("userinfo_pc");
	/**
	 * 跳转到用户密码管理（mobile）
	 */
	public static String USERPASSWORD_M = ConfigUtil.get("userpassword_mobile");
	/**
	 * 跳转到用户密码管理（pc）
	 */
	public static String USERPASSWORD_PC = ConfigUtil.get("userpassword_pc");
	/**
	 * 跳转到公司检索（mobile）
	 */
	public static String COMPANYSEARCH_M = ConfigUtil.get("companysearch_mobile");
	/**
	 * 跳转到公司详情页面（mobile）
	 */
	public static String COMPDEATIL = ConfigUtil.get("companydetail_mobile");
	/**
	 * 跳转到公司添加页面（mobile）
	 */
	public static String COMPADD = ConfigUtil.get("companyadd_mobile");
	/**
	 * 跳转到公司检索（mobile）
	 */
	public static String COMPANYSEARCH_PC = ConfigUtil.get("companysearch_pc");
	/**
	 * 跳转到员工管理
	 */
	public static String EMPLOYEE = ConfigUtil.get("employee_manage");
	/**
	 * 跳转到标签管理
	 */
	public static String TAGMANAGE = ConfigUtil.get("tag_manage");
	/**
	 * 跳转到员工详情
	 */
	public static String EMPLOYEEINFO = ConfigUtil.get("employee_info");
	/**
	 * 跳转到筐管理
	 */
	public static String KUANG = ConfigUtil.get("kuang_manage");
	/**
	 * 跳转到筐详情
	 */
	public static String KUANGINFO = ConfigUtil.get("kuang_info");
	/**
	 * 跳转到筐添加
	 */
	public static String KUANGADD = ConfigUtil.get("kuang_add");
	/**
	 * 公司详情页面（ＰＣ）
	 */
	public static String COMPANYDETAILPC = ConfigUtil.get("companydetail_pc");
	/**
	 * 跳转到公司添加页面（PC）
	 */
	public static String COMPADD_PC = ConfigUtil.get("companyadd_pc");
	/**
	 * 跳转到我的足迹（pc）
	 */
	public static String MYFOOT_PC = ConfigUtil.get("myfoot_pc");
	/**
	 * 跳转到我的足迹（MB,WX）
	 */
	public static String MYFOOT_MW = ConfigUtil.get("myfoot_mw");
	
	/**
	 * 跳转到公司检索（mobile）
	 */
	/**
	 * 跳转到投资机构清单页面
	 * @param loginType
	 * @return
	 */
	public static String investmentList(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), GOTOSEARCHINVESTMENTLIST);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), GOTOSEARCHINVESTMENTLIST);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), GOTOSEARCHINVESTMENTLIST_PC);
		return (String) map.get(loginType);
	}
	/**
	 * 跳转到主搜索页面
	 * @param loginType
	 * @return
	 */
	public static String searchMain(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), GOTOSEARCHMAIN);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), GOTOSEARCHMAIN);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), GOTOSEARCHMAIN_PC);
		return (String) map.get(loginType);
	}
	
/**
 * 跳转到投资机构高级筛选页面
 * @param loginType
 * @return
 */
	public static String gotoSearchInvest(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), GOTOSEARCHINVESTMENT);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), GOTOSEARCHINVESTMENT);
		return (String) map.get(loginType);
	}
	/**
	 * 多条件搜索跳转到投资机构列表页面
	 * @param loginType
	 * @return
	 */
	public static String gotoSearchInvestList(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), GOTOSEARCHINVESTMENTLIST);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), GOTOSEARCHINVESTMENTLIST);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), GOTOSEARCHINVESTMENTLIST_PC);
		return (String) map.get(loginType);
	}
	/**
	 * 跳转到投资人详情页面
	 * @param loginType
	 * @return
	 */
	public static String investorDeatil(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), INVESTORDEATIL);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), INVESTORDEATIL);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), INVESTORDEATIL_PC);
		return (String) map.get(loginType);
	}
		/**
	 * 登录页面跳转
	 * @author zzg
	 */
	public static String login(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), LOGIN_MOBILE);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), LOGIN_MOBILE);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), LOGIN_PC);
		return  map.get(logintype);
	}
	/**
	 *跳转到会议搜索页面
	 *@author zzg
	 */
	public static String meettingsearch(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), MEETSEARCH);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), MEETSEARCH);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), MEETSEARCH_PC);
		return  map.get(logintype);
	}
	/**
	 *跳转到会议详情页面
	 *@author zzg
	 */
	public static String meettinginfo(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), MEETINFO);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), MEETINFO);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), MEETINFO_PC);
		return  map.get(logintype);
	}
	/**跳转到交易搜索页面
	 * @author zzg
	 */
	public static String tradeserach(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), TRADESERECH_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), TRADESERECH_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), TRADESERECH_PC);
		return  map.get(logintype);
	}
	/**跳转到用户信息修改
	 * @author zzg
	 */
	public static String userinfo(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), USERINFO_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), USERINFO_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), USERINFO_PC);
		return  map.get(logintype);
	}
	/**跳转到密码管理
	 * @author zzg
	 */
	public static String userpasssword(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), USERPASSWORD_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), USERPASSWORD_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), USERPASSWORD_PC);
		return  map.get(logintype);
	}
	/**
	 * 跳转到公司检索页面
	 * @author zzg
	 */
	public static String companyserarch(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), COMPANYSEARCH_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), COMPANYSEARCH_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), COMPANYSEARCH_PC);
		return  map.get(logintype);
	}
	/**
	 * 跳转到融资计划搜索
	 * @author zzg
	 */
	public static String fanincingsearch(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), FANINCINGSEARCH);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), FANINCINGSEARCH);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), FANINCINGSEARCH_PC);
		return  map.get(logintype);
	}
	
	
	/**
	 * 跳转交易详情界面
	 * @param logintype
	 * @return 跳转路径
	 * @author duwenjie
	 */
	public static String urlTradeDetial (String logintype){
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), TRADEDETIAL_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), TRADEDETIAL_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), TRADEDETIAL_P);
		return  map.get(logintype);
		
	}
	
	/**
	 * 跳转到交易添加
	 * @author rqq
	 */
	public static String urlTradeAdd(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), TRADEADD_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), TRADEADD_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), TRADEADD_PC);
		return  map.get(logintype);
	}
	/**
	 * 跳转到会议添加
	 * @author rqq
	 */
	public static String urlMeetingAdd(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), MEETINGADD_M);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), MEETINGADD_M);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), MEETINGADD_PC);
		return  map.get(logintype);
	}
	
	/**
	 * 我的足迹
	 * @param logintype
	 * @date 2016-6-1
	 * @return
	 */
	public static String myfootSearch(String logintype) {
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), MYFOOT_MW);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), MYFOOT_MW);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), MYFOOT_PC);
		return  map.get(logintype);
	}
	
	
	/**
	 * 跳转员工管理
	 * @param logintype
	 * @return 跳转路径
	 * @author zzg
	 */
	public static String employee (String logintype){
		HashMap<String, String> map=new HashMap<String, String>();
//		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), EMPLOYEE);
		return  EMPLOYEE;
		
	}
	/**
	 * 跳转标签管理
	 * @param logintype
	 * @return 跳转路径
	 * @author zzg
	 */
	public static String tag_manage (String logintype){
		HashMap<String, String> map=new HashMap<String, String>();
//		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), EMPLOYEE);
		return  TAGMANAGE;
		
	}
	public static String kuang (String logintype){
//		HashMap<String, String> map=new HashMap<String, String>();
//		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), EMPLOYEE);
		return  KUANG;
		
	}
	public static String employee_info (String logintype){
//		HashMap<String, String> map=new HashMap<String, String>();
//		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), EMPLOYEE);
		return  EMPLOYEEINFO;
		
	}
		/**
	 * 跳转到公司详情页面
	 * @param loginType
	 * @author yl
	 * @return
	 */
	public static String compDeatil(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), COMPDEATIL);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), COMPDEATIL);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), COMPANYDETAILPC);
		return (String) map.get(loginType);
	}
	/**
 * 跳转到系统筐详情页面
 * @param loginType
 * @author zzg
 * @return
 */
	public static String kuanginfo(String loginType) {
//		Map map=new HashMap<String,String>();
//		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), COMPDEATIL);
//		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), COMPDEATIL);
		return KUANGINFO;
	}
	/**
	 * 跳转到系统筐添加页面
	 * @param loginType
	 * @author zzg
	 * @return
	 */
		public static String kuangadd(String loginType) {
//			Map map=new HashMap<String,String>();
//			map.put(CommonUtil.findNoteTxtOfXML("PHONE"), COMPDEATIL);
//			map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), COMPDEATIL);
			return KUANGADD;
		}
	
	/**
	 * 跳转到公司添加页面
	 * @param loginType
	 * @author yl
	 * @return
	 */
	public static String companyAdd(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), COMPADD);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), COMPADD);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), COMPADD_PC);
		return (String) map.get(loginType);
	}
	/**
	 * 跳转到投资人添加页面
	 * @param loginType
	 * @author yl
	 * @return
	 */
	public static String investorAdd(String loginType) {
		Map map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), INVESTORADD);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), INVESTORADD);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), INVESTORADD_PC);
		return (String) map.get(loginType);
	}
		
	/**
	 * 跳转投资机构添加界面
	 * @param logintype
	 * @return
	 * @author duwenjie
	 */
	public static String urlInvestmentAdd(String logintype){
		Map<String,String> map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), investmentAdd_MB);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), investmentAdd_MB);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), investmentAdd_PC);
		return (String) map.get(logintype);
	}
	
	/**
	 * 跳转投资机构详情界面
	 * @param logintype
	 * @return
	 * @author duwenjie
	 */
	public static String urlInvestmentDetail(String logintype){
		Map<String,String> map=new HashMap<String,String>();
		map.put(CommonUtil.findNoteTxtOfXML("PHONE"), investmentDetailInfo);
		map.put(CommonUtil.findNoteTxtOfXML("PHONE-BROWSER"), investmentDetailInfo);
		map.put(CommonUtil.findNoteTxtOfXML("COMPUTER"), investmentDetailInfoPC);
		return (String) map.get(logintype);
	}
	
	
}
