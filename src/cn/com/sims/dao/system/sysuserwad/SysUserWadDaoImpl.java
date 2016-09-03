package cn.com.sims.dao.system.sysuserwad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadinfo.SysWadInfo;
import cn.com.sims.model.syswadjuri.SysWadJuri;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Service
public class SysUserWadDaoImpl implements SysUserWadDao{
	@Resource
	private SqlMapClient sqlMapClient;
	@Override
	public List<SysUserWad> sysuserwadbyuserid(String str, String id)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,id);
	}
	@Override
	public List<SysWadJuri> wad_juri(String str, String id) throws Exception {
		// TODO Auto-generated method stub
		 	return sqlMapClient.queryForList(str,id);
	}
	@Override
	public List<Map<String, Object>> wadbyname(String str, HashMap<String, Object> map)
			throws Exception {

		return sqlMapClient.queryForList(str,map);
	}
	@Override
	public int wadbyname_num(String str, HashMap<String, Object> map)
			throws Exception {
		
		return (Integer) sqlMapClient.queryForObject(str,map);
	}

	public void Deletewadbycode(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.delete(str, code);
	}
	@Override
	public void Deleteuserwadbycode(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.delete(str, code);
	}
	@Override
	public void Deletewadjuribycode(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.delete(str, code);
	}
	@Override
	public HashMap<String, Object> wadinfobycode(String str, String code)
			throws Exception {
	
		return (HashMap<String, Object>) sqlMapClient.queryForObject(str,code);
	}
	@Override
	public List<Map<String, String>> juList(String str,String code) throws Exception {
		
		return sqlMapClient.queryForList(str,code);
	}
	@Override
	public void setwad_juri(String str, List<SysWadJuri> sysWadJuri)
			throws Exception {

		 sqlMapClient.insert(str, sysWadJuri);
	}
	@Override
	public void setwad_user(String str, List<SysUserWad> SysUserWad)
			throws Exception {

		sqlMapClient.insert(str, SysUserWad);
	}
	@Override
	public void updatawadinfo(String str, HashMap<String, Object> map)
			throws Exception {
		sqlMapClient.update(str, map);
	}
	@Override
	public void addwadinfo(String str, SysWadInfo sysWadInfo) throws Exception {
	
		sqlMapClient.insert(str, sysWadInfo);
	}
	@Override
	public List<SysWadInfo> wadlistbyname(String str, String name)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,name);
	}
	@Override
	public void Deleteuserwadbyusercode(String str, String code)
			throws Exception {
		sqlMapClient.delete(str, code);
		
	}
	@Override
	public int findBaskJuri(String str, Map<String, String> map)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str,map);
	}

}
