package cn.com.sims.dao.Investment.organizationinvesfund;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
public interface IOrganizationInvesfundDao {

	/**
	 * 查询投资机构基金条数
	 * @param str
	 * @param orgCode投资机构code
	 * @return
	 * @throws Exception
	 */
	public int findCountTfundByOrgId(String str,String orgCode) throws Exception;

	/**
	 * 根据投资机构Ｉｄ查询基金信息
	 * @param str
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesfundInfo> findInvesfundByOrganizationId(String str,String id)throws Exception;
	
	/**
	 * 根据投资机构code分页查询基金信息
	 * @param str
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesfundInfo> findPageInvesfundByOrgId(String str,Map<String, String> map)throws Exception;
	
	
	/**
	 * 添加投资机构基金信息
	 * @param str
	 * @param 新增投资机构基金对象
	 * @throws Exception
	 */
	public void insertInvesfund(String str,BaseInvesfundInfo info) throws Exception;
	
	/**
	 * 修改投资机构基金
	 * @param str
	 * @param info 基金对象
	 * @return
	 * @throws Exception
	 */
	public int updateInvesfundByCode(String str,BaseInvesfundInfo info)throws Exception;
	
	/**
	 * 删除投资机构基金
	 * @param str
	 * @param fundCode 基金code
	 * @return
	 * @throws Exception
	 */
	public int deleteInvesfundByCode(String str,String fundCode)throws Exception;
	
}
