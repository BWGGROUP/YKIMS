package cn.com.sims.dao.baseupdloginfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseupdloginfo.BaseUpdlogInfo;

/**
 * 
 * @author duwenjie
 *	2015-10-10
 */
public interface IBaseUpdlogInfoDao {
	
	/**
	 * 添加系统更新记录
	 * @param noteInfo
	 * @throws Exception
	 */
	public void insertUpdlogInfo(String str,BaseUpdlogInfo updlogInfo) throws Exception;

	
	/**
	 * 根据操作模块类型,code分页查询更新记录
	 * @param str
	 * @param Map base_updlog_type,base_ele_code
	 * @return
	 * @throws Exception
	 */
	public List<BaseUpdlogInfo> findPageUpdlogInfo(String str,Map<String , String> map) throws Exception;
	
	/**
	 * 根据操作模块类型,code分页查询更新记录总数
	 * @return
	 * @throws Exception
	 */
	public int findPageUpdlogCount(String str,Map<String, String> map) throws Exception;
	
	/**
	 * 条件查询用户机构操作足迹数量
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int findUpdByupdlogUserCount(String str,Map<String, Object> map) throws Exception;
	
	/**
	 * 日志会议足迹数量
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int findMeetingCountByupdlogUser(String str,Map<String, Object> map)throws Exception;
	
	/**
	 * 条件查询用户机构操作足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findOrgByupdlogUser(String str,Map<String, Object> map)throws Exception; 
	
	/**
	 * 查询用户交易足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findTradeByupdlogUser(String str,Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户操作公司足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findCompanyByupdlogUser(String str,Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户操作会议足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findMeetingByupdlogUser(String str,Map<String, Object> map)throws Exception;
	
	/**
	 * 删除日志信息
	 * @param str
	 * @param map（elecode，logtype）
	 * @throws Exception
	 */
	public void deleteUpdlogByElecode(String str,Map<String, String> map)throws Exception;
}
