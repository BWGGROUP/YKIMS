package cn.com.sims.dao.viewmeetingrele;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;

/**
 * 
 * @author duwenjie
 * @date 2016-3-7
 *
 */
public interface IViewMeetingReleDao {
	/**
	 * 根据会议code删除会议业务数据
	 * @param str
	 * @param meetingcode
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-7
	 */
	public void deleteMeetingReleByCode(String str,String meetingcode)throws Exception;
	
	/**
	 * 查询登录用户是否有权限查看会议
	 * @param str
	 * @param map
	 * @return 
	 * @throws Exception
	 */
	public String screenloginInfo(String str,Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户参与会议数量
	 * @return
	 * @throws Exception
	 */
	public int findJoinMeetingCountByUsercode(String str,String usercode)throws Exception;
	
	/**
	 * 查询用户参与会议信息
	 * @param str
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findJoinMeetingByUsercode(String str,Map<String, Object> map)throws Exception;
	
}
