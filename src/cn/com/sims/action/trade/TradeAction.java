package cn.com.sims.action.trade;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;


/**
 * 
 * @author zzg
 *@date 2015-10-20
 */
@Controller
public class TradeAction {
	private static final Logger logger = Logger
			.getLogger(TradeAction.class);
	@Resource
	ISysLabelelementInfoService dic;//字典
	@Resource
	ITradeInfoService ITradeInfoService;
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	/**
	 * 跳转到 交易搜索页面
	 * @author zzg
	 * @date 2015-10-13
	 */
		@RequestMapping(value = "trade_search")
		public String trade_search(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam(value="backtype",required=false)String backtype
				) throws Exception {
			logger.info("TradeAction.trade_search()方法Start");
			List<Map<String, String>> induList=null;//字典 行业
			List<Map<String, String>> stageList=null;//字典 阶段
			try {
				induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));//查询行业
				stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-trastage"));//查询投资阶段
			} catch (Exception e) {
				logger.error("TradeAction.trade_search() 发生异常", e);
			}
			request.setAttribute("induList", JSONArray.fromObject(induList));
			request.setAttribute("stageList", JSONArray.fromObject(stageList));
			request.setAttribute("backtype", backtype);
			String Url=ConstantUrl.tradeserach(logintype);
			logger.info("TradeAction.trade_search()方法结束");
			return Url;
		}
		/**近期交易多条件检索
		 * @author zzg
		 * @date 2015-10-21
		 * @param request
		 * @param response
		 * @param logintype
		 * @param starttime
		 * @param endtime
		 * @param induList
		 * @param stageList
		 * @param page
		 * @throws Exception
		 */
		@RequestMapping(value = "trade_searchlist")
		public void trade_searchlist(HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam(value="type",required=false)String type,
				@RequestParam(value="starttime",required=false)String starttime,
				@RequestParam(value="endtime",required=false)String endtime,
				@RequestParam(value="induList",required=false)String induList,
				@RequestParam(value="stageList",required=false)String stageList,
				@RequestParam(value="datearea",required=false)int datearea,
				Page page
				) throws Exception {
			logger.info("TradeAction.trade_searchlist()方法Start");
			SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			String message;
			int list_total=0;
			
			Map<String, Object> uplogMap=new HashMap<String, Object>();
			Map<String, String> labelMap=null;
			SysLabelelementInfo lable=null;
			
			JSONArray iarray=JSONArray.fromObject(induList);
			JSONArray sarray=JSONArray.fromObject(stageList);
			List<String> ilist=iarray.subList(0, iarray.size());
			List<String> slist=sarray.subList(0, sarray.size());
			List<Map<String, Object>> list=null;
			HashMap<String, Object> map=new HashMap<String, Object>();
			// 2015-12-22 zzg 修改 增加近期筛选字段 start
			map.put("induList", ilist);
			map.put("stageList", slist);
			if (datearea>0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化对象.
				Date sdate=new Date();
				Calendar calendar = Calendar.getInstance();//日历对象
				  calendar.setTime(sdate);//设置当前日期
				  calendar.add(Calendar.MONTH, -datearea);//减月份
				map.put("endtime", sdf.format(sdate));
				map.put("starttime", sdf.format(calendar.getTime()));
			}else {
				map.put("starttime", starttime);
				map.put("endtime", endtime);
			}
			// 2015-12-22 zzg 修改 增加近期筛选字段 end
		
		try {
			list_total=ITradeInfoService.tradesearchlist_num(map);
			page.setTotalCount(list_total);
			map.put("start", page.getStartCount());
			map.put("size", page.getPageSize());
			list=(List<Map<String, Object>>) ITradeInfoService.tradesearchlist(map);
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
			if (list.size()==0) {
				message="nomore";
			}else {
				message="success";
			}
			if((starttime!=null&&!starttime.equals(""))||(endtime!=null&&!endtime.equals(""))){
				labelMap = new HashMap<String, String>();
				labelMap.put("开始时间", starttime);
				labelMap.put("结束时间", endtime);
				uplogMap.put("时间", labelMap);
			}
			if(datearea>0){
				labelMap = new HashMap<String, String>();
				labelMap.put("月数", String.valueOf(datearea));
				uplogMap.put("近期", labelMap);
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
					uplogMap.put("关注行业", labelMap);
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
			if(type!=null && type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-trading"),
						CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("tradae"),
						"",
						JSONArray.fromObject(uplogMap).toString(),
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
			logger.info("TradeAction.trade_searchlist()方法end");
	
		} catch (Exception e) {
			message="error";
			logger.error("TradeAction.trade_searchlist() 发生异常", e);
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
			logger.error("TradeAction.trade_searchlist() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("TradeAction.trade_searchlist() 发生异常", e);
			e.printStackTrace();
		}
		}
}
