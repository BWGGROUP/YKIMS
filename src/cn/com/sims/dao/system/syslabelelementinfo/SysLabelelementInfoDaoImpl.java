package cn.com.sims.dao.system.syslabelelementinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.syslabelelement.SysLabelelementInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author shbs-tp004 duwenjie
 *	2015-10-12
 */
@Service
public class SysLabelelementInfoDaoImpl implements ISysLabelelementInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public List<Map<String, String>> findDIC(String str,String labelCode) throws Exception {
		
		return sqlMapClient.queryForList(str, labelCode);
	}
	@Override
	public SysLabelelementInfo querylabelelementbyname(String str,Map<String, String> map)
			throws Exception {
	    return (SysLabelelementInfo)sqlMapClient.queryForObject(str,map);
	}
	
	@Override
	public String querymaxlabelelementbycode(String str,String labelcode)
			throws Exception {
	    return sqlMapClient.queryForObject(str,labelcode).toString();
	}
	@Override
	public void insertsyslabelementinfo(String str,SysLabelelementInfo syslabelelementinfo)
			throws Exception {
	    sqlMapClient.insert(str,syslabelelementinfo);
	}
	@Override
	public List<Map<String, String>> findAllCurrencyChild(String str) throws Exception {
		return sqlMapClient.queryForList(str);
	}
	@Override
	public void updatalable(String str, SysLabelelementInfo lable)
			throws Exception {
		sqlMapClient.update(str, lable);
		
	}
	@Override
	public int findLabelCountByLabelcode(String str, String labelcode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, labelcode);
	}
	@Override
	public SysLabelelementInfo findLabelNextLabel(String str,
			Map<String, String> map) throws Exception {
		return (SysLabelelementInfo) sqlMapClient.queryForObject(str, map);
	}
	@Override
	public int updateLabelIndex(String str, Map<String, Object> map)
			throws Exception {
		return sqlMapClient.update(str, map);
	}

}
