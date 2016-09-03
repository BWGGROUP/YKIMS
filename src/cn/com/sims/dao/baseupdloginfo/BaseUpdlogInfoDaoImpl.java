package cn.com.sims.dao.baseupdloginfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.mail.handlers.message_rfc822;

import cn.com.sims.model.baseupdloginfo.BaseUpdlogInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2010-10-10
 */
@Service
public class BaseUpdlogInfoDaoImpl implements IBaseUpdlogInfoDao {

	@Resource
	private SqlMapClient sqlMapClient;
	
	
	@Override
	public void insertUpdlogInfo(String str, BaseUpdlogInfo updlogInfo)
			throws Exception {
		sqlMapClient.insert(str,updlogInfo);
	}


	@Override
	public List<BaseUpdlogInfo> findPageUpdlogInfo(String str,
			Map<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}


	@Override
	public int findPageUpdlogCount(String str,Map<String, String> map) throws Exception {
		return (Integer)sqlMapClient.queryForObject(str,map);
	}


	@Override
	public int findUpdByupdlogUserCount(String str, Map<String, Object> map)
			throws Exception {
		return (Integer)sqlMapClient.queryForObject(str,map);
	}


	@Override
	public List<Map<String, Object>> findOrgByupdlogUser(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public List<Map<String, Object>> findTradeByupdlogUser(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public List<Map<String, Object>> findCompanyByupdlogUser(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public List<Map<String, Object>> findMeetingByupdlogUser(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public void deleteUpdlogByElecode(String str, Map<String, String> map)
			throws Exception {
		sqlMapClient.delete(str, map);
	}


	@Override
	public int findMeetingCountByupdlogUser(String str, Map<String, Object> map)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, map);
	}

}
