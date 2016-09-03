package cn.com.sims.action.trade;

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
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;
import cn.com.sims.model.viewtradeser.ViewTradSer;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.baseinvestorinfo.IBaseInvestorInfoService;
import cn.com.sims.service.basetradeinfo.IBaseTradeInfoService;
import cn.com.sims.service.basetradenoteinfo.IBaseTradenoteInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;


/**
 * 
 * @author duwenjie
 * @date 2015-10-23
 */
@Controller
public class TradeDetialAction {
	
	@Resource
	ITradeInfoService iTradeInfoService;//交易
	
	@Resource
	InvestorService investorService;//投资人
	
	@Resource
	IBaseTradenoteInfoService tradenoteInfoService;//交易备注
	
	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	
	@Resource
	IBaseTradeInfoService iBaseTradeInfoService;//交易信息(基础)
	
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	@Resource
	IBaseInvestmentInfoService iBaseInvestmentInfoService;//投资机构
	
	@Resource
	IBaseInvestmentInfoService baseInvestmentInfoService;//投资机构信息基础层
	
	@Resource
	IBaseInvestorInfoService iBaseInvestorInfoService;//投资人
	
	private static final Logger logger = Logger.getLogger(TradeDetialAction.class);
	
	
	/**
	 * 查询交易详情
	 * @param request
	 * @param response
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param tradeCode交易Code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	@RequestMapping(value = "findTradeDetialInfo")
	public String findTradeDetialInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,String tradeCode,
			@RequestParam(value="backherf",required=false)String backherf) throws Exception {
		logger.info("TradeDetialAction.findTradeDetialInfo()方法开始");
		ViewTradSer info=null;//接收交易详情
		String  message="success";
		try {
//			info=iTradeInfoService.findViewTradeInfoByID(tradeId);
			if(tradeCode!=null&& !tradeCode.trim().equals("")){
				info=iTradeInfoService.findTradeSerInfoByTradeCode(tradeCode);
				if(info==null){
					message=CommonUtil.findNoteTxtOfXML("message_nullData");
				}
			}else{
				message=CommonUtil.findNoteTxtOfXML("message_nullData");
			}
			
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("TradeDetialAction.findTradeDetialInfo()方法异常",e);
			e.printStackTrace();
		}
		
		request.setAttribute("tradeInfo", info!=null?new JSONObject(info):new JSONObject(new ViewTradeInfo()));
		request.setAttribute("backherf", backherf);
		request.setAttribute("message",message);
		return ConstantUrl.urlTradeDetial(logintype);
	}
	
	
	
	
	/**
	 * 初始加载交易详情
	 * @param request
	 * @param response
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param tradeCode交易Code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	@RequestMapping(value = "initTradeDetialInfo")
	public void initTradeDetialInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,String tradeCode) throws Exception {
		logger.info("TradeDetialAction.initTradeDetialInfo()方法开始");
		Page page = new Page();
		List<Map<String, String>> baskList=null;//字典 关注筐
		List<Map<String, String>> induList=null;//字典 行业
		List<Map<String, String>> stageList=null;//字典 阶段
		List<BaseTradenoteInfo> noteList=null;//交易备注
		List<Map<String, String>> tradeInfoList = null;//融资信息
		ViewTradSer info=null;//接收交易详情
		long version=-1;//排他锁版本号
		String message="success";
		try {
			//查询筐
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));
			//查询行业
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));
			//查询投资阶段
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-trastage"));
			
			/*2015-12-15 dwj add 查询交易信息*/
			info=iTradeInfoService.findTradeSerInfoByTradeCode(tradeCode);
			
			//交易信息(基础)
			BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(tradeCode);
			if(baseTradeInfo!=null){
				//获取交易信息排他锁版本号
				version=baseTradeInfo.getBase_datalock_viewtype();
				
				//查询交易数量
				int count=iTradeInfoService.findTradeInfoCountByTradeCode(tradeCode);
				
				page.setTotalCount(count);
				
				tradeInfoList=iTradeInfoService.findTradeInfoByTradeCode(page, tradeCode);
				
				//查询交易备注
				noteList=tradenoteInfoService.findTradenoteByTradeCode(tradeCode);
			}else {
				message=CommonUtil.findNoteTxtOfXML("message_nullData");
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("TradeDetialAction.initTradeDetialInfo()方法异常",e);
			e.printStackTrace();
		}
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("version", version);
			
			resultJSON.put("page", new JSONObject(page));
			resultJSON.put("baskList", JSONArray.fromObject(baskList));
			resultJSON.put("tradeInfoList", JSONArray.fromObject(tradeInfoList));
			resultJSON.put("induList", JSONArray.fromObject(induList));
			resultJSON.put("stageList", JSONArray.fromObject(stageList));
			resultJSON.put("noteList", JSONArray.fromObject(noteList));
			resultJSON.put("tradeInfo", info!=null?new JSONObject(info):new JSONObject(new ViewTradeInfo()));
			
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.initTradeDetialInfo() 返回 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	
	/**
	 * 修改交易详情
	 * @param request
	 * @param response
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param info 交易信息
	 * @param oldData 原数据
	 * @param comMoneyType 公司估值类型
	 * @param newStageName 阶段新数据名称
	 * @param type 修改标签
	 * @param version 排他锁版本号
	 * @throws Exception
	 */
	@RequestMapping(value = "updateTradeDetialInfo")
	public void updateTradeDetialInfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			BaseTradeInfo info,String oldData,String comMoneyType,String newStageName,String type,long version) throws Exception {
		logger.info("TradeDetialAction.updateTradeDetialInfo()方法开始");
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		ViewTradSer tradeInfo=null;//接收交易详情(业务)
		String message="success";
		String newCode="";//返回数据code,如果有
		String newData="";//返回新数据
		try {
			Timestamp timestamp =CommonUtil.getNowTime_tamp();
			/*更新交易信息*/
			//排他锁版本号加1
			info.setBase_datalock_viewtype(version+1);
			info.setUpdid(userInfo.getSys_user_code());
			info.setUpdtime(timestamp);
			/*修改交易信息*/
			int data=iBaseTradeInfoService.updateTradeInfo(info);
			if(data!=0){
				/*调用存储过程*/
				storedProcedureService.callViewTradeser(new SysStoredProcedure(info.getBase_trade_code(), "upd", CommonUtil.findNoteTxtOfXML("basic"), userInfo.getSys_user_code(),"",""));
				//交易信息(基础)
				BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(info.getBase_trade_code());
				version=baseTradeInfo.getBase_datalock_viewtype();
				//查询交易信息
				tradeInfo=iTradeInfoService.findTradeSerInfoByTradeCode(info.getBase_trade_code());
			
			
				if(type.equals("base_trade_comnum")){
					newData=tradeInfo.getBase_trade_comnum();
					StringBuffer oldBuffer=new StringBuffer();
					StringBuffer newBuffer=new StringBuffer();
					oldBuffer.append("公司估值[");
					newBuffer.append("修改为[");
					if(info.getBase_trade_comnum()!=null){
						oldBuffer.append((oldData.equals("")?"空":oldData)+",");
						newBuffer.append((info.getBase_trade_comnum().equals("")||info.getBase_trade_comnum()==null)?"空":info.getBase_trade_comnum()+",");
					}
					if(comMoneyType!=null){
						oldBuffer.append("类型:"+(comMoneyType.equals("1")?"获投后":(comMoneyType.equals("0")?"获投前":"空")));
						newBuffer.append("类型:"+(info.getBase_trade_comnumtype().equals("1")?"获投后":(info.getBase_trade_comnumtype().equals("0")?"获投前":"空")));
					}
					oldBuffer.append("]");
					newBuffer.append("]");
					/*添加公司估值修改系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							info.getBase_trade_code(), 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoEditBasic"),
							oldBuffer.toString(),
							newBuffer.toString(),
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}else if (type.equals("base_trade_money")) {
					newData=tradeInfo.getBase_trade_money();
					/*添加融资金额修改系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							info.getBase_trade_code(), 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoEditBasic"),
							"融资金额:"+(oldData.equals("")?"空":oldData),
							"修改为["+((info.getBase_trade_money().equals("")||info.getBase_trade_money()==null)?"空":info.getBase_trade_money())+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}else if(type.equals("base_trade_stage")){
					newCode=tradeInfo.getBase_trade_stage();
					newData=tradeInfo.getBase_trade_stagecont();
					/*添加阶段修改系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							info.getBase_trade_code(), 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoEditBasic"),
							"交易阶段:"+(oldData.equals("")?"空":oldData),
							"修改为["+(newStageName.equals("")?"空":newStageName)+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}else if(type.equals("base_trade_date")){
					newData=tradeInfo.getBase_trade_date();
					/*添加交易日期修改系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							info.getBase_trade_code(), 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoEditBasic"),
							"交易日期:"+oldData,
							",修改为["+info.getBase_trade_date()+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}
				
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("TradeDetialAction.updateTradeDetialInfo()方法异常",e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			if(message.equals("success")){
				resultJSON.put("newData", newData);
				if(info.getBase_trade_comnumtype()!=null){
					resultJSON.put("newType", info.getBase_trade_comnumtype());
				};
				if(!newCode.equals("")){
					resultJSON.put("newCode", newCode);
				}
				resultJSON.put("version", version);
			}
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.updateTradeDetialInfo() 返回 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	

	/**
	 * 修改交易信息筐,行业
	 * @param request
	 * @param response
	 * @param tradeCode交易code
	 * @param type标签类型
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param newData修改后数据
	 * @param oldData原数据
	 * @param version排他锁版本号
	 * @throws Exception
	 */
	@RequestMapping(value = "updateTradeInfoLabel")
	public void editOrgInfo(HttpServletRequest request,
			HttpServletResponse response,String tradeCode,String type,String logintype,String newData,String oldData,Long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		String message="success";//返回消息
		String resultData="[]";//返回数据
		
		/*修改交易信息*/
		BaseTradeInfo info = new BaseTradeInfo();
		info.setBase_trade_code(tradeCode);
		info.setBase_datalock_viewtype(version+1);
		info.setUpdid(userInfo.getSys_user_code());
		info.setUpdtime(CommonUtil.getNowTime_tamp());
		
		try {
			//更新交易信息排他锁版本号
			int data=iBaseTradeInfoService.updateTradeInfo(info);
			if(data!=0){
				//修改交易标签信息
				iBaseTradeInfoService.tranModifyBaseTradeLabelInfo(userInfo, newData, oldData, tradeCode, type, logintype);
				/*调用存储过程*/
				storedProcedureService.callViewTradeser(new SysStoredProcedure(info.getBase_trade_code(), "upd", type, userInfo.getSys_user_code(),"",""));
				
				//交易信息(基础)
				BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(info.getBase_trade_code());
				if(baseTradeInfo!=null){
					version=baseTradeInfo.getBase_datalock_viewtype();
				}else {
					message=CommonUtil.findNoteTxtOfXML("message_nullData");
				}
				
				//查询交易信息
				ViewTradSer tradeInfo=iTradeInfoService.findTradeSerInfoByTradeCode(tradeCode);
				if(tradeInfo!=null){
					if(type.equals("trade-Lable-bask")){
						//获取新交易筐信息
						resultData=tradeInfo.getView_trade_baskcont();
					}else if(type.equals("trade-Lable-indu")){
						//获取新交易行业信息
						resultData=tradeInfo.getView_trade_inducont();
					}
				}else {
					message=CommonUtil.findNoteTxtOfXML("message_nullData");
				}
				
			}else {
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("TradeDetialAction.updateTradeInfoLabel() 发生异常", e);
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
			logger.error("TradeDetialAction.updateTradeInfoLabel() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	/**
	 * 添加交易备注信息
	 * @param request
	 * @param response
	 * @param info 接收备注内容,交易code
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 * @author duwenjie
	 */
	@RequestMapping(value = "addTradeNote")
	public void addOrganizationNote(HttpServletRequest request,
			HttpServletResponse response,BaseTradenoteInfo info,String logintype) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<BaseTradenoteInfo> noteList = null;//定义投资机构备注集合
		String message = "success";
		/* 添加投资机构note */
		try {
			//生成一个交易备注code
			String code=CommonUtil.PREFIX_TRADENOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.TRADENOTE_TYPE);
			Timestamp timestamp=CommonUtil.getNowTime_tamp();
			info.setBase_tradenote_code(code);
			info.setSys_user_name(userInfo.getSys_user_name());
			info.setCreateid(userInfo.getSys_user_code());
			info.setCreatetime(timestamp);
			info.setUpdid(userInfo.getSys_user_code());
			info.setUpdtime(timestamp);
			info.setDeleteflag("0");
			//添加备注
			tradenoteInfoService.insertBaseTradenoteInfo(info);
			
			
			/* 查询投资机构note*/
			noteList=tradenoteInfoService.findTradenoteByTradeCode(info.getBase_trade_code());
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("TradeDetialAction.addTradeNote() 发生异常", e);
			e.printStackTrace();
		}
		
		/* 返回交易备注集合 */
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
			logger.error("TradeDetialAction.addTradeNote() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
	
	
	
	/**
	 * 删除交易备注
	 * @param request
	 * @param response
	 * @param noteCode 备注code
	 * @param tradeCode 交易code
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteTradeNote")
	public void deleteTradeNote(HttpServletRequest request,
			HttpServletResponse response,String noteCode,String tradeCode,String logintype) throws Exception {
//		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<BaseTradenoteInfo> noteList = null;//定义投资机构备注集合
		String message = "success";
		
		try {
			//删除交易备注
			int data=tradenoteInfoService.deleteBaseTradenoteInfo(noteCode);
			/* 查询投资机构note*/
			noteList=tradenoteInfoService.findTradenoteByTradeCode(tradeCode);
			//判断已被删除
			if(data==0){
				message=CommonUtil.findNoteTxtOfXML("deleteNull");
			}
			
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("TradeDetialAction.deleteTradeNote() 发生异常",e);
			e.printStackTrace();
		}
		
		
		/* 返回交易备注集合 */
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
			logger.error("TradeDetialAction.deleteTradeNote() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
		
	}
	
	
	/**
	 * 删除机构交易融资信息
	 * @param request
	 * @param response
	 * @param refBool 判断标识(true:主页删除,false:子页删除)
	 * @param orgName 投资机构名称
	 * @param investor 投资人
	 * @param money 金额
	 * @param orgCode 机构code
	 * @param tradeCode 交易code
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param version 排他锁版本号
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteTradeInvesInfo")
	public void deleteTradeInvesInfo(HttpServletRequest request,
			HttpServletResponse response,Page page,Boolean refBool,
			String orgName,String investor,String money,
			String orgCode,String tradeCode,String logintype,Long version
			) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		List<Map<String, String>> tradeInfoList = null;//融资信息
		
		try {
			//删除融资信息
			int data=iBaseTradeInfoService.tranModifyDeleteTradeInvesInfo(userInfo, tradeCode, orgCode,version);
			if(data!=0){
				Timestamp timestamp = CommonUtil.getNowTime_tamp();
				int count=iTradeInfoService.findTradeInfoCountByTradeCode(tradeCode);
				if(count!=0){
					page.setTotalCount(count);
				}
				//查询融资信息
				tradeInfoList=iTradeInfoService.findTradeInfoByTradeCode(refBool==true?new Page():page, tradeCode);
				//交易信息(基础)
				BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(tradeCode);
				if(baseTradeInfo!=null){
					version=baseTradeInfo.getBase_datalock_viewtype();
				}
				
				/*添加融资金额修改系统更新记录*/
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
						"删除[投资机构:"+orgName+",投资人:"+investor+",金额:"+money+"]融资信息",
						"",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("TradeDetialAction.deleteTradeInvesInfo() 发生异常",e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("version", version);
			resultJSON.put("page",new JSONObject(page));
			resultJSON.put("list", JSONArray.fromObject(tradeInfoList));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.deleteTradeInvesInfo() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	 * 添加交易融资信息
	 * @param request
	 * @param response
	 * @param info 融资信息对象
	 * @param orgName 投资机构名称
	 * @param investor 投资人
	 * @param version 排他锁版本号
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	@RequestMapping(value = "insertTradeInvesInfo")
	public void insertTradeInvesInfo(HttpServletRequest request,
			HttpServletResponse response,Page page,BaseTradeInves info,
			String orgName,String investor,String logintype,Long version) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		boolean bool = true;//判断是否添加投资人及机构投资人关系
		baseInvestmentInfo orgInfo = null;//机构对象
		BaseInvestorInfo baseinvestor = null;//投资人
		List<Map<String, String>> tradeInfoList = null;//融资信息
		Timestamp timestamp = CommonUtil.getNowTime_tamp();
		try {
			//创建来源：1：IT桔子创建；2：易凯创建
			info.setBase_trade_stem("2");
			//当前状态：1：IT桔子创建；2：易凯创建；3：易凯修改；4：IT桔子修改；
			info.setBase_trade_estate("2");
			info.setCreateid(userInfo.getSys_user_code());
			info.setCreatetime(timestamp);
			info.setUpdid(userInfo.getSys_user_code());
			info.setUpdtime(timestamp);
			
			
			/*添加投资机构*/
			if(info.getBase_investment_code()==null||"".equals(info.getBase_investment_code().trim())){
				bool=false;
				//判断投资机构简称是否已存在
				message=baseInvestmentInfoService.findOnlyInvementName(null,orgName, null, null);
				if(message.equals("only")){
					message="success";
					timestamp = CommonUtil.getNowTime_tamp();
					String[] pinYin=CommonUtil.getPinYin(orgName);//获取机构名称全拼,简拼
					//生成投资机构code
					String code=CommonUtil.PREFIX_INVESTMENT+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTMENT_TYPE);
					orgInfo =new baseInvestmentInfo();
					orgInfo.setBase_investment_code(code);
					orgInfo.setBase_investment_name(orgName);
					orgInfo.setBase_investment_ename(null);
					orgInfo.setBase_investment_fullname(null);
					orgInfo.setBase_investment_stem("2");
					orgInfo.setBase_investment_estate("2");
					orgInfo.setBase_investment_namep(pinYin[0]);
					orgInfo.setBase_investment_namepf(pinYin[1]);
					orgInfo.setDeleteflag("0");
					orgInfo.setCreateid(userInfo.getSys_user_code());
					orgInfo.setCreatetime(timestamp);
					orgInfo.setUpdid(userInfo.getSys_user_code());
					orgInfo.setUpdtime(timestamp);
					orgInfo.setBase_datalock_viewtype(0);
					orgInfo.setBase_datalock_pltype("0");
					
					//添加投资机构信息
					baseInvestmentInfoService.tranModifyInsertInvestmentInfo(userInfo, orgInfo, null, null,null,null);
					
					info.setBase_investment_code(code);
					
					//duwenjie update date:2015-12-10
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							orgInfo.getBase_investment_code(), 
							orgInfo.getBase_investment_name(),
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("orgInfoAdd"),
							"",
							"添加投资机构:"+orgName,
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
					
				}
			}
			
			if(message.equals("success")){
				//添加投资人
				//zzg update date:2015-12-03
				if((info.getBase_investor_code()==null || "".equals(info.getBase_investor_code().trim()))
						&&(!investor.trim().equals("")&&investor.trim()!=null)){
					bool=false;
					baseinvestor = new BaseInvestorInfo();
			    	//投资人名称
		            baseinvestor.setBase_investor_name(investor.trim());
		            //生成投资人id
		            String investorcode=CommonUtil.PREFIX_INVESTOR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTOR_TYPE);
		            baseinvestor.setBase_investor_code(investorcode);
		            //投资人姓名拼音全拼,投资人姓名拼音首字母
		            String[] pinYin=CommonUtil.getPinYin(investor.trim());
		            //投资人姓名拼音全拼
		            baseinvestor.setBase_investor_namepf(pinYin[0]);
		            //投资人姓名拼音首字母
		            baseinvestor.setBase_investor_namep(pinYin[1]);
		            //创建来源
		            baseinvestor.setBase_investor_stem("2");
		        	//当前状态
		            baseinvestor.setBase_investor_estate("2");
		            //排它锁
		            baseinvestor.setBase_datalock_viewtype(1);
		        	//PL锁状态
		            baseinvestor.setBase_datalock_pltype("0");
		            //删除标识
		            baseinvestor.setDeleteflag("0");
		            //创建者
		            baseinvestor.setCreateid(userInfo.getSys_user_code());
		            //创建时间
		            baseinvestor.setCreatetime(timestamp);
		            //更新者
		            baseinvestor.setUpdid(userInfo.getSys_user_code());
		            //更新时间
		            baseinvestor.setUpdtime(timestamp);
		            
		            //赋值投资人ｃｏｄｅ
		            info.setBase_investor_code(investorcode);
		            }
				//投资人机构关系
				if(bool==false){
					//duwenjie add date:2015-12-10
					timestamp = CommonUtil.getNowTime_tamp();
					
					BaseInvestmentInvestor investorinfo=new BaseInvestmentInvestor();  
					//投资人id
                	investorinfo.setBase_investor_code(info.getBase_investor_code());
                					//投资机构id
                	investorinfo.setBase_investment_code(info.getBase_investment_code());
                					//在职状态
                	investorinfo.setBase_investor_state("0");
                					//删除标识
                	investorinfo.setDeleteflag("0");
            					//创建者
                	investorinfo.setCreateid(userInfo.getSys_user_code());
            					//创建时间
                	investorinfo.setCreatetime(timestamp);
            					//更新者
                	investorinfo.setUpdid(userInfo.getSys_user_code());
                					//更新时间
                	investorinfo.setUpdtime(timestamp);
		            
                	//添加投资人，投资机构投资人信息
		            iBaseInvestorInfoService.tranModifyInsertBaseInvestorOrBetweenInvestment(baseinvestor, investorinfo);
				
		            //duwenjie update date:2015-12-10
		            /*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-investment"),
							CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
							info.getBase_investment_code(), 
							orgName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("addInvestor"),
							"",
							"添加投资人："+investor,
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
		            
				}
				
			}
			if(message.equals("success")){
				//添加融资信息
				int	data=iBaseTradeInfoService.tranModifyInsertBaseTradeInvesInfo(userInfo, info,version);
				if(data!=0){
					int count=iTradeInfoService.findTradeInfoCountByTradeCode(info.getBase_trade_code());
					page.setTotalCount(count);
					//查询融资信息
					tradeInfoList=iTradeInfoService.findTradeInfoByTradeCode(new Page(), info.getBase_trade_code());
					
					BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(info.getBase_trade_code());
					if(baseTradeInfo!=null){
						version=baseTradeInfo.getBase_datalock_viewtype();
					}
					//duwenjie add date:2015-12-10
					timestamp=CommonUtil.getNowTime_tamp();
					/*添加融资金额修改系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							info.getBase_trade_code(), 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
							CommonUtil.findNoteTxtOfXML("tradeInvesInfoAdd"),
							"",
							"添加[投资机构:"+(orgName.equals("")?"空":orgName)+",投资人:"
							+(investor.equals("")?"空":investor)+",金额:"
							+((info.getBase_trade_inmoney().equals("")||info.getBase_trade_inmoney()==null)?"空":info.getBase_trade_inmoney())
							+"]融资信息",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}else{
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}
			
			
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("saveFail");
			logger.error("TradeDetialAction.insertTradeInvesInfo() 发生异常",e);
			e.printStackTrace();
		}
		
		/* 返回融资信息集合 */
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("version", version);
			resultJSON.put("list", JSONArray.fromObject(tradeInfoList));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.insertTradeInvesInfo() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	 * 分页查询融资信息
	 * @param request
	 * @param response
	 * @param page 分页对象
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param tradeCode 交易code
	 * @throws Exception
	 */
	@RequestMapping(value = "findPageTradeInvesInfo")
	public void findPageTradeInvesInfo(HttpServletRequest request,
			HttpServletResponse response,Page page,String logintype,String tradeCode) throws Exception {
//		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		List<Map<String, String>> tradeInfoList = null;//融资信息
		
		try {
			//查询交易数量
			int count=iTradeInfoService.findTradeInfoCountByTradeCode(tradeCode);
			if(count!=0){
				page.setTotalCount(count);
				tradeInfoList=iTradeInfoService.findTradeInfoByTradeCode(page, tradeCode);
			}
			
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("selectFail");
			logger.error("TradeDetialAction findPageTradeInvesInfo() 发生异常", e);
			e.printStackTrace();
		}
		
		
		/* 返回融资信息集合 */
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("page", new JSONObject(page));
			resultJSON.put("list", JSONArray.fromObject(tradeInfoList));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction findPageTradeInvesInfo() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	/**
	 * 删除交易信息
	 * @param request
	 * @param response
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param tradeCode 交易code
	 * @param version 排他锁版本号
	 * @param orgCodeString 机构code串
	 * @param tradeDate 交易日期
	 * @param companyName 公司名称
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteTradeInfo")
	public void deleteTradeInfo(HttpServletRequest request,
			HttpServletResponse response,String logintype,String tradeCode,
			String version,String orgCodeString,String tradeDate,String companyName) throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		try {
			int data=iBaseTradeInfoService.tranDeleteTradeInfoByTradeCode(
					userInfo.getSys_user_code(), tradeCode, version, orgCodeString);
			if(data>0){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
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
						CommonUtil.findNoteTxtOfXML("tradeInfoDelete"),
						"删除[公司:"+companyName+",交易日期:"+tradeDate+"]交易记录",
						"",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("TradeDetialAction.deleteTradeInfo() 发生异常", e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.deleteTradeInfo() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	/**
	 * 修改交易融资信息
	 * @author zzg
	 * @date 2015-12-02
	 * @param request
	 * @param response
	 * @param logintype
	 * @param jsonobject
	 * @throws Exception
	 */
	@RequestMapping(value = "update_list_tade")
	public void update_list_tade(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			String jsonobject,String olddata) throws Exception{
		logger.info("TradeDetialAction.update_list_tade()方法开始");
		JSONObject json=new JSONObject(jsonobject);
		JSONObject oldjson=new JSONObject(olddata);
		String message="error";
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		try {
			//查询融资信息详情
			BaseTradeInves bastradinfo=new BaseTradeInves();

			HashMap<String, String> map=new HashMap<String, String>();
			map.put("tradecode", json.getString("tradecode"));
			map.put("base_investment_code", json.getString("base_investment_code"));
			bastradinfo=iBaseTradeInfoService.findBaseTradeInvesByCode(map);
			BaseInvestorInfo baseinvestor=null;
			BaseInvestmentInvestor investorinfo=null; 
			if (bastradinfo!=null) {
				//投资人姓名为空
				if (json.get("base_investor_name").equals("")) {
					bastradinfo.setBase_investor_code(null);
				}else  {
					if (json.get("base_investor_code").equals("")) {
						baseinvestor=new BaseInvestorInfo();
						investorinfo=new BaseInvestmentInvestor();
						//创建新的联系人
						baseinvestor.setBase_investor_name(json.get("base_investor_name").toString());
                		//生成投资人id
          String investorcode=CommonUtil.PREFIX_INVESTOR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTOR_TYPE);
          baseinvestor.setBase_investor_code(investorcode);
                		//投资人姓名拼音全拼,投资人姓名拼音首字母
          String[] pinYin=CommonUtil.getPinYin(baseinvestor.getBase_investor_name());
			//投资人姓名拼音全拼
          baseinvestor.setBase_investor_namep(pinYin[0]);
            			//投资人姓名拼音首字母
         baseinvestor.setBase_investor_namepf(pinYin[1]);
                		//创建来源
          baseinvestor.setBase_investor_stem("2");
    			//当前状态
          baseinvestor.setBase_investor_estate("2");
                		//排它锁
         baseinvestor.setBase_datalock_viewtype(1);
    		//PL锁状态
         baseinvestor.setBase_datalock_pltype("0");
                		//删除标识
         baseinvestor.setDeleteflag("0");
            		//创建者
          baseinvestor.setCreateid(userInfo.getSys_user_code());
            		//创建时间
          baseinvestor.setCreatetime(CommonUtil.getNowTime_tamp());
            		//更新者
          baseinvestor.setUpdid(userInfo.getSys_user_code());
                		//更新时间
          baseinvestor.setUpdtime(baseinvestor.getCreatetime());
          bastradinfo.setBase_investor_code(investorcode);
          
          		//投资人和机构的关系
        //投资人id
      	investorinfo.setBase_investor_code(baseinvestor.getBase_investor_code());
      					//投资机构id
      	investorinfo.setBase_investment_code(json.getString("base_investment_code"));
      					//在职状态
      	investorinfo.setBase_investor_state("0");
      					//删除标识
      	investorinfo.setDeleteflag("0");
  					//创建者
      	investorinfo.setCreateid(userInfo.getSys_user_code());
  					//创建时间
      	investorinfo.setCreatetime(CommonUtil.getNowTime_tamp());
  					//更新者
      	investorinfo.setUpdid(userInfo.getSys_user_code());
      					//更新时间
      	investorinfo.setUpdtime(investorinfo.getCreatetime());
					}else {
						bastradinfo.setBase_investor_code(json.get("base_investor_code").toString());
					}
				}
				//领投
				bastradinfo.setBase_trade_collvote(json.get("base_trade_collvote").toString());
				//对赌
				bastradinfo.setBase_trade_ongam(json.get("base_trade_ongam").toString());
				//分次付款
				bastradinfo.setBase_trade_subpay(json.get("base_trade_subpay").toString());
				bastradinfo.setBase_trade_inmoney(json.get("base_trade_inmoney").toString());
			}else {
				message="notrade";
			}
		boolean br=	iBaseTradeInfoService.tranModifyUpdate_trade(bastradinfo,baseinvestor,investorinfo,userInfo,json.getString("version"));
			if (br) {
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*删除交易信息系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-trading"),
						CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
						json.getString("tradecode"), 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
						CommonUtil.findNoteTxtOfXML("tradeInfoEdit"),
						"融资信息[机构:"+oldjson.getString("base_investment_name")+";投资人:"+oldjson.getString("base_investor_name")+
						";金额:"+oldjson.getString("base_trade_inmoney")+";领头:"+isornot(oldjson.getString("base_trade_collvote"))+";分期:"+
						isornot(oldjson.getString("base_trade_subpay"))+";对赌:"+isornot(oldjson.getString("base_trade_ongam"))+"]",
						"　修改为[机构:"+json.getString("base_investment_name")+";投资人:"+json.getString("base_investor_name")+
						";金额:"+json.getString("base_trade_inmoney")+";领头:"+isornot(json.getString("base_trade_collvote"))+";分期:"+
						isornot(json.getString("base_trade_subpay"))+";对赌:"+isornot(json.getString("base_trade_ongam"))+"]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
				
				
				message="success";
			}else {
				message="havechange";
			}
		
		} catch (Exception e) {
			logger.error("TradeDetialAction.update_list_tade()方法开始",e);
			e.printStackTrace();
		}
		logger.info("TradeDetialAction.update_list_tade()方法结束");
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("TradeDetialAction.update_list_tade()方法开始",e);
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	private static String isornot(String str) {
		if (str.equals("0")) {
			return "是";
		}else if(str.equals("1")){
			return "否";
		}
		
		return str;
	}
	
}