package cn.com.sims.dao.viewmeetingrele;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author duwenjie
 * @date 2016-3-7
 *
 */
@Service
public class ViewMeetingReleDaoService implements IViewMeetingReleDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	
	@Override
	public void deleteMeetingReleByCode(String str, String meetingcode)
			throws Exception {
		sqlMapClient.delete(str, meetingcode);
		
	}


	@Override
	public String screenloginInfo(String str, Map<String, Object> map)
			throws Exception {
		return (String) sqlMapClient.queryForObject(str, map);
	}


	@Override
	public int findJoinMeetingCountByUsercode(String str, String usercode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str,usercode);
	}


	@Override
	public List<Map<String, Object>> findJoinMeetingByUsercode(String str,
			Map<String, Object> map) throws Exception {
		return sqlMapClient.queryForList(str,map);
	}

}
