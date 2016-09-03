package cn.com.sims.dao.financing.financingsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
@Service
public class FinancingSearchDaoImpl implements FinancingSearchDao{
	@Resource
	private SqlMapClient sqlMapClient;
	@Override
	public List<BaseFinancplanInfo> financingsearch(String str,
			HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, map);
	}

	@Override
	public int financingsearch_pagetotal(String str, HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return Integer.parseInt(sqlMapClient.queryForObject(str, map).toString());
	}

	@Override
	public List findFinancing(String str, String time) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, time);
	}

	@Override
	public int tranModifyFinlan(String str,
			BaseFinancplanInfo baseFinancplanInfo) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.update(str, baseFinancplanInfo);
	}

	@Override
	public int updateFinlanemail(String str,
		BaseFinancplanEmail basefinancplanemail) throws Exception {
	    // TODO Auto-generated method stub
	    return sqlMapClient.update(str, basefinancplanemail);
	}

	@Override
	public int findFincingCountByUsercode(String str, String usercode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, usercode);
	}

	@Override
	public List<Map<String, Object>> findFincingByUsercode(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}

}
