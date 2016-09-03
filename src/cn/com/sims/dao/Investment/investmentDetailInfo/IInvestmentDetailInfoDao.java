package cn.com.sims.dao.Investment.investmentDetailInfo;

import java.util.Map;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;

public interface IInvestmentDetailInfoDao {
	
	/**
	 * 根据ID查询投资机构详情
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	public viewInvestmentInfo findInvestmentDetailById(String str,String id) throws Exception;
	
	/**
	 * 修改投资机构删除标识
	 * @param str
	 * @param map（orgcode:机构code，del:删除标识（0：正常，1：删除））
	 * @return
	 * @throws Exception
	 */
	public int updateViewOrgDel(String str,Map<String, Object> map)throws Exception;
	
}