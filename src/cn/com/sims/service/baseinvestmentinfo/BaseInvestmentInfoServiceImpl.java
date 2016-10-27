package cn.com.sims.service.baseinvestmentinfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.Investment.organizationinvesfund.IOrganizationInvesfundDao;
import cn.com.sims.dao.baseinveslabelinfo.IBaseInveslabelInfoDao;
import cn.com.sims.dao.baseinvesreponinfo.IBaseInvesreponInfoDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseinvesreponinfo.IBaseInvesreponInfoServiceImpl;
import cn.com.sims.service.investment.baseinvesnoteinfo.IBaseInvesnoteInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/**
 * 
 * @author duwenjie
 * @date 2015-10-14
 */

@Service
public class BaseInvestmentInfoServiceImpl implements IBaseInvestmentInfoService{

	@Resource
	IBaseInvestmentInfoDao dao;
	
	@Resource
	IBaseInveslabelInfoDao labeldao;//标签Dao
	
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	
	@Resource
	IOrganizationInvesfundDao fundDao;//投资机构基金
	
	@Resource
	IBaseInvesreponInfoDao ykDao;//易凯联系人
	
	@Resource
	IBaseInvesnoteInfoService baseInvesnoteInfoService;//投资机构note
	
	@Resource
	InvestorDao investorDao;//投资人
	
	private static final Logger gs_logger = Logger.getLogger(IBaseInvesreponInfoServiceImpl.class);
	
	@Override
	public int tranModifyBaseInvestment(
			String  userCode,
			String	base_investment_code	,
			String	base_investment_name	,
			String	base_investment_ename	,
			String	base_investment_fullname	,
			String	base_investment_img	,
			String	base_investment_stem	,
			String	base_investment_estate	,
			String	base_investment_namep	,
			String	base_investment_namepf	,
			String	tmp_Investment_Code	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime	,
			long	base_datalock_viewtype	,
			String	base_datalock_pltype	,
			String	sys_user_code	,
			String	base_datalock_sessionid	,
			String	base_datalock_viewtime)
			throws Exception {
		gs_logger.info("tranModifyBaseInvestment 方法开始");
		int data=0;
		baseInvestmentInfo info=new baseInvestmentInfo();
		try {
			info.setBase_investment_code(base_investment_code);
			info.setBase_investment_name(base_investment_name);
			info.setBase_investment_ename(base_investment_ename);
			info.setBase_investment_fullname(base_investment_fullname);
			info.setBase_investment_img(base_investment_img);
			info.setBase_investment_stem(base_investment_stem);
			info.setBase_investment_estate(base_investment_estate);
			info.setBase_investment_namep(base_investment_namep);
			info.setBase_investment_namepf(base_investment_namepf);
			info.setTmp_Investment_Code(tmp_Investment_Code);
			info.setTmp_Investment_Code(tmp_Investment_Code);
			info.setDeleteflag(deleteflag);
			info.setCreateid(createid);
			info.setCreatetime(createtime);
			info.setUpdid(updid);
			info.setUpdtime(updtime);
			info.setBase_datalock_viewtype(base_datalock_viewtype);
			info.setBase_datalock_pltype(base_datalock_pltype);
			info.setSys_user_code(sys_user_code);
			info.setBase_datalock_sessionid(base_datalock_sessionid);
			info.setBase_datalock_viewtime(base_datalock_viewtime);
			
			data=dao.tranModifyBaseInvestment("baseInvestmentInfo.tranModifyBaseInvestment", info);
		
			if(data!=0){
				/*调用存储过程*/
				storedProcedureService.callViewinvestment(new SysStoredProcedure(base_investment_code, "upd", CommonUtil.findNoteTxtOfXML("basic"), userCode,"",""));
				
			}
		} catch (Exception e) {
			gs_logger.error("tranModifyBaseInvestment 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("tranModifyBaseInvestment 方法结束");
		}
		return data;
	}

	@Override
	public baseInvestmentInfo findBaseInvestmentByCode(String orgCode)
			throws Exception {
		gs_logger.info("findBaseInvestmentByCode 方法开始");
		baseInvestmentInfo info=null;
		try {
			info=dao.findBaseInvestmentByCode("baseInvestmentInfo.findBaseInvestmentByCode", orgCode);
		} catch (Exception e) {
			gs_logger.error("findBaseInvestmentByCode 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("findBaseInvestmentByCode 方法结束");
		}
		return info;
	}

	@Override
	public String findOnlyInvementName(String orgCode,String name, String fullName, String eName)
			throws Exception {
		String data="";
		int cN=0;
		int fN=0;
		int eN=0;
		gs_logger.info("findOnlyInvementName 方法开始");
		baseInvestmentInfo base = new baseInvestmentInfo();
		base.setBase_investment_code(orgCode);
		base.setBase_investment_ename(eName);
		base.setBase_investment_fullname(fullName);
		base.setBase_investment_name(name);
		try {
			if(name!=null&&!name.trim().equals("")){
				cN=dao.findInvestmentOnlyName("baseInvestmentInfo.findInvestmentOnlyName", base);
			}
			if(fullName!=null&&!fullName.trim().equals("")){
				fN=dao.findInvestmentOnlyEName("baseInvestmentInfo.findInvestmentOnlyFullName", base);
			}
			if(eName!=null&&!eName.trim().equals("")){
				eN=dao.findInvestmentOnlyFullName("baseInvestmentInfo.findInvestmentOnlyEName", base);
			}
			
			
			if(cN==0){
				if(eN==0){
					if(fN==0){
						data="only";
					}else {
						data="机构全称已存在";
					}
				}else {
					if(fN==0){
						data="机构英文名称已存在";
					}else {
						data="机构英文名称,全称已存在";
					}
				}
			}else{
				if(eN==0){
					if(fN==0){
						data="机构简称已存在";
					}else {
						data="机构简称,全称已存在";
					}
				}else {
					if(fN==0){
						data="机构简称,英文名称已存在";
					}else {
						data="机构全称,简称,英文名称已存在";
					}
				}
			}
		} catch (Exception e) {
			gs_logger.error("findOnlyInvementName 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("findOnlyInvementName 方法结束");
		}
		return data;
	}

	@Override
	public List<Map<String, String>> findAllBaseInvestment() throws Exception {
		gs_logger.info("findAllBaseInvestment 方法开始");
		List<Map<String, String>> info=null;
		try {
			info=dao.findAllBaseInvestment("baseInvestmentInfo.findAllBaseInvestment");
		} catch (Exception e) {
			gs_logger.error("findAllBaseInvestment 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("findAllBaseInvestment 方法结束");
		}
		return info;
	}

	
//	/**
//	 * 添加投资机构信息（基础）
//	 * @param info 投资机构信息
//	 * @author duwenjie
//	 * @date 2015-11-4
//	 */
//	@Override
//	public void insertBaseInvestmentInfo(baseInvestmentInfo info) throws Exception{
//		gs_logger.info("insertBaseInvestmentInfo 方法开始");
//		try {
//			
//		} catch (Exception e) {
//			gs_logger.error("insertBaseInvestmentInfo 方法开始",e);
//			e.printStackTrace();
//			throw e;
//		}
//		gs_logger.info("insertBaseInvestmentInfo 方法结束");
//	}

	
	/**
	 * 添加投资机构信息（机构信息，基金，备注）
	 * @param investmentInfo 机构信息对象
	 * @param labelList　标签集合
	 * @param fundlList　基金集合
	 * @param note　机构备注
	 * @author duwenjie
	 * @date 2015-11-4
	 */
	@Override
	public void tranModifyInsertInvestmentInfo(
			SysUserInfo userInfo,
			baseInvestmentInfo investmentInfo,
			List<BaseInveslabelInfo> labelList,
			List<BaseInvesfundInfo> fundlList,
			List<BaseInvesreponInfo> ykList,
			BaseInvesnoteInfo note) throws Exception {
		gs_logger.info("tranModifyInsertInvestmentInfo 方法开始");
		try {
			//添加投资机构信息（基础）
			dao.insertBaseInvestmentInfo("baseInvestmentInfo.insertBaseInvestmentInfo", investmentInfo);
			
			if(labelList!=null){
				//添加标签
				for (BaseInveslabelInfo labelInfo : labelList) {
					labeldao.insertOrgInveslabelInfo("BaseInveslabelInfo.insertOrgInveslabelInfo",labelInfo );
				}
			}
			if(fundlList!=null){
				//保存投资机构基金
				for (BaseInvesfundInfo info : fundlList) {
					fundDao.insertInvesfund("BaseInvesfundInfo.insertInvesfund", info);
				}
			}
			if(ykList!=null){
				//保存易凯联系人
				for (BaseInvesreponInfo info :ykList) {
					ykDao.insertYKLinkMan("BaseInvesreponInfo.insertYKLinkMan",info);
				}
			}
			
			
			/*调用存储过程*/
			storedProcedureService.callViewinvestment(new SysStoredProcedure(investmentInfo.getBase_investment_code(), "add", CommonUtil.findNoteTxtOfXML("all"), userInfo.getSys_user_code(),"",""));
			
			
			if(note!=null){
				baseInvesnoteInfoService.insertOrgNote(note);//添加投资机构note到数据库
			}
			
			
		} catch (Exception e) {
			gs_logger.error("tranModifyInsertInvestmentInfo 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		
		gs_logger.info("tranModifyInsertInvestmentInfo 方法结束");
		
	}

	@Override
	public BaseInvestorInfo findOrgInvestorByName(Map<String, String> map)
			throws Exception {
		gs_logger.info("findOrgInvestorByName 方法开始");
		BaseInvestorInfo info=null;
		
		try {
			info=investorDao.findOrgInvestorByName("baseInvestorInfo.findOrgInvestorByName", map);
		} catch (Exception e) {
			gs_logger.error("findOrgInvestorByName 方法异常",e);
			throw e;
		}
		
		gs_logger.info("findOrgInvestorByName 方法结束");
		return info;
	}

}
