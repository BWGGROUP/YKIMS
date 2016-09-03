package cn.com.sims.dao.Investment.baseinvesnoteinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2015-10-09
 */
@Service
public class BaseInvesnoteInfoDaoImpl implements IBaseInvesnoteInfoDao {

	@Resource
	private SqlMapClient sqlMapClient;
	
	
	@Override
	public void insertOrgNote(String str,BaseInvesnoteInfo noteInfo) throws Exception {
		sqlMapClient.insert(str, noteInfo);
	}
	
	/**
	 * 根据投资机构ID查询投资机构note
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String str, String id)
			throws Exception {
		return sqlMapClient.queryForList(str,id);
	}

	@Override
	public int findCountByOrgId(String str, String orgCode) throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, orgCode);
	}

	@Override
	public List<BaseInvesnoteInfo> findPageInvesnoteByOrgId(String str,
			Map<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}

	@Override
	public int tranDeleteInvestmentNote(String str, String noteCode)
			throws Exception {
		return sqlMapClient.delete(str, noteCode);
		
	}

}
