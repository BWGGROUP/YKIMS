package cn.com.sims.dao.baseentrepreneurinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;

/**
 * 
 * @author rqq
 * @date 2015-10-26
 */

@Service
public class BaseEntrepreneurInfoDaoImpl implements IBaseEntrepreneurInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public BaseEntrepreneurInfo findBaseEntrepreneurBytmpcodeForIT(String str,String tmpentrepreneurcode)
		throws Exception {
		return (BaseEntrepreneurInfo) sqlMapClient.queryForObject(str, tmpentrepreneurcode);
	}
	
	@Override
	public void updateBaseEntrepreneurforIT(String str,
		BaseEntrepreneurInfo baseentrepreneurinfo) throws Exception {
		sqlMapClient.update(str, baseentrepreneurinfo);
	}
	
	@Override
	public void insertBaseEntrepreneurforIT(String str,
		BaseEntrepreneurInfo baseentrepreneurinfo) throws Exception {
		 sqlMapClient.insert(str, baseentrepreneurinfo);
	}

	@Override
	public int queryEntrepreneurlistnumBycompId(String str,
		Map<String, Object> map) throws Exception {
	    return (Integer) sqlMapClient.queryForObject(str, map);
	}

	@Override
	public List<BaseEntrepreneurInfo> queryEntrepreneurlistBycompId(String str,
		Map<String, Object> map) throws Exception {
	    return sqlMapClient.queryForList(str,map);
	}
}
