package cn.com.sims.service.baseupdloginfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseupdloginfo.BaseUpdlogInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2015-10-10
 */
public interface IBaseUpdlogInfoService {

	/**
	 * 添加系统更新记录
	 * @param noteInfo
	 * @throws Exception
	 */
	public void insertUpdlogInfo(String	base_updlog_type,
			String	base_updlog_typename,
			String	base_ele_code,
			String	base_ele_name,
			String	base_updlog_opertype,
			String	sys_user_code,
			String	sys_user_name,
			String	base_updlog_opertypecode,
			String	base_updlog_opertypename,
			String	base_updlog_opercont,
			String	base_updlog_oridata,
			String	base_updlog_newdata,
			String	operaflag,
			String	createid,
			Timestamp createtime,
			String	updid,
			Timestamp updtime) throws Exception;
	
	
	/**
	 * 根据操作模块类型,code分页查询更新记录
	 * @param page
	 * @param type
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<BaseUpdlogInfo> findPageUpdlogInfo(Page page,String type,String code) throws Exception;

	/**
	 * 根据操作类型,code查询更新记录总数
	 * @param code
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int findPageUpdlogCount(String code,String type) throws Exception;
	
	/**
	 * 条件查询用户操作足迹数量
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public  int findUpdByupdlogUserCount(Map<String, Object> map) throws Exception;
	/**
	 * 条件查询用户操作交易足迹数量
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int findTradeCountByupdlogUser(Map<String, Object> map) throws Exception;
	/**
	 * 条件查询用户机构操作足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findOrgByupdlogUser(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户交易足迹信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findTradeByupdlogUser(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户操作公司足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findCompanyByupdlogUser(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户操作会议足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findMeetingByupdlogUser(Map<String, Object> map)throws Exception;
	
	/**
	 * 会议操作足迹信息数量
	 * @return
	 * @throws Exception
	 */
	public int findMeetingCountByupdlogUser(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户参与会议数量
	 * @return
	 * @throws Exception
	 */
	public int findJoinMeetingCountByUsercode(String usercode)throws Exception;
	
	/**
	 * 查询用户参与会议信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findJoinMeetingByUsercode(Map<String, Object> map)throws Exception;
	
	/**
	 * 查询用户创建融资计划数量
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public int findFincingCountByUsercode(String usercode) throws Exception;
	
	/**
	 * 查询用户创建融资计划
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findFincingByUsercode(Map<String, Object> map) throws Exception;
}
