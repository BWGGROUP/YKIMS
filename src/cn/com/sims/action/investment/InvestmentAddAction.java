package cn.com.sims.action.investment;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.sims.action.trade.TradeDetialAction;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;

/**
 * 
 * @author duwenjie
 * @date 2015-11-3
 *
 */
@Controller
public class InvestmentAddAction {
	
	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	IBaseInvestmentInfoService baseInvestmentInfoService;//投资机构信息基础层
	
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	@Resource
	SysUserInfoService SysUserInfoService;//系统用户
	
	private static final Logger logger = Logger.getLogger(TradeDetialAction.class);
	


	/**
	 * 跳转添加投资机构界面
	 * @param request
	 * @param response
	 * @param logintype　登录标识(电脑:PC,微信:WX,手机:MB)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "organization_add")
	public String findInvestmentAdd(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		logger.info("InvestmentAddAction　findInvestmentAdd()方法开始");
		return ConstantUrl.urlInvestmentAdd(logintype);
		
	}
	
	/**
	 * 初始化加载添加界面所需数据
	 * @param request
	 * @param response
	 * @param logintype　登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	@RequestMapping(value = "initInvestmentInfoData")
	public void initInvestmentInfoData(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		String message="success";
		List<Map<String, String>> currencyList=null;//字典 币种
		List<Map<String, String>> currencyChildList=null;//字典 币种子项
		List<Map<String, String>> baskList=null;//字典 关注筐
		List<Map<String, String>> induList=null;//字典 行业
		List<Map<String, String>> stageList=null;//字典 阶段
		List<Map<String, String>> featureList=null;//字典 特征
		List<Map<String, String>> typeList=null;//字典 类型
		List<Map<String, String>> bgroundList=null;//字典 背景
		List<Map<String, String>> userInfoMap = null;//系统用户
		
		try {
			currencyList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-currency"));//查询币种字典
			currencyChildList=dic.findAllCurrencyChild();//查询币种子项数据
			
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));//查询筐
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));//查询投资机构行业
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-investage"));//查询投资阶段
			featureList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-feature"));// 查询投资特征
			typeList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-type"));//查询类型
			bgroundList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bground"));//查询背景
			userInfoMap=SysUserInfoService.findAllUserInfo();//查询系统用户
		} catch (Exception e) {
			
			logger.error("InvestmentAddAction　initInvestmentInfoData()方法异常",e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			
			resultJSON.put("message", message);
			resultJSON.put("currencyList", JSONArray.fromObject(currencyList));
			resultJSON.put("currencyChildList", JSONArray.fromObject(currencyChildList));
			
			resultJSON.put("baskList", JSONArray.fromObject(baskList));
			resultJSON.put("induList", JSONArray.fromObject(induList));
			resultJSON.put("stageList", JSONArray.fromObject(stageList));
			resultJSON.put("featList", JSONArray.fromObject(featureList));
			resultJSON.put("typeList", JSONArray.fromObject(typeList));
			resultJSON.put("bgroundList", JSONArray.fromObject(bgroundList));
			resultJSON.put("investorList", JSONArray.fromObject(userInfoMap));
			
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentAddAction initInvestmentInfoData() 返回 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	 * 添加投资机构信息
	 * @param request
	 * @param response
	 * @param logintype　登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param orgFullName　机构全称
	 * @param orgName　机构简称
	 * @param orgEname　机构英文名称
	 * @param orgNote　备注
	 * @param baskInfo　筐
	 * @param induInfo　行业
	 * @param payattInfo　近期关注
	 * @param bggroundInfo　背景
	 * @param typeInfo　类型
	 * @param stageInfo　阶段
	 * @param featInfo　特征
	 * @param fundInfo　基金
	 * @param yklinkInfo 易凯联系人
	 * @throws Exception
	 * @author duwenjie
	 */
	@RequestMapping(value = "insertInvestmentInfo")
	public void insertInvestmentInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			String orgFullName,String orgName,String orgEname,String orgNote,
			String baskInfo,String induInfo,String payattInfo,String bggroundInfo,String typeInfo,
			String stageInfo,String featInfo,String fundInfo,String yklinkInfo) throws Exception {
		SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String fundCode="";//基金code
		Timestamp timestamp = null;
		JSONArray array = null;
		JSONObject obj = null;
		baseInvestmentInfo info = null;//机构对象
		BaseInvesnoteInfo noteInfo = null;//机构备注
		BaseInvesfundInfo invesfundInfo = null;//基金对象
		BaseInvesreponInfo ykInfo  = null;//易凯联系人对象
		List<BaseInveslabelInfo> labelList = null;//标签集合
		List<BaseInvesfundInfo> fundList = null;//基金集合
		List<BaseInvesreponInfo> ykList = null;//易凯联系人集合
		
		// --start date:2015-12-10 Task:076 author:duwenjie option:update
		// 删除conBuffer 系统日志说明内容
		// --end  
		//StringBuffer conBuffer=new StringBuffer();
		
		String message="success";
		try {
			//判断投资机构全称,简称,英文名称是否已存在
			message=baseInvestmentInfoService.findOnlyInvementName(null,orgName, orgFullName, orgEname);
			if(message.equals("only")){
				message="success";
				timestamp = new Timestamp(new Date().getTime());
				String[] pinYin=CommonUtil.getPinYin(orgName);//获取机构名称全拼,简拼
				//生成投资机构code
				String code=CommonUtil.PREFIX_INVESTMENT+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTMENT_TYPE);
				info =new baseInvestmentInfo();
				info.setBase_investment_code(code);
				info.setBase_investment_name(orgName);
				info.setBase_investment_ename(orgEname);
				info.setBase_investment_fullname(orgFullName);
				info.setBase_investment_stem("2");
				info.setBase_investment_estate("2");
				info.setBase_investment_namep(pinYin[0]);
				info.setBase_investment_namepf(pinYin[1]);
				info.setDeleteflag("0");
				info.setCreateid(userInfo.getSys_user_code());
				info.setCreatetime(timestamp);
				info.setUpdid(userInfo.getSys_user_code());
				info.setUpdtime(timestamp);
				info.setBase_datalock_viewtype(0);
				info.setBase_datalock_pltype("0");

				labelList=new ArrayList<BaseInveslabelInfo>();
				/*判断筐标签*/
				if(baskInfo.length()>2){
					array=JSONArray.fromObject(baskInfo);

					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-bask"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				/*判断行业标签*/
				if(induInfo.length()>2){
					array=JSONArray.fromObject(induInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-orgindu"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				
				/*判断近期关注标签*/
				if(payattInfo.length()>2){
					array=JSONArray.fromObject(payattInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-payatt"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				/*判断背景标签*/
				if(bggroundInfo.length()>2){
					array=JSONArray.fromObject(bggroundInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-bground"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				
				/*判断类型标签*/
				if(typeInfo.length()>2){
					array=JSONArray.fromObject(typeInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-type"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				
				/*判断阶段标签*/
				if(stageInfo.length()>2){
					array=JSONArray.fromObject(stageInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-investage"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				
				/*判断投资特征标签*/
				if(featInfo.length()>2){
					array=JSONArray.fromObject(featInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						labelList.add(
								new BaseInveslabelInfo(
										code, 
										obj.getString("code"), 
										CommonUtil.findNoteTxtOfXML("Lable-feature"),
										"0", 
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(), 
										timestamp));
					}
				}
				
				/*判断基金*/
				if(fundInfo.length()>2){
					fundList=new ArrayList<BaseInvesfundInfo>();
					array=JSONArray.fromObject(fundInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						fundCode=CommonUtil.PREFIX_INVESFUND+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESFUND_TYPE);
						invesfundInfo = new BaseInvesfundInfo(
								fundCode,
								code,
								obj.getString("fundName"),
								obj.getString("fundCurrency"), 
								obj.getString("fundCurrencyTxt"),
								obj.getString("fundScale"),
								obj.getString("fundScaleTxt"),
								"",
								"",
								"",
								"", 
								"0",
								userInfo.getSys_user_code(), 
								timestamp, 
								userInfo.getSys_user_code(), 
								timestamp,
								"0");
						fundList.add(invesfundInfo);
					}
				}
				
				if(yklinkInfo.length()>2){
					ykList=new ArrayList<BaseInvesreponInfo>();
					array=JSONArray.fromObject(yklinkInfo);
					for (Object object : array) {
						obj=(JSONObject) object;
						ykInfo = new BaseInvesreponInfo(
								code, 
								obj.getString("code"), 
								obj.getString("name"),
								"0",
								userInfo.getSys_user_code(), 
								timestamp,
								userInfo.getSys_user_code(), 
								timestamp);
						ykList.add(ykInfo);
					}
				}
				
				if(orgNote!=null && !orgNote.trim().equals("")){
					noteInfo = new BaseInvesnoteInfo();
					//生成一个投资机构备注code
					String notecode=CommonUtil.PREFIX_INVESTMENTNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTMENTNOTE_TYPE);
					noteInfo.setBase_invesnote_code(notecode);//投资机构note code
					noteInfo.setBase_investment_code(code);
					noteInfo.setBase_invesnote_content(orgNote);
					noteInfo.setSys_user_name(userInfo.getSys_user_name());
					noteInfo.setDeleteflag("0");
					noteInfo.setCreateid(userInfo.getSys_user_code());
					noteInfo.setCreatetime(timestamp);
					noteInfo.setUpdid(userInfo.getSys_user_code());
					noteInfo.setUpdtime(timestamp);
				}
				
				//添加投资机构信息
				baseInvestmentInfoService.tranModifyInsertInvestmentInfo(userInfo, info, labelList, fundList,ykList, noteInfo);
				
				
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-investment"),
						CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
						info.getBase_investment_code(), 
						info.getBase_investment_name(),
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
						CommonUtil.findNoteTxtOfXML("orgInfoAdd"),
						"",
						"添加投资机构:"+info.getBase_investment_name(),
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
				
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("InvestmentAddAction　insertInvestmentInfo　添加投资机构异常", e);
			e.printStackTrace();
			
		}
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			
			resultJSON.put("message", message);
			if(message.equals("success")){
				resultJSON.put("orgCode", info.getBase_investment_code());
			}
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentAddAction insertInvestmentInfo() 返回 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	
	}
	/**
	 * 根据机构名称判断名称是否已存在
	 * @author zzg
	 * @date 2015-12-18
	 * @param request
	 * @param response
	 * @param logintype
	 * @param orgFullName
	 * @param orgName
	 * @param orgEname
	 * @throws Exception
	 */
	@RequestMapping(value = "checkorgbyname")
	public void checkorgbyname(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			String orgFullName,String orgName,String orgEname)
			 throws Exception {
		logger.info("InvestmentAddAction checkorgbyname() 方法开始");
		String message="error";
		try {
			message=baseInvestmentInfoService.findOnlyInvementName(null,orgName, orgFullName, orgEname);
		} catch (Exception e) {
			logger.error("InvestmentAddAction checkorgbyname() 返回 发生异常", e);
		}
		logger.info("InvestmentAddAction checkorgbyname() 方法结束");
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();			
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentAddAction checkorgbyname() 返回 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

}
