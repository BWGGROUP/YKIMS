package cn.com.sims.action.meeting;

import java.io.File;
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
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basemeetingfile.BaseMeetingFile;
import cn.com.sims.model.basemeetinginfo.BaseMeetingInfo;
import cn.com.sims.model.basemeetinglabelinfo.BaseMeetingLabelInfo;
import cn.com.sims.model.basemeetingnoteinfo.BaseMeetingNoteInfo;
import cn.com.sims.model.basemeetingparp.BaseMeetingParp;
import cn.com.sims.model.basemeetingrele.BaseMeetingRele;
import cn.com.sims.model.basemeetingshare.BaseMeetingShare;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.service.tradeinfo.ITradeAddService;
import cn.com.sims.service.basemeetingfile.IBaseMeetingFileService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.investment.investmentDetailInfoService.IInvestmentDetailInfoService;
import cn.com.sims.service.meeting.IMeetingAddService;
import cn.com.sims.service.meeting.MeetingService;
import cn.com.sims.util.email.IEmailSenderService;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Controller
public class MeetingAction {
	
	@Resource
	MeetingService meetingService;
	//会议
	@Resource
	IMeetingAddService meetingaddservice;
	
	@Resource
	ISysLabelelementInfoService dic;//字典
	
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	//添加交易service
	@Resource
	ITradeAddService tradeaddservice;
	
	@Resource
	IEmailSenderService emailSenderService;
	
	@Resource
	SysUserInfoService sysUserInfoService;
	
	@Resource
	IBaseMeetingFileService meetingFileService;
	
	@Resource
	ICompanyDetailService companyDetailService;
	@Resource
	IInvestmentDetailInfoService iInvestmentDetailInfoService;//投资机构详情
	// 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
	private static final Logger logger = Logger
			.getLogger(MeetingAction.class);
	
	
	/**
	 * 跳转到会议搜索页面
	 * @author zzg
	 * @date 2015-10-13
	 */
	@RequestMapping(value = "meeting_search")
	public String meeting_search(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="backtype",required=false)String backtype
			) throws Exception {
		String Url=ConstantUrl.meettingsearch(logintype);
		List<Map<String, String>> typeLabelList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
		request.setAttribute("typeLabelList", JSONArray.fromObject(typeLabelList));
		request.setAttribute("backtype", backtype);
		return Url;
	}
	/**
	 * 通过输入模糊匹配系统用户
	 * @author zzg
	 * @date 2015-10-14
	 */
	@RequestMapping(value = "sysuserbyname")
	public void sysuserbyname(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("name") String name,@RequestParam(value="logintype",required=false)String logintype) throws Exception{
		logger.info("MeetingAction.sysuserbyname()方法Start");
		List<SysUserInfo> list=null;
		if ("".equals(name)) {
			name=null;
		}
		String message;
		try {
			list=sysUserInfoService.sysuserbyname(name);
			logger.info("MeetingAction.sysuserbyname()方法end");
			if(list.size()>0){
				message="success";
			}else {
				message="nomore";
			}
	
		} catch (Exception e) {
			logger.error("MeetingAction.sysuserbyname() 发生异常", e);
			e.printStackTrace();
			message="error";
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
			logger.error("CompanyCommonAction.companylistbyname() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("CompanyCommonAction.companylistbyname() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**根据相关投资机构 公司 记录人 投资人 筛选会议列表
	 * @author zzg
	 * @date 20155-10-14
	 * @throws Exception
	 */
	@RequestMapping(value = "screenmeetinglist")
	public void screenmeetinglist(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value="orgaincode",required=false) String orgaincode,
			@RequestParam(value="companycode",required=false) String companycode,
			@RequestParam(value="recordcode" ,required=false) String recordcode,
			@RequestParam(value="investorcode" ,required=false) String investorcode,
			@RequestParam(value="meetingtype" ,required=false) String meetingtype,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="type",required=false)String type ,
			Page page) throws Exception{
		logger.info("MeetingAction.screenmeetinglist()方法Start");
		String message;
		String roottype="noroot";
		List<ViewMeetingRele> list=null;
		SysUserInfo sysUserInfo = null;
		JSONArray jsonArray=null;
		Map<String, Object> uplogMap=new HashMap<String, Object>();
		Map<String, String> labelMap=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		
		int list_total=0;
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		map.put("orgaincode", orgaincode);
		map.put("companycode", companycode);
		map.put("recordcode", recordcode);
		map.put("investorcode", investorcode);
		map.put("meetingtype", meetingtype);
		map.put("sysUserWadslisList", userInfo.getSysUserWadslisList());
		map.put("loginuserid", userInfo.getSys_user_code());
		try {
			list_total=meetingService.screenlist_num(map);
	
			page.setTotalCount(list_total);
			map.put("start", page.getStartCount());
			map.put("size", page.getPageSize());
			list=meetingService.screenlist(map);
			if (list.size()>0) {
				message="success";
				/*duwenjie 翻译创建人名称 */
				for (ViewMeetingRele vmeeting : list) {
					if(vmeeting!=null){
						sysUserInfo = sysUserInfoService.sysuserbycode(vmeeting.getCreateid());
						if(sysUserInfo!=null){
							vmeeting.setCreateName(sysUserInfo.getSys_user_name());
						}
						
						if(vmeeting.getBase_meeting_compcont()!=null&&!vmeeting.getBase_meeting_compcont().equals("")){
							jsonArray= JSONArray.fromObject(vmeeting.getBase_meeting_compcont());
//							for (int i = 0; i < jsonArray.size(); i++) {
//								jsonObject=jsonArray.getJSONObject(i);
//								if(jsonObject!=null){
//									content+=jsonObject.getString("name")+",";
//								}
//							}
							vmeeting.setBase_meeting_compcont(jsonArray.getJSONObject(0).getString("name"));
						}
						
						if(vmeeting.getBase_meeting_invicont()!=null&&!vmeeting.getBase_meeting_invicont().equals("")){
							jsonArray= JSONArray.fromObject(vmeeting.getBase_meeting_invicont());
//							for (int i = 0; i < jsonArray.size(); i++) {
//								jsonObject=jsonArray.getJSONObject(i);
//								if(jsonObject!=null){
//									content+=jsonObject.getString("name")+",";
//								}
//							}
							vmeeting.setBase_meeting_invicont(jsonArray.getJSONObject(0).getString("name"));
						}
						/** 判断用户是否为会议查看超级权限，会议是否分享给基金权限筐，已判断用户是否有权限查看会议*/
						boolean bool=false;
						List<SysUserWad> wadlist=new ArrayList<SysUserWad>();
						wadlist.addAll(userInfo.getSysUserWadslisList());
						for (int i = 0; i < wadlist.size(); i++) {
							//判断是否包含会议查看超级权限
							if(wadlist.get(i).getSys_wad_code().equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))){
								wadlist.remove(i);
								bool=true;
								i--;
							}
						}
						if(bool==true){
							//判断会议是否分享给基金权限筐，修改包含超级会议查看权限用户查看会议的权限
							bool=meetingService.findMeetingJuri(vmeeting.getBase_meeting_code(), CommonUtil.findNoteTxtOfXML("JURI-FUND"));
							if(bool==true){
								map=new HashMap<String, Object>();
								map.put("sysUserWadslisList", wadlist);
								map.put("loginuserid", userInfo.getSys_user_code());
								map.put("meetingcode", vmeeting.getBase_meeting_code());
								
								String data=meetingService.screenloginInfo(map);
								if(data.equals("0")){
									vmeeting.setVisible("0");
								}
							}
						}
					/**判断查看会议权限－－－－结束*/	
					}
				}
			}else {
				message="nomore";
			}
			
			if(message.equals("success")){
				if(userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
					roottype="root";
				}
			}
			
			if(orgaincode!=null&&!orgaincode.equals("")){
				viewInvestmentInfo info=null;//定义投资机构详情
				info=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgaincode);//投资机构详情(业务层)
				if(info!=null){
					labelMap=new HashMap<String, String>();
					labelMap.put(info.getBase_investment_code(), info.getBase_investment_name());
					uplogMap.put("机构", labelMap);
				}
			}
			if(companycode!=null && !companycode.equals("")){
				//公司详细信息（业务数据）
				viewCompInfo viewCompInfo = null;
				//根据公司ｉｄ查询公司信息
				viewCompInfo=companyDetailService.findCompanyDeatilByCode(companycode);
				if(viewCompInfo!=null){
					labelMap=new HashMap<String, String>();
					labelMap.put(viewCompInfo.getBase_comp_code(), viewCompInfo.getBase_comp_name());
					uplogMap.put("公司", labelMap);
				}
			}
			if(recordcode!=null && !recordcode.equals("")){
				sysUserInfo = sysUserInfoService.sysuserbycode(recordcode);
				if(sysUserInfo!=null){
					labelMap=new HashMap<String, String>();
					labelMap.put(sysUserInfo.getSys_user_code(),sysUserInfo.getSys_user_name());
					uplogMap.put("记录人", labelMap);
				}
			}
			if(meetingtype!=null && !meetingtype.equals("")){
//				labelMap=new HashMap<String, String>();
				uplogMap.put("会议类型", meetingtype);
			}
			if(type!=null && type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-meeting"),
						CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("meeting"),
						"",
						JSONArray.fromObject(uplogMap).toString(),
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
			logger.info("MeetingAction.screenmeetinglist()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("MeetingAction.screenmeetinglist() 发生异常", e);
			e.printStackTrace();
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("roottype", roottype);
			resultJSON.put("list", JSONArray.fromObject(list));
			resultJSON.put("page", JSONObject.fromObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("MeetingAction.screenmeetinglist() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("MeetingAction.screenmeetinglist() 发生异常", e);
			e.printStackTrace();
		}
		
	}
	/**
	 * 跳转到会议详情页面
	 * @author zzg
	 * @date 2015-10-13
	 */
		@RequestMapping(value = "meeting_info")
		public String meeting_info(HttpServletRequest request,
				HttpServletResponse response,
				@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam("meetingcode") String meetingcode,
				@RequestParam(value="backtype",required=false)String backtype
				) throws Exception {
			logger.info("MeetingAction.meeting_info()方法Start");
			ViewMeetingRele viewMeetingRele=null;
			Map<String, Object> map=null;
			String Url="";
			try {
				SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					String data="0";
					/** 判断用户是否为会议查看超级权限，会议是否分享给基金权限筐，已判断用户是否有权限查看会议*/
					boolean bool=false;
					List<SysUserWad> wadlist=new ArrayList<SysUserWad>();
					wadlist.addAll(userInfo.getSysUserWadslisList());
					for (int i = 0; i < wadlist.size(); i++) {
						//判断是否包含会议查看超级权限
						if(wadlist.get(i).getSys_wad_code().equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))){
							wadlist.remove(i);
							bool=true;
							i--;
						}
					}
					
					map=new HashMap<String, Object>();
					map.put("loginuserid", userInfo.getSys_user_code());
					map.put("meetingcode", meetingcode);
					
					if(bool==true){
						//判断会议是否分享给基金权限筐
						bool=meetingService.findMeetingJuri(meetingcode, CommonUtil.findNoteTxtOfXML("JURI-FUND"));
						if(bool==true){
							map.put("sysUserWadslisList", wadlist);
							data=meetingService.screenloginInfo(map);
						}else{
							map.put("sysUserWadslisList", userInfo.getSysUserWadslisList());
							data=meetingService.screenloginInfo(map);
						}
					}else{
						map.put("sysUserWadslisList", wadlist);
						data=meetingService.screenloginInfo(map);
					}
				/**判断查看会议权限－－－－结束*/
					if(data.equals("1")){
						viewMeetingRele=meetingService.viewmeetingreleBycode(meetingcode);
						if(viewMeetingRele!=null && userInfo!=null){
							/*判断当前用户是否为会议记录人*/
							if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
									||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
								viewMeetingRele.setUserstatus("1");
							}else {
								viewMeetingRele.setUserstatus("0");
							}
						}
						
						request.setAttribute("viewMeetingRele", JSONObject.fromObject(viewMeetingRele));
						request.setAttribute("meetingcode", meetingcode);
						request.setAttribute("nowdate",new Timestamp(new Date().getTime()));
						request.setAttribute("backtype", backtype);
						
						Url=ConstantUrl.meettinginfo(logintype);
					}else{
						request.setAttribute("message", "noroot");
						Url=ConstantUrl.meettingsearch(logintype);
					}
					
					
					
				}
				
			} catch (Exception e) {
				logger.error("MeetingAction.meeting_info() 发生异常", e);
				e.printStackTrace();
			}
			return Url;
		}
		
		
		
		
		
		
		
		/**
		 * 初始加载会议详情
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetingcode
		 * @throws Exception
		 */
		@RequestMapping(value = "initloadmeetinginfo")
		public void initloadmeetinginfo(HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam("meetingcode") String meetingcode) throws Exception {
			logger.info("MeetingAction.initloadmeetinginfo()方法Start");
			String message="success";
			ViewMeetingRele viewMeetingRele=null;
			List<BaseMeetingNoteInfo> baseMeetingNoteInfos=null;
			//duwenjie 2016-3-4 添加编辑分享  初始化：筐
			List<Map<String, String>> SysWadList=null;
			//初始化：易凯用户
			List<Map<String, String>> YKuserList=null;
			List<Map<String, String>> userShareList=null;
			List<Map<String, String>> sysl=null;
			List<Map<String, String>> typeLabelList=null;
			BaseMeetingInfo meetingInfo= null;
			List<BaseMeetingFile> meetingFile=null;
			long version=-1;
			try {
				viewMeetingRele=meetingService.viewmeetingreleBycode(meetingcode);
				if (viewMeetingRele!=null) {
					meetingFile=meetingFileService.findMeetingFileByMeetingcode(meetingcode);
					
					baseMeetingNoteInfos=meetingService.basemeetingnoteBymeetcode(meetingcode);
					/*dwj 2016-3-4 查询分享权限*/
					SysWadList=meetingaddservice.querysyswadinfo();
				    YKuserList=meetingaddservice.querysysuserinfo();
				    typeLabelList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
				    
					//会议分享权限
					userShareList=meetingService.findMeetShareByMeetingCode(meetingcode,viewMeetingRele.getBase_meeting_sharetype());
					//系统全部超级权限
					sysl=meetingaddservice.querysupersyswadinfo();
					if(sysl!=null && userShareList!=null){
						String code="";
						for (int i=0;i<userShareList.size();i++) {
							if(userShareList.get(i)!=null){
								code=userShareList.get(i).get("code");
								for (Map<String, String> smap : sysl) {
									if(smap!=null&& smap.get("code").equals(code)){
										userShareList.remove(i);
										i--;
									}
								}
							}
						}
					}
					meetingInfo = meetingService.findBaseMeetingInfoByCode(meetingcode);
					if(meetingInfo!=null){
						version=meetingInfo.getBase_datalock_viewtype();
					}
				}
				
				logger.info("MeetingAction.initloadmeetinginfo()方法end");
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.initloadmeetinginfo() 发生异常", e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("baseMeetingNoteInfos", JSONArray.fromObject(baseMeetingNoteInfos));
				resultJSON.put("SysWadList", JSONArray.fromObject(SysWadList));
				resultJSON.put("YKuserList", JSONArray.fromObject(YKuserList));
				resultJSON.put("UserShareList", JSONArray.fromObject(userShareList));
				resultJSON.put("typeLabelList", JSONArray.fromObject(typeLabelList));
				resultJSON.put("meetingFile", JSONArray.fromObject(meetingFile));
				resultJSON.put("version", version);
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.initloadmeetinginfo() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.initloadmeetinginfo() 发生异常", e);
				e.printStackTrace();
			}
			
		}
		
		
		/**
		 * 根据会议notecode删除会议备注
		 * @author zzg
		 * @date 2015-10-16
		 */
		@RequestMapping(value = "meetingnote_delet")
		public void meetingnote_delet(HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam("notecode") String notecode) throws Exception {
			logger.info("MeetingAction.meetingnote_delet()方法Start");
//			SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			String message;
			try {
				int i=meetingService.meetingnote_del(notecode);
				if (i==1) {
//					/* 添加系统更新记录 */
//					baseUpdlogInfoService.insertUpdlogInfo(
//							CommonUtil.findNoteTxtOfXML("Lable-meeting"),
//							CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//							notecode,
//							CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//							CommonUtil.OPERTYPE_YK,
//							userInfo.getSys_user_code(),
//							userInfo.getSys_user_name(),
//							CommonUtil.findNoteTxtOfXML("CODE-YK-DELENOTE"),
//							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELENOTE"), 
//							CommonUtil.findNoteTxtOfXML("meetingNoteAdd"),
//							"",
//							"",
//							logintype,
//							userInfo.getSys_user_code(),
//							"",
//							null);
					message="success";
				}else {
					message="failure";
				}
				logger.info("MeetingAction.meetingnote_delet()方法end");
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.meetingnote_delet() 发生异常", e);
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
				logger.error("MeetingAction.meetingnote_delet() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.meetingnote_delet() 发生异常", e);
				e.printStackTrace();
			}
		}
		@RequestMapping(value = "meetingnote_add")
		public void meetingnote_add(HttpServletRequest request,
				HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam("meetingcode") String meetingcode,@RequestParam("text") String text) throws Exception {
			logger.info("MeetingAction.meetingnote_add()方法Start");
			String message="error";
			BaseMeetingNoteInfo baseMeetingNoteInfo=new BaseMeetingNoteInfo();
			String code=CommonUtil.PREFIX_MEETINGNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANYNOTE_TYPE);//生成一个投资机构code
			SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			baseMeetingNoteInfo.setBase_meetingnote_code(code);//note code
			baseMeetingNoteInfo.setCreateid(userInfo.getSys_user_code());// creatid
			baseMeetingNoteInfo.setUpdid(userInfo.getSys_user_code());//更新人code
			baseMeetingNoteInfo.setBase_meeting_code(meetingcode);//会议code
			baseMeetingNoteInfo.setBase_invesnote_content(text);//会议内容
			baseMeetingNoteInfo.setCreatetime(new Timestamp(new Date().getTime()));//创建时间
			baseMeetingNoteInfo.setUpdtime(new Timestamp(new Date().getTime()));//更新时间
			baseMeetingNoteInfo.setSys_user_name(userInfo.getSys_user_name());//创建人姓名
			int i=0;
			try {
				 i=meetingService.meetingnote_add(baseMeetingNoteInfo);
				 if (i==1) {
					 /* 添加系统更新记录 */
//						baseUpdlogInfoService.insertUpdlogInfo(
//								CommonUtil.findNoteTxtOfXML("Lable-meeting"),
//								CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//								baseMeetingNoteInfo.getBase_meetingnote_code(),
//								CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//								CommonUtil.OPERTYPE_YK,
//								userInfo.getSys_user_code(),
//								userInfo.getSys_user_name(),
//								CommonUtil.findNoteTxtOfXML("CODE-YK-ADDNOTE"),
//								CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADDNOTE"), 
//								CommonUtil.findNoteTxtOfXML("meetingNoteAdd"),
//								"",
//								baseMeetingNoteInfo.getBase_invesnote_content(),
//								logintype,
//								userInfo.getSys_user_code(),
//								"",
//								null);
						message="success";
				} 
					logger.info("MeetingAction.meetingnote_add()方法end");
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.meetingnote_add() 发生异常", e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("MeetingNote", JSONObject.fromObject(baseMeetingNoteInfo));
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.meetingnote_delet() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.meetingnote_delet() 发生异常", e);
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 修改会议分享范围
		 * @param request
		 * @param response
		 * @param logintype 登录标识 
		 * @param version 数据库版本号
		 * @param sharetype 分享类型（1：筐，2：人）
		 * @param meetcode 会议code
		 * @param newbask 新分享数据
		 * @author duwenjie
		 * @date 2016-3-4
		 */
		@RequestMapping(value="updateMeetingInfo")
		public void updateMeetingInfo(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="version",required=true)long version,
				@RequestParam(value="sharetype",required=true)String sharetype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="newbask",required=true)String newbask){
			String message="success";
			List<Map<String, String>> userShareList=null;
			List<Map<String, String>> sysl=null;
			List<Map<String, String>> dellist=null;
			List<BaseMeetingShare> addlist=null;
//			List<BaseMeetingShare> maillist=null;
//			List<SysUserInfo> emaiList=null;
//			List<SysUserInfo> baskList=null;
			
//			SysUserInfo sysUserInfo=null;
			BaseMeetingShare  meetingShare=null;
			net.sf.json.JSONObject newShare=null;
			BaseMeetingInfo meetingInfo=null;
			JSONArray newArray = null;
			ViewMeetingRele viewMeetingRele=null;
			Map<String, String> edmapMap=null;
			boolean bool=false;
			String code="";
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				newArray = JSONArray.fromObject(newbask);
				
				if(userInfo!=null){
					
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						if(userInfo.getSys_user_code().equals(viewMeetingRele.getCreateid())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							dellist=new ArrayList<Map<String,String>>();
							addlist= new ArrayList<BaseMeetingShare>();
//							maillist= new ArrayList<BaseMeetingShare>();
							//会议分享权限
							userShareList=meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
							//系统全部超级权限
							sysl=meetingaddservice.querysupersyswadinfo();
							if(userShareList!=null){
								//筛选删除分享
								if(userShareList!=null && userShareList.size()>0){
									code="";
									for (Map<String, String> map : userShareList) {
										for (Object object : newArray) {
											JSONObject jsonObject=JSONObject.fromObject(object);
											code=String.valueOf(jsonObject.get("code"));
											if(code.equals(map.get("code"))){
												bool=true;
												break;
											}
										}
										if(bool==false){
											edmapMap= new HashMap<String, String>();
											edmapMap.put("meetcode", meetcode);
											edmapMap.put("baskcode", map.get("code"));
											dellist.add(edmapMap);
										}else{
											bool=false;
										}
									}
								}
							}
							
							//筛选添加分享
							if(newArray!=null && newArray.size()>0){
								code="";
								for (int i = 0; i < newArray.size(); i++) {
									newShare=newArray.getJSONObject(i);
									code=newShare.getString("code");
									if(userShareList!=null){
										for (Map<String, String> map : userShareList) {
											if(code.equals(map.get("code"))){
												bool=true;
												break;
											}
										}
									}
									
									if(bool==false){
										meetingShare = new BaseMeetingShare();
										meetingShare.setBase_meeting_code(meetcode);
										meetingShare.setBase_meeting_sharecode(code);
										meetingShare.setBase_meeting_sharetype(sharetype);
										meetingShare.setCreateid(userInfo.getSys_user_code());
										meetingShare.setCreatetime(CommonUtil.getNowTime_tamp());
										meetingShare.setDeleteflag("0");
										meetingShare.setUpdid(meetingShare.getCreateid());
										meetingShare.setUpdtime(meetingShare.getCreatetime());
										addlist.add(meetingShare);
//										maillist.add(meetingShare);
									}else {
										bool=false;
									}
									
								}
							}
							//修改分享类型时重新添加超级权限分享
							for (Map<String, String> smap : sysl) {
								if(smap!=null){
									edmapMap= new HashMap<String, String>();
									edmapMap.put("meetcode", meetcode);
									edmapMap.put("baskcode", smap.get("code"));
									dellist.add(edmapMap);
									
									meetingShare = new BaseMeetingShare();
									meetingShare.setBase_meeting_code(meetcode);
									meetingShare.setBase_meeting_sharecode(smap.get("code"));
									meetingShare.setBase_meeting_sharetype(sharetype);
									meetingShare.setCreateid(userInfo.getSys_user_code());
									meetingShare.setCreatetime(CommonUtil.getNowTime_tamp());
									meetingShare.setDeleteflag("0");
									meetingShare.setUpdid(meetingShare.getCreateid());
									meetingShare.setUpdtime(meetingShare.getCreatetime());
									addlist.add(meetingShare);
								}
							}
							
							meetingInfo=new BaseMeetingInfo();
							meetingInfo.setBase_meeting_code(meetcode);
							meetingInfo.setBase_datalock_viewtype(version+1);
							meetingInfo.setBase_datalock_pltype("0");
							meetingInfo.setUpdid(userInfo.getSys_user_code());
							meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
							//修改会议排他锁
							int data=meetingService.updateBaseMeetingInfo(meetingInfo);
							if(data>0){
								//修改会议分享范围
								meetingService.tranModifyShareInfo(userInfo.getSys_user_code(),meetcode,addlist, dellist);
								
/*-------------------------------------推送邮件提醒开始----------------------------------*/
//								if(maillist!=null && maillist.size()>0){
//									emaiList= new ArrayList<SysUserInfo>();
//									for (BaseMeetingShare object : maillist) {
//										if(sharetype.equals("1")){//分享筐
//											baskList=meetingService.findUserInfoByBaskCode(object.getBase_meeting_sharecode());
//											if(baskList!=null && baskList.size()>0){
//												for (SysUserInfo sysuser : baskList) {
//													//判断非登录人
//													if(sysuser!=null 
//															&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//														for (SysUserInfo emailuser : emaiList) {
//															if(emailuser.getSys_user_code().equals(sysuser.getSys_user_code())){
//																bool=true;
//																break;
//															}
//														}
//														if(bool==false){
//															emaiList.add(sysuser);
//														}else{
//															bool=false;
//														}
//													}
//												}
//											}
//										}else if(sharetype.equals("2")){//分享人
//											sysUserInfo=sysUserInfoService.sysuserbycode(object.getBase_meeting_sharecode());
//											if(sysUserInfo!=null
//													&& !sysUserInfo.getSys_user_code().equals(userInfo.getSys_user_code())){
//												emaiList.add(sysUserInfo);
//											}
//											
//										}
//										
//									}
//									
//									//CommonUtil.findNoteTxtOfXML("SHAREMEETING")
//									String address=request.getScheme()+"://"+request.getServerName()
//									+":"+request.getServerPort()+request.getContextPath()+"/"+
//									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
//									
//									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingMessage.ftl","",emaiList);
//
//								}
/*-------------------------------------送邮件提醒结束----------------------------------*/					
							}else{
								message="upfail";
							}
						}else {
							message="noroot";
						}
					}else {
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
				
				
//				if(addlist!=null){
//					for (BaseMeetingShare share : addlist) {
//						
//					}
//					 /* 添加系统更新记录 */
//					baseUpdlogInfoService.insertUpdlogInfo(
//							CommonUtil.findNoteTxtOfXML("Lable-meeting"),
//							CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//							meetcode,
//							CommonUtil.findNoteTxtOfXML("Lable-meeting-name"),
//							CommonUtil.OPERTYPE_YK,
//							userInfo.getSys_user_code(),
//							userInfo.getSys_user_name(),
//							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
//							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
//							CommonUtil.findNoteTxtOfXML("meetingShareUpd"),
//							"",
//							"",
//							logintype,
//							userInfo.getSys_user_code(),
//							"",
//							null);
//				}
				if(message.equals("success")){
					meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
					if(meetingInfo!=null){
						version=meetingInfo.getBase_datalock_viewtype();
					}
					//会议分享权限
					userShareList=meetingService.findMeetShareByMeetingCode(meetcode,sharetype);
					if(sysl!=null){//去除超级权限
						for (int i=0;i<userShareList.size();i++) {
							if(userShareList.get(i)!=null){
								code=userShareList.get(i).get("code");
								for (Map<String, String> smap : sysl) {
									if(smap!=null&& smap.get("code").equals(code)){
										userShareList.remove(i);
										i--;
									}
								}
							}
						}
					}
					
					
				}
				
			} catch (Exception e) {
				meetcode = "error";
				logger.error("MeetingAction.updateMeetingInfo 方法异常",e);
				e.printStackTrace();
			}
			
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				resultJSON.put("userShareList", JSONArray.fromObject(userShareList));
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingInfo() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.updateMeetingInfo() 发生异常", e);
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 删除会议信息
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode
		 * @author duwenjie
		 * 
		 */
		@RequestMapping(value="deleteMeetingInfo")
		public void deleteMeetingInfo(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode
				){
			String message="success";
			List<BaseMeetingFile> filelist=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					if(userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
						meetingService.tranDeleteMeetingInfoByCode(meetcode);
						
						filelist=meetingFileService.findMeetingFileByMeetingcode(meetcode);
						if(filelist!=null&&filelist.size()>0){
							for (BaseMeetingFile info : filelist) {
								if(info!=null){
									String format=info.getBase_meeting_filename().substring(
											info.getBase_meeting_filename().lastIndexOf("."),info.getBase_meeting_filename().length());
									String url=CommonUtil.findNoteTxtOfXML("MEETING_WORD_SRC")+"/"+info.getBase_file_code()+format;
									File file=new File(url);
									file.delete();
								}
							}
							meetingFileService.deleteMeetingfileByMeetingcode(meetcode);
						}
						
					}else {
						message="noroot";
					}
				}else {
					meetcode="nouser";
				}
				
				
			} catch (Exception e) {
				message = "error";
				logger.error("MeetingAction.deleteMeetingInfo 方法异常",e);
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
			} catch (Exception e) {
				logger.error("MeetingAction.deleteMeetingInfo() 发生异常", e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 修改会议参会人
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode
		 * @param newjoinpeople
		 */
		@RequestMapping(value = "updateMeetingJoinPeople")
		public void updateMeetingJoinPeople(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="version",required=true)long version,
				@RequestParam(value="newjoinpeople",required=true)String newjoinpeople){
			String message="success";
			JSONArray jsonArray=null;
			JSONObject jsonObject= null;
			List<BaseMeetingParp> addlist=null;
			List<Map<String, String>> delList=null;
			BaseMeetingParp meetingParp=null;
			Map<String, String> map = null;
			ViewMeetingRele viewMeetingRele=null;
			BaseMeetingInfo meetingInfo = null;
			boolean bool=false;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							addlist=new ArrayList<BaseMeetingParp>();
							delList=new ArrayList<Map<String,String>>();
							jsonArray=JSONArray.fromObject(newjoinpeople);
							String[] strcode=viewMeetingRele.getView_meeting_usercode().split(",");
							for (int i = 0; i < strcode.length; i++) {
								if(strcode[i]!=null && !strcode[i].trim().equals("")){
									for (int j = 0; j < jsonArray.size(); j++) {
										jsonObject=jsonArray.getJSONObject(j);
										if(jsonObject!=null && strcode[i].equals(jsonObject.get("code"))){
											bool=true;
											break;
										}
									}
									
									if (bool==false) {
										map= new HashMap<String, String>();
										map.put("meetingcode", meetcode);
										map.put("usercode", strcode[i]);
										delList.add(map);
									}else {
										bool=false;
									}
								}
								
								
								
							}
							
							
							for (int j = 0; j < jsonArray.size(); j++) {
								jsonObject=jsonArray.getJSONObject(j);
								if(jsonObject!=null){
									for (int i = 0; i < strcode.length; i++) {
										if(strcode[i]!=null && jsonObject.getString("code").equals(strcode[i])){
											bool=true;
											break;
										}
									}
									
									if(bool==false){
										meetingParp=new BaseMeetingParp();
										meetingParp.setBase_meeting_code(meetcode);
										meetingParp.setSys_user_code(jsonObject.getString("code"));
										meetingParp.setSys_user_name(jsonObject.getString("name"));
										meetingParp.setDeleteflag("0");
										meetingParp.setCreateid(userInfo.getSys_user_code());
										meetingParp.setCreatetime(CommonUtil.getNowTime_tamp());
										meetingParp.setUpdid(userInfo.getSys_user_code());
										meetingParp.setUpdtime(meetingParp.getCreatetime());
										addlist.add(meetingParp);
									}else {
										bool=false;
									}
								}
							}
							meetingInfo=new BaseMeetingInfo();
							meetingInfo.setBase_meeting_code(meetcode);
							meetingInfo.setBase_datalock_viewtype(version+1);
							meetingInfo.setBase_datalock_pltype("0");
							meetingInfo.setUpdid(userInfo.getSys_user_code());
							meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
							//修改会议排他锁
							int data=meetingService.updateBaseMeetingInfo(meetingInfo);
							if(data>0){
								meetingService.tranModifyMeetingParp(userInfo.getSys_user_code(),meetcode,delList, addlist);
								
								meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
								if(meetingInfo!=null){
									version=meetingInfo.getBase_datalock_viewtype();
								}
								
/*-----------------推送会议通知开始 -----------------------*/
								//推送会议通知
								if(addlist!=null && addlist.size()>0){
									SysUserInfo sysUserInfo=null;
									List<SysUserInfo> emaiList=new ArrayList<SysUserInfo>();
									
									for (BaseMeetingParp meetparp : addlist) {
										//判断是否为登录人
										if(!meetparp.getSys_user_code().equals(userInfo.getSys_user_code())){
											sysUserInfo=sysUserInfoService.sysuserbycode(meetparp.getSys_user_code());
											if(sysUserInfo!=null){
												emaiList.add(sysUserInfo);
											}
										}
									}
									
									String address=request.getScheme()+"://"+request.getServerName()
									+":"+request.getServerPort()+request.getContextPath()+"/"+
									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
									
									StringBuffer sBuffer=new StringBuffer();
									if(viewMeetingRele.getBase_meeting_invicont()!=null
											||viewMeetingRele.getBase_meeting_compcont()!=null){
										JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
										JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
										
										sBuffer.append("关于");
										if(viewMeetingRele.getBase_meeting_invicont()!=null){
											sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("，");
											}
										}
										if(viewMeetingRele.getBase_meeting_compcont()!=null){
											sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
										}
										sBuffer.append("的");
									}
									
									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingMessage.ftl",sBuffer.toString(),emaiList);
									
								}
/*-----------------推送会议通知结束 -----------------------*/
								
								
								
							}else {
								message="change";
							}
						}else{
							message="noroot";
						}
					}else{
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.updateMeetingJoinPeople()  方法异常",e);
				e.printStackTrace();
			}
			
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingJoinPeople() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.updateMeetingJoinPeople() 发生异常", e);
				e.printStackTrace();
			}
			
			
		}
		
		/**
		 * 修改会议相关机构
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode
		 * @param version
		 * @param neworg
		 * @author duwenjie
		 * @date 2016-3-11
		 */
		@RequestMapping(value="updateMeetingOrg")
		public void updateMeetingOrg(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="neworg",required=true)String neworg){
			String message="success";
			JSONObject jsonObject=null;
			//机构
		    baseInvestmentInfo baseinves = null;
		    //投资人
		    BaseInvestorInfo baseinvestor = new BaseInvestorInfo();
		    //投资人机构关系
		    BaseInvestmentInvestor investorinfo = null;
		    //会议相关
		    BaseMeetingRele basemeetingrele = null;
		    BaseMeetingInfo meetingInfo = null;
		    ViewMeetingRele viewMeetingRele=null;
		    // 生成投资机构id
		    String invementcode ="";
		    // 投资人id
		    String investorcode ="";
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							jsonObject=JSONObject.fromObject(neworg);
							if(jsonObject!=null){
								//验证机构信息
							    //投资机构
							    if(jsonObject.get("investmenttype")!=null 
							    		&& "add".equals(jsonObject.get("investmenttype").toString().trim())
							    		&& jsonObject.get("name")!=null
							    		&& !"".equals(jsonObject.get("name").toString().trim())){
							    		int total=tradeaddservice.queryInvestmentOnlyNamefottrade(jsonObject.get("name").toString());
				        				if (total>0) {
				        					message="orgexsit";//机构简称已存在
				        				}else{
				        					baseinves = new baseInvestmentInfo();
				        				    // 机构简称
				        				    baseinves.setBase_investment_name(jsonObject.get("name").toString().trim());
				        				    // 生成投资机构id
				        				    invementcode = CommonUtil.PREFIX_INVESTMENT
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
				        				   
				        				}
							    	}else if(jsonObject.get("investmenttype")!=null 
							    			&& "select".equals(jsonObject.get("investmenttype").toString().trim())
							    			&&  jsonObject.get("code")!=null 
							    			&& !"".equals(jsonObject.get("code").toString().trim())
							    			&&  jsonObject.get("name")!=null 
							    			&& !"".equals(jsonObject.get("name").toString().trim())){
							    		baseinves = new baseInvestmentInfo();
							    		//机构code
							    		baseinves.setBase_investment_code(jsonObject.get("code").toString().trim());
							    		//机构简称
							    		baseinves.setBase_investment_name(jsonObject.get("name").toString().trim());
							    	}else {
										message="nullorg";//机构信息不能为空
									}
							    	
							    	//投资人
							    	if("success".equals(message) 
							    		&& jsonObject.get("investortype")!=null 
							    		&& "add".equals(jsonObject.get("investortype").toString().trim())
							    		&&  jsonObject.get("personname")!=null 
						    			&& !"".equals(jsonObject.get("personname").toString().trim())){
							    		//投资人名称
							    		baseinvestor.setBase_investor_name(jsonObject.get("personname").toString().trim());
					                    //生成投资人id
							            investorcode=CommonUtil.PREFIX_INVESTOR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTOR_TYPE);
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

							    	}else if("success".equals(message) 
							    			&&jsonObject.get("investortype")!=null 
								    		&& "select".equals(jsonObject.get("investortype").toString().trim())
								    		&&  jsonObject.get("personcode")!=null 
							    			&& !"".equals(jsonObject.get("personcode").toString().trim())
							    			&&  jsonObject.get("personname")!=null 
							    			&& !"".equals(jsonObject.get("personname").toString().trim())){
							    		//投资人code
							    		baseinvestor.setBase_investor_code(jsonObject.get("personcode").toString());
							    		//投资人姓名
							    		baseinvestor.setBase_investor_name(jsonObject.get("personname").toString());
							    	}
							    	
							    	//投资人机构关系
							    	if("success".equals(message) &&((jsonObject.get("investmenttype")!=null 
							    		&&"add".equals(jsonObject.get("investmenttype").toString().trim()))
							    		|| (jsonObject.get("investortype")!=null 
								    	&&"add".equals(jsonObject.get("investortype").toString().trim()))
								    	&& baseinves.getBase_investment_code()!=null
								    	&& !"".equals(baseinves.getBase_investment_code())
								    	&& baseinvestor.getBase_investor_code()!=null
								    	&& !"".equals(baseinvestor.getBase_investor_code()))){
							    		
							    		investorinfo=new BaseInvestmentInvestor();
										//投资人id
				                    	investorinfo.setBase_investor_code(baseinvestor.getBase_investor_code());
				                    	//投资机构id
				                    	investorinfo.setBase_investment_code(baseinves.getBase_investment_code());
				                    	//在职状态
				                    	investorinfo.setBase_investor_state("0");
				                    	//职位名称
				                    	if(jsonObject.get("personpo")!=null
				                    			&& !"".equals(jsonObject.get("personpo").toString().trim())){
				                    		investorinfo.setBase_investor_posiname(jsonObject.get("personpo").toString().trim());
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
								    }
							    	
							    	if("success".equals(message)){
							    		basemeetingrele = new BaseMeetingRele();

							    		//会议id
									    basemeetingrele.setBase_meeting_code(meetcode);
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
								    	basemeetingrele.setBase_meeting_relepposi(jsonObject.get("personpo").toString());
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
								    	
								    	meetingInfo=new BaseMeetingInfo();
										meetingInfo.setBase_meeting_code(meetcode);
										meetingInfo.setBase_datalock_viewtype(version+1);
										meetingInfo.setBase_datalock_pltype("0");
										meetingInfo.setUpdid(userInfo.getSys_user_code());
										meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
										//修改会议排他锁
										int data=meetingService.updateBaseMeetingInfo(meetingInfo);
										if(data>0){
											//修改会议相关机构
									    	meetingService.tranModifyMeetingOrg(baseinves,baseinvestor,investorinfo,basemeetingrele);
									    	
									    	if(jsonObject!=null 
													&&"add".equals(jsonObject.get("investmenttype").toString().trim())){
									    		/*修改会议机构时添加机构修改系统更新记录*/
												baseUpdlogInfoService.insertUpdlogInfo(
														CommonUtil.findNoteTxtOfXML("Lable-investment"),
														CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
														invementcode, 
														"",
														CommonUtil.OPERTYPE_YK,
														userInfo.getSys_user_code(), 
														userInfo.getSys_user_name(),
														CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
														CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
														CommonUtil.findNoteTxtOfXML("addtradeInvestment"),
														"",
														"创建会议时,添加机构["+jsonObject.get("name").toString().trim()+"]",
														logintype,
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp(),
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp());
									    	
									    	}
									    	
									    	if(jsonObject!=null &&"add".equals(jsonObject.get("investortype").toString().trim())){
									    		/*修改会议机构时添加投资人修改系统更新记录*/
												baseUpdlogInfoService.insertUpdlogInfo(
														CommonUtil.findNoteTxtOfXML("Lable-people"),
														CommonUtil.findNoteTxtOfXML("Lable-people-name"),
														investorcode, 
														"",
														CommonUtil.OPERTYPE_YK,
														userInfo.getSys_user_code(), 
														userInfo.getSys_user_name(),
														CommonUtil.findNoteTxtOfXML("CODE-YK-CRE"),
														CommonUtil.findNoteTxtOfXML("CONTENT-YK-CRE"), 
														CommonUtil.findNoteTxtOfXML("addtradeInvestor"),
														"",
														"创建会议时,添加投资人["+jsonObject.get("personname").toString().trim()+"]",
														logintype,
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp(),
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp());
													}
									    	if((jsonObject!=null &&"add".equals(jsonObject.get("investmenttype").toString().trim()))
									    			|| (jsonObject!=null &&"add".equals(jsonObject.get("investortype").toString().trim()))){
									    		/*修改会议机构时添加投资人机构关系修改系统更新记录*/
												baseUpdlogInfoService.insertUpdlogInfo(
														CommonUtil.findNoteTxtOfXML("Lable-people"),
														CommonUtil.findNoteTxtOfXML("Lable-people-name"),
														baseinvestor.getBase_investor_code(), 
														"",
														CommonUtil.OPERTYPE_YK,
														userInfo.getSys_user_code(), 
														userInfo.getSys_user_name(),
														CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
														CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
														CommonUtil.findNoteTxtOfXML("investorEdit"),
														"",
														"创建会议时,添加投资人["+jsonObject.get("personname").toString().trim()+"]的所属机构"
														+"["+jsonObject.get("name").toString().trim()+"]",
														logintype,
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp(),
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp());
													
									    	}
									    	
									    	
									    	
											meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
											if(meetingInfo!=null){
												version=meetingInfo.getBase_datalock_viewtype();
											}
											
/*-----------------推送会议通知开始 -----------------------*/
											//参会人
											List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
											//分享范围
//											List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
											//所有超级权限
//											List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
											//移除超级权限
//											for (int i = 0; i < shareList.size(); i++) {
//												for (int j = 0; j < SysWadList.size(); j++) {
//													if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//														shareList.remove(i);
//														i--;
//														break;
//													}
//												}
//											}
											
											List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//											List<SysUserInfo> baskList = null;
											SysUserInfo sysUserInfo = null;
											boolean bool=true;
											//匹配分享范围人员数据
											if(list!=null && list.size()>0){
												
//												for (Map<String, String> map : shareList) {
//													if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//														
//														baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//														if(baskList!=null && baskList.size()>0){
//															for (SysUserInfo sysuser : baskList) {
//																if(sysuser!=null
//																		&&  !userInfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																	for (SysUserInfo emailinfo : emaiList) {
//																		if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																			bool=false;
//																			break;
//																		}
//																	}
//																	if(bool==true){
//																		emaiList.add(sysuser);
//																	}else {
//																		bool=true;
//																	}
//																	
//																}
//															}
//														}
//													}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//															&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//														sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//														if(sysUserInfo!=null){
//															emaiList.add(sysUserInfo);
//														}
//														
//													}
//												
//													
//												}
												
												//匹配参会人数据
												for (BaseMeetingParp meetingParp : list) {
													if(meetingParp!=null 
															&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code())){
														for (SysUserInfo emailinfo : emaiList) {
															if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
																bool=false;
																break;
															}
														}
														if(bool==true){
															sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
															if(sysUserInfo!=null){
																emaiList.add(sysUserInfo);
															}
														}else {
															bool=true;
														}
													}
												}
												
											}
											
											String address=request.getScheme()+"://"+request.getServerName()
											+":"+request.getServerPort()+request.getContextPath()+"/"+
											"meeting_info.html?meetingcode="+meetcode+"&logintype=";
											
											StringBuffer sBuffer=new StringBuffer();
											if(viewMeetingRele.getBase_meeting_invicont()!=null
													||viewMeetingRele.getBase_meeting_compcont()!=null){
												JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
												JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
												
												sBuffer.append("关于");
												if(viewMeetingRele.getBase_meeting_invicont()!=null){
													sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
													if(viewMeetingRele.getBase_meeting_compcont()!=null){
														sBuffer.append("，");
													}
												}
												if(viewMeetingRele.getBase_meeting_compcont()!=null){
													sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
												}
												sBuffer.append("的");
											}
											
											emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/
											
											
										}else {
											message="change";
										}
								    	
							    	}
							}
							
							
						}else{
							message="noroot";
						}
					}else{
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
				
				
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.updateMeetingOrg() 方法异常",e);
				e.printStackTrace();
			}
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				if(jsonObject!=null 
					&&"add".equals(jsonObject.get("investmenttype").toString().trim())){
					resultJSON.put("orgcode", invementcode);
				}
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingJoinPeople() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.updateMeetingJoinPeople() 发生异常", e);
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 删除会议相关机构
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode 会议code
		 * @param version 版本号
		 * @param orgcode 机构code
		 * @author duwenjie
		 * @date 2016-3-14
		 */
		@RequestMapping(value="delMeetingOrg")
		public void delMeetingOrg(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="investorcode",required=false)String investorcode,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="orgcode",required=true)String orgcode){
			String message="success";
			Map<String, String> mapdata=null;
			BaseMeetingInfo meetingInfo=null;
			ViewMeetingRele viewMeetingRele=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							mapdata=new HashMap<String, String>();
							mapdata.put("meetingcode", meetcode);
							mapdata.put("reletype", "1");
							mapdata.put("relecode", orgcode);
							mapdata.put("relepcode", investorcode);
							
							
							meetingInfo=new BaseMeetingInfo();
							meetingInfo.setBase_meeting_code(meetcode);
							meetingInfo.setBase_datalock_viewtype(version+1);
							meetingInfo.setBase_datalock_pltype("0");
							meetingInfo.setUpdid(userInfo.getSys_user_code());
							meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
							//修改会议排他锁
							int data=meetingService.updateBaseMeetingInfo(meetingInfo);
							
							if(data>0){
								data=meetingService.tranDeleteMeetingReleOrg(userInfo.getSys_user_code(), mapdata);
								if(data!=0){
									meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
									if(meetingInfo!=null){
										version=meetingInfo.getBase_datalock_viewtype();
									}
									
/*-----------------推送会议通知开始 -----------------------*/
									//参会人
									List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
									//分享范围
//									List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
									
//									if(shareList!=null && shareList.size()>0){
//										//所有超级权限
//										List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
//										//移除超级权限
//										for (int i = 0; i < shareList.size(); i++) {
//											for (int j = 0; j < SysWadList.size(); j++) {
//												if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//													shareList.remove(i);
//													break;
//												}
//											}
//										}
//									}
									
									List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//									List<SysUserInfo> baskList = null;
									SysUserInfo sysUserInfo = null;
									boolean bool=true;
									//匹配分享范围人员数据
									if(list!=null && list.size()>0){
										
//										for (Map<String, String> map : shareList) {
//											if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//												baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//												if(baskList!=null && baskList.size()>0){
//													for (SysUserInfo sysuser : baskList) {
//														if(sysuser!=null 
//																&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//															for (SysUserInfo emailinfo : emaiList) {
//																if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																	bool=false;
//																	break;
//																}
//															}
//															if(bool==true){
//																emaiList.add(sysuser);
//															}else {
//																bool=true;
//															}
//															
//														}
//													}
//												}
//											}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//													&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//												sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//												if(sysUserInfo!=null){
//													emaiList.add(sysUserInfo);
//												}
//												
//											}
//										
//											
//										}
										
										//匹配参会人数据
										for (BaseMeetingParp meetingParp : list) {
											if(meetingParp!=null 
													&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code())){
												for (SysUserInfo emailinfo : emaiList) {
													if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
														bool=false;
														break;
													}
												}
												if(bool==true){
													sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
													if(sysUserInfo!=null){
														emaiList.add(sysUserInfo);
													}
												}else {
													bool=true;
												}
											}
										}
										
									}
									
									String address=request.getScheme()+"://"+request.getServerName()
									+":"+request.getServerPort()+request.getContextPath()+"/"+
									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
									
									StringBuffer sBuffer=new StringBuffer();
									if(viewMeetingRele.getBase_meeting_invicont()!=null
											||viewMeetingRele.getBase_meeting_compcont()!=null){
										JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
										JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
										
										sBuffer.append("关于");
										if(viewMeetingRele.getBase_meeting_invicont()!=null){
											sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("，");
											}
										}
										if(viewMeetingRele.getBase_meeting_compcont()!=null){
											sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
										}
										sBuffer.append("的");
									}
									
									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/
									
									
								}else {
									message="fail";
								}
							}else {
								message="change";
							}
							
						}else{
							message="noroot";
						}
					}else{
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
				
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.delMeetingOrg() 发生异常",e);
				e.printStackTrace();
			}
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.delMeetingOrg() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.delMeetingOrg() 发生异常", e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 修改会议相关公司信息
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode
		 * @param version
		 * @param newcompany
		 * @author duwenjie
		 * @date 2016-3-14
		 */
		@RequestMapping(value="updateMeetingCompany")
		public void updateMeetingCompany(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="newcompany",required=true)String newcompany){
			String message="success";
			ViewMeetingRele viewMeetingRele=null;
			JSONObject jsonObject=null;
			List<BaseFinancplanEmail> emailList=null;
			BaseMeetingInfo meetingInfo=null;
			BaseFinancplanInfo basefinancplaninfo=null;
			BaseFinancplanEmail basefinancplanemail=null;
			String compcode="";
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){

						    //公司
							BaseCompInfo basecompinfo = new BaseCompInfo();
						    //公司联系人
							BaseEntrepreneurInfo baseentrepreneurinfo=new BaseEntrepreneurInfo();
						    //公司联系人关系
							BaseCompEntrepreneur basecompentrepreneur=new BaseCompEntrepreneur();
						    //会议相关
						    BaseMeetingRele basemeetingrele=new BaseMeetingRele();
						    
						    jsonObject=JSONObject.fromObject(newcompany);
						    
						    if(jsonObject!=null){
						    	if(jsonObject.get("comptype")!=null
							    	&&"add".equals(jsonObject.get("comptype").toString().trim())
							    	&& jsonObject.get("name")!=null
							    	&& !"".equals(jsonObject.get("name").toString().trim())){
								    int total=tradeaddservice.queryCompOnlyNamefortrade(jsonObject.get("name").toString());
					    			if (total>0) {
					    				message="compnameexsit";
					    			}else{
					    				//公司name
					    			    basecompinfo.setBase_comp_name(jsonObject.get("name").toString());
										//公司简称拼音全拼,公司简称拼音首字母
					    			    String[] pinYin=CommonUtil.getPinYin(basecompinfo.getBase_comp_name());
					            		//公司简称拼音全拼
					    				basecompinfo.setBase_comp_namep(pinYin[0]);
					            		//公司简称拼音首字母
					    				basecompinfo.setBase_comp_namef(pinYin[1]);
					    				//生成公司id
					    				compcode=CommonUtil.PREFIX_COMP+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANY_TYPE);
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
							    }else if(jsonObject.get("comptype")!=null 
							    	&& "select".equals(jsonObject.get("comptype").toString().trim())
							    	&& jsonObject.get("code")!=null
							    	&& !"".equals(jsonObject.get("code").toString().trim())
							    	&& jsonObject.get("name")!=null
							    	&& !"".equals(jsonObject.get("name").toString().trim())){
							        //公司code
								   	basecompinfo.setBase_comp_code(jsonObject.get("code").toString().trim());
								   	//公司简称
								   	basecompinfo.setBase_comp_name(jsonObject.get("name").toString().trim());
							    }else {
									message="nullcompany";
								}
							    	
							    //公司联系人
							    if(message.equals("success")
							    	&&jsonObject.get("entrepreneurtype")!=null 
							    	&& "add".equals(jsonObject.get("entrepreneurtype").toString().trim())
							    	&& jsonObject.get("personname")!=null
							    	&& !"".equals(jsonObject.get("personname").toString().trim())){
								   	//公司联系人名称
								   	baseentrepreneurinfo.setBase_entrepreneur_name(jsonObject.get("personname").toString().trim());
								   	//生成公司联系人id
								    String investorcode=CommonUtil.PREFIX_ENTREPRENEUR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.ENTREPRENEUR_TYPE);
								    baseentrepreneurinfo.setBase_entrepreneur_code(investorcode);
					                //公司联系人姓名拼音全拼,公司联系人姓名拼音首字母
								    String[] pinYin=CommonUtil.getPinYin(jsonObject.get("personname").toString().trim());
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

							    }else if(message.equals("success")
							    	&&jsonObject.get("entrepreneurtype")!=null 
							    	&& "select".equals(jsonObject.get("entrepreneurtype").toString().trim())
							    	&& jsonObject.get("personcode")!=null
							    	&& !"".equals(jsonObject.get("personcode").toString().trim())
							    	&& jsonObject.get("personname")!=null
							    	&& !"".equals(jsonObject.get("personname").toString().trim())
							    ){
							    	//公司联系人code
							    	baseentrepreneurinfo.setBase_entrepreneur_code(jsonObject.get("personcode").toString());
							    	//公司联系人姓名
							    	baseentrepreneurinfo.setBase_entrepreneur_name(jsonObject.get("personname").toString());
							    }else{
							    	message="nulllinkuser";
							    }
							    	
							    //公司联系人关系
							    if(message.equals("success")
							    	&&(message.equals("success") 
							    	&& ((jsonObject.get("comptype")!=null 
							    	&&"add".equals(jsonObject.get("comptype").toString().trim()))
							    	|| (jsonObject.get("entrepreneurtype")!=null 
								    &&"add".equals(jsonObject.get("entrepreneurtype").toString().trim()))
								    && basecompinfo.getBase_comp_code()!=null
								    && !"".equals(basecompinfo.getBase_comp_code())
								    && baseentrepreneurinfo.getBase_entrepreneur_code()!=null
								    && !"".equals(baseentrepreneurinfo.getBase_entrepreneur_code())))
							    ){
									//公司id
									basecompentrepreneur.setBase_comp_code(basecompinfo.getBase_comp_code());
				                    //公司联系人id
									basecompentrepreneur.setBase_entrepreneur_code(baseentrepreneurinfo.getBase_entrepreneur_code());
				                    //在职状态
									basecompentrepreneur.setBase_entrepreneur_state("0");
				                    //职位名称
				                    if(jsonObject.get("personpo")==null 
				                    	|| "".equals(jsonObject.get("personpo").toString().trim())){
				                    	basecompentrepreneur.setBase_entrepreneur_posiname(null);
				                    }else{
				                    	basecompentrepreneur.setBase_entrepreneur_posiname(jsonObject.get("personpo").toString().trim());
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
							    }
							    //公司融资计划
							    if(jsonObject.get("financplanflag")!=null 
							    		&& (Boolean)jsonObject.get("financplanflag")){
							    	basefinancplaninfo=new BaseFinancplanInfo();
								
									String plancode=CommonUtil.PREFIX_FINANCPLAN+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.FINANCPLAN_TYPE);//生成一个公司融资计划code
									//融资计划code
									basefinancplaninfo.setBase_financplan_code(plancode);
									//公司code
									basefinancplaninfo.setBase_comp_code(basecompinfo.getBase_comp_code());
									//公司简称
									basefinancplaninfo.setBase_comp_name(basecompinfo.getBase_comp_name());
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									//融资计划计划日期
									 Date financplantime=new Date(jsonObject.get("plandate").toString().trim().replace("-","/").replace("年","/").replace("月","/").replace("日","/"));
									basefinancplaninfo.setBase_financplan_date(Long.toString(financplantime.getTime()));
								    //融资计划提醒日期
									Date emailtime=new Date(jsonObject.get("remindate").toString().trim().replace("-","/").replace("年","/").replace("月","/").replace("日","/"));
									basefinancplaninfo.setBase_financplan_remindate(format.format(emailtime));
									//融资计划内容
									basefinancplaninfo.setBase_financplan_cont(jsonObject.get("plancont").toString().trim());
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

									/**
									 * 添加融资计划发送邮件信息
									 */
									
									List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
									if(list!=null){
										emailList = new ArrayList<BaseFinancplanEmail>();
										for (BaseMeetingParp baseMeetingParp : list) {
											if(baseMeetingParp!=null){
												basefinancplanemail=new BaseFinancplanEmail();
												
												basefinancplanemail.setBase_financplan_code(plancode);
												//用户id
												basefinancplanemail.setBase_financplan_email(baseMeetingParp.getSys_user_code());
												
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
												
												emailList.add(basefinancplanemail);
												
											}
										}
									}
									
									
									
								}
							    /**会议相关公司**/
							    if( message.equals("success")){
								
								    //会议id
								    basemeetingrele.setBase_meeting_code(meetcode);
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
							    	basemeetingrele.setBase_meeting_relepposi(jsonObject.get("personpo").toString());
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
							    	
							    	
							    	meetingInfo=new BaseMeetingInfo();
									meetingInfo.setBase_meeting_code(meetcode);
									meetingInfo.setBase_datalock_viewtype(version+1);
									meetingInfo.setBase_datalock_pltype("0");
									meetingInfo.setUpdid(userInfo.getSys_user_code());
									meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
									//修改会议排他锁
									int data=meetingService.updateBaseMeetingInfo(meetingInfo);
									
									if(data>0){
										meetingService.tranModifyMeetingCompany(basecompinfo, baseentrepreneurinfo,basecompentrepreneur, basefinancplaninfo, emailList, basemeetingrele);
										
										if("add".equals(jsonObject.get("comptype").toString().trim())){
											/*添加公司 系统更新记录*/
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
													"修改会议时,添加公司["+basecompinfo.getBase_comp_name()+"]",
													logintype,
													userInfo.getSys_user_code(),
													CommonUtil.getNowTime_tamp(),
													userInfo.getSys_user_code(),
													CommonUtil.getNowTime_tamp());
										}
										
										if("add".equals(jsonObject.get("entrepreneurtype").toString().trim())){
											/*添加 联系人 系统更新记录*/
											baseUpdlogInfoService.insertUpdlogInfo(
													CommonUtil.findNoteTxtOfXML("Lable-company"),
													CommonUtil.findNoteTxtOfXML("Lable-company-name"),
													basecompinfo.getBase_comp_code(), 
													"",
													CommonUtil.OPERTYPE_YK,
													userInfo.getSys_user_code(), 
													userInfo.getSys_user_name(),
													CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
													CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
													CommonUtil.findNoteTxtOfXML("compPeopleUpdate"),
													"",
													"修改会议时,添加公司联系人["+baseentrepreneurinfo.getBase_entrepreneur_name()+"]",
													logintype,
													userInfo.getSys_user_code(),
													CommonUtil.getNowTime_tamp(),
													userInfo.getSys_user_code(),
													CommonUtil.getNowTime_tamp());
										}
										
										if(("add".equals(jsonObject.get("comptype").toString().trim()))
												||("add".equals(jsonObject.get("entrepreneurtype").toString().trim()))){
											//添加公司 联系人关系
											baseUpdlogInfoService.insertUpdlogInfo(
														CommonUtil.findNoteTxtOfXML("Lable-company"),
														CommonUtil.findNoteTxtOfXML("Lable-company-name"),
														basecompinfo.getBase_comp_code(), 
														"",
														CommonUtil.OPERTYPE_YK,
														userInfo.getSys_user_code(), 
														userInfo.getSys_user_name(),
														CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
														CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
														CommonUtil.findNoteTxtOfXML("compPeopleUpdate"),
														"",
														"修改会议时,添加公司["+jsonObject.get("name").toString()+"]联系人["+jsonObject.get("personname").toString().trim()+"]",
														logintype,
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp(),
														userInfo.getSys_user_code(),
														CommonUtil.getNowTime_tamp());
										}
										
										if(jsonObject.get("financplanflag")!=null 
									    		&& (Boolean)jsonObject.get("financplanflag")){
											/*添加系统 融资计划更新记录*/
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
													"添加融资计划["+basefinancplaninfo.getBase_financplan_cont()+"]",
													logintype,
													userInfo.getSys_user_code(), 
													CommonUtil.getNowTime_tamp(),
													"",
													CommonUtil.getNowTime_tamp());
										}
										
										
										
										meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
										if(meetingInfo!=null){
											version=meetingInfo.getBase_datalock_viewtype();
										}	
										
/*-----------------推送会议通知开始 -----------------------*/
										//参会人
										List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
										//分享范围
//										List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
										
//										if(shareList!=null && shareList.size()>0){
//											//所有超级权限
//											List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
//											//移除超级权限
//											for (int i = 0; i < shareList.size(); i++) {
//												for (int j = 0; j < SysWadList.size(); j++) {
//													if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//														shareList.remove(i);
//														break;
//													}
//												}
//											}
//										}
										
										List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//										List<SysUserInfo> baskList = null;
										SysUserInfo sysUserInfo = null;
										boolean bool=true;
										//匹配分享范围人员数据
										if(list!=null && list.size()>0){
											
//											for (Map<String, String> map : shareList) {
//												if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//													baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//													if(baskList!=null && baskList.size()>0){
//														for (SysUserInfo sysuser : baskList) {
//															if(sysuser!=null 
//																	&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//																for (SysUserInfo emailinfo : emaiList) {
//																	if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																		bool=false;
//																		break;
//																	}
//																}
//																if(bool==true){
//																	emaiList.add(sysuser);
//																}else {
//																	bool=true;
//																}
//																
//															}
//														}
//													}
//												}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//														&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//													sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//													if(sysUserInfo!=null){
//														emaiList.add(sysUserInfo);
//													}
//													
//												}
//											
//												
//											}
											
											//匹配参会人数据
											for (BaseMeetingParp meetingParp : list) {
												if(meetingParp!=null
														&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code()) ){
													for (SysUserInfo emailinfo : emaiList) {
														if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
															bool=false;
															break;
														}
													}
													if(bool==true){
														sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
														if(sysUserInfo!=null){
															emaiList.add(sysUserInfo);
														}
													}else {
														bool=true;
													}
												}
											}
											
										}
										
										String address=request.getScheme()+"://"+request.getServerName()
										+":"+request.getServerPort()+request.getContextPath()+"/"+
										"meeting_info.html?meetingcode="+meetcode+"&logintype=";
										
										StringBuffer sBuffer=new StringBuffer();
										if(viewMeetingRele.getBase_meeting_invicont()!=null
												||viewMeetingRele.getBase_meeting_compcont()!=null){
											JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
											JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
											
											sBuffer.append("关于");
											if(viewMeetingRele.getBase_meeting_invicont()!=null){
												sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
												if(viewMeetingRele.getBase_meeting_compcont()!=null){
													sBuffer.append("，");
												}
											}
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
											}
											sBuffer.append("的");
										}
										emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/
										
									}else {
										message="change";
									}
							    }
						    }
						}else{
							message="noroot";
						}
					}else{
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
				
				
			} catch (Exception e) {
				message="error";
				e.printStackTrace();
			}
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				if(message.equals("success")
						&&"add".equals(jsonObject.get("comptype").toString().trim())){
					resultJSON.put("companycode", compcode);
				}
				
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingCompany() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.updateMeetingCompany() 发生异常", e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 删除会议相关公司
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode 会议code
		 * @param version 版本号
		 * @param companycode 公司code
		 * @author duwenjie
		 * @date 2016-3-14
		 */
		@RequestMapping(value="delMeetingCompany")
		public void delMeetingCompany(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="companycode",required=true)String companycode,
				@RequestParam(value="relepcode",required=false)String relepcode){
			String message="success";
			Map<String, String> mapdata=null;
			BaseMeetingInfo meetingInfo=null;
			ViewMeetingRele viewMeetingRele=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							mapdata=new HashMap<String, String>();
							mapdata.put("meetingcode", meetcode);
							mapdata.put("reletype", "2");
							mapdata.put("relecode", companycode);
							mapdata.put("relepcode", relepcode);
							
							
							meetingInfo=new BaseMeetingInfo();
							meetingInfo.setBase_meeting_code(meetcode);
							meetingInfo.setBase_datalock_viewtype(version+1);
							meetingInfo.setBase_datalock_pltype("0");
							meetingInfo.setUpdid(userInfo.getSys_user_code());
							meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
							//修改会议排他锁
							int data=meetingService.updateBaseMeetingInfo(meetingInfo);
							
							if(data>0){
								data=meetingService.tranDeleteMeetingReleCompany(userInfo.getSys_user_code(), mapdata);
								if(data!=0){
									meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
									if(meetingInfo!=null){
										version=meetingInfo.getBase_datalock_viewtype();
									}
									
/*-----------------推送会议通知开始 -----------------------*/
									//参会人
									List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
									//分享范围
//									List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
									
//									if(shareList!=null && shareList.size()>0){
//										//所有超级权限
//										List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
//										//移除超级权限
//										for (int i = 0; i < shareList.size(); i++) {
//											for (int j = 0; j < SysWadList.size(); j++) {
//												if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//													shareList.remove(i);
//													break;
//												}
//											}
//										}
//									}
									
									List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//									List<SysUserInfo> baskList = null;
									SysUserInfo sysUserInfo = null;
									boolean bool=true;
									//匹配分享范围人员数据
									if(list!=null && list.size()>0){
										
//										for (Map<String, String> map : shareList) {
//											if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//												baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//												if(baskList!=null && baskList.size()>0){
//													for (SysUserInfo sysuser : baskList) {
//														if(sysuser!=null
//																&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//															for (SysUserInfo emailinfo : emaiList) {
//																if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																	bool=false;
//																	break;
//																}
//															}
//															if(bool==true){
//																emaiList.add(sysuser);
//															}else {
//																bool=true;
//															}
//															
//														}
//													}
//												}
//											}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//													&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//												sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//												if(sysUserInfo!=null){
//													emaiList.add(sysUserInfo);
//												}
//												
//											}
//										
//											
//										}
										
										//匹配参会人数据
										for (BaseMeetingParp meetingParp : list) {
											if(meetingParp!=null 
													&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code())){
												for (SysUserInfo emailinfo : emaiList) {
													if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
														bool=false;
														break;
													}
												}
												if(bool==true){
													sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
													if(sysUserInfo!=null){
														emaiList.add(sysUserInfo);
													}
												}else {
													bool=true;
												}
											}
										}
										
									}
									
									String address=request.getScheme()+"://"+request.getServerName()
									+":"+request.getServerPort()+request.getContextPath()+"/"+
									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
									
									StringBuffer sBuffer=new StringBuffer();
									if(viewMeetingRele.getBase_meeting_invicont()!=null
											||viewMeetingRele.getBase_meeting_compcont()!=null){
										JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
										JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
										
										sBuffer.append("关于");
										if(viewMeetingRele.getBase_meeting_invicont()!=null){
											sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("，");
											}
										}
										if(viewMeetingRele.getBase_meeting_compcont()!=null){
											sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
										}
										sBuffer.append("的");
									}
									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/				
									
								}else {
									message="fail";
								}
							}else {
								message="change";
							}
							
						}else{
							message="noroot";
						}
					}else{
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
				
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.delMeetingOrg() 发生异常",e);
				e.printStackTrace();
			}
			
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.delMeetingOrg() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.delMeetingOrg() 发生异常", e);
				e.printStackTrace();
			}
		}

		/**
		 * 修改会议内容
		 * @param request
		 * @param response
		 * @param logintype
		 * @param meetcode
		 * @param version
		 * @param content
		 */
		@RequestMapping(value="updateMeetingContent")
		public void updateMeetingContent(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="logintype",required=true)String logintype,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="version",required=true)Long version,
				@RequestParam(value="content",required=true)String content){
			String message="success";
			ViewMeetingRele viewMeetingRele=null;
			BaseMeetingInfo meetingInfo=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						//判断登录人是否为会议记录人
						if(viewMeetingRele.getCreateid().equals(userInfo.getSys_user_code())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							if(content!=null && !content.trim().equals("")){
								meetingInfo=new BaseMeetingInfo();
								meetingInfo.setBase_meeting_code(meetcode);
								meetingInfo.setBase_datalock_viewtype(version+1);
								meetingInfo.setBase_datalock_pltype("0");
								meetingInfo.setUpdid(userInfo.getSys_user_code());
								meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
								meetingInfo.setBase_meeting_content(content);
								//修改会议信息
								int data=meetingService.updateMeetingContent(meetingInfo);
								if(data!=0){
									meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
									if(meetingInfo!=null){
										version=meetingInfo.getBase_datalock_viewtype();
									}
									
									
/*-----------------推送会议通知开始 -----------------------*/
									//参会人
									List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
									//分享范围
//									List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
									
//									if(shareList!=null && shareList.size()>0){
//										//所有超级权限
//										List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
//										//移除超级权限
//										for (int i = 0; i < shareList.size(); i++) {
//											for (int j = 0; j < SysWadList.size(); j++) {
//												if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//													shareList.remove(i);
//													break;
//												}
//											}
//										}
//									}
									
									List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//									List<SysUserInfo> baskList = null;
									SysUserInfo sysUserInfo = null;
									boolean bool=true;
									//匹配分享范围人员数据
									if(list!=null && list.size()>0){
										
//										for (Map<String, String> map : shareList) {
//											if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//												baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//												if(baskList!=null && baskList.size()>0){
//													for (SysUserInfo sysuser : baskList) {
//														if(sysuser!=null
//																&& !sysuser.getSys_user_code().equals(userInfo.getSys_user_code())){
//															for (SysUserInfo emailinfo : emaiList) {
//																if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																	bool=false;
//																	break;
//																}
//															}
//															if(bool==true){
//																emaiList.add(sysuser);
//															}else {
//																bool=true;
//															}
//															
//														}
//													}
//												}
//											}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//													&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//												sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//												if(sysUserInfo!=null){
//													emaiList.add(sysUserInfo);
//												}
//												
//											}
//										
//											
//										}
										
										//匹配参会人数据
										for (BaseMeetingParp meetingParp : list) {
											if(meetingParp!=null
													&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code()) ){
												for (SysUserInfo emailinfo : emaiList) {
													if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
														bool=false;
														break;
													}
												}
												if(bool==true){
													sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
													if(sysUserInfo!=null){
														emaiList.add(sysUserInfo);
													}
												}else {
													bool=true;
												}
											}
										}
										
									}
									
									String address=request.getScheme()+"://"+request.getServerName()
									+":"+request.getServerPort()+request.getContextPath()+"/"+
									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
									
									StringBuffer sBuffer=new StringBuffer();
									if(viewMeetingRele.getBase_meeting_invicont()!=null
											||viewMeetingRele.getBase_meeting_compcont()!=null){
										JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
										JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
										
										sBuffer.append("关于");
										if(viewMeetingRele.getBase_meeting_invicont()!=null){
											sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("，");
											}
										}
										if(viewMeetingRele.getBase_meeting_compcont()!=null){
											sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
										}
										sBuffer.append("的");
									}
									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/
									
								}else {
									message="change";
								}
							}else{
								message="nullpar";
							}
						}else{
								message="noroot";
							}
					}else{
						message="nomeeting";
					}
						
				}else {
					message="nouser";
				}
				
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.updateMeetingContent 方法异常",e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingContent() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.updateMeetingContent() 发生异常", e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 生成会议附件code
		 * @author dwj
		 * @date 2016-5-10
		 * @return
		 */
		public Map<String, Object> meetingFileCode() {
			String code="";
			String message="success";
			Map<String, Object> map=null;
			try {
				map=new HashMap<String, Object>();
				code=CommonUtil.PREFIX_MEETINGFILE
				+serialNumberGeneratorService.getCodeGeneratorstr(
						CommonUtil.STEPNUM, CommonUtil.MEETING_FILETYPE);
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.meetingFileCode() 发生异常", e);
				e.printStackTrace();
			}
			
			map.put("message", message);
			map.put("filecode", code);
			
			return map;
		}
		
		/**
		 * 添加会议附件信息
		 * @param request
		 * @param response
		 * @param src
		 * @param filecode
		 * @param meetingcode
		 * @param filename
		 * @return message
		 * @author dwj
		 * @date 2016-5-5
		 */
		public Map<String, Object> insertMeetingFile(HttpServletRequest request,HttpServletResponse response,
				String src,
				String filecode,
				String meetingcode,
				String filename) {
			String message = "success";
			BaseMeetingFile info=null;
			List<BaseMeetingFile> infolist=null;
			Map<String, Object> map=null;
			try {
				if (meetingcode!=null&&!"".equals(meetingcode)
						&&src!=null&&!"".equals(src)
						&&filename!=null && !"".equals(filename)) {
					SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
					info=new BaseMeetingFile();
					info.setBase_file_code(filecode);
					info.setBase_meeting_code(meetingcode);
					info.setBase_meeting_filename(filename);
					info.setBase_meeting_src(src);
					info.setCreateid(userInfo.getSys_user_code());
					info.setCreatetime(CommonUtil.getNowTime_tamp());
					info.setDeleteflag("0");
					info.setUpdid(info.getCreateid());
					info.setUpdtime(info.getCreatetime());
					
					meetingFileService.insertMeetingFileInfo(info);
					
					infolist= meetingFileService.findMeetingFileByMeetingcode(meetingcode);
				}else {
					message="nodata";
				}
			} catch (Exception e) {
				message="error";
				logger.error("MeetingAction.insertMeetingFile方法异常",e);
				e.printStackTrace();
			}
			
			map=new HashMap<String, Object>();
			map.put("message", message);
			map.put("info", infolist);
			return map;
		}
		
		/**
		 * 删除会议附件
		 * @param request
		 * @param response
		 * @param logintype
		 */
		@RequestMapping(value="delMeetingFile")
		public void loadMeetingFile(HttpServletRequest request,HttpServletResponse response,
				String filecode,String logintype){
			String message="success";
			BaseMeetingFile info=null;
			List<BaseMeetingFile> infolist=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				if(userInfo!=null){
					info=meetingFileService.findMeetingFileByMeetingFilecode(filecode);
					if(info!=null){
						int data=meetingFileService.deleteMeetingfileByfilecode(filecode);
						if (data>0) {
							
							infolist=meetingFileService.findMeetingFileByMeetingcode(info.getBase_meeting_code());
							String format=info.getBase_meeting_filename().substring(
									info.getBase_meeting_filename().lastIndexOf("."),info.getBase_meeting_filename().length());
							String url=CommonUtil.findNoteTxtOfXML("MEETING_WORD_SRC")+"/"+info.getBase_file_code()+format;
							File file=new File(url);
							file.delete();
						}else {
							message="unexist";
						}
					}else {
						message="unfile";
					}
				}else {
					message="nouser";
				}
			} catch (Exception e) {
				message="error";
				logger.error("delMeetingFile 方法异常",e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				if(message.equals("success")){
					resultJSON.put("fileinfo", JSONArray.fromObject(infolist));
				}
				
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("MeetingAction.delMeetingFile 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("MeetingAction.delMeetingFile 发生异常", e);
				e.printStackTrace();
			}
		}
		
		/**
		 * 修改会议类型
		 * @param request
		 * @param response
		 * @param version
		 * @param typelabel
		 * @param meetcode
		 * @param logintype
		 */
		@RequestMapping(value="updateMeetingType")
		public void updateMeetingType(HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value="version",required=true)long version,
				@RequestParam(value="typelabel",required=true)String typelabel,
				@RequestParam(value="meetcode",required=true)String meetcode,
				@RequestParam(value="logintype",required=true)String logintype) {
			String message="success";
			JSONArray newArray = null;
			ViewMeetingRele viewMeetingRele=null;
			BaseMeetingInfo meetingInfo=null;
			BaseMeetingLabelInfo labelInfo=null;
			List<Map<String, String>> meetingTypeList=null;
			try {
				SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				newArray = JSONArray.fromObject(typelabel);
				
				if(userInfo!=null){
					
					viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
					if(viewMeetingRele!=null){
						if(userInfo.getSys_user_code().equals(viewMeetingRele.getCreateid())
								||userInfo.getSys_user_code().equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))){
							labelInfo=new BaseMeetingLabelInfo();
							labelInfo.setBase_meeting_code(meetcode);
							labelInfo.setSys_label_code(CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
							for (Object object : newArray) {
								JSONObject obj=JSONObject.fromObject(object);
								
								labelInfo.setSys_labelelement_code(obj.get("code").toString());
								labelInfo.setCreateid(userInfo.getSys_user_code());
								labelInfo.setDeleteflag("0");
								labelInfo.setCreatetime(CommonUtil.getNowTime_tamp());
								
							}
							labelInfo.setUpdid(userInfo.getSys_user_code());
							labelInfo.setUpdtime(labelInfo.getCreatetime());
							meetingInfo=new BaseMeetingInfo();
							meetingInfo.setBase_meeting_code(meetcode);
							meetingInfo.setBase_datalock_viewtype(version+1);
							meetingInfo.setBase_datalock_pltype("0");
							meetingInfo.setUpdid(userInfo.getSys_user_code());
							meetingInfo.setUpdtime(CommonUtil.getNowTime_tamp());
							//修改会议排他锁
							int data=meetingService.updateBaseMeetingInfo(meetingInfo);
							if(data>0){
								//修改会议分享范围
								meetingService.tranModifyUpdateMeetingType(labelInfo);
								
								if(message.equals("success")){
									meetingInfo = meetingService.findBaseMeetingInfoByCode(meetcode);
									if(meetingInfo!=null){
										version=meetingInfo.getBase_datalock_viewtype();
									}
									
									viewMeetingRele=meetingService.viewmeetingreleBycode(meetcode);
									if(viewMeetingRele!=null){
										Map<String, String> map=new HashMap<String, String>();
										map.put("code", viewMeetingRele.getBase_meeting_typecode());
										map.put("name", viewMeetingRele.getBase_meeting_typename());
										meetingTypeList = new ArrayList<Map<String,String>>();
										meetingTypeList.add(map);
									}
									
									
/*-----------------推送会议通知开始 -----------------------*/
									//参会人
									List<BaseMeetingParp> list=meetingService.findMeetingUserInfo(meetcode);
									//分享范围
//									List<Map<String, String>> shareList= meetingService.findMeetShareByMeetingCode(meetcode,viewMeetingRele.getBase_meeting_sharetype());
									//所有超级权限
//									List<Map<String, String>> SysWadList=meetingaddservice.querysupersyswadinfo();
									//移除超级权限
//									for (int i = 0; i < shareList.size(); i++) {
//										for (int j = 0; j < SysWadList.size(); j++) {
//											if(shareList.get(i).get("code").equals(SysWadList.get(j).get("code"))){
//												shareList.remove(i);
//												i--;
//												break;
//											}
//										}
//									}
									
									List<SysUserInfo> emaiList = new ArrayList<SysUserInfo>();
//									List<SysUserInfo> baskList = null;
									SysUserInfo sysUserInfo = null;
									boolean bool=true;
									//匹配分享范围人员数据
									if(list!=null && list.size()>0){
										
//										for (Map<String, String> map : shareList) {
//											if(viewMeetingRele.getBase_meeting_sharetype().equals("1")){//分享筐
//												
//												baskList=meetingService.findUserInfoByBaskCode(map.get("code"));
//												if(baskList!=null && baskList.size()>0){
//													for (SysUserInfo sysuser : baskList) {
//														if(sysuser!=null
//																&&  !userInfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//															for (SysUserInfo emailinfo : emaiList) {
//																if(emailinfo!=null && emailinfo.getSys_user_code().equals(sysuser.getSys_user_code())){
//																	bool=false;
//																	break;
//																}
//															}
//															if(bool==true){
//																emaiList.add(sysuser);
//															}else {
//																bool=true;
//															}
//															
//														}
//													}
//												}
//											}else if(viewMeetingRele.getBase_meeting_sharetype().equals("2")
//													&& !map.get("code").equals(userInfo.getSys_user_code())){//分享人
//												sysUserInfo=sysUserInfoService.sysuserbycode(map.get("code"));
//												if(sysUserInfo!=null){
//													emaiList.add(sysUserInfo);
//												}
//												
//											}
//										
//											
//										}
										
										//匹配参会人数据
										for (BaseMeetingParp meetingParp : list) {
											if(meetingParp!=null 
													&& !meetingParp.getSys_user_code().equals(userInfo.getSys_user_code())){
												for (SysUserInfo emailinfo : emaiList) {
													if(emailinfo!=null && emailinfo.getSys_user_code().equals(meetingParp.getSys_user_code())){
														bool=false;
														break;
													}
												}
												if(bool==true){
													sysUserInfo=sysUserInfoService.sysuserbycode(meetingParp.getSys_user_code());
													if(sysUserInfo!=null){
														emaiList.add(sysUserInfo);
													}
												}else {
													bool=true;
												}
											}
										}
										
									}
									
									String address=request.getScheme()+"://"+request.getServerName()
									+":"+request.getServerPort()+request.getContextPath()+"/"+
									"meeting_info.html?meetingcode="+meetcode+"&logintype=";
									
									StringBuffer sBuffer=new StringBuffer();
									if(viewMeetingRele.getBase_meeting_invicont()!=null
											||viewMeetingRele.getBase_meeting_compcont()!=null){
										JSONArray jsonorg=JSONArray.fromObject(viewMeetingRele.getBase_meeting_invicont());
										JSONArray jsoncom=JSONArray.fromObject(viewMeetingRele.getBase_meeting_compcont());
										
										sBuffer.append("关于");
										if(viewMeetingRele.getBase_meeting_invicont()!=null){
											sBuffer.append("投资机构("+JSONObject.fromObject(jsonorg.get(0)).getString("name")+")");
											if(viewMeetingRele.getBase_meeting_compcont()!=null){
												sBuffer.append("，");
											}
										}
										if(viewMeetingRele.getBase_meeting_compcont()!=null){
											sBuffer.append("公司("+JSONObject.fromObject(jsoncom.get(0)).getString("name")+")");
										}
										sBuffer.append("的");
									}
									
									emailSenderService.sendMeetingEmail(userInfo.getSys_user_email(),userInfo.getSys_user_name(),address,"meetingUpdMessage.ftl",sBuffer.toString(),emaiList);
/*-----------------推送会议通知结束 -----------------------*/
									
								}
							}else{
								message="upfail";
							}
						}else {
							message="noroot";
						}
					}else {
						message="nomeeting";
					}
					
				}else {
					message="nouser";
				}
			} catch (Exception e) {
				message = "error";
				logger.error("MeetingAction.updateMeetingType 发生异常", e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("version", version);
				resultJSON.put("meetingTypeJson", JSONArray.fromObject(meetingTypeList));
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			}  catch (Exception e) {
				logger.error("MeetingAction.updateMeetingType() 发生异常", e);
				e.printStackTrace();
			}
			
		}
}
