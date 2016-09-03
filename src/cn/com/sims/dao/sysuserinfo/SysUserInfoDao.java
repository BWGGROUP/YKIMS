package cn.com.sims.dao.sysuserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.sysuserinfo.SysUserInfo;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
public interface SysUserInfoDao {
	/**通过邮箱查询用户
	 * @author zzg
	 */
	public List<SysUserInfo> sysuserbyemail(String str, String email) throws Exception;
	/**
	 * 查询所有用户信息
	 * @return 用户code,用户名称 集合
	 * @throws Exception
	 */
	public List<Map<String, String>> findAllUserInfo(String str) throws Exception;
	/**通过姓名查询用户（模糊匹配）
	 * @author zzg
	 */
	public List<SysUserInfo> sysuserbyname(String str,String name) throws Exception;
	/**通过微信授权userid 查询用户
	 * @author zzg
	 */
	public List<SysUserInfo> weixin_userid(String str,String id) throws Exception;
	/**系统用户微信绑定
	 * @author zzg
	 */
	public int bindweichat(String str,SysUserInfo u)throws Exception;
	/** 查询属于该筐的用户
	 * @author zzg
	 */
	public List<SysUserInfo> userbyKcode(String str,HashMap<String,Object> map) throws Exception;
	/** 查询不属于该筐的用户
	 * @author zzg
	 */
	public List<SysUserInfo> usernoinKcode(String str,HashMap<String,Object> map) throws Exception;
	
	public SysUserInfo findUserInfo(String str,String code) throws Exception;
	/**添加筐时，查询所有员工 （不包含admin用户）
	 * @author zzg
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public List<SysUserInfo> addwad_alluser(String str,String admin) throws Exception ;
	public void changeuserinfo(String str,SysUserInfo userinfo) throws Exception ;
	
	
	/**
	 * 查询全部易凯用户
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> querysysuserinfo(String str) throws Exception;
	public List<SysUserInfo> userbyname(String str,HashMap<String, Object> map)
			throws Exception ;
	public int userbyname_num(String str,HashMap<String, Object> map) throws Exception ;
	public void adduser(String str,SysUserInfo userInfo) throws Exception ;
	
	/**
	 * 根据筐code查询用户信息
	 * @param str
	 * @param wadcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-22
	 */
	public List<SysUserInfo> findUserInfoByBaskCode(String str,String wadcode)throws Exception;
	
}
