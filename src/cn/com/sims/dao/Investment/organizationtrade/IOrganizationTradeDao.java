package cn.com.sims.dao.Investment.organizationtrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.vieworganizationtrade.ViewOrganizationTrade;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
public interface IOrganizationTradeDao {

	/**
	 * 根据机构ｉｄ查询机构交易记录
	 * @param str
	 * @param organizationId　机构ＩＤ
	 * @return
	 * @throws Exception
	 */
	public List<ViewOrganizationTrade> findTradeByOrganizationId(String str,String id) throws Exception;
	
	
	/**
	 * 根据机构id分页查询机构交易记录
	 * @param str
	 * @param 条件集合map
	 * @return
	 * @throws Exception
	 */
	public List<ViewOrganizationTrade> findPageTradeByOrgId(String str,HashMap<String, String> map) throws Exception;
	
	/**
	 * 根据机构id查询机构交易记录总条数
	 * @param str
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 */
	public int findCountTradeByOrgId(String str,String orgCode) throws Exception;
	
	
}