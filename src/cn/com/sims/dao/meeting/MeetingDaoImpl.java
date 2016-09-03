package cn.com.sims.dao.meeting;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basemeetingnoteinfo.BaseMeetingNoteInfo;
import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;

/**
 * 
 * @author zzg
 *@date 20155-10-14
 */
@Service
public class MeetingDaoImpl implements  MeetingDao{
	@Resource
	private SqlMapClient sqlMapClient;
	/**根据相关投资机构 公司 记录人 筛选会议列表
	 * @author zzg
	 * @date 20155-10-14
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ViewMeetingRele> screenlist(String str,
			HashMap<String, Object> map) throws Exception {
	
		return sqlMapClient.queryForList(str,map);
	}
	@Override
	public int screenlist_num(String str, HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.queryForObject(str,map);
	}
	@Override
	public ViewMeetingRele viewmeetingreleBycode(String str,String id) throws Exception {
		
		return (ViewMeetingRele)sqlMapClient.queryForObject(str,id);
	}
	@Override
	public List<BaseMeetingNoteInfo> basemeetingnoteBymeetcode(String str,
			String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,id);
	}
	@Override
	public int meetingnote_del(String str, String id) throws Exception {
		
		return sqlMapClient.delete(str, id);
	}
	@Override
	public void meetingnote_add(String str, BaseMeetingNoteInfo b)
			throws Exception {
		
		  sqlMapClient.insert(str, b);
	}

}
