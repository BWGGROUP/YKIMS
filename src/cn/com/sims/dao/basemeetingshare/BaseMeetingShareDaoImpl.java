package cn.com.sims.dao.basemeetingshare;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basemeetingshare.BaseMeetingShare;

/**
 * 
 * @author rqq
 * @date 2015-11-09 
 */

@Service
public class BaseMeetingShareDaoImpl implements IBaseMeetingShareDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void addmeetingshare(String str,
		BaseMeetingShare basemeetingshare) throws Exception {
	    sqlMapClient.insert(str, basemeetingshare);
	    
	}

	@Override
	public List<Map<String, String>> findUserShareByMeetingCode(String str, String code)
			throws Exception {
		return sqlMapClient.queryForList(str,code);
	}

	@Override
	public void deleteShare(String str, Map<String, String> map)
			throws Exception {
		sqlMapClient.delete(str, map);
	}

	@Override
	public void deleteShareByMeetingCode(String str, String meetingcode)
			throws Exception {
		sqlMapClient.delete(str, meetingcode);
		
	}


	
	
}
