package cn.com.sims.dao.Investment.organizationtrade;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.vieworganizationtrade.ViewOrganizationTrade;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
@Service
public class OrganizationTradeDaoImpl implements IOrganizationTradeDao{
	
	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<ViewOrganizationTrade> findTradeByOrganizationId(String str,
			String id) throws Exception {
		return (List<ViewOrganizationTrade>)sqlMapClient.queryForList(str, id);
	}

	@Override
	public List<ViewOrganizationTrade> findPageTradeByOrgId(String str,
			HashMap<String, String> map) throws Exception {
		return (List<ViewOrganizationTrade>)sqlMapClient.queryForList(str, map);
	}

	@Override
	public int findCountTradeByOrgId(String str, String orgCode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, orgCode);
	}
}