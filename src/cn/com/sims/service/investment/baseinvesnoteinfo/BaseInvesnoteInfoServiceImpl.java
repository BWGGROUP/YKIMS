package cn.com.sims.service.investment.baseinvesnoteinfo;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.Investment.baseinvesnoteinfo.IBaseInvesnoteInfoDao;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2015-10-09
 */
@Service
public class BaseInvesnoteInfoServiceImpl implements IBaseInvesnoteInfoService {

	@Resource
	IBaseInvesnoteInfoDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(BaseInvesnoteInfoServiceImpl.class);
	
	@Override
	public void insertOrgNote(BaseInvesnoteInfo noteInfo) throws Exception {
		gs_logger.info("addInvesnoteInfo方法开始");
		try {
			 dao.insertOrgNote("BaseInvesnoteInfo.insertOrgNote", noteInfo);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("addInvesnoteInfo方法异常",e);
			throw e;
		}finally{
			gs_logger.info("addInvesnoteInfo方法结束");
		}
	}
	
	/**
	 * 根据ID查询投资机构note(基础层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-28
	 */
	@Override
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String orgCode)
			throws Exception {
		gs_logger.info("findInvesnoteByOrgId方法开始");
		List<BaseInvesnoteInfo> list = null;
		try {
			list= dao.findInvesnoteByOrgId("BaseInvesnoteInfo.findInvesnoteByOrgId", orgCode);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvesnoteByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findInvesnoteByOrgId方法结束");
		return list;
	}

	@Override
	public int findCountByOrgId(String orgCode) throws Exception {
		gs_logger.info("findCountByOrgId方法开始");
		int data=0;
		try {
			data= dao.findCountByOrgId("BaseInvesnoteInfo.findCountByOrgId", orgCode);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findCountByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findCountByOrgId方法结束");
		return data;
	}

	@Override
	public List<BaseInvesnoteInfo> findPageInvesnoteByOrgId(
			String orgCode,Page page) throws Exception {
		gs_logger.info(" findPageInvesnoteByOrgId方法开始");
		List<BaseInvesnoteInfo> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map.put("pageSize", page.getPageSize()+"");
			map.put("startCount", page.getStartCount()+"");
			map.put("orgCode", orgCode);
			list= dao.findPageInvesnoteByOrgId("BaseInvesnoteInfo.findPageInvesnoteInfo", map); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findPageInvesnoteByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findPageInvesnoteByOrgId方法结束");
		return list;
	}

	@Override
	public int tranDeleteInvestmentNote(String noteCode) throws Exception {
		gs_logger.info("tranDeleteInvestmentNote方法结束");
		int data=0;
		try {
			data=dao.tranDeleteInvestmentNote("BaseInvesnoteInfo.tranDeleteInvestmentNote", noteCode);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findPageInvesnoteByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findPageInvesnoteByOrgId方法结束");
		
		return data;
	}

}
