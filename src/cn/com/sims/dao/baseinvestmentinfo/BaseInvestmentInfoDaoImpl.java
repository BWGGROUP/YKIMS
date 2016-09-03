package cn.com.sims.dao.baseinvestmentinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-14
 */

@Service
public class BaseInvestmentInfoDaoImpl implements IBaseInvestmentInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public int tranModifyBaseInvestment(String str, baseInvestmentInfo info)
			throws Exception {
		return sqlMapClient.update(str, info);
	}

	@Override
	public baseInvestmentInfo findBaseInvestmentByCode(String str,
			String orgCode) throws Exception {
		return (baseInvestmentInfo) sqlMapClient.queryForObject(str, orgCode);
	}
	
	@Override
	public baseInvestmentInfo findBaseInvestmentBynameForIT(String str,
			String baseinvestmentname) throws Exception {
		return (baseInvestmentInfo) sqlMapClient.queryForObject(str, baseinvestmentname);
	}
	
	@Override
	public baseInvestmentInfo findBaseInvestmentBytmpcodeForIT(String str,
			String tmpinvestmentcode) throws Exception {
		return (baseInvestmentInfo) sqlMapClient.queryForObject(str, tmpinvestmentcode);
	}
	
	@Override
	public void updateBaseInvestmentforIT(String str,
		baseInvestmentInfo baseinvestmentinfo) throws Exception {
		sqlMapClient.update(str, baseinvestmentinfo);
	}
	
	@Override
	public void insertBaseInvestmentforIT(String str,
		baseInvestmentInfo baseinvestmentinfo) throws Exception {
		 sqlMapClient.insert(str, baseinvestmentinfo);
	}
	@Override
	public int findInvestmentOnlyName(String str, baseInvestmentInfo info) throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, info);
	}

	@Override
	public int findInvestmentOnlyFullName(String str, baseInvestmentInfo info)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, info);
	}

	@Override
	public int findInvestmentOnlyEName(String str, baseInvestmentInfo info)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, info);
	}

	/**
	 * 查询所有投资机构信息,下拉筐选择使用
	 * @author duwenjie
	 */
	@Override
	public List<Map<String, String>> findAllBaseInvestment(String str)
			throws Exception {
		return sqlMapClient.queryForList(str);
	}

	@Override
	public int queryInvestmentOnlyNamefottrade(String str,
		String name) throws Exception {
	    return (Integer) sqlMapClient.queryForObject(str, name);
	}
	/**
	 * 添加投资机构信息
	 * @param info投资机构对象
	 * @author duwenjie
	 * @date 2015-11-4
	 */
	@Override
	public void insertBaseInvestmentInfo(String str, baseInvestmentInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	/**
	 * 修改投资机构删除标识
	 * @param str
	 * @param map（orgcode:机构code,del:删除标识，lock:排他锁版本号）
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateBaseOrgDelete(String str, Map<String, Object> map)
			throws Exception {
		return sqlMapClient.update(str, map);
	}

	
}
