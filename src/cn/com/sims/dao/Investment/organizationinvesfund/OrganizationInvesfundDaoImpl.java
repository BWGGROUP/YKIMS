package cn.com.sims.dao.Investment.organizationinvesfund;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
@Service
public class OrganizationInvesfundDaoImpl implements IOrganizationInvesfundDao {

	@Resource
	private SqlMapClient sqlMapClient;
	
	
	@Override
	public int findCountTfundByOrgId(String str, String orgCode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, orgCode);
	}
	
	/**
	 * 根据投资机构Ｉｄ查询基金信息
	 * @param str
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BaseInvesfundInfo> findInvesfundByOrganizationId(String str,
			String id) throws Exception {
		return sqlMapClient.queryForList(str, id);
	}

	@Override
	public List<BaseInvesfundInfo> findPageInvesfundByOrgId(String str,
			Map<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}
	
	/**
	 * 添加投资机构基金信息
	 * @param str
	 * @param 新增投资机构基金对象
	 * @throws Exception
	 */
	@Override
	public void insertInvesfund(String str, BaseInvesfundInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	/**
	 * 修改投资机构基金
	 * @param str
	 * @param info 基金对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateInvesfundByCode(String str, BaseInvesfundInfo info)
			throws Exception {
		return sqlMapClient.update(str, info);
	}

	/**
	 * 删除投资机构基金
	 * @param str
	 * @param fundCode 基金code
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteInvesfundByCode(String str, String fundCode)
			throws Exception {
		return sqlMapClient.delete(str, fundCode);
	}


	
}
