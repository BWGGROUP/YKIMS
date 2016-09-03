package cn.com.sims.service.meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.Investment.InvestmentDao;
import cn.com.sims.dao.basecompentrepreneur.IBaseCompEntrepreneurDao;
import cn.com.sims.dao.baseentrepreneurinfo.IBaseEntrepreneurInfoDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.basemeetinginfo.IBaseMeetingInfoDao;
import cn.com.sims.dao.basemeetinglabelinfo.BaseMeetinglabelInfoDao;
import cn.com.sims.dao.basemeetingparp.IBaseMeetingParpDao;
import cn.com.sims.dao.basemeetingrele.IBaseMeetingReleDao;
import cn.com.sims.dao.basemeetingshare.IBaseMeetingShareDao;
import cn.com.sims.dao.company.companycommon.CompanyCommonDao;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.meeting.MeetingDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.dao.sysuserinfo.SysUserInfoDao;
import cn.com.sims.dao.syswadinfo.SysWadInfoDao;
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

/**
 * @ClassName: MeetingAddServiceImpl
 * @author rqq
 * @date 2015-11-02
 */
@Service
public class MeetingAddServiceImpl implements IMeetingAddService {

    // 投资人dao
    @Resource
    InvestorDao investordao;
    
    // 机构dao业务
    @Resource
    InvestmentDao investmentdao;
    
    // 机构基础
    @Resource
    IBaseInvestmentInfoDao baseinvestmentinfodao;
    
    // 公司基础
    @Resource
    CompanyCommonDao companydao;
    
    // 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
    
    //机构与投资人关系dao基础
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
    
    
    //公司联系人基础
    @Resource
    IBaseEntrepreneurInfoDao baseentrepreneurinfodao;
    
    //公司联系人公司关系基础
    @Resource
    IBaseCompEntrepreneurDao basecompentrepreneurdao;
    
    //易凯用户基础
    @Resource
    SysUserInfoDao sysuserinfodao;
    
    //系统筐基础
    @Resource
    SysWadInfoDao syswadinfodao;
    
    //会议信息基础
    @Resource
    IBaseMeetingInfoDao basemeetinginfodao;
    
    //会议信息分享范围
    @Resource
    IBaseMeetingShareDao basemeetingsharedao;
    
    //会议与会者信息基础
    @Resource
    IBaseMeetingParpDao basemeetingparpdao;
    
    //会议相关机构公司投资人信息基础
    @Resource
    IBaseMeetingReleDao basemeetingreledao;
    
    //会议note信息基础
    @Resource
    MeetingDao meetingdao;
    //融资计划dao   
	@Resource
	CompanyDetailDao companyDetailDao;
	
	@Resource
	BaseMeetinglabelInfoDao meetinglabelInfoDao;
	
    private static final Logger gs_logger = Logger
	    .getLogger(MeetingAddServiceImpl.class);

    @Override
    public int queryEntrepreneurlistnumBycompId(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("MeetingAddServiceImpl queryEntrepreneurlistnumBycompId方法开始");
    	int i=0;
    	try {
    		i=baseentrepreneurinfodao.queryEntrepreneurlistnumBycompId
    			("baseentrepreneurinfo.queryEntrepreneurlistnumBycompId", map);

    	} catch (Exception e) {
    		gs_logger.error("MeetingAddServiceImpl queryEntrepreneurlistnumBycompId方法异常",e);
    		throw e;
    		}
		gs_logger.info("MeetingAddServiceImpl queryEntrepreneurlistnumBycompId方法结束");
    	return i;
    }

    @Override
    public List<BaseEntrepreneurInfo> queryEntrepreneurlistBycompId(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("MeetingAddServiceImpl queryEntrepreneurlistBycompId方法开始");
    	List<BaseEntrepreneurInfo> list = new ArrayList<BaseEntrepreneurInfo>();
    	try {
    		list=baseentrepreneurinfodao.queryEntrepreneurlistBycompId
    			("baseentrepreneurinfo.queryEntrepreneurlistBycompId", map);
    	} catch (Exception e) {
    		gs_logger.error("MeetingAddServiceImpl queryEntrepreneurlistBycompId方法异常",e);
    		throw e;
    		}
    	gs_logger.info("MeetingAddServiceImpl queryEntrepreneurlistBycompId方法结束");
    	return list;
    }

    @Override
    public List<Map<String, String>> querysysuserinfo() throws Exception {
		gs_logger.info("MeetingAddServiceImpl querysysuserinfo方法开始");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    	try {
    		list=sysuserinfodao.querysysuserinfo
    			("SysUserInfo.querysysuserinfo");
    	} catch (Exception e) {
    		gs_logger.error("MeetingAddServiceImpl querysysuserinfo方法异常",e);
    		throw e;
    		}
    	gs_logger.info("MeetingAddServiceImpl querysysuserinfo方法结束");
    	return list;
    }

    @Override
    public List<Map<String, String>> querysyswadinfo() throws Exception {
	gs_logger.info("MeetingAddServiceImpl querysyswadinfo方法开始");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	try {
		list=syswadinfodao.querysyswadinfo
			("SysWadInfo.querysyswadinfo");
	} catch (Exception e) {
		gs_logger.error("MeetingAddServiceImpl querysyswadinfo方法异常",e);
		throw e;
		}
	gs_logger.info("MeetingAddServiceImpl querysyswadinfo方法结束");
	return list;
    }
    
    @Override
    public List<Map<String, String>> querysupersyswadinfo() throws Exception {
	gs_logger.info("MeetingAddServiceImpl querysupersyswadinfo方法开始");
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	try {
		list=syswadinfodao.querysyswadinfo
			("SysWadInfo.querysupersyswadinfo");
	} catch (Exception e) {
		gs_logger.error("MeetingAddServiceImpl querysupersyswadinfo方法异常",e);
		throw e;
		}
	gs_logger.info("MeetingAddServiceImpl querysupersyswadinfo方法结束");
	return list;
    }

    @Override
    public void tranModifyaddmeetinginfo(List<BaseCompInfo> basecompinfolist,
		List<BaseFinancplanInfo> basefinancplaninfolist,
		List<BaseFinancplanEmail> basefinancplanemaillist,
	    List<BaseEntrepreneurInfo> baseentrepreneurlist,
	    List<BaseCompEntrepreneur> basecompentrepreneurlist,
	    List<baseInvestmentInfo> baseinvestmentinfolist,
	    List<BaseInvestorInfo> baseinvestorinfolist,
	    List<BaseInvestmentInvestor> baseinvestmentinvestorlist,
	    BaseMeetingInfo basemeetinginfo,
	    List<BaseMeetingShare> basemeetingsharellist,
	    List<BaseMeetingParp> basemeetingparplist,
	    List<BaseMeetingRele> basemeetingrelelist,
	    BaseMeetingNoteInfo basemeetingnoteinfo,
	    List<BaseMeetingLabelInfo> meetinglabellist) throws Exception {
	gs_logger.info("MeetingAddServiceImpl tranModifyaddmeetinginfo方法开始");
    	try {
    	    //添加公司
    	for(int i=0;i<basecompinfolist.size();i++){
    	BaseCompInfo basecompinfo=basecompinfolist.get(i);
    		companydao.insertBaseCompforIT(
    			    "BaseCompdInfo.insertBaseCompforIT", basecompinfo);
			/* 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewcompinfo",
				new SysStoredProcedure(
					basecompinfo.getBase_comp_code(),"add","all",
					basecompinfo.getCreateid(),"", ""));
    			}
    	 //添加公司联系人
    	for(int i=0;i<baseentrepreneurlist.size();i++){
    	BaseEntrepreneurInfo baseentrepreneurinfo=baseentrepreneurlist.get(i);
    	baseentrepreneurinfodao.insertBaseEntrepreneurforIT(
    			    "baseentrepreneurinfo.insertCompPeople", baseentrepreneurinfo);
    			}
    		
    	//添加公司联系人关系
		for(int i=0;i<basecompentrepreneurlist.size();i++){
		    BaseCompEntrepreneur basecompentrepreneur=basecompentrepreneurlist.get(i);
		    basecompentrepreneurdao.insertCompEntrepreneurforit(
			    	"basecompentrepreneur.insertCompEntrepreneurforit",basecompentrepreneur);
		    /* 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewcompinfo",
				new SysStoredProcedure(
					basecompentrepreneur.getBase_comp_code(),"upd","compperson",
					basecompentrepreneur.getCreateid(),"", ""));
			}
	    	    //添加公司融资计划
	    	for(int i=0;i<basefinancplaninfolist.size();i++){
	    	BaseFinancplanInfo basefinancplaninfo=basefinancplaninfolist.get(i);
	    		companyDetailDao.insertFinancplan(
	    		"BaseFinancplanInfo.insertFinancplan", basefinancplaninfo);
	    			}
	    	 //添加公司融资计划提醒人员
	    	for(int i=0;i<basefinancplanemaillist.size();i++){
	    	BaseFinancplanEmail basefinancplanemail=basefinancplanemaillist.get(i);
	    	companyDetailDao.insertPlanEamil(
	    		"BaseFinancplanEmail.insertPlanEamil",basefinancplanemail);
	    			}
    		//添加投资机构
    		for(int i=0;i<baseinvestmentinfolist.size();i++){
    		baseInvestmentInfo baseinvestmentinfo=baseinvestmentinfolist.get(i);
    		baseinvestmentinfodao.insertBaseInvestmentforIT(
			"baseInvestmentInfo.insertBaseInvestmentforIT",baseinvestmentinfo);
			/* 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewinvestment",
				new SysStoredProcedure(baseinvestmentinfo.getBase_investment_code(),"add","all",
					baseinvestmentinfo.getCreateid(),"", ""));
    			}
    		
    		//添加投资人
    		for(int i=0;i<baseinvestorinfolist.size();i++){
    		BaseInvestorInfo baseinvestorinfo=baseinvestorinfolist.get(i);
        		investordao.insertBaseInvestorforIT(
        			"baseInvestorInfo.insertBaseInvestorforIT",baseinvestorinfo);
			/* 调用存储过程,更新业务数据库 */
            sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewinvestorInfo",
    				new SysStoredProcedure(baseinvestorinfo.getBase_investor_code(),"add","all",
    					baseinvestorinfo.getCreateid(),"", ""));
    			}
    		
    		//添加投资人机构关系
    		for(int i=0;i<baseinvestmentinvestorlist.size();i++){
    		BaseInvestmentInvestor baseinvestmentinvestor=baseinvestmentinvestorlist.get(i);
    		baseinvestmentinvestordao.insertInvestmentInvestorforit(
			"BaseInvestmentInvestor.insertInvestmentInvestorforit",baseinvestmentinvestor);
			/* 调用存储过程,更新业务数据库 */
        	sysstoredproceduredao.callViewinvestment(
        			"SysStoredProcedure.callViewinvestorInfo",
        			new SysStoredProcedure(
        				baseinvestmentinvestor.getBase_investor_code(),"upd","investment",
        				baseinvestmentinvestor.getCreateid(),"", ""));
    			}
    		
    		   //添加会议
    		basemeetinginfodao.addmeetinginfo(
				"basemeetinginfo.addmeetinginfo", basemeetinginfo);
    		
    		//添加会议分享范围
    		for (int i = 0; i < basemeetingsharellist.size(); i++) {
    		basemeetingsharedao.addmeetingshare(
			    "basemeetingshare.addmeetingshare",
			    basemeetingsharellist.get(i));
			}
    		//添加会议参会者
    		for (int i = 0; i < basemeetingparplist.size(); i++) {
    		    basemeetingparpdao.addmeetingparp(
    				"basemeetingparp.addmeetingparp", basemeetingparplist.get(i));
			}
    		//添加会议相关机构公司
    		for (int i = 0; i < basemeetingrelelist.size(); i++) {
    		    	basemeetingreledao.addmeetingrele(
    				"basemeetingrele.addmeetingrele", basemeetingrelelist.get(i));
			}
    		
    		if(meetinglabellist!=null&&meetinglabellist.size()>0){
    			for (BaseMeetingLabelInfo info : meetinglabellist) {
					if(info!=null){
						meetinglabelInfoDao.insertBaseMeetingLabelInfo("BaseMeetingLabelInfo.insertBaseMeetingLabelInfo", info);
					}
				}
    		}
    		
		/*添加会议， 调用存储过程,更新业务数据库 */
		sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewmeetingrele",
				new SysStoredProcedure(
					basemeetinginfo.getBase_meeting_code(),"add","",
					basemeetinginfo.getCreateid(),"", ""));
		  //添加会议note
    		if(basemeetingnoteinfo!=null && !"".equals(basemeetingnoteinfo)){
    		meetingdao.meetingnote_add("BaseMeetingNoteInfo.meetingnote_add", basemeetingnoteinfo);
    			}
    		gs_logger.info("MeetingAddServiceImpl tranModifyaddmeetinginfo方法结束");
    	} catch (Exception e) {
    		gs_logger.error("MeetingAddServiceImpl tranModifyaddmeetinginfo方法异常",e);
    		throw e;
    		}
	
    }
}