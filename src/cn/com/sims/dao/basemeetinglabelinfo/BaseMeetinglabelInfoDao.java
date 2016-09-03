package cn.com.sims.dao.basemeetinglabelinfo;

import java.util.Map;

import cn.com.sims.model.basemeetinglabelinfo.BaseMeetingLabelInfo;

public interface BaseMeetinglabelInfoDao {
	/**
	 * 添加会议标签信息
	 * @param str
	 * @param info
	 * @throws Exception
	 */
	public void insertBaseMeetingLabelInfo(String str,BaseMeetingLabelInfo info)throws Exception;

	/**
	 * 删除会议标签信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteBaseMeetingLabelInfo(String str,Map<String, String> map)throws Exception;
}
