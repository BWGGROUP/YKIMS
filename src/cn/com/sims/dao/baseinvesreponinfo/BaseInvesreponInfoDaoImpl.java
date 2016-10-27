package cn.com.sims.dao.baseinvesreponinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author duwenjie
 *	@date 2015-10-12
 */

@Service
public class BaseInvesreponInfoDaoImpl implements IBaseInvesreponInfoDao {

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<Map<String, String>> findYKLinkManByOrgCode(String str,
			String orgCode) throws Exception {
		return sqlMapClient.queryForList(str, orgCode);
	}

	@Override
	public void insertYKLinkMan(String str, BaseInvesreponInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	@Override
	public int tranModifyYKLinkMan(String str, BaseInvesreponInfo info)
			throws Exception {
		return sqlMapClient.update(str, info);
	}
	
}
