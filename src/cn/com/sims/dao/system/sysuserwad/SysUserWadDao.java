package cn.com.sims.dao.system.sysuserwad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadinfo.SysWadInfo;
import cn.com.sims.model.syswadjuri.SysWadJuri;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
public interface SysUserWadDao {
	/**根据用户Id查询用户筐
	 * @author zzg
	 * @date 2015-10-13
	 */
	public List<SysUserWad> sysuserwadbyuserid(String str,String id) throws Exception;
	/**通过筐id 查询权限
	 * @author zzg
	 * @date 2015-10-30
	 */
		public List<SysWadJuri> wad_juri(String str,String id) throws Exception;
		/**根据筐名称 查询筐列表 包括权限 和员工
		 * @author zzg
		 * @date 2015-10-30
		 */
	public List<Map<String, Object>> wadbyname(String str,HashMap<String, Object> map) throws Exception;
			/**根据筐名称 查询筐列表 总条数
			 * @author zzg
			 * @date 2015-10-30
			 */
	public int wadbyname_num(String str,HashMap<String, Object> map) throws Exception;
				/**
				 * 根据code删除筐
				 * @param code
				 * @return
				 * @throws Exception
				 * @author zzg
				 * @date 2015-10-30
				 */
public void Deletewadbycode(String str,String code) throws Exception;
/**
 * 根据筐code删除用户筐
 * @param code
 * @return
 * @throws Exception
 * @author zzg
 * @date 2015-10-30
 */
public void Deleteuserwadbycode(String str,String code) throws Exception;
/**
 * 根据code删除筐权限
 * @param code
 * @return
 * @throws Exception
 * @author zzg
 * @date 2015-10-30
 */
public void Deletewadjuribycode(String str,String code) throws Exception;
/**
 * 根据code查询筐详情
 * @param code
 * @return
 * @throws Exception
 * @author zzg
 * @date 2015-10-30
 */
public HashMap<String, Object> wadinfobycode(String str,String code) throws Exception; 
/**
 * 根据筐code查询所有权限及勾选状态
 * @param code
 * @return
 * @throws Exception
 * @author zzg
 * @date 2015-11-02
 */
public List<Map<String, String>> juList(String str,String code) throws Exception;
/**批量添加筐权限
 * @author zzg
 * @date 2015-11-03
 * @param sysWadJuri
 * @return
 * @throws Exception
 */
public void setwad_juri(String str,	List<SysWadJuri> sysWadJuri)  throws Exception;
/**批量添加筐员工
 * @author zzg
 * @date 2015-11-03
 * @param sysWadJuri
 * @return
 * @throws Exception
 */
public void setwad_user(String str,	List<SysUserWad> SysUserWad)  throws Exception;
/**更改筐基本信息
 * @author zzg
 * @date 2015-11-03
 * @param map
 * @return
 * @throws Exception
 */
public void updatawadinfo(String str,	HashMap<String, Object> map) throws Exception;
/**
 * 添加筐信息
 * @author zzg
 * @date 2015-11-03
 * @return
 * @throws Exception
 */
	public void addwadinfo(String str, SysWadInfo sysWadInfo) throws Exception;
	/**根据筐名称 查询筐列表 
	 * @author zzg
	 * @date 2015-11-05
	 */
	public List<SysWadInfo> wadlistbyname(String str, String name) throws Exception;
	/**
	 * 根据用户code删除用户筐
	 * @param code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-30
	 */
	public void Deleteuserwadbyusercode(String str,String code) throws Exception;
	
	/**
	 * 查询筐是否有指定权限
	 * @param str
	 * @param map(wadcode,juricode)
	 * @return
	 * @throws Exception
	 */
	public int findBaskJuri(String str,Map<String, String> map)throws Exception;
}
