package cn.com.sims.action.meeting;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basemeetinginfo.BaseMeetingInfo;
import cn.com.sims.model.basemeetinglabelinfo.BaseMeetingLabelInfo;
import cn.com.sims.model.basemeetingnoteinfo.BaseMeetingNoteInfo;
import cn.com.sims.model.basemeetingparp.BaseMeetingParp;
import cn.com.sims.model.basemeetingrele.BaseMeetingRele;
import cn.com.sims.model.basemeetingshare.BaseMeetingShare;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.meeting.IMeetingAddService;
import cn.com.sims.service.meeting.MeetingService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.service.tradeinfo.ITradeAddService;
import cn.com.sims.util.email.IEmailSenderService;

/**
 * 
 * @author rqq
 * @date 2015-11-05
 */
@Controller
public class MeetingAddAction {
	
	//生成id
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
	
	//系统更新记录
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;
	
	//会议
	@Resource
	IMeetingAddService meetingaddservice;
	
	//添加交易service
	@Resource
	ITradeAddService tradeaddservice;
	
	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	IEmailSenderService emailSenderService;
	
	@Resource
	MeetingService meetingService;
	
	@Resource
	SysUserInfoService sysUserInfoService;
	
	
	private static final Logger logger = Logger.getLogger(MeetingAddAction.class);
	
	
	/**
	 * 添加会议初始化信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 * @author rqq
	 */
	@RequestMapping(value = "addMeetingInfoinit")
	public String addMeetingInfoinit(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype
			) throws Exception {
		logger.info("MeetingAddAction.addMeetingInfoinit()方法开始");
		//初始化：易凯用户
		List<Map<String, String>> YKuserList=null;
		//初始化：筐
		List<Map<String, String>> SysWadList=null;
		//初始化：会议类型
		List<Map<String, String>> meetingTypeList=null;
		
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		try {
		    	
		    YKuserList=meetingaddservice.querysysuserinfo();
		    SysWadList=meetingaddservice.querysyswadinfo();
		    meetingTypeList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
		    
		} catch (Exception e) {
			logger.error("MeetingAddAction.addMeetingInfoinit()方法异常",e);
			e.printStackTrace();
		}
		
		request.setAttribute("YKuserList", JSONArray.fromObject(YKuserList));
		request.setAttribute("SysWadList", JSONArray.fromObject(SysWadList));
		request.setAttribute("meetingTypeList", JSONArray.fromObject(meetingTypeList));
		request.setAttribute("userInfo", net.sf.json.JSONObject.fromObject(userInfo));
		request.setAttribute("nowdate",new Timestamp(new Date().getTime()));
		logger.info("MeetingAddAction.addMeetingInfoinit()方法结束");
		return ConstantUrl.urlMeetingAdd(logintype);
	}
	
	/**选择公司联系人时-根据公司code查询，并实现加载更多
	 * @author rqq
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param compcode:公司code
	 * @param name:公司联系人名称
	 * @param pagenow
	 * @param limit
	 * @throws Exception
	 */
	@RequestMapping(value = "queryentrepreneurlistBycompcode")
	public void queryentrepreneurlistBycompcode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("compcode") String compcode,
			@RequestParam("name") String name,
			Page page,String logintype) throws Exception {
		logger.info("MeetingAddAction.queryentrepreneurlistBycompcode()方法Start");
	
		String message="";
		List<BaseEntrepreneurInfo> list = null;
		try {
		    Map<String, Object> map=new HashMap<String, Object>();
		    //公司code
			if(compcode==null ||"".equals(compcode)){
				map.put("compcode", null);
			}else {
				map.put("compcode", compcode);
			}
			//名称
			if(name==null ||"".equals(name)){
				map.put("name", null);
			}else {
				map.put("name", name);
			}
			
			int total=meetingaddservice.queryEntrepreneurlistnumBycompId(map);
			page.setTotalCount(total);
			map.put("pagestart", page.getStartCount());
			map.put("limit",page.getPageSize());
			list=meetingaddservice.queryEntrepreneurlistBycompId(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
			logger.info("MeetingAddAction.queryentrepreneurlistBycompcode方法end");
		} catch (Exception e) {
			logger.error("MeetingAddAction.queryentrepreneurlistBycompcode 发生异常", e);
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
			logger.error("MeetingAddAction.queryentrepreneurlistBycompcode 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("MeetingAddAction.queryentrepreneurlistBycompcode 发生异常", e);
			e.printStackTrace();
		}
	}
	
	/**添加会议信息
	 * @author rqq
	 * @date 2015-11-04
	 * @param request
	 * @param response
	 * @param meetingdate：会议日期
	 * @param meetingcontent：会议内容
	 * @param investmentInfoList：机构信息
	 * base_investment_code:机构code;base_investment_name:机构name;investmenttype:机构添加类型;
	 * base_investor_code:投资人code;base_investor_name:投资人name;investortype:投资人类型;
	 * base_investor_posiname:投资人职位;
	 * @param compInfoList：公司信息:
	 * base_comp_code:公司code;base_comp_name:公司名称；comptype：操作类型：select,add;
	 * base_entrepreneur_code:公司联系人code;base_entrepreneur_name:公司联系人name;
	 * entrepreneurtype:公司联系人类型;base_entrepreneur_posiname:公司联系人职位;
	 * financplanflag：是否创建公司融资计划；base_financplan_date：公司融资计划计划日期
	 * base_financplan_remindate：公司融资计划提醒日期；base_financplan_cont：公司融资计划内容；
	 * @param parpuserJson：与会者信息，code:用户code,name:用户姓名
	 * @param sharewadJson：分享范围，code:筐code,name:筐名称
	 * @param sharetype :分享类型（1:筐,2:人）
	 * @param meetingtypeJson :会议类型(基金会议，投行会议)
	 * @param noteinfo：会议备注
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "addmeetinginfo")
	public void addmeetinginfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("meetingdate") String meetingdate,
			@RequestParam("meetingcontent") String meetingcontent,
			@RequestParam(value="investmentInfoList",required=false) String investmentInfojson,
			@RequestParam(value="compInfoList",required=false) String compInfojson,
			@RequestParam(value="parpuserJson",required=false) String parpuserJson,
			@RequestParam(value="sharetype",required=false) String sharetype,
			@RequestParam(value="sharewadJson",required=false) String sharewadJson,
			@RequestParam(value="meetingtypeJson",required=false) String meetingtypeJson,
			@RequestParam(value="noteinfo",required=false) String noteinfo,
			String logintype) throws Exception {
		logger.info("MeetingAddAction.addmeetinginfo()方法Start");
	
		String message="success";
		String messagedetail="";
		//会议code
		String meetingcode="";
		List<BaseMeetingLabelInfo> meetingLableList=null;
		try {
		    SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		    	//公司list;
		    List<BaseCompInfo> basecompinfolist=new ArrayList<BaseCompInfo>();
		    
		    	//公司联系人list;
		    List<BaseEntrepreneurInfo> baseentrepreneurlist
		    				=new ArrayList<BaseEntrepreneurInfo>();
		    	//公司联系人关系list;
		    List<BaseCompEntrepreneur> basecompentrepreneurlist
		    			=new ArrayList<BaseCompEntrepreneur>();
		  //公司融资计划list;
		    List<BaseFinancplanInfo> basefinancplaninfolist
		    					=new ArrayList<BaseFinancplanInfo>();
		  //公司融资计划提醒人员list;
		    List<BaseFinancplanEmail> basefinancplanemaillist
		    					=new ArrayList<BaseFinancplanEmail>();
			//机构list;
			List<baseInvestmentInfo> baseinvestmentinfolist=new ArrayList<baseInvestmentInfo>();
			//投资人list
			List<BaseInvestorInfo> baseinvestorinfolist=new ArrayList<BaseInvestorInfo>();
			//机构投资人关系list
			List<BaseInvestmentInvestor> baseinvestmentinvestorlist
								=new ArrayList<BaseInvestmentInvestor>();
			//会议对象
			BaseMeetingInfo basemeetinginfo=null;
			//会议分享list;
			List<BaseMeetingShare> basemeetingsharellist
								=new ArrayList<BaseMeetingShare>();
			//会议参会者list
			List<BaseMeetingParp> basemeetingparplist
								=new ArrayList<BaseMeetingParp>();
			//会议相关机构公司投资人信息list
			List<BaseMeetingRele> basemeetingrelelist
								=new ArrayList<BaseMeetingRele>();
			//会议note对象
			BaseMeetingNoteInfo basemeetingnoteinfo=null;
			
			JSONArray investmentInfoarray=JSONArray.fromObject(investmentInfojson);
			JSONArray comparray=JSONArray.fromObject(compInfojson);
			JSONArray parpuserarray=parpuserJson!=null?JSONArray.fromObject(parpuserJson):null;
			JSONArray sharewadarray=sharewadJson!=null?JSONArray.fromObject(sharewadJson):null;
			JSONArray labelarray=meetingtypeJson!=null?JSONArray.fromObject(meetingtypeJson):null;
			
			List<Map<String, String>> investmentInfoList=investmentInfoarray.subList(0, investmentInfoarray.size());
			List<Map<String, Object>> compInfoList=comparray.subList(0, comparray.size());
			List<Map<String, String>> parpuserList=parpuserarray!=null?parpuserarray.subList(0, parpuserarray.size()):null;
			List<Map<String, String>> shareList=sharewadarray!=null?sharewadarray.subList(0, sharewadarray.size()):null;
			List<Map<String, String>> labelList=labelarray!=null?labelarray.subList(0, labelarray.size()):null;
			
			/**会议对象**/
			basemeetinginfo=new BaseMeetingInfo();
			//会议code
			meetingcode=CommonUtil.PREFIX_MEETING+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.MEETING_TYPE);
			basemeetinginfo.setBase_meeting_code(meetingcode);
		    //会议时间
			basemeetinginfo.setBase_meeting_time(meetingdate);
			//会议内容
			basemeetinginfo.setBase_meeting_content(meetingcontent);
			//排它锁
			basemeetinginfo.setBase_datalock_viewtype(1);
			//PL锁状态
			basemeetinginfo.setBase_datalock_pltype("0");
			//删除标识
			basemeetinginfo.setDeleteflag("0");
			//创建者
			basemeetinginfo.setCreateid(userInfo.getSys_user_code());
			//创建时间
			basemeetinginfo.setCreatetime(CommonUtil.getNowTime_tamp());
			//更新者
			basemeetinginfo.setUpdid(userInfo.getSys_user_code());
			//更新时间
			basemeetinginfo.setUpdtime(basemeetinginfo.getCreatetime());
			
			
			/**分享范围**/
			List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
			//分享范围
			if(sharewadJson!=null && !"".equals(sharewadJson) ){
			    SysWadList.addAll(shareList);
			}
			for(int i=0;i<SysWadList.size();i++){
				BaseMeetingShare basemeetingshare= new BaseMeetingShare();
				Map<String, String> sharemapMap=SysWadList.get(i);
				if(sharemapMap.get("code")!=null ){
					//会议id
				    basemeetingshare.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
					//筐code
				    basemeetingshare.setBase_meeting_sharecode(sharemapMap.get("code").toString().trim());
				    //分享类别
				    if(sharetype==null || sharetype.trim().equals("")){
				    	sharetype="1";//默认类型为筐
				    }
				    basemeetingshare.setBase_meeting_sharetype(sharetype);
				    //删除标识
				    basemeetingshare.setDeleteflag("0");
					//创建者
				    basemeetingshare.setCreateid(userInfo.getSys_user_code());
					//创建时间
				    basemeetingshare.setCreatetime(CommonUtil.getNowTime_tamp());
					//更新者
				    basemeetingshare.setUpdid(userInfo.getSys_user_code());
					//更新时间
				    basemeetingshare.setUpdtime(basemeetingshare.getCreatetime());
					//放置list
				    basemeetingsharellist.add(basemeetingshare);
			    
			}
			}
			
			/**参会者**/
			
			//参会者
			if(parpuserJson!=null && !"".equals(parpuserJson) ){
			    for(int i=0;i<parpuserList.size();i++){
				BaseMeetingParp basemeetingparp= new BaseMeetingParp();
				Map<String, String> basemeetingparpMap=parpuserList.get(i);
				if(basemeetingparpMap.get("code")!=null && basemeetingparpMap.get("name")!=null ){
				//会议id
				    basemeetingparp.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
					//与会者code
				    basemeetingparp.setSys_user_code(basemeetingparpMap.get("code").toString().trim());
					//与会者姓名
				    basemeetingparp.setSys_user_name(basemeetingparpMap.get("name").toString().trim());
					//删除标识
				    basemeetingparp.setDeleteflag("0");
					//创建者
				    basemeetingparp.setCreateid(userInfo.getSys_user_code());
					//创建时间
				    basemeetingparp.setCreatetime(CommonUtil.getNowTime_tamp());
					//更新者
				    basemeetingparp.setUpdid(userInfo.getSys_user_code());
					//更新时间
				    basemeetingparp.setUpdtime(basemeetingparp.getCreatetime());
					//放置list
				    basemeetingparplist.add(basemeetingparp);
			    }
			}
			}

			/**相关机构list**/
			if(investmentInfojson!=null && !"".equals(investmentInfojson) && "success".equals(message)){
			/**机构list**/
			boolean bool;
			boolean boolrele;
			//验证机构信息
			for(int i=0;i<investmentInfoList.size();i++){
				bool=true;
			    Map<String, String> investmentMap=investmentInfoList.get(i);
			    //机构
			    baseInvestmentInfo baseinves = new baseInvestmentInfo();
			    //投资人
			    BaseInvestorInfo baseinvestor=new BaseInvestorInfo();
			    //投资人机构关系
			    BaseInvestmentInvestor investorinfo=new BaseInvestmentInvestor();
			    //会议相关
			    BaseMeetingRele basemeetingrele=new BaseMeetingRele();
			    
			    if (investmentMap.get("investmenttype")!=null&&"add".equals(investmentMap.get("investmenttype").toString().trim())
			    		&&baseinvestmentinfolist!=null && baseinvestmentinfolist.size()>0) {
					//验证重复机构
			    	for (baseInvestmentInfo info : baseinvestmentinfolist) {
						if(info.getBase_investment_name().equals(investmentMap.get("base_investment_name").trim())){
							bool=false;
							//机构code
				    		baseinves.setBase_investment_code(info.getBase_investment_code());
				    		//机构简称
				    		baseinves.setBase_investment_name(info.getBase_investment_name());
							break;
						}
					}
				}
			    //投资机构
			    if(investmentMap.get("investmenttype")!=null 
			    		&& "add".equals(investmentMap.get("investmenttype").toString().trim())
			    		&& investmentMap.get("base_investment_name")!=null
			    		&& !"".equals(investmentMap.get("base_investment_name").toString().trim())){
			    	if(bool==true){
			    		int total=tradeaddservice.queryInvestmentOnlyNamefottrade(investmentMap.get("base_investment_name"));
        				if (total>0) {
        					message="invesnameexsit";
        					messagedetail="机构简称："+investmentMap.get("base_investment_name")+"已存在";
        					break;
        				}else{
        				    // 机构简称
        				    baseinves.setBase_investment_name(investmentMap.get("base_investment_name").toString().trim());
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
			    		
			    }else if(investmentMap.get("investmenttype")!=null 
			    		&& "select".equals(investmentMap.get("investmenttype").toString().trim())
			    			&&  investmentMap.get("base_investment_code")!=null 
			    			&& !"".equals(investmentMap.get("base_investment_code").toString().trim())
			    			&&  investmentMap.get("base_investment_name")!=null 
			    			&& !"".equals(investmentMap.get("base_investment_name").toString().trim())){
			    	    //机构code
			    		baseinves.setBase_investment_code(investmentMap.get("base_investment_code").toString().trim());
			    		//机构简称
			    		baseinves.setBase_investment_name(investmentMap.get("base_investment_name").toString().trim());
			    }
			    	
			    //投资人
			    if(investmentMap.get("investortype")!=null 
			    		&& "add".equals(investmentMap.get("investortype").toString().trim())
			    		&& investmentMap.get("base_investor_name")!=null 
		    			&& !"".equals(investmentMap.get("base_investor_name").toString().trim())){
			    		
				    	//投资人名称
			    		baseinvestor.setBase_investor_name(investmentMap.get("base_investor_name").toString().trim());
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
			    	}else if(investmentMap.get("investortype")!=null 
			    		&& "select".equals(investmentMap.get("investortype").toString().trim())
			    		&&  investmentMap.get("base_investor_code")!=null 
		    			&& !"".equals(investmentMap.get("base_investor_code").toString().trim())
		    			&&  investmentMap.get("base_investor_name")!=null 
		    			&& !"".equals(investmentMap.get("base_investor_name").toString().trim())){
			    	    //投资人code
			    		baseinvestor.setBase_investor_code(investmentMap.get("base_investor_code"));
			    		//投资人姓名
			    		baseinvestor.setBase_investor_name(investmentMap.get("base_investor_name"));
			    	}
			    	
			    //投资人机构关系
			    if(((investmentMap.get("investmenttype")!=null 
			    		&&"add".equals(investmentMap.get("investmenttype").toString().trim()))
				    || (investmentMap.get("investortype")!=null 
				    	&&"add".equals(investmentMap.get("investortype").toString().trim()))
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
                    	//职位名称
                    	if(investmentMap.get("base_investor_posiname")==null 
                    		|| "".equals(investmentMap.get("base_investor_posiname").toString().trim())){
                    		investorinfo.setBase_investor_posiname(null);
                    	}else{
                    		investorinfo.setBase_investor_posiname(investmentMap.get("base_investor_posiname").toString().trim());
                    	}
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
			    /**会议相关机构**/
			    if(basemeetinginfo.getBase_meeting_code()!=null 
				    && !"".equals(basemeetinginfo.getBase_meeting_code())){
			    	boolrele=true;
			    	//判断是否重复添加会议相关机构投资人
			    	for (BaseMeetingRele rele : basemeetingrelelist) {
						if(rele!=null){
							if(baseinvestor.getBase_investor_code()!=null){
								if(baseinves.getBase_investment_code().equals(rele.getBase_meeting_relecode())
										&&rele.getBase_meeting_relepcode()!=null
										&&rele.getBase_meeting_relepcode().equals(baseinvestor.getBase_investor_code())){
									boolrele=false;
									break;
								}
							}else {
								if(rele.getBase_meeting_relepcode()==null
										&&baseinves.getBase_investment_code().equals(rele.getBase_meeting_relecode())){
									boolrele=false;
									break;
								}
							}
						}
					}
			    	
			    	if(boolrele==true){
			    		//会议id
					    basemeetingrele.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
					    //相关类型:１：机构２：公司
					    basemeetingrele.setBase_meeting_rele("1");
					    //相关code
				    	basemeetingrele.setBase_meeting_relecode(baseinves.getBase_investment_code());
				    	//相关code简称
				    	basemeetingrele.setBase_meeting_relename(baseinves.getBase_investment_name());
				    	//相关联系人code
				    	basemeetingrele.setBase_meeting_relepcode(baseinvestor.getBase_investor_code());
				    	//相关联系人姓名
				    	basemeetingrele.setBase_meeting_relepname(baseinvestor.getBase_investor_name());
				    	//相关联系人职位名称
				    	basemeetingrele.setBase_meeting_relepposi(investmentMap.get("base_investor_posiname"));
				    	//删除标识
				    	basemeetingrele.setDeleteflag("0");
	    					//创建者
				    	basemeetingrele.setCreateid(userInfo.getSys_user_code());
	    					//创建时间
				    	basemeetingrele.setCreatetime(CommonUtil.getNowTime_tamp());
	    					//更新者
				    	basemeetingrele.setUpdid(userInfo.getSys_user_code());
	        				//更新时间
				    	basemeetingrele.setUpdtime(basemeetingrele.getCreatetime());
				    	basemeetingrelelist.add(basemeetingrele);
			    	}
			    	
			    }
			}
			}
			
			/**相关公司list**/
			if(compInfoList!=null && !"".equals(compInfoList) && "success".equals(message) ){
			/**公司list**/
			//验证公司信息
			 for (int i=0;i< compInfoList.size();i++){
				Map<String, Object> compmapMap=compInfoList.get(i);
			    //公司
				BaseCompInfo basecompinfo = new BaseCompInfo();
			    //公司联系人
				BaseEntrepreneurInfo baseentrepreneurinfo=new BaseEntrepreneurInfo();
			    //公司联系人关系
				BaseCompEntrepreneur basecompentrepreneur=new BaseCompEntrepreneur();
			    //会议相关
			    BaseMeetingRele basemeetingrele=new BaseMeetingRele();


			    	if(
			    		compmapMap.get("comptype")!=null
			    		&&"add".equals(compmapMap.get("comptype").toString().trim())
			    		&& compmapMap.get("base_comp_name")!=null
			    		&& !"".equals(compmapMap.get("base_comp_name").toString().trim())){
			    	int total=tradeaddservice.queryCompOnlyNamefortrade(compmapMap.get("base_comp_name").toString());
    				if (total>0) {
    					message="compnameexsit";
    					messagedetail="公司简称："+compmapMap.get("base_comp_name")+"已存在";
    					break;
    				}
    				else{
    				    		//公司name
    				    basecompinfo.setBase_comp_name(compmapMap.get("base_comp_name").toString());
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
              		basecompinfolist.add(basecompinfo);
    				}
			    	}
			    	else if(compmapMap.get("comptype")!=null 
			    		&& "select".equals(compmapMap.get("comptype").toString().trim())
			    		&& compmapMap.get("base_comp_code")!=null
			    		&& !"".equals(compmapMap.get("base_comp_code").toString().trim())
			    		&& compmapMap.get("base_comp_name")!=null
			    		&& !"".equals(compmapMap.get("base_comp_name").toString().trim())
			    		){
			    	    	//公司code
			    	basecompinfo.setBase_comp_code(compmapMap.get("base_comp_code").toString().trim());
			    		//公司简称
			    	basecompinfo.setBase_comp_name(compmapMap.get("base_comp_name").toString().trim());
			    	}
			    	
			    	//公司联系人
			    	if(compmapMap.get("entrepreneurtype")!=null 
			    		&& "add".equals(compmapMap.get("entrepreneurtype").toString().trim())
			    		&& compmapMap.get("base_entrepreneur_name")!=null
			    		&& !"".equals(compmapMap.get("base_entrepreneur_name").toString().trim())
			    		){
			    	//公司联系人名称
			    	baseentrepreneurinfo.setBase_entrepreneur_name(compmapMap.get("base_entrepreneur_name").toString().trim());
	                    		//生成公司联系人id
	              String investorcode=CommonUtil.PREFIX_ENTREPRENEUR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.ENTREPRENEUR_TYPE);
	              baseentrepreneurinfo.setBase_entrepreneur_code(investorcode);
	                    		//公司联系人姓名拼音全拼,公司联系人姓名拼音首字母
	              String[] pinYin=CommonUtil.getPinYin(compmapMap.get("base_entrepreneur_name").toString().trim());
					//公司联系人姓名拼音全拼
	              baseentrepreneurinfo.setBase_entrepreneur_namep(pinYin[0]);
                    			//公司联系人姓名拼音首字母
	              baseentrepreneurinfo.setBase_entrepreneur_namepf(pinYin[1]);
	                    		//创建来源
	              baseentrepreneurinfo.setBase_entrepreneur_stem("2");
	        			//当前状态
	              baseentrepreneurinfo.setBase_entrepreneur_estate("2");
	                    		//删除标识
	              baseentrepreneurinfo.setDeleteflag("0");
	                		//创建者
	              baseentrepreneurinfo.setCreateid(userInfo.getSys_user_code());
	                		//创建时间
	              baseentrepreneurinfo.setCreatetime(CommonUtil.getNowTime_tamp());
	                		//更新者
	              baseentrepreneurinfo.setUpdid(userInfo.getSys_user_code());
	                    		//更新时间
	              baseentrepreneurinfo.setUpdtime(baseentrepreneurinfo.getCreatetime());
	              baseentrepreneurlist.add(baseentrepreneurinfo);

			    		}
			    	else	if(compmapMap.get("entrepreneurtype")!=null 
			    		&& "select".equals(compmapMap.get("entrepreneurtype").toString().trim())
			    		&& compmapMap.get("base_entrepreneur_code")!=null
			    		&& !"".equals(compmapMap.get("base_entrepreneur_code").toString().trim())
			    		&& compmapMap.get("base_entrepreneur_name")!=null
			    		&& !"".equals(compmapMap.get("base_entrepreneur_name").toString().trim())
			    		){
			    	    //公司联系人code
			    	baseentrepreneurinfo.setBase_entrepreneur_code(compmapMap.get("base_entrepreneur_code").toString());
			    	  //公司联系人姓名
			    	baseentrepreneurinfo.setBase_entrepreneur_name(compmapMap.get("base_entrepreneur_name").toString());
			    		}
			    	
			    	//公司联系人关系
			    if(((compmapMap.get("comptype")!=null 
			    		&&"add".equals(compmapMap.get("comptype").toString().trim()))
				    || (compmapMap.get("entrepreneurtype")!=null 
				    	&&"add".equals(compmapMap.get("entrepreneurtype").toString().trim()))
				    	&& basecompinfo.getBase_comp_code()!=null
				    	&& !"".equals(basecompinfo.getBase_comp_code())
				    	&& baseentrepreneurinfo.getBase_entrepreneur_code()!=null
				    	&& !"".equals(baseentrepreneurinfo.getBase_entrepreneur_code()))
				    		){
							//公司id
							basecompentrepreneur.setBase_comp_code(basecompinfo.getBase_comp_code());
                    					//公司联系人id
							basecompentrepreneur.setBase_entrepreneur_code(baseentrepreneurinfo.getBase_entrepreneur_code());
                    					//在职状态
							basecompentrepreneur.setBase_entrepreneur_state("0");
                    					//职位名称
                    	if(compmapMap.get("base_entrepreneur_posiname")==null 
                    		|| "".equals(compmapMap.get("base_entrepreneur_posiname").toString().trim())){
                    	basecompentrepreneur.setBase_entrepreneur_posiname(null);
                    					}
                    	else{
                    	basecompentrepreneur.setBase_entrepreneur_posiname(compmapMap.get("base_entrepreneur_posiname").toString().trim());
                    					}
                    					//删除标识
                    	basecompentrepreneur.setDeleteflag("0");
                					//创建者
                    	basecompentrepreneur.setCreateid(userInfo.getSys_user_code());
                					//创建时间
                    	basecompentrepreneur.setCreatetime(CommonUtil.getNowTime_tamp());
                					//更新者
                    	basecompentrepreneur.setUpdid(userInfo.getSys_user_code());
                    					//更新时间
                    	basecompentrepreneur.setUpdtime(basecompentrepreneur.getCreatetime());
                    	
                    	basecompentrepreneur.setBase_comp_name(basecompinfo.getBase_comp_name());
                    	basecompentrepreneur.setBase_entrepreneur_name(baseentrepreneurinfo.getBase_entrepreneur_name());
                    	basecompentrepreneurlist.add(basecompentrepreneur);
			    }
			    //公司融资计划
			    if(compmapMap.get("financplanflag")!=null 
			    		&& (Boolean)compmapMap.get("financplanflag")){
				BaseFinancplanInfo basefinancplaninfo=new BaseFinancplanInfo();
				
				String plancode=CommonUtil.PREFIX_FINANCPLAN+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.FINANCPLAN_TYPE);//生成一个公司融资计划code
				//融资计划code
				basefinancplaninfo.setBase_financplan_code(plancode);
				//公司code
				basefinancplaninfo.setBase_comp_code(basecompinfo.getBase_comp_code());
				//公司简称
				basefinancplaninfo.setBase_comp_name(basecompinfo.getBase_comp_name());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				//融资计划计划日期
				 Date financplantime=new Date(compmapMap.get("base_financplan_date").toString().trim().replace("-","/").replace("年","/").replace("月","/").replace("日","/"));
				basefinancplaninfo.setBase_financplan_date(Long.toString(financplantime.getTime()));
			    //融资计划提醒日期
				Date emailtime=new Date(compmapMap.get("base_financplan_remindate").toString().trim().replace("-","/").replace("年","/").replace("月","/").replace("日","/"));
				basefinancplaninfo.setBase_financplan_remindate(format.format(emailtime));
				 //融资计划内容
				basefinancplaninfo.setBase_financplan_cont(compmapMap.get("base_financplan_cont").toString().trim());
				basefinancplaninfo.setBase_financplan_sendef("0");
				basefinancplaninfo.setBase_financplan_sendwf("0");
				basefinancplaninfo.setBase_financplan_sendestate("0");
				basefinancplaninfo.setBase_financplan_sendwstate("0");
				//删除标识
				basefinancplaninfo.setDeleteflag("0");
    				//创建者
				basefinancplaninfo.setCreateid(userInfo.getSys_user_code());
    				//创建时间
				basefinancplaninfo.setCreatetime(CommonUtil.getNowTime_tamp());
    				//更新者
				basefinancplaninfo.setUpdid(userInfo.getSys_user_code());
        			//更新时间
				basefinancplaninfo.setUpdtime(basefinancplaninfo.getCreatetime());
				basefinancplaninfolist.add(basefinancplaninfo);
				for(int m=0;m<basemeetingparplist.size();m++){
				 BaseFinancplanEmail basefinancplanemail=new BaseFinancplanEmail();
				 /**
					 * 添加融资计划发送邮件信息
					 */
				 basefinancplanemail.setBase_financplan_code(plancode);
				 //用户id
				 basefinancplanemail.setBase_financplan_email(basemeetingparplist.get(m).getSys_user_code());
				 basefinancplanemail.setBase_financplan_sendwstate("0");
				//删除标识
				 basefinancplanemail.setDeleteflag("0");
	    				//创建者
				 basefinancplanemail.setCreateid(userInfo.getSys_user_code());
	    				//创建时间
				 basefinancplanemail.setCreatetime(CommonUtil.getNowTime_tamp());
	    				//更新者
				 basefinancplanemail.setUpdid(userInfo.getSys_user_code());
	        			//更新时间
				 basefinancplanemail.setUpdtime(basefinancplanemail.getCreatetime());
				 //提醒时间
				 basefinancplanemail.setBase_financplan_remindate(basefinancplaninfo.getBase_financplan_remindate());
				 basefinancplanemaillist.add(basefinancplanemail);
				}
			    }
			    /**会议相关公司**/
			    if(basemeetinginfo.getBase_meeting_code()!=null 
				    && !"".equals(basemeetinginfo.getBase_meeting_code())){
				
				    //会议id
				    basemeetingrele.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
				    //相关类型:１：机构２：公司
				    basemeetingrele.setBase_meeting_rele("2");
				    //相关code
			    	basemeetingrele.setBase_meeting_relecode(basecompinfo.getBase_comp_code());
			    	 //相关code简称
			    	basemeetingrele.setBase_meeting_relename(basecompinfo.getBase_comp_name());
			    	  //相关联系人code
			    	basemeetingrele.setBase_meeting_relepcode(baseentrepreneurinfo.getBase_entrepreneur_code());
			    	//相关联系人姓名
			    	basemeetingrele.setBase_meeting_relepname(baseentrepreneurinfo.getBase_entrepreneur_name());
			    	//相关联系人职位名称
			    	basemeetingrele.setBase_meeting_relepposi(compmapMap.get("base_entrepreneur_posiname").toString());
			    	//删除标识
			    	basemeetingrele.setDeleteflag("0");
    					//创建者
			    	basemeetingrele.setCreateid(userInfo.getSys_user_code());
    					//创建时间
			    	basemeetingrele.setCreatetime(CommonUtil.getNowTime_tamp());
    					//更新者
			    	basemeetingrele.setUpdid(userInfo.getSys_user_code());
        				//更新时间
			    	basemeetingrele.setUpdtime(basemeetingrele.getCreatetime());
			    	basemeetingrelelist.add(basemeetingrele);
			    }
			}
			}
			
			
			
			/**会议note**/
			if(noteinfo!=null && !"".equals(noteinfo) && "success".equals(message)){
			  //生成一个会议备注code
				String notecode=CommonUtil.PREFIX_MEETINGNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.TRADENOTE_TYPE);
				basemeetingnoteinfo=new BaseMeetingNoteInfo() ;
				//会议id
				basemeetingnoteinfo.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
				//会议noteid
				basemeetingnoteinfo.setBase_meetingnote_code(notecode);
				//创建者用户姓名
				basemeetingnoteinfo.setSys_user_name(userInfo.getSys_user_name());
				//内容
				basemeetingnoteinfo.setBase_invesnote_content(noteinfo);
				//删除标识
				basemeetingnoteinfo.setDeleteflag("0");
				//创建者
				basemeetingnoteinfo.setCreateid(userInfo.getSys_user_code());
				//创建时间
				basemeetingnoteinfo.setCreatetime(CommonUtil.getNowTime_tamp());
				//更新者
				basemeetingnoteinfo.setUpdid(userInfo.getSys_user_code());
				//更新时间
				basemeetingnoteinfo.setUpdtime(basemeetingnoteinfo.getCreatetime());
				
			}
			
			if(labelList!=null&&labelList.size()>0){
				meetingLableList=new ArrayList<BaseMeetingLabelInfo>();
				BaseMeetingLabelInfo info=null;
				for (Map<String, String> map : labelList) {
					if(map!=null){
						info=new BaseMeetingLabelInfo();
						info.setBase_meeting_code(basemeetinginfo.getBase_meeting_code());
						info.setSys_label_code(CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
						info.setSys_labelelement_code(map.get("code"));
						info.setCreateid(userInfo.getSys_user_code());
						info.setDeleteflag("0");
						info.setCreatetime(CommonUtil.getNowTime_tamp());
						info.setUpdid(userInfo.getSys_user_code());
						info.setUpdtime(info.getCreatetime());
						meetingLableList.add(info);
					}
				}
			}
			
			
			if("success".equals(message)){
			    meetingaddservice.tranModifyaddmeetinginfo(
				    basecompinfolist, 
				    basefinancplaninfolist,basefinancplanemaillist,
				    baseentrepreneurlist,
				    basecompentrepreneurlist, baseinvestmentinfolist, baseinvestorinfolist, 
				    baseinvestmentinvestorlist, basemeetinginfo, basemeetingsharellist, 
				    basemeetingparplist, basemeetingrelelist, basemeetingnoteinfo,meetingLableList);
			
			

			
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
					"创建会议时,添加机构["+baseinvestmentinfo.getBase_investment_name()+"]",
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
					"创建会议时,添加投资人["+baseinvestorinfo.getBase_investor_name()+"]",
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
			/*添加交易时添加投资人机构关系修改系统更新记录*/
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
					"创建会议时,添加投资人["+baseinvestmentinvestor.getBase_investor_name()+"]的所属机构"
					+"["+baseinvestmentinvestor.getBase_investment_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			

			//添加公司更新记录
			for(int i=0;i<basecompinfolist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
			    BaseCompInfo basecompinfo=basecompinfolist.get(i);
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
					"创建时会议时,添加公司["+basecompinfo.getBase_comp_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
		
			
			//添加公司联系人关系更新记录
			for(int i=0;i<basecompentrepreneurlist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
			    BaseCompEntrepreneur basecompentrepreneur=basecompentrepreneurlist.get(i);
			/*添加交易日期修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
				CommonUtil.findNoteTxtOfXML("Lable-company"),
				CommonUtil.findNoteTxtOfXML("Lable-company-name"),
				basecompentrepreneur.getBase_comp_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
					CommonUtil.findNoteTxtOfXML("compPeopleUpdate"),
					"",
					"创建会议时,添加公司联系人["+basecompentrepreneur.getBase_entrepreneur_name()+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			//融资计划更新记录
			for(int i=0;i<basefinancplaninfolist.size();i++){
			    Timestamp time = new Timestamp(new Date().getTime());
			    BaseFinancplanInfo basefinancplaninfo=basefinancplaninfolist.get(i);
		    	/*添加系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-company"),
					CommonUtil.findNoteTxtOfXML("Lable-company-name"),
					basefinancplaninfo.getBase_comp_code(), 
					basefinancplaninfo.getBase_comp_name(),
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
					CommonUtil.findNoteTxtOfXML("addFinancplan"),
					"",
					"添加["+basefinancplaninfo.getBase_financplan_cont()+"]",
					logintype,
					userInfo.getSys_user_code(), 
					time,
					"",
					time);
				}
			//添加会议更新记录
			if(basemeetinginfo!=null && !"".equals(basemeetinginfo)){
			    Timestamp time = new Timestamp(new Date().getTime());
			/*添加会议修改系统更新记录*/
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-meeting"),
					CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
					basemeetinginfo.getBase_meeting_code(), 
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
					CommonUtil.findNoteTxtOfXML("addMeeting"),
					"",
					"添加会议",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(),
					time);
				}
			
			}
			
			//推送会议通知邮件
			if(message.equals("success")){
				List<SysUserInfo> emaiList=new ArrayList<SysUserInfo>();
//				List<SysUserInfo> baskList = null;
				SysUserInfo sysUserInfo = null;
				boolean bool = true;
//				if(shareList!=null && shareList.size()>0){
//					for (Map<String, String> map : shareList) {
//						if(sharetype.equals("1")){//分享筐
//							baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//							if(baskList!=null && baskList.size()>0){
//								for (SysUserInfo sysuser : baskList) {
//									if(sysuser!=null
//											&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//										for (SysUserInfo emailinfo : emaiList) {
//											if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//												bool=false;
//												break;
//											}
//										}
//										if(bool==true){
//											emaiList.add(sysuser);
//										}else {
//											bool=true;
//										}
//										
//									}
//								}
//							}
//						}else if(sharetype.equals("2")
//								&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//							sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//							if(sysUserInfo!=null){
//								emaiList.add(sysUserInfo);
//							}
//							
//						}
//					}
//				}
				//匹配参会人
				if(parpuserList!=null && parpuserList.size()>0){
					for (Map<String, String> map : parpuserList) {
						if (map!=null
								&& !map.get("code").equals(userInfo.getSys_user_code())) {
							for (SysUserInfo emailinfo : emaiList) {
								if(emailinfo!=null && emailinfo.getSys_user_code().equals(map.get("code"))){
									bool=false;
									break;
								}
							}
							if(bool==true){
								sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
								if(sysUserInfo!=null){
									emaiList.add(sysUserInfo);
								}
							}else {
								bool=true;
							}
							
						}
					}
				}
				//推送会议通知
				if(emaiList!=null && emaiList.size()>0){
					String address=request.getScheme()+"://"+request.getServerName()
					+":"+request.getServerPort()+request.getContextPath()+"/"+
					"meeting_info.html?meetingcode="+basemeetinginfo.getBase_meeting_code()+"&logintype=";
					StringBuffer sBuffer=new StringBuffer();
					if(investmentInfoList.size()>0||compInfoList.size()>0){
						sBuffer.append("关于");
						if(investmentInfoList.size()>0){
							sBuffer.append("投资机构("+investmentInfoList.get(0).get("base_investment_name").toString()+")");
							if(compInfoList.size()>0){
								sBuffer.append("，");
							}
						}
						if(compInfoList.size()>0){
							sBuffer.append("公司("+compInfoList.get(0).get("base_comp_name").toString()+")");
						}
						sBuffer.append("的");
					}
					emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingMessage.ftl",sBuffer.toString(),emaiList);
				}
				
			}
			
			
		} catch (Exception e) {
			logger.error("MeetingAddAction.addmeetinginfo 发生异常", e);
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
			resultJSON.put("meetingcode", meetingcode);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("MeetingAddAction.addmeetinginfo 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("MeetingAddAction.addmeetinginfo 发生异常", e);
			e.printStackTrace();
		}
	}
}