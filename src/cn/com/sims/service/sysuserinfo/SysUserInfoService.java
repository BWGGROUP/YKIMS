package cn.com.sims.service.sysuserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.sysuserinfo.SysUserInfo;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
public interface SysUserInfoService {
	/**
	 * 查询所有用户信息
	 * @return 用户code,用户名称 集合
	 * @throws Exception
	 */
	public List<Map<String, String>> findAllUserInfo() throws Exception;
	/**通过邮箱查询用户
	 * @author zzg
	 */
	public List<SysUserInfo> sysuserbyemail(String email) throws Exception;
	/**通过姓名查询用户（模糊匹配）
	 * @author zzg
	 */
	public List<SysUserInfo> sysuserbyname(String name) throws Exception;
	/**通过微信授权userid 查询用户
	 * @author zzg
	 */
	public List<SysUserInfo> weixin_userid(String id) throws Exception;
	/**系统用户微信绑定
	 * @author zzg
	 */
	public int bindweichat(SysUserInfo u)throws Exception;
	/** 查询属于该筐的用户
	 * @author zzg
	 */
	public List<SysUserInfo> userbyKcode(HashMap<String,Object> map) throws Exception;
	/** 查询不属于该筐的用户
	 * @author zzg
	 */
	public List<SysUserInfo> usernoinKcode(HashMap<String,Object> map) throws Exception;
	/**添加筐时，查询所有员工 （不包含admin用户）
	 * @author zzg
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public List<SysUserInfo> addwad_alluser(String admin) throws Exception;
	/**
	 * 通过用户id查询用户
	 * @return
	 * @throws Exception
	 */
	public SysUserInfo sysuserbycode(String code)throws Exception;
	
	public Boolean changeuserinfo(SysUserInfo userinfo) throws Exception;
	/**通过姓名查询用户（模糊匹配 不包含admin）
	 * @author zzg
	 */
	public List<SysUserInfo> userbyname(HashMap<String, Object> map) throws Exception;
	public int userbyname_num(HashMap<String, Object> map) throws Exception;
	/**
	 * 删除用户
	 * @author zzg
	 * @date 2015-11-11
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Boolean tranDeleteuser(SysUserInfo userInfo) throws Exception;
	/**
	 * 添加用户
	 * @author zzg
	 * @date 2015-11-11
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public Boolean adduser(SysUserInfo userInfo)throws Exception;
	
}
