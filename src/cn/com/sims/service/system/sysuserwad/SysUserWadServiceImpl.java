package cn.com.sims.service.system.sysuserwad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadinfo.SysWadInfo;
import cn.com.sims.model.syswadjuri.SysWadJuri;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.system.sysuserwad.SysUserWadDao;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Service
public class SysUserWadServiceImpl implements SysUserWadService{
	private static final Logger gs_logger = Logger
			.getLogger(SysUserWadServiceImpl.class);
@Resource
SysUserWadDao SysUserWadDao;
	@Override
	public List<SysUserWad> sysuserwadbyuserid(String id) throws Exception {
		gs_logger.info("SysUserWadServiceImpl sysuserwadbyuserid方法开始");
		List<SysUserWad>list =null;
		try {
			list=SysUserWadDao.sysuserwadbyuserid("SysUserWad.userwadbyuserid", id);
			gs_logger.info("SysUserWadServiceImpl sysuserwadbyuserid方法结束");
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl sysuserwadbyuserid方法异常",e);
			throw e;
		}
		
		return list;
	}
	@Override
	public List<SysWadJuri> wad_juri(String id) throws Exception {
		gs_logger.info("SysUserWadServiceImpl wad_juri方法开始");
		List<SysWadJuri> list=null;
		try {
			list=SysUserWadDao.wad_juri("SysWadJuri.wadcode", id);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl wad_juri方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl wad_juri方法结束");
		return list;
	}
	@Override
	public List<Map<String, Object>> wadbyname(HashMap<String, Object> map) throws Exception {
		gs_logger.info("SysUserWadServiceImpl wad_juri方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=SysUserWadDao.wadbyname("SysWadInfo.wadbyname", map);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl wadbyname方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl wadbyname方法结束");
		return list;
	}
	@Override
	public int wadbyname_num(HashMap<String, Object> map) throws Exception {
		gs_logger.info("SysUserWadServiceImpl wadbyname_num方法开始");
int i=0;
		try {
			i=SysUserWadDao.wadbyname_num("SysWadInfo.wadbyname_num", map);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl wadbyname_num方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl wadbyname_num方法结束");
		return i;
	}
	@Override
	public Boolean tranDeletewadbycode(String code) throws Exception {
		gs_logger.info("SysUserWadServiceImpl tranDeletewadbycode方法开始");
	Boolean su=false;
	try {
		SysUserWadDao.Deleteuserwadbycode("SysUserWad.deletuserWad", code);//删除用户与筐的关系 
		SysUserWadDao.Deletewadjuribycode("SysWadJuri.deletJuriwad", code);//删除权限与筐的关系
		SysUserWadDao.Deletewadbycode("SysWadInfo.deletwad", code);//删除筐
		su=true;
	} catch (Exception e) {
		gs_logger.error("SysUserWadServiceImpl tranDeletewadbycode方法异常",e);
		throw e;
	}
	gs_logger.info("SysUserWadServiceImpl tranDeletewadbycode方法结束");
		return su;
	}
	@Override
	public HashMap<String, Object> wadinfobycode(String code) throws Exception {
		gs_logger.info("SysUserWadServiceImpl wadinfobycode方法开始");
		HashMap<String, Object> map=null;
		try {
			map=SysUserWadDao.wadinfobycode("SysWadInfo.wadinfobycode", code);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl wadinfobycode方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl wadinfobycode方法结束");
		return map;
	}
	@Override
	public List<Map<String, String>> juList(String code) throws Exception {
		List<Map<String, String>> list=null;
		try {
			list=SysUserWadDao.juList("SysWadInfo.juList",code);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl juList方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl juList方法结束");
		return list;
	}
	@Override
	public Boolean tranDeletewadjuribycode(String code) throws Exception {
		gs_logger.info("SysUserWadServiceImpl tranDeletewadjuribycode方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.Deletewadjuribycode("SysWadJuri.deletJuriwad", code);//删除权限与筐的关系
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl tranDeletewadjuribycode方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl tranDeletewadjuribycode方法结束");
			return su;
	}
	@Override
	public Boolean setwad_juri(List<SysWadJuri> sysWadJuri) throws Exception {
		gs_logger.info("SysUserWadServiceImpl setwad_juri方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.setwad_juri("SysWadJuri.setwad_juri", sysWadJuri);//添加权限与筐的关系
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl setwad_juri方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl setwad_juri方法结束");
			return su;
	}
	@Override
	public Boolean tranDeleteuserwadbycode(String code) throws Exception {
		gs_logger.info("SysUserWadServiceImpl tranDeleteuserwadbycode方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.Deleteuserwadbycode("SysUserWad.deletuserWad", code);//删除用户与筐的关系 
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl tranDeleteuserwadbycode方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl tranDeleteuserwadbycode方法结束");
			return su;
	}
	@Override
	public Boolean setwad_user(List<SysUserWad> SysUserWad) throws Exception {
		gs_logger.info("SysUserWadServiceImpl setwad_juri方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.setwad_user("SysUserWad.setwad_user", SysUserWad);//添加用户与筐的关系
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl setwad_juri方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl setwad_juri方法结束");
			return su;
	}
	@Override
	public Boolean updatawadinfo(HashMap<String, Object> map) throws Exception {
		gs_logger.info("SysUserWadServiceImpl updatawadinfo方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.updatawadinfo("SysWadInfo.updatawadinfo", map);//添加权限与筐的关系
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl updatawadinfo方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl updatawadinfo方法结束");
			return su;
	}
	@Override
	public Boolean tranModifywadallinfo(HashMap<String, Object> map,
			List<SysWadJuri> sysWadJuri, List<SysUserWad> SysUserWad)
			throws Exception {
		gs_logger.info("SysUserWadServiceImpl tranModifywadallinfo方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.Deletewadjuribycode("SysWadJuri.deletJuriwad", map.get("code").toString());//删除权限与筐的关系
			SysUserWadDao.Deleteuserwadbycode("SysUserWad.deletuserWad", map.get("code").toString());//删除用户与筐的关系
			if(sysWadJuri.size()>0){
				SysUserWadDao.setwad_juri("SysWadJuri.setwad_juri", sysWadJuri);//添加权限与筐的关系
			}
		if(SysUserWad.size()>0){
			SysUserWadDao.setwad_user("SysUserWad.setwad_user", SysUserWad);//添加用户与筐的关系
			}
		if(!map.get("code").toString().equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))
				&&!map.get("code").toString().equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))){
			SysUserWadDao.updatawadinfo("SysWadInfo.updatawadinfo", map);//修改筐详情
		}
			
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl tranModifywadallinfo方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl tranModifywadallinfo方法结束");
	return su;
	}
	@Override
	public Boolean addwadinfo(SysWadInfo sysWadInfo) throws Exception {
		gs_logger.info("SysUserWadServiceImpl addwadinfo方法开始");
		Boolean su=false;
		try {
			SysUserWadDao.addwadinfo("SysWadInfo.addwadinfo", sysWadInfo);
			su=true;
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl addwadinfo方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl addwadinfo方法结束");
			return su;
	}
	@Override
	public Boolean tranSavewadinfo(SysWadInfo sysWadInfo,
			List<SysWadJuri> sysWadJuri, List<SysUserWad> SysUserWad)
			throws Exception {
		gs_logger.info("SysUserWadServiceImpl addwadinfo方法开始");
		Boolean su=false;
try {
	SysUserWadDao.addwadinfo("SysWadInfo.addwadinfo", sysWadInfo);
	if(sysWadJuri.size()>0){
		SysUserWadDao.setwad_juri("SysWadJuri.setwad_juri", sysWadJuri);//添加权限与筐的关系
	}
if(SysUserWad.size()>0){
	SysUserWadDao.setwad_user("SysUserWad.setwad_user", SysUserWad);//添加用户与筐的关系
	}
su=true;
} catch (Exception e) {
	gs_logger.error("SysUserWadServiceImpl addwadinfo方法异常",e);
throw e;
}
gs_logger.info("SysUserWadServiceImpl addwadinfo方法结束");
		return su;
	}
	@Override
	public List<SysWadInfo> wadlistbyname(String name) throws Exception {

		List<SysWadInfo> list=null;
		try {
			list=SysUserWadDao.wadlistbyname("SysWadInfo.wadlistbyname",name);
		} catch (Exception e) {
			gs_logger.error("SysUserWadServiceImpl juList方法异常",e);
			throw e;
		}
		gs_logger.info("SysUserWadServiceImpl juList方法结束");
		return list;
	}

}
