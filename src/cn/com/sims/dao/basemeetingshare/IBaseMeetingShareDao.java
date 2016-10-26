package cn.com.sims.dao.basemeetingshare;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.basemeetingshare.BaseMeetingShare;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
public interface IBaseMeetingShareDao {
	
	/**
	 * 添加会议分享范围信息(基础层)
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @param basemeetingshare会议分享范围信息
	 * @return
	 * @throws Exception
	 */
	public void addmeetingshare(String str,BaseMeetingShare basemeetingshare)throws Exception;
	
	/**
	 * 根据会议code查询用户分享范围
	 * @param str
	 * @param code
	 * @return
	 * @throws Exception
	 * @author duwnejie
	 * @date 2016-3-4
	 */
	public List<Map<String, String>> findUserShareByMeetingCode(String str,String code)throws Exception;
	
	/**
	 * 根据会议code,筐code删除分享范围
	 * @param str
	 * @param map (meetcode:会议code，baskcode：筐code)
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-4
	 * 
	 */
	public void deleteShare(String str,Map<String, String> map)throws Exception;
	
	/**
	 * 根据会议code删除分享范围
	 * @param str
	 * @param meetingcode
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-7
	 * 
	 */
	public void deleteShareByMeetingCode(String str,String meetingcode)throws Exception;
}
