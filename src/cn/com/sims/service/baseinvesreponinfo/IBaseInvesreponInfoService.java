package cn.com.sims.service.baseinvesreponinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-12
 */
public interface IBaseInvesreponInfoService {
	
	/**
	 * 根据投资机构code查询易凯联系人
	 * @param str
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findYKLinkManByOrgCode(String orgCode) throws Exception;

	/**
	 * 根据投资机构易凯联系人
	 * @param str
	 * @param 易凯联系人对象info
	 * @throws Exception
	 */
	public void insertYKLinkMan(BaseInvesreponInfo info) throws Exception;
	
	/**
	 * 修改投资机构易凯联系人
	 * @param orgCode投资机构code
	 * @param oldUserCode原数据code
	 * @param newUserCode新数据code
	 * @param newUserName新数据Name
	 * @param updCode更新人code
	 * @return
	 * @throws Exception
	 */
	public int tranModifyYKLinkMan(String orgCode,String oldUserCode,String newUserCode,String newUserName,String updCode) throws Exception;
}
