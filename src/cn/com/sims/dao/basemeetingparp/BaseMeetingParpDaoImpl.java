package cn.com.sims.dao.basemeetingparp;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basemeetingparp.BaseMeetingParp;

/**
 * 
 * @author rqq
 * @date 2015-11-09 
 */

@Service
public class BaseMeetingParpDaoImpl implements IBaseMeetingParpDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void addmeetingparp(String str, BaseMeetingParp basemeetingparp)
		throws Exception {
	    sqlMapClient.insert(str, basemeetingparp);
	    
	}

	@Override
	public void deleteMeetingParpByCode(String str, String meetingcode)
			throws Exception {
		sqlMapClient.delete(str, meetingcode);
		
	}

	/**
	 * 根据会议code，用户code删除会议参会人
	 * @param str
	 * @param map
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-10
	 */
	@Override
	public void deleteMeetingParpByUser(String str, Map<String, String> map)
			throws Exception {
		sqlMapClient.delete(str, map);
	}

	@Override
	public List<BaseMeetingParp> findMeetingUserInfo(String str,
			String meetingcode) throws Exception {
		return sqlMapClient.queryForList(str, meetingcode);
	}

	


	
	
}
