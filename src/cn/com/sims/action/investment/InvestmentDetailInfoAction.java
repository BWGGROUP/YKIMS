package cn.com.sims.action.investment;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewtradeser.ViewTradSer;
import cn.com.sims.service.baseinveslabelinfo.IBaseInveslabelInfoService;
import cn.com.sims.service.baseinvesreponinfo.IBaseInvesreponInfoService;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.basetradeinfo.IBaseTradeInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.investment.baseinvesnoteinfo.IBaseInvesnoteInfoService;
import cn.com.sims.service.investment.investmentDetailInfoService.IInvestmentDetailInfoService;
import cn.com.sims.service.investment.organizationinvesfund.IOrganizationInvesfundService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
@Controller
public class InvestmentDetailInfoAction {
	
	@Resource
	IInvestmentDetailInfoService iInvestmentDetailInfoService;//投资机构详情
	
	@Resource
	ITradeInfoService iTradeInfoService;//交易信息
	
	@Resource
	IOrganizationInvesfundService iOrganizationInvesfundService;//投资机构基金
	
	@Resource
	InvestorService investorService;//投资人
	
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	
	@Resource
	IBaseInvesnoteInfoService baseInvesnoteInfoService;//投资机构note
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	@Resource
	IBaseInvesreponInfoService baseInvesreponInfoService;//易凯联系人
	
	@Resource
	SysUserInfoService SysUserInfoService;//系统用户

	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	IBaseInveslabelInfoService baseLabelInfoService;//投资机构标签信息

	@Resource
	IBaseInvestmentInfoService baseInvestmentInfoService;//投资机构信息基础层

	@Resource
	IBaseTradeInfoService iBaseTradeInfoService;//交易信息(基础)
	
	private static final Logger logger = Logger
	.getLogger(InvestmentDetailInfoAction.class);

	/**
	* 根据投资机构id查询投资机构详情
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)(电脑:PC,微信:WX,手机:MB)
	* @param id投资机构code
	* @param type 判断搜索存储日志标识
	* @param backtype请求来源（search：搜索列表,）
	* @return
	* @version 2015-09-24
	*/
	@RequestMapping(value = "findInvestmentById")
	public String findInvestmentById(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam("id") String id,
			String logintype,
			String type,
			String backtype) throws Exception {
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		viewInvestmentInfo info=null;//定义投资机构详情
		baseInvestmentInfo baseInfo=null;//定义投资机构信息(基础层)
		try {
			
			if(id!=null && !id.trim().equals("")){
				baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(id);//投资机构详情(基础层)
				info=iInvestmentDetailInfoService.findInvestementDetailInfoByID(id);//投资机构详情(业务层)
				if(info==null && baseInfo==null){
					//未查询到数据
					message=CommonUtil.findNoteTxtOfXML("message_nullData");
				}
			}else{
				message=CommonUtil.findNoteTxtOfXML("message_nullData");
			}
			
			if(type!=null&&type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-investment"),
						CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("investment"),
						"",
						"[{\"搜索\":\"{\""+info.getBase_investment_code()+"\":\""+info.getBase_investment_name()+"\"}\"}]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
		} catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findInvestmentById() 发生异常", e);
			e.printStackTrace();
		}
		
		request.setAttribute("message", message);
		request.setAttribute("orgCode", id);
		request.setAttribute("backtype", backtype);
		return ConstantUrl.urlInvestmentDetail(logintype);
	}
	
	
	
	
	
	/**
	* 根据投资机构Code查询投资机构详情
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)(电脑:PC,微信:WX,手机:MB)
	* @param orgCode投资机构code
	* @return
	* @version 2015-11-2
	*/
	@RequestMapping(value = "initInvestmentByOrgCode")
	public void initInvestmentByOrgCode(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String logintype) throws Exception {
		String message = "success";
		long version=0;
		viewInvestmentInfo info=null;//定义投资机构详情
		baseInvestmentInfo baseInfo=null;//定义投资机构信息(基础层)
		List<Map<String, String>> tradeList = null;//定义投资机构交易集合
		List<BaseInvesfundInfo> fundList = null;//定义投资机构基金集合
		List<Map<String, String>> investorList = null;//定义投资人集合
		List<BaseInvesnoteInfo> noteList = null;//定义投资机构备注集合
		List<Map<String, String>> linkmanMap = null;//易凯联系人
		List<Map<String, String>> userInfoMap = null;//系统用户
		List<Map<String, String>> currencyList=null;//字典 币种
		List<Map<String, String>> currencyChildList=null;//字典 币种子项
		List<Map<String, String>> baskList=null;//字典 关注筐
		List<Map<String, String>> induList=null;//字典 行业
		List<Map<String, String>> bggroundList=null;//字典 背景
		List<Map<String, String>> stageList=null;//字典 阶段
		List<Map<String, String>> featureList=null;//字典 特征
		List<Map<String, String>> typeList=null;//字典 类型
		List<Map<String, String>> tradelabelList=null;//交易记录标签信息
		
		try {
			currencyList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-currency"));//查询币种字典
			currencyChildList=dic.findAllCurrencyChild();//查询币种子项数据
			
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));//查询筐
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));//查询行业
			bggroundList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bground"));//查询背景
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-investage"));//查询投资阶段
			featureList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-feature"));// 查询投资特征
			typeList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-type"));//查询类型
			userInfoMap=SysUserInfoService.findAllUserInfo();//查询系统用户
			
			if(orgCode!=null && !orgCode.trim().equals("")){
				//查询投资机构交易记录标签信息
				tradelabelList=baseLabelInfoService.findTradeLabelByOrgCode(orgCode);
				
				//投资机构详情(业务层)
				info=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);
				
				//投资机构详情(基础层)
				baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
				 if(baseInfo!=null){
					 version=baseInfo.getBase_datalock_viewtype();
				 }
				//分页查询投资机构交易信息
				tradeList=iTradeInfoService.findPageTradeInfoByOrgCode(new Page(), orgCode);
				//分页查询投资机构基金
				fundList=iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode,new Page());
				//投资人
				investorList=investorService.findPageInvestorByOrgId(orgCode, new Page());
				//易凯联系人	
				linkmanMap=baseInvesreponInfoService.findYKLinkManByOrgCode(orgCode);
				//投资机构备注note	
				noteList=baseInvesnoteInfoService.findInvesnoteByOrgId(orgCode);
				
			}else{
				message="未发现机构信息";
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("InvestmentDetailInfoAction.initInvestmentByOrgCode 发生异常", e);
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
			resultJSON.put("bggroundList", JSONArray.fromObject(bggroundList));
			resultJSON.put("stageList", JSONArray.fromObject(stageList));
			resultJSON.put("featureList", JSONArray.fromObject(featureList));
			resultJSON.put("typeList", JSONArray.fromObject(typeList));
			
			resultJSON.put("unDelLabelList", JSONArray.fromObject(tradelabelList));
			resultJSON.put("version",version);
			resultJSON.put("investementDetailInfo",info!=null?new JSONObject(info):new JSONObject(new viewInvestmentInfo()));
			resultJSON.put("tradeList", JSONArray.fromObject(tradeList));
			resultJSON.put("fundList", JSONArray.fromObject(fundList));
			resultJSON.put("investorList", JSONArray.fromObject(investorList));
			resultJSON.put("linkList", JSONArray.fromObject(linkmanMap));
			resultJSON.put("userInfoList", JSONArray.fromObject(userInfoMap));
			resultJSON.put("noteList", JSONArray.fromObject(noteList));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.initInvestmentByOrgCode() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	
	/**
	* 根据投资机构id分页查询交易信息
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param orgCode投资机构code
	* @param page分页对象
	* @return
	* @version 2015-09-24
	*/
	@RequestMapping(value = "findPageTradeByOrgId")
	public void findPageTradeByOrgId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("orgCode") String orgCode,Page page,String logintype) throws Exception {
		List<Map<String, String>> tradeList=null;//接收投资机构交易记录
		JSONArray jsonArray=null;
		String message = "success";
		try {
			//根据机构id查询交易记录总数
			int totalcount=iTradeInfoService.findCountTradeInfoByOrgCode(orgCode);
			page.setTotalCount(totalcount);
			
			//分页查询投资机构交易
			tradeList=iTradeInfoService.findPageTradeInfoByOrgCode(page, orgCode);
			
			jsonArray=JSONArray.fromObject(tradeList);
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("InvestmentDetailInfoAction.findPageTradeByOrgId() 发生异常", e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findPageTradeByOrgId() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	 * 删除投资机构交易信息
	 * @param request
	 * @param response
	 * @param orgCode 机构code
	 * @param tradeCode 交易code
	 * @param page 分页对象
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param version
	 * @throws Exception
	 * 
	 * --start date:2015-12-10 Task:076 author:duwenjie option:add
	 * 添加判断排他锁 version
	 * --end  
	 */
	@RequestMapping(value = "deleteInvestmentTrade")
	public void deleteInvestmentTrade(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String tradeCode,Page page,String logintype,long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<Map<String, String>> tradeList=null;//接收投资机构交易记录
		List<Map<String, String>> tradeInfoList = null;//融资信息
		ViewTradSer info=null;//接收交易详情
		JSONArray jsonArray=null;
		String message = "success";
		//date:2015-12-10 Task:076 author:duwenjie option:update
		//交易排他锁
		long tradeVersion=-1;
		int data=0;
		try {
			//date:2015-12-10 Task:076 author:duwenjie option:add
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),orgCode, null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			if(lock>0){
				//查询融资信息
				tradeInfoList=iTradeInfoService.findTradeInfoByTradeCode(new Page(), tradeCode);
				//交易信息(基础)
				BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(tradeCode);
				info=iTradeInfoService.findTradeSerInfoByTradeCode(tradeCode);
				
				if(baseTradeInfo!=null){
					tradeVersion=baseTradeInfo.getBase_datalock_viewtype();
				}
				//date:2015-12-10 Task:076 author:duwenjie option:update
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				
				//date:2015-12-15 duwenjie 判断交易存在唯一该机构融资信息
				if(tradeInfoList.size()==1&&tradeInfoList.get(0).get("base_investment_code").equals(orgCode)){
					//删除整条交易
					data=iBaseTradeInfoService.tranDeleteTradeInfoByTradeCode(
							userInfo.getSys_user_code(), tradeCode, (tradeVersion+"").trim(), orgCode);
					if(data>0){
						/*删除交易信息系统更新记录*/
						baseUpdlogInfoService.insertUpdlogInfo(
								CommonUtil.findNoteTxtOfXML("Lable-investment"),
								CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
								orgCode, 
								"",
								CommonUtil.OPERTYPE_YK,
								userInfo.getSys_user_code(), 
								userInfo.getSys_user_name(),
								CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
								CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
								CommonUtil.findNoteTxtOfXML("tradeInfoDelete"),
								"删除[公司:"+info.getBase_comp_name()+",交易日期:"+info.getBase_trade_date()+"]交易记录",
								"",
								logintype,
								userInfo.getSys_user_code(),
								timestamp,
								userInfo.getSys_user_code(),
								timestamp);
					}else{
						//已被删除返回信息
						message=CommonUtil.findNoteTxtOfXML("deleteNull");
					}
				}else{
					//删除当前机构交易
					data=iBaseTradeInfoService.tranModifyDeleteTradeInvesInfo(userInfo, tradeCode, orgCode,tradeVersion);
					
					if(data>0){
						for (Map<String, String> map : tradeInfoList) {
							String code=map.get("base_investment_code");
							if(code.equals(orgCode)){
								/*删除交易信息系统更新记录*/
								baseUpdlogInfoService.insertUpdlogInfo(
										CommonUtil.findNoteTxtOfXML("Lable-trading"),
										CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
										tradeCode, 
										"",
										CommonUtil.OPERTYPE_YK,
										userInfo.getSys_user_code(), 
										userInfo.getSys_user_name(),
										CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
										CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
										CommonUtil.findNoteTxtOfXML("tradeInvesInfoDelete"),
										"删除[投资机构:"+map.get("base_investment_name")+",投资人:"+map.get("base_investor_name")+",金额:"+map.get("base_trade_inmoney")+"]融资信息",
										"",
										logintype,
										userInfo.getSys_user_code(),
										timestamp,
										userInfo.getSys_user_code(),
										timestamp);
								break;
							}
						
						}
						
					}else{
						//已被删除返回信息
						message=CommonUtil.findNoteTxtOfXML("deleteNull");
					}
				}
				//根据机构id查询交易记录总数
				int totalcount=iTradeInfoService.findCountTradeInfoByOrgCode(orgCode);
				page.setTotalCount(totalcount);
				
				//分页查询投资机构交易
				tradeList=iTradeInfoService.findPageTradeInfoByOrgCode(page, orgCode);
				
				jsonArray=JSONArray.fromObject(tradeList);
				
				//date:2015-12-10 Task:076 author:duwenjie option:add
				/*获取最新排他锁版本号*/
				version=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode).getBase_datalock_viewtype();
			
			}else {
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("InvestmentDetailInfoAction.deleteInvestmentTrade() 发生异常", e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.deleteInvestmentTrade() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	* 根据投资机构id分页查询投资机构投资人
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param orgCode投资机构code
	* @param page分页对象
	* @return
	* @version 2015-09-24
	*/
	@RequestMapping(value = "findPageOrgInvestorByOrgId")
	public void findPageOrgInvestorByOrgId(HttpServletRequest request,
			HttpServletResponse response, String orgCode,Page page,String logintype) throws Exception {
		List<Map<String, String>> investorList = null;//定义投资人集合
		String message = "success";
		try {
			//根据机构id查询投资机构note总数
			int totalcount=investorService.findCountInvestor(orgCode);
			page.setTotalCount(totalcount);
			
			//分页查询投资机构note
			investorList=investorService.findPageInvestorByOrgId(orgCode, page);//投资人
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("InvestmentDetailInfoAction.findPageOrgInvestorByOrgId 发生异常", e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(investorList));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findPageOrgInvestorByOrgId() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	* 根据投资机构code分页查询投资机构基金
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param orgCode投资机构code
	* @param page分页对象
	* @return
	* @version 2015-09-24
	*/
	@RequestMapping(value = "findPageFundByOrgId")
	public void findPageFundByOrgId(HttpServletRequest request,
			HttpServletResponse response, String orgCode,Page page,String logintype) throws Exception {
		List<BaseInvesfundInfo> fundList=null;//接收投资机构交易记录
		String message = "success";
		try {
			//根据机构id查询交易记录总数
			int totalcount=iOrganizationInvesfundService.findCountTfundByOrgId(orgCode);
			page.setTotalCount(totalcount);
			
			//分页查询投资机构交易
			fundList=iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode,page);//分页查询投资机构基金
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("InvestmentDetailInfoAction.findPageTradeByOrgId() 发生异常", e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(fundList));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findPageTradeByOrgId() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	* 添加投资机构备注,返回最新投资机构备注
	* 
	* @param request
	* @param response
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param base_ele_name机构名称
	* @param noteInfo备注信息
	* @return 
	* @version 2015-10-9
	*/
	@RequestMapping(value = "addOrganizationNote")
	public void addOrganizationNote(HttpServletRequest request,
			HttpServletResponse response,BaseInvesnoteInfo noteInfo,String base_ele_name,String logintype) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<BaseInvesnoteInfo> noteList = null;//定义投资机构备注集合
		String message = "success";
		/* 添加投资机构note */
		try {
			//生成一个投资机构备注code
			String code=CommonUtil.PREFIX_INVESTMENTNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTMENTNOTE_TYPE);
			Timestamp timestamp = CommonUtil.getNowTime_tamp();
			noteInfo.setSys_user_name(userInfo.getSys_user_name());//添加创建者用户名
			noteInfo.setCreateid(userInfo.getSys_user_code());//创建者id
			noteInfo.setCreatetime(timestamp);//创建时间
			noteInfo.setBase_invesnote_code(code);//投资机构note code
			noteInfo.setUpdid(userInfo.getSys_user_code());
			noteInfo.setUpdtime(timestamp);
			baseInvesnoteInfoService.insertOrgNote(noteInfo);//添加投资机构note到数据库
			
			
			/* 查询投资机构note*/
			noteList=baseInvesnoteInfoService.findPageInvesnoteByOrgId(noteInfo.getBase_investment_code(),new Page());//查询投资机构note
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("InvestmentDetailInfoAction.addOrganizationNote() 发生异常", e);
			e.printStackTrace();
		}
		
		/* 返回投资机构note集合 */
		PrintWriter out = null;
		JSONArray jsonArray=null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			jsonArray=JSONArray.fromObject(noteList);
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", jsonArray);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.addOrganizationNote() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	/**
	* 添加投资机构基金,返回最新投资机构基金
	* 
	* @param request
	* @param response
	* @param base_ele_name投资机构名称
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param info基金
	* @param version 排他锁版本号
	* @return 
	* @version 2015-10-9
	* 
	* --start date:2015-12-10 Task:076 author:duwenjie option:add
	* 添加判断排他锁 version
	* --end 
	*/
	@RequestMapping(value = "addOrganizationInvesfund")
	public void addOrganizationInvesfund(HttpServletRequest request,
			HttpServletResponse response,BaseInvesfundInfo info,String base_ele_name,String logintype,long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		List<BaseInvesfundInfo> fundList = null;//定义投资机构基金集合
		String message = "success";
		
		try {
			
			//生成一个投资机构基金code
			String code=CommonUtil.PREFIX_INVESFUND+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESFUND_TYPE);
			Timestamp timestamp = CommonUtil.getNowTime_tamp();
			info.setBase_invesfund_code(code);//投资机构基金id
			info.setCreateid(userInfo.getSys_user_code());//创建者code
			info.setCreatetime(timestamp);//创建时间
			info.setUpdid(userInfo.getSys_user_code());
			info.setUpdtime(timestamp);
			info.setBase_invesfund_state("0");
			
			//date:2015-12-10 Task:076 author:duwenjie option:add
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),info.getBase_investment_code(), null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			if(lock>0){
				iOrganizationInvesfundService.tranSaveInsertInvesfund(info,userInfo.getSys_user_code());//保存投资机构基金
				
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-investment"),
						CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
						info.getBase_investment_code(), 
						base_ele_name,
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
						CommonUtil.findNoteTxtOfXML("orgInvesfundAdd"),
						"",
						"添加基金[名称:"+info.getBase_invesfund_name()+",币种:"+info.getBase_invesfund_currencyname()+
						",规模:"+info.getBase_invesfund_scale()+"]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
				//date:2015-12-10 Task:076 author:duwenjie option:add
				/*获取最新排他锁版本号*/
				version=baseInvestmentInfoService.findBaseInvestmentByCode(info.getBase_investment_code()).getBase_datalock_viewtype();
			
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("InvestmentDetailInfoAction.addOrganizationInvesfund() 添加基金 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			
			/*查询投资机构基金*/
			fundList=iOrganizationInvesfundService.findPageInvesfundByOrgId(info.getBase_investment_code(), new Page());
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(fundList));
			resultJSON.put("page", new JSONObject(new Page()));
			//date:2015-12-10 Task:076 author:duwenjie option:add
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.addOrganizationInvesfund() 查询基金 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	/**
	 * 修改投资机构基金
	 * @param request
	 * @param response
	 * @param info 基金对象
	 * @param page 分页对象
	 * @param base_ele_name 机构名称
	 * @param refBool 判断修改位置（主页：true，子页：false）
	 * @param fundName 原基金名称
	 * @param fundCurrency 原币种
	 * @param fundScale 原规模
	 * @param fundState 原状态
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param version 排他锁版本号
	 * @throws Exception
	 * 
	 * --start date:2015-12-10 Task:076 author:duwenjie option:add
	 * 添加判断排他锁 version
	 * --end
	 */
	@RequestMapping(value = "editOrganizationInvesfund")
	public void editOrganizationInvesfund(HttpServletRequest request,
			HttpServletResponse response,BaseInvesfundInfo info,Page page,
			String base_ele_name,String refBool,long version,
			String fundName,String fundCurrency,String fundScale,
			String fundState,String logintype) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		List<BaseInvesfundInfo> fundList = null;//定义投资机构基金集合
		List<BaseInvesfundInfo> fundListFirst = null;//主界面投资机构基金集合
		String message = "success";
		
		try {
			//date:2015-12-10 Task:076 author:duwenjie option:add
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),info.getBase_investment_code(), null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			if(lock>0){
				info.setUpdtime(CommonUtil.getNowTime_tamp());
				info.setUpdid(userInfo.getSys_user_code());
				//修改投资机构基金
				int data=iOrganizationInvesfundService.tranModifyInvesfund(info, userInfo.getSys_user_code());
				if(data==0){
					message=CommonUtil.findNoteTxtOfXML("saveFail");
				}else{
					if(refBool.equals("false")){
						//根据机构id查询交易记录总数
						int totalcount=iOrganizationInvesfundService.findCountTfundByOrgId(info.getBase_investment_code());
						page.setTotalCount(totalcount);
						/*查询投资机构基金*/
						fundList=iOrganizationInvesfundService.findPageInvesfundByOrgId(info.getBase_investment_code(), page);
					}
					
					if(refBool.equals("false") && page.getPageCount()==1){
						fundListFirst=fundList;
					}else {
						fundListFirst=iOrganizationInvesfundService.findPageInvesfundByOrgId(info.getBase_investment_code(), new Page());
					}
					
					StringBuffer logOld = new StringBuffer();
					StringBuffer logNew = new StringBuffer();
					logNew.append("修改为[");
					logOld.append("基金"+fundName+"[");
					if(!fundName.equals(info.getBase_invesfund_name())){
						logOld.append("名称:"+fundName+",");
						logNew.append("基金名称:"+info.getBase_invesfund_name()+",");
					}
					if(!fundCurrency.equals(info.getBase_invesfund_currencyname())){
						logOld.append("币种:"+fundCurrency+",");
						logNew.append("币种:"+info.getBase_invesfund_currencyname()+",");
					}
					if(!fundScale.equals(info.getBase_invesfund_scale())){
						logOld.append("金额:"+fundScale+",");
						logNew.append("金额:"+info.getBase_invesfund_scale()+",");
					}
					if(!fundState.equals(info.getBase_invesfund_state())){
						logOld.append("状态:"+(fundState.equals("0")?"有效":"无效")+",");
						logNew.append("状态:"+(info.getBase_invesfund_state().equals("0")?"有效":"无效"));
					}
					logNew.append("]");
					logOld.append("]");
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							info.getBase_investment_code(), 
							base_ele_name,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("orgInvesfundEdit"),
							logOld.toString(),
							logNew.toString(),
							logintype,
							userInfo.getSys_user_code(),
							info.getUpdtime(),
							userInfo.getSys_user_code(),
							info.getUpdtime());
					
					//date:2015-12-10 Task:076 author:duwenjie option:add
					/*获取最新排他锁版本号*/
					version=baseInvestmentInfoService.findBaseInvestmentByCode(info.getBase_investment_code()).getBase_datalock_viewtype();
				
				}
			}else {
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("editOrganizationInvesfund方法异常",e);
			e.printStackTrace();
		}
		
		

		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			if(refBool.equals("false")){
				resultJSON.put("list", JSONArray.fromObject(fundList));
				resultJSON.put("page", new JSONObject(page));
			}
			resultJSON.put("fundlist", JSONArray.fromObject(fundListFirst));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.editOrganizationInvesfund() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	 * 删除投资机构基金
	 * @param request
	 * @param response
	 * @param orgCode 机构code
	 * @param orgName 机构名称
	 * @param fundCode 基金code
	 * @param fundName 基金名称
	 * @param currency 币种
	 * @param scale 金额
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param page 分页对象
	 * @param refBool 判断修改位置（主页：true，子页：false）
	 * @param version 排他锁
	 * @throws Exception
	 * 
	 * --start date:2015-12-10 Task:076 author:duwenjie option:add
	 * 添加判断排他锁 version
	 * --end
	 */
	@RequestMapping(value = "deleteOrganizationInvesfund")
	public void deleteOrganizationInvesfund(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String orgName,
			String fundCode,String fundName,String currency,String scale,
			String logintype,Page page,String refBool,long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		List<BaseInvesfundInfo> fundList = null;//定义投资机构基金集合
		List<BaseInvesfundInfo> fundListFirst = null;//主界面投资机构基金集合
		String message = "success";
		try {
			//date:2015-12-10 Task:076 author:duwenjie option:add
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),orgCode, null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			if(lock>0){
				int data=iOrganizationInvesfundService.tranDeleteInvesfund(orgCode, fundCode, userInfo.getSys_user_code());
				if(data==0){
					message=CommonUtil.findNoteTxtOfXML("deleteFail");
				}else{
					Timestamp timestamp=CommonUtil.getNowTime_tamp();
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							orgCode, 
							orgName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("orgInvesfundDelete"),
							"删除基金[名称:"+fundName+",币种:"+currency+",金额:"+scale+"]",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					
					if(refBool.equals("false")){
						//根据机构id查询交易记录总数
						int totalcount=iOrganizationInvesfundService.findCountTfundByOrgId(orgCode);
						page.setTotalCount(totalcount);
						/*查询投资机构基金*/
						fundList=iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode, page);
					}
					
					if(refBool.equals("false")&&page.getPageCount()==1){
						fundListFirst=fundList;
					}else {
						fundListFirst=iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode, new Page());
					}
					
					//date:2015-12-10 Task:076 author:duwenjie option:add
					/*获取最新排他锁版本号*/
					version=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode).getBase_datalock_viewtype();
				
				}
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("deleteOrganizationInvesfund方法异常",e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			if(refBool.equals("false")){
				resultJSON.put("list", JSONArray.fromObject(fundList));
				resultJSON.put("page", new JSONObject(page));
			}
			if(page.getPageCount()<=1){
				resultJSON.put("fundlist", JSONArray.fromObject(fundListFirst));
			}
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.deleteOrganizationInvesfund 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	* 添加投资机构易凯联系人,返回易凯联系人列表
	* 
	* @param request
	* @param response
	* @param base_ele_name投资机构名称
	* @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	* @param info易凯联系人
	* @return 
	* @version 2015-10-9
	* 
	* --start date:2015-12-10 Task:076 author:duwenjie option:add
	* 添加判断排他锁 version
	* --end 
	*/
	@RequestMapping(value = "addOrgYKLinkMan")
	public void addOrgYKLinkMan(HttpServletRequest request,
			HttpServletResponse response,BaseInvesreponInfo info,String base_ele_name,String logintype,long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		List<Map<String, String>> linkList=null;//易凯联系人集合
		String message="success";
		try {
			Timestamp timestamp = CommonUtil.getNowTime_tamp();
			info.setCreateid(userInfo.getSys_user_code());
			info.setCreatetime(timestamp);
			info.setUpdid(userInfo.getSys_user_code());
			info.setUpdtime(timestamp);
			
			//date:2015-12-10 Task:076 author:duwenjie option:add
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),info.getBase_investment_code(), null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			if(lock>0){
				/*添加易凯联系人*/
				baseInvesreponInfoService.insertYKLinkMan(info);
				
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-investment"),
						CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
						info.getBase_investment_code(), 
						base_ele_name,
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
						CommonUtil.findNoteTxtOfXML("orgYKLinkManAdd"),
						"",
						"添加易凯联系人姓名:"+info.getSys_user_name(),
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
				
				//date:2015-12-10 Task:076 author:duwenjie option:add
				/*获取最新排他锁版本号*/
				version=baseInvestmentInfoService.findBaseInvestmentByCode(info.getBase_investment_code()).getBase_datalock_viewtype();
			
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("InvestmentDetailInfoAction.addOrgYKLinkMan() 添加易凯联系人 发生异常", e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			
			/*查询投资机构易凯联系人*/
			linkList=baseInvesreponInfoService.findYKLinkManByOrgCode(info.getBase_investment_code());
			
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(linkList));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.addOrgYKLinkMan() 查询基金 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	/**
	 * 编辑投资机构易凯联系人
	 * @param request
	 * @param response
	 * @param orgCode投资机构code
	 * @param orgName投资机构名称
	 * @param oldUserCode原用户code
	 * @param oldUserName原用户名称
	 * @param newUserCode新用户code
	 * @param newUserName新用户名称
	 * @param version排他锁版本号
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	@RequestMapping(value = "editOrgYKLinkMan")
	public void editOrgYKLinkMan(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String orgName,String oldUserCode,String oldUserName,String newUserCode,String newUserName,long version,String logintype) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		List<Map<String, String>> linkmanMap = null;//易凯联系人
		baseInvestmentInfo baseInfo=null;//投资机构信息
		String message="success";
		
		try {
			//判断排他锁版本是否最新
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),orgCode, null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(),CommonUtil.getNowTime_tamp(), version+1, null, null, null, null);
			if(lock!=0){
				int data=baseInvesreponInfoService.tranModifyYKLinkMan(orgCode, oldUserCode, newUserCode, newUserName,userInfo.getSys_user_code());
				if(data==0){
					message="更新失败";
				}else {
					Timestamp timestamp = CommonUtil.getNowTime_tamp();
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							orgCode, 
							orgName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("orgYKLinkManEdit"),
							"原易凯联系人:"+oldUserName,
							"修改为:"+newUserName,
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					
					linkmanMap=baseInvesreponInfoService.findYKLinkManByOrgCode(orgCode);//易凯联系人
				}
				
				/*查询投资机构信息*/
				baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
				
				/*获取最新排他锁版本号*/
				version=baseInfo.getBase_datalock_viewtype();
			}else {
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("editOrgYKLinkMan 方法异常",e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(linkmanMap));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.editOrgYKLinkMan() 修改投资机构易凯联系人 返回查询投资机构易凯联系人 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	
	
	
	/**
	 * 修改投资机构,返回投资机构信息列表
	 * @param request
	 * @param response
	 * @param orgCode投资机构code
	 * @param orgName投资机构名称
	 * @param type筐类型
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param newData新数据code字符串
	 * @param oldData原数据code字符串
	 * @param version排他锁版本号
	 * @throws Exception
	 */
	@RequestMapping(value = "updateOrgInfo")
	public void updateOrgInfo(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String orgName,String type,String logintype,String newData,String oldData,Long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		viewInvestmentInfo orgInfo=null;//定义投资机构详情
		baseInvestmentInfo baseInfo=null;//定义投资机构信息
		String message="success";//返回消息
		String resultData="[]";//返回数据
		
		try {
			//更新,获取是否更新成功0失败,1成功
			int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),orgCode, null, null, null, null, null, "3", null, null, null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), (version+1), "0", null, null, null);
			
			/*如果lock不为0则更新成功*/
			if(lock!=0){
				/*修改投资机构标签*/
				baseLabelInfoService.tranModifyUpdateBaseInvestment(userInfo, newData, oldData, orgCode, type,orgName,logintype);
				
				/*查询投资机构详情*/
				orgInfo=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);//投资机构详情
				
				/*查询投资机构信息*/
				baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
				
				/*获取最新排他锁版本号*/
				version=baseInfo.getBase_datalock_viewtype();
				
				if(type.equals("Lable-bask")){//筐
					resultData=orgInfo.getView_investment_baskcont();
				}else if(type.equals("Lable-indu")){//行业
					resultData=orgInfo.getView_investment_inducont();
				}else if(type.equals("Lable-bground")){//背景
					if(orgInfo.getView_investment_backcode()!=null &&orgInfo.getView_investment_backcont()!=null){
						resultData="[{'code':'"+orgInfo.getView_investment_backcode()+"','name':'"+orgInfo.getView_investment_backcont()+"'}]";
					}else{
						resultData=null;
					}
					
				}else if(type.equals("Lable-payatt")){//近期关注
					resultData=orgInfo.getView_investment_payattcont();
				}else if(type.equals("Lable-investage")){//投资阶段
					resultData=orgInfo.getView_investment_stagecont();
				}else if(type.equals("Lable-feature")){//投资特征
					resultData=orgInfo.getView_investment_featcont();
				}else if(type.equals("Lable-type")){//类型
					resultData=orgInfo.getView_investment_typecont();
				}else{
					message="标签信息不存在";
				}
			}else {
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("updateOrgInfo 方法异常",e);
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", resultData!=null?JSONArray.fromObject(resultData):null);
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.updateOrgInfo() 修改投资机构标签信息 返回查询投资机构信息 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	/**
	 * 修改投资机构名称
	 * @param request
	 * @param response
	 * @param orgCode投资机构code
	 * @param orgName投资机构名称
	 * @param logintype登录标识
	 * @param cName修改后中文简称
	 * @param fName修改后中文全称
	 * @param eName修改后英文名称
	 * @param oldFname原中文全称
	 * @param oldCname原中文简称
	 * @param oldEname原英文名称
	 * @param version排他锁版本号
	 * @throws Exception
	 */
	@RequestMapping(value = "updateInvestmentName")
	public void updateInvestmentName(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String orgName,String logintype,
			String fName,String cName,String eName,long version,
			String oldFname,String oldCname,String oldEname) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		viewInvestmentInfo orgInfo=null;//定义投资机构详情
		baseInvestmentInfo baseInfo=null;//定义投资机构信息
		String message="success";
		
		try {
			//判断投资机构全称,简称,英文名称是否已存在
			message=baseInvestmentInfoService.findOnlyInvementName(orgCode,cName, fName, eName);
			if(message.equals("only")){
				message="success";
				String[] pinYin=CommonUtil.getPinYin(cName);//获取机构名称全拼,简拼
				//修改投资机构信息
				int lock=baseInvestmentInfoService.tranModifyBaseInvestment(userInfo.getSys_user_code(),orgCode, cName, eName, fName, null, null, "3", pinYin[0], pinYin[1], null, null, null, null, userInfo.getSys_user_code(), CommonUtil.getNowTime_tamp(), version+1, "0", null, null, null);
				if(lock!=0){
					Timestamp timestamp = CommonUtil.getNowTime_tamp();
					StringBuffer nString=new StringBuffer();
					StringBuffer oString=new StringBuffer();
					if(!fName.equals(oldFname)){
						oString.append(" 机构全称:"+(oldFname.equals("")?"(空)":oldFname));
						nString.append(" 机构全称:"+(fName.equals("")?"(空)":fName));
					}
					if(!cName.equals(oldCname)){
						oString.append(" 机构简称:"+(oldCname.equals("")?"(空)":oldCname));
						nString.append(" 机构简称:"+(cName.equals("")?"(空)":cName));
					}
					if(!eName.equals(oldEname)){
						oString.append(" 英文名称:"+(oldEname.equals("")?"(空)":oldEname));
						nString.append(" 英文名称:"+(eName.equals("")?"(空)":eName));
					}

					
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							orgCode, 
							orgName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("orgInvestmentNameEdit"),
							"["+oString.toString()+"]",
							"修改为["+nString.toString()+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					
					/*查询投资机构详情*/
					orgInfo=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);//投资机构详情
					
					
					/*查询投资机构信息*/
					baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
					
					/*获取最新排他锁版本号*/
					version=baseInfo.getBase_datalock_viewtype();
				}else {
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("updateInvestmentName 方法异常",e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			if(message.equals("success")){
				resultJSON.put("cnname", orgInfo.getBase_investment_name());
				resultJSON.put("enname", orgInfo.getBase_investment_ename());
				resultJSON.put("fullname", orgInfo.getBase_investment_fullname());
				resultJSON.put("version", version);
			}
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.updateInvestmentName() 修改投资机构标签信息 返回查询投资机构信息 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	
	/**
	 * 根据投资机构备注code删除
	 * @param request
	 * @param response
	 * @param orgCode 投资机构code
	 * @param noteCode投资机构备注code
	 * @param logintype登录标识(电脑PC,微信WX,手机浏览器MB)
	 * @throws Exception
	 */
	@RequestMapping(value = "tranDeleteInvestmentNote")
	public void tranDeleteInvestmentNote(HttpServletRequest request,
			HttpServletResponse response,String orgCode,String noteCode,String logintype) throws Exception {
		int data=0;
		String message="success";
		List<BaseInvesnoteInfo> noteList = null;//定义投资机构备注集合
		try {
			data=baseInvesnoteInfoService.tranDeleteInvestmentNote(noteCode);
			noteList=baseInvesnoteInfoService.findInvesnoteByOrgId(orgCode);//投资机构备注note
			if(data==0){
				message=CommonUtil.findNoteTxtOfXML("deleteNull");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("InvestmentDetailInfoAction.tranDeleteInvestmentNote() 修改投资机构标签信息 返回查询投资机构信息 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(noteList));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.tranDeleteInvestmentNote() 修改投资机构标签信息 返回查询投资机构信息 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	//2015-12-02 TASK076 yl add start
		/**
		 * 删除投资机构的投资人
		 * @param request
		 * @param response
		 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
		 * @param orgCode 投资机构code
		 * @param invstorCode 投资人code
		 * @param orgName 投资机构名称
		 * @param investName 投资人名称
		 * @param page 分页对象
		 * @param refBool判断主页还是分页（主页：true，分页：false）
		 * @throws Exception
		 * 
		 * --start
		 * date:2015-12-07 Task:076 author:duwenjie option:add 
		 * 添加传递参数，page对象，refBool判断分页主页
		 * 返回参数list投资人集合，page分页对象
		 * --end
		 */
		@RequestMapping(value = "delInvestorInfo")
		public void delInvestorInfo(HttpServletRequest request,
				HttpServletResponse response,String logintype,String orgCode,
	     String invstorCode,String orgName,String investName,Page page,String refBool) throws Exception {
			logger.info("InvestmentDetailInfoAction.delInvestorInfo() 开始");
			SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			String message = "success";
			List<Map<String, String>> investorList = null;//定义投资人集合
			try {
				//查询此投资人是否参加过交易
				int count = investorService.selectTrade(orgCode,invstorCode);
				if(count==0){
					//删除投资人投资机构关系
					int data = investorService.tranDeleteInvestor(orgCode,invstorCode,userInfo.getSys_user_code());
					if(data>0){
						//date:2015-12-07 Task:076 author:duwenjie option:add
						//查询投资机构投资人信息
						//根据机构id查询投资机构note总数
						int totalcount=investorService.findCountInvestor(orgCode);
						page.setTotalCount(totalcount);
						//投资人
						investorList=investorService.findPageInvestorByOrgId(orgCode, page);
						
						Timestamp timestamp=CommonUtil.getNowTime_tamp();
						baseUpdlogInfoService.insertUpdlogInfo(
								CommonUtil.findNoteTxtOfXML("Lable-investment"),
								CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
								orgCode, 
								orgName,
								CommonUtil.OPERTYPE_YK,
								userInfo.getSys_user_code(), 
								userInfo.getSys_user_name(),
								CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
								CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
								CommonUtil.findNoteTxtOfXML("investmentInvestorDelete"),
								"删除[投资机构:"+orgName+"的投资人:"+investName+"]",
								"",
								logintype,
								userInfo.getSys_user_code(),
								timestamp,
								userInfo.getSys_user_code(),
								timestamp);
					}else if(data==0){
						//已经被删除
						message="delete";
					}else{
						//删除失败
						message=CommonUtil.findNoteTxtOfXML("deleteFail");
					}
				}else{
					//此投资人参加过交易，不允许删除
					message=CommonUtil.findNoteTxtOfXML("nodel");
				}
				
				logger.info("InvestmentDetailInfoAction.delInvestorInfo() 结束");
			} catch (Exception e) {
				message=CommonUtil.findNoteTxtOfXML("deleteFail");
				logger.error("InvestmentDetailInfoAction.delInvestorInfo() 发生异常", e);
				e.printStackTrace();
			}
			
			
			
			PrintWriter out = null;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("list", JSONArray.fromObject(investorList));
				resultJSON.put("page", new JSONObject(page));
				out.println(resultJSON.toString());
				out.flush();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				out.close();
			}
		}
		//2015-12-02 TASK076 yl add end
	
	
		/**
		 * 修改机构删除标识
		 * @param orgcode 机构code
		 * @param type 操作类型（0：删除机构，1：还原机构）
		 * @author duwenjie
		 * @date 2016-4-20
		 * 
		 */
		@RequestMapping(value="updateOrganizationDelete")
		public void updateOrganizationDelete(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="orgcode",required=true)String orgcode,
				@RequestParam(value="type",required=true)String type,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="logintype",required=true)String logintype){
			SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			String message = "success";
			baseInvestmentInfo baseInfo=null;
			String deleteflag = "";
			Map<String, Object> basemap=null;
			Map<String, Object> map=null;
			try {
				
				if(userInfo!=null){
					if(type.equals("0")||type.equals("1")){
						map=new HashMap<String, Object>();
						map.put("orgcode", orgcode);
						map.put("updtime", CommonUtil.getNowTime_tamp());
						map.put("updid", userInfo.getSys_user_code());
						
						if(type.equals("0")){//删除投资机构
							map.put("del", 1);
						}else if (type.equals("1")) {//还原投资机构
							map.put("del", 0);
						}
						basemap=map;
						basemap.put("lock", version);
						
						int data=iInvestmentDetailInfoService.tranModifyUpdateOrgDelete(basemap, map);
						if(data==0){
							message=CommonUtil.findNoteTxtOfXML("updateFail");
						}else {
							/*查询投资机构信息*/
							baseInfo=baseInvestmentInfoService.findBaseInvestmentByCode(orgcode);
							/*获取最新排他锁版本号*/
							version=baseInfo.getBase_datalock_viewtype();
							deleteflag=baseInfo.getDeleteflag();
							
							if(type.equals("1")){//还原投资机构操作
								/*添加系统更新记录*/
								baseUpdlogInfoService.insertUpdlogInfo(
										CommonUtil.findNoteTxtOfXML("Lable-investment"),
										CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
										orgcode, 
										baseInfo.getBase_investment_name(),
										CommonUtil.OPERTYPE_YK,
										userInfo.getSys_user_code(), 
										userInfo.getSys_user_name(),
										CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
										CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
										CommonUtil.findNoteTxtOfXML("upd")
										+CommonUtil.findNoteTxtOfXML("investment"),
										"",
										"修改机构为有效",
										logintype,
										userInfo.getSys_user_code(),
										baseInfo.getUpdtime(),
										userInfo.getSys_user_code(),
										baseInfo.getUpdtime());
								
							}else{
								/*添加系统更新记录*/
								baseUpdlogInfoService.insertUpdlogInfo(
										CommonUtil.findNoteTxtOfXML("Lable-investment"),
										CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
										baseInfo.getBase_investment_code(), 
										baseInfo.getBase_investment_name(),
										CommonUtil.OPERTYPE_YK,
										userInfo.getSys_user_code(), 
										userInfo.getSys_user_name(),
										CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
										CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
										CommonUtil.findNoteTxtOfXML("upd")
										+CommonUtil.findNoteTxtOfXML("investment"),
										"修改机构为无效",
										"",
										logintype,
										userInfo.getSys_user_code(),
										baseInfo.getUpdtime(),
										userInfo.getSys_user_code(),
										baseInfo.getUpdtime());
							}
						}
					}else{
						message="参数错误";//操作参数错误
					}
					
				}else {
					message="未登录";
				}
			} catch (Exception e) {
				message=CommonUtil.findNoteTxtOfXML("selectFail");
				e.printStackTrace();
				logger.error("InvestmentDetailInfoAction updateOrganizationDelete方法异常",e);
			}
			
			PrintWriter out = null;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				resultJSON.put("deleteflag", deleteflag);
				out.println(resultJSON.toString());
				out.flush();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				out.close();
			}
			
		}
	
}