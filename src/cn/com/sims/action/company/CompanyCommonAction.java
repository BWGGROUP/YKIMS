package cn.com.sims.action.company;


import java.io.IOException;
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
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.company.companyCommon.companyCommonService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Controller
public class CompanyCommonAction {
	@Resource
	companyCommonService companyCommonService;//公司公用方法Service
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	ISysLabelelementInfoService dic;//字典
	private static final Logger logger = Logger
			.getLogger(CompanyCommonAction.class);
	
	/**选择公司时-模糊匹配公司名称，英文名拼音，并实现加载更多
	 * @author zzg
	 * @date 20155-10-09
	 * @param request
	 * @param response
	 * @param name
	 * @param pagenow
	 * @param limit
	 * @throws Exception
	 */
	@RequestMapping(value = "companylistbyname")
	public void findInvestmentById(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("name") String name,Page page,String logintype) throws Exception {
		logger.info("CompanyCommonAction.companylistbyname()方法Start");
	
		String message="";
		List<BaseCompInfo> list = null;
		try {
			HashMap map=new HashMap();
			if(name.equals("")){
				map.put("name", null);
			}else {
				map.put("name", name);
			}
			int total=companyCommonService.companylistByname_totalnum(map);
			page.setTotalCount(total);
			map.put("pagestart", page.getStartCount());
			map.put("limit",page.getPageSize());
			list=companyCommonService.companylistByname(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
			logger.info("CompanyCommonAction.companylistbyname()方法end");
		} catch (Exception e) {
			logger.error("CompanyCommonAction.companylistbyname() 发生异常", e);
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
			logger.error("CompanyCommonAction.companylistbyname() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyCommonAction.companylistbyname() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	/**跳转到公司检索页面
	 * @author zzg
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2015-10-22
	 */
	@RequestMapping(value = "company_search")
	public String company_search(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="backtype",required=false)String backtype) throws Exception {
		logger.info("CompanyCommonAction.company_search()方法Start");
		List<Map<String, String>> baskList=null;//字典 关注筐
		List<Map<String, String>> induList=null;//字典 行业
		List<Map<String, String>> stageList=null;//字典 阶段
		try {
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));//查询筐
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));//查询行业
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comstage"));//查询投资阶段
			logger.info("CompanyCommonAction.company_search()方法end");
		} catch (Exception e) {
			logger.error("CompanyCommonAction.company_search() 发生异常", e);
		}
		request.setAttribute("induList", JSONArray.fromObject(induList));
		request.setAttribute("stageList", JSONArray.fromObject(stageList));
		request.setAttribute("baskList", JSONArray.fromObject(baskList));
		request.setAttribute("backtype", backtype);
		String Url=ConstantUrl.companyserarch(logintype);
		return Url;
	}
	/**
	 * 公司多条件筛选
	 * @author zzg
	 * @date 201-10-22
	 * @param request
	 * @param response
	 * @param logintype
	 * @param induList
	 * @param stageList
	 * @param baskList
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "company_searchlist")
	public void company_searchlist(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="induList",required=false)String induList,
			@RequestParam(value="stageList",required=false)String stageList,
			@RequestParam(value="baskList",required=false)String baskList,
			@RequestParam(value="type",required=false)String type,
			Page page
			) throws Exception {
		logger.info("CompanyCommonAction.company_searchlist()方法Start");
		SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message;
		int list_total=0;
		Map<String, Object> uplogMap=new HashMap<String, Object>();
		Map<String, String> labelMap=null;
		SysLabelelementInfo lable=null;
		JSONArray iarray=JSONArray.fromObject(induList);
		JSONArray sarray=JSONArray.fromObject(stageList);
		JSONArray barray=JSONArray.fromObject(baskList);
		List<String> ilist=iarray.subList(0, iarray.size());
		List<String> slist=sarray.subList(0, sarray.size());
		List<String> blist=barray.subList(0, barray.size());
		List<Map<String, Object>> list=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("induList", ilist);
		map.put("stageList", slist);
		map.put("baskList", blist);
	try {
		if(blist.size()>0){
			labelMap = new HashMap<String, String>();
			for (Object object : blist) {
				lable=dic.lablebycode(object.toString());
				if(lable!=null){
					labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
				}
			}
			if (labelMap.size()>0) {
				uplogMap.put("筐", labelMap);
			}
		}
		
		if(ilist.size()>0){
			labelMap = new HashMap<String, String>();
			for (Object object : ilist) {
				lable=dic.lablebycode(object.toString());
				if(lable!=null){
					labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
				}
			}
			if (labelMap.size()>0) {
				uplogMap.put("行业", labelMap);
			}
		}
		
		if(slist.size()>0){
			labelMap = new HashMap<String, String>();
			for (Object object : slist) {
				lable=dic.lablebycode(object.toString());
				if(lable!=null){
					labelMap.put(lable.getSys_labelelement_code(), lable.getSys_labelelement_name());
				}
			}
			if (labelMap.size()>0) {
				uplogMap.put("投资阶段", labelMap);
			}
		}
		
		
		
		
		
		list_total=companyCommonService.companysearchlist_num(map);
		page.setTotalCount(list_total);
		map.put("start", page.getStartCount());
		map.put("size", page.getPageSize());
		list=(List<Map<String, Object>>) companyCommonService.companysearchlist(map);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("view_comp_inducont")==null){
				list.get(i).put("view_comp_inducont", new JSONArray());
			}else {
				list.get(i).put("view_comp_inducont", JSONArray.fromObject((list.get(i).get("view_comp_inducont"))));
			}
		
		}
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("view_comp_baskcont")==null){
				list.get(i).put("view_comp_baskcont", new JSONArray());
			}else {
				list.get(i).put("view_comp_baskcont", JSONArray.fromObject((list.get(i).get("view_comp_baskcont"))));
			}
		
		}
		if (list.size()==0) {
			message="nomore";
		}else {
			message="success";
		}
		if(type!=null && type.equals("search")){
			Timestamp timestamp=CommonUtil.getNowTime_tamp();
			/*添加系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-company"),
					CommonUtil.findNoteTxtOfXML("Lable-company-name"),
					"", 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
					CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("company"),
					"",
					JSONArray.fromObject(uplogMap).toString(),
					logintype,
					userInfo.getSys_user_code(),
					timestamp,
					userInfo.getSys_user_code(),
					timestamp);
		}
		
		
		logger.info("CompanyCommonAction.company_searchlist()方法end");

	} catch (Exception e) {
		message="error";
		logger.error("CompanyCommonAction.company_searchlist() 发生异常", e);
		e.printStackTrace();
	}
	PrintWriter out;
	response.setContentType("text/html; charset=UTF-8");
	try {
		out = response.getWriter();
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("message", message);
		resultJSON.put("list", list);
		resultJSON.put("page", new JSONObject(page));
		out.println(resultJSON.toString());
		out.flush();
		out.close();
	} catch (IOException e) {
		message = "error";
		logger.error("CompanyCommonAction.trade_searchlist() 发生异常", e);
		e.printStackTrace();
		throw e;
	} catch (Exception e) {
		logger.error("CompanyCommonAction.trade_searchlist() 发生异常", e);
		e.printStackTrace();
		throw e;
	}
	}
	/**通过名称搜索公司列表
	 * @author zzg
	 * @date 2015-10-23
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "company_searchlistbyname")
	public void company_searchlistbyname(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("name")String name,
			@RequestParam(value="type",required=false)String type,
			Page page) throws Exception {
		logger.info("CompanyCommonAction.company_searchlistbyname()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message;
		int list_total=0;
		List<Map<String, Object>> list=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		
		try {
			list_total=companyCommonService.company_searchlistbyname_num(map);
			page.setTotalCount(list_total);
			map.put("start", page.getStartCount());
			map.put("size", page.getPageSize());
			list=companyCommonService.company_searchlistbyname(map);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("view_comp_inducont")==null){
					list.get(i).put("view_comp_inducont", new JSONArray());
				}else {
					list.get(i).put("view_comp_inducont", JSONArray.fromObject((list.get(i).get("view_comp_inducont"))));
				}
			
			}
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).get("view_comp_baskcont")==null){
					list.get(i).put("view_comp_baskcont", new JSONArray());
				}else {
					list.get(i).put("view_comp_baskcont", JSONArray.fromObject((list.get(i).get("view_comp_baskcont"))));
				}
				}
			if (list.size()==0) {
				message="nomore";
			}else {
				message="success";
			}
			
			if(type!=null&& type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-company"),
						CommonUtil.findNoteTxtOfXML("Lable-company-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("company"),
						"",
						"[{\"搜索\":\""+name+"\"}]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
			
			logger.info("CompanyCommonAction.company_searchlistbyname()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyCommonAction.company_searchlistbyname() 发生异常", e);
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", list);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyCommonAction.company_searchlistbyname() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyCommonAction.company_searchlistbyname() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
}
