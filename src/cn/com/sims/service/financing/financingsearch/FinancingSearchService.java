package cn.com.sims.service.financing.financingsearch;

import java.util.List;
import java.util.Map;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;

public interface FinancingSearchService {
/**
 * @zzg
 * 查询融资计划
 * @date 2015-10-10
 */
	public List<BaseFinancplanInfo> financingsearch(String companycode,String  timestamp,Page page) throws Exception;
	/**
	 * @zzg
	 * 查询融资计划总页数
	 * @date 2015-10-10
	 */
	public int financingsearch_pagetotal(String companycode,String  timestamp) throws Exception;
	
	/**
	 * 查询用户创建融资计划数量
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public int findFincingCountByUsercode(String usercode) throws Exception;
	
	/**
	 * 查询用户创建融资计划
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findFincingByUsercode(Map<String, Object> map) throws Exception;
}
