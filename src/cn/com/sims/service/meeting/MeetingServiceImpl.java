package cn.com.sims.service.meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.basecompentrepreneur.IBaseCompEntrepreneurDao;
import cn.com.sims.dao.baseentrepreneurinfo.IBaseEntrepreneurInfoDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.basemeetinginfo.IBaseMeetingInfoDao;
import cn.com.sims.dao.basemeetinglabelinfo.BaseMeetinglabelInfoDao;
import cn.com.sims.dao.basemeetingparp.IBaseMeetingParpDao;
import cn.com.sims.dao.basemeetingrele.IBaseMeetingReleDao;
import cn.com.sims.dao.basemeetingshare.IBaseMeetingShareDao;
import cn.com.sims.dao.baseupdloginfo.IBaseUpdlogInfoDao;
import cn.com.sims.dao.company.companycommon.CompanyCommonDao;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.meeting.MeetingDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.dao.system.sysuserwad.SysUserWadDao;
import cn.com.sims.dao.sysuserinfo.SysUserInfoDao;
import cn.com.sims.dao.syswadinfo.SysWadInfoDao;
import cn.com.sims.dao.viewmeetingrele.IViewMeetingReleDao;
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
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;
/**
 * 
 * @author zzg
 *@date 20155-10-14
 */
@Service
public class MeetingServiceImpl implements MeetingService {
	private static final Logger gs_logger = Logger
			.getLogger(MeetingServiceImpl.class);
	@Resource
	MeetingDao MeetingDao;
	@Resource
	IBaseMeetingShareDao shareDao;
	@Resource
	IBaseMeetingInfoDao meetingInfoDao;
	@Resource
	IBaseMeetingParpDao parpDao;
	@Resource
	IBaseMeetingReleDao releDao;
	@Resource
	IViewMeetingReleDao viewReleDao;
	@Resource
	IBaseUpdlogInfoDao updlogDao;
	@Resource
	SysUserWadDao wadUserDao;
	@Resource
	BaseMeetinglabelInfoDao labelInfoDao;
	
	// 机构基础
    @Resource
    IBaseInvestmentInfoDao baseinvestmentinfodao;
    //会议相关机构公司投资人信息基础
    @Resource
    IBaseMeetingReleDao basemeetingreledao;
    //机构与投资人关系dao基础
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
    // 投资人dao
    @Resource
    InvestorDao investordao;
    
    // 公司基础
    @Resource
    CompanyCommonDao companydao;
    //公司联系人基础
    @Resource
    IBaseEntrepreneurInfoDao baseentrepreneurinfodao;
    
    //公司联系人公司关系基础
    @Resource
    IBaseCompEntrepreneurDao basecompentrepreneurdao;
    //融资计划dao   
	@Resource
	CompanyDetailDao companyDetailDao;
    
	// 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
    
    // 系统用户
    @Resource
    SysUserInfoDao sysUserInfoDao;
	
	/**根据相关投资机构 公司 记录人 筛选会议列表
	 * @author zzg
	 * @date 20155-10-14
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ViewMeetingRele> screenlist(HashMap<String, Object> map)
			throws Exception {
		List<ViewMeetingRele>  list=null;
		gs_logger.info("MeetingServiceImpl screenlist方法开始");
		try {
			list=MeetingDao.screenlist("ViewMeetingRele.screenlist", map);
			gs_logger.info("MeetingServiceImpl screenlist方法结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl screenlist方法异常",e);
			throw e;
		}
		return list;
	}

	@Override
	public int screenlist_num(HashMap<String, Object> map) throws Exception {
		gs_logger.info("MeetingServiceImpl screenlist_num方法开始");
		int i=0;
		try {
			i=MeetingDao.screenlist_num("ViewMeetingRele.screenlist_num", map);
			gs_logger.info("MeetingServiceImpl screenlist_num方法结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl screenlist_num方法异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public ViewMeetingRele viewmeetingreleBycode(String id) throws Exception {
		ViewMeetingRele v=null;
		gs_logger.info("MeetingServiceImpl viewmeetingreleBycode方法开始");
		try {
			v=MeetingDao.viewmeetingreleBycode("ViewMeetingRele.viewmeetingreleBycode", id);
			gs_logger.info("MeetingServiceImpl viewmeetingreleBycode方法结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl viewmeetingreleBycode方法异常",e);
		throw e;
		}
		return v;
	}

	@Override
	public List<BaseMeetingNoteInfo> basemeetingnoteBymeetcode(String id)
			throws Exception {
		gs_logger.info("MeetingServiceImpl basemeetingnoteBymeetcode方法开始");
		List<BaseMeetingNoteInfo> list =null;
		try {
			list=MeetingDao.basemeetingnoteBymeetcode("BaseMeetingNoteInfo.meetingnotebymeetingcode", id);
			gs_logger.info("MeetingServiceImpl basemeetingnoteBymeetcode方法 结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl basemeetingnoteBymeetcode方法 异常",e);
			throw e;
		}
		return list;
	}

	@Override
	public int meetingnote_del(String id) throws Exception {
		gs_logger.info("MeetingServiceImpl basemeetingnoteBymeetcode方法 开始");
		int i=0;
		try {
			i=MeetingDao.meetingnote_del("BaseMeetingNoteInfo.meetingnote_del", id);
			gs_logger.info("MeetingServiceImpl basemeetingnoteBymeetcode方法 结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl basemeetingnoteBymeetcode方法 异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public int meetingnote_add(BaseMeetingNoteInfo b) throws Exception {
		gs_logger.info("MeetingServiceImpl meetingnote_add方法 开始");
		int i=0;
		try {
			MeetingDao.meetingnote_add("BaseMeetingNoteInfo.meetingnote_add", b);
			i=1;
			gs_logger.info("MeetingServiceImpl meetingnote_add方法 结束");
		} catch (Exception e) {
			i=0;
			gs_logger.error("MeetingServiceImpl meetingnote_add方法 异常",e);
			throw e;
		}
		return i;
	}

	/**
	 * 根据会议code查询分享范围
	 * @param code 会议code
	 * @param type 分享类型（1：筐，2：人）
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-4
	 */
	@Override
	public List<Map<String, String>> findMeetShareByMeetingCode(String code,String type)
			throws Exception {
		gs_logger.info("MeetingServiceImpl findUserShareByMeetingCode方法 开始");
		List<Map<String, String>> list=null;
		try {
			if(type!=null&&type.equals("1")){
				list=shareDao.findUserShareByMeetingCode("basemeetingshare.findBaskShareByMeetingCode", code);
			}else if (type!=null&&type.equals("2")) {
				list=shareDao.findUserShareByMeetingCode("basemeetingshare.findUserShareByMeetingCode", code);
			}
			gs_logger.info("MeetingServiceImpl findUserShareByMeetingCode方法 结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl findUserShareByMeetingCode方法 异常",e);
			throw e;
		}
		return list;
	}

	
	/**
	 * 根据会议code,筐code删除分享范围
	 * @param map (meetcode:会议code，baskcode：筐code)
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-4
	 */
	@Override
	public void tranModifyShareInfo(String createid,String meetingcode,List<BaseMeetingShare> addList,List<Map<String, String>> delList)throws Exception{
		gs_logger.info("MeetingServiceImpl deleteShare方法 开始");
		try {
			if(delList!=null){
				//删除分享
				for (Map<String, String> map : delList) {
					if(map!=null){
						shareDao.deleteShare("basemeetingshare.deleteShare", map);
					}
				}
			}
			if(addList!=null){
				//添加分享范围
				for (BaseMeetingShare shareInfo: addList) {
					if(shareInfo!=null){
						shareDao.addmeetingshare("basemeetingshare.addmeetingshare", shareInfo);
					}
				}
				
			}
			if(addList!=null && delList!=null){
				/*添加会议， 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
						"SysStoredProcedure.callViewmeetingrele",
						new SysStoredProcedure(
							meetingcode,"upd","",
							createid,"", ""));
				
			}
			
			gs_logger.info("MeetingServiceImpl deleteShare方法 结束");
		} catch (Exception e) {
			gs_logger.error("MeetingServiceImpl deleteShare方法 异常",e);
			throw e;
		}
		
	}

	/**
	 * 根据会议code删除会议相关信息
	 * @param meetingcode
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-7
	 */
	@Override
	public void tranDeleteMeetingInfoByCode(String meetingcode)
			throws Exception {
		gs_logger.info("tranDeleteMeetingInfoByCode 方法开始");
		Map<String, String> map=new HashMap<String, String>();
		try {
			map.put("elecode", meetingcode);
			map.put("logtype", CommonUtil.findNoteTxtOfXML("Lable-meeting"));
			
			meetingInfoDao.deleteMeetingInfoByCode("basemeetinginfo.deleteMeetingInfoByCode", meetingcode);
			
			shareDao.deleteShareByMeetingCode("basemeetingshare.deleteShareByCode", meetingcode);
			
			parpDao.deleteMeetingParpByCode("basemeetingparp.deleteMeetingParpByCode", meetingcode);
			
			releDao.deleteMeetingReleByCode("basemeetingrele.deleteMeetingReleByCode", meetingcode);
			
			viewReleDao.deleteMeetingReleByCode("ViewMeetingRele.deleteMeetingRele", meetingcode);
			
			MeetingDao.meetingnote_del("BaseMeetingNoteInfo.deleteMeetingnoteByMeetcode", meetingcode);
			
			updlogDao.deleteUpdlogByElecode("BaseUpdlogInfo.deleteUpdlogByElecode", map);
		} catch (Exception e) {
			gs_logger.error("tranDeleteMeetingInfoByCode 方法异常",e);
			throw e;
		}
		gs_logger.info("tranDeleteMeetingInfoByCode 方法结束");
		
	}

	
	/**
	 * 修改会议信息
	 * @param info
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-9
	 */
	@Override
	public int updateBaseMeetingInfo(BaseMeetingInfo info) throws Exception {
		gs_logger.info("updateBaseMeetingInfo 方法开始");
		int data=0;
		try {
			if(info!=null){
				data=meetingInfoDao.updateBaseMeetingInfo("basemeetinginfo.updateBaseMeetingInfo", info);
			}
		} catch (Exception e) {
			gs_logger.error("updateBaseMeetingInfo 方法异常",e);
			throw e;
		}
		gs_logger.info("updateBaseMeetingInfo 方法结束");
		return data;
	}

	/**
	 * 根据会议code查询会议信息
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-9
	 */
	@Override
	public BaseMeetingInfo findBaseMeetingInfoByCode(String meetingcode)
			throws Exception {
		gs_logger.info("findBaseMeetingInfoByCode 方法开始");
		BaseMeetingInfo meetingInfo = null;
		try {
			meetingInfo=meetingInfoDao.findBaseMeetingInfo("basemeetinginfo.findBaseMeetingInfo", meetingcode);
		} catch (Exception e) {
			gs_logger.error("findBaseMeetingInfoByCode 方法异常",e);
			throw e;
		}
		gs_logger.info("findBaseMeetingInfoByCode 方法结束");
		return meetingInfo;
	}

	/**
	 * 修改会议参会人
	 * @param meetcode
	 * @param list
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-10
	 */
	@Override
	public void tranModifyMeetingParp(String createid,String meetingcode,List<Map<String, String>> dellist,
			List<BaseMeetingParp> list) throws Exception {
		gs_logger.info("tranModifyMeetingParp 方法结束");
		try {
			if(dellist!=null){
				for (Map<String, String> map : dellist) {
					parpDao.deleteMeetingParpByUser("basemeetingparp.deleteMeetingParpByUser", map);
				}
			}
			
			if(list!=null){
				for (BaseMeetingParp baseMeetingParp : list) {
					parpDao.addmeetingparp("basemeetingparp.addmeetingparp", baseMeetingParp);
				}
			}
			
			if(dellist!=null && list!=null){
				/*修改会议， 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
						"SysStoredProcedure.callViewmeetingrele",
						new SysStoredProcedure(
							meetingcode,"upd","",
							createid,"", ""));
				
			}
		} catch (Exception e) {
			gs_logger.info("tranModifyMeetingParp 方法结束");
			throw e;
		}
		gs_logger.info("tranModifyMeetingParp 方法结束");
		
	}
	
	/**
	 * 修改会议相关机构
	 * @param baseinvestmentinfo 机构信息
	 * @param investorInfo 投资人信息
	 * @param investmentinvestor 机构投资人关系
	 * @param meetingrele 会议相关机构
	 * @throws Exception
	 * @date 2016-3-14
	 * @author duwenjie
	 */
	@Override
	public void tranModifyMeetingOrg(baseInvestmentInfo baseinvestmentinfo,
			BaseInvestorInfo investorInfo,
			BaseInvestmentInvestor investmentinvestor,
			BaseMeetingRele meetingrele) throws Exception {
		gs_logger.info("tranModifyMeetingOrg方法开始");
		try {
            //
            if(investmentinvestor!=null){
            	//添加投资机构
    			if(baseinvestmentinfo.getCreateid()!=null){
    				baseinvestmentinfodao.insertBaseInvestmentforIT(
    						"baseInvestmentInfo.insertBaseInvestmentforIT",baseinvestmentinfo);
    				/* 调用存储过程,更新业务数据库 */
    				sysstoredproceduredao.callViewinvestment(
    							"SysStoredProcedure.callViewinvestment",
    							new SysStoredProcedure(baseinvestmentinfo.getBase_investment_code(),"add","all",
    							baseinvestmentinfo.getCreateid(),"", ""));
    			}
    			
    			if(investorInfo.getCreateid()!=null){
    				//添加投资人
            		investordao.insertBaseInvestorforIT(
            			"baseInvestorInfo.insertBaseInvestorforIT",investorInfo);
	    			/* 调用存储过程,更新业务数据库 */
	                sysstoredproceduredao.callViewinvestment(
        				"SysStoredProcedure.callViewinvestorInfo",
        				new SysStoredProcedure(investorInfo.getBase_investor_code(),"add","all",
        						investorInfo.getCreateid(),"", ""));
    			}
            	
            	//投资机构投资人关系
        		baseinvestmentinvestordao.insertInvestmentInvestorforit(
    			"BaseInvestmentInvestor.insertInvestmentInvestorforit",investmentinvestor);
    			/* 调用存储过程,更新业务数据库 */
            	sysstoredproceduredao.callViewinvestment(
            			"SysStoredProcedure.callViewinvestorInfo",
            			new SysStoredProcedure(
            					investmentinvestor.getBase_investor_code(),"upd","investment",
            					investmentinvestor.getCreateid(),"", ""));
    		}
        	
        	//会议相关公司机构
        	basemeetingreledao.addmeetingrele("basemeetingrele.addmeetingrele", meetingrele);
        	/*修改会议， 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
					"SysStoredProcedure.callViewmeetingrele",
					new SysStoredProcedure(
						meetingrele.getBase_meeting_code(),"upd","",
						meetingrele.getCreateid(),"", ""));
			
		} catch (Exception e) {
			gs_logger.error("tranModifyMeetingOrg方法异常",e);
			throw e;
		}
		
		gs_logger.info("tranModifyMeetingOrg方法结束");
	}

	/**
	 * 删除会议相关机构信息
	 * @param createid
	 * @param map （meetingcode：会议code，reletype：相关类型（1：机构，2：公司），relecode：相关code）
	 * @throws Exception
	 * @date 2016-3-14
	 * @author duwenjie
	 */
	@Override
	public int tranDeleteMeetingReleOrg(String createid,
			Map<String, String> map) throws Exception {
		gs_logger.info("tranDeleteMeetingReleByType 方法开始");
		int data=0;
		try {
			data=releDao.deleteMeetingReleByType("basemeetingrele.deleteMeetingReleByType", map);
			if(data>0){
				/*修改会议， 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
						"SysStoredProcedure.callViewmeetingrele",
						new SysStoredProcedure(
							map.get("meetingcode"),"upd","",
							createid,"", ""));
			}
		} catch (Exception e) {
			gs_logger.error("tranDeleteMeetingReleByType 方法异常",e);
			throw e;
		}
		gs_logger.info("tranDeleteMeetingReleByType 方法结束");
		return data;
	}
	
	/**
	 * 删除会议相关公司信息
	 * @param createid
	 * @param map （meetingcode：会议code，reletype：相关类型（1：机构，2：公司），relecode：相关code）
	 * @throws Exception
	 * @date 2016-3-15
	 * @author duwenjie
	 */
	@Override
	public int tranDeleteMeetingReleCompany(String createid,
			Map<String, String> map) throws Exception {
		gs_logger.info("tranDeleteMeetingReleCompany 方法开始");
		int data=0;
		try {
			data=releDao.deleteMeetingReleByType("basemeetingrele.deleteMeetingReleByType", map);
			
			if(data>0){
				/*修改会议， 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
						"SysStoredProcedure.callViewmeetingrele",
						new SysStoredProcedure(
							map.get("meetingcode"),"upd","",
							createid,"", ""));
			}
		} catch (Exception e) {
			gs_logger.error("tranDeleteMeetingReleCompany 方法异常",e);
			throw e;
		}
		gs_logger.info("tranDeleteMeetingReleCompany 方法结束");
		return data;
	}
	

	/**
	 * 查询会议与会人
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @date 2016-3-14
	 * @author duwenjie
	 */
	@Override
	public List<BaseMeetingParp> findMeetingUserInfo(String meetingcode)
			throws Exception {
		gs_logger.info("findMeetingUserInfo 方法开始");
		List<BaseMeetingParp>  data=null;
		try {
			data=parpDao.findMeetingUserInfo("basemeetingparp.findMeetingUserInfo", meetingcode);
		} catch (Exception e) {
			gs_logger.error("findMeetingUserInfo 方法异常",e);
			throw e;
		}
		gs_logger.info("findMeetingUserInfo 方法结束");
		return data;
	}

	
	/**
	 * 修改会议相关公司
	 * @param basecompinfo 公司
	 * @param userinfo 联系人
	 * @param basefinancplaninfo 融资计划
	 * @param emailList 发送邮件
	 * @param basemeetingrele 公司会议关系
	 * @throws Exception
	 * @date 2016-3-15
	 * @author duwenjie
	 */
	@Override
	public void tranModifyMeetingCompany(BaseCompInfo basecompinfo,
			BaseEntrepreneurInfo userinfo,
			BaseCompEntrepreneur basecompentrepreneur,
			BaseFinancplanInfo basefinancplaninfo,
			List<BaseFinancplanEmail> emailList,
			BaseMeetingRele basemeetingrele) throws Exception {
		gs_logger.info("tranModifyMeetingCompany 方法开始");
		try {
			
			if(basecompinfo!=null && basecompinfo.getCreateid()!=null){
				//添加公司
	    		companydao.insertBaseCompforIT(
	    			    "BaseCompdInfo.insertBaseCompforIT", basecompinfo);
				/* 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
					"SysStoredProcedure.callViewcompinfo",
					new SysStoredProcedure(
						basecompinfo.getBase_comp_code(),"add","all",
						basecompinfo.getCreateid(),"", ""));
			}
			
	    	if(userinfo!=null && userinfo.getCreateid()!=null){
	    		 //添加公司联系人
		    	baseentrepreneurinfodao.insertBaseEntrepreneurforIT(
		    			    "baseentrepreneurinfo.insertCompPeople", userinfo);
	    	}		
			
	    	if((basecompinfo!=null && basecompinfo.getCreateid()!=null)
	    			||(userinfo!=null && userinfo.getCreateid()!=null)){
	    		//添加公司联系人关系
			    basecompentrepreneurdao.insertCompEntrepreneurforit(
				    	"basecompentrepreneur.insertCompEntrepreneurforit",basecompentrepreneur);
			    /* 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
					"SysStoredProcedure.callViewcompinfo",
					new SysStoredProcedure(
							basecompentrepreneur.getBase_comp_code(),"upd","compperson",
							basecompentrepreneur.getCreateid(),"", ""));
	    	}
	    	
	    		
	    	if(basefinancplaninfo!=null){
	    		//添加公司融资计划
	    		companyDetailDao.insertFinancplan(
	    		"BaseFinancplanInfo.insertFinancplan", basefinancplaninfo);
	    		if(emailList!=null && emailList.size()>0){
	    			//添加公司融资计划提醒人员
			    	for(int i=0;i<emailList.size();i++){
				    	BaseFinancplanEmail basefinancplanemail=emailList.get(i);
				    	companyDetailDao.insertPlanEamil(
				    		"BaseFinancplanEmail.insertPlanEamil",basefinancplanemail);
			    	}
	    		}
	    	}

	    	//会议相关公司机构
        	basemeetingreledao.addmeetingrele("basemeetingrele.addmeetingrele", basemeetingrele);
        	/*修改会议， 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
					"SysStoredProcedure.callViewmeetingrele",
					new SysStoredProcedure(
							basemeetingrele.getBase_meeting_code(),"upd","",
							basemeetingrele.getCreateid(),"", ""));
			
		} catch (Exception e) {
			gs_logger.error("tranModifyMeetingCompany 方法异常",e);
			throw e;
		}
		gs_logger.info("tranModifyMeetingCompany 方法结束");
	}

	/**
	 * 根据筐code查询用户信息
	 * 
	 * @param wadcode 筐code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-22
	 */
	@Override
	public List<SysUserInfo> findUserInfoByBaskCode(String wadcode)
			throws Exception {
		gs_logger.info("findUserInfoByBaskCode 方法开始");
		List<SysUserInfo> list=null;
		try {
			list=sysUserInfoDao.findUserInfoByBaskCode("SysUserInfo.findUserInfoByBaskCode", wadcode);
		} catch (Exception e) {
			gs_logger.error("findUserInfoByBaskCode 方法异常",e);
			throw e;
		}
		gs_logger.info("findUserInfoByBaskCode 方法结束");
		return list;
	}

	
	/**
	 * 查询登录用户是否有权限查看会议
	 * @param map
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-22
	 */
	@Override
	public String screenloginInfo(Map<String, Object> map) throws Exception {
		gs_logger.info("screenloginInfo 方法开始");
		String data="";
		try {
			data=viewReleDao.screenloginInfo("ViewMeetingRele.screenloginInfo", map);
		} catch (Exception e) {
			gs_logger.error("screenloginInfo 方法异常",e);
			throw e;
		}
		gs_logger.info("screenloginInfo 方法结束");
		return data;
	}

	
	/**
	 * 修改会议信息
	 * @param meetingInfo
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-23
	 */
	@Override
	public int updateMeetingContent(BaseMeetingInfo meetingInfo)
			throws Exception {
		gs_logger.info("updateMeetingContent 方法开始");
		int data = 0;
		try {
			if(meetingInfo!=null){
				data=meetingInfoDao.updateBaseMeetingInfo("basemeetinginfo.updateBaseMeetingInfo", meetingInfo);
			}
			if(data>0){
				/*修改会议， 调用存储过程,更新业务数据库 */
				sysstoredproceduredao.callViewinvestment(
						"SysStoredProcedure.callViewmeetingrele",
						new SysStoredProcedure(
							meetingInfo.getBase_meeting_code(),"upd","",
							meetingInfo.getUpdid(),"", ""));
			}
		} catch (Exception e) {
			gs_logger.error("updateMeetingContent 方法异常",e);
			throw e;
		}
		gs_logger.info("updateMeetingContent 方法结束");
		return data;
	}

	/**
	 * 查询会议分享的筐是否都包含指定权限
	 * @param meetingcode
	 * @param juri
	 * @return true:分享筐存在指定权限，false:未分享或不全存在
	 * @throws Exception
	 */
	@Override
	public boolean findMeetingJuri(String meetingcode, String juri)
			throws Exception {
		gs_logger.info("findMeetingJuri 方法开始");
		List<Map<String, String>> list=null;
		Map<String, String> map=null;
		boolean bool=false;
		
		try {
			list=shareDao.findUserShareByMeetingCode("basemeetingshare.findBaskShareByMeetingCode", meetingcode);
			if(list!=null){
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i)!=null
							&&!list.get(i).get("code").equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))
							&&!list.get(i).get("code").equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))){
						map=new HashMap<String, String>();
						map.put("wadcode", list.get(i).get("code"));
						map.put("juricode", juri);
						int data=wadUserDao.findBaskJuri("SysWadJuri.findBaskJuri", map);
						if(data>0){
							bool=true;
						}else {
							bool=false;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			gs_logger.error("findMeetingJuri　方法异常",e);
			throw e;
		}
		gs_logger.info("findMeetingJuri 方法结束");
		return bool;
	}

	@Override
	public void tranModifyUpdateMeetingType(BaseMeetingLabelInfo info)
			throws Exception {
		gs_logger.info("tranModifyUpdateMeetingType 方法开始");
		Map<String, String> map=null;
		try {
			map=new HashMap<String, String>();
			map.put("meetingcode", info.getBase_meeting_code());
			map.put("labelcode", info.getSys_label_code());
			map.put("elementcode", info.getSys_labelelement_code());
			
			labelInfoDao.deleteBaseMeetingLabelInfo("BaseMeetingLabelInfo.deleteBaseMeetingLabelInfo", map);
			if(info.getSys_labelelement_code()!=null
					&&!info.getSys_labelelement_code().equals("")){
				labelInfoDao.insertBaseMeetingLabelInfo("BaseMeetingLabelInfo.insertBaseMeetingLabelInfo", info);
			}
			
			/*添加会议， 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
					"SysStoredProcedure.callViewmeetingrele",
					new SysStoredProcedure(
						info.getBase_meeting_code(),"upd","",
						info.getUpdid(),"", ""));
		} catch (Exception e) {
			gs_logger.error("tranModifyUpdateMeetingType　方法异常",e);
			throw e;
		}
		gs_logger.info("tranModifyUpdateMeetingType 方法结束");
	}

}
