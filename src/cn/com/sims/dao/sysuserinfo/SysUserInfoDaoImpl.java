package cn.com.sims.dao.sysuserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.sysuserinfo.SysUserInfo;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Service
public class SysUserInfoDaoImpl implements SysUserInfoDao{
	@Resource
	private SqlMapClient sqlMapClient;
	/**通过邮箱查询用户
	 * @author zzg
	 */
	@Override
	public List<SysUserInfo> sysuserbyemail(String str, String email)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,email);
	}

	
	@Override
	public List<Map<String, String>> findAllUserInfo(String str) throws Exception {
		return sqlMapClient.queryForList(str);
	}


	@Override
	public List<SysUserInfo> sysuserbyname(String str, String name)
			throws Exception {
		
		return sqlMapClient.queryForList(str,name);
	}


	@Override
	public List<SysUserInfo> weixin_userid(String str, String id)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,id);
	}


	@Override
	public int bindweichat(String str, SysUserInfo u) throws Exception {
		
		return (int) sqlMapClient.update(str,u);
	}


	@Override
	public List<SysUserInfo> userbyKcode(String str, HashMap<String,Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public List<SysUserInfo> usernoinKcode(String str, HashMap<String,Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,map);
	}

	@Override
	public SysUserInfo findUserInfo(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		return (SysUserInfo)sqlMapClient.queryForObject(str, code);
	}


	@Override
	public List<SysUserInfo> addwad_alluser(String str, String admin) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,admin);
	}


	@Override
	public void changeuserinfo(String str,SysUserInfo userinfo) throws Exception {
	
		sqlMapClient.update(str,userinfo);
	}


	@Override
	public List<Map<String,String>> querysysuserinfo(String str) throws Exception {
		return sqlMapClient.queryForList(str);
	}


	@Override
	public List<SysUserInfo> userbyname(String str, HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,map);
	}


	@Override
	public int userbyname_num(String str, HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject(str, map);
	}


	@Override
	public void adduser(String str, SysUserInfo userInfo) throws Exception {
		sqlMapClient.insert(str, userInfo);
	}


	@Override
	public List<SysUserInfo> findUserInfoByBaskCode(String str, String wadcode)
			throws Exception {
		return sqlMapClient.queryForList(str,wadcode);
	}

}
