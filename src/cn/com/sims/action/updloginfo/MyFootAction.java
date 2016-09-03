package cn.com.sims.action.updloginfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;

/**
 * 我的足迹
 * @author duwenjie
 * @date 2016-6-1
 */
@Controller
public class MyFootAction {

	@Resource
	IBaseUpdlogInfoService updService;
	
	@Resource
	ITradeInfoService ITradeInfoService;
	
//	@Resource
//	FinancingSearchService fincingService;
	
	@Resource
	SysUserInfoService sysUserInfoService;
	
	private static final Logger logger = Logger
	.getLogger(MyFootAction.class);
	
	/**
	 * 跳转足迹搜索页面
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 */
	@RequestMapping(value="myfoot_search")
	public String myfoot_search(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="logintype",required=true)String logintype,
			@RequestParam(value="backtype",required=false)String backtype){
		request.setAttribute("backtype", backtype);
		return ConstantUrl.myfootSearch(logintype);
	}
	
	/**
	 * 条件查询用户足迹信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @param opertype
	 * @param logtype
	 */
	@RequestMapping(value="findUserFoot")
	public void findUserFoot(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="logintype",required=true)String logintype,
			@RequestParam(value="opertype",required=true)String opertype,
			@RequestParam(value="logtype",required=true)String logtype,Page page) {
		String message="success";
		List<Map<String, Object>> list=null;
		Map<String, Object> map=null;
		try {
			SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			if(userInfo!=null){
				map=new HashMap<String, Object>();
				map.put("opertype", opertype);
				map.put("usercode", userInfo.getSys_user_code());
				if(logtype.equals("0")){//投资机构
					map.put("logtype", CommonUtil.findNoteTxtOfXML("Lable-investment"));
					int data=updService.findUpdByupdlogUserCount(map);
					if(data>0){
						page.setTotalCount(data);
						map.put("starttime", page.getStartCount());
						map.put("pageSize", page.getPageSize());
						
						list=updService.findOrgByupdlogUser(map);
					}
					
				}else if(logtype.equals("1")){//近期交易
					map.put("logtype", CommonUtil.findNoteTxtOfXML("Lable-trading"));
					int data=updService.findTradeCountByupdlogUser(map);
					if(data>0){
						page.setTotalCount(data);
						map.put("starttime", page.getStartCount());
						map.put("pageSize", page.getPageSize());
						
						list=updService.findTradeByupdlogUser(map);
						
						//date:2015-11-30 author:zzg option:update Task:070
						for (int i = 0; i < list.size(); i++) {
							Map<String, String> inmentmap=ITradeInfoService.trade_investment(list.get(i).get("base_trade_code").toString());
							if (inmentmap.get("view_investment_cont")==null) {
								list.get(i).put("view_investment_cont", "[]");
							}else {
								list.get(i).put("view_investment_cont", JSONArray.fromObject(inmentmap.get("view_investment_cont")));
							}
							
						}
						//date:2015-11-30 author:zzg option:update_end Task:070
						for (int i = 0; i < list.size(); i++) {
							if(list.get(i).get("view_trade_inducont")==null){
								list.get(i).put("view_trade_inducont", new JSONArray());
							}else {
								list.get(i).put("view_trade_inducont", JSONArray.fromObject((list.get(i).get("view_trade_inducont"))));
							}
						}
					}
					
				}else if(logtype.equals("2")){//公司
					map.put("logtype", CommonUtil.findNoteTxtOfXML("Lable-company"));
					int data=updService.findUpdByupdlogUserCount(map);
					if(data>0){
						page.setTotalCount(data);
						map.put("starttime", page.getStartCount());
						map.put("pageSize", page.getPageSize());
						list= updService.findCompanyByupdlogUser(map);
						
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
					}
					
				}else if(logtype.equals("3")){//会议
					int data=0;
					if(opertype.equals("YK-JOIN")){//判断选择参与会议
						data=updService.findJoinMeetingCountByUsercode(userInfo.getSys_user_code());
					}else{
						map.put("logtype", CommonUtil.findNoteTxtOfXML("Lable-meeting"));
						data=updService.findMeetingCountByupdlogUser(map);
					}
					
					if(data>0){
						page.setTotalCount(data);
						map.put("starttime", page.getStartCount());
						map.put("pageSize", page.getPageSize());
						if(opertype.equals("YK-JOIN")){
							list = updService.findJoinMeetingByUsercode(map);
						}else {
							list = updService.findMeetingByupdlogUser(map);
						}
						
						SysUserInfo sysUserInfo = null;
						JSONArray jsonArray=null;
						for (Map<String, Object> meet : list) {
							if(meet!=null){
								sysUserInfo = sysUserInfoService.sysuserbycode(meet.get("createid").toString());
								if(sysUserInfo!=null){
									meet.put("createName", sysUserInfo.getSys_user_name());
								}
								
								if(meet.get("base_meeting_compcont")!=null 
										&& !"".equals(meet.get("base_meeting_compcont").toString())){
									jsonArray= JSONArray.fromObject(meet.get("base_meeting_compcont").toString());
									meet.put("base_meeting_compcont", jsonArray.getJSONObject(0).getString("name"));
								}else {
									meet.put("base_meeting_compcont","");
								}
								
								if(meet.get("base_meeting_invicont")!=null 
										&& !"".equals(meet.get("base_meeting_invicont").toString())){
									jsonArray= JSONArray.fromObject(meet.get("base_meeting_invicont").toString());
									meet.put("base_meeting_invicont", jsonArray.getJSONObject(0).getString("name"));
								}else {
									meet.put("base_meeting_invicont", "");
								}
								
							}
						}
					}
				}else if(logtype.equals("4")){//融资计划
					int data=updService.findFincingCountByUsercode(userInfo.getSys_user_code());
					if(data>0){
						page.setTotalCount(data);
						map.put("starttime", page.getStartCount());
						map.put("pageSize", page.getPageSize());
						
						list=updService.findFincingByUsercode(map);
					}
				}
			}
		} catch (Exception e) {
			logger.error("findUserFoot 方法异常",e);
			e.printStackTrace();
		}
		
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", list!=null?JSONArray.fromObject(list):null);
			resultJSON.put("page", JSONObject.fromObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("findUserFoot() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("findUserFoot 发生异常", e);
			e.printStackTrace();
		}
		
	}
	
}
