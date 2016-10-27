package cn.com.sims.service.investment.investmentDetailInfoService;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
public interface IInvestmentDetailInfoService {
	
	/**
	 * 根据投资机构ID查询投资机构详情(业务层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-24
	 */
	public viewInvestmentInfo findInvestementDetailInfoByID(String id) throws Exception;
	
	/**
	 * 根据投资机构ID查询投资机构note(基础层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-24
	 */
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String id) throws Exception;
	
	/**
	 * 修改投资机构删除标识
	 * @param basemap（orgcode:机构code，del:删除标识（0：正常，1：删除），lock:排他锁版本号）
	 * @param map（orgcode:机构code，del:删除标识（0：正常，1：删除））
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-4-20
	 */
	public int tranModifyUpdateOrgDelete(Map<String, Object> basemap,Map<String, Object> map)throws Exception;
}