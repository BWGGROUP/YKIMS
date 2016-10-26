package cn.com.sims.service.investment.investmentDetailInfoService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.dao.Investment.baseinvesnoteinfo.IBaseInvesnoteInfoDao;
import cn.com.sims.dao.Investment.investmentDetailInfo.IInvestmentDetailInfoDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;

/**
 * @author duwenjie
 * 2015-09-25
 */
@Service
public class InvestmentDetailInfoServiceImpl implements IInvestmentDetailInfoService {
	@Resource
	IInvestmentDetailInfoDao dao;
	
	@Resource
	IBaseInvestmentInfoDao basedao;
	
	@Resource
	IBaseInvesnoteInfoDao noteDao;
	
	private static final Logger gs_logger = Logger.getLogger(InvestmentDetailInfoServiceImpl.class);
	
	
	/**
	 * 根据ID查询投资机构详情(业务层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-24
	 */
	@Override
	public viewInvestmentInfo findInvestementDetailInfoByID(String id)
			throws Exception {
		gs_logger.info("FindInvestementDetailInfoByID方法开始");
		viewInvestmentInfo info=null;
		try {
			info= dao.findInvestmentDetailById("viewInvestmentInfo.findInvestmentById", id);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("FindInvestementDetailInfoByID方法异常",e);
			throw e;
		}
		gs_logger.info("FindInvestementDetailInfoByID方法结束");
		return info;
	}


	/**
	 * 根据ID查询投资机构note(基础层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-28
	 */
	@Override
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String id)
			throws Exception {
		gs_logger.info("findInvesnoteByOrgId方法开始");
		List<BaseInvesnoteInfo> list = null;
		try {
			list= noteDao.findInvesnoteByOrgId("BaseInvesnoteInfo.findInvesnoteByOrgId", id);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvesnoteByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findInvesnoteByOrgId方法结束");
		return list;
	}


	/**
	 * 修改投资机构删除标识
	 * @param basemap（orgcode:机构code，del:删除标识（0：正常，1：删除），lock:排他锁版本号）
	 * @param map（orgcode:机构code，del:删除标识（0：正常，1：删除））
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-4-20
	 */
	@Override
	public int tranModifyUpdateOrgDelete(Map<String, Object> basemap,Map<String, Object> map) throws Exception {
		int data=0;
		gs_logger.info("tranModifyUpdateOrgDelete方法开始");
		try {
			data=basedao.updateBaseOrgDelete("baseInvestmentInfo.updateBaseOrgDelete", basemap);
			if(data>0){
				data=dao.updateViewOrgDel("viewInvestmentInfo.updateViewOrgDel", map);
			}
		} catch (Exception e) {
			gs_logger.error("tranModifyUpdateOrgDelete 方法异常",e);
			throw e;
		}
		gs_logger.info("tranModifyUpdateOrgDelete方法结束");
		return data;
	}

	
}