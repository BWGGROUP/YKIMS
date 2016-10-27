package cn.com.sims.dao.basemeetingparp;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.basemeetingparp.BaseMeetingParp;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
public interface IBaseMeetingParpDao {
	
	/**
	 * 添加会议与会者信息(基础层)
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @param basemeetingparp　会议与会者信息
	 * @return
	 * @throws Exception
	 */
	public void addmeetingparp(String str,BaseMeetingParp basemeetingparp)throws Exception;
	
	
	/**
	 * 根据会议code删除会议与会者信息
	 * @param str
	 * @param meetingcode
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-7
	 */
	public void deleteMeetingParpByCode(String str,String meetingcode)throws Exception;
	
	/**
	 * 根据会议code，用户code删除会议参会人
	 * @param str
	 * @param map (会议code：meetingcode，用户code：usercode)
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-10
	 */
	public void deleteMeetingParpByUser(String str,Map<String, String> map)throws Exception;
	
	/**
	 * 查询会议与会人
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-14
	 */
	public List<BaseMeetingParp> findMeetingUserInfo(String str,String meetingcode)throws Exception;
}
