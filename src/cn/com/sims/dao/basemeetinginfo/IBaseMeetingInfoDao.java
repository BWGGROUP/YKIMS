package cn.com.sims.dao.basemeetinginfo;

import cn.com.sims.model.basemeetinginfo.BaseMeetingInfo;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
public interface IBaseMeetingInfoDao {
	
	/**
	 * 添加会议信息(基础层)
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @param basemeetinginfo会议信息
	 * @return
	 * @throws Exception
	 */
	public void addmeetinginfo(String str,BaseMeetingInfo basemeetinginfo)throws Exception;
	
	/**
	 * 根据会议code删除会议信息
	 * @param str
	 * @param meetingcode
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-7
	 */
	public void deleteMeetingInfoByCode(String str,String meetingcode)throws Exception;
	
	/**
	 * 修改会议信息
	 * @param str
	 * @param info
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-9
	 */
	public int updateBaseMeetingInfo(String str,BaseMeetingInfo info)throws Exception;
	
	/**
	 * 根据会议code查询会议信息
	 * @param str
	 * @param meetingcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-9
	 */
	public BaseMeetingInfo findBaseMeetingInfo(String str,String meetingcode)throws Exception;
}
