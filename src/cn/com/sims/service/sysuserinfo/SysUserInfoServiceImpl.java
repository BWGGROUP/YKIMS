package cn.com.sims.service.sysuserinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.system.sysuserwad.SysUserWadDao;
import cn.com.sims.dao.sysuserinfo.SysUserInfoDao;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Service
public class SysUserInfoServiceImpl implements SysUserInfoService{
	private static final Logger gs_logger = Logger
			.getLogger(SysUserInfoServiceImpl.class);
	@Resource
	SysUserInfoDao SysUserInfoDao;
	@Resource
	SysUserWadDao SysUserWadDao;
	/**通过邮箱查询用户
	 * @author zzg
	 */
	@Override
	public List<SysUserInfo> sysuserbyemail(String email) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl login方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.sysuserbyemail("SysUserInfo.sysuserbyemail", email);
			gs_logger.info("SysUserInfoServiceImpl login方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl login方法异常",e);
			throw e;
		}
		
		return list;
	}
	@Override
	public List<Map<String, String>> findAllUserInfo() throws Exception {
		gs_logger.info("SysUserInfoServiceImpl findAllUserInfo方法开始");
		List<Map<String, String>> list = null;
		try {
			list=SysUserInfoDao.findAllUserInfo("SysUserInfo.findAllUserInfo");
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error(" SysUserInfoServiceImpl findAllUserInfo方法异常",e);
			throw e;
		}finally{
			gs_logger.info(" SysUserInfoServiceImpl findAllUserInfo方法结束");
		}
		return list;
	}
	@Override
	public List<SysUserInfo> sysuserbyname(String name) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl sysuserbyname方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.sysuserbyname("SysUserInfo.sysuserbyname", name);
			gs_logger.info("SysUserInfoServiceImpl sysuserbyname方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl sysuserbyname方法异常",e);
		throw e;
		}
		return list;
	}
	@Override
	public List<SysUserInfo> weixin_userid(String id) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl weixin_userid方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.weixin_userid("SysUserInfo.weixin_userid", id);
			gs_logger.info("SysUserInfoServiceImpl weixin_userid方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl weixin_userid方法异常",e);
			throw e;
		}
		return list;
	}
	@Override
	public int bindweichat(SysUserInfo u) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl bindweichat方法开始");
		int i=0;
		try {
			i=SysUserInfoDao.bindweichat("SysUserInfo.bindweichat", u);
			gs_logger.info("SysUserInfoServiceImpl bindweichat方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl bindweichat方法异常",e);
			throw e;
		}
		return i;
	}
	@Override
	public List<SysUserInfo> userbyKcode(HashMap<String,Object> map) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl userbyKcode方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.userbyKcode("SysUserInfo.userbyKcode", map);
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl userbyKcode方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl userbyKcode方法结束");
		return list;
	}
	@Override
	public List<SysUserInfo> usernoinKcode(HashMap<String,Object> map) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl usernoinKcode方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.userbyKcode("SysUserInfo.usernoinKcode", map);
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl usernoinKcode方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl usernoinKcode方法结束");
		return list;
	}
	@Override
	public List<SysUserInfo> addwad_alluser(String admin) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl addwad_alluser方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.addwad_alluser("SysUserInfo.addwad_alluser", admin);
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl addwad_alluser方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl addwad_alluser方法结束");
		return list;
	}
	@Override
	public SysUserInfo sysuserbycode(String code) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl sysuserbycode方法开始");
		SysUserInfo userInfo=null;
		try {
			userInfo=SysUserInfoDao.findUserInfo("SysUserInfo.findUserInfo",code);
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl sysuserbycode方法异常",e);
		throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl sysuserbycode方法结束");
		return userInfo;
	}
	@Override
	public Boolean changeuserinfo(SysUserInfo userinfo) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl changeuserinfo方法开始");
		Boolean suBoolean=false;
		try {
			SysUserInfoDao.changeuserinfo("SysUserInfo.changeuserinfo", userinfo);
			suBoolean=true;
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl changeuserinfo方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl changeuserinfo方法结束");
		return suBoolean;
	}
	@Override
	public List<SysUserInfo> userbyname(HashMap<String, Object> map)
			throws Exception {
		gs_logger.info("SysUserInfoServiceImpl userbyname方法开始");
		List<SysUserInfo> list=null;
		try {
			list=SysUserInfoDao.userbyname("SysUserInfo.userbyname", map);
			gs_logger.info("SysUserInfoServiceImpl userbyname方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl userbyname方法异常",e);
		throw e;
		}
		return list;

	}
	@Override
	public int userbyname_num(HashMap<String, Object> map) throws Exception {
		int i=0;
		gs_logger.info("SysUserInfoServiceImpl userbyname_num方法开始");
		try {
			i=SysUserInfoDao.userbyname_num("SysUserInfo.userbyname_num", map);
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl userbyname_num方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl userbyname_num方法结束");
		return i;
	}
	@Override
	public Boolean tranDeleteuser(SysUserInfo userInfo) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl tranDeleteuser方法开始");
		Boolean suBoolean=false;
		try {
			SysUserInfoDao.changeuserinfo("SysUserInfo.changeuserinfo", userInfo);
			SysUserWadDao.Deleteuserwadbyusercode("SysUserWad.deletuserWadbyusercode", userInfo.getSys_user_code());//删除用户与筐的关系
			suBoolean=true;
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl tranDeleteuser方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl tranDeleteuser方法结束");
		return suBoolean;
	}
	@Override
	public Boolean adduser(SysUserInfo userInfo) throws Exception {
		gs_logger.info("SysUserInfoServiceImpl adduser方法开始");
		Boolean suBoolean=false;
		try {
			SysUserInfoDao.adduser("SysUserInfo.adduser",userInfo);
			suBoolean=true;
		} catch (Exception e) {
			gs_logger.error("SysUserInfoServiceImpl adduser方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserInfoServiceImpl adduser方法结束");
		return suBoolean;
	}
}
