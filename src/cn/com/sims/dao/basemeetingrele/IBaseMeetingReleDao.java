package cn.com.sims.dao.basemeetingrele;

import java.util.Map;

import cn.com.sims.model.basemeetingrele.BaseMeetingRele;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
public interface IBaseMeetingReleDao {
	
	/**
	 * 添加会议相关机构公司投资人信息(基础层)
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @param basemeetingrele　议相关机构公司投资人信息
	 * @return
	 * @throws Exception
	 */
	public void addmeetingrele(String str,BaseMeetingRele basemeetingrele)throws Exception;
	
	/**
	 * 根据会议code删除会议相关机构公司信息
	 * @param str
	 * @param meetingcode
	 * @throws Exception
	 */
	public void deleteMeetingReleByCode(String str,String meetingcode)throws Exception;
	
	/**
	 * 删除指定会议相关机构/公司
	 * @param str
	 * @param map （meetingcode：会议code，reletype：相关类型（1：机构，2：公司），relecode：相关code）
	 * @throws Exception
	 */
	public int deleteMeetingReleByType(String str,Map<String, String> map)throws Exception;
}
