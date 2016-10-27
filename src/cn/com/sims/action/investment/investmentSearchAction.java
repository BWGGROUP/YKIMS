package cn.com.sims.action.investment;

import java.io.IOException;
/**
 * @auther yanglian
 * @creatime 2015-09-24
 */
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
import cn.com.sims.action.investment.beanUtil.exportExcelThead;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.investment.IInvestmentService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.util.email.IEmailSenderService;

/**
 * @author yanglian
 * @date 2015-09-24
 */
@Controller
public class investmentSearchAction {
	@Resource
	IInvestmentService investmentService;// 投资机构service
	@Resource
	InvestorService investorService;// 投资人service
	@Resource
	IEmailSenderService emailSenderService;//email 发送service
	@Resource
	ICompanyDetailService companyDetailService;//公司
	
	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	private static final Logger logger = Logger
			.getLogger(investmentSearchAction.class);

	/**
	 * 模糊查询投资机构名称或投资人名称
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @return
	 * @version 2015-09-24
	 */
	@RequestMapping(value = "search")
	public void search(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("name") String name) throws Exception {
		logger.info("investmentSearchAction.search()方法Start");

		List investmentlist = new ArrayList(50);//投资机构list
		List investorList = new ArrayList(50);//投资人list
		String message = "success";

		try {
			        //根据输入内容模糊匹配投资机构前４条
					investmentlist = investmentService.findInvestment(name);
					//根据输入内容模糊匹配投资人前４条
					investorList = investorService.findInvestorName(name);
				
		} catch (Exception e) {
			logger.error("investmentSearchAction.search()方法异常", e);
			e.printStackTrace();
		}

		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(investmentlist);
			JSONArray jsonArrayor = JSONArray.fromObject(investorList);
			resultJSON.put("investmentlist", jsonArray);
			resultJSON.put("investorList", jsonArrayor);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investmentSearchAction search() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investmentSearchAction search() 发生异常", e);
			e.printStackTrace();
		}

	}

	/**
	 * 通过名字，拼音，首字母模糊查询投资机构信息
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param type
	 * @param pages
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "findInvestmentByName")
	public void findInvestmentByName(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("type") String type,
			@RequestParam(value="logintype",required=false)String logintype,
			Page page, @RequestParam("name") String name) throws Exception {

		logger.info("investmentSearchAction.findInvestmentByName()方法Start");

		List list = null;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String message = "success";

		try {
			if (type != null && !"".equals(type)) {
				if ("1".equals(type)) {// type 为“１”时，搜投资机构
					list = new ArrayList<viewInvestmentInfo>();
					paraMap.put("name", name);
					//根据输入条件查询符合要求的投资机构总条数
					int countSize = investmentService
							.findCountSizeByName(paraMap);
					page.setTotalCount(countSize);
					//每页显示的条数
					paraMap.put("pageSize", page.getPageSize());
					//此页显示的开始索引
					paraMap.put("startIndex", page.getStartCount());
					//根据输入条件查询，条数，开始索引查询符合条件的投资机构信息
					list = investmentService.findInvestmentByName(paraMap);
				} else {
					message = "error";
				}
			}
		} catch (Exception e) {
			logger.error("investmentSearchAction.findInvestmentByName()方法异常", e);
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
			resultJSON.put("types", "text");
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investmentSearchAction.findInvestmentByName() 发生异常",
					e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investmentSearchAction.findInvestmentByName() 发生异常",
					e);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 通过名字，拼音，首字母模糊查询投资机构信息
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param type
	 * @param pages
	 * @param name
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-12-15
	 */
	@RequestMapping(value = "findInvestmentNameListByName")
	public void findInvestmentNameListByName(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("type") String type,
			@RequestParam(value="logintype",required=false)String logintype,
			Page page, @RequestParam("name") String name) throws Exception {

		logger.info("investmentSearchAction.findInvestmentNameListByName()方法Start");

		List list = null;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String message = "success";

		try {
			if (type != null && !"".equals(type)) {
				if ("1".equals(type)) {// type 为“１”时，搜投资机构
					list = new ArrayList<viewInvestmentInfo>();
					paraMap.put("name", name);
					//根据输入条件查询符合要求的投资机构总条数
					int countSize = investmentService
							.findCountSizeByName(paraMap);
					page.setTotalCount(countSize);
					//每页显示的条数
					paraMap.put("pageSize", page.getPageSize());
					//此页显示的开始索引
					paraMap.put("startIndex", page.getStartCount());
					//根据输入条件查询，条数，开始索引查询符合条件的投资机构信息
					list = investmentService.findInvestmentNameListByName(paraMap);
				} else {
					message = "error";
				}
			}
		} catch (Exception e) {
			logger.error("investmentSearchAction.findInvestmentNameListByName()方法异常", e);
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
			resultJSON.put("types", "text");
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("investmentSearchAction.findInvestmentNameListByName() 发生异常",
					e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investmentSearchAction.findInvestmentNameListByName() 发生异常",
					e);
			e.printStackTrace();
		}

	}
	
	
	
	

	/**
	 * 通过名字，拼音，首字母模糊查询跳转到投资机构清单页面
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "gotoInvestmentList")
	public String gotoInvestmentList(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("type") String type,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("name") String name,Page page) throws Exception {
		logger.info("investmentSearchAction.gotoSearch()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List list = null;
		String message = "success";
		int pagecount=1;
		Map<String, Object> paraMap = new HashMap<String, Object>();

		try {
			if (type != null && !"".equals(type)) {
				if ("1".equals(type)) {// type 为“１”时，搜投资机构
					list = new ArrayList<viewInvestmentInfo>();
					//每页显示的条数
					paraMap.put("pageSize", page.getPageSize());
					//输入的查询条件
					paraMap.put("name", name);
					//根据输入条件查询符合要求的投资机构总条数
					int countSize = investmentService
							.findCountSizeByName(paraMap);
					page.setTotalCount(countSize);
					paraMap.put("startCount", page.getStartCount());
					list = investmentService.gotoSearchByName(paraMap);
				} else if ("2".equals(type)) {// type 为“１”时，搜投资人
					/* list = investorService.findInvestorName(name); */
				} else {
					message = "error";
				}
			}
			
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
					"[{\"搜索\":\""+name+"\"}]",
					logintype,
					userInfo.getSys_user_code(),
					timestamp,
					userInfo.getSys_user_code(),
					timestamp);
		} catch (Exception e) {
			logger.error("investmentSearchAction.findInvestmentByName()方法异常", e);
			e.printStackTrace();
		}

		Map<String, Object> investment = new HashMap<String, Object>();
		if(page.getTotalPage()==0){
			pagecount=1;
		}else{
			pagecount=page.getTotalPage();
		}
		investment.put("pageCunt", pagecount);
		investment.put("pageCount", page.getPageCount());
		investment.put("totalCount", page.getTotalCount());
		investment.put("investmentList", list);
		investment.put("types", "text");
		request.setAttribute("investment", investment);
		String Url = ConstantUrl.investmentList(logintype);
		return Url;
	}


	/**
	 * 跳转到搜索主页面
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "gotoSearchMain")
	public String gotoSearchMain(HttpServletRequest request,
			@RequestParam(value="logintype",required=false)String logintype,
			HttpServletResponse response) throws Exception {
		if (logintype.equals(CommonUtil.findNoteTxtOfXML("COMPUTER"))) {
			// 初始化ｌｉｓｔ定义
			List lableBaskList = null;// 框list
			List lableCurrencyList = null;// 币种list
			List lableFeatureList = null;// 投资特征list
			List lableStageList = null;// 投资阶段list
			List lableScaleList = null;// 融资规模list
			List lableinduList = null;// 行业list
			//2015-12-18 yl add start
			List lableBgroundList = null;//基金list
			//2015-12-18 yl add end
			List<?> lableTypeList = null;//投资类型
			String message = "success";

			Map<String, Object> paraMap = new HashMap<String, Object>();
			try {
				
				//2015-11-27  TASK069 yl mod start
				// 筐，行业，币种，投资特征，投资阶段，融资规模初始化查询
				lableBaskList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-bask"));
				lableCurrencyList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-currency"));
				lableFeatureList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-feature"));
//				lableStageList = investmentService.findLable("Lable-stage");
				lableStageList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-investage"));
				lableScaleList = investmentService.findLableScle();
				lableinduList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
				//2015-11-27  TASK069 yl mod end
				//2015-12-18 TASK92 yl add start
				lableBgroundList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-bground"));
				//2015-12-18 TASK92 yl add end
				/* 2016-5-3 dwj add 类型标签*/
				lableTypeList=investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-type"));
			} catch (Exception e) {
				logger.error("investmentSearchAction.gotoSearchMain()方法异常", e);
				e.printStackTrace();
			}

			Map<Object,Object> investment = new HashMap<Object, Object>();
			investment.put("lableBaskList", lableBaskList);
			investment.put("lableCurrencyList", lableCurrencyList);
			investment.put("lableFeatureList", lableFeatureList);
			investment.put("lableStageList", lableStageList);
			investment.put("lableScaleList", lableScaleList);
			investment.put("lableinduList", lableinduList);
			investment.put("lableTypeList", lableTypeList);
			//2015-12-18 TASK92 yl add start
			investment.put("lableBgroundList", lableBgroundList);
			//2015-12-18 TASK92 yl add end
			request.setAttribute("investment", investment);
		}
	
		
		String Url = ConstantUrl.searchMain(logintype);
		return Url;
	}

	/**
	 * 投资机构搜索页面高级筛选
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "initInvestmentSearch")
	public String initInvestmentSearch(HttpServletRequest request,
			@RequestParam(value="logintype",required=false)String logintype,
			HttpServletResponse response) throws Exception {
		logger.info("investmentSearchAction.initInvestmentSearch()方法Start");
		
		// 初始化ｌｉｓｔ定义
		List lableBaskList = null;// 框list
		List lableCurrencyList = null;// 币种list
		List lableFeatureList = null;// 投资特征list
		List lableStageList = null;// 投资阶段list
		List lableScaleList = null;// 融资规模list
		List lableinduList = null;// 行业list
		//2015-12-18 TASK92 yl add start
		List lableBgroundList = null;// 行业list
		//2015-12-18 TASK92 yl add end
		
		List lableTypeList = null;//类型标签
		String message = "success";

		Map<String, Object> paraMap = new HashMap<String, Object>();
		try {
//2015-11-30 TASK069 yl mod start
			// 筐，行业，币种，投资特征，投资阶段，融资规模初始化查询
			lableBaskList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-bask"));
			lableCurrencyList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-currency"));
			lableFeatureList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-feature"));
//			lableStageList = investmentService.findLable("Lable-stage");
			lableStageList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-investage"));
			lableScaleList = investmentService.findLableScle();
			lableinduList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
			//2015-11-30 TASK069 yl mod end
			//2015-12-18 TASK92 yl add start
			lableBgroundList = investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-bground"));
			//2015-12-18 TASK92 yl add end
			
			/* 2016-5-3 dwj add 类型标签*/
			lableTypeList=investmentService.findLable(CommonUtil.findNoteTxtOfXML("Lable-type"));
		} catch (Exception e) {
			logger.error("investmentSearchAction.initInvestmentSearch()方法异常", e);
			e.printStackTrace();
		}

		Map<Object,Object> investment = new HashMap<Object, Object>();
		investment.put("lableBaskList", lableBaskList);
		investment.put("lableCurrencyList", lableCurrencyList);
		investment.put("lableFeatureList", lableFeatureList);
		investment.put("lableStageList", lableStageList);
		investment.put("lableScaleList", lableScaleList);
		investment.put("lableinduList", lableinduList);
		//2015-12-18 TASK92 yl add start
		investment.put("lableBgroundList", lableBgroundList);
		//2015-12-18 TASK92 yl add end
		
		investment.put("lableTypeList", lableTypeList);
		
		request.setAttribute("investment", investment);
		String Url = ConstantUrl.gotoSearchInvest(logintype);
		return Url;
	}

	/**
	 * 根据名称，拼音，首字母查询公司
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "searchCompany")
	public void searchCompany(HttpServletRequest request,
			@RequestParam(value="logintype",required=false)String logintype,
			HttpServletResponse response, @RequestParam("name") String name)
			throws Exception {
		logger.info("investmentSearchAction.searchCompany()方法Start");
		List list = new ArrayList(50);
		String message = "success";

		try {
			// 根据名称，拼音，首字母查询符合条件的公司
			list = investmentService.findCompany(name);

		} catch (Exception e) {
			message="error";
			logger.error("investmentSearchAction.searchCompany()方法异常", e);
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
			out.println(resultJSON.toString());
			out.flush();
			out.close();

		} catch (IOException e) {
			message = "error";
			logger.error("investmentSearchAction searchCompany() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("investmentSearchAction searchCompany() 发生异常", e);
			e.printStackTrace();
		}

	}

	/**
	 * 多条件查询投资机构并跳转到投资机构列表页面
	 * 
	 * @param request
	 * @param response
	 * @param logintype 
	 * @param hangye
	 * @param kuang
	 * @param currency
	 * @param feature
	 * @param stage
	 * @param scale
	 * @param payatt
	 * @param competition 
	 * @param deleteflag 状态
	 * @param invtype 投资类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "searchMoreCondition")
	public String searchMoreCondition(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="type",required=false)String type,
			@RequestParam("hangye") String hangye,
			@RequestParam("kuang") String kuang,
			@RequestParam("currency") String currency,
			@RequestParam("feature") String feature,
			@RequestParam("stage") String stage,
			@RequestParam("scale") String scale,
			@RequestParam("payatt") String payatt,
			@RequestParam("bground") String bground,
			@RequestParam("competition") String competition,
			@RequestParam("deleteflag") String deleteflag,
			@RequestParam("invtype") String invtype,
			Page page) throws Exception {
		logger.info("investmentSearchAction.searchMoreCondition()方法Start");
		SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List investmentList = new ArrayList();
		String message = "success";
		int countSize = 0;
		int pagecount = 1;
		// 判断有无选择竞争公司，text:未选择竞争公司 scre:选择了竞争公司
		String types = "text";

		Map<String, Object> paraMap = new HashMap<String, Object>();

		try {
			/**
			 * 筐,行业,币种,投资特征,投资阶段,融资规模值转化成list
			 */
			List lableBaskList;// 筐list
			List lableinduList;// 行业list
			List lableCurrencyList;// 币种list
			List lableFeatureList;// 投资特征list
			List lableStageList;// 投资阶段list
			List lableScaleList;// 融资规模list
			List lablepayatteList;// 近期关注list
			List competitionList = null;// 竞争公司list
			List usdScaleList = new ArrayList(50);// 美元融资规模list
			List cnyScaleList = new ArrayList(50);// 人民币融资规模list
			//2015-12-18 TASK92 yl add start
			List lableBgroundList;//背景list
			//2015-12-18 TASK92 yl add end
			
			List delList;//状态 2016-4-21 duwenjie add
			List typeList;//类型 2016-5-3 dwj add
			
			SysLabelelementInfo lable=null;
			Map<String, Object> uplogMap=new HashMap<String, Object>();
			Map<String, String> labelMap=null;
			// 判断前台传来的参数是否为“”，并转化成list
			// 筐list
			if ("".equals(kuang)) {
				lableBaskList = null;
			} else {
				lableBaskList = Arrays.asList(kuang.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableBaskList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("机构", labelMap);
				}
			}
			// 行业list
			if ("".equals(hangye)) {
				lableinduList = null;
			} else {
				lableinduList = Arrays.asList(hangye.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableinduList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("行业", labelMap);
				}
			}
			// 币种list
			if ("".equals(currency)) {
				lableCurrencyList = null;
			} else {
				lableCurrencyList = Arrays.asList(currency.split(","));
				labelMap = new HashMap<String, String>();
				for (Object object : lableCurrencyList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("币种", labelMap);
				}
			}
			// 投资特征list
			if ("".equals(feature)) {
				lableFeatureList = null;
			} else {
				lableFeatureList = Arrays.asList(feature.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableFeatureList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("投资特征", labelMap);
				}
			}
			//投资阶段list
			if ("".equals(stage)) {
				lableStageList = null;
			} else {
				lableStageList = Arrays.asList(stage.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableStageList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("投资阶段", labelMap);
				}
			}
			// 类型list 2016-5-3 dwj
			if ("".equals(invtype)) {
				typeList = null;
			} else {
				typeList = Arrays.asList(invtype.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : typeList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("投资类型", labelMap);
				}
			}
			
			//2015-12-18 TASK92 yl add start
			//背景list
			if ("".equals(bground)) {
				lableBgroundList = null;
			} else {
				lableBgroundList = Arrays.asList(bground.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableBgroundList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("背景", labelMap);
				}
			}
			//2015-12-18 TASK92 yl add end
			
			// 融资规模list
			if ("".equals(scale)) {
				lableScaleList = null;
			} else {
				lableScaleList = Arrays.asList(scale.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lableScaleList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("融资规模", labelMap);
				}
				
				for(int i=0;i<lableCurrencyList.size();i++){
					if(lableCurrencyList.get(i).toString().contains("Lable-currency-001")){
						usdScaleList.add(null);
					}else if(lableCurrencyList.get(i).toString().contains("Lable-currency-002")){
						cnyScaleList.add(null);
					}
				}
				
				for(int i=0;i<lableScaleList.size();i++){
					if(lableScaleList.get(i).toString().contains("usd")){
						if(usdScaleList.get(0)==null){
							usdScaleList.set(0, lableScaleList.get(i));
						}else{
							usdScaleList.add(lableScaleList.get(i));
						}
					}else if(lableScaleList.get(i).toString().contains("cny")){
						if(cnyScaleList.get(0)==null){
							cnyScaleList.set(0,lableScaleList.get(i));
						}else{
							cnyScaleList.add(lableScaleList.get(i));
						}
					}
				}
			}
			// 近期关注list
			if ("".equals(payatt)) {
				lablepayatteList = null;
			} else {
				lablepayatteList = Arrays.asList(payatt.split(","));
				
				labelMap = new HashMap<String, String>();
				for (Object object : lablepayatteList) {
					lable=dic.lablebycode(object.toString());
					if(lable!=null){
						labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("近期关注", labelMap);
				}
			}
			// 竞争公司list
			if ("".equals(competition)) {
				competitionList = null;
			} else {
				competitionList = Arrays.asList(competition.split(","));
				types = "scre";
				
				labelMap = new HashMap<String, String>();
				viewCompInfo compInfo=null;
				for (Object object : competitionList) {
					compInfo=companyDetailService.findCompanyDeatilByCode(object.toString());
					if(lable!=null){
						labelMap.put(compInfo.getBase_comp_code(), compInfo.getBase_comp_name());
					}
				}
				if (labelMap.size()>0) {
					uplogMap.put("竞争公司", labelMap);
				}
			}
			
			//状态 duwenjie 2016-4-21
			if ("".equals(deleteflag)) {
				delList = Arrays.asList("0");
			} else {
				delList = Arrays.asList(deleteflag.split(","));
			}
			
			

			// 筐，行业，币种，投资特征，投资阶段，融资规模初始化查询
			paraMap.put("lableBaskList", lableBaskList);
			paraMap.put("lableinduList", lableinduList);
			paraMap.put("lableCurrencyList", lableCurrencyList);
			paraMap.put("lableFeatureList", lableFeatureList);
			paraMap.put("lableStageList", lableStageList);
			paraMap.put("lableScaleList", lableScaleList);
			paraMap.put("lablepayatteList", lablepayatteList);
			paraMap.put("competitionList", competitionList);
			paraMap.put("usdScaleList", usdScaleList);
			paraMap.put("cnyScaleList", cnyScaleList);
			//2015-12-18 TASK92 yl add start
			paraMap.put("lableBgroundList", lableBgroundList);
			//2015-12-18 TASK92 yl add end
			
			paraMap.put("delList", delList);//2016-4-21 dwj
			paraMap.put("typeList", typeList);//2016-5-3 dwj
			
           //查询符合要求的投资机构总条数
			countSize = investmentService.findInvestmentByMoreCount(paraMap);
			page.setTotalCount(countSize);
			//每页显示的条数
			paraMap.put("pageSize", page.getPageSize());
			//开始索引
			paraMap.put("startCount", page.getStartCount());
			if(page.getTotalPage()==0){
				pagecount=1;
			}else{
				pagecount=page.getTotalPage();
			}
			
			investmentList = investmentService.findInvestmentByMoreCon(paraMap,competitionList);

			if(type!=null && "search".equals(type)){
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
						JSONArray.fromObject(uplogMap).toString(),
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
		} catch (Exception e) {
			logger.error("investmentSearchAction.initInvestmentSearch()方法异常", e);
			e.printStackTrace();
		}

		Map<String,Object> investment = new HashMap<String, Object>();
		investment.put("types", types);
		investment.put("investmentList", investmentList);
		investment.put("pageCunt", pagecount);
		investment.put("pageCount", page.getPageCount());
		investment.put("totalCount", page.getTotalCount());
		request.setAttribute("investment", investment);
		String Url = ConstantUrl.gotoSearchInvestList(logintype);

		return Url;
	}

	/**
	 * 多条件查询投资机构分页查询显示
	 * 
	 * @param request
	 * @param response
	 * @param logintype
	 * @param page
	 * @param hangye
	 * @param kuang
	 * @param currency
	 * @param feature
	 * @param stage
	 * @param scale
	 * @param payatt
	 * @param competition
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping(value = "searchMoreConditionByPage")
	public void searchMoreConditionByPage(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("hangye") String hangye,
			@RequestParam("kuang") String kuang,
			@RequestParam("currency") String currency,
			@RequestParam("feature") String feature,
			@RequestParam("stage") String stage,
			@RequestParam("scale") String scale,
			@RequestParam("payatt") String payatt,
			@RequestParam("competition") String competition,
			@RequestParam("bground") String bground,
			@RequestParam("type") String type,
			@RequestParam("deleteflag") String deleteflag,
			@RequestParam("invtype") String invtype) throws Exception {
		logger.info("investmentSearchAction.searchMoreConditionByPage()方法Start");


		List investmentList = new ArrayList();
		String message = "success";
		int countSize = 0;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 标识有误选择竞争公司　text:未选择竞争公司 scre:选择了竞争公司
		String types = "text";
		try {
			/**
			 * 筐,行业,币种,投资特征,投资阶段,融资规模值转化成list
			 */
			List lableBaskList;
			List lableinduList;// 行业list
			List lableCurrencyList;// 币种list
			List lableFeatureList;// 投资特征list
			List lableStageList;// 投资阶段list
			List lableScaleList;// 融资规模list
			List lablepayatteList;// 近期关注list
			List competitionList;// 竞争公司list
			List usdScaleList = new ArrayList(50);// 美元融资规模list
			List cnyScaleList = new ArrayList(50);// 人民币融资规模list
			//2015-12-18 TASK92 yl add start
			List lableBgroundList;//背景list
			//2015-12-18 TASK92 yl add end

			List delList;//状态 2016-4-21 duwenjie add
			List typeList;//类型 2016-5-3 dwj add
			
			// 判断前台传来的参数是否为“”，并转化成list
			//2015-12-18 TASK92 yl add start
			//背景list
			if ("".equals(bground)) {
				lableBgroundList = null;
			} else {
				lableBgroundList = Arrays.asList(bground.split(","));
				
			}
			//2015-12-18 TASK92 yl add end
			// 筐list
			if ("".equals(kuang)) {
				lableBaskList = null;
			} else {
				lableBaskList = Arrays.asList(kuang.split(","));
			}
			// 行业list
			if ("".equals(hangye)) {
				lableinduList = null;
			} else {
				lableinduList = Arrays.asList(hangye.split(","));
			}
			// 币种list
			if ("".equals(currency)) {
				lableCurrencyList = null;
			} else {
				lableCurrencyList = Arrays.asList(currency.split(","));
			}
			// 投资特征list
			if ("".equals(feature)) {
				lableFeatureList = null;
			} else {
				lableFeatureList = Arrays.asList(feature.split(","));
			}
			// 投资阶段list
			if ("".equals(stage)) {
				lableStageList = null;
			} else {
				lableStageList = Arrays.asList(stage.split(","));
			}
			// 融资规模list
			if ("".equals(scale)) {
				lableScaleList = null;
			} else {
				lableScaleList = Arrays.asList(scale.split(","));
				for(int i=0;i<lableCurrencyList.size();i++){
					if(lableCurrencyList.get(i).toString().contains("Lable-currency-001")){
						usdScaleList.add(null);
					}else if(lableCurrencyList.get(i).toString().contains("Lable-currency-002")){
						cnyScaleList.add(null);
					}
				}
				
				for(int i=0;i<lableScaleList.size();i++){
					if(lableScaleList.get(i).toString().contains("usd")){
						if(usdScaleList.get(0)==null){
							usdScaleList.set(0, lableScaleList.get(i));
						}else{
							usdScaleList.add(lableScaleList.get(i));
						}
					}else if(lableScaleList.get(i).toString().contains("cny")){
						if(cnyScaleList.get(0)==null){
							cnyScaleList.set(0,lableScaleList.get(i));
						}else{
							cnyScaleList.add(lableScaleList.get(i));
						}
					}
				}
			}
			// 类型list 2016-5-3 dwj
			if ("".equals(invtype)) {
				typeList = null;
			} else {
				typeList = Arrays.asList(invtype.split(","));
			}
			//状态 duwenjie 2016-4-21
			if ("".equals(deleteflag)) {
				delList = Arrays.asList("0");
			} else {
				delList = Arrays.asList(deleteflag.split(","));
			}
			
			// 近期关注list
			if ("".equals(payatt)) {
				lablepayatteList = null;
			} else {
				lablepayatteList = Arrays.asList(payatt.split(","));
			}
			// 竞争公司list
			if ("".equals(competition)) {
				competitionList = null;
			} else {
				competitionList = Arrays.asList(competition.split(","));
				types = "scre";
			}
			
			// 筐，行业，币种，投资特征，投资阶段，融资规模初始化查询
			paraMap.put("lableBaskList", lableBaskList);
			paraMap.put("lableinduList", lableinduList);
			paraMap.put("lableCurrencyList", lableCurrencyList);
			paraMap.put("lableFeatureList", lableFeatureList);
			paraMap.put("lableStageList", lableStageList);
//			paraMap.put("lableScaleList", lableScaleList);
			paraMap.put("lablepayatteList", lablepayatteList);
			paraMap.put("competitionList", competitionList);
			paraMap.put("usdScaleList", usdScaleList);
			paraMap.put("cnyScaleList", cnyScaleList);

			paraMap.put("delList", delList);//2016-4-21 dwj
			paraMap.put("typeList", typeList);//2016-5-3 dwj
			
			//2015-12-18 TASK92 yl add start
			paraMap.put("lableBgroundList", lableBgroundList);
			//2015-12-18 TASK92 yl add end
			// 根据多条件查询符合条件的所有投资机构的总条数
			countSize = investmentService.findInvestmentByMoreCount(paraMap);
			page.setTotalCount(countSize);
			paraMap.put("pageSize", page.getPageSize());
			paraMap.put("startCount", page.getStartCount());

			// 根据多条件查询符合条件的投资机构分页显示
			investmentList = investmentService.findInvestmentByMoreCon(paraMap,competitionList);

		} catch (Exception e) {
			logger.error(
					"investmentSearchAction.searchMoreConditionByPage()方法异常", e);
			e.printStackTrace();
		}

		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(investmentList);
			resultJSON.put("list", jsonArray);
			resultJSON.put("types", types);
			resultJSON.put("pageCunt", page.getTotalCount());
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error(
					"investmentSearchAction.searchMoreConditionByPage() 发生异常",
					e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(
					"investmentSearchAction.searchMoreConditionByPage() 发生异常",
					e);
			e.printStackTrace();
		}

	}
	/**
	 * 投资机构列表导出excel
	 * @param request
	 * @param response
	 * @param page page对象
	 * @param hangye　选择的行业
	 * @param kuang　选择的筐
	 * @param currency　选择的币种
	 * @param feature　选择的特征
	 * @param stage　选择的阶段
	 * @param scale　选择的规模
	 * @param name　投资机构输入框信息
	 * @param payatt　选择的近期关注
	 * @param competition　输入的竞争公司
	 * @param type
	 * @param logintype
	 * @param hiddenPath
	 * @throws Exception
	 */
	@RequestMapping(value = "exportExcelByMoreCon")
	public void exportExcelByMoreCon(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("hangye") String hangye,
			@RequestParam("kuang") String kuang,
			@RequestParam("currency") String currency,
			@RequestParam("feature") String feature,
			@RequestParam("stage") String stage,
			@RequestParam("scale") String scale,
			@RequestParam("name") String name,
			@RequestParam("payatt") String payatt,
			@RequestParam("competition") String competition,
			@RequestParam("type") String type,
			@RequestParam("bground") String bground,
			@RequestParam("labletype") String labletype,
			@RequestParam("hiddenPath") String hiddenPath) throws Exception {
		logger.info("investmentSearchAction.exportExcelByMoreCon()方法Start");
	
		//此处从session中获取登录者的信息，
		SysUserInfo sysUserInfo =(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
	
		exportExcelThead exportExcelThead = new exportExcelThead();
		exportExcelThead.setInvestmentService(investmentService);
		exportExcelThead.setPath(request.getSession().getServletContext().getRealPath("/"));
		exportExcelThead.setRequest(request);
		exportExcelThead.setResponse(response);
		exportExcelThead.setCurrency(currency);
		exportExcelThead.setFeature(feature);
		exportExcelThead.setHangye(hangye);
		exportExcelThead.setKuang(kuang);
		exportExcelThead.setPayatt(payatt);
		exportExcelThead.setScale(scale);
		exportExcelThead.setStage(stage);
		exportExcelThead.setCompetition(competition);
		exportExcelThead.setHiddenPath(hiddenPath);
		exportExcelThead.setType(type);
		exportExcelThead.setInvestmentName(name);
		exportExcelThead.setEmailSenderService(emailSenderService);
		exportExcelThead.setSysUserInfo(sysUserInfo);
		//2015-12-18 TASK92 yl add start
		exportExcelThead.setBground(bground);//背景
		//2015-12-18 TASK92 yl add end
		exportExcelThead.setLabletype(labletype);//投资类型
		
		boolean offerTag = CommonUtil.bQueue.offer(exportExcelThead);
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		try {
			JSONObject resultJSON = new JSONObject();
			
			resultJSON.put("message", offerTag);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			out.println("{\"message\":\"false\"}");
			out.flush();
			out.close();
			logger.error(
					"investmentSearchAction.searchMoreConditionByPage() 发生异常",
					e);
			e.printStackTrace();
		} 
		
		logger.info("investmentSearchAction.exportExcelByMoreCon()方法end");
	}
}
