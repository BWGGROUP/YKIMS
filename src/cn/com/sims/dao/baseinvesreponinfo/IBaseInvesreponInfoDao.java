package cn.com.sims.dao.baseinvesreponinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;

/**
 * 
 * @author duwenjie
 *	@date 2015-10-12
 */
public interface IBaseInvesreponInfoDao {

	/**
	 * 根据投资机构Code查询易凯联系人
	 * @param str
	 * @param 机构code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findYKLinkManByOrgCode(String str,String orgCode) throws Exception;
	
	/**
	 * 根据投资机构易凯联系人
	 * @param str
	 * @param 易凯联系人对象info
	 * @throws Exception
	 */
	public void insertYKLinkMan(String str,BaseInvesreponInfo info) throws Exception;

	/**
	 * 修改投资机构易凯联系人
	 * @param str
	 * @param info易凯联系人对象
	 * @return
	 * @throws Exception
	 */
	public int tranModifyYKLinkMan(String str,BaseInvesreponInfo info) throws Exception;
}
