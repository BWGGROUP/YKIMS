package cn.com.sims.action.investorDeatil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
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
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
import cn.com.sims.service.baseinvestornoteinfo.IInvestorNoteService;
import cn.com.sims.service.basetradeinfo.IBaseTradeInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;

/**
 * 投资人信息检索 
 * @author  yanglian
 * @date ：2015-10-15 
 * 
 */
@Controller
public class investorInfoAction {
	@Resource
	InvestorService investorService;// 投资人service
	@Resource
	ITradeInfoService tradeInfoService;// 投资机构交易service
	@Resource
	IInvestorNoteService investornoteService;//投资人note信息service　
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	@Resource
	ISysLabelelementInfoService dic;//字典
	@Resource
	ITradeInfoService iTradeInfoService;//交易
	@Resource
	IBaseTradeInfoService iBaseTradeInfoService;//交易信息(基础)
	private static final Logger logger = Logger
			.getLogger(investorInfoAction.class);
/**
 * 投资人详情页面初始化
 * @param request
 * @param response
 * @param logintype
 * @param code
 * @param type 判断搜索存储日志标识
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月16日
 */
	@RequestMapping(value = "findInvestorDeatilByCode")
	public String findInvestorDeatilByCode(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="backherf",required=false)String backherf,
			@RequestParam("code") String code) throws Exception {
		logger.info("investorInfoAction.findInvestorDeatilByCode()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		//投资人基础数据
		BaseInvestorInfo baseInfo = null;
		//投资人详细信息
		viewInvestorInfo investorInfo = null;
		List<Map<String, String>> viewTradeInfo = null;//定义投资机构交易集合
		List<Map<String, String>> noteList = null;//定义投资机构交易集合
		List<Map<String, String>> induList=null;//字典 行业
//		Map<String, String> tradeMap = new HashMap<String,String>();
		List<Map<String, String>> tradelabelList=null;//交易记录行业标签信息
		
//		String message = "success";

		try {
			//交易记录行业标签信息
			tradelabelList=investorService.findTradeLabelByCode(code);
			//根据投资人id查询投资人详细信息（基础表）
			baseInfo = investorService.findInvestorByCode(code);
			//查询行业
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
			//根据投资人id查询投资人详细信息
			investorInfo = investorService.findInvestorDeatilByCode(code);			
			//根据投资人id查询投资人参与的交易信息
			HashMap<String, Object> map= new HashMap<String, Object>();
			map.put("pagesize", new Page().getPageSize());
			map.put("startIndex", 0);
			map.put("code", code);
			
			viewTradeInfo = tradeInfoService.findTradeInfoByInvestorCode(map);
			//根据投资人id查询投资人note信息
			noteList = investornoteService.findNoteByInvestorCode(code);
			
			if(type!=null && type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-people"),
						CommonUtil.findNoteTxtOfXML("Lable-people-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("investor"),
						"",
						"[{\"搜索\":\"{\""+investorInfo.getBase_investor_code()+"\":\""+investorInfo.getBase_investor_name()+"\"}\"}]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
			logger.info("investorInfoAction.findInvestorDeatilByCode()方法end");
		} catch (Exception e) {
			logger.error("investorInfoAction.findInvestorDeatilByCode()方法异常", e);
			e.printStackTrace();
		}

		
		request.setAttribute("viewTradeInfo",JSONArray.fromObject(viewTradeInfo));
		request.setAttribute("investorInfo", investorInfo);
		request.setAttribute("noteList", JSONArray.fromObject(noteList));
		request.setAttribute("induList", JSONArray.fromObject(induList));
		request.setAttribute("baseInfo", baseInfo!=null?new JSONObject(baseInfo):new BaseInvestorInfo());
		request.setAttribute("unDelLabelList", JSONArray.fromObject(tradelabelList));
		request.setAttribute("backherf", backherf);
		String Url = ConstantUrl.investorDeatil(logintype);
		return Url;

	}
	
	@RequestMapping(value = "findNoteByInvestorCode")
	public void findNoteByInvestorCode(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code) throws Exception {
		logger.info("investorInfoAction.findNoteByInvestorCode()方法Start");
		
		//投资人详细信息
//		viewInvestorInfo investorInfo = null;
		List<Map<String, String>> viewTradeInfo = null;//定义投资机构交易集合
//		List<Map<String, String>> noteList = null;//定义投资机构交易集合
		Map<String,Object> tradeMap = new HashMap<String,Object>();
		
//		String message = "success";

		try {
			//根据投资人id查询投资人参与的交易信息总条数
			int totalCount = tradeInfoService.findTradeInfoCount(code);
			//给page类总条数赋值
			page.setTotalCount(totalCount);
			//获取每页显示的条数
			tradeMap.put("pageSize", page.getPageSize());
			//开始索引
			tradeMap.put("startIndex", page.getStartCount());
			//投资人ｉｄ
			tradeMap.put("code", code);
			//根据投资人id查询投资人参与的交易信息
			viewTradeInfo = tradeInfoService.findpageTradeInfoByCode(tradeMap);
			logger.info("investorInfoAction.findNoteByInvestorCode()方法end");
		} catch (Exception e) {
			logger.error("investorInfoAction.findNoteByInvestorCode()方法异常", e);
			e.printStackTrace();
		}

		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("viewTradeInfo", JSONArray.fromObject(viewTradeInfo));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("investorInfoAction.findNoteByInvestorCode() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}

	}
	
	/**
	 * 根据投资人noteid删除note信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @param notecode
	 * @throws Exception
	*@author yl
	*@date 2015年10月19日
	 */
	@RequestMapping(value = "investornote_delet")
	public void investornote_delet(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("notecode") String notecode) throws Exception {
		logger.info("investorInfoAction.investornote_delet()方法Start");
		String message;
		try {
			int i=investornoteService.investotnote_del(notecode);
			if (i==1) {
				message="success";
			}else {
				message="failure";
			}
			logger.info("investorInfoAction.investornote_delet()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("investorInfoAction.investornote_delet() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investorInfoAction.investornote_delet() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investorInfoAction.investornote_delet() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 更新投资人note
	 * @param request
	 * @param response
	 * @param noteInfo
	 * @param base_ele_name
	 * @param logintype
	 * @throws Exception
	*@author yl
	*@date 2015年10月19日
	 */
	@RequestMapping(value = "addInvestorNote")
	public void addInvestorNote(HttpServletRequest request,
			HttpServletResponse response,BaseInvestornoteInfo noteInfo,String logintype) throws Exception {
		List<Map<String, String>> noteList = null;//定义投资机构备注集合
		/* 添加投资人note */
		try {
			String code=CommonUtil.PREFIX_INVESTORNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTORNOTE_TYPE);//生成一个投资机构code
			SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			
			noteInfo.setSys_user_name(sysUserInfo.getSys_user_name());//添加创建者用户名
			noteInfo.setCreateid(sysUserInfo.getSys_user_code());//创建者id
			noteInfo.setCreatetime(new Timestamp(new Date().getTime()));//创建时间
			noteInfo.setBase_investornote_code(code);//投资人note code
			noteInfo.setBase_investor_code(noteInfo.getBase_investor_code());
			noteInfo.setUpdid(noteInfo.getCreateid());
			noteInfo.setUpdtime(noteInfo.getCreatetime());
			investornoteService.insertInvestorNote(noteInfo);//添加投资人note到数据库
			
			
			/* 查询投资人note*/
			noteList=investornoteService.findNoteByInvestorCode(noteInfo.getBase_investor_code());//根据投资人code查询投资人note
			
		} catch (Exception e) {
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
			resultJSON.put("noteList", jsonArray);
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
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param notecode
	 * @throws Exception
	*@author yl
	*@date 2015年10月20日
	 */
	@RequestMapping(value = "editInvestorInfo")
	public void editInvestorInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("info") String info,@RequestParam("type") String type) throws Exception {
		logger.info("investorInfoAction.editInvestorInfo()方法Start");
		String message="success";
		String oldstr="";
		String newstr="";
		//投资人详细信息
		viewInvestorInfo viewInvestorInfo = null;
		BaseInvestorInfo baseInfo = null;
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		net.sf.json.JSONObject json =net.sf.json.JSONObject.fromObject(info);
		json.remove("createtime");
		json.remove("updtime");
		BaseInvestorInfo investorInfo=(BaseInvestorInfo) net.sf.json.JSONObject.toBean(json, BaseInvestorInfo.class);

		try {
			//根据投资人id查询投资人详细信息
			viewInvestorInfo = investorService.findInvestorDeatilByCode(investorInfo.getBase_investor_code());		
			
			investorInfo.setBase_datalock_viewtype(investorInfo.getBase_datalock_viewtype()+1);//设置版本号信息
			investorInfo.setUpdid(sysUserInfo.getSys_user_code());
			investorInfo.setUpdtime(new Timestamp(new Date().getTime()));
			investorInfo.setBase_investor_estate("3");
			investorInfo.setBase_datalock_pltype("0");
			investorInfo.setBase_investor_code(investorInfo.getBase_investor_code());
			int lock=investorService.tranModifyInvestor(investorInfo);
			if(lock!=0){
				
				if("name".equals(type)){
					oldstr = "修改姓名["+viewInvestorInfo.getBase_investor_name()+"]";
					newstr = "修改为["+investorInfo.getBase_investor_name()+"]";
				}else if("phone".equals(type)){
					oldstr = "修改联系方式[手机:"+viewInvestorInfo.getBase_investor_phone()+";email:"+viewInvestorInfo.getBase_investor_email()+";微信："+viewInvestorInfo.getBase_investor_wechat()+"]";
					newstr = "修改为[手机:"+investorInfo.getBase_investor_phone()+";email:"+investorInfo.getBase_investor_email()+";微信："+investorInfo.getBase_investor_wechat()+"]";
				}
				Timestamp time = new Timestamp(new Date().getTime());
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-people"),
						CommonUtil.findNoteTxtOfXML("Lable-people-name"),
						investorInfo.getBase_investor_code(), 
						investorInfo.getBase_investor_name(),
						CommonUtil.OPERTYPE_YK,
						sysUserInfo.getSys_user_code(), 
						sysUserInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
						CommonUtil.findNoteTxtOfXML("investorEdit"),
						oldstr,
						newstr,
						logintype,
						sysUserInfo.getSys_user_code(),
						time,
						sysUserInfo.getSys_user_code(),
						time);
				//根据投资人id查询投资人详细信息（基础表）
				baseInfo = investorService.findInvestorByCode(investorInfo.getBase_investor_code());
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
				baseInfo=investorInfo;
			}
			//根据投资人id查询投资人详细信息
			viewInvestorInfo = investorService.findInvestorDeatilByCode(investorInfo.getBase_investor_code());		
			
			logger.info("investorInfoAction.editInvestorInfo()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("investorInfoAction.editInvestorInfo() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("investorInfo", viewInvestorInfo!=null?new JSONObject(viewInvestorInfo):new viewInvestorInfo());
			resultJSON.put("baseInfo", baseInfo!=null?new JSONObject(baseInfo):new BaseInvestorInfo());
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investorInfoAction.investornote_delet() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investorInfoAction.investornote_delet() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 修改投资人所选行业和近期关注
	 * @param request
	 * @param response
	 * @param investorCode
	 * @param investorName
	 * @param type
	 * @param logintype
	 * @param newData
	 * @param oldData
	 * @param version
	 * @throws Exception
	*@author yl
	*@date 2015年10月23日
	 */
	@RequestMapping(value = "updateInvsetorInfo")
	public void updateInvsetorInfo(HttpServletRequest request,
			HttpServletResponse response, String investorCode,
			String investorName, String type, String logintype, String newData,
			String oldData, int version) throws Exception {
		//获取当前登录人
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		// 定义投资人详情
		viewInvestorInfo viewInvestorInfo = null;
		// 定义投资人信息
		BaseInvestorInfo baseInfo = null;
		// 返回消息
		String message = "success";
		// 返回数据
		String resultData = null;
		BaseInvestorInfo investorInfo = new BaseInvestorInfo();

		investorInfo.setBase_investor_code(investorCode);
		investorInfo.setBase_investor_estate("3");
		investorInfo.setUpdid(sysUserInfo.getSys_user_code());
		investorInfo.setUpdtime(new Timestamp(new Date().getTime()));
		investorInfo.setBase_datalock_viewtype(version + 1);
		investorInfo.setBase_datalock_pltype("0");

		// //更新,获取是否更新成功0失败,1成功
		int lock = investorService.tranModifyInvestor(investorInfo);

		/* 如果lock不为0则更新成功 */
		if (lock != 0) {
			/* 修改投资人标签 */
			investorService.tranModifyUpdateBaseInvestor(sysUserInfo, newData,
					oldData, investorCode, type, investorName, logintype);

			
			/* 查询投资人详情 */
			viewInvestorInfo = investorService
					.findInvestorDeatilByCode(investorCode);

			/* 查询投人构信息 */
			baseInfo = investorService.findInvestorByCode(investorCode);

			/* 获取最新排他锁版本号 */
			version = (int) baseInfo.getBase_datalock_viewtype();

			if (type.equals("Lable-indu")) {// 行业
				resultData = viewInvestorInfo.getView_investor_inducont();
			} else if (type.equals("Lable-payatt")) {// 近期关注
				resultData = viewInvestorInfo.getView_investor_payattcont();
			} else {
				message = "标签信息不存在!";
			}
		} else {
			message = "已被修改,请刷新页面再修改!";
		}

		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(resultData));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		} catch (Exception e) {
			logger.error(
					"investorInfoAction.updateInvsetorInfo() 修改投资人标签信息 返回查询投资人信息 发生异常",
					e);
			e.printStackTrace();
		} finally {
			out.close();
		}

	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param notecode
	 * @throws Exception
	*@author yl
	*@date 2015年10月20日
	 */
	@RequestMapping(value = "editInvestor")
	public void editInvestor(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("investmentcode") String investmentcode,
			@RequestParam("investmentName") String investmentName,@RequestParam("state") String state,
			@RequestParam("posi") String posi,@RequestParam("name") String name,
			@RequestParam("oldstr") String oldstr,@RequestParam("version") String version) throws Exception {
		logger.info("investorInfoAction.editInvestor()方法Start");
		String message="success";
		String newstr="";
		//投资人详细信息
		viewInvestorInfo viewInvestorInfo = null;
		BaseInvestorInfo baseInfo = null;
		BaseInvestorInfo investorInfo = new BaseInvestorInfo();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		Map<String,Object> investment = new HashMap<String, Object>();
		try {
				
			//设置版本号信息
			investorInfo.setBase_datalock_viewtype(Integer.parseInt(version)+1);
			investorInfo.setUpdid(sysUserInfo.getSys_user_code());
			investorInfo.setBase_investor_estate("3");
			investorInfo.setBase_datalock_pltype("0");
			investorInfo.setBase_investor_code(code);
			investorInfo.setUpdtime(new Timestamp(new Date().getTime()));
			int lock=investorService.tranModifyInvestor(investorInfo);
			if(lock!=0){
				newstr = "修改为[投资机构名称:"+investmentName+";职位:"+posi+";状态:"+state+"]";
				if("已离职".equals(state)){
					state="1";
				}else{
					state="0";
				}
				investment.put("code", code);
				investment.put("updid", sysUserInfo.getSys_user_code());
				investment.put("state", state);
				investment.put("posi", posi);
				investment.put("investmentcode", investmentcode);
				
				investment.put("time",new Timestamp(new Date().getTime()));
				int updcount=investorService.tranModifyUpdInvestor(investment);
				
				if(updcount!=0){
					
				}
				
				Timestamp time = new Timestamp(new Date().getTime());
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-people"),
						CommonUtil.findNoteTxtOfXML("Lable-people-name"),
						code,
						name,
						CommonUtil.OPERTYPE_YK,
						sysUserInfo.getSys_user_code(), 
						sysUserInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
						CommonUtil.findNoteTxtOfXML("investorInvesEdit"),
						"["+oldstr+"]",
						newstr,
						logintype,
						sysUserInfo.getSys_user_code(),
						time,
						"",
						time);
				//根据投资人id查询投资人详细信息（基础表）
				baseInfo = investorService.findInvestorByCode(investorInfo.getBase_investor_code());
				version =baseInfo.getBase_datalock_viewtype()+"";
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			//根据投资人id查询投资人详细信息
			viewInvestorInfo = investorService.findInvestorDeatilByCode(investorInfo.getBase_investor_code());		
			
			logger.info("investorInfoAction.editInvestor()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("investorInfo", viewInvestorInfo!=null?new JSONObject(viewInvestorInfo):new viewInvestorInfo());
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 添加投资人所属机构
	 * @param request
	 * @param response
	 * @param logintype
	 * @param code
	 * @param investmentcode
	 * @param state
	 * @param posi
	 * @param name
	 * @param oldstr
	 * @param version
	 * @throws Exception
	*@author yl
	*@date 2015年10月26日
	 */
	@RequestMapping(value = "addInvestor")
	public void addInvestor(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("investmentcode") String investmentcode,
			@RequestParam("state") String state,@RequestParam("investmentname") String investmentname,
			@RequestParam("posi") String posi,@RequestParam("name") String name,
			@RequestParam("oldstr") String oldstr,@RequestParam("version") String version) throws Exception {
		logger.info("investorInfoAction.editInvestor()方法Start");
		String message="success";
		String newstr="";
		//投资人详细信息
				viewInvestorInfo viewInvestorInfo = null;
				BaseInvestorInfo baseInfo = null;
				BaseInvestorInfo investorInfo = new BaseInvestorInfo();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		BaseInvestmentInvestor investmentInvestor = new BaseInvestmentInvestor();
		try {

			//设置版本号信息
			investorInfo.setBase_datalock_viewtype(Integer.parseInt(version)+1);
			investorInfo.setUpdid(sysUserInfo.getSys_user_code());
			investorInfo.setBase_investor_estate("3");
			investorInfo.setBase_datalock_pltype("0");
			investorInfo.setBase_investor_code(code);
			investorInfo.setUpdtime(new Timestamp(new Date().getTime()));
			int lock=investorService.tranModifyInvestor(investorInfo);
			if(lock!=0){
				newstr = "添加[投资机构名称:"+investmentname+";职位:"+posi+";在职状态:"+state+"]";
				if("已离职".equals(state)){
					state="1";
				}else{
					state="0";
				}
				investmentInvestor.setBase_investment_code(investmentcode);
				investmentInvestor.setBase_investor_code(code);
				investmentInvestor.setBase_investor_state(state);
				investmentInvestor.setBase_investor_posiname(posi);
				investmentInvestor.setDeleteflag("0");
				investmentInvestor.setCreateid(sysUserInfo.getSys_user_code());
				investmentInvestor.setCreatetime(new Timestamp(new Date().getTime()));
				investmentInvestor.setUpdid(investmentInvestor.getCreateid());
				investmentInvestor.setUpdtime(investmentInvestor.getCreatetime());
				int updcount=investorService.insertInvestor(investmentInvestor);
				
				if(updcount!=0){
					
				}
				
				Timestamp time = new Timestamp(new Date().getTime());
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-people"),
						CommonUtil.findNoteTxtOfXML("Lable-people-name"),
						code,
						name,
						CommonUtil.OPERTYPE_YK,
						sysUserInfo.getSys_user_code(), 
						sysUserInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
						CommonUtil.findNoteTxtOfXML("addInvestorInvesEdit"),
						"",
						newstr,
						logintype,
						sysUserInfo.getSys_user_code(),
						time,
						"",
						time);
				//根据投资人id查询投资人详细信息（基础表）
				baseInfo = investorService.findInvestorByCode(investorInfo.getBase_investor_code());
				version =baseInfo.getBase_datalock_viewtype()+"";
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			//根据投资人id查询投资人详细信息
			viewInvestorInfo = investorService.findInvestorDeatilByCode(investorInfo.getBase_investor_code());		
			
			logger.info("investorInfoAction.editInvestor()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("investorInfo",viewInvestorInfo!=null?new JSONObject(viewInvestorInfo):new viewInvestorInfo());
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 删除投资人交易
	 * @date 2015-12-01
	 * @author zzg
	 * @param request
	 * @param response
	 * @param logintype
	 * @param tradecode
	 * @param investorcode
	 * @throws Exception
	 */
	@RequestMapping(value = "delinvestor_trade")
	public void delinvestor_trade(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("tradecode") String tradecode,@RequestParam("investmentcode") 
	String investmentcode,String tradeDate,String companyName,String investmentName,String investorcode)throws Exception  {
		logger.info("investorInfoAction.delinvestor()方法start");
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("tradecode", tradecode);
		map.put("investmentcode", investmentcode);
		String message="error";
		try {
			//查询是否含有不属于该投资投资机构的融资信息
			int count=iTradeInfoService.investor_notin_tradser(map);
			//如果有不属于该投资投资人的融资信息，则只融资信息（由交易code和投资人code决定）
			SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			int data=0;
			BaseTradeInfo baseinfo=new BaseTradeInfo();
			baseinfo=iBaseTradeInfoService.findBaseTradeInfoByCode(tradecode);
			if(count>0){
				if (baseinfo.getBase_trade_code()!=null) {
					//删除融资信息
					 data=iBaseTradeInfoService.tranModifyDeleteTradeInvesInfo(userInfo,tradecode,investmentcode,baseinfo.getBase_datalock_viewtype());
				}
				if (data>0) {
					Timestamp timestamp=CommonUtil.getNowTime_tamp();
					/*删除交易信息系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							tradecode, 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoDelete"),
							"删除[公司:"+companyName+",交易日期:"+tradeDate+",机构:"+investmentName+"]交易记录",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					message="success";
				}else {
					message="notrade";
				}
			}else {
				//否则删除该条交易全部信息，包含６张数据表
				//删除融资信息
				if (baseinfo.getBase_trade_code()!=null) {
					String version=String.valueOf(baseinfo.getBase_datalock_viewtype());
					data=iBaseTradeInfoService.tranDeleteTradeInfoByTradeCode(userInfo.getSys_user_code(),tradecode,version,investmentcode);
				}
				if (data>0) {
					Timestamp timestamp=CommonUtil.getNowTime_tamp();
					/*删除交易信息系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-people"),
							CommonUtil.findNoteTxtOfXML("Lable-people-name"),
							investorcode, 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoDelete"),
							"删除[公司:"+companyName+",交易日期:"+tradeDate+",机构:"+investmentName+"]交易记录",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					message="success";
				}else {
					message="notrade";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investorInfoAction.editInvestor() 发生异常", e);
			e.printStackTrace();
		}
	}
}
