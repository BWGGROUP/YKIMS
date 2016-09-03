package cn.com.sims.service.investment.organizationinvesfund;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.Investment.organizationinvesfund.IOrganizationInvesfundDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;

@Service
public class OrganizationInvesfundServiceImpl implements IOrganizationInvesfundService {

	@Resource
	IOrganizationInvesfundDao dao;
	
	@Resource
	ISysStoredProcedureDao sysStoredao;//存储过程
	
	private static final Logger gs_logger = Logger.getLogger(OrganizationInvesfundServiceImpl.class);
	
	
	/**
	 * 根据机构ＩＤ查询机构基金信息（基础层）
	 * @param 机构ｉｄ
	 * @return　基金集合
	 * @throws Exception
	 */
	@Override
	public List<BaseInvesfundInfo> findInvesfundByOrganizationId(
			String orgCode) throws Exception {
		gs_logger.info(" findInvesfundByOrganizationId方法开始");
		List<BaseInvesfundInfo> list = null;
		try {
			list= dao.findInvesfundByOrganizationId("BaseInvesfundInfo.findInvesfundByOrganizationId", orgCode); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvesfundByOrganizationId方法异常",e);
			throw e;
		}
		gs_logger.info("findInvesfundByOrganizationId方法结束");
		return list;
	}


	@Override
	public List<BaseInvesfundInfo> findPageInvesfundByOrgId(
			String orgCode,Page page) throws Exception {
		gs_logger.info(" findInvesfundByOrganizationId方法开始");
		List<BaseInvesfundInfo> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map.put("pageSize", page.getPageSize()+"");
			map.put("startCount", page.getStartCount()+"");
			map.put("orgCode", orgCode);
			list= dao.findPageInvesfundByOrgId("BaseInvesfundInfo.findPageInvesfundByOrgId", map); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvesfundByOrganizationId方法异常",e);
			throw e;
		}
		gs_logger.info("findInvesfundByOrganizationId方法结束");
		return list;
	}

	/**
	 * 添加投资机构基金信息
	 * @param str
	 * @param 新增投资机构基金对象
	 * @throws Exception
	 * 
	 * 2015-11-26 duwenjie 更改方法名，调用存储过程
	 */
	@Override
	public void tranSaveInsertInvesfund(BaseInvesfundInfo info,String userId)
			throws Exception {
		gs_logger.info("insertInvesfund方法开始");
		try {
			dao.insertInvesfund("BaseInvesfundInfo.insertInvesfund", info); 
			sysStoredao.callViewinvestment("SysStoredProcedure.callViewinvestment",new SysStoredProcedure(info.getBase_investment_code(), "upd", CommonUtil.findNoteTxtOfXML("Lable-currency"),userId, "", ""));
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("insertInvesfund方法异常",e);
			throw e;
		}finally{
			gs_logger.info("insertInvesfund方法结束");
		}
	}


	@Override
	public int findCountTfundByOrgId(String orgCode) throws Exception {
		gs_logger.info("insertInvesfund方法开始");
		int data=0;
		try {
			data=dao.findCountTfundByOrgId("BaseInvesfundInfo.findCountTfundByOrgId", orgCode); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("insertInvesfund方法异常",e);
			throw e;
		}finally{
			gs_logger.info("insertInvesfund方法结束");
		}
		return data;
	}


	/**
	 * 修改投资机构基金
	 * @param info 修改基金对象
	 * @param userId 用户ID
	 * @author duwenjie
	 * @date 2015-11-30
	 */
	@Override
	public int tranModifyInvesfund(BaseInvesfundInfo info, String userId)
			throws Exception {
		gs_logger.info("tranModifyInvesfund方法开始");
		int data=0;
		try {
			data=dao.updateInvesfundByCode("BaseInvesfundInfo.updateInvesfundByCode", info);
			sysStoredao.callViewinvestment("SysStoredProcedure.callViewinvestment",new SysStoredProcedure(info.getBase_investment_code(), "upd", CommonUtil.findNoteTxtOfXML("Lable-currency"),userId, "", ""));
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("tranModifyInvesfund方法异常",e);
			throw e;
		}finally{
			gs_logger.info("tranModifyInvesfund方法结束");
		}
		return data;
	}


	/**
	 * 删除投资机构基金
	 * @param fundCode 基金code
	 * @param userId 用户code
	 * @author duwenjie
	 * @date 2015-11-30 
	 */
	@Override
	public int tranDeleteInvesfund(String investmentCode,String fundCode, String userId)
			throws Exception {
		gs_logger.info("tranDeleteInvesfund方法开始");
		int data=0;
		try {
			data=dao.deleteInvesfundByCode("BaseInvesfundInfo.deleteInvesfundByCode", fundCode);
			sysStoredao.callViewinvestment("SysStoredProcedure.callViewinvestment",new SysStoredProcedure(investmentCode, "upd", CommonUtil.findNoteTxtOfXML("Lable-currency"),userId, "", ""));
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("tranDeleteInvesfund方法异常",e);
			throw e;
		}finally{
			gs_logger.info("tranDeleteInvesfund方法结束");
		}
		return data;
	}


}
