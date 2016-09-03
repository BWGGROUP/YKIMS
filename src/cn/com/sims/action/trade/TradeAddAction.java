package cn.com.sims.action.trade;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.tradeinfo.ITradeAddService;


/**
 * 
 * @author rqq
 * @date 2015-11-02
 */
@Controller
public class TradeAddAction {
	
	//添加交易service
	@Resource
	ITradeAddService tradeaddservice;
	
	//投资人
	@Resource
	InvestorService investorService;
	
	//公司
	@Resource
	ICompanyDetailService companyDetailService;
	
	//字典
	@Resource
	ISysLabelelementInfoService dic;
	
	//生成id
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
	
	//系统更新记录
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;
	
	//投资机构
	@Resource
	IBaseInvestmentInfoService baseinvestmentinfoservice;
	
	private static final Logger logger = Logger.getLogger(TradeAddAction.class);
	
	
	/**
	 * 添加交易初始化信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @param investmentcode:机构Code
	 * 	@param compcode:公司Code
	 * @param backherf:返回地址
	 * @return
	 * @throws Exception
	 * @author rqq
	 */
	@RequestMapping(value = "addTradeInfoinit")
	public String addTradeInfoinit(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="investmentcode",required=false) String investmentcode,
			@RequestParam(value="compcode",required=false) String compcode,
			@RequestParam(value="backherf",required=false) String backherf,
			@RequestParam(value="investorcode",required=false) String investorcode//投资人code
			) throws Exception {
		logger.info("TradeAddAction.addTradeInfoinit()方法开始");
		//初始化：字典 关注筐
		List<Map<String, String>> baskList=null;
		//初始化：字典 行业
		List<Map<String, String>> induList=null;
		//初始化：字典 阶段
		List<Map<String, String>> stageList=null;
		//初始化：选中机构
		baseInvestmentInfo baseinvestmentinfo=null;
		//初始化：选中公司（业务数据）
		viewCompInfo viewCompInfo = null;
		//初始化：选中投资人
		viewInvestorInfo viewInvestorInfo=null;
		try {
			//查询筐
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));
			//查询行业
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));
			//查询投资阶段
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-trastage"));
			//查询选中公司
			if(compcode!=null && !"".equals(compcode)){
				//根据公司code查询公司信息
				viewCompInfo=companyDetailService.findCompanyDeatilByCode(compcode);
			}
			//2015-11-30 TASK078 RQQ ModStart
			//查询选中的机构
        	if(investmentcode!=null && !"".equals(investmentcode)){
        			    baseinvestmentinfo=baseinvestmentinfoservice.findBaseInvestmentByCode(investmentcode);//投资机构详情(基础层)
        			}
        	//2015-11-30 TASK078 RQQ ModEnd
        	//2015-12-10 zzg TASK081 AddStart
        	if(investorcode!=null&&!"".equals(investorcode)){
        		viewInvestorInfo=investorService.findInvestorDeatilByCode(investorcode);
        		}
        	//2015-12-10 zzg TASK081 AddEnd
        	
		} catch (Exception e) {
			logger.error("TradeAddAction.addTradeInfoinit()方法异常",e);
			e.printStackTrace();
		}
		
		request.setAttribute("baskList", JSONArray.fromObject(baskList));
		request.setAttribute("induList", JSONArray.fromObject(induList));
		request.setAttribute("stageList", JSONArray.fromObject(stageList));
		request.setAttribute("baseinvestmentinfo", net.sf.json.JSONObject.fromObject(baseinvestmentinfo));
		request.setAttribute("viewCompInfo", net.sf.json.JSONObject.fromObject(viewCompInfo));
		request.setAttribute("viewInvestorInfo", net.sf.json.JSONObject.fromObject(viewInvestorInfo));
		//2015-11-30 TASK078 RQQ AddStart
		request.setAttribute("backherf", backherf);
		//2015-11-30 TASK078 RQQ AddEnd
		logger.info("TradeAddAction.addTradeInfoinit()方法结束");
		return ConstantUrl.urlTradeAdd(logintype);
	}
	
	/**选择机构时-模糊匹配机构名称，英文名拼音，并实现加载更多
	 * @author rqq
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param name
	 * @param pagenow
	 * @param limit
	 * @throws Exception
	 */
	@RequestMapping(value = "queryinvestmentlistByname")
	public void queryinvestmentlistByname(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("name") String name,
			Page page,String logintype) throws Exception {
		logger.info("TradeAddAction.queryinvestmentlistByname()方法Start");
	
		String message="";
		List<viewInvestmentInfo> list = null;
		try {
		    Map<String, Object> map=new HashMap<String, Object>();
			if(name==null ||"".equals(name)){
				map.put("name", null);
			}else {
				map.put("name", name);
			}
			int total=tradeaddservice.queryInvestmentlistnumByOrgname(map);
			page.setTotalCount(total);
			map.put("pagestart", page.getStartCount());
			map.put("limit",page.getPageSize());
			list=tradeaddservice.queryInvestmentlistByOrgname(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
			logger.info("TradeAddAction.queryinvestmentlistByname方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.queryinvestmentlistByname 发生异常", e);
			e.printStackTrace();
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(list);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("TradeAddAction.queryinvestmentlistByname 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.queryinvestmentlistByname 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**选择公司时-模糊匹配公司名称，英文名拼音，并实现加载更多
	 * @author rqq
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param name
	 * @param pagenow
	 * @param limit
	 * @throws Exception
	 */
	@RequestMapping(value = "querycompanylistByname")
	public void querycompanylistByname(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("name") String name,
			Page page,String logintype) throws Exception {
		logger.info("TradeAddAction.querycompanylistByname()方法Start");
	
		String message="";
		List<viewCompInfo> list = null;
		try {
		    Map<String, Object> map=new HashMap<String, Object>();
			if(name==null ||"".equals(name)){
				map.put("name", null);
			}else {
				map.put("name", name);
			}
			int total=tradeaddservice.querycompanylistnumByname(map);
			page.setTotalCount(total);
			map.put("pagestart", page.getStartCount());
			map.put("limit",page.getPageSize());
			list=tradeaddservice.querycompanylistByname(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
			logger.info("TradeAddAction.querycompanylistByname方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.querycompanylistByname 发生异常", e);
			e.printStackTrace();
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(list);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("TradeAddAction.querycompanylistByname 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.querycompanylistByname 发生异常", e);
			e.printStackTrace();
		}
	}
	/**选择投资人时-根据投资机构查询，并实现加载更多
	 * @author rqq
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param invementcode:机构code
	 * @param name:投资人名称
	 * @param pagenow
	 * @param limit
	 * @throws Exception
	 */
	@RequestMapping(value = "queryinvestorlistByinvementcode")
	public void queryinvestorlistByinvementcode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("invementcode") String invementcode,
			@RequestParam("name") String name,
			Page page,String logintype) throws Exception {
		logger.info("TradeAddAction.queryinvestorlistByinvementcode()方法Start");
	
		String message="";
		List<viewInvestorInfo> list = null;
		try {
		    Map<String, Object> map=new HashMap<String, Object>();
		    //机构code
			if(invementcode==null ||"".equals(invementcode)){
				map.put("invementcode", null);
			}else {
				map.put("invementcode", invementcode);
			}
			//名称
			if(name==null ||"".equals(name)){
				map.put("name", null);
			}else {
				map.put("name", name);
			}
			
			int total=tradeaddservice.queryInvestorlistnumByOrgId(map);
			page.setTotalCount(total);
			map.put("pagestart", page.getStartCount());
			map.put("limit",page.getPageSize());
			list=tradeaddservice.queryInvestorlistByOrgId(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
			logger.info("TradeAddAction.queryinvestorlistByinvementcode方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.queryinvestorlistByinvementcode 发生异常", e);
			e.printStackTrace();
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(list);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("TradeAddAction.queryinvetorlistByinvementcode 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.queryinvetorlistByinvementcode 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**添加机构判断机构是否唯一
	 * @author rqq
	 * @date 2015-11-04
	 * @param request
	 * @param response
	 * @param name：机构简称
	 * @throws Exception
	 */
	@RequestMapping(value = "queryInvestmentOnlyNamefottrade")
	public void queryInvestmentOnlyNamefottrade(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("name") String name,
			String logintype) throws Exception {
		logger.info("TradeAddAction.queryInvestmentOnlyNamefottrade()方法Start");
	
		String message="";
		try {
		   
			int total=tradeaddservice.queryInvestmentOnlyNamefottrade(name);
			
			if (total>0) {
				message="exsit";
			}else {
				message="nodata";
			}
			logger.info("TradeAddAction.queryInvestmentOnlyNamefottrade方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.queryInvestmentOnlyNamefottrade 发生异常", e);
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
			logger.error("TradeAddAction.queryInvestmentOnlyNamefottrade 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.queryInvestmentOnlyNamefottrade 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**判断机构投资人是否唯一
	 * @author dwj
	 * @date 2016-5-13
	 * @param request
	 * @param response
	 * @param orgcode：投资机构 code
	 * @param name：投资人名称
	 * @throws Exception
	 */
	@RequestMapping(value = "queryOrgInvestorOnlyNamefottrade")
	public void queryOrgInvestorOnlyNamefottrade(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="orgcode",required=true)String orgcode,
			@RequestParam(value="name",required=true)String name,
			@RequestParam(value="logintype",required=true)String logintype) throws Exception {
		String message="";
		BaseInvestorInfo info=null;
		try {
			Map<String, String> map=new HashMap<String, String>();
			map.put("orgcode", orgcode);
			map.put("name", name);
			info=baseinvestmentinfoservice.findOrgInvestorByName(map);
			if (info!=null) {
				message="exsit";
			}else {
				message="nodata";
			}
		} catch (Exception e) {
			message="error";
			logger.error("TradeAddAction.queryOrgInvestorOnlyNamefottrade 发生异常", e);
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
			logger.error("TradeAddAction.queryOrgInvestorOnlyNamefottrade 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.queryOrgInvestorOnlyNamefottrade 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**添加机构判断公司是否唯一
	 * @author rqq
	 * @date 2015-11-04
	 * @param request
	 * @param response
	 * @param name：公司名称
	 * @throws Exception
	 */
	@RequestMapping(value = "queryCompOnlyNamefortrade")
	public void queryCompOnlyNamefortrade(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("name") String name,
			String logintype) throws Exception {
		logger.info("TradeAddAction.queryCompOnlyNamefortrade()方法Start");
	
		String message="";
		try {
		   
			int total=tradeaddservice.queryCompOnlyNamefortrade(name);
			
			if (total>0) {
				message="exsit";
			}else {
				message="nodata";
			}
			logger.info("TradeAddAction.queryCompOnlyNamefortrade方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.queryCompOnlyNamefortrade 发生异常", e);
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
			logger.error("TradeAddAction.queryCompOnlyNamefortrade 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.queryCompOnlyNamefortrade 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**添加交易信息
	 * @author rqq
	 * @date 2015-11-04
	 * @param request
	 * @param response
	 * @param tradedate：交易日期
	 * @param compinfo：公司信息:compcode:公司code;compname:公司名称；opertype：操作类型：select,add;
	 * @param basklist：筐信息
	 * @param indulist：行业信息
	 * @param stageList：阶段信息
	 * @param tradeInfoList：交易信息base_investment_code:机构code;base_investment_name:机构name;
	 * investmenttype:机构添加类型;base_investor_code:投资人code;base_investor_name:投资人name;
	 * investortype:投资人类型;base_trade_inmoney:金额;base_trade_collvote:是否领投;
	 * base_trade_ongam:是否对赌;base_trade_subpay:是否分次付款;
	 * @param trademoney：融资金额
	 * @param tradecomnum：公司估值
	 * @param tradecomnumtype：公司估值类型
	 * @param noteinfo：交易备注
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "addtradeinfo")
	public void addtradeinfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("tradedate") String tradedate,
			@RequestParam(value="compinfo",required=false) String compinfo,
			@RequestParam(value="basklist",required=false) String basklist,
			@RequestParam(value="indulist",required=false) String indulist,
			@RequestParam(value="stageList",required=false) String stageList,
			@RequestParam(value="tradeInfoList",required=false) String tradeInfoList,
			@RequestParam(value="trademoney",required=false) String trademoney,
			@RequestParam(value="tradecomnum",required=false) String tradecomnum,
			@RequestParam(value="tradecomnumtype",required=false) String tradecomnumtype,
			@RequestParam(value="noteinfo",required=false) String noteinfo,
			String logintype) throws Exception {
		logger.info("TradeAddAction.addtradeinfo()方法Start");
	
		String message="success";
		String messagedetail="";
		try {
		    SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		    	//公司对象
			BaseCompInfo basecompinfo=null;
			//机构list;
			List<baseInvestmentInfo> baseinvestmentinfolist=new ArrayList<baseInvestmentInfo>();
			//投资人list
			List<BaseInvestorInfo> baseinvestorinfolist=new ArrayList<BaseInvestorInfo>();
			//机构投资人关系list
			List<BaseInvestmentInvestor> baseinvestmentinvestorlist
								=new ArrayList<BaseInvestmentInvestor>();
			//交易对象
			BaseTradeInfo basetradeinfo=null;
			//交易标签list;
			List<BaseTradelabelInfo> basetradelabellist
								=new ArrayList<BaseTradelabelInfo>();
			//机构交易list
			List<BaseTradeInves> basetradeinveslist
								=new ArrayList<BaseTradeInves>();
			//交易note对象
			BaseTradenoteInfo basetradenoteinfo=null;
			
		   JSONArray carray=JSONArray.fromObject(compinfo);
			JSONArray barray=JSONArray.fromObject(basklist);
			JSONArray iarray=JSONArray.fromObject(indulist);
			JSONArray sarray=JSONArray.fromObject(stageList);
			JSONArray tradearray=JSONArray.fromObject(tradeInfoList);
			List<Map<String, String>> blist=barray.subList(0, barray.size());
			List<Map<String, String>> ilist=iarray.subList(0, iarray.size());
			List<Map<String, String>> slist=sarray.subList(0, sarray.size());
			List<Map<String, String>> tradeinvesList=tradearray.subList(0, tradearray.size());
			List<Map<String, String>> compinfoList=carray.subList(0, carray.size());
			//验证公司添加项目简称判重
			if(compinfoList.size()>0){
			    Map<String, String> compmapMap=compinfoList.get(0);
			    	if("add".equals(compmapMap.get("opertype"))){
			    	int total=tradeaddservice.queryCompOnlyNamefortrade(compmapMap.get("compname"));
        				if (total>0) {
        					message="compnameexsit";
        				}
        				else{
        				    basecompinfo=new BaseCompInfo();
        				    //公司name
        				    basecompinfo.setBase_comp_name(compmapMap.get("compname"));
							//公司简称拼音全拼,公司简称拼音首字母
        				    String[] pinYin=CommonUtil.getPinYin(basecompinfo.getBase_comp_name());
                					//公司简称拼音全拼
        				    basecompinfo.setBase_comp_namep(pinYin[0]);
                					//公司简称拼音首字母
        				    basecompinfo.setBase_comp_namef(pinYin[1]);
        				  //生成公司id
        	              String compcode=CommonUtil.PREFIX_COMP+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANY_TYPE);
        	              basecompinfo.setBase_comp_code(compcode);
        	              			//创建来源
                  		basecompinfo.setBase_comp_stem("2");
      							//当前状态
                  		basecompinfo.setBase_comp_estate("2");
                  					//排它锁
                  		basecompinfo.setBase_datalock_viewtype(1);
      						//PL锁状态
                  		basecompinfo.setBase_datalock_pltype("0");
                  					//删除标识
                  		basecompinfo.setDeleteflag("0");
              						//创建者
                  		basecompinfo.setCreateid(userInfo.getSys_user_code());
              						//创建时间
                  		basecompinfo.setCreatetime(CommonUtil.getNowTime_tamp());
              						//更新者
                  		basecompinfo.setUpdid(userInfo.getSys_user_code());
              			 			//更新时间
                  		basecompinfo.setUpdtime(basecompinfo.getCreatetime());
        				}
			    	}
			    	
			}
			
			if("success".equals(message)){
			/**交易对象**/
			//添加交易信息
			//生成交易id
			basetradeinfo=new BaseTradeInfo();
          String tradecode=CommonUtil.PREFIX_TRADE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.TRADE_TYPE);
          basetradeinfo.setBase_trade_code(tradecode);
          if(compinfoList.get(0).get("opertype")!=null
	    		&& "select".equals(compinfoList.get(0).get("opertype").toString().trim())){
            		//公司id
          basetradeinfo.setBase_comp_code(compinfoList.get(0).get("compcode").toString().trim());
          		}
          else if(compinfoList.get(0).get("opertype")!=null
	    		&& "add".equals(compinfoList.get(0).get("opertype").toString().trim())){
              		//公司id
              basetradeinfo.setBase_comp_code(basecompinfo.getBase_comp_code());
          		}
          		//投资日期
          basetradeinfo.setBase_trade_date(tradedate);
          if(stageList==null || "".equals(stageList) || "[]".equals(stageList) ){
          		//阶段
          basetradeinfo.setBase_trade_stage(null);
          }else{
              		//阶段
          basetradeinfo.setBase_trade_stage(slist.get(0).get("code").toString().trim());
          		}
          		//融资金额
          if(trademoney==null || "".equals(trademoney)){
              basetradeinfo.setBase_trade_money(null);
              		}
          else{
          basetradeinfo.setBase_trade_money(trademoney);
          		}
          		//公司估值
          if(tradecomnum==null || "".equals(tradecomnum)){
              basetradeinfo.setBase_trade_comnum(null);
              		}
         else{
          basetradeinfo.setBase_trade_comnum(tradecomnum);
              		}
          /** 2015-11-30 TASK072 RQQ AddStart**/
			//公司估值类型
          if(tradecomnum==null || "".equals(tradecomnum)
        	  || tradecomnumtype==null || "".equals(tradecomnumtype)){
          basetradeinfo.setBase_trade_comnumtype(null);
          		}
          else{
          basetradeinfo.setBase_trade_comnumtype(tradecomnumtype);
          		}
          /** 2015-11-30 TASK072 RQQ AddEnd**/
          		//创建来源
			basetradeinfo.setBase_trade_stem("2");
			//当前状态
			basetradeinfo.setBase_trade_estate("2");
			//排它锁
			basetradeinfo.setBase_datalock_viewtype(1);
			//PL锁状态
			basetradeinfo.setBase_datalock_pltype("0");
			//删除标识
			basetradeinfo.setDeleteflag("0");
			//创建者
			basetradeinfo.setCreateid(userInfo.getSys_user_code());
			//创建时间
			basetradeinfo.setCreatetime(CommonUtil.getNowTime_tamp());
			//更新者
			basetradeinfo.setUpdid(userInfo.getSys_user_code());
			//更新时间
			basetradeinfo.setUpdtime(basetradeinfo.getCreatetime());
			/**交易标签**/
			
			//筐
			if(basklist!=null && !"".equals(basklist) || "[]".equals(basklist) ){
			    for(int i=0;i<blist.size();i++){
				BaseTradelabelInfo basetradelabelinfo= new BaseTradelabelInfo();
				Map<String, String> baskmapMap=blist.get(i);
				if(baskmapMap.get("code")!=null){
				//交易id
			    	basetradelabelinfo.setBase_trade_code(basetradeinfo.getBase_trade_code());
					//标签元素code
			    	basetradelabelinfo.setSys_labelelement_code(baskmapMap.get("code").toString().trim());
					//标签元素code
			    	basetradelabelinfo.setSys_label_code(CommonUtil.findNoteTxtOfXML("Lable-bask"));
					//删除标识
			    	basetradelabelinfo.setDeleteflag("0");
					//创建者
			    	basetradelabelinfo.setCreateid(userInfo.getSys_user_code());
					//创建时间
			    	basetradelabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
					//更新者
			    	basetradelabelinfo.setUpdid(userInfo.getSys_user_code());
					//更新时间
			    	basetradelabelinfo.setUpdtime(basetradelabelinfo.getCreatetime());
					//放置list
			    	basetradelabellist.add(basetradelabelinfo);
			    }
			}
			}
			
			//行业
			if(indulist!=null && !"".equals(indulist) || "[]".equals(indulist) ){
			    for(int i=0;i<ilist.size();i++){
				BaseTradelabelInfo basetradelabelinfo= new BaseTradelabelInfo();
				Map<String, String> indumapMap=ilist.get(i);
				if(indumapMap.get("code")!=null){
				//交易id
			    	basetradelabelinfo.setBase_trade_code(basetradeinfo.getBase_trade_code());
					//标签元素code
			    	basetradelabelinfo.setSys_labelelement_code(indumapMap.get("code").toString().trim());
					//标签元素code
			    	basetradelabelinfo.setSys_label_code(CommonUtil.findNoteTxtOfXML("Lable-comindu"));
					//删除标识
			    	basetradelabelinfo.setDeleteflag("0");
					//创建者
			    	basetradelabelinfo.setCreateid(userInfo.getSys_user_code());
					//创建时间
			    	basetradelabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
					//更新者
			    	basetradelabelinfo.setUpdid(userInfo.getSys_user_code());
					//更新时间
			    	basetradelabelinfo.setUpdtime(basetradelabelinfo.getCreatetime());
					//放置list
			    	basetradelabellist.add(basetradelabelinfo);
			    }
			}
			}
			
			if(tradeInfoList!=null && !"".equals(tradeInfoList) ){
			/**交易机构list**/
			//验证交易的机构信息
			for(int i=0;i<tradeinvesList.size();i++){
			    
			    Map<String, String> invemmapMap=tradeinvesList.get(i);
			    baseInvestmentInfo baseinves = new baseInvestmentInfo();
			    BaseInvestorInfo baseinvestor=new BaseInvestorInfo();
			    BaseInvestmentInvestor investorinfo=new BaseInvestmentInvestor();  
			    BaseTradeInves basetradeinves=new BaseTradeInves();
			    //投资机构
			    if(invemmapMap.get("investmenttype")!=null 
			    		&& "add".equals(invemmapMap.get("investmenttype").toString().trim())
			    		&& invemmapMap.get("base_investment_name")!=null
			    		&& !"".equals(invemmapMap.get("base_investment_name").toString().trim())){
			    	int total=tradeaddservice.queryInvestmentOnlyNamefottrade(invemmapMap.get("base_investment_name"));
        				if (total>0) {
        					message="invesnameexsit";
        					messagedetail="机构简称："+invemmapMap.get("base_investment_name")+"已存在";
        					break;
        				}
        				else{
        				    // 机构简称
        				    baseinves.setBase_investment_name(invemmapMap.get("base_investment_name").toString().trim());
        				    // 生成投资机构id
        				    String invementcode = CommonUtil.PREFIX_INVESTMENT
        					    + serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM,
        						    CommonUtil.INVESTMENT_TYPE);
        				    baseinves.setBase_investment_code(invementcode);
        				    // 机构简称拼音全拼,机构简称拼音首字母
        				    String[] pinYin = CommonUtil.getPinYin(baseinves.getBase_investment_name());
        				    // 机构简称拼音全拼
        				    baseinves.setBase_investment_namep(pinYin[0]);
        				    // 机构简称拼音首字母
        				    baseinves.setBase_investment_namepf(pinYin[1]);
        				    // 创建来源
        				    baseinves.setBase_investment_stem("2");
        				    // 当前状态
        				    baseinves.setBase_investment_estate("2");
        				    // 排它锁
        				    baseinves.setBase_datalock_viewtype(1);
        				  // PL锁状态
        				    baseinves.setBase_datalock_pltype("0");
        				    // 删除标识
        				    baseinves.setDeleteflag("0");
        				    // 创建者
        				    baseinves.setCreateid(userInfo.getSys_user_code());
        				    // 创建时间
        				    baseinves.setCreatetime(CommonUtil.getNowTime_tamp());
        				    // 更新者
        				    baseinves.setUpdid(userInfo.getSys_user_code());
        				    // 更新时间
        				    baseinves.setUpdtime(baseinves.getCreatetime());
        				    baseinvestmentinfolist.add(baseinves);
        				}
			    	}
			    else if(invemmapMap.get("investmenttype")!=null 
			    		&& "select".equals(invemmapMap.get("investmenttype").toString().trim())
			    		&&  invemmapMap.get("base_investment_code")!=null 
			    		&& !"".equals(invemmapMap.get("base_investment_code").toString().trim())
			    			){
				//机构code
			    	baseinves.setBase_investment_code(invemmapMap.get("base_investment_code").toString().trim());
			    		//机构简称
			    	baseinves.setBase_investment_name(invemmapMap.get("base_investment_name").toString().trim());
			    	}
			    	
			    	
			    	//投资人
			    if(invemmapMap.get("investortype")!=null 
			    		&& "add".equals(invemmapMap.get("investortype").toString().trim())
			    		&&  invemmapMap.get("base_investor_name")!=null 
		    			&& !"".equals(invemmapMap.get("base_investor_name").toString().trim())){
			    	//投资人名称
	               baseinvestor.setBase_investor_name(invemmapMap.get("base_investor_name").toString().trim());
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
	              baseinvestorinfolist.add(baseinvestor);
			    		}
			    	else	if(invemmapMap.get("investortype")!=null 
			    		&& "select".equals(invemmapMap.get("investortype").toString().trim())
			    		&&  invemmapMap.get("base_investor_code")!=null 
		    			&& !"".equals(invemmapMap.get("base_investor_code").toString().trim())
		    			){
			    	   //投资人code
			    	baseinvestor.setBase_investor_code(invemmapMap.get("base_investor_code"));
			    	  //投资人姓名
			    	baseinvestor.setBase_investor_name(invemmapMap.get("base_investor_name"));
			    		}
			    	
			    	//投资人机构关系
			    if(((invemmapMap.get("investmenttype")!=null 
			    		&&"add".equals(invemmapMap.get("investmenttype").toString().trim()))
				    || (invemmapMap.get("investortype")!=null 
				    	&&"add".equals(invemmapMap.get("investortype").toString().trim()))
				    	&& baseinves.getBase_investment_code()!=null
				    	&& !"".equals(baseinves.getBase_investment_code())
				    	&& baseinvestor.getBase_investor_code()!=null
				    	&& !"".equals(baseinvestor.getBase_investor_code()))
				    		){
							//投资人id
                    	investorinfo.setBase_investor_code(baseinvestor.getBase_investor_code());
                    					//投资机构id
                    	investorinfo.setBase_investment_code(baseinves.getBase_investment_code());
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
                    	
                    	investorinfo.setBase_investment_name(baseinves.getBase_investment_name());
                    	investorinfo.setBase_investor_name(baseinvestor.getBase_investor_name());
                    	baseinvestmentinvestorlist.add(investorinfo);
                    	
				    		}
			    /**机构交易**/
			    if(basetradeinfo.getBase_trade_code()!=null 
				    && !"".equals(basetradeinfo.getBase_trade_code())
				    && baseinves.getBase_investment_code()!=null 
				    && !"".equals(baseinves.getBase_investment_code())){
			    //交易id
			    basetradeinves.setBase_trade_code(basetradeinfo.getBase_trade_code());
			  //投资机构id
			    basetradeinves.setBase_investment_code(baseinves.getBase_investment_code());
			//投资人id
    		   if(baseinvestor!=null && !"".equals(baseinvestor) && baseinvestor.getBase_investor_code()!=null){
        				//投资人id
    		    	basetradeinves.setBase_investor_code(baseinvestor.getBase_investor_code());
    		    		}
    		    else {
				//投资人id
    		    	basetradeinves.setBase_investor_code(null);
    		    		}
    		    		//金额
    		    	if(invemmapMap.get("base_trade_inmoney")!=null){
    		    	    		//金额
    		    	basetradeinves.setBase_trade_inmoney(invemmapMap.get("base_trade_inmoney").toString().trim());
	    				}
    		    	else {
    		    	    		//金额
    		    	basetradeinves.setBase_trade_inmoney(null);
	    				}
    		    			//是否领投
    		    	if(invemmapMap.get("base_trade_collvote")!=null){
    		    	    		//是否领投
    		    	basetradeinves.setBase_trade_collvote(invemmapMap.get("base_trade_collvote").toString().trim());
	    				}
    		    	else {
    		    	    		//是否领投
    		    	basetradeinves.setBase_trade_collvote("1");
	    				}
    		    			//是否对赌
    		    	if(invemmapMap.get("base_trade_ongam")!=null){
    		    	    		//是否对赌
    		    	basetradeinves.setBase_trade_ongam(invemmapMap.get("base_trade_ongam").toString().trim());
	    				}
    		    	else {
    		    	    		//是否对赌
    		    	basetradeinves.setBase_trade_ongam("1");
	    				}
    		    			//是否分次付款
    		    	if(invemmapMap.get("base_trade_subpay")!=null){
    		    	    		//金额
    		    	basetradeinves.setBase_trade_subpay(invemmapMap.get("base_trade_subpay").toString().trim());
	    				}
    		    	else {
    		    	    		//金额
    		    	basetradeinves.setBase_trade_subpay("1");
	    				}
        				//删除标识
    		    	basetradeinves.setDeleteflag("0");
    					//创建者
    		    	basetradeinves.setCreateid(userInfo.getSys_user_code());
    					//创建时间
    		    	basetradeinves.setCreatetime(CommonUtil.getNowTime_tamp());
    					//更新者
    		    	basetradeinves.setUpdid(userInfo.getSys_user_code());
        				//更新时间
    		    	basetradeinves.setUpdtime(basetradeinves.getCreatetime());
    		    	basetradeinveslist.add(basetradeinves);
			    }
			}
			}
			if(noteinfo!=null && !"".equals(noteinfo) && "success".equals(message)){
			  //生成一个交易备注code
				String notecode=CommonUtil.PREFIX_TRADENOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.TRADENOTE_TYPE);
				basetradenoteinfo=new BaseTradenoteInfo();
				//交易id
				basetradenoteinfo.setBase_trade_code(basetradeinfo.getBase_trade_code());
				//交易noteid
				basetradenoteinfo.setBase_tradenote_code(notecode);
				//创建者用户姓名
				basetradenoteinfo.setSys_user_name(userInfo.getSys_user_name());
				//删除标识
				basetradenoteinfo.setBase_tradenote_content(noteinfo);
				//内容
				basetradenoteinfo.setDeleteflag("0");
				//创建者
				basetradenoteinfo.setCreateid(userInfo.getSys_user_code());
				//创建时间
				basetradenoteinfo.setCreatetime(CommonUtil.getNowTime_tamp());
				//更新者
				basetradenoteinfo.setUpdid(userInfo.getSys_user_code());
				//更新时间
				basetradenoteinfo.setUpdtime(basetradenoteinfo.getCreatetime());
				
			}
			if("success".equals(message)){
			tradeaddservice.tranModifyaddtradeinfo(basecompinfo, baseinvestmentinfolist, baseinvestorinfolist, baseinvestmentinvestorlist, basetradeinfo, basetradelabellist, basetradeinveslist, basetradenoteinfo);
			
			//添加公司更新记录
			if(basecompinfo!=null && !"".equals(basecompinfo)){
			    Timestamp time = new Timestamp(new Date().getTime());
			/*添加交易时添加公司修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-company"),
					CommonUtil.findNoteTxtOfXML("Lable-company-name"),
					basecompinfo.getBase_comp_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
					CommonUtil.findNoteTxtOfXML("addComp"),
					"",
					"创建交易时,添加公司["+basecompinfo.getBase_comp_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			//添加机构更新记录
			for(int i=0;i<baseinvestmentinfolist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
		    	baseInvestmentInfo baseinvestmentinfo=baseinvestmentinfolist.get(i);
			/*添加交易时添加机构修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-investment"),
					CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
					baseinvestmentinfo.getBase_investment_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
					CommonUtil.findNoteTxtOfXML("addtradeInvestment"),
					"",
					"创建交易时,添加机构["+baseinvestmentinfo.getBase_investment_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			//添加投资人更新记录
			for(int i=0;i<baseinvestorinfolist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
		    		BaseInvestorInfo baseinvestorinfo=baseinvestorinfolist.get(i);
			/*添加交易时添加投资人修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-people"),
					CommonUtil.findNoteTxtOfXML("Lable-people-name"),
					baseinvestorinfo.getBase_investor_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
					CommonUtil.findNoteTxtOfXML("addtradeInvestor"),
					"",
					"创建交易时,添加投资人["+baseinvestorinfo.getBase_investor_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			//添加投资人机构关系更新记录
			for(int i=0;i<baseinvestmentinvestorlist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
		    		BaseInvestmentInvestor baseinvestmentinvestor=baseinvestmentinvestorlist.get(i);
			/*添加交易日期修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-people"),
					CommonUtil.findNoteTxtOfXML("Lable-people-name"),
					baseinvestmentinvestor.getBase_investor_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
					CommonUtil.findNoteTxtOfXML("investorEdit"),
					"",
					"创建交易时,添加投资人["+baseinvestmentinvestor.getBase_investor_name()+"]的所属机构"
					+"["+baseinvestmentinvestor.getBase_investment_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			//添加交易更新记录
			if(basetradeinfo!=null && !"".equals(basetradeinfo)){
			    Timestamp time = new Timestamp(new Date().getTime());
			/*添加交易修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-trading"),
					CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
					basetradeinfo.getBase_trade_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
					CommonUtil.findNoteTxtOfXML("addTrade"),
					"",
					"添加交易",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			}
			}
			logger.info("TradeAddAction.addtradeinfo方法end");
		} catch (Exception e) {
			logger.error("TradeAddAction.addtradeinfo 发生异常", e);
			e.printStackTrace();
			message="error";
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("messagedetail", messagedetail);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("TradeAddAction.addtradeinfo 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAddAction.addtradeinfo 发生异常", e);
			e.printStackTrace();
		}
	}
  
}