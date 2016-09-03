package cn.com.sims.action.financing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.financing.financingsearch.FinancingSearchService;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Controller
public class FinancingSearchAction {
	@Resource
	FinancingSearchService FinancingSearchService;
	
	@Resource
	ICompanyDetailService companyDetailService;
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	private static final Logger logger = Logger
			.getLogger(FinancingSearchAction.class);
	/**跳转到融资计划搜索
	 * @author zzg
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "financing_search")
	public String financing_search(HttpServletRequest request,
			HttpServletResponse response,String logintype) throws Exception {
//		String Url=ConstantUrl.FANINCINGSEARCH;
		String Url=ConstantUrl.fanincingsearch(logintype);
		return Url;
	}
	/**
	 * 搜索融资计划列表
	 * @author zzg
	 * @param request
	 * @param response
	 * @param companycode
	 * @param page
	 * @param timestamp
	 * @param type 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "financingsearch")
	public void financingsearch(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("companycode") String companycode,Page page
			,@RequestParam("timestamp") String timestamp,String logintype,String type) throws Exception {
		logger.info("FinancingSearchAction.financingsearch()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<BaseFinancplanInfo> list=null;
		Map<String, Object> uplogMap=new HashMap<String, Object>();
		Map<String, String> labMap=null;
		String message;
		try {
			int pagetotal=FinancingSearchService.financingsearch_pagetotal(companycode, timestamp);
			page.setTotalCount(pagetotal);
			list=FinancingSearchService.financingsearch(companycode, timestamp, page);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}

			
			if(type!=null && type.equals("search")){
				if(timestamp!=null&&!timestamp.equals("")){
					uplogMap.put("截止日期", new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(timestamp))));
				}
				if(companycode!=null&& !companycode.equals("")){
					//公司详细信息（业务数据）
					viewCompInfo viewCompInfo = null;
					//根据公司ｉｄ查询公司信息
					viewCompInfo=companyDetailService.findCompanyDeatilByCode(companycode);
					if(viewCompInfo!=null){
						labMap=new HashMap<String, String>();
						labMap.put(viewCompInfo.getBase_comp_code(), viewCompInfo.getBase_comp_name());
						uplogMap.put("公司名称", labMap);
					}
				}
				Timestamp times=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-financing"),
						CommonUtil.findNoteTxtOfXML("Lable-financing-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("financing"),
						"",
						JSONArray.fromObject(uplogMap).toString(),
						logintype,
						userInfo.getSys_user_code(),
						times,
						userInfo.getSys_user_code(),
						times);
			}
			
			logger.info("FinancingSearchAction.financingsearch()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("FinancingSearchAction.financingsearch() 发生异常", e);
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
			logger.error("FinancingSearchAction.financingsearch() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("FinancingSearchAction.financingsearch() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
		
	}
}
