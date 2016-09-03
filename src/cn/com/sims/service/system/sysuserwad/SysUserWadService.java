package cn.com.sims.service.system.sysuserwad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadinfo.SysWadInfo;
import cn.com.sims.model.syswadjuri.SysWadJuri;

public interface SysUserWadService {
/**根据用户Id查询用户筐
 * @author zzg
 * @date 2015-10-13
 */
	public List<SysUserWad> sysuserwadbyuserid(String id) throws Exception;
	/**通过筐id 查询权限
	 * @author zzg
	 * @date 2015-10-30
	 */
		public List<SysWadJuri> wad_juri(String id) throws Exception;
		/**根据筐名称 查询筐列表 包括权限 和员工
		 * @author zzg
		 * @date 2015-10-30
		 */
public List<Map<String, Object>> wadbyname(HashMap<String, Object> map) throws Exception;
			/**根据筐名称 查询筐列表 总条数
			 * @author zzg
			 * @date 2015-10-30
			 */
	public int wadbyname_num(HashMap<String, Object> map) throws Exception;		
	/**
	 * 根据code删除筐
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-30
	 */
	public Boolean tranDeletewadbycode(String code) throws Exception;	
	/**
	 * 根据code查询筐详情
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-11-02
	 */
	public HashMap <String, Object> wadinfobycode(String code) throws Exception;
	
	/**
	 * 查询所有权限
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-11-02
	 */
	public List<Map<String, String>> juList(String code) throws Exception;
	
	/**
	 * 根据筐code删除筐权限
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-11-03
	 */
	public Boolean tranDeletewadjuribycode(String code) throws Exception;	
	/**
	 * @author zzg
	 * @date 2015-11-03
	 * @param sysWadJuri
	 * @return
	 * @throws Exception
	 */
	public Boolean setwad_juri(	List<SysWadJuri> sysWadJuri)  throws Exception;
	/**
	 * 根据筐code删除筐下所有员工
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-11-03
	 */
	public Boolean tranDeleteuserwadbycode(String code) throws Exception;	
	/**
	 * @author zzg
	 * @date 2015-11-03
	 * @param sysWadJuri
	 * @return
	 * @throws Exception
	 */
	public Boolean setwad_user(	List<SysUserWad> SysUserWad)  throws Exception;
	/**更改筐基本信息
	 * @author zzg
	 * @date 2015-11-03
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Boolean updatawadinfo(HashMap<String, Object> map) throws Exception;
	/**更改筐全部信息，用于回滚
	 * @author zzg
	 * @date 2015-11-03
	 * @param map
	 * @param sysWadJuris
	 * @param SysUserWads
	 * @return
	 * @throws Exception
	 */
	public Boolean tranModifywadallinfo(HashMap<String, Object> map,List<SysWadJuri> sysWadJuris,	List<SysUserWad> SysUserWads) throws Exception;
/**
 * 添加筐信息
 * @author zzg
 * @date 2015-11-03
 * @return
 * @throws Exception
 */
	public Boolean addwadinfo(SysWadInfo sysWadInfo) throws Exception;
	/**
	 *  添加筐及人员 权限
	 * @date 2015-11-05
	 * @author zzg
	 * @param sysWadInfo
	 * @param sysWadJuris
	 * @param SysUserWads
	 * @return
	 * @throws Exception
	 */
	public  Boolean tranSavewadinfo(SysWadInfo sysWadInfo ,List<SysWadJuri> sysWadJuris,	List<SysUserWad> SysUserWads) throws Exception;
	/**根据筐名称 查询筐列表 
	 * @author zzg
	 * @date 2015-11-05
	 */
	public List<SysWadInfo> wadlistbyname(String name) throws Exception;
}
